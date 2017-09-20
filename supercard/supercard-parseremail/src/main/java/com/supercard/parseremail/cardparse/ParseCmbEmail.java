package com.supercard.parseremail.cardparse;

import com.supercard.tour.BillEntity;
import com.supercard.tour.BillItemEntity;
import org.apache.commons.mail.util.MimeMessageParser;
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

    private Collection<BillItemEntity> parseBillItems(BillEntity bill) {

        Elements billItemsTableTrsElement = emailHtmlDoc.select("span#reportPanel1 span#fixBand15 > table");
        Element billItemsTableTrElement = null;
        Elements billItemsTdsElement = null;

        Collection<BillItemEntity> billItems = new ArrayList<>();
        BillItemEntity billItem = null;

        for (int i=0; i<billItemsTableTrsElement.size(); i++) {

            billItemsTableTrElement = billItemsTableTrsElement.get(i).select(" table>tbody>tr").first();

            billItemsTdsElement = billItemsTableTrElement.select(">td>table>tbody>tr>td>div>font");

            billItem = new BillItemEntity();

            billItem.setTransDate(billItemsTdsElement.get(0).html());
            billItem.setRecordDate(billItemsTdsElement.get(1).html());
            billItem.setTransDesc(billItemsTdsElement.get(2).html());
            billItem.setTransMoney(billItemsTdsElement.get(3).html());
            billItem.setTransCardNumber(billItemsTdsElement.get(4).html());
            billItem.setTransArea(billItemsTdsElement.get(5).html());
//            billItem.setTransMoney(billItemsTdsElement.get(3).html());

            billItem.setTransCurrency(billItem.getTransMoney().split("&nbsp;")[0].replace("-", ""));

            if (billItem.getTransArea().equals("&nbsp;")) {
                billItem.setTransArea(null);
            }


            if (billItem.getTransDate().equals("&nbsp;")) {
                billItem.setTransType("repayment");
                billItem.setTransDate(null);
            } else {
                billItem.setTransType("consumption");
            }

            if (billItem.getTransDate() != null)
                billItem.setTransDate(bill.getBillMonth().substring(0,4) + "/" + billItem.getTransDate().substring(0, 2) + "/" + billItem.getTransDate().substring(2,4));

            if (billItem.getRecordDate() != null)
                billItem.setRecordDate(bill.getBillMonth().substring(0,4) + "/" + billItem.getRecordDate().substring(0, 2) + "/" + billItem.getRecordDate().substring(2,4));

            if (billItem.getTransDesc() != null) {
                billItem.setTransDesc(escapeContent(billItem.getTransDesc()));
            }

            billItem.setTransMoney(billItem.getTransMoney().split("&nbsp;")[1]);

            billItems.add(billItem);

        }

        return billItems;

    }

    public BillEntity parseNormalBill() {

        Element billExpiredElement = emailHtmlDoc.select("span#fixBand18") != null && emailHtmlDoc.select("span#fixBand18").size() > 0 ? emailHtmlDoc.select("span#fixBand18").first() : null;
        Element billMoneyElement = emailHtmlDoc.select("span#fixBand57") != null && emailHtmlDoc.select("span#fixBand57").size() > 0 ? emailHtmlDoc.select("span#fixBand57").first() : null;
        Element billMoneyMinElement = emailHtmlDoc.select("span#fixBand46") != null && emailHtmlDoc.select("span#fixBand46").size() > 0 ? emailHtmlDoc.select("span#fixBand46").first() : null;

        if (!(billExpiredElement != null && billMoneyElement != null && billMoneyMinElement != null)) return null;

        BillEntity bill = new BillEntity();

        Pattern billExpiredPattern = Pattern.compile("([\\d]{2}/[\\d]{2})");
        Pattern moneyRbmPattern = Pattern.compile("(￥[\\d\\.]+)");
        Pattern billMonthPattern = Pattern.compile("billMonth=([\\d]{6})");

        Matcher billExpiredMatcher = billExpiredPattern.matcher(billExpiredElement.html());
        Matcher billMoneyMatcher = moneyRbmPattern.matcher(billMoneyElement.html());
        Matcher billMoneyMinMatcher = moneyRbmPattern.matcher(billMoneyMinElement.html());
        Matcher billMonthMatcher = billMonthPattern.matcher(this.content);

        if (billMonthMatcher.find()) bill.setBillMonth(billMonthMatcher.group(1));
        if (billExpiredMatcher.find()) bill.setBillExpired(billExpiredMatcher.group(1));
        if (billMoneyMatcher.find()) bill.setBillMoney(billMoneyMatcher.group(1));
        if (billMoneyMinMatcher.find()) bill.setBillMoneyMin(billMoneyMinMatcher.group(1));

        bill.setReceivDate(this.getReceivDate());
        bill.setUserIdentity(this.getUseremail());

        return bill;

    }

    @Override
    public Collection<BillEntity> parse() {


        Collection<BillEntity> billList = new ArrayList<>();
        BillEntity bill = parseNormalBill();

        if (bill != null) {
            billList.add(bill);
            return billList;
        }

        bill = new BillEntity();

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

        bill.setBank("招商");
        bill.setBillItems(parseBillItems(bill));
        bill.setUserIdentity(useremail);
        bill.setReceivDate(this.getReceivDate());

        billList.add(bill);

        return billList;

    }
}
