package com.infoshareacademy.jbusters.data;

import java.io.FileReader;
import java.io.IOException;
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
}