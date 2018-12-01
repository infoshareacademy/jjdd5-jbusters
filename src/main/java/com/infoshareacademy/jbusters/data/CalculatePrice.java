package com.infoshareacademy.jbusters.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalculatePrice {

    public BigDecimal calculatePrice(List<Transaction> filteredList) {
        List<Transaction> updatedPricesList = updatePricesInList(filteredList);

        return BigDecimal.valueOf(updatedPricesList.stream()
                .mapToDouble(transaction -> transaction.getPrice().doubleValue())
                .average().orElse(Double.valueOf(0)));
    }

    //TODO metoda nie powinna na stałe zmieniać cen transakcji
    private List<Transaction> updatePricesInList(List<Transaction> transactions) {
        Map<String, Long> mostPS = transactions.stream()
                .map(transaction -> transaction.getParkingSpot())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        String mostPopularParkingSpot = mostPS.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        System.out.println(mostPS);
        System.out.println(mostPopularParkingSpot);

        Map<String, Long> mostSL = transactions.stream()
                .map(transaction -> transaction.getStandardLevel())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        String mostPopularStandardLevel = mostSL.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        System.out.println(mostSL);
        System.out.println(mostPopularStandardLevel);

        List<Transaction> listToCalculateTrend = transactions.stream()
                .filter(transaction -> transaction.getStandardLevel().equalsIgnoreCase(mostPopularStandardLevel))
                .filter(transaction -> transaction.getParkingSpot().equalsIgnoreCase(mostPopularParkingSpot))
                .filter(transaction -> transaction.getLevel() != 1)
                .sorted((o1, o2) -> o1.getTransactionDate().compareTo(o2.getTransactionDate()))
                .collect(Collectors.toList());

        listToCalculateTrend.forEach(System.out::println);

        BigDecimal overallTrend = overallTrend(listToCalculateTrend);

        System.out.println("Roczny tręd wzrostu cen mieszkań wynosi: " + overallTrend.multiply(BigDecimal.valueOf(36500)) + "%");


        List<Transaction> exportList = transactions.stream()
                .map(item -> new Transaction(item))
                .collect(Collectors.toList());


        exportList.forEach(t -> t.setPrice(updatePrice(t, overallTrend)));

        return  exportList;
    }

    private BigDecimal updatePrice(Transaction transaction, BigDecimal trend) {
        long duration = DAYS.between(transaction.getTransactionDate(), LocalDate.now());
        return transaction.getPrice().multiply(BigDecimal.ONE.add(BigDecimal.valueOf(duration).multiply(trend))).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal trendPerDay(List<Transaction> listToCalculateTrend, int first, int last) {
        long duration = DAYS.between(listToCalculateTrend.get(first).getTransactionDate(), listToCalculateTrend.get(last).getTransactionDate());

        System.out.println(duration + " dni upłyneło między transakcjami, użytymi do obliczenia trędu");

        BigDecimal priceOfNewest = listToCalculateTrend.get(last).getPricePerM2();
        BigDecimal priceOfOldest = listToCalculateTrend.get(first).getPricePerM2();

        BigDecimal trendPerDay = (((priceOfNewest.subtract(priceOfOldest)).divide(priceOfOldest, 6, RoundingMode.HALF_UP)).divide(BigDecimal.valueOf(duration), 6, RoundingMode.HALF_UP));

        return trendPerDay;
    }

    private BigDecimal overallTrend(List<Transaction> listToCalculateTrend) {
        BigDecimal pairOne = trendPerDay(listToCalculateTrend, 0, listToCalculateTrend.size() - 1);
        BigDecimal pairTwo = trendPerDay(listToCalculateTrend, 1, listToCalculateTrend.size() - 2);
        BigDecimal pairThree = trendPerDay(listToCalculateTrend, 2, listToCalculateTrend.size() - 3);

        return (pairOne.add(pairTwo).add(pairThree)).divide(BigDecimal.valueOf(3), 4, RoundingMode.HALF_UP);
    }
}
