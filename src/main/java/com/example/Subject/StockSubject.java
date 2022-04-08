package com.example.Subject;

import com.example.Observer.Observer;

import java.util.ArrayList;

public class StockSubject implements Subject {

    double[][] prices;
    String type;
    ArrayList<Observer> observerList;

    public StockSubject() {
        observerList = new ArrayList<>();
        prices = null;
    }

    @Override
    public void addObserver(Observer o) {
        if (!(observerList.contains(o))) {
            observerList.add(o);
        }
    }

    @Override
    public void deleteObserver(Observer o) {//TODO Auto-generated method stub
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.drawFig();
        }
    }

    public void setPriceAndType(double[][] prices, String type) {
        this.prices = prices;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public double[][] getPrices() {
        return this.prices;
    }
}
