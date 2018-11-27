package com.infoshareacademy.jbusters.data;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterTransactions {

    private List<Transaction> transactionsBase = new ArrayList<>();

    public FilterTransactions(ArrayList<Transaction> transactionsData) {

        transactionsBase = transactionsData;
    }

    // metoda zwracajaca liste tranzakcji, ktora jest wynikiem wielokrotnego przefiltrowania gwnej bazy tranzakcji
    //kolejnosc filtrow:  data tranzakcji/miasto/dzielnica/rynek/kategoria budowy/powierzchnia mieszkania

    public List<Transaction> theGreatFatFilter(Transaction userTransaction){

        if(basicFilter(userTransaction).size()>11){

        }
        return new ArrayList<Transaction>();
    }


    private List<Transaction> basicFilter(Transaction userTransaction) {
        List<Transaction> lista = transactionsBase.stream()
                .filter(transaction -> transaction.getTransactionDate().isAfter(userTransaction.getTransactionDate().minusYears(2)))
                .filter(transaction -> transaction.getCity().equalsIgnoreCase(userTransaction.getCity()))
                .filter(transaction -> transaction.getTypeOfMarket().equals(userTransaction.getTypeOfMarket()))
                .filter(transaction -> transaction.getConstructionYearCategory() == (userTransaction.getConstructionYearCategory()))
                .collect(Collectors.toList());

        return lista;
    }

    private List<Transaction> districtFilter(List<Transaction> transactionsBase, Transaction userTransaction, boolean districtWeightRespected) {
        List<Transaction> lista = transactionsBase.stream()

                .filter(transaction -> transaction.getDistrict().equalsIgnoreCase(userTransaction.getDistrict()))
                .collect(Collectors.toList());

        return new ArrayList(lista);
    }

    private List<Transaction> flatArrayFilter(List<Transaction> transactionsBase, Transaction userTransaction, BigDecimal areaDiff) {
        List<Transaction> lista = transactionsBase.stream()

                .filter(transaction -> ((transaction.getFlatArea()).compareTo(userTransaction.getFlatArea().add(areaDiff))) <= 0)
                .filter(transaction -> ((transaction.getFlatArea()).compareTo(userTransaction.getFlatArea().subtract(areaDiff))) >= 0)

                .collect(Collectors.toList());

        return new ArrayList(lista);
    }


    private List<Transaction> removeOutliers(List<Transaction> transToClear, BigDecimal maxDiff) {

        List<Transaction> transSortedByPPerM2 = transToClear.stream()
                .sorted((o1, o2) -> o1.getPricePerM2().compareTo(o2.getPricePerM2()))
                .collect(Collectors.toList());

        removeLeftOutliers(transSortedByPPerM2,maxDiff);
        removeRightOutliers(transSortedByPPerM2,maxDiff);
        return transSortedByPPerM2;
    }

    private void removeLeftOutliers(List<Transaction> transSortedByPPerM2, BigDecimal maxDiff){

        BigDecimal firstPPM2 = getPricePerMeter(transSortedByPPerM2, 0);
        BigDecimal secondPPM2 = getPricePerMeter(transSortedByPPerM2, 1);
        if (isPriceDifferenceToBig(firstPPM2, secondPPM2, maxDiff)) transSortedByPPerM2.remove(0);
    }

    private void removeRightOutliers(List<Transaction> transSortedByPPerM2, BigDecimal maxDiff){
        int lastIndex = transSortedByPPerM2.size() - 1;
        BigDecimal lastPPM2 = getPricePerMeter(transSortedByPPerM2, lastIndex);
        BigDecimal secondToLastPPM2 = getPricePerMeter(transSortedByPPerM2, lastIndex - 1);
        if (isPriceDifferenceToBig(secondToLastPPM2, lastPPM2, maxDiff)) transSortedByPPerM2.remove(lastIndex);
    }

    private boolean isPriceDifferenceToBig(BigDecimal firstPrice, BigDecimal secondPrice, BigDecimal maxDiff) {
        return secondPrice.subtract(firstPrice).compareTo(maxDiff) > 0;
    }

    //TODO porownac enumy z danej listy i na ich podastawie wyszanczyc minimalne wartosci pol w danym zbiorze, tania transakcja powinna miec minimalne pola jesli nie to wywalamy ja
    private boolean isCheapestTransactionValid(List<Transaction> finallySortedList) {
        Transaction transToCheck = getLessExpensiveFlat(finallySortedList);

        return isWorstFlatArea(finallySortedList, transToCheck) &&
                isWorstLevel(finallySortedList, transToCheck) &&
                isWorstStandardLevel(finallySortedList, transToCheck) &&
                isWorstParkingPlace(finallySortedList, transToCheck);

    }

    private boolean isMostExpensiveTransactionValid(List<Transaction> finallySortedList) {
        Transaction transToCheck = getLessExpensiveFlat(finallySortedList);

        return isBestFlatArea(finallySortedList, transToCheck) &&
                isBestLevel(finallySortedList, transToCheck) &&
                isBestStandardLevel(finallySortedList, transToCheck) &&
                isBestParkingPlace(finallySortedList, transToCheck);

    }

    private Transaction getMostExpensiveFlat(List<Transaction> sortedTransactions) {
        return sortedTransactions.get(sortedTransactions.size() - 1);
    }

    private Transaction getLessExpensiveFlat(List<Transaction> sortedTransactions) {
        return sortedTransactions.get(0);
    }

    private boolean isWorstParkingPlace(List<Transaction> finallySortedList, Transaction transToCheck) {

        ParkingPlace worstParking = finallySortedList.stream()
                .map(x -> ParkingPlace.valueOf(x.getParkingSpot()))
                .distinct().min(new ParkingPlaceComparator())
                .orElse(ParkingPlace.BRAK_MP);
        return ParkingPlace.valueOf(transToCheck.getParkingSpot()).compareTo(worstParking) == 0;

    }

    private boolean isBestParkingPlace(List<Transaction> finallySortedList, Transaction transToCheck) {

        ParkingPlace bestParking = finallySortedList.stream()
                .map(x -> ParkingPlace.valueOf(x.getParkingSpot()))
                .distinct().max(new ParkingPlaceComparator())
                .orElse(ParkingPlace.GARAZ);
        return ParkingPlace.valueOf(transToCheck.getParkingSpot()) == bestParking;

    }

    private boolean isWorstStandardLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        StandardLevel worstStandard = finallySortedList.stream()
                .map(x -> StandardLevel.valueOf(x.getStandardLevel()))
                .distinct().min(new StandardLevelComparator())
                .orElse(StandardLevel.DEWELOPERSKI);
        return StandardLevel.valueOf(transToCheck.getStandardLevel()) == worstStandard;
    }

    private boolean isBestStandardLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        StandardLevel bestStandard = finallySortedList.stream()
                .map(x -> StandardLevel.valueOf(x.getStandardLevel()))
                .distinct().max(new StandardLevelComparator())
                .orElse(StandardLevel.WYSOKI);
        return StandardLevel.valueOf(transToCheck.getStandardLevel()) == bestStandard;
    }

    private boolean isWorstLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        int worstLevel = finallySortedList.stream()
                .mapToInt(Transaction::getLevel)
                .min()
                .orElse(0);
        return transToCheck.getLevel() == worstLevel;
    }

    private boolean isBestLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        int bestLevel = finallySortedList.stream()
                .mapToInt(Transaction::getLevel)
                .max()
                .orElse(0);
        return transToCheck.getLevel() == bestLevel;
    }

    private boolean isWorstFlatArea(List<Transaction> finallySortedList, Transaction transToCheck) {
        BigDecimal worstFlatArea = finallySortedList.stream()
                .map(Transaction::getFlatArea)
                .distinct()
                .min(BigDecimal::compareTo)
                .orElse(new BigDecimal(0.0));
        return transToCheck.getFlatArea().compareTo(worstFlatArea) == 0;
    }

    private boolean isBestFlatArea(List<Transaction> finallySortedList, Transaction transToCheck) {
        BigDecimal worstFlatArea = finallySortedList.stream()
                .map(Transaction::getFlatArea)
                .distinct()
                .max(BigDecimal::compareTo)
                .orElse(new BigDecimal(0.0));
        return transToCheck.getFlatArea().compareTo(worstFlatArea) == 0;
    }


    private BigDecimal getPricePerMeter(List<Transaction> transactionsList, int index) {
        return transactionsList.get(index).getPricePerM2();
    }



    //TODO:wpisac metode do wypisywania dzielnic
    public List<String> getDistrictsWithSameWeight(String inputDistrict) {
        return new ArrayList<String>();
    }


}
