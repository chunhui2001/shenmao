package com.supercard.refactor.movie.store;

import java.util.Enumeration;

public class CustomerExtractMethodStatement extends Customer {


    public CustomerExtractMethodStatement(String name) {
        super(name);
    }

    public String statement() {

        double totalAmount = 0; // 总金额
        int frequentRenterPoints = 0;

        Enumeration rentals = _rentals.elements();

        String result = "Rental Record for " + this._name + ": \n";

        while (rentals.hasMoreElements()) {

            // [1] 为新提取的函数选择合适的变量名, 任何傻瓜都可以写出计算机可以理解的代码，唯有写出人类容易理解的代码，才是优秀的程序员
            Rental each = (Rental)rentals.nextElement();
            double thisAmount = amountFor(each); // 当前金额
            frequentRenterPoints++;

            if (each.getMovie().getPriceCode() == Movie._NEW_RELEASE && each.getDaysRented() > 1) frequentRenterPoints++;

            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";

            totalAmount += thisAmount;

        }

        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "Your earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";

        return result;

    }

    private double amountFor(Rental rental) {

        /**
         * *** Move Method:
         * [1] 观察 amountFor() 时我们可以发现该函数使用了来自 Rental 类的信息, 却没有使用来自 Customer 类的信息
         * [2] 此刻需考虑该函数是否放错了位置, 绝大多数情况下函数应该驻留在它所使用的数据的所属对象内, 所以 amountFor() 应该移到 Rental 类中
         * [3] Extract Method 和 Move Method 往往同时发生
         */

        double result = 0; // 当前金额

        switch (rental.getMovie().getPriceCode()) {

            case Movie._REGULAR:
                result += 2;
                if (rental.getDaysRented() > 2) result += (rental.getDaysRented() - 2) * 1.5;
                break;
            case Movie._CHILDRENS:
                result += 1.5;
                if (rental.getDaysRented() > 3) result += (rental.getDaysRented() - 3) * 1.5;
                break;
            case Movie._NEW_RELEASE:
                result += rental.getDaysRented() * 3;
                break;
        }

        return result;

    }

}
