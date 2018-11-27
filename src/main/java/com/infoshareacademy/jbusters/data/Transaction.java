package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.console.ConsoleReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    PropLoader properties = new PropLoader("app.properties");

    private LocalDate transactionDate;
    private String city;
    private String district;
    private String street;
    private String typeOfMarket;
    private BigDecimal price;
    private String currency;
    private BigDecimal flatArea;
    private BigDecimal pricePerM2;
    private int level;
    private String parkingSpot;
    private String standardLevel;
    private String constructionYear;
    private int constructionYearCategory;

    @Override
    public String toString() {

        BigDecimal exchangeRate = new BigDecimal(properties.getExchangeRate());
        currency = properties.getCurrency();
        price = price.divide(exchangeRate, BigDecimal.ROUND_HALF_UP);
        pricePerM2 = pricePerM2.divide(exchangeRate, BigDecimal.ROUND_HALF_UP);
        typeOfMarket = typeOfMarket.toLowerCase();
        parkingSpot = parkingSpot.toLowerCase();
        standardLevel = standardLevel.toLowerCase();

        return String.format("%-19s%-12s%-7s%-8s%-10s%-27s%-9s%-30s%-14s%-17s%-8s%-12s%-5s%-7s%-8s%-15s%-10s%-5s%-8s%-4s%-15s%-36s%-17s%-30s%-20s%-12s%-16s%-1s",
                "Transaction date:", transactionDate,
                "city:", city,
                "district:", district,
                "street:", street,
                "market type:", typeOfMarket,
                "price:", price, currency,
                "size:", flatArea,
                "price per m2:", pricePerM2, currency,
                "level:", level,
                "parking spot:", parkingSpot,
                "standard level:", standardLevel,
                "construction year:", constructionYear,
                "year category:", constructionYearCategory);
    }

    // ************GETTER AND SETTER***********

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTypeOfMarket() {
        return typeOfMarket;
    }

    public void setTypeOfMarket(String typeOfMarket) {
        this.typeOfMarket = typeOfMarket;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFlatArea() {
        return flatArea;
    }

    public void setFlatArea(BigDecimal flatArea) {
        this.flatArea = flatArea;
    }

    public BigDecimal getPricePerM2() {
        return pricePerM2;
    }

    public void setPricePerM2(BigDecimal pricePerM2) {
        this.pricePerM2 = pricePerM2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getStandardLevel() {
        return standardLevel;
    }

    public void setStandardLevel(String standardLevel) {
        this.standardLevel = standardLevel;
    }

    public String getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(String constructionYear) {
        this.constructionYear = constructionYear;
    }

    public int getConstructionYearCategory() {
        return constructionYearCategory;
    }

    public void setConstructionYearCategory(int constructionYearCategory) {
        this.constructionYearCategory = constructionYearCategory;
    }
}
