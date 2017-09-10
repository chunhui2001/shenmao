package com.supercard.cardparse;

import com.supercard.BillEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.internet.MimeMessage;
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

        Elements elements = this.emailHtmlDoc.select("span#fixBand5");

        Elements elements2 = this.emailHtmlDoc.select("span#fixBand7>table");



        for (int i =0 ; i<elements.size(); i++) {
            System.out.println(elements.get(i).html());
        }



        for (int i =0 ; i<elements2.size(); i++) {
            if(elements2.get(i).select("#fixBand53").size() > 0) continue;
            System.out.println(elements2.get(i).html());
        }


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

        return null;
    }

}