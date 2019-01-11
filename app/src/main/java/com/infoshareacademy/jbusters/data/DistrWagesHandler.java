package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.model.Tranzaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DistrWagesHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);
    private Properties properties;

    public DistrWagesHandler(InputStream is) {
        this.properties = new Properties();
        loadProperties(is);
    }

    public Properties getProperties() {
        return properties;
    }


    private void loadProperties(InputStream is) {
        try {
            this.properties.load(is);
            is.close();
            LOGGER.info("district property file loaded successfully");
        } catch (IOException e) {
            System.out.println("Plik zawierający parametry potrzebne do porównywania dzielnic nie istnieje.");
            LOGGER.error("Error loading file: {}");
        }
    }
//todo properties zamienic na dane z tabeli
    public boolean districtWageComparator(Tranzaction checkedTransaction, Transaction userTransaction) {
        String checkedTransactionDistrict = districtStringParser(checkedTransaction.getTranzactionDistrict());
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