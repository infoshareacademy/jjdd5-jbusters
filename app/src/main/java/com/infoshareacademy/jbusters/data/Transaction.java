package com.infoshareacademy.jbusters.data;


import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@SessionScoped
public class Transaction implements Serializable {

    PropLoader properties = new PropLoader(System.getProperty("jboss.home.dir") + "/data/app.properties");

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
    private String transactionName;
    private boolean important;


    public Transaction() {
    }

    public Transaction(Transaction transaction) {
        this.transactionDate = transaction.transactionDate;
        this.city = transaction.city;
        this.district = transaction.district;
        this.street = transaction.street;
        this.typeOfMarket = transaction.typeOfMarket;
        this.price = transaction.price;
        this.currency = transaction.currency;
        this.flatArea = transaction.flatArea;
        this.pricePerM2 = transaction.pricePerM2;
        this.level = transaction.level;
        this.parkingSpot = transaction.parkingSpot;
        this.standardLevel = transaction.standardLevel;
        this.constructionYear = transaction.constructionYear;
        this.constructionYearCategory = transaction.constructionYearCategory;

    }

    @Override
    public String toString() {

        BigDecimal exchangeRate = new BigDecimal(properties.getExchangeRate());
        currency = properties.getCurrency();
        price = price.divide(exchangeRate, BigDecimal.ROUND_HALF_UP);
        pricePerM2 = pricePerM2.divide(exchangeRate, BigDecimal.ROUND_HALF_UP);
        typeOfMarket = typeOfMarket.toLowerCase();
        parkingSpot = parkingSpot.toLowerCase();
        standardLevel = standardLevel.toLowerCase();

        return
                "Data transakcji:\t" + transactionDate + "\n" +
                        "Miasto:\t\t\t" + city + "\n" +
                        "Dzielnica:\t\t" + district + "\n" +
                        "Ulica:\t\t\t" + street + "\n" +
                        "Rodzaj rynku:\t\t" + typeOfMarket + "\n" +
                        "Cena:\t\t\t" + price + " " + currency + "\n" +
                        "Wielkość:\t\t" + flatArea + " m2\n" +
                        "Cena za m2:\t\t" + pricePerM2 + " " + currency + "\n" +
                        "Piętro:\t\t\t" + level + "\n" +
                        "Miejsce postojowe:\t" + parkingSpot + "\n" +
                        "Standard wykończenia:\t" + standardLevel + "\n" +
                        "Rok budowy:\t\t" + constructionYear + "\n" +
                        "ważna:\t\t" + important + "\n" +
                        "Nazwa transakcji:\t" + transactionName + "\n" +
                        "Kategoria roku budowy:\t" + constructionYearCategory;
    }

    public String toStringNoPrice() {

        BigDecimal exchangeRate = new BigDecimal(properties.getExchangeRate());
        currency = properties.getCurrency();
        price = price.divide(exchangeRate, BigDecimal.ROUND_HALF_UP);
        pricePerM2 = pricePerM2.divide(exchangeRate, BigDecimal.ROUND_HALF_UP);
        typeOfMarket = typeOfMarket.toLowerCase();
        parkingSpot = parkingSpot.toLowerCase();
        standardLevel = standardLevel.toLowerCase();

        return

                "Nazwa transakcji:\t" + transactionName + "\n" +
                        "Miasto:\t\t\t" + city + "\n" +
                        "Dzielnica:\t\t" + district + "\n" +
                        "Ulica:\t\t\t" + street + "\n" +
                        "Rodzaj rynku:\t\t" + typeOfMarket + "\n" +
                        "Wielkość:\t\t" + flatArea + " m2\n" +
                        "Piętro:\t\t\t" + level + "\n" +
                        "Miejsce postojowe:\t" + parkingSpot + "\n" +
                        "Standard wykończenia:\t" + standardLevel + "\n" +
                        "Ważna:\t" + important + "\n" +
                        "Kategoria roku budowy:\t" + getConstructionYearCategoryString(getConstructionYearCategory());
    }

    private String getConstructionYearCategoryString(int constructionYearCategory) {
        if (constructionYearCategory == 1) {
            return "przed rokiem 1970";
        } else if (constructionYearCategory == 2) {
            return "między rokiem 1970 a 1990";
        } else if (constructionYearCategory == 3) {
            return "po roku 1990";
        } else return "nie podano";
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

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionName() {
        return transactionName;
    }
}
