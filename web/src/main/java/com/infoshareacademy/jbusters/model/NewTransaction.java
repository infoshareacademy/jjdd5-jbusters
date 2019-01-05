package com.infoshareacademy.jbusters.model;

import javax.persistence.*;

@Entity
@Table(name = "new_transaction")
public class NewTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NEW_TRANSACTION_ID")
    private int newTransactionId;

    @ManyToOne
    @JoinColumn(name = "NEW_TRANSACTION_USER_ID")
    private User newTransactionUserId;

    @Column(name = "NEW_TRANSACTION_DESCRIPTION")
    private String newTransactionDescription;

    @Column(name = "NEW_TRANSACTION_SALE")
    private String newTransactionSale;

    @Column(name = "NEW_TRANSACTION_IMPORTANT")
    private String newTransactionImportant;

    @Column(name = "NEW_TRANSACTION_DATA_TRANSACTION")
    private String newTransactionDataTransaction;

    @Column(name = "NEW_TRANSACTION_CITY")
    private String newTransactionCity;

    @Column(name = "NEW_TRANSACTION_DISTRICT")
    private String newTransactionDistrict;

    @Column(name = "NEW_TRANSACTION_STREET")
    private String newTransactionStreet;

    @Column(name = "NEW_TRANSACTION_TYPE_OF_MARKET")
    private String newTransactionTypeOfMarket;

    @Column(name = "NEW_TRANSACTION_PRICE")
    private String newTransactionPrice;

    @Column(name = "NEW_TRANSACTION_FLAT_AREA")
    private String newTransactionFlatArea;

    @Column(name = "NEW_TRANSACTION_PRICE_PER_M2")
    private String newTransactionPricePerM2;

    @Column(name = "NEW_TRANSACTION_LEVEL")
    private String newTransactionLevel;

    @Column(name = "NEW_TRANSACTION_PARKING_SPOT")
    private String newTransactionParkingSpot;

    @Column(name = "NEW_TRANSACTION_STANDARD_LEVEL")
    private String newTransactionStandardLevel;

    @Column(name = "NEW_TRANSACTION_CONSTRUCTION_YEAR")
    private String newTransactionConstructionYear;

    @Column(name = "NEW_TRANSACTION_CONSTRUCTION_YEAR_CATEGORY")
    private String newTransactionConstructionYearCategory;

    public NewTransaction() {
    }

    public NewTransaction(User newTransactionUserId, String newTransactionDescription, String newTransactionSale, String newTransactionImportant, String newTransactionDataTransaction, String newTransactionCity, String newTransactionDistrict, String newTransactionStreet, String newTransactionTypeOfMarket, String newTransactionPrice, String newTransactionFlatArea, String newTransactionPricePerM2, String newTransactionLevel, String newTransactionParkingSpot, String newTransactionStandardLevel, String newTransactionConstructionYear, String newTransactionConstructionYearCategory) {
        this.newTransactionUserId = newTransactionUserId;
        this.newTransactionDescription = newTransactionDescription;
        this.newTransactionSale = newTransactionSale;
        this.newTransactionImportant = newTransactionImportant;
        this.newTransactionDataTransaction = newTransactionDataTransaction;
        this.newTransactionCity = newTransactionCity;
        this.newTransactionDistrict = newTransactionDistrict;
        this.newTransactionStreet = newTransactionStreet;
        this.newTransactionTypeOfMarket = newTransactionTypeOfMarket;
        this.newTransactionPrice = newTransactionPrice;
        this.newTransactionFlatArea = newTransactionFlatArea;
        this.newTransactionPricePerM2 = newTransactionPricePerM2;
        this.newTransactionLevel = newTransactionLevel;
        this.newTransactionParkingSpot = newTransactionParkingSpot;
        this.newTransactionStandardLevel = newTransactionStandardLevel;
        this.newTransactionConstructionYear = newTransactionConstructionYear;
        this.newTransactionConstructionYearCategory = newTransactionConstructionYearCategory;
    }

    public int getNewTransactionId() {
        return newTransactionId;
    }

    public void setNewTransactionId(int newTransactionId) {
        this.newTransactionId = newTransactionId;
    }

    public User getNewTransactionUserId() {
        return newTransactionUserId;
    }

    public void setNewTransactionUserId(User newTransactionUserId) {
        this.newTransactionUserId = newTransactionUserId;
    }

    public String getNewTransactionDescription() {
        return newTransactionDescription;
    }

    public void setNewTransactionDescription(String newTransactionDescription) {
        this.newTransactionDescription = newTransactionDescription;
    }

    public String getNewTransactionSale() {
        return newTransactionSale;
    }

    public void setNewTransactionSale(String newTransactionSale) {
        this.newTransactionSale = newTransactionSale;
    }

    public String getNewTransactionImportant() {
        return newTransactionImportant;
    }

    public void setNewTransactionImportant(String newTransactionImportant) {
        this.newTransactionImportant = newTransactionImportant;
    }

    public String getNewTransactionDataTransaction() {
        return newTransactionDataTransaction;
    }

    public void setNewTransactionDataTransaction(String newTransactionDataTransaction) {
        this.newTransactionDataTransaction = newTransactionDataTransaction;
    }

    public String getNewTransactionCity() {
        return newTransactionCity;
    }

    public void setNewTransactionCity(String newTransactionCity) {
        this.newTransactionCity = newTransactionCity;
    }

    public String getNewTransactionDistrict() {
        return newTransactionDistrict;
    }

    public void setNewTransactionDistrict(String newTransactionDistrict) {
        this.newTransactionDistrict = newTransactionDistrict;
    }

    public String getNewTransactionStreet() {
        return newTransactionStreet;
    }

    public void setNewTransactionStreet(String newTransactionStreet) {
        this.newTransactionStreet = newTransactionStreet;
    }

    public String getNewTransactionTypeOfMarket() {
        return newTransactionTypeOfMarket;
    }

    public void setNewTransactionTypeOfMarket(String newTransactionTypeOfMarket) {
        this.newTransactionTypeOfMarket = newTransactionTypeOfMarket;
    }

    public String getNewTransactionPrice() {
        return newTransactionPrice;
    }

    public void setNewTransactionPrice(String newTransactionPrice) {
        this.newTransactionPrice = newTransactionPrice;
    }

    public String getNewTransactionFlatArea() {
        return newTransactionFlatArea;
    }

    public void setNewTransactionFlatArea(String newTransactionFlatArea) {
        this.newTransactionFlatArea = newTransactionFlatArea;
    }

    public String getNewTransactionPricePerM2() {
        return newTransactionPricePerM2;
    }

    public void setNewTransactionPricePerM2(String newTransactionPricePerM2) {
        this.newTransactionPricePerM2 = newTransactionPricePerM2;
    }

    public String getNewTransactionLevel() {
        return newTransactionLevel;
    }

    public void setNewTransactionLevel(String newTransactionLevel) {
        this.newTransactionLevel = newTransactionLevel;
    }

    public String getNewTransactionParkingSpot() {
        return newTransactionParkingSpot;
    }

    public void setNewTransactionParkingSpot(String newTransactionParkingSpot) {
        this.newTransactionParkingSpot = newTransactionParkingSpot;
    }

    public String getNewTransactionStandardLevel() {
        return newTransactionStandardLevel;
    }

    public void setNewTransactionStandardLevel(String newTransactionStandardLevel) {
        this.newTransactionStandardLevel = newTransactionStandardLevel;
    }

    public String getNewTransactionConstructionYear() {
        return newTransactionConstructionYear;
    }

    public void setNewTransactionConstructionYear(String newTransactionConstructionYear) {
        this.newTransactionConstructionYear = newTransactionConstructionYear;
    }

    public String getNewTransactionConstructionYearCategory() {
        return newTransactionConstructionYearCategory;
    }

    public void setNewTransactionConstructionYearCategory(String newTransactionConstructionYearCategory) {
        this.newTransactionConstructionYearCategory = newTransactionConstructionYearCategory;
    }
}
