package com.infoshareacademy.jbusters.data;

public class Statistics {

    private String cityName;
    private String districtName;
    private int counter;
    private double averageValue;

    @Override
    public String toString() {
        return "Miasto " + cityName + " było użyte " + counter + " raz(y) w wycenie nieruchomości.\n" +
                "Dzielnica " + districtName + " była użyta " + counter + " raz(y) w wycenie nieruchomości.\n" +
                "Średnia cena nieruchomości w tym rejonie wynosi " + averageValue + " PLN";
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(double averageValue) {
        this.averageValue = averageValue;
    }
}
