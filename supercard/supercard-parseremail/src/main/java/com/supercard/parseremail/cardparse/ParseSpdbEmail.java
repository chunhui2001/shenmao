package com.supercard.parseremail.cardparse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercard.tour.BillEntity;
import com.supercard.tour.BillItemEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

/**
 * Created by keesh on 11/09/2017.
 */
public class ParseSpdbEmail extends ParseEmailBase {

    private static final String homeDataAction = "https://ebill.spdbccc.com.cn/cloudbank-portal/myBillController/loadHomeData.action";

    private String cookieString = null;

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

    public String getBillJson() {

        Element element = this.emailHtmlDoc.select("a").first();
        String billUrl = escapeContent(element.attr("href")).replaceAll(" ", "");

        this.cookieString = doGet(billUrl, null, null);

        if (this.cookieString == null) return null;

        return doPost(homeDataAction, this.cookieString, null);

    }

    @Override
    public Collection<BillEntity> parse() {

        String billJsonString = getBillJson();

        Map<String,String> billMap = null;

        try {
            billMap = new ObjectMapper().readValue(billJsonString, new TypeReference<HashMap<String,String>>(){});
        } catch (IOException e) {
            return null;
        }

        if (billMap == null) return null;

        BillEntity billEntity = new BillEntity();

        billEntity.setUserIdentity(this.useremail);
        billEntity.setBillMoney(billMap.get("cash"));
        billEntity.setBillMoneyMin(billMap.get("minPay"));
        billEntity.setBillExpired(billMap.get("lastBackDate"));
        billEntity.setBillMonth(billMap.get("billYM"));
        billEntity.setCustomerName(billMap.get("userName").split(" ")[0]);
        billEntity.setGender(billMap.get("userName").split(" ")[1]);
        billEntity.setCardNumber(billMap.get("cardNo"));


        billEntity.setBillItems(parseBillItems());

        return new ArrayList<BillEntity>() {{ add(billEntity); }};

    }

    private Collection<BillItemEntity> parseBillItems() {

        String billItemAction = "https://ebill.spdbccc.com.cn/cloudbank-portal/billDetailController/PCloadBillsDetailAll.action";

        String billItemResultString = doPost(billItemAction, this.cookieString, null);

        if (billItemResultString == null) return null;

        Map<String, Object> result = new HashMap<>();

        try {
            result = new ObjectMapper().readValue(billItemResultString, new TypeReference<HashMap<String,Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<LinkedHashMap<String, String>> itemList =  (ArrayList<LinkedHashMap<String, String>>)result.get("list");
        Collection<BillItemEntity> itemListEntity = new ArrayList<>();
        BillItemEntity billItemEntity = null;


        for (int i =0; i<itemList.size(); i++) {

            billItemEntity = new BillItemEntity();

            // amountFL: D consumption, C repayment

            billItemEntity.setTransMoney(itemList.get(i).get("amount"));
            billItemEntity.setTransDesc(itemList.get(i).get("desLine1") + " " + itemList.get(i).get("desLine2"));
            billItemEntity.setTransCurrency("RMB");
            billItemEntity.setTransCardNumber(itemList.get(i).get("cardNbr1"));
            billItemEntity.setTransDate(itemList.get(i).get("purDate8"));
            billItemEntity.setRecordDate(null);
            billItemEntity.setTransArea(null);

            if (itemList.get(i).get("amountFl").endsWith("D")) {
                billItemEntity.setTransType("consumption");
            } else if (itemList.get(i).get("amountFl").endsWith("C")) {
                billItemEntity.setTransType("repayment");
            } else {
                billItemEntity.setTransType(itemList.get(i).get("amountFl"));
            }

            itemListEntity.add(billItemEntity);

        }


        return itemListEntity;

    }




}
