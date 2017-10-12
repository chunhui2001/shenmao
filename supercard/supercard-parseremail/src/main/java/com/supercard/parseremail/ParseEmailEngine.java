package com.supercard.parseremail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercard.parseremail.cardparse.*;
import com.supercard.repository.BillRepository;
import com.supercard.tour.BillEntity;
import org.apache.commons.mail.util.MimeMessageParser;

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

public class ParseEmailEngine {

    public static void run() throws Exception {

        String email = "76920104@qq.com";
        String password = "ujwljrfbjdydbigc";

        ParseEmailEngineHelper parseEmailEngineHelper = new ParseEmailEngineHelper(email, password);
        Message message[] = parseEmailEngineHelper.search();

        System.out.println("邮件数量:　" + message.length);

        MimeMessageParser parser = null;
        BillRepository billRepository = new BillRepository(null);

        for (int i = 0; i < message.length; i++) {

            MimeMessage mimeMessage = (MimeMessage) message[i];
            parser = parseEmailEngineHelper.getParser(mimeMessage);
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
                    case "alerts@citibank.com":
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
            billRepository.save(billEntityList);

            try {
                System.out.println( "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "] " + new ObjectMapper().writeValueAsString(billEntityList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            System.out.println("============== *************** ==============");

        }

        parseEmailEngineHelper.release();

        System.out.println("");
        System.out.println("DONE! Parse email bills completed! DONE!");

    }
}
