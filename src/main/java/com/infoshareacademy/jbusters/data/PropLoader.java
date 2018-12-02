package com.infoshareacademy.jbusters.data;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropLoader {

    private static final String DECIMAL_PLACES_KEY = "decimalPlaces";
    private static final String DECIMAL_PLACES_DEFAULT_VALUE = "0";
    private static final String CURRENCY_KEY = "currency";
    private static final String CURRENCY_DEFAULT_VALUE = "PLN";
    private static final String EXCHANGE_RATE_KEY = "exchangeRate";
    private static final String EXCHANGE_RATE_DEFAULT_VALUE = "1";
    private static final String AREA_DIFF_KEY = "areaDiff";
    private static final String AREA_DIFF_DEFAULT_VALUE = "20";
    private static final String AREA_DIFF_EXPANDED_KEY = "areaDiffExpanded";
    private static final String AREA_DIFF_EXPANDED_DEFAULT_VALUE = "25";
    private static final String MIN_RESULTS_NUMBER_KEY = "minResultsNumber";
    private static final String MIN_RESULTS_NUMBER_DEFAULT_VALUE = "11";
    private static final String PRICE_DIFF_KEY = "priceDiff";
    private static final String PRICE_DIFF_DEFAULT_VALUE = "600";

    private Properties properties;

    public PropLoader(String file) {
        this.properties = new Properties();
        loadProperties(file);
    }

    private void loadProperties(String file) {
        try {
            this.properties.load(new FileReader(file));
        } catch (IOException e) {
            System.out.println("Missing properties file.");
        }
    }

    public int getDecimalPlaces() {
        String decimalPlaces = properties.getProperty(DECIMAL_PLACES_KEY, DECIMAL_PLACES_DEFAULT_VALUE);
        return Integer.parseInt(decimalPlaces);
    }

    public String getCurrency() {
        String currency = properties.getProperty(CURRENCY_KEY, CURRENCY_DEFAULT_VALUE);
        return currency;
    }

    public String getExchangeRate() {
        String exchangeRate = properties.getProperty(EXCHANGE_RATE_KEY, EXCHANGE_RATE_DEFAULT_VALUE);
        return exchangeRate;
    }

    public BigDecimal getExchangeRateBigDecimal() {
        String exchangeRateString = properties.getProperty(EXCHANGE_RATE_KEY, EXCHANGE_RATE_DEFAULT_VALUE);
        BigDecimal exchangeRateBigDecimal = new BigDecimal(exchangeRateString);
        return exchangeRateBigDecimal;
    }

    public BigDecimal getAreaDiff() {
        String areaDiffString = properties.getProperty(AREA_DIFF_KEY, AREA_DIFF_DEFAULT_VALUE);
        BigDecimal areaDiff = new BigDecimal(areaDiffString);
        return areaDiff;
    }

    public BigDecimal getAreaDiffExpanded() {
        String areaDiffExpandedString = properties.getProperty(AREA_DIFF_EXPANDED_KEY, AREA_DIFF_EXPANDED_DEFAULT_VALUE);
        BigDecimal areaDiffExpanded = new BigDecimal(areaDiffExpandedString);
        return areaDiffExpanded;
    }

    public int getMinResultsNumber() {
        String minResultsNumberString = properties.getProperty(MIN_RESULTS_NUMBER_KEY, MIN_RESULTS_NUMBER_DEFAULT_VALUE);
        return Integer.parseInt(minResultsNumberString);
    }

    public BigDecimal getPriceDiff() {
        String priceDiffString = properties.getProperty(PRICE_DIFF_KEY, PRICE_DIFF_DEFAULT_VALUE);
        BigDecimal priceDiff = new BigDecimal(priceDiffString);
        return priceDiff;
    }
}