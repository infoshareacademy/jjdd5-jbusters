package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.dao.DistrictWageDao;
import com.infoshareacademy.jbusters.dao.TranzactionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Stateless
public class FilterTransactions {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterTransactions.class);


    private BigDecimal areaDiff;
   private BigDecimal areaDiffExpanded;
    private int minResultsNumber;
    private BigDecimal priceDiff;

    @Inject
    StaticFields staticFields;
    @Inject
    TranzactionDao tranzactionDao;
    @Inject
    DistrictWageDao districtWageDao;

    private DistrWagesHandler distrWagesHandler;

    @PostConstruct
    public void init() {
        areaDiff = staticFields.getAreaDiff();
        areaDiffExpanded = staticFields.getAreaDiffExpanded();
        minResultsNumber = staticFields.getMinResultsReq();
        priceDiff = staticFields.getPriceDiff();
        distrWagesHandler = new DistrWagesHandler(districtWageDao);
    }

    public FilterTransactions() {
    }


    public List<Transaction> theGreatFatFilter(Transaction userTransaction) {

        LocalDate oldestDateAccepted = LocalDate.now().minusYears(2);
        String userCity = userTransaction.getCity();
        String userTransactionType = userTransaction.getTypeOfMarket();
        int userConstrYearCat = userTransaction.getConstructionYearCategory();

        List<Transaction> transactionsBase = new ArrayList<>();
        tranzactionDao.basicFilter(oldestDateAccepted, userCity, userTransactionType, userConstrYearCat).forEach(t ->{
            transactionsBase.add(new Transaction(t));
        } );
        LOGGER.info("uruchomiono filtr bazowy: tabela wynikowa zawiera {} element/ów", transactionsBase.size());
        if (transactionsBase.size() < 11) {
            return new ArrayList<>();
        }


        return selectorFilter(true, true, transactionsBase,new ArrayList<>(), userTransaction);
    }


    private List<Transaction> notEnoughtResultsAction() {

        return new ArrayList<>();
    }


    private List<Transaction> selectorFilter(boolean isSingleDistrict, boolean isAreaDiffSmall, List<Transaction> outerTransactionsList, List<Transaction> innerTransactionsList,Transaction userTransaction) {

        if (isSingleDistrict) {

            if (isAreaDiffSmall) {
                List<Transaction> singleDistrictFilter = singleDistrictFilter(outerTransactionsList, userTransaction);
                List<Transaction> flatAreafilter = flatAreaFilter(singleDistrictFilter, userTransaction, areaDiff);
                flatAreafilter = invalidTransactionsRemover(flatAreafilter);
                if (isEnoughtResults(flatAreafilter, minResultsNumber)) {
                    return flatAreafilter;
                } else {
                    return selectorFilter(true, false,outerTransactionsList, singleDistrictFilter, userTransaction);
                }

            } else {

                List<Transaction> areaExpanded = flatAreaFilter(innerTransactionsList, userTransaction, areaDiffExpanded);
                areaExpanded = invalidTransactionsRemover(areaExpanded);
                if (isEnoughtResults(areaExpanded, minResultsNumber)) {
                    return areaExpanded;
                } else {
                    return selectorFilter(false, true, outerTransactionsList, new ArrayList<>(), userTransaction);
                }

            }
        } else {

            if (isAreaDiffSmall) {
                List<Transaction> multiDistrictFilter = multiDistrictFilter(outerTransactionsList, userTransaction);
                List<Transaction> flatAreafilter = flatAreaFilter(multiDistrictFilter, userTransaction, areaDiff);
                flatAreafilter = invalidTransactionsRemover(flatAreafilter);

                if (isEnoughtResults(flatAreafilter, minResultsNumber)) {
                    return flatAreafilter;
                } else {
                    return selectorFilter(false, false, outerTransactionsList, multiDistrictFilter, userTransaction);
                }

            } else {

                List<Transaction> areaExpanded = flatAreaFilter(innerTransactionsList, userTransaction, areaDiffExpanded);
                areaExpanded = invalidTransactionsRemover(areaExpanded);
                if (isEnoughtResults(areaExpanded, minResultsNumber)) {
                    return areaExpanded;
                } else {
                    return notEnoughtResultsAction();
                }

            }
        }
    }


    public List<Transaction> singleDistrictFilter(List<Transaction> transactionsBase, Transaction userTransaction) {
        LOGGER.info("Single District filter aktywowany");
        List<Transaction> lista = transactionsBase.stream()
                .filter(transaction -> (transaction.getDistrict().trim()).equalsIgnoreCase(userTransaction.getDistrict().trim()))
                .collect(Collectors.toList());
        LOGGER.info("Po przefiltrowaniu SingleDistrictFilter uzyskano {} element/ów",lista.size());
        return lista;
    }


    private List<Transaction> multiDistrictFilter(List<Transaction> transactionsBase, Transaction userTransaction) {
        LOGGER.info("Multi District filter aktywowany");

        OptionalInt userDistrictWage = distrWagesHandler.lookForWage(userTransaction);
        if(!userDistrictWage.isPresent()){
            LOGGER.warn("{} {}" + "<--- ta dzielnica nie znajduje sie w bazie",userTransaction.getCity(),userTransaction.getDistrict());
            return new ArrayList<>();
        }

        List<Transaction> lista = transactionsBase.stream()
                .filter(transaction -> distrWagesHandler.multiDistrictWageComparator(transaction, userDistrictWage.getAsInt()))
                .peek(t-> System.out.println("dzielnica badana:___"+t.getDistrict()+"___"+"Dzielnica usera:___"+ userTransaction.getDistrict()))
                .collect(Collectors.toList());
        LOGGER.info("Po przefiltrowaniu MultiDistrictFilter uzyskano {} element/ów",lista.size());
        return lista;
    }

    public List<Transaction> flatAreaFilter(List<Transaction> transactionsBase, Transaction userTransaction, BigDecimal areaDiff) {
        LOGGER.info("Flat Area filter aktywowany");

        List<Transaction> lista = transactionsBase.stream()
                .filter(transaction -> ((transaction.getFlatArea()).compareTo(userTransaction.getFlatArea().add(areaDiff))) <= 0)
                .filter(transaction -> ((transaction.getFlatArea()).compareTo(userTransaction.getFlatArea().subtract(areaDiff))) >= 0)
                .collect(Collectors.toList());
        LOGGER.info("Po przefiltrowaniu flatAreaFilter uzyskano {} element/ów",lista.size());
        return lista;
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

        //removeLeftOutliers(transSortedByPPerM2, maxDiff);
        //removeRightOutliers(transSortedByPPerM2, maxDiff);

        BigDecimal sumPPM2 = transSortedByPPerM2.stream()
                .map(Transaction::getPricePerM2)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal avg = sumPPM2.divide(new BigDecimal(transSortedByPPerM2.size()), RoundingMode.HALF_UP);

        transSortedByPPerM2 = transSortedByPPerM2.stream()
                .filter(x -> x.getPricePerM2().compareTo(avg.multiply(BigDecimal.valueOf(0.7))) >= 0)
                .filter(x -> x.getPricePerM2().compareTo(avg.multiply(BigDecimal.valueOf(1.3))) <= 0)
                .collect(Collectors.toList());

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

}