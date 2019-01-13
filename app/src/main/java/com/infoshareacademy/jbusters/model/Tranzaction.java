package com.infoshareacademy.jbusters.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Tranzaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private int transactionId;
    
    @Column(name = "TRANSACTION_DATE")
    private LocalDate transactionDataTransaction;

    @Column(name = "CITY")
    private String transactionCity;

    @Column(name = "DISTRICT")
    private String transactionDistrict;

    @Column(name = "STREET")
    private String transactionStreet;

    @Column(name = "TYPE_OF_MARKET")
    private String transactionTypeOfMarket;

    @Column(name = "PRICE")
    private BigDecimal transactionPrice;

    @Column(name = "FLAT_AREA")
    private BigDecimal transactionFlatArea;

    @Column(name = "PRICE_PER_M2")
    private BigDecimal transactionPricePerM2;

    @Column(name = "LEVEL")
    private int transactionLevel;

    @Column(name = "PARKING_SPOT")
    private String transactionParkingSpot;

    @Column(name = "STANDARD_LEVEL")
    private String transactionStandardLevel;

    @Column(name = "CONSTRUCTION_YEAR")
    private String transactionConstructionYear;

    @Column(name = "CONSTRUCTION_YEAR_CATEGORY")
    private int transactionConstructionYearCategory;



    public Tranzaction() {
    }

    public Tranzaction(
                       LocalDate transactionDataTransaction, String transactionCity,
                       String transactionDistrict, String transactionStreet,
                       String transactionTypeOfMarket, BigDecimal transactionPrice,
                       BigDecimal transactionFlatArea, BigDecimal transactionPricePerM2,
                       int transactionLevel, String transactionParkingSpot,
                       String transactionStandardLevel, String transactionConstructionYear,
                       int transactionConstructionYearCategory) {

        this.transactionDataTransaction = transactionDataTransaction;
        this.transactionCity = transactionCity;
        this.transactionDistrict = transactionDistrict;
        this.transactionStreet = transactionStreet;
        this.transactionTypeOfMarket = transactionTypeOfMarket;
        this.transactionPrice = transactionPrice;
        this.transactionFlatArea = transactionFlatArea;
        this.transactionPricePerM2 = transactionPricePerM2;
        this.transactionLevel = transactionLevel;
        this.transactionParkingSpot = transactionParkingSpot;
        this.transactionStandardLevel = transactionStandardLevel;
        this.transactionConstructionYear = transactionConstructionYear;
        this.transactionConstructionYearCategory = transactionConstructionYearCategory;
    }




    // -----------------GETTERS & SETTERS----------------------------------
    public int getTranzactionId() {
        return transactionId;
    }

    public void setTranzactionId(int transactionId) {
        this.transactionId = transactionId;
    }


    public LocalDate getTranzactionDataTransaction() {
        return transactionDataTransaction;
    }

    public void setTranzactionDataTransaction(LocalDate transactionDataTransaction) {
        this.transactionDataTransaction = transactionDataTransaction;
    }

    public String getTranzactionCity() {
        return transactionCity;
    }

    public void setTranzactionCity(String transactionCity) {
        this.transactionCity = transactionCity;
    }

    public String getTranzactionDistrict() {
        return transactionDistrict;
    }

    public void setTranzactionDistrict(String transactionDistrict) {
        this.transactionDistrict = transactionDistrict;
    }

    public String getTranzactionStreet() {
        return transactionStreet;
    }

    public void setTranzactionStreet(String transactionStreet) {
        this.transactionStreet = transactionStreet;
    }

    public String getTranzactionTypeOfMarket() {
        return transactionTypeOfMarket;
    }

    public void setTranzactionTypeOfMarket(String transactionTypeOfMarket) {
        this.transactionTypeOfMarket = transactionTypeOfMarket;
    }

    public BigDecimal getTranzactionPrice() {
        return transactionPrice;
    }

    public void setTranzactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public BigDecimal getTranzactionFlatArea() {
        return transactionFlatArea;
    }

    public void setTranzactionFlatArea(BigDecimal transactionFlatArea) {
        this.transactionFlatArea = transactionFlatArea;
    }

    public BigDecimal getTranzactionPricePerM2() {
        return transactionPricePerM2;
    }

    public void setTranzactionPricePerM2(BigDecimal transactionPricePerM2) {
        this.transactionPricePerM2 = transactionPricePerM2;
    }

    public int getTranzactionLevel() {
        return transactionLevel;
    }

    public void setTranzactionLevel(int transactionLevel) {
        this.transactionLevel = transactionLevel;
    }

    public String getTranzactionParkingSpot() {
        return transactionParkingSpot;
    }

    public void setTranzactionParkingSpot(String transactionParkingSpot) {
        this.transactionParkingSpot = transactionParkingSpot;
    }

    public String getTranzactionStandardLevel() {
        return transactionStandardLevel;
    }

    public void setTranzactionStandardLevel(String transactionStandardLevel) {
        this.transactionStandardLevel = transactionStandardLevel;
    }

    public String getTranzactionConstructionYear() {
        return transactionConstructionYear;
    }

    public void setTranzactionConstructionYear(String transactionConstructionYear) {
        this.transactionConstructionYear = transactionConstructionYear;
    }

    public int getTranzactionConstructionYearCategory() {
        return transactionConstructionYearCategory;
    }

    public void setTranzactionConstructionYearCategory(int transactionConstructionYearCategory) {
        this.transactionConstructionYearCategory = transactionConstructionYearCategory;
    }

}