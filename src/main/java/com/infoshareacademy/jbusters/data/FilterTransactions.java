package com.infoshareacademy.jbusters.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilterTransactions {

    private List<Transaction> transactionsBase;
    BigDecimal areaDiff = new BigDecimal(20);
    BigDecimal areaDiffExpanded = new BigDecimal(25);
    int minResultsNumber = 11;
    BigDecimal priceDiff = new BigDecimal(600.0);

    public FilterTransactions(List<Transaction> transactionsData) {

        transactionsBase = transactionsData;
    }

    // metoda zwracajaca liste tranzakcji, ktora jest wynikiem wielokrotnego przefiltrowania gwnej bazy tranzakcji
    //kolejnosc filtrow:  data tranzakcji/miasto/dzielnica/rynek/kategoria budowy/powierzchnia mieszkania

    public List<Transaction> theGreatFatFilter(Transaction userTransaction) {
        List<Transaction> basicFilter = basicFilter(userTransaction);
        return selectorFilter(true, true, basicFilter, userTransaction);
    }


    private List<Transaction> notEnoughtResultsAction() {
        System.out.println("Baza zawiera zbyt mala ilosc pasujacych transakcji");
        return new ArrayList<Transaction>();
    }

    public List<Transaction> basicFilter(Transaction userTransaction) {
        List<Transaction> lista = transactionsBase.stream()
                .filter(transaction -> transaction.getTransactionDate().isAfter(userTransaction.getTransactionDate().minusYears(2)))
                .filter(transaction -> transaction.getCity().equalsIgnoreCase(userTransaction.getCity()))
                .filter(transaction -> transaction.getTypeOfMarket().equals(userTransaction.getTypeOfMarket()))
                .filter(transaction -> transaction.getConstructionYearCategory() == (userTransaction.getConstructionYearCategory()))
                .collect(Collectors.toList());

        return lista;
    }


    //TODO testy testy testy
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

                .filter(transaction -> transaction.getDistrict().equalsIgnoreCase(userTransaction.getDistrict()))
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

//TODO wstawic tutaj filtry konglomeraty karolo juanowe worstFlatFilter & bestFlatFilter co by dzialalo
    public List<Transaction> invalidTransactionsRemover(List<Transaction> finallySortedList) {
        finallySortedList = removeOutliers(finallySortedList, priceDiff);

/*
        mostExpensiveNotValidRemover(finallySortedList);
        cheapestNotValidRemover(finallySortedList);

*/

        return finallySortedList;
    }

    public List<Transaction> removeOutliers(List<Transaction> transToClear, BigDecimal maxDiff) {

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
/*
    private void cheapestNotValidRemover(List<Transaction> finallySortedList) {
        boolean isRemoved = true;
        while (isRemoved) {
            if (!isCheapestTransactionValid(finallySortedList)) {
                finallySortedList.remove(getLessExpensiveFlat(finallySortedList));
                isRemoved = true;
            } else {
                isRemoved = false;
            }
        }
    }

    private void mostExpensiveNotValidRemover(List<Transaction> finallySortedList) {
        while (true) {
            if (!isMostExpensiveTransactionValid(finallySortedList)) {
                finallySortedList.remove(getMostExpensiveFlat(finallySortedList));
            } else {
                break;
            }
        }
    }
//FIXME metody sprawdzajace isXXX zamienic na metody filtrujace

    private boolean isCheapestTransactionValid(List<Transaction> finallySortedList) {
        Transaction transToCheck = getLessExpensiveFlat(finallySortedList);

        return isWorstFlatArea(finallySortedList, transToCheck) &&
                isWorstLevel(finallySortedList, transToCheck) &&
                isWorstStandardLevel(finallySortedList, transToCheck) &&
                isWorstParkingPlace(finallySortedList, transToCheck);

    }

    private boolean isMostExpensiveTransactionValid(List<Transaction> finallySortedList) {
        Transaction transToCheck = getMostExpensiveFlat(finallySortedList);

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
                .skip(1)
                .map(x -> ParkingPlace.fromString(x.getParkingSpot().trim()))
                .distinct()
                .min(new ParkingPlaceComparator())
                .orElse(ParkingPlace.BRAK_MP);

        ParkingPlace transactionParking = ParkingPlace.fromString(transToCheck.getParkingSpot().trim());
        return transactionParking.compareTo(worstParking) <= 0;

    }

    private boolean isBestParkingPlace(List<Transaction> finallySortedList, Transaction transToCheck) {

        ParkingPlace bestParking = finallySortedList.stream()
                .limit(finallySortedList.size() - 1)
                .map(x -> ParkingPlace.fromString(x.getParkingSpot()))
                .distinct()
                .max(new ParkingPlaceComparator())
                .orElse(ParkingPlace.GARAZ);
        return ParkingPlace.fromString(transToCheck.getParkingSpot()).compareTo(bestParking) >= 0;

    }

    private boolean isWorstStandardLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        StandardLevel worstStandard = finallySortedList.stream()
                .skip(1)
                .map(x -> StandardLevel.fromString(x.getStandardLevel()))
                .distinct().min(new StandardLevelComparator())
                .orElse(StandardLevel.DEWELOPERSKI);
        return StandardLevel.fromString(transToCheck.getStandardLevel()).compareTo(worstStandard) <= 0;
    }

    private boolean isBestStandardLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        StandardLevel bestStandard = finallySortedList.stream()
                .limit(finallySortedList.size() - 1)
                .map(x -> StandardLevel.fromString(x.getStandardLevel()))
                .distinct().max(new StandardLevelComparator())
                .orElse(StandardLevel.WYSOKI);
        return StandardLevel.fromString(transToCheck.getStandardLevel()).compareTo(bestStandard) >= 0;
    }


    private boolean isWorstLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        return !doesContainGroundLevel(finallySortedList) || transToCheck.getLevel() == 1;
    }

    private boolean isBestLevel(List<Transaction> finallySortedList, Transaction transToCheck) {

        return !doesContainGroundLevel(finallySortedList) || transToCheck.getLevel() > 1;
    }

    private boolean doesContainGroundLevel(List<Transaction> finallySortedList) {
        return finallySortedList.stream()
                .limit(finallySortedList.size() - 1)
                .mapToInt(Transaction::getLevel)
                .anyMatch(level -> level == 1);
    }

    private List<Transaction> WorstFlatFilter(List<Transaction> finallySortedList, Transaction transToCheck) {
        Transaction worstByFlatArea = getWorstByFlatArea(finallySortedList, transToCheck);
        return finallySortedList.stream()

                .filter(transaction -> transaction.getFlatArea().compareTo(worstByFlatArea.getFlatArea()) <= 0 )
                .filter(transaction -> transaction.getPricePerM2().compareTo(worstByFlatArea.getPricePerM2()) >= 0)
                .sorted(Comparator.comparing(Transaction::getPricePerM2))
                .collect(Collectors.toList());
    }

    private Transaction getWorstByFlatArea(List<Transaction> finallySortedList, Transaction transToCheck) {
        return finallySortedList.stream()
                .filter(transaction -> transaction.getLevel() == 1)
                .sorted(((o1, o2) -> o2.getFlatArea().compareTo(o1.getFlatArea())))
                .collect(Collectors.toList()).get(0);

    }



    private List<Transaction> BestFlatFilter(List<Transaction> finallySortedList, Transaction transToCheck) {
        Transaction bestByFlatArea = getBestByFlatArea(finallySortedList, transToCheck);
        return finallySortedList.stream()
                .filter(transaction -> transaction.getFlatArea().compareTo(bestByFlatArea.getFlatArea()) >= 0 )
                .filter(transaction -> transaction.getPricePerM2().compareTo(bestByFlatArea.getPricePerM2()) <= 0)
                .sorted(Comparator.comparing(Transaction::getPricePerM2))
                .collect(Collectors.toList());
    }

    private Transaction getBestByFlatArea(List<Transaction> finallySortedList, Transaction transToCheck) {
        return finallySortedList.stream()
                .filter(transaction -> transaction.getLevel() > 1)
                .sorted(((o1, o2) -> o2.getFlatArea().compareTo(o1.getFlatArea())))
                .collect(Collectors.toList()).get(0);
    }*/

    private BigDecimal getPricePerMeter(List<Transaction> transactionsList, int index) {
        return transactionsList.get(index).getPricePerM2();
    }


    private boolean isEnoughtResults(List<Transaction> listToCheck, int minSize) {
        return listToCheck.size() >= minSize;
    }

    /*private boolean isWorstLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        int worstLevel = finallySortedList.stream()
                .skip(1)
                .mapToInt(Transaction::getLevel)
                .min()
                .orElse(0);
        return transToCheck.getLevel() <= worstLevel;
    }*/

/*    private boolean isBestLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        int worstLevel = finallySortedList.stream()
                .limit(finallySortedList.size() - 1)
                .mapToInt(Transaction::getLevel)
                .min()
                .orElse(0);
        return transToCheck.getLevel() > worstLevel;
    } */


/*    private boolean isWorstFlatArea(List<Transaction> finallySortedList, Transaction transToCheck) {
        BigDecimal worstFlatArea = finallySortedList.stream()
                .skip(1)
                .map(Transaction::getFlatArea)
                .distinct()
                .max(BigDecimal::compareTo)
                .orElse(new BigDecimal(0.0));
        return transToCheck.getFlatArea().compareTo(worstFlatArea) >= 0;
    }*/

    /*    private boolean isBestFlatArea(List<Transaction> finallySortedList, Transaction transToCheck) {
        BigDecimal worstFlatArea = finallySortedList.stream()
                .limit(finallySortedList.size() - 1)
                .map(Transaction::getFlatArea)
                .distinct()
                .min(BigDecimal::compareTo)
                .orElse(new BigDecimal(0.0));
        return transToCheck.getFlatArea().compareTo(worstFlatArea) <= 0;
    }*/
    //TODO:wpisac metode do wypisywania dzielnic
    public List<String> getDistrictsWithSameWeight(String inputDistrict) {
        return new ArrayList<String>();
    }


}
