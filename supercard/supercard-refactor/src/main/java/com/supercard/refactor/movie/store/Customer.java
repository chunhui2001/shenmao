package com.supercard.refactor.movie.store;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 用于表示租赁影片的客户
 */
public class Customer {

    protected String _name; // 客户名字
    protected Vector _rentals = new Vector(); // 客户的所有租赁条目

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental rental) {
        this._rentals.add(rental);
    }

    public String getName() {
        return this._name;
    }

    /**
     * 此函数需要重构
     * @return
     */
    public String statement() {

        double totalAmount = 0; // 总金额
        int frequentRenterPoints = 0;

        Enumeration rentals = _rentals.elements();

        String result = "Rental Record for " + this._name + ": \n";

        while (rentals.hasMoreElements()) {

            /** statement 做的事太多, 可以运用 Extract Method (提取方法) 重构此部分代码
             * *** Extract Method:
             * [1] 找出函数内的局部变量 each, thisAmount
             * [2] each 没有被修改过, 可以作为一个只读参数传递给新函数
             * [3] thisAmount 会被修改, 在参数传递过程中需格外小心可能会被修改的参数, 如果只有一个参数会被修改，可以把它当作返回值
             */
            double thisAmount = 0; // 当前金额
            Rental each = (Rental)rentals.nextElement();

            switch (each.getMovie().getPriceCode()) {

                case Movie._REGULAR:
                    thisAmount += 2;
                    if (each.getDaysRented() > 2) thisAmount += (each.getDaysRented() - 2) * 1.5;
                    break;
                case Movie._CHILDRENS:
                    thisAmount += 1.5;
                    if (each.getDaysRented() > 3) thisAmount += (each.getDaysRented() - 3) * 1.5;
                    break;
                case Movie._NEW_RELEASE:
                    thisAmount += each.getDaysRented() * 3;
                    break;
            }

            frequentRenterPoints++;

            if (each.getMovie().getPriceCode() == Movie._NEW_RELEASE && each.getDaysRented() > 1) frequentRenterPoints++;

            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";

            totalAmount += thisAmount;

        }

        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "Your earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";

        return result;

    }

}
