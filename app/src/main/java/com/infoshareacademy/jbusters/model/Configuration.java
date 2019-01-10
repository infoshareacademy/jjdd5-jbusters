package com.infoshareacademy.jbusters.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name="configurations")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONFIGURATION_ID")
    private int configId;


    @Column(name = "MIN_RESULTS_REQUIRED")
    @NotNull
    private int minResultsReq;

    @Column(name="EXCHANGE_RATE")
    @NotNull
    BigDecimal exchangeRate;

    @Column(name="CURRENCY")
    @NotNull
    String currency;

    @Column(name="AREA_DIFF")
    @NotNull
    BigDecimal areaDiff;

    @Column(name="AREA_DIFF_EXPANDED")
    @NotNull
    BigDecimal areaDiffExpanded;

    @Column(name="DECIMAL_PLACES")
    @NotNull
    int decimalPlaces;

    @Column(name="PRICE_DIFF")
    @NotNull
    BigDecimal priceDiff;

    @Column(name="LONG_DF_PATTERN")
    @NotNull
    String longDFPattern;

    @Column(name="SHORT_DF_PATTERN")
    @NotNull
    String shortDFPattern;

    @Column(name="DECIMAL_SEPARATOR")
    @NotNull
    String decimalSep;

    @Column(name="GROUPING_SEPARATOR")
    @NotNull
    String groupingSep;

    public Configuration() {
    }

    public Configuration(int minResultsReq, BigDecimal exchangeRate,
                         String currency, BigDecimal areaDiff,
                         BigDecimal areaDiffExpanded, int decimalPlaces,
                         BigDecimal priceDiff, String longDFPattern,
                         String shortDFPattern, String decimalSep,
                         String groupingSep) {

        this.minResultsReq = minResultsReq;
        this.exchangeRate = exchangeRate;
        this.currency = currency;
        this.areaDiff = areaDiff;
        this.areaDiffExpanded = areaDiffExpanded;
        this.decimalPlaces = decimalPlaces;
        this.priceDiff = priceDiff;
        this.longDFPattern = longDFPattern;
        this.shortDFPattern = shortDFPattern;
        this.decimalSep = decimalSep;
        this.groupingSep = groupingSep;
    }
//
//    public Configuration(String longDFPattern, String shortDFPattern,
//                         char decimalSep, char groupingSep) {
//        this.minResultsReq = 11;
//        this.exchangeRate = new BigDecimal(1.0);
//        this.currency = "PLN";
//        this.areaDiff = new BigDecimal(20.0);
//        this.areaDiffExpanded = new BigDecimal(25.0);
//        this.decimalPlaces = 2;
//        this.priceDiff = new BigDecimal(600.0);
//        this.longDFPattern = longDFPattern;
//        this.shortDFPattern = shortDFPattern;
//        this.decimalSep = decimalSep;
//        this.groupingSep = groupingSep;
//    }

    public int getConfigId() {
        return configId;
    }

    public int getMinResultsReq() {
        return minResultsReq;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAreaDiff() {
        return areaDiff;
    }

    public BigDecimal getAreaDiffExpanded() {
        return areaDiffExpanded;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public BigDecimal getPriceDiff() {
        return priceDiff;
    }

    public String getLongDFPattern() {
        return longDFPattern;
    }

    public String getShortDFPattern() {
        return shortDFPattern;
    }

    public String getDecimalSep() {
        return decimalSep;
    }

    public String getGroupingSep() {
        return groupingSep;
    }

//    public void setConfigId(int configId) {
//        this.configId = configId;
//    }

    public void setMinResultsReq(int minResultsReq) {
        this.minResultsReq = minResultsReq;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAreaDiff(BigDecimal areaDiff) {
        this.areaDiff = areaDiff;
    }

    public void setAreaDiffExpanded(BigDecimal areaDiffExpanded) {
        this.areaDiffExpanded = areaDiffExpanded;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public void setPriceDiff(BigDecimal priceDiff) {
        this.priceDiff = priceDiff;
    }

    public void setLongDFPattern(String longDFPattern) {
        this.longDFPattern = longDFPattern;
    }

    public void setShortDFPattern(String shortDFPattern) {
        this.shortDFPattern = shortDFPattern;
    }

    public void setDecimalSep(String decimalSep) {
        this.decimalSep = decimalSep;
    }

    public void setGroupingSep(String groupingSep) {
        this.groupingSep = groupingSep;
    }
}
