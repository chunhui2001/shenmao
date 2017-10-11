package com.supercard.tour;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * The BillEntity Pojo
 */
public final class BillEntity {

//    @JsonSerialize(using = ToStringSerializer.class)
    @JsonSerialize(using = ObjectIdJsonSerializer.class)
    private ObjectId id;

    private String userIdentity;
    private String customerName;
    private String billMonth;
    private String cardNumber;
    private String billBoundMin;
    private String billBoundMax;
    private String billExpired;
    private String billMoney;
    private String billMoneyMin;
    private String gender;
    private String bank;
    private String currency;
    private String subject;
    private String billContent;
    private String contentType;     // html, pdf,


    public ObjectId getId() {
        return id;
    }
    public void setId(final ObjectId id) {
        this.id = id;
    }


    public Date getReceivDate() {
        return receivDate;
    }

    public void setReceivDate(Date receivDate) {
        this.receivDate = receivDate;
    }

    protected Date receivDate = null;

    private Collection<BillItemEntity> billItems;

    public BillEntity() {
        this.id = new ObjectId();
        billItems = new ArrayList<>();
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBillBoundMin() {
        return billBoundMin;
    }

    public void setBillBoundMin(String billBoundMin) {
        this.billBoundMin = billBoundMin;
    }

    public String getBillBoundMax() {
        return billBoundMax;
    }

    public void setBillBoundMax(String billBoundMax) {
        this.billBoundMax = billBoundMax;
    }

    public String getBillExpired() {
        return billExpired;
    }

    public void setBillExpired(String billExpired) {
        this.billExpired = billExpired;
    }

    public String getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public String getBillMoneyMin() {
        return billMoneyMin;
    }

    public void setBillMoneyMin(String billMoneyMin) {
        this.billMoneyMin = billMoneyMin;
    }

    public Collection<BillItemEntity> getBillItems() {
        return billItems;
    }

    public void setBillItems(Collection<BillItemEntity> billItems) {
        this.billItems = billItems;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }

    public String getBillContent() {
        return this.billContent;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

}
