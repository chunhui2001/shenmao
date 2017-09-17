package com.supercard.cardparse;

import com.supercard.BillEntity;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by keesh on 10/09/2017.
 */
public abstract class ParseEmailBase {

    private static final String _BROWSER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";

    protected static final Pattern _DATE_PATTERN_DASH = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");

    protected String useremail = null;
    protected String subject = null;
    protected String from = null;
    protected String contentType = null;
    protected MimeMessageParser parser = null;
    protected String content = null;
    protected Document emailHtmlDoc = null;
    protected Elements orderElements = null;
    protected Date receivDate = null;
    protected MimeMessage mimeMessage = null;

    public ParseEmailBase() {

    }

    public ParseEmailBase(String userEmail, String htmlContent) {
        this.useremail = userEmail;
        this.content = htmlContent == null ? null : htmlContent;
        this.emailHtmlDoc = htmlContent == null ? null : Jsoup.parse(htmlContent);
    }

    public ParseEmailBase(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {

        this.mimeMessage = message;
        this.parser = parser;
        this.useremail = userEmail;
        this.from = parser.getFrom();
        this.subject = parser.getSubject();
        this.contentType = message.getContentType();
        this.content = parser.getHtmlContent();
        this.emailHtmlDoc = Jsoup.parse(content);
//        this.receivDate = message.getReceivedDate();

        this.receivDate = message.getSentDate();

    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public MimeMessageParser getParser() {
        return parser;
    }

    public void setParser(MimeMessageParser parser) {
        this.parser = parser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Document getEmailHtmlDoc() {
        return emailHtmlDoc;
    }

    public void setEmailHtmlDoc(Document emailHtmlDoc) {
        this.emailHtmlDoc = emailHtmlDoc;
    }

    public Elements getOrderElements() {
        return orderElements;
    }

    public void setOrderElements(Elements orderElements) {
        this.orderElements = orderElements;
    }

    public Date getReceivDate() {
        return receivDate;
    }

    public void setReceivDate(Date receivDate) {
        this.receivDate = receivDate;
    }

    abstract public Collection<BillEntity> parse() throws IOException;

    protected String escapeContent(String content) {
        return Pattern.compile("(&nbsp;|\\s)+").matcher(content).replaceAll(" ");
    }


    public static String getCookie(HttpResponse httpResponse)
    {


        Map<String,String> cookieMap = new HashMap<String, String>(64);

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

    protected static String doGet(String url, String cookie, String queryString) {

        CloseableHttpResponse response1 = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        String result = null;
        if (cookie != null) httpGet.setHeader("Cookie", cookie);

        httpGet.setHeader("User-Agent", _BROWSER_AGENT);

        try {

            response1 = httpclient.execute(httpGet);
            if (cookie == null) return getCookie(response1);

            HttpEntity entity1 = response1.getEntity();

            result = IOUtils.toString(entity1.getContent(), StandardCharsets.UTF_8);

            EntityUtils.consume(entity1);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (response1 != null) try {
                response1.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }


        return result;

    }

    protected static String doPost(String url, String cookie, Object params) {

        String result = null;
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(url);

        if (cookie != null) httpPost.setHeader("Cookie", cookie);

        httpPost.setHeader("User-Agent", _BROWSER_AGENT);


        List<NameValuePair> paramsValuePair = new ArrayList<>();

        for (Map.Entry<String, String> p : ((Map<String, String>)params).entrySet()) {
            paramsValuePair.add(new BasicNameValuePair(p.getKey(), p.getValue()));
        }

        if (paramsValuePair.size() > 0) {
            httpPost.setEntity(new UrlEncodedFormEntity(paramsValuePair, Consts.UTF_8));
            httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
        }


        try {

            httpResponse = HttpClients.createDefault().execute(httpPost);
            HttpEntity homeDataActionPostEntity = httpResponse.getEntity();
//            result = EntityUtils.toString(homeDataActionPostEntity);
            result = IOUtils.toString(homeDataActionPostEntity.getContent(), StandardCharsets.UTF_8);
            EntityUtils.consume(homeDataActionPostEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(httpResponse != null){
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }



    protected static File saveFile(String dir, String fileName, InputStream input) {


        File savefile = new File(dir, fileName);

        BufferedOutputStream bos = null;
        try {

            bos = new BufferedOutputStream(
                    new FileOutputStream(savefile));
            byte[] buff = new byte[2048];
            InputStream is = input;
            int ret = 0;

            try {

                while ((ret = is.read(buff)) > 0) {
                    bos.write(buff, 0, ret);
                }

                bos.close();
                is.close();

            } catch (IOException e) {
                savefile = null;
            }

        } catch (FileNotFoundException e) {
            savefile = null;
        }



        return savefile;
    }


}
