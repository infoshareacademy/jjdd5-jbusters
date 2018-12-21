package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@ApplicationScoped
public class Data {

    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);
    private final List<Transaction> transactionsBase = new ArrayList<>(fileToData());

    public Data() {
    }

    public List<Transaction> fileToData() {
        List<String> listFileTransactionCSV = null;

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("transaction.csv");
        listFileTransactionCSV = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        listFileTransactionCSV.remove(0);
        DataLoader data = new DataLoader();
        LOGGER.info("Load file CSV. Number of loaded transactions: {}", listFileTransactionCSV.size());
        return data.createTransactionList(listFileTransactionCSV, false);
    }

    // Metoda do wyciągania z bazy danych listy miast/dzielnic bez duplikatów + w kolejnkości alfabetycznej
    // i przekazuje ja do wyswietlenia w menu

    public List<String> cityList(List<Transaction> transactionBase) {
        List<String> cityList = new ArrayList<>();
        for (int i = 0; i < transactionBase.size(); i++) {
            cityList.add(transactionBase.get(i).getCity());
        }
        Set<String> noDuplicates = new TreeSet<>(cityList);
        cityList = new ArrayList<>(noDuplicates);
        LOGGER.info("Create city list. List size: {}", cityList.size());
        return cityList;
    }

    public List<String> districtList(List<Transaction> transactionsBase, Transaction newTransaction) {

        List<String> districtList = new ArrayList<>();
        for (int i = 0; i < transactionsBase.size(); i++) {
            if (newTransaction.getCity().equals(transactionsBase.get(i).getCity())) {
                districtList.add(transactionsBase.get(i).getDistrict().trim());
            }
        }
        Set<String> noDuplicates = new TreeSet<>(districtList);
        districtList = new ArrayList<>(noDuplicates);
        LOGGER.info("Create district list. List size: {}", districtList.size());
        return districtList;
    }

    public List<Transaction> getTransactionsBase() {
        return transactionsBase;
    }

}

