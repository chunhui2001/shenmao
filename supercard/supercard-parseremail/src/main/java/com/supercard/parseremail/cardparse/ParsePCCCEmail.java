package com.supercard.parseremail.cardparse;

import com.supercard.parseremail.BillEntity;
import com.supercard.parseremail.entities.BillItem;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by keesh on 09/09/2017.
 */
public class ParsePCCCEmail extends ParseEmailBase {

    public ParsePCCCEmail() {

    }

    public ParsePCCCEmail(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {
        super(userEmail, message, parser);
    }


    public ParsePCCCEmail(String userEmail, String htmlContent) {
        super(userEmail, htmlContent);
    }

    public Collection<BillEntity> parse() {

        orderElements = emailHtmlDoc.select("div#cntDiv");

        Collection<BillEntity> billEntityList = new ArrayList<>();

        orderElements.stream().forEach( element -> {

            BillEntity bill = new BillEntity();

            bill.setUserIdentity(this.useremail);

            Element element1 = element.select("#container>div").first();
            Element element2 = element.select("#container #billInfo1>#table1").first();
            // Element element3 = element2.nextElementSibling();    // ignore this element
            Element element4 = element.select("#container #billInfo19").first();

            parseElement1(element1, bill);
            parseElement2(element2, bill);
            parseElement4(element4, bill);

            billEntityList.add(bill);

        });

        return billEntityList;

    }

    private void parseElement1(Element element1, BillEntity bill) {

        String element1Html = element1.html();

        Pattern userNameRegexPattern = Pattern.compile("尊敬的([\\W\\w]+)(先生|女士)+您好");
        Pattern cardMonthRegexPattern = Pattern.compile("([\\d]{4}年[\\d]{2}月)");
        Pattern cardNumberRegexPattern = Pattern.compile("([\\d]{6}[\\*]+[\\d]{4})");
        Pattern orderMonthBoundPattern = Pattern.compile("([\\d]{4}/[\\d]{2}/[\\d]{2})-([\\d]{4}/[\\d]{2}/[\\d]{2})");

        Matcher userNameMatcher = userNameRegexPattern.matcher(element1Html);
        Matcher cardMonthMatcher = cardMonthRegexPattern.matcher(element1Html);
        Matcher cardNumberMatcher = cardNumberRegexPattern.matcher(element1Html);
        Matcher orderMonthBoundMather = orderMonthBoundPattern.matcher(element1Html);

        if (userNameMatcher.find()) {
            bill.setCustomerName(userNameMatcher.group(1).trim());
            bill.setGender(userNameMatcher.group(2));
        }

        if (cardMonthMatcher.find()) bill.setBillMonth(cardMonthMatcher.group(1));
        if (cardNumberMatcher.find()) bill.setCardNumber(cardNumberMatcher.group(1));
        if (orderMonthBoundMather.find()) {
            bill.setBillBoundMin(orderMonthBoundMather.group(1));
            bill.setBillBoundMax(orderMonthBoundMather.group(2));
        }

    }

    private void parseElement2(Element element2, BillEntity bill) {

        Elements elements = element2.select(">tbody>tr");

        Pattern billExpiredPattern = Pattern.compile("([\\d]{4}/[\\d]{2}/[\\d]{2}|[\\-]{3})");
        Pattern billMoneyPattern = Pattern.compile("(￥([\\-\\s\\d\\.]+))");

        Element billExpiredElement = elements.get(0);
        Element billMoneyElement = elements.get(1);
        Element billMoneyMinElement = elements.get(2);

        Matcher billExpiredMatcher = billExpiredPattern.matcher(billExpiredElement.html());
        Matcher billMoneyMatcher = billMoneyPattern.matcher(billMoneyElement.html());
        Matcher billMoneyMinMatcher = billMoneyPattern.matcher(billMoneyMinElement.html());

        if (billExpiredMatcher.find()) bill.setBillExpired(billExpiredMatcher.group(1));
        if (billMoneyMatcher.find()) bill.setBillMoney(billMoneyMatcher.group(1));

        if (billMoneyMinMatcher.find()) bill.setBillMoneyMin(billMoneyMinMatcher.group(1));



    }

    private Collection<BillItem> getBillItems(Element element4, int index) {

        Pattern cardNumberPattern = Pattern.compile("([\\d]{4,})");

        Elements elements = element4.select(">div>#table3");

        if (index + 1 > elements.size()) return null;

        Element element = elements.get(index);
        String cardNumber = null;
        Element cardNumberElement = element.select(".DetailDataCell span.InfoData").get(1);
        Matcher cardNumberMatcher = cardNumberPattern.matcher(cardNumberElement.html());
        if (cardNumberMatcher.find()) cardNumber = cardNumberMatcher.group(1);
        Elements trElements = element.select(">tbody>tr");
        Element itemElement = null;

        Collection<BillItem> billItemList = new ArrayList<>();

        BillItem billItem = null;

        for (int i=0; i<trElements.size(); i++) {

            billItem = new BillItem();

            itemElement = trElements.get(i);
            Elements tdElement = itemElement.select(">td");

            if (tdElement.size() != 5) continue;

            billItem.setTransCardNumber(cardNumber);
            billItem.setTransDate(tdElement.get(0).html());
            billItem.setRecordDate(tdElement.get(1).html());
            billItem.setTransDesc(tdElement.get(2).html());
            billItem.setTransCurrency(tdElement.get(3).html().split(" ")[0]);
            billItem.setTransMoney(tdElement.get(3).html().split(" ")[1]);

            if (billItem.getTransDesc() != null && billItem.getTransDesc().contains("还款")) {
                billItem.setTransType("repayment");
            }

            if (billItem.getTransDesc() != null && billItem.getTransDesc().contains("消费")) {
                billItem.setTransType("consumption");
            }

            billItemList.add(billItem);

        }

        return billItemList;

    }

    private void parseElement4(Element element4, BillEntity bill) {

        bill.setBillItems(getBillItems(element4, 0));
        bill.setBillItems(getBillItems(element4, 1));

    }


}
