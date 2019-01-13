package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@ApplicationScoped
public class ExchangeRatesManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRatesManager.class);
    private static final Path EXCHANGE_RATES_PROPERTIES_FILE = StaticFields.getExchangeRatesPropertiesFile();
    private static final Path EXCHANGE_RATES_SELECTION_FILE = StaticFields.getExchangeRatesSelectionFile();
    private static final String EXCHANGE_RATES_URL = StaticFields.getExchangeRatesUrl();
    private static final String SEPARATOR = ",";
    private static final String VALUE = "value";
    private static final String CODE = "code";
    private static final BigDecimal EXCHANGE_RATE_DEFAULT_VALUE = BigDecimal.valueOf(1.0000);
    private static final String EXCHANGE_RATE_DEFAULT_CODE = "PLN";
    private static final Properties exchangeRatesProperties = new Properties();
    private static final Properties exchangeRatesSelection = new Properties();

    public void setExchangeRate(String code, String value) throws IOException {

        if (!Files.exists(EXCHANGE_RATES_SELECTION_FILE)) {
            Files.createFile(EXCHANGE_RATES_SELECTION_FILE);
        }

        exchangeRatesSelection.setProperty(CODE, code);
        exchangeRatesSelection.setProperty(VALUE, value);

        FileWriter write = new FileWriter(EXCHANGE_RATES_SELECTION_FILE.toString());
        exchangeRatesSelection.store(write, "Exchange Rate Selection");
        write.close();
        LOGGER.info("Exchange rate set to: {} {}", code, value);
    }

    public String getExchangeRate() throws IOException {

        if (!Files.exists(EXCHANGE_RATES_SELECTION_FILE)) {
            return "PLN=1.0000";
        } else {
            FileInputStream fis = new FileInputStream(EXCHANGE_RATES_SELECTION_FILE.toString());
            exchangeRatesSelection.load(fis);
            fis.close();
            String code = exchangeRatesSelection.getProperty(CODE);
            String value = exchangeRatesSelection.getProperty(VALUE);

            return code + "=" + value;
        }
    }

    public BigDecimal getExRate() {

        if (!Files.exists(EXCHANGE_RATES_SELECTION_FILE)) {
            return EXCHANGE_RATE_DEFAULT_VALUE;
        } else {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(EXCHANGE_RATES_SELECTION_FILE.toString());
            } catch (FileNotFoundException e) {
                LOGGER.error("Missing exchange rates selection file in path: {}", EXCHANGE_RATES_SELECTION_FILE.toString());
            }

            try {
                exchangeRatesSelection.load(fis);
                LOGGER.info("Exchange rate loaded from path: {}", EXCHANGE_RATES_SELECTION_FILE.toString());
                fis.close();
            } catch (IOException e) {
                LOGGER.error("Exchange rates selection file could not be loaded from path: {}", EXCHANGE_RATES_SELECTION_FILE.toString());
            }

            String exchangeRateCodeString = exchangeRatesSelection.getProperty(CODE);
            String exchangeRateValueString = exchangeRatesSelection.getProperty(VALUE);
            BigDecimal exchangeRate = new BigDecimal(exchangeRateValueString);
            LOGGER.info("Returned following exchange rate: {} {}", exchangeRateCodeString, exchangeRateValueString);

            return exchangeRate;
        }
    }

    public void updateExchangeRates() throws IOException {

        if (!Files.exists(EXCHANGE_RATES_PROPERTIES_FILE)) {
            Files.createFile(EXCHANGE_RATES_PROPERTIES_FILE);
        }

        URL onlineExchangeRates = new URL(EXCHANGE_RATES_URL);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(onlineExchangeRates.openStream()))) {

        String inputLine;
        String[] splitLine;

        while ((inputLine = in.readLine()) != null) {

            splitLine = inputLine.split(SEPARATOR);

            exchangeRatesProperties.setProperty(splitLine[0], splitLine[2]);
        }

        } catch (IOException | NullPointerException e) {
            LOGGER.warn("File not found.");
        }

        FileWriter write = new FileWriter(EXCHANGE_RATES_PROPERTIES_FILE.toString());
        exchangeRatesProperties.store(write, "Exchange Rates");
        write.close();
    }

    public String getLastModifiedDate() {

        LOGGER.info("Checking last modification date of exchange rates file.");

        if (!Files.exists(EXCHANGE_RATES_PROPERTIES_FILE)) {
            LOGGER.info("Exchange rates file is not existing.");
            return "No date";
        } else {
            File file = new File(EXCHANGE_RATES_PROPERTIES_FILE.toString());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            LOGGER.info("Exchange rates file was last modified on: {}", sdf.format(file.lastModified()));
            return sdf.format(file.lastModified());
        }
    }

    public String getURL() {

        LOGGER.info("Checking source URL for exchange rates.");
        LOGGER.info("URL is: " + EXCHANGE_RATES_URL);

        return EXCHANGE_RATES_URL;
    }

    public String getExCode() {
        if (!Files.exists(EXCHANGE_RATES_SELECTION_FILE)) {
            return EXCHANGE_RATE_DEFAULT_CODE;
        }
        try {
            FileInputStream fis = new FileInputStream(EXCHANGE_RATES_SELECTION_FILE.toString());
            exchangeRatesSelection.load(fis);
            LOGGER.info("Exchange rate code loaded from path: {}", EXCHANGE_RATES_SELECTION_FILE.toString());
            fis.close();
        } catch (IOException e) {
            LOGGER.error("Missing exchange rates selection file in path: {}", EXCHANGE_RATES_SELECTION_FILE.toString());
        }
        return exchangeRatesSelection.getProperty(CODE);
    }

    public Map<String, String> getExRatesMap() {

        Map<String, String> exRatesMap = new HashMap<>();

        Enumeration<?> e = exchangeRatesProperties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = exchangeRatesProperties.getProperty(key);

            exRatesMap.put(key, value);
        }

        return exRatesMap;
    }
}
