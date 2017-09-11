package com.supercard.cardparse;

import com.supercard.BillEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by keesh on 10/09/2017.
 */
public abstract class ParseEmailBase {

    protected String useremail = null;
    protected String subject = null;
    protected String from = null;
    protected String contentType = null;
    protected MimeMessageParser parser = null;
    protected String content = null;
    protected Document emailHtmlDoc = null;
    protected Elements orderElements = null;
    protected Date receivDate = null;

    public ParseEmailBase() {

    }

    public ParseEmailBase(String userEmail, String htmlContent) {
        this.useremail = userEmail;
        this.content = htmlContent;
        this.emailHtmlDoc = Jsoup.parse(this.content);
    }

    public ParseEmailBase(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {

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

    abstract public Collection<BillEntity> parse();

    protected String escapeContent(String content) {
        return Pattern.compile("(&nbsp;|\\s)+").matcher(content).replaceAll(" ");
    }

    protected static String doPost(String url, String cookie, Object params) {

        String result = null;
        HttpPost homeDataActionPost = new HttpPost(url);

        if (cookie != null) homeDataActionPost.setHeader("Cookie", cookie);

        try {

            CloseableHttpResponse homeDataActionPostResult = HttpClients.createDefault().execute(homeDataActionPost);

            HttpEntity homeDataActionPostEntity = homeDataActionPostResult.getEntity();
            result = EntityUtils.toString(homeDataActionPostEntity);

            EntityUtils.consume(homeDataActionPostEntity);
            homeDataActionPostResult.close();

        } catch (IOException e) {
            return null;
        } finally {

        }

        return result;

    }

}
