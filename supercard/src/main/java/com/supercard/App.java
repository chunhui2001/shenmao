package com.supercard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercard.cardparse.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.search.*;

/**
 * Hello world!
 *
 */
public class App 
{

    public static String readFile(String filePath)  {

        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            return IOUtils.toString(inputStream, "utf-8");
        } catch (IOException e) {
            return null;
        }

    }

    public static void main( String[] args ) throws Exception {


        String host = "pop.qq.com";
        String email = "76920104@qq.com";
        String password = "ujwljrfbjdydbigc";

        if (1==2) {

//            ParsePCCCEmail parsePCCCEmail = new ParsePCCCEmail(email, readFile("/Users/keesh/Desktop/demo.html"));
//            Collection<BillEntity> billEntityList = parsePCCCEmail.parse();
//            System.out.println(new ObjectMapper().writeValueAsString(billEntityList));

//
//            ParseCmbEmail parsePmbEmail = new ParseCmbEmail(email, readFile("/Users/keesh/Desktop/demo.html"));
//            Collection<BillEntity> billEntityList = parsePmbEmail.parse();
//            System.out.println(new ObjectMapper().writeValueAsString(billEntityList));

//            ParseCiticEmail parseCiticEmail = new ParseCiticEmail(email, readFile("/Users/keesh/Desktop/demo.html"));
//            Collection<BillEntity> billEntityList = parseCiticEmail.parse();
//            System.out.println(new ObjectMapper().writeValueAsString(billEntityList));


//            ParseSpdbEmail parseSpdbEmail = new ParseSpdbEmail(email, readFile("/Users/keesh/Desktop/demo.html"));
//            Collection<BillEntity> billEntityList = parseSpdbEmail.parse();
//            System.out.println(new ObjectMapper().writeValueAsString(billEntityList));

//            new ParseCitiBankEmail(email, null).parse();

            new ParseCgbEmail(email, null).parse();

            return;
        }

        Properties props = new Properties() {{
            setProperty("mail.pop3.host", "pop.qq.com");
            setProperty("mail.pop3.port", "995");
        }};

        // SSL安全连接参数
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "true");
        props.setProperty("mail.pop3.socketFactory.port", "995");

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("pop3");
        store.connect(host, email, password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        // Message message[] = folder.getMessages();
        Message message[] = folder.search(new AndTerm(
                new OrTerm(
                    new FromStringTerm[]{
                        new FromStringTerm("广发银行"),     // 补发的账单是以PDF附件形式发送的
//                        new FromStringTerm("中信银行"),
//                        new FromStringTerm("交通银行"),
//                        new FromStringTerm("招商银行"),           // 有的对账单在邮件里面只有到期还款日和还款金额，需要在网页内登陆查看账单详细信息
//                        new FromStringTerm("浦发银行")  // 邮件内是一个链接地址
                }),
                new OrTerm(
                    new SubjectTerm[]{
                        new SubjectTerm("账单")
                })));

        System.out.println("邮件数量:　" + message.length);

        MimeMessageParser parser = null;

        for (int i = 0; i < message.length; i++) {

            MimeMessage mimeMessage = (MimeMessage) message[i];
            parser = new MimeMessageParser(mimeMessage).parse();

            String from = parser.getFrom();

            Collection<BillEntity> billEntityList = null;

            System.out.println("");

            try {

                switch (from) {
                    case "PCCC@bocomcc.com":
                        System.out.println("============== " + from + " [交通 FOCUS][" + (i + 1) + "] ==============");
                        billEntityList = new ParsePCCCEmail(email, mimeMessage, parser).parse();
                        break;
                    case "citiccard@citiccard.com":
                        System.out.println("============== " + from + " [中信 FOCUS][" + (i + 1) + "] ==============");
                        billEntityList = new ParseCiticEmail(email, mimeMessage, parser).parse();
                        break;
                    case "estmtservice@eb.spdbccc.com.cn":
                        System.out.println("============== " + from + " [浦发 FOCUS][" + (i + 1) + "] ==============");
                        billEntityList = new ParseSpdbEmail(email, mimeMessage, parser).parse();
                        break;
                    case "creditcard@cgbchina.com.cn":
                        System.out.println("============== " + from + " [广发 FOCUS][" + (i + 1) + "] ==============");
                        billEntityList = new ParseCgbEmail(email, mimeMessage, parser).parse();
                        break;
                    case "ccsvc@message.cmbchina.com":
                        System.out.println("============== " + from + " [招商 FOCUS][" + (i + 1) + "] ==============");
                        billEntityList = new ParseCmbEmail(email, mimeMessage, parser).parse();
                        break;
                    case "citibank@emailmgmt.china.citibank.com":
                        System.out.println("============== " + from + " [花旗 FOCUS][" + (i + 1) + "] ==============");
                        billEntityList = new ParseCitiBankEmail(email, mimeMessage, parser).parse();
                        break;
                    default:
                        System.out.println(from + " [IGNORE][" + (i + 1) + "]");
                        continue;

                }

            } catch (Exception e) {
                    System.out.print(parser.getHtmlContent());
                System.out.print(from + "ERROR");
            }

            System.out.println(new ObjectMapper().writeValueAsString(billEntityList));
            System.out.println("============== *************** ==============");

        }

        folder.close(true);
        store.close();

        System.out.println("");
        System.out.println("DONE! Parse email bills completed! DONE!");

    }
}
