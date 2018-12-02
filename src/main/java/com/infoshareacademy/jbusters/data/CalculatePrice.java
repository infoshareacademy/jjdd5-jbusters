package com.infoshareacademy.jbusters.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalculatePrice {

    private Transaction userTransaction;
    private List<Transaction> filteredList;

    public CalculatePrice(Transaction transaction, List<Transaction> filteredList) {
        this.userTransaction = transaction;
        this.filteredList = filteredList;
    }

    private List<Transaction> updatePricesInList() {
        List<Transaction> transactions = filteredList;

        BigDecimal min = BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getPricePerM2().doubleValue())
                .min().orElse((double) 0));

        System.out.println("Cena minimalna przed aktualizacja o trend to: " + min);

        String mostPopularParkingSpot = mapOfParkingSpots(transactions).entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        String mostPopularStandardLevel = mapOfStandardLevel(transactions).entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        List<Transaction> listToCalculateTrend = transactions.stream()
                .filter(transaction -> transaction.getStandardLevel().equalsIgnoreCase(mostPopularStandardLevel))
                .filter(transaction -> transaction.getParkingSpot().equalsIgnoreCase(mostPopularParkingSpot))
                .filter(transaction -> transaction.getLevel() != 1)
                .sorted((o1, o2) -> o1.getTransactionDate().compareTo(o2.getTransactionDate()))
                .collect(Collectors.toList());

        BigDecimal overallTrend = overallTrend(listToCalculateTrend);

        System.out.println("Roczny tręd wzrostu cen mieszkań wynosi: " + overallTrend.multiply(BigDecimal.valueOf(36500)) + "%");

        List<Transaction> exportList = transactions.stream()
                .map(item -> new Transaction(item))
                .collect(Collectors.toList());

        exportList.forEach(t -> t.setPricePerM2(updatePrice(t, overallTrend)));

        return exportList;
    }

    private Map<String, Long> mapOfParkingSpots(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> transaction.getParkingSpot())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    }

    private Map<String, Long> mapOfStandardLevel(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> transaction.getStandardLevel())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    }


    private BigDecimal updatePrice(Transaction transaction, BigDecimal trend) {
        long duration = DAYS.between(transaction.getTransactionDate(), LocalDate.now());
        return transaction.getPricePerM2().multiply(BigDecimal.ONE.add(BigDecimal.valueOf(duration).multiply(trend))).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal trendPerDay(List<Transaction> listToCalculateTrend, int first, int last) {
        long duration = DAYS.between(listToCalculateTrend.get(first).getTransactionDate(), listToCalculateTrend.get(last).getTransactionDate());

        System.out.println(duration + " dni upłyneło między transakcjami, użytymi do obliczenia trędu");

        BigDecimal priceOfNewest = listToCalculateTrend.get(last).getPricePerM2();
        BigDecimal priceOfOldest = listToCalculateTrend.get(first).getPricePerM2();

        BigDecimal trendPerDay = (((priceOfNewest.subtract(priceOfOldest)).divide(priceOfOldest, 6, RoundingMode.HALF_UP)).divide(BigDecimal.valueOf(duration), 6, RoundingMode.HALF_UP));

        if (trendPerDay.compareTo(BigDecimal.valueOf(0.000274)) > 0) {
            return BigDecimal.valueOf(0.000274);
        } else {
            return trendPerDay;
        }
    }

    private BigDecimal overallTrend(List<Transaction> listToCalculateTrend) {
        BigDecimal pairOne = trendPerDay(listToCalculateTrend, 0, listToCalculateTrend.size() - 1);
        BigDecimal pairTwo = trendPerDay(listToCalculateTrend, 1, listToCalculateTrend.size() - 2);
        BigDecimal pairThree = trendPerDay(listToCalculateTrend, 2, listToCalculateTrend.size() - 3);

        return (pairOne.add(pairTwo).add(pairThree)).divide(BigDecimal.valueOf(3), 6, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePrice() {
        List<Transaction> transactions = updatePricesInList();

        BigDecimal average = BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getPricePerM2().doubleValue())
                .average().orElse((double) 0));

        BigDecimal min = BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getPricePerM2().doubleValue())
                .min().orElse((double) 0));

        BigDecimal max = BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getPricePerM2().doubleValue())
                .max().orElse((double) 0));

        System.out.println("Średnia cena dla zbioru podobnych mieszkań to " + average.setScale(2, RoundingMode.HALF_UP) + "zł za m2");
        System.out.println("Cena maksymalna dla dobranego zbioru to " + max + "zł za m2");
        System.out.println("Cena minimalna dla dobranego zbioru to " + min + "zł za m2");

        BigDecimal lowerFactor = min.divide(average, 3, RoundingMode.HALF_UP);
        BigDecimal upperFactor = max.divide(average, 3, RoundingMode.HALF_UP);

        BigDecimal weightOne;
        BigDecimal weightTwo;
        BigDecimal weightThree;
        BigDecimal weightFour;

        if (isBestStandardLevel(transactions, userTransaction) && mapOfStandardLevel(transactions).size() > 1) {
            weightOne = upperFactor.multiply(BigDecimal.valueOf(0.4));
        } else if (!isBestStandardLevel(transactions, userTransaction) && mapOfStandardLevel(transactions).size() > 1) {
            weightOne = lowerFactor.multiply(BigDecimal.valueOf(0.4));
        } else {
            weightOne = BigDecimal.valueOf(0.4);
        }

        if (isBestLevel(userTransaction)) {
            weightTwo = upperFactor.multiply(BigDecimal.valueOf(0.2));
        } else {
            weightTwo = lowerFactor.multiply(BigDecimal.valueOf(0.2));
        }

        if (isBestFlatArea(transactions, userTransaction)) {
            weightThree = upperFactor.multiply(BigDecimal.valueOf(0.2));
        } else {
            weightThree = lowerFactor.multiply(BigDecimal.valueOf(0.2));
        }

        if (isBestParkingPlace(transactions, userTransaction) && mapOfParkingSpots(transactions).size() > 1) {
            weightFour = upperFactor.multiply(BigDecimal.valueOf(0.2));
        } else if (!isBestParkingPlace(transactions, userTransaction) && mapOfParkingSpots(transactions).size() > 1) {
            weightFour = lowerFactor.multiply(BigDecimal.valueOf(0.2));
        } else {
            weightFour = BigDecimal.valueOf(0.2);
        }

        BigDecimal finalWeight = weightOne.add(weightTwo).add(weightThree).add(weightFour);

        System.out.println("Współczynnik wagi dla tego mieszkania to " + finalWeight);

        BigDecimal pricePerM2 = average.multiply(finalWeight);
        System.out.println("Obliczona cena za m2 to " + pricePerM2.setScale(2, RoundingMode.HALF_UP) + "zł");

        return pricePerM2;
    }

    private boolean isBestLevel(Transaction transToCheck) {
        return transToCheck.getLevel() > 1;
    }

    private boolean isBestFlatArea(List<Transaction> finallySortedList, Transaction transToCheck) {
        BigDecimal averageFlatArea = BigDecimal.valueOf(finallySortedList.stream()
                .mapToDouble(t -> t.getFlatArea().doubleValue())
                .average().orElse((double) 0));
        System.out.println("Średnia wielkość mieszkań dla dobranego zbioru to " + averageFlatArea.setScale(2, RoundingMode.HALF_UP) + "m2");
        return transToCheck.getFlatArea().compareTo(averageFlatArea) < 0;
    }

    private boolean isBestStandardLevel(List<Transaction> finallySortedList, Transaction transToCheck) {
        StandardLevel bestStandard = finallySortedList.stream()
                .limit(finallySortedList.size() - 1)
                .map(x -> StandardLevel.fromString(x.getStandardLevel()))
                .distinct().max(new StandardLevelComparator())
                .orElse(StandardLevel.WYSOKI);
        return StandardLevel.fromString(transToCheck.getStandardLevel()).compareTo(bestStandard) >= 0;
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
}
