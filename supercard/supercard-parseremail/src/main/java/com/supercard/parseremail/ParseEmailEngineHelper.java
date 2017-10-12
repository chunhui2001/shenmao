package com.supercard.parseremail;

import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SubjectTerm;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ParseEmailEngineHelper {

    private static final String _FOLDER_NAME = "INBOX";

    private static Properties getQqProps() {

        Properties props = new Properties() {{
            setProperty("mail.pop3.host", "pop.qq.com");
            setProperty("mail.pop3.port", "995");
        }};

        // SSL安全连接参数
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "true");
        props.setProperty("mail.pop3.socketFactory.port", "995");

        return props;
    }

    private static Session getSession(Properties properties) {
        return Session.getDefaultInstance(properties, null);
    }

    private String _host = "pop.qq.com";
    private String _email = null;
    private String _password = null;
    private Properties _props = null;
    private Store _store = null;
    private Folder _folder = null;

    public ParseEmailEngineHelper(String email, String password) {
        this._email = email;
        this._password = password;
        this._props = getQqProps();
    }

    private void connect() {
        try {
            _store = getSession(this._props).getStore("pop3");
            _store.connect(_host, _email, _password);
        } catch (MessagingException e) {

        }
    }


    private void getFolder() {

        if (1==1) {
            try {
                _folder = _store.getFolder(_FOLDER_NAME);
                _folder.open(Folder.READ_ONLY);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return ;
        }

        Callable<Folder> callable = new Callable<Folder>() {
            public Folder call() throws Exception {
                return _store.getFolder(_FOLDER_NAME);
            }
        };


        FutureTask<Folder> future = new FutureTask<Folder>(callable);
        new Thread(future).start();

//        future.run();

        try {

            if (future.isDone()) {
                System.out.println(future.get());
            }

        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }

    }

    public Message[] search() {

        this.connect();
        this.getFolder();

        Message message[] = new Message[0];

        try {


            message = _folder.search(new AndTerm(
                    new OrTerm(
                            new FromStringTerm[]{

                                    new FromStringTerm("alerts"),           // 花旗
//                                    new FromStringTerm("广发银行"),     // 补发的账单是以PDF附件形式发送的, 新账单无明细，只能通过app查看
//                                    new FromStringTerm("中信银行"),
//                                    new FromStringTerm("交通银行"),
//                                    new FromStringTerm("招商银行"),     // 有的对账单在邮件里面只有到期还款日和还款金额，需要在网页内登陆查看账单详细信息
//                                    new FromStringTerm("浦发银行")      // 邮件内是一个链接地址
                            }),
                    new OrTerm(
                            new SubjectTerm[]{
                                    new SubjectTerm("账单"),
                                    new SubjectTerm("花旗银行信用卡账单提醒")
                            })));

        } catch (NoSuchProviderException e) {

        } catch (MessagingException e) {

        }

        return message;
    }

    public MimeMessageParser getParser(MimeMessage mimeMessage) {
        try {
            if (!this._folder.isOpen()) {
                this._folder.open(Folder.READ_ONLY);
                System.out.println("The folder not open, opened again: " + this._folder.isOpen());
            }
            return new MimeMessageParser(mimeMessage).parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void release() {
        try {
            if (this._folder != null) this._folder.close(true);
            if (this._store != null) this._store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
