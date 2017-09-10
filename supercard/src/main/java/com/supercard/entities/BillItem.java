package com.supercard.entities;

/**
 * Created by keesh on 09/09/2017.
 */
public class BillItem {

    private String billDate;    // 交易日期
    private String recordDate;  // 记账日期
    private String cardNumber;  // 卡号末四位
    private String currency;    // 币种
    private String money;       // 交易币种/金额
    private String type;        // 还款(repayment)或消费(consumption)
    private String desc;         // 交易说明
    private String transArea;   // 交易地

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTransArea() {
        return transArea;
    }

    public void setTransArea(String transArea) {
        this.transArea = transArea;
    }
}
