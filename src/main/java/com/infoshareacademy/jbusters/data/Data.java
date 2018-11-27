package com.infoshareacademy.jbusters.data;

//Klasa zawierajaca dane wczytywane z pliku, kazdy rekord w pliku excel bedzie jedna iinstancja clasy transaction.
// klasa data powinna zawierac metody pozwalajace dodawac rekord do bazy(pliku?)

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Data {

    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);
    private List<Transaction> transactionsBase = new ArrayList<>(fileToData());

    public List<Transaction> fileToData() {
        Path pathToFileTransactionCSV = Paths.get("data", "transaction.csv");
        List<String> listFileTransactionCSV = null;
        try {
            listFileTransactionCSV = Files.readAllLines(pathToFileTransactionCSV);
            listFileTransactionCSV.remove(0);
            DataLoader data = new DataLoader();
            LOGGER.info("Load file CSV. Path: {}", pathToFileTransactionCSV);
            return data.createTransactionList(listFileTransactionCSV);
        } catch (IOException e) {
            System.out.println("Error while loading data: ");
            e.printStackTrace();
            LOGGER.error("Error loading file: {}", pathToFileTransactionCSV);
        }
        return null;
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
                districtList.add(transactionsBase.get(i).getDistrict());
            }
        }
        Set<String> noDuplicates = new TreeSet<>(districtList);
        districtList = new ArrayList<>(noDuplicates);
        LOGGER.info("Creste district list. List size: {}", districtList.size());
        return districtList;
    }

    public List<Transaction> getTransactionsBase() {
        return transactionsBase;
    }

}

