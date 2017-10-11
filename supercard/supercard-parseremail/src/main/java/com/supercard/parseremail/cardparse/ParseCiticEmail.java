package com.supercard.parseremail.cardparse;

import com.supercard.tour.BillEntity;
import com.supercard.tour.BillItemEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by keesh on 10/09/2017.
 */
public class ParseCiticEmail extends ParseEmailBase {

    public ParseCiticEmail() {

    }

    public ParseCiticEmail(String userEmail, String htmlContent) {
        this.useremail = userEmail;
        this.content = htmlContent;
        this.emailHtmlDoc = Jsoup.parse(this.content);
    }

    public ParseCiticEmail(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {
        super(userEmail, message, parser);
    }

    @Override
    public Collection<BillEntity> parse() {

        BillEntity bill = new BillEntity();

        Pattern userNamePattern = Pattern.compile("尊敬的([\\W\\w]+?)?(先生|女士)+：");
        Pattern billBoundPattern = Pattern.compile("([\\d]{4}年[\\d]{2}月)账单已产生([\\W\\w]+?)记录了您([\\d]{4}年[\\d]{2}月[\\d]{2}日)-([\\d]{4}年[\\d]{2}月[\\d]{2}日)账户变动信息");
        Pattern billExpiredPattern = Pattern.compile("到期还款日：([\\d]{4}年[\\d]{2}月[\\d]{2}日)");

        Matcher userNameMatcher = userNamePattern.matcher(this.content);
        Matcher billBoundMatcher = billBoundPattern.matcher(this.content);
        Matcher billExpiredMatcher = billExpiredPattern.matcher(this.content);

        if (userNameMatcher.find()) {
            bill.setCustomerName(userNameMatcher.group(1));
            bill.setGender(userNameMatcher.group(2));
        }

        if (billBoundMatcher.find()) {
            bill.setBillMonth(billBoundMatcher.group(1).replaceAll("年", "/").replace("月", ""));
            bill.setBillBoundMin(billBoundMatcher.group(3).replaceAll("年|月", "/").replace("日", ""));
            bill.setBillBoundMax(billBoundMatcher.group(4).replaceAll("年|月", "/").replace("日", ""));
        }

        if (billExpiredMatcher.find()) {
            bill.setBillExpired(billExpiredMatcher.group(1).replaceAll("年|月", "/").replace("日", ""));
        }

//        Elements billTdelements = this.emailHtmlDoc.select("span#fixBand33 span#fixBand3 span#loopBand2 span#fixBand5 div font");
        Elements billTdelements = this.emailHtmlDoc.select("span#fixBand33 span#fixBand3 span#loopBand2>table>tbody>tr:nth-child(2) table table table"); // 找第四级 table ，可能有多币种

        bill.setBillMoney("");
        bill.setBillMoneyMin("");
        bill.setCurrency("");

        for (int i=0; i<billTdelements.size(); i++) {

            Elements billFontElements = billTdelements.get(i).select("div font");

            int offset = 0;

            if (billFontElements.size() == 7) {
                // 有卡号
                bill.setCardNumber(billFontElements.get(0).html());
            } else {
                // 无卡号
                offset = 1;
            }

            bill.setBillMoney(bill.getBillMoney() + billFontElements.get(5 - offset).html().replaceAll(",", "") + ",");
            bill.setBillMoneyMin(bill.getBillMoneyMin() + billFontElements.get(6 - offset).html().replaceAll(",", "") + ",");
            bill.setCurrency(bill.getCurrency() + billFontElements.get(1 - offset).html().replaceAll(",", "") + ",");

        }

        bill.setBillMoney(bill.getBillMoney().substring(0, bill.getBillMoney().length() - 1));
        bill.setBillMoneyMin(bill.getBillMoneyMin().substring(0, bill.getBillMoneyMin().length() - 1));
        bill.setCurrency(bill.getCurrency().substring(0, bill.getCurrency().length() - 1));

        bill.setBank("中信");
//        bill.setCardNumber(billTdelements.get(0).html());
//        bill.setBillMoney(billTdelements.get(5).html());
//        bill.setBillMoneyMin(billTdelements.get(6).html());

        // parse billItems
        bill.setBillItems(parseBillItems(bill));
        bill.setUserIdentity(useremail);

        bill.setReceivDate(this.getReceivDate());
        bill.setSubject(this.subject);
        bill.setBillContent(this.content);
        bill.setContentType("HTML");

        return new ArrayList<BillEntity>() {{ add(bill); }};
    }

    private Collection<BillItemEntity> parseBillItems(BillEntity bill) {

        Elements billItemsElement = this.emailHtmlDoc.select("span#fixBand7>table");
        Elements tdElements = null;

        Collection<BillItemEntity> billItems = new ArrayList<>();
        BillItemEntity billItem = null;

        for (int i = 0; i < billItemsElement.size(); i++) {

            if (billItemsElement.get(i).select("#fixBand53").size() > 0) continue;

            billItem = new BillItemEntity();
            tdElements = billItemsElement.get(i).select("div font");

            billItem.setTransDate(tdElements.get(0).html());
            billItem.setRecordDate(tdElements.get(1).html());
            billItem.setTransCardNumber(tdElements.get(2).html());
            billItem.setTransDesc(escapeContent(tdElements.get(3).html()));
            billItem.setTransCurrency(tdElements.get(6).html());
            billItem.setTransMoney(tdElements.get(7).html());
            billItem.setTransType("consumption");

            billItems.add(billItem);

        }

        return billItems;

    }

}