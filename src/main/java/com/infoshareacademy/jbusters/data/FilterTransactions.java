package com.infoshareacademy.jbusters.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public ArrayList<Transaction> districtFilter(List<Transaction> transactionsBase,Transaction userTransaction, boolean districtWeightRespected) {
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

    public Transaction getMostExpensiveFlat(List<Transaction> sortedTransactions){
        return sortedTransactions.get(sortedTransactions.size()-1);
    }
    public Transaction getLessExpensiveFlat(List<Transaction> sortedTransactions){
        return sortedTransactions.get(0);
    }
    private List<Transaction> removeOutliers (List<Transaction> transToClear,BigDecimal maxDiff){
        List<Transaction> transSortedByPPerM2 = transToClear.stream()
                .sorted((o1, o2)->o1.getPricePerM2().compareTo(o2.getPricePerM2())).collect(Collectors.toList());

        int lastIndex = transSortedByPPerM2.size()-1;
        BigDecimal firstPPM2 = getPricePerMeter(transSortedByPPerM2,0);
        BigDecimal secondPPM2 = getPricePerMeter(transSortedByPPerM2,1);
        BigDecimal lastPPM2 = getPricePerMeter(transSortedByPPerM2,lastIndex);
        BigDecimal secondToLastPPM2 = getPricePerMeter(transSortedByPPerM2,lastIndex-1);

        if (isPriceDifferenceToBig(firstPPM2,secondPPM2,maxDiff)) transSortedByPPerM2.remove(0);
        if (isPriceDifferenceToBig(secondToLastPPM2,lastPPM2,maxDiff)) transSortedByPPerM2.remove(lastIndex);

        return transSortedByPPerM2;
    }
    //TODO porownac enumy z danej listy i na ich podastawie wyszanczyc minimalne wartosci pol w danym zbiorze, tania transakcja powinna miec minimalne pola jesli nie to wywalamy ja
    private boolean isCheapestTransactionValid(List<Transaction> finallySortedList){
            Transaction transToCheck = getLessExpensiveFlat(finallySortedList);

    }
    private boolean isMostExpensiveTransactionValid(List<Transaction>){


    }

    private BigDecimal getPricePerMeter(List<Transaction> transactionsList, int index ){
    return transactionsList.get(index).getPricePerM2();
    }
    private boolean isPriceDifferenceToBig(BigDecimal firstPrice, BigDecimal secondPrice, BigDecimal maxDiff){
        return secondPrice.subtract(firstPrice).compareTo(maxDiff)>0;
    }


    //TODO:wpisac metode do wypisywania dzielnic
    public ArrayList<String> getDistrictsWithSameWeight(String inputDistrict){

    }


}
