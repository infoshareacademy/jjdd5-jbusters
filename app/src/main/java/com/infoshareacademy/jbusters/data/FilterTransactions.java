package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.console.ConsoleViewer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class FilterTransactions {

    private PropLoader properties = new PropLoader(System.getProperty("jboss.home.dir") + "/data/app.properties");

    private Map<String, Integer> districtProperties;
    private List<Transaction> transactionsBase;
    private BigDecimal areaDiff = properties.getAreaDiff();
    private BigDecimal areaDiffExpanded = properties.getAreaDiffExpanded();
    private int minResultsNumber = properties.getMinResultsNumber();
    private BigDecimal priceDiff = properties.getPriceDiff();

    @Inject
    private Data data;

    public FilterTransactions() {
        districtProperties = new DistrWagesHandler(System.getProperty("jboss.home.dir") + "/data/districts.properties").getDistrictWages();
    }

    @PostConstruct
    public void init() {
        transactionsBase = data.getTransactionsBase();
    }

    public void setData(Data data) {
        this.data = data;
    }
// metoda zwracajaca liste tranzakcji, ktora jest wynikiem wielokrotnego przefiltrowania gwnej bazy tranzakcji
    //kolejnosc filtrow:  data tranzakcji/miasto/dzielnica/rynek/kategoria budowy/powierzchnia mieszkania

    public List<Transaction> theGreatFatFilter(Transaction userTransaction) {
        List<Transaction> basicFilter = basicFilter(userTransaction);
        if (basicFilter.size() < 11) {
            return new ArrayList<>();
        }
        return selectorFilter(true, true, basicFilter, userTransaction);
    }


    private List<Transaction> notEnoughtResultsAction() {

        ConsoleViewer.clearScreen();
        System.out.println(":: Wycena niemożliwa, baza zawiera zbyt małą ilość pasujących transakcji ::\n");
        return new ArrayList<Transaction>();
    }

    public List<Transaction> basicFilter(Transaction userTransaction) {
        List<Transaction> lista = transactionsBase.stream()
                .filter(transaction -> transaction.getTransactionDate().isAfter(userTransaction.getTransactionDate().minusYears(2)))
                .filter(transaction -> transaction.getCity().equalsIgnoreCase(userTransaction.getCity()))
                .filter(transaction -> transaction.getTypeOfMarket().equalsIgnoreCase(userTransaction.getTypeOfMarket()))
                .filter(transaction -> transaction.getConstructionYearCategory() == (userTransaction.getConstructionYearCategory()))
                .collect(Collectors.toList());

        return lista;
    }

    private List<Transaction> selectorFilter(boolean isSingleDistrict, boolean isAreaDiffSmall, List<Transaction> transactionsList, Transaction userTransaction) {
        if (isSingleDistrict) {
            List<Transaction> singleDistrictFilter = singleDistrictFilter(transactionsList, userTransaction);
            if (isAreaDiffSmall) {

                List<Transaction> flatAreafilter = flatAreaFilter(singleDistrictFilter, userTransaction, areaDiff);
                flatAreafilter = invalidTransactionsRemover(flatAreafilter);
                if (isEnoughtResults(flatAreafilter, minResultsNumber)) {
                    return flatAreafilter;
                } else {
                    return selectorFilter(true, false, singleDistrictFilter, userTransaction);
                }

            } else {

                List<Transaction> areaExpanded = flatAreaFilter(singleDistrictFilter, userTransaction, areaDiffExpanded);
                areaExpanded = invalidTransactionsRemover(areaExpanded);
                if (isEnoughtResults(areaExpanded, minResultsNumber)) {
                    return areaExpanded;
                } else {
                    return selectorFilter(false, true, transactionsList, userTransaction);
                }

            }
        } else {
            List<Transaction> multiDistrictFilter = multiDistrictFilter(transactionsList, userTransaction);
            if (isAreaDiffSmall) {

                List<Transaction> flatAreafilter = flatAreaFilter(multiDistrictFilter, userTransaction, areaDiff);
                flatAreafilter = invalidTransactionsRemover(flatAreafilter);

                if (isEnoughtResults(flatAreafilter, minResultsNumber)) {
                    return flatAreafilter;
                } else {
                    return selectorFilter(false, false, multiDistrictFilter, userTransaction);
                }

            } else {

                List<Transaction> areaExpanded = flatAreaFilter(multiDistrictFilter, userTransaction, areaDiffExpanded);
                areaExpanded = invalidTransactionsRemover(areaExpanded);
                if (isEnoughtResults(areaExpanded, minResultsNumber)) {
                    return flatAreaFilter(multiDistrictFilter, userTransaction, areaDiffExpanded);
                } else {
                    return notEnoughtResultsAction();
                }

            }
        }
    }


    public List<Transaction> singleDistrictFilter(List<Transaction> transactionsBase, Transaction userTransaction) {
        List<Transaction> lista = transactionsBase.stream()

                .filter(transaction -> (transaction.getDistrict()).equalsIgnoreCase(userTransaction.getDistrict()))
                .collect(Collectors.toList());

        return lista;
    }

    //TODO add district weight evaluation
    private List<Transaction> multiDistrictFilter(List<Transaction> transactionsBase, Transaction userTransaction) {
        List<Transaction> lista = transactionsBase.stream()

                .filter(transaction -> districtWageComparator(transaction, userTransaction))
                .collect(Collectors.toList());
        return new ArrayList(lista);
    }

    public List<Transaction> flatAreaFilter(List<Transaction> transactionsBase, Transaction userTransaction, BigDecimal areaDiff) {
        List<Transaction> lista = transactionsBase.stream()

                .filter(transaction -> ((transaction.getFlatArea()).compareTo(userTransaction.getFlatArea().add(areaDiff))) <= 0)
                .filter(transaction -> ((transaction.getFlatArea()).compareTo(userTransaction.getFlatArea().subtract(areaDiff))) >= 0)
                .collect(Collectors.toList());

        return new ArrayList(lista);
    }

    public List<Transaction> invalidTransactionsRemover(List<Transaction> finallySortedList) {
        finallySortedList = removeOutliers(finallySortedList, priceDiff);
        return finallySortedList;
    }

    public List<Transaction> removeOutliers(List<Transaction> transToClear, BigDecimal maxDiff) {

        if (transToClear.size() < 11) return new ArrayList<>();

        List<Transaction> transSortedByPPerM2 = transToClear.stream()
                .sorted(Comparator.comparing(Transaction::getPricePerM2))
                .collect(Collectors.toList());

        removeLeftOutliers(transSortedByPPerM2, maxDiff);
        removeRightOutliers(transSortedByPPerM2, maxDiff);
        return transSortedByPPerM2;
    }

    private void removeLeftOutliers(List<Transaction> transSortedByPPerM2, BigDecimal maxDiff) {

        BigDecimal firstPPM2 = getPricePerMeter(transSortedByPPerM2, 0);
        BigDecimal secondPPM2 = getPricePerMeter(transSortedByPPerM2, 1);
        if (isPriceDifferenceToBig(firstPPM2, secondPPM2, maxDiff)) transSortedByPPerM2.remove(0);
    }

    private void removeRightOutliers(List<Transaction> transSortedByPPerM2, BigDecimal maxDiff) {
        int lastIndex = transSortedByPPerM2.size() - 1;
        BigDecimal lastPPM2 = getPricePerMeter(transSortedByPPerM2, lastIndex);
        BigDecimal secondToLastPPM2 = getPricePerMeter(transSortedByPPerM2, lastIndex - 1);
        if (isPriceDifferenceToBig(secondToLastPPM2, lastPPM2, maxDiff)) transSortedByPPerM2.remove(lastIndex);
    }

    private boolean isPriceDifferenceToBig(BigDecimal firstPrice, BigDecimal secondPrice, BigDecimal maxDiff) {
        return secondPrice.subtract(firstPrice).compareTo(maxDiff) > 0;
    }

    private BigDecimal getPricePerMeter(List<Transaction> transactionsList, int index) {
        return transactionsList.get(index).getPricePerM2();
    }

    private boolean isEnoughtResults(List<Transaction> listToCheck, int minSize) {
        return listToCheck.size() >= minSize;
    }

    private boolean districtWageComparator(Transaction checkedTransaction, Transaction userTransaction) {
        String checkedTransactionDistrict = districtStringParser(checkedTransaction.getDistrict());
        String userTransactionDistrict = districtStringParser(userTransaction.getDistrict());

        DistrWagesHandler districtPropnew = new DistrWagesHandler(System.getProperty("jboss.home.dir") + "/data/districts.properties");


        if(districtPropnew.getProperties().containsKey(userTransactionDistrict)){
            return (districtPropnew.getProperties().getProperty(checkedTransactionDistrict).equals(districtPropnew.getProperties().getProperty(userTransactionDistrict)));
        }

        return false;
    }

    private String districtStringParser(String districtName) {
        return districtName.trim().replace(" ", "_").toLowerCase();
    }
}