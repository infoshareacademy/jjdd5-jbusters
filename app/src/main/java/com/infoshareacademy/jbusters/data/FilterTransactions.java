package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.console.ConsoleViewer;
import com.infoshareacademy.jbusters.dao.TranzactionDao;
import com.infoshareacademy.jbusters.model.Tranzaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FilterTransactions {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);


    private DistrWagesHandler distrWagesHandler;
    private List<Tranzaction> transactionsBase;
    private List<Tranzaction> base;

    private BigDecimal areaDiff;
    private BigDecimal areaDiffExpanded;
    private int minResultsNumber;
    private BigDecimal priceDiff;

    @Inject
    private Data data;

    @Inject
    StaticFields staticFields;

    @Inject
    TranzactionDao tranzactionDao;


    public FilterTransactions() {
        PropLoader properties = new PropLoader();
        try {
            InputStream is = staticFields.getAppPropertiesURL().openStream();
            properties = new PropLoader(is);
            areaDiff = properties.getAreaDiff();
            areaDiffExpanded = properties.getAreaDiffExpanded();
            minResultsNumber = properties.getMinResultsNumber();
            priceDiff = properties.getPriceDiff();

            is = staticFields.getDistrPropertiesURL().openStream();
            distrWagesHandler = new DistrWagesHandler(is);
        } catch (Exception e) {
            LOGGER.error("Missing properties file in path {} or {}", staticFields.getAppPropertiesURL().toString(), staticFields.getDistrPropertiesURL().toString());
        }
    }

    @PostConstruct
    public void init() {
        //transactionsBase = data.getTransactionsBase();
        transactionsBase = tranzactionDao.findAll();


    }

    public void setData(Data data) {
        this.data = data;
    }
// metoda zwracajaca liste tranzakcji, ktora jest wynikiem wielokrotnego przefiltrowania gwnej bazy tranzakcji
    //kolejnosc filtrow:  data tranzakcji/miasto/dzielnica/rynek/kategoria budowy/powierzchnia mieszkania

    public List<Tranzaction> theGreatFatFilter(Transaction userTransaction) {
//        List<Transaction> basicFilter = basicFilter(userTransaction);
//        if (basicFilter.size() < 11) {
//            return new ArrayList<>();
//        }
//        return selectorFilter(true, true, basicFilter, userTransaction);
        LocalDate oldestDateAccepted = LocalDate.now().minusYears(2);
        String userCity = userTransaction.getCity();
        String userTransactionType = userTransaction.getTypeOfMarket();
        int userConstrYearCat = userTransaction.getConstructionYearCategory();

        base = tranzactionDao.basicFilter(oldestDateAccepted,userCity,userTransactionType,userConstrYearCat);
        if (base.size() < 11) {
            return new ArrayList<>();
        }
        return selectorFilter(true, true, base, userTransaction);
    }


    private List<Tranzaction> notEnoughtResultsAction() {

        ConsoleViewer.clearScreen();
        System.out.println(":: Wycena niemożliwa, baza zawiera zbyt małą ilość pasujących transakcji ::\n");
        return new ArrayList<>();
    }

//    public List<Transaction> basicFilter(Transaction userTransaction) {
//        List<Transaction> lista = transactionsBase.stream()
//                .filter(transaction -> transaction.getTransactionDate().isAfter(userTransaction.getTransactionDate().minusYears(2)))
//                .filter(transaction -> transaction.getCity().equalsIgnoreCase(userTransaction.getCity()))
//                .filter(transaction -> transaction.getTypeOfMarket().equalsIgnoreCase(userTransaction.getTypeOfMarket()))
//                .filter(transaction -> transaction.getConstructionYearCategory() == (userTransaction.getConstructionYearCategory()))
//                .collect(Collectors.toList());
//
//        return lista;
//    }

    private List<Tranzaction> selectorFilter(boolean isSingleDistrict, boolean isAreaDiffSmall, List<Tranzaction> transactionsList, Transaction userTransaction) {
        if (isSingleDistrict) {
            List<Tranzaction> singleDistrictFilter = singleDistrictFilter(transactionsList, userTransaction);
            if (isAreaDiffSmall) {

                List<Tranzaction> flatAreafilter = flatAreaFilter(singleDistrictFilter, userTransaction, areaDiff);
                flatAreafilter = invalidTransactionsRemover(flatAreafilter);
                if (isEnoughtResults(flatAreafilter, minResultsNumber)) {
                    return flatAreafilter;
                } else {
                    return selectorFilter(true, false, singleDistrictFilter, userTransaction);
                }

            } else {

                List<Tranzaction> areaExpanded = flatAreaFilter(singleDistrictFilter, userTransaction, areaDiffExpanded);
                areaExpanded = invalidTransactionsRemover(areaExpanded);
                if (isEnoughtResults(areaExpanded, minResultsNumber)) {
                    return areaExpanded;
                } else {
                    return selectorFilter(false, true, transactionsList, userTransaction);
                }

            }
        } else {
            List<Tranzaction> multiDistrictFilter = multiDistrictFilter(transactionsList, userTransaction);
            if (isAreaDiffSmall) {

                List<Tranzaction> flatAreafilter = flatAreaFilter(multiDistrictFilter, userTransaction, areaDiff);
                flatAreafilter = invalidTransactionsRemover(flatAreafilter);

                if (isEnoughtResults(flatAreafilter, minResultsNumber)) {
                    return flatAreafilter;
                } else {
                    return selectorFilter(false, false, multiDistrictFilter, userTransaction);
                }

            } else {

                List<Tranzaction> areaExpanded = flatAreaFilter(multiDistrictFilter, userTransaction, areaDiffExpanded);
                areaExpanded = invalidTransactionsRemover(areaExpanded);
                if (isEnoughtResults(areaExpanded, minResultsNumber)) {
                    return flatAreaFilter(multiDistrictFilter, userTransaction, areaDiffExpanded);
                } else {
                    return notEnoughtResultsAction();
                }

            }
        }
    }


    public List<Tranzaction> singleDistrictFilter(List<Tranzaction> tranzactionsBase, Transaction userTransaction) {
//        List<Transaction> lista = transactionsBase.stream()
        List<Tranzaction> lista = base.stream()

                .filter(transaction -> (transaction.getTranzactionDistrict()).equalsIgnoreCase(userTransaction.getDistrict()))
                .collect(Collectors.toList());

        return lista;
    }


    private List<Tranzaction> multiDistrictFilter(List<Tranzaction> transactionsBase, Transaction userTransaction) {
        //List<Transaction> lista = transactionsBase.stream()
        List<Tranzaction> lista = base.stream()
                .filter(transaction -> distrWagesHandler.districtWageComparator(transaction, userTransaction))
                .collect(Collectors.toList());
        return new ArrayList(lista);
    }

    public List<Tranzaction> flatAreaFilter(List<Tranzaction> transactionsBase, Transaction userTransaction, BigDecimal areaDiff) {
        //List<Transaction> lista = transactionsBase.stream()
        List<Tranzaction> lista = base.stream()

                .filter(transaction -> ((transaction.getTranzactionFlatArea()).compareTo(userTransaction.getFlatArea().add(areaDiff))) <= 0)
                .filter(transaction -> ((transaction.getTranzactionFlatArea()).compareTo(userTransaction.getFlatArea().subtract(areaDiff))) >= 0)
                .collect(Collectors.toList());

        return new ArrayList(lista);
    }

    public List<Tranzaction> invalidTransactionsRemover(List<Tranzaction> finallySortedList) {
        finallySortedList = removeOutliers(finallySortedList, priceDiff);
        return finallySortedList;
    }

    public List<Tranzaction> removeOutliers(List<Tranzaction> transToClear, BigDecimal maxDiff) {

        if (transToClear.size() < 11) return new ArrayList<>();

        List<Tranzaction> transSortedByPPerM2 = transToClear.stream()
                .sorted(Comparator.comparing(Tranzaction::getTranzactionPricePerM2))
                .collect(Collectors.toList());

        //removeLeftOutliers(transSortedByPPerM2, maxDiff);
        //removeRightOutliers(transSortedByPPerM2, maxDiff);

        BigDecimal sumPPM2 = transSortedByPPerM2.stream()
                .map(Tranzaction::getTranzactionPricePerM2)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal avg = sumPPM2.divide(new BigDecimal(transSortedByPPerM2.size()), RoundingMode.HALF_UP);

        transSortedByPPerM2 = transSortedByPPerM2.stream()
                .filter(x -> x.getTranzactionPricePerM2().compareTo(avg.multiply(BigDecimal.valueOf(0.7))) >= 0)
                .filter(x -> x.getTranzactionPricePerM2().compareTo(avg.multiply(BigDecimal.valueOf(1.3))) <= 0)
                .collect(Collectors.toList());

        return transSortedByPPerM2;
    }

    private void removeLeftOutliers(List<Tranzaction> transSortedByPPerM2, BigDecimal maxDiff) {

        BigDecimal firstPPM2 = getPricePerMeter(transSortedByPPerM2, 0);
        BigDecimal secondPPM2 = getPricePerMeter(transSortedByPPerM2, 1);
        if (isPriceDifferenceToBig(firstPPM2, secondPPM2, maxDiff)) transSortedByPPerM2.remove(0);
    }

    private void removeRightOutliers(List<Tranzaction> transSortedByPPerM2, BigDecimal maxDiff) {
        int lastIndex = transSortedByPPerM2.size() - 1;
        BigDecimal lastPPM2 = getPricePerMeter(transSortedByPPerM2, lastIndex);
        BigDecimal secondToLastPPM2 = getPricePerMeter(transSortedByPPerM2, lastIndex - 1);
        if (isPriceDifferenceToBig(secondToLastPPM2, lastPPM2, maxDiff)) transSortedByPPerM2.remove(lastIndex);
    }

    private boolean isPriceDifferenceToBig(BigDecimal firstPrice, BigDecimal secondPrice, BigDecimal maxDiff) {
        return secondPrice.subtract(firstPrice).compareTo(maxDiff) > 0;
    }

    private BigDecimal getPricePerMeter(List<Tranzaction> transactionsList, int index) {
        return transactionsList.get(index).getTranzactionPricePerM2();
    }

    private boolean isEnoughtResults(List<Tranzaction> listToCheck, int minSize) {
        return listToCheck.size() >= minSize;
    }

}