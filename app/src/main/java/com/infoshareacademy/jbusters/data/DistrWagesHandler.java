package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.spi.FileTypeDetector;
import java.util.*;

public class DistrWagesHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);
    private Properties properties;

    public DistrWagesHandler(String file) {
        this.properties = new Properties();
        loadProperties(file);
    }
    public DistrWagesHandler(InputStream is) {
        this.properties = new Properties();
        loadProperties(is);
    }

   public Properties getProperties() {
        return properties;
    }

    private void loadProperties(String file) {
        try {
            Path path = Paths.get(file);
            if(!file.contains(".properties")){
                LOGGER.error("Error - wrong filetype, .properties expected");
                throw new IOException();
            }
            this.properties.load(new FileReader(file));
            LOGGER.info("district property file loaded successfully");
        } catch (IOException e) {
            System.out.println("Plik zawierający parametry potrzebne do porównywania dzielnic nie istnieje.");
            LOGGER.error("Error loading file: {}");
        } catch (NullPointerException e){
            LOGGER.error("Error loading file: null input");
        }
    }

    private void loadProperties(InputStream is) {
        try {
            this.properties.load(is);
            is.close();
            LOGGER.info("district property file loaded successfully");
        } catch (IOException e) {
            System.out.println("Plik zawierający parametry potrzebne do porównywania dzielnic nie istnieje.");
            LOGGER.error("Error loading file: {}");
        } catch (NullPointerException e){
            LOGGER.error("Error loading file: null input");
        }
    }

/*    public Map<String, Integer> getDistrictWages() {
        return new HashMap<String, Integer>((Map) properties);
    }*/

    public boolean districtWageComparator(Transaction checkedTransaction, Transaction userTransaction) {
        String checkedTransactionDistrict = districtStringParser(checkedTransaction.getDistrict());
        String userTransactionDistrict = districtStringParser(userTransaction.getDistrict());

        if(properties.containsKey(userTransactionDistrict)){
            return (isDistrictWageEqual(checkedTransactionDistrict,userTransactionDistrict));
        }

        return false;
    }

    private boolean isDistrictWageEqual(String districtChecked, String userDistrict){
        return properties.getProperty(districtChecked).equals(userDistrict);
    }
    private String districtStringParser(String districtName) {
        return districtName.trim().replace(" ", "_").toLowerCase();
    }

}