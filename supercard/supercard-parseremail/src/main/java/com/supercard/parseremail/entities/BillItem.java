package com.supercard.parseremail.entities;

/**
 * Created by keesh on 09/09/2017.
 */
public class BillItem {

    private String transDate;    // 交易日期
    private String recordDate;  // 记账日期
    private String transCardNumber;  // 卡号末四位
    private String transCurrency;    // 币种
    private String transMoney;       // 交易币种/金额
    private String transType;        // 还款(repayment)或消费(consumption)
    private String transDesc;         // 交易说明
    private String transArea;   // 交易地

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getTransCardNumber() {
        return transCardNumber;
    }

    public void setTransCardNumber(String transCardNumber) {
        this.transCardNumber = transCardNumber;
    }

    public String getTransCurrency() {
        return transCurrency;
    }

    public void setTransCurrency(String transCurrency) {
        this.transCurrency = transCurrency;
    }

    public String getTransMoney() {
        return transMoney;
    }

    public void setTransMoney(String transMoney) {
        this.transMoney = transMoney;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransDesc() {
        return transDesc;
    }

    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    public String getTransArea() {
        return transArea;
    }

    public void setTransArea(String transArea) {
        this.transArea = transArea;
    }
}
