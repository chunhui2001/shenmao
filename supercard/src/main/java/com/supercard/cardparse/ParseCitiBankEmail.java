package com.supercard.cardparse;

import com.supercard.BillEntity;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.internet.MimeMessage;
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

        String loginPage = "https://www.citibank.com.cn/CNGCB/JSO/signon/DisplayUsernameSignon.do";

        String loginPageResult = doGet(loginPage, "", null);

        System.out.println(loginPageResult);

        // <a ID="link_lkSignOn" class="appNavNext" href="/CNGCB/JSO/signon/ProcessUsernameSignon.do?JFP_TOKEN=2JYQIUML&SYNC_TOKEN=56bb5ba78d67f03730b950a11d71d675" onClick="" target="_top" title="登录" >登录</a>
//        Pattern tokenPattern = Pattern.compile("<a ID=\"link_lkSignOn\" class=\"appNavNext\" href=\"\\/CNGCB\\/JSO\\/signon\\/ProcessUsernameSignon.do?JFP_TOKEN=([A-Z0-9]+)&SYNC_TOKEN=([a-zA-Z0-9]+)\" onClick=\"\" target=\"_top\" title=\"登录\" >登录<\\/a>");
        Pattern tokenPattern = Pattern.compile("<a\\s{1,}ID=\"link_lkSignOn\"\\s{1,}class=\"appNavNext\"\\s{1,}href=\"/CNGCB/JSO/signon/ProcessUsernameSignon\\.do\\?JFP_TOKEN=([A-Z0-9]+)&SYNC_TOKEN=([a-zA-Z0-9]+)\"\\s{1,}onClick=\"\"\\s{1,}target=\"_top\"\\s{1,}title=\"登录\"\\s{0,}>登录<\\/a>");
//        Pattern tokenPattern = Pattern.compile(" href=\"([\\W\\w]+?)\" ");

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



        return null;
    }
}
