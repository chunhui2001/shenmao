package com.supercard.refactor.movie.store;

import java.util.Enumeration;

public class CustomerMoveMethodAmountFor extends Customer {

    public CustomerMoveMethodAmountFor(String name) {
        super(name);
    }

    public String statement() {

        double totalAmount = 0; // 总金额
        int frequentRenterPoints = 0; // 常客积分

        Enumeration rentals = _rentals.elements();

        String result = "Rental Record for " + this._name + ": \n";

        while (rentals.hasMoreElements()) {

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

        return rental.getCharge();

    }

}
