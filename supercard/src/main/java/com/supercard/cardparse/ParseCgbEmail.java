package com.supercard.cardparse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercard.BillEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by keesh on 17/09/2017.
 */
public class ParseCgbEmail extends ParseEmailBase {

    public ParseCgbEmail() {

    }

    public ParseCgbEmail(String userEmail, String htmlContent) {
        super(userEmail, htmlContent);
    }

    public ParseCgbEmail(String userEmail, MimeMessage message, MimeMessageParser parser) throws Exception {
        super(userEmail, message, parser);
    }

    private File getAttachBill() {

        List<DataSource> emailAttachment = this.parser.getAttachmentList();

        for (DataSource atta : emailAttachment) {

            if (!atta.getName().equals("综合对账单打印版.pdf")) continue;

            InputStream ins = null;

            try {
                ins = atta.getInputStream();
                return saveFile(System.getProperty("java.io.tmpdir"),
                        atta.getName().split("\\.")[0] + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(this.receivDate) + "." + atta.getName().split("\\.")[1], ins);
            } catch (IOException e) {

            }


        }

        return null;
    }

    public String getPdfBillContent(File pdFile) {
        File attacheBill = pdFile;

        if (attacheBill == null || !attacheBill.exists()) return null;

        String _content = null;
        PDDocument doc = null;

        try {
            doc = PDDocument.load(attacheBill);
            _content = new PDFTextStripper().getText(doc);
        } catch (IOException e) {
            return null;
        } finally {
            if (doc != null) try {
                doc.close();
            } catch (IOException e) {

            }
        }

        return _content;
    }

    @Override
    public Collection<BillEntity> parse() throws IOException {

//        File attacheBill = getAttachBill();

        File attacheBill = attacheBill = new File("/Users/keesh/Downloads/综合对账单打印版_20170910014535.pdf");

        String _content = getPdfBillContent(attacheBill);

        System.out.println(_content);

        Pattern custNameAndAddressPattern = Pattern.compile("积分按卡号汇总情况\\s{1,}([\\+ ＝\\－]{4}?)\\s{1,}([\\W\\w]+?)\\s{1,}([\\W\\w]+?)\\s{1,}([\\W\\w]+?)\\s{1,}([\\W\\w]+?)\\s{1,}([\\d]{25})\\s{1,}/");

        Matcher custNameAndAddressMatcher = custNameAndAddressPattern.matcher(_content);

        BillEntity bill = new BillEntity();

        if (custNameAndAddressMatcher.find()) {
            bill.setCustomerName(custNameAndAddressMatcher.group(2));
        }

        System.out.println(new ObjectMapper().writeValueAsString(bill));


        return new ArrayList<BillEntity>() {{ add(bill); }};
    }
}
