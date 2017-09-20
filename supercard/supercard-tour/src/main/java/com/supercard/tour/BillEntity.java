package com.supercard.tour;


import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * The BillEntity Pojo
 */
public final class BillEntity {

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

}
