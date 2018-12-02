package com.infoshareacademy.jbusters.data;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DistrWagesHandler {

    private Properties properties;

    public DistrWagesHandler(String file) {
        this.properties = new Properties();
        loadProperties(file);
    }

    private void loadProperties(String file) {
        try {
            this.properties.load(new FileReader(file));
        } catch (IOException e) {
            System.out.println("Plik zawierający parametry potrzebne do porównywania dzielnic nie istnieje.");
        }
    }

    public Map<String,Integer> getDistrictWages(){
       return new HashMap<String,Integer>((Map)properties);
    }

}