package com.example.Observer;

public class Klineobject {
    double startPrice;
    double closePrice;
    double maxPrice;
    double minPrice;

    public double getStartPrice() {
        return startPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public Klineobject(double startPrice, double closePrice, double maxPrice, double minPrice) {
        this.startPrice = startPrice;
        this.closePrice = closePrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

}
