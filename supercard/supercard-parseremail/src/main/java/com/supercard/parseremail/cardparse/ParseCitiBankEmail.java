package com.supercard.parseremail.cardparse;

import com.supercard.tour.BillEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.nodes.Document;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by keesh on 16/09/2017.
 */
public class ParseCitiBankEmail extends ParseEmailBase  {


    public ParseCitiBankEmail() {

    }


    public ParseCitiBankEmail(String userEmail, String htmlContent) {
        super(userEmail, htmlContent);
    }

    public ParseCitiBankEmail(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {
        super(userEmail, message, parser);
    }


    @Override
    public Collection<BillEntity> parse() {

        /*
        String loginPage = "https://www.citibank.com.cn/CNGCB/JSO/signon/DisplayUsernameSignon.do";

        String loginPageResult = doGet(loginPage, "", null);

        System.out.println(loginPageResult);

        Pattern tokenPattern = Pattern.compile("<a\\s{1,}ID=\"link_lkSignOn\"\\s{1,}class=\"appNavNext\"\\s{1,}href=\"/CNGCB/JSO/signon/ProcessUsernameSignon\\.do\\?JFP_TOKEN=([A-Z0-9]+)&SYNC_TOKEN=([a-zA-Z0-9]+)\"\\s{1,}onClick=\"\"\\s{1,}target=\"_top\"\\s{1,}title=\"登录\"\\s{0,}>登录<\\/a>");

        Matcher tokenMatcher = tokenPattern.matcher(loginPageResult);

        String jfpToken = null;
        String syncToken = null;
        String username = "chunhui2001@gmail.com";
        String password = "Keesh1";

        Map<String, String> params = new HashMap<>();

        if (!tokenMatcher.find()) return null;

        params.put("JFP_TOKEN", tokenMatcher.group(1));
        params.put("SYNC_TOKEN", tokenMatcher.group(2));
        params.put("username", username);
        params.put("password", password);

        String cookie = null;

        String loginPostResult = doPost("https://www.citibank.com.cn/CNGCB/JSO/signon/ProcessUsernameSignon.do", cookie, params);

        String loginAction = "https://www.citibank.com.cn/CNGCB/JSO/signon/ProcessUsernameSignon.do";
        */

        if (!this.subject.equals("花旗银行信用卡账单提醒")) return null;

        Pattern cardNumberPattern = Pattern.compile("您尾号([\\d]{4})");//的花旗银行信用卡20/09/2017账单应还款CNY1.80，最低还款额CNY0.09，到期还款日10/10/2017";
        Pattern billMoneyPattern = Pattern.compile("([\\d]{2}/[\\d]{2}/[\\d]{4})账单应还款(([A-Z]{3,5})([\\d,.]{1,}))");//最低还款额CNY0.09，到期还款日10/10/2017";
        Pattern billMoneyMinPattern = Pattern.compile("最低还款额(([A-Z]{3,5})([\\d,.]{1,}))");
        Pattern billExpiredPattern = Pattern.compile("到期还款日([\\d]{2}/[\\d]{2}/[\\d]{4})");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(this.content);
        Matcher billMoneyMatcher = billMoneyPattern.matcher(this.content);
        Matcher billMoneyMinMatcher = billMoneyMinPattern.matcher(this.content);
        Matcher billExpiredMatcher = billExpiredPattern.matcher(this.content);

        BillEntity billEntity = new BillEntity();

        if (cardNumberMatcher.find()) {
            billEntity.setCardNumber(cardNumberMatcher.group(1));
        }

        if (billMoneyMatcher.find()) {
            billEntity.setBillMonth(billMoneyMatcher.group(1));
            billEntity.setCurrency(billMoneyMatcher.group(3));
            billEntity.setBillMoney(billMoneyMatcher.group(2));
        }

        if (billMoneyMinMatcher.find()) {
            billEntity.setBillMoneyMin(billMoneyMinMatcher.group(1));
        }

        if (billExpiredMatcher.find()) {
            billEntity.setBillExpired(billExpiredMatcher.group(1));
        }


        billEntity.setBank("花旗");
        billEntity.setUserIdentity(useremail);
        billEntity.setReceivDate(this.getReceivDate());
        billEntity.setSubject(this.subject);
        billEntity.setBillContent(this.content);
        billEntity.setContentType("TEXT");

        return new ArrayList<BillEntity>() {{ add(billEntity); }};
    }
}
