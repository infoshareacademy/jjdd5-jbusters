package com.infoshareacademy.jbusters.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterTransactions {

private List<Transaction> transactionsBase= new ArrayList<>();

public FilterTransactions(ArrayList<Transaction> transactionsData){
    transactionsBase = transactionsData;
}
    // metoda zwracajaca liste tranzakcji, ktora jest wynikiem wielokrotnego przefiltrowania gwnej bazy tranzakcji
    //kolejnosc filtrow:  data tranzakcji/miasto/dzielnica/rynek/kategoria budowy/powierzchnia mieszkania
    public ArrayList<Transaction> basicFilter(Transaction userTransaction) {
        List<Transaction> lista = transactionsBase.stream()
                .filter(transaction -> transaction.getTransactionDate().isAfter(userTransaction.getTransactionDate().minusYears(2)))
                .filter(transaction -> transaction.getCity().equalsIgnoreCase(userTransaction.getCity()))
                .filter(transaction -> transaction.getTypeOfMarket().equals(userTransaction.getTypeOfMarket()))
                .filter(transaction -> transaction.getConstructionYearCategory()==(userTransaction.getConstructionYearCategory()))
                .collect(Collectors.toList());

        return new ArrayList(lista);
    }

    public ArrayList<Transaction> districtFilter(List<Transaction> transactionsBase,Transaction userTransaction,boolean districtWeightRespected) {
        List<Transaction> lista = transactionsBase.stream()

                .filter(transaction -> transaction.getDistrict().equalsIgnoreCase(userTransaction.getDistrict()))

                .collect(Collectors.toList());

        return new ArrayList(lista);
    }

    public ArrayList<Transaction> flatArrayFilter(List<Transaction> transactionsBase,Transaction userTransaction,BigDecimal areaDiff) {
        List<Transaction> lista = transactionsBase.stream()

                .filter(transaction -> ((transaction.getFlatArea()).compareTo(userTransaction.getFlatArea().add(areaDiff)))<=0)
                .filter(transaction -> ((transaction.getFlatArea()).compareTo(userTransaction.getFlatArea().subtract(areaDiff)))>=0)

                .collect(Collectors.toList());

        return new ArrayList(lista);
    }

    //TODO:wpisac metode do wypisywania dzielnic
    public ArrayList<String> getDistrictsWithSameWeight(String inputDistrict){

    }


}
