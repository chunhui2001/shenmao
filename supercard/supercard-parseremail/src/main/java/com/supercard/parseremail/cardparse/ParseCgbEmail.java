package com.supercard.parseremail.cardparse;

import com.supercard.tour.BillEntity;
import com.supercard.tour.BillItemEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.select.Elements;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by keesh on 17/09/2017.
 */
public class ParseCgbEmail extends ParseEmailBase {

    private static final Pattern _CARD_NUMBER_PATTERN = Pattern.compile("([\\d]{4}\\*{8}[\\d]{4})");

    public ParseCgbEmail() {

    }

    public ParseCgbEmail(String userEmail, String htmlContent) {
        super(userEmail, htmlContent);
    }

    public ParseCgbEmail(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {
        super(userEmail, message, parser);
    }

    private File getAttachBill() {

        List<DataSource> emailAttachment = this.parser.getAttachmentList();

        for (DataSource atta : emailAttachment) {

            if (!atta.getName().equals("综合对账单打印版.pdf")) continue;

            InputStream ins = null;

            try {
                ins = atta.getInputStream();
                return saveFile(System.getProperty("java.io.tmpdir"),
                        atta.getName().split("\\.")[0] + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(this.receivDate) + "." + atta.getName().split("\\.")[1], ins);
            } catch (IOException e) {

            }


        }

        return null;
    }

    public String getPdfBillContent(File pdFile) {
        File attacheBill = pdFile;

        if (attacheBill == null || !attacheBill.exists()) return null;

        String _content = null;
        PDDocument doc = null;

        try {
            doc = PDDocument.load(attacheBill);
            _content = new PDFTextStripper().getText(doc);
        } catch (IOException e) {
            return null;
        } finally {
            if (doc != null) try {
                doc.close();
            } catch (IOException e) {

            }
        }

        return _content;
    }



    private Collection<BillItemEntity> parseBillItems(String contentString, BillEntity bill) {

        String[] contentLines = contentString.split("\\n");

        if (contentLines.length == 0) return null;

        Collection<BillItemEntity> billItems = new ArrayList<>();
        BillItemEntity billItem = null;
        String[] lineArray = null;

        for (String line : contentLines) {

            lineArray = line.split("\\s");

            if (lineArray.length == 6) {

                Matcher cardNumberMatcher = _CARD_NUMBER_PATTERN.matcher(lineArray[0]);

                if (bill.getBillMoney() == null && cardNumberMatcher.find()) {
                    bill.setBillMoney(lineArray[1]);
                    bill.setBillMoneyMin(lineArray[2]);
                    bill.setBillExpired(lineArray[3]);
                }

                continue;
            }

            if (lineArray.length != 5) continue;

            String transDesc = lineArray[0];
            String recordDate = lineArray[1];
            String transMoney = lineArray[2];
            String transCurreny = lineArray[4];
            String transDate = null;

            Matcher transDateMatcher = _DATE_PATTERN_DASH.matcher(transDesc);

            if (transDateMatcher.find()) {
                transDate = transDateMatcher.group(1);
                transDesc = transDesc.replace(transDate, "");
            }

            if (!(transDate != null && transDesc != null && recordDate != null && transMoney != null && transCurreny != null)) {
                continue;
            }

            billItem = new BillItemEntity();

            billItem.setRecordDate(recordDate);
            billItem.setTransDate(transDate);
            billItem.setTransMoney(transMoney);
            billItem.setTransCardNumber(bill.getCardNumber());
            billItem.setTransCurrency(transCurreny);
            billItem.setTransDesc(transDesc);
            billItem.setTransType(transMoney.charAt(0) == '-' ? "repayment" : "consumption");

            billItems.add(billItem);

        }

        return billItems;

    }

    @Override
    public Collection<BillEntity> parse() {

        BillEntity bill = new BillEntity();

        File attacheBill = getAttachBill();

        if (attacheBill == null) {

            // 新账单，非补发
            String custNameAndGenderHtml = emailHtmlDoc.select("div#fixBand8 div>font").first().html();
            String billBoundHtml = emailHtmlDoc.select("div#fixBand32 div>font").last().html();    // 2017/09/06 至 2017/10/05
            Elements billItemElements = emailHtmlDoc.select("div#reportPanel1 div#fixBand4 table table font");

            Pattern billMonthPattern = Pattern.compile("以下是您([\\d]{4})年([\\d]{2})月的信用卡电子账单");
            Pattern custNameAndGenderPattern = Pattern.compile("尊敬的([\\W\\w]+)(先生|女士)+,");
            Pattern datePattern = Pattern.compile("([\\d]{4}/[\\d]{2}/[\\d]{2})");

            Matcher custNameAndGenderMatcher = custNameAndGenderPattern.matcher(custNameAndGenderHtml);
            Matcher dateMatcher = datePattern.matcher(billBoundHtml);
            Matcher billMonthMatcher = billMonthPattern.matcher(this.content);

            if (billMonthMatcher.find()) {
                bill.setBillMonth(billMonthMatcher.group(1) + billMonthMatcher.group(2));
            }

            if (custNameAndGenderMatcher.find()) {
                bill.setCustomerName(custNameAndGenderMatcher.group(1));
                bill.setGender(custNameAndGenderMatcher.group(2));
            }

            if (dateMatcher.find()) {
                bill.setBillBoundMin(dateMatcher.group(1));
                dateMatcher.find();
                bill.setBillBoundMax(dateMatcher.group(1));
            }

            bill.setCardNumber(billItemElements.get(0).html());
            bill.setBillMoney(billItemElements.get(1).html());
            bill.setBillMoneyMin(billItemElements.get(2).html());
            bill.setBillExpired(billItemElements.get(3).html());
            bill.setCurrency(billItemElements.get(4).html());
//            bill.setCardNumber(billItemElements.get(5).html());

            bill.setBank("广发");
            bill.setUserIdentity(useremail);
            bill.setReceivDate(this.getReceivDate());
            bill.setSubject(this.subject);
            bill.setBillContent(this.content);
            bill.setContentType("HTML");

            // TODO 本期还款金额，待补充

            return new ArrayList<BillEntity>() {{ add(bill); }};

        } else {

//        File attacheBill = new File("/Users/keesh/Downloads/综合对账单打印版_20170910014535.pdf");

            String _content = getPdfBillContent(attacheBill);

            Pattern custNameAndAddressPattern = Pattern.compile("积分按卡号汇总情况\\s{1,}([\\+ ＝\\－]{4}?)\\s{1,}([\\W\\w]+?)\\s{1,}([\\W\\w]+?)\\s{1,}([\\W\\w]+?)\\s{1,}([\\W\\w]+?)\\s{1,}([\\d]{25})\\s{1,}/");
            Pattern billBoundPattern = Pattern.compile("(\\d{4}/\\d{2}/\\d{2})\\s{1,}-\\s{1,}(\\d{4}/\\d{2}/\\d{2})");

            Matcher custNameAndAddressMatcher = custNameAndAddressPattern.matcher(_content);
            Matcher cardNumberMatcher = _CARD_NUMBER_PATTERN.matcher(_content);
            Matcher billBoundMatcher = billBoundPattern.matcher(_content);


            if (custNameAndAddressMatcher.find()) {
                bill.setCustomerName(custNameAndAddressMatcher.group(2));
                if (bill.getCustomerName().equals("调额信息")) bill.setCustomerName(custNameAndAddressMatcher.group(3));
            }
            if (cardNumberMatcher.find()) bill.setCardNumber(cardNumberMatcher.group(1));
            if (billBoundMatcher.find()) {
                bill.setBillBoundMin(billBoundMatcher.group(1));
                bill.setBillBoundMax(billBoundMatcher.group(2));
                bill.setBillMonth(bill.getBillBoundMin().split("/")[0] + bill.getBillBoundMin().split("/")[1]);
            }

            bill.setBank("广发");
            bill.setBillItems(parseBillItems(_content, bill));
            bill.setUserIdentity(useremail);
            bill.setReceivDate(this.getReceivDate());
            bill.setSubject(this.subject);
            bill.setBillContent(_content);
            bill.setContentType("PDF");

            return new ArrayList<BillEntity>() {{ add(bill); }};
        }


    }
}
