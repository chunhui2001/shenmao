package com.supercard.cardparse;

import com.supercard.BillEntity;
import com.supercard.entities.BillItem;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by keesh on 10/09/2017.
 */
public class ParseCmbEmail extends ParseEmailBase {

    public ParseCmbEmail() {

    }


    public ParseCmbEmail(String userEmail, String htmlContent) {
        super(userEmail, htmlContent);
    }

    public ParseCmbEmail(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {
        super(userEmail, message, parser);
    }

    private Collection<BillItem> parseBillItems(BillEntity bill) {

        Elements billItemsTableTrsElement = emailHtmlDoc.select("span#reportPanel1 span#fixBand15 > table");
        Element billItemsTableTrElement = null;
        Elements billItemsTdsElement = null;

        Collection<BillItem> billItems = new ArrayList<>();
        BillItem billItem = null;

        for (int i=0; i<billItemsTableTrsElement.size(); i++) {

            billItemsTableTrElement = billItemsTableTrsElement.get(i).select(" table>tbody>tr").first();

            billItemsTdsElement = billItemsTableTrElement.select(">td>table>tbody>tr>td>div>font");

            billItem = new BillItem();

            billItem.setBillDate(billItemsTdsElement.get(0).html());
            billItem.setRecordDate(billItemsTdsElement.get(1).html());
            billItem.setDesc(billItemsTdsElement.get(2).html());
            billItem.setMoney(billItemsTdsElement.get(3).html());
            billItem.setCardNumber(billItemsTdsElement.get(4).html());
            billItem.setTransArea(billItemsTdsElement.get(5).html());
//            billItem.setMoney(billItemsTdsElement.get(3).html());

            billItem.setCurrency(billItem.getMoney().split("&nbsp;")[0].replace("-", ""));

            if (billItem.getTransArea().equals("&nbsp;")) {
                billItem.setTransArea(null);
            }


            if (billItem.getBillDate().equals("&nbsp;")) {
                billItem.setType("repayment");
                billItem.setBillDate(null);
            } else {
                billItem.setType("consumption");
            }

            if (billItem.getBillDate() != null)
                billItem.setBillDate(bill.getBillMonth().substring(0,4) + "/" + billItem.getBillDate().substring(0, 2) + "/" + billItem.getBillDate().substring(2,4));

            if (billItem.getRecordDate() != null)
                billItem.setRecordDate(bill.getBillMonth().substring(0,4) + "/" + billItem.getRecordDate().substring(0, 2) + "/" + billItem.getRecordDate().substring(2,4));

            if (billItem.getDesc() != null) {
                billItem.setDesc(billItem.getDesc().replaceAll("&nbsp;", " "));
            }

            billItem.setMoney(billItem.getMoney().split("&nbsp;")[1]);

            billItems.add(billItem);

        }

        return billItems;

    }

    @Override
    public Collection<BillEntity> parse() {

        BillEntity bill = new BillEntity();

        Element billMonthBoundElement = emailHtmlDoc.select("span#fixBand6").first();
        Element billMontyElement = emailHtmlDoc.select("span#fixBand7").first();
        Element billExpiredElement = emailHtmlDoc.select("span#fixBand8").first();


        Pattern userNameAndBillMonthPattern = Pattern.compile("尊敬的(&nbsp;)?([\\W\\w]+?)(&nbsp;)?(先生|女士)+，您好！以下是您的招商银行信用卡([\\d]+)月账单");
        Pattern billMonthBoundPattern = Pattern.compile("([\\d]{4}/[\\d]{2}/[\\d]{2})-([\\d]{4}/[\\d]{2}/[\\d]{2})(\\(补\\))?");
        Pattern moneyPattern = Pattern.compile("(￥([\\-\\s\\d\\.]+))");
        Pattern datePattern = Pattern.compile("([\\d]{4}/[\\d]{2}/[\\d]{2})");


        Matcher userNameAndBillMonthMatcher = userNameAndBillMonthPattern.matcher(this.content);
        Matcher billMonthBoundMatcher = billMonthBoundPattern.matcher(billMonthBoundElement.html());
        Matcher billMoneyMatcher = moneyPattern.matcher(billMontyElement.html());
        Matcher billExpiredMatcher = datePattern.matcher(billExpiredElement.html());

        if (userNameAndBillMonthMatcher.find()) {
            bill.setCustomerName(userNameAndBillMonthMatcher.group(2));
            bill.setGender(userNameAndBillMonthMatcher.group(4));
            bill.setBillMonth(userNameAndBillMonthMatcher.group(5));
        }

        if (billMonthBoundMatcher.find()) {
            bill.setBillBoundMin(billMonthBoundMatcher.group(1));
            bill.setBillBoundMax(billMonthBoundMatcher.group(2));
        }

        if (billMoneyMatcher.find()) {
            bill.setBillMoney(billMoneyMatcher.group(1));
            if (billMoneyMatcher.find()) {
                bill.setBillMoneyMin(billMoneyMatcher.group(1));
            }
        }



        if (billExpiredMatcher.find()) {
            bill.setBillExpired(billExpiredMatcher.group(1));
        }

        if (bill.getBillBoundMin() != null
                && bill.getBillBoundMin().length() > 0
                && bill.getBillBoundMin().indexOf("/") != -1) {
            bill.setBillMonth(bill.getBillBoundMin().split("/")[0] + "/" + bill.getBillMonth());
        }

        bill.setBillItems(parseBillItems(bill));
        bill.setUserEmail(useremail);


        return new ArrayList<BillEntity>() {{ add(bill); }};

    }
}
