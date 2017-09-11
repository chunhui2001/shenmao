package com.supercard.cardparse;

import com.supercard.BillEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by keesh on 11/09/2017.
 */
public class ParseSpdbEmail extends ParseEmailBase {

    private static final String homeDataAction = "https://ebill.spdbccc.com.cn/cloudbank-portal/myBillController/loadHomeData.action";

    public ParseSpdbEmail() {

    }

    public ParseSpdbEmail(String userEmail, String htmlContent) {
        this.useremail = userEmail;
        this.content = htmlContent;
        this.emailHtmlDoc = Jsoup.parse(this.content);
    }

    public ParseSpdbEmail(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {
        super(userEmail, message, parser);
    }

    @Override
    public Collection<BillEntity> parse() {

        Element element = this.emailHtmlDoc.select("a").first();
        String billUrl = escapeContent(element.attr("href")).replaceAll(" ", "");

        System.out.println(billUrl + " billUrl");



        CloseableHttpResponse response1 = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(billUrl);
        String cookieString = null;

        try {

            response1 = httpclient.execute(httpGet);
            cookieString = setCookie(response1);
//            HttpEntity entity1 = response1.getEntity();
//            EntityUtils.consume(entity1);
            response1.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        if (cookieString == null) return null;

        System.out.println(doPost(homeDataAction, cookieString, null));


        return null;
    }


    public static Map<String,String> cookieMap = new HashMap<String, String>(64);

    public static String setCookie(HttpResponse httpResponse)
    {

        Header headers[] = httpResponse.getHeaders("Set-Cookie");

        if (headers == null || headers.length==0) return null;

        String cookie = "";

        for (int i = 0; i < headers.length; i++) {
            cookie += headers[i].getValue();
            if(i != headers.length-1) cookie += ";";
        }

        String cookies[] = cookie.split(";");

        for (String c : cookies)
        {
            c = c.trim();
            if(cookieMap.containsKey(c.split("=")[0])) cookieMap.remove(c.split("=")[0]);
            cookieMap.put(c.split("=")[0], c.split("=").length == 1 ? "":(c.split("=").length ==2?c.split("=")[1]:c.split("=",2)[1]));
        }

        String cookiesTmp = "";

        for (String key :cookieMap.keySet())
        {
            cookiesTmp +=key+"="+cookieMap.get(key)+";";
        }

        return cookiesTmp.substring(0,cookiesTmp.length()-2);
    }

}
