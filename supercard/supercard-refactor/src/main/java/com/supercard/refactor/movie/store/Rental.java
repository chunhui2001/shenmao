package com.supercard.refactor.movie.store;

/**
 * 表示某个顾客租了一部影片
 */
public class Rental {

    private Movie _movie;
    private int _daysRented;

    public Rental(Movie movie, int daysRented) {
        this._movie = movie;
        this._daysRented = daysRented;
    }

    public Movie getMovie() {
        return this._movie;
    }

    public int getDaysRented() {
        return this._daysRented;
    }


    public double getCharge() {

        double result = 0; // 当前金额

        switch (this.getMovie().getPriceCode()) {

            case Movie._REGULAR:
                result += 2;
                if (this.getDaysRented() > 2) result += (this.getDaysRented() - 2) * 1.5;
                break;
            case Movie._CHILDRENS:
                result += 1.5;
                if (this.getDaysRented() > 3) result += (this.getDaysRented() - 3) * 1.5;
                break;
            case Movie._NEW_RELEASE:
                result += this.getDaysRented() * 3;
                break;
        }

        return result;

    }

}
