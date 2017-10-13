package com.supercard.refactor.movie.store;

/**
 * The movie Pojo
 * 纯数据类
 */
public class Movie {

    public static final int _CHILDRENS = 2;
    public static final int _REGULAR = 0;
    public static final int _NEW_RELEASE = 1;

    private String _title; // 影片名称
    private int _priceCode; // 影片类型

    public Movie(String title, int priceCode) {
        this._title = title;
        this._priceCode = priceCode;
    }

    public int getPriceCode() {
        return this._priceCode;
    }

    public void setPriceCode(int priceCode) {
        this._priceCode = priceCode;
    }

    public String getTitle() {
        return this._title;
    }

}
