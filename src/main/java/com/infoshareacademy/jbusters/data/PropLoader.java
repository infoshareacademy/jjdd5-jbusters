package com.infoshareacademy.jbusters.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropLoader {

    private static final String DECIMAL_PLACES_KEY = "decimalplaces";
    private static final String DECIMAL_PLACES_DEFAULT_VALUE = "2";

    private Properties properties;

    public PropLoader(String file) {
        this.properties = new Properties();
        loadProperties(file);
    }

    private void loadProperties(String file) {
        try {
            this.properties.load(new FileReader(file));
        } catch (IOException e) {
            System.out.println("brak pliku properties");
        }
    }

    public int getDecimalPlaces() {
        String decimalPlaces = properties.getProperty(DECIMAL_PLACES_KEY, DECIMAL_PLACES_DEFAULT_VALUE);
        return Integer.parseInt(decimalPlaces);
    }
}