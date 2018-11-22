package com.infoshareacademy.jbusters.data;

//Klasa zawierajaca dane wczytywane z pliku, kazdy rekord w pliku excel bedzie jedna iinstancja clasy transaction.
// klasa data powinna zawierac metody pozwalajace dodawac rekord do bazy(pliku?)

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Data {

    private List<Transaction> transactionsBase = new ArrayList<>(fileToData());

    public List<Transaction> fileToData() {

        Path pathToFileTransactionCSV = Paths.get("src", "main", "resources", "transaction.csv");
        List<String> listFileTransactionCSV = null;
        try {
            listFileTransactionCSV = Files.readAllLines(pathToFileTransactionCSV);
            listFileTransactionCSV.remove(0);
            DataLoader data = new DataLoader();

            return data.createTransactionList(listFileTransactionCSV);
        } catch (IOException e) {
            System.out.println("Error while loading data: ");
            e.printStackTrace();
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

        return districtList;
    }


    public List<Transaction> getTransactionsBase() {
        return transactionsBase;
    }


    public ArrayList<Transaction> filterData() {
        //metoda wypluwajca przefiltrowana liste
        return null;
    }

    public void addTransactionToData(Transaction trans) {
        //metoda dodajca tranzakcje do bazy danych
    }
}

