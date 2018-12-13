package com.infoshareacademy.jbusters.data;

public class Statistics {

    private String statisticName;
    private int counter;

    @Override
    public String toString() {
        return "Dzielnica " + statisticName + " była użyta " + counter + " raz(y) w wycenie nieruchomości";
    }

    public String getStatisticName() {
        return statisticName;
    }

    public void setStatisticName(String statisticName) {
        this.statisticName = statisticName;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
