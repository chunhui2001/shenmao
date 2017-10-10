package com.supercard.parseremail.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercard.parseremail.cardparse.*;
import com.supercard.repository.BillRepository;
import com.supercard.tour.BillEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SubjectTerm;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public class BillEmailScanJob implements Job {

    public void start() throws Exception {

        String host = "pop.qq.com";
        String email = "76920104@qq.com";
        String password = "ujwljrfbjdydbigc";

        Properties props = new Properties() {{
            setProperty("mail.pop3.host", "pop.qq.com");
            setProperty("mail.pop3.port", "995");
        }};

        // SSL安全连接参数
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "true");
        props.setProperty("mail.pop3.socketFactory.port", "995");

        Session session = Session.getDefaultInstance(props, null);
        Store store = null;
        Folder folder = null;
        // Message message[] = folder.getMessages();
        Message message[] = new Message[0];

        try {

            store = session.getStore("pop3");
            store.connect(host, email, password);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            message = folder.search(new AndTerm(
                    new OrTerm(
                            new FromStringTerm[]{
                                    new FromStringTerm("广发银行"),     // 补发的账单是以PDF附件形式发送的, 新账单无明细，只能通过app查看
//                                    new FromStringTerm("中信银行"),
//                                    new FromStringTerm("交通银行"),
//                                    new FromStringTerm("招商银行"),     // 有的对账单在邮件里面只有到期还款日和还款金额，需要在网页内登陆查看账单详细信息
//                                    new FromStringTerm("浦发银行")      // 邮件内是一个链接地址
                            }),
                    new OrTerm(
                            new SubjectTerm[]{
                                    new SubjectTerm("账单")
                            })));

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {

        }



        System.out.println("邮件数量:　" + message.length);

        MimeMessageParser parser = null;

        for (int i = 0; i < message.length; i++) {

            MimeMessage mimeMessage = (MimeMessage) message[i];
            String from = null;

            try {
                parser = new MimeMessageParser(mimeMessage).parse();
                from = parser.getFrom();
            } catch (Exception e) {
                e.printStackTrace();
            }



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
                System.out.println(e.getMessage());
                System.out.println(parser.getHtmlContent());
                System.out.println(from + " ERROR " + "[" + parser.getSubject() + "]");
            }

            // save
            new BillRepository().save(billEntityList);

            try {
                System.out.println(new ObjectMapper().writeValueAsString(billEntityList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println("============== *************** ==============");

        }

        try {
            folder.close(true);
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        System.out.println("");
        System.out.println("DONE! Parse email bills completed! DONE!");

//        System.out.println("Hello Quartz!" +
//                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            this.start();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}