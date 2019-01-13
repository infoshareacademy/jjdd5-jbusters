package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.console.ConsoleViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalculatePrice {

    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);

    private Transaction userTransaction;
    private List<Transaction> filteredList;
    private PropLoader properties;
    private BigDecimal exchangeRate;
    private ExchangeRatesManager exchangeRatesManager = new ExchangeRatesManager();
    private static final double MAX_TREND_RATE_PER_DAY = 0.000274;
    private static final double MIN_TREND_RATE_PER_DAY = -0.000274;

    public CalculatePrice(Transaction transaction, List<Transaction> filteredList) {
        this.userTransaction = transaction;
        this.filteredList = filteredList;

        properties = new PropLoader();
        try {
            properties = new PropLoader(StaticFields.getAppPropertiesURL().openStream());
            exchangeRate = properties.getExchangeRateBigDecimal();

        } catch (Exception e) {
            LOGGER.error("Missing properties file in path {}", StaticFields.getAppPropertiesURL().toString());
        }
    }

    private List<Transaction> updatePricesInList() {
        List<Transaction> transactions = filteredList;

        BigDecimal min = getMinimumPriceInList(transactions);

        ConsoleViewer.clearScreen();
        System.out.println(":: Wybrano wycenę mieszkania ::\n");
        System.out.println("Statystyki:\n");
        System.out.println("Cena minimalna przed aktualizacją o trend to:"+getTabs(3) + StaticFields.formatWithLongDF(min.divide(exchangeRate, BigDecimal.ROUND_UP)) + " " + properties.getCurrency());

        List<Transaction> listToCalculateTrend = getListToCalculateTrend(transactions);

        BigDecimal overallTrend = overallTrend(listToCalculateTrend);

        System.out.println("Roczny trend wzrostu cen mieszkań wynosi:"+getTabs(3) + StaticFields.formatWithShortDF(overallTrend.multiply(BigDecimal.valueOf(36500))) + "%");


        List<Transaction> exportList = transactions.stream()
                .map(item -> new Transaction(item))
                .collect(Collectors.toList());

        exportList.forEach(t -> t.setPricePerM2(updatePrice(t, overallTrend)));

        return exportList;
    }

    private List<Transaction> getListToCalculateTrend(List<Transaction> transactions) {
        String mostPopularParkingSpot = mapOfParkingSpots(transactions).entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        String mostPopularStandardLevel = mapOfStandardLevel(transactions).entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        return transactions.stream()
                .filter(transaction -> transaction.getStandardLevel().equalsIgnoreCase(mostPopularStandardLevel))
                .filter(transaction -> transaction.getParkingSpot().equalsIgnoreCase(mostPopularParkingSpot))
                .filter(transaction -> transaction.getLevel() != 1)
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .collect(Collectors.toList());
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
        if (duration < 1) {
            duration = 1;
        }

        LOGGER.info("{} day/days have passed between transactions used to calculate price change trend:\t",duration);
        BigDecimal priceOfNewest = listToCalculateTrend.get(last).getPricePerM2();
        BigDecimal priceOfOldest = listToCalculateTrend.get(first).getPricePerM2();

        BigDecimal trendPerDay = (((priceOfNewest.subtract(priceOfOldest)).divide(priceOfOldest, 6, RoundingMode.HALF_UP)).divide(BigDecimal.valueOf(duration), 6, RoundingMode.HALF_UP));

        if (trendPerDay.compareTo(BigDecimal.valueOf(MAX_TREND_RATE_PER_DAY)) > 0) {
            return BigDecimal.valueOf(MAX_TREND_RATE_PER_DAY);
        } else if (trendPerDay.compareTo(BigDecimal.valueOf(MIN_TREND_RATE_PER_DAY)) < 0) {
            return BigDecimal.valueOf(MIN_TREND_RATE_PER_DAY);
        } else {
            return trendPerDay;
        }
    }

    public BigDecimal overallTrend(List<Transaction> filteredList) {
        List<Transaction> listToCalculateTrend = getListToCalculateTrend(filteredList);

        BigDecimal pairOneTrend = trendPerDay(listToCalculateTrend, 0, listToCalculateTrend.size() - 1);
        BigDecimal pairTwoTrend = trendPerDay(listToCalculateTrend, 1, listToCalculateTrend.size() - 2);
        BigDecimal pairThreeTrend = trendPerDay(listToCalculateTrend, 2, listToCalculateTrend.size() - 3);

        return (pairOneTrend.add(pairTwoTrend).add(pairThreeTrend)).divide(BigDecimal.valueOf(3), 6, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePrice() {
        List<Transaction> transactions = updatePricesInList();

        BigDecimal average = getAvaragePriceInList(transactions);

        BigDecimal min = getMinimumPriceInList(transactions);

        BigDecimal max = getMaxPriceInList(transactions);

        String currencySuffix = properties.getCurrency() +" per m2";
        LOGGER.info("Average price calculated from similar flats set equals:"+getTabs(3)
                + StaticFields.formatWithLongDF(
                        average.setScale(2, RoundingMode.HALF_UP).divide(exchangeRate, BigDecimal.ROUND_UP))
                + " " + properties.getCurrency() + currencySuffix);
        LOGGER.info("Maximum prise in similar flats set equals:"+getTabs(3)
                + StaticFields.formatWithLongDF(max.divide(exchangeRate, BigDecimal.ROUND_UP))
                + " " + properties.getCurrency() + currencySuffix);
        System.out.println("Minimum prise in similar flats set equals:"+getTabs(4)
                + StaticFields.formatWithLongDF(min.divide(exchangeRate, BigDecimal.ROUND_UP))
                + " " + properties.getCurrency() + currencySuffix);

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

        LOGGER.info("Flat's wage coefficient equals:"+getTabs(3) + finalWeight);

        BigDecimal pricePerM2 = average.multiply(finalWeight);
        LOGGER.info("Calculated price per square meter equals:"+getTabs(5)
                + StaticFields.formatWithLongDF(
                        pricePerM2.setScale(2, RoundingMode.HALF_UP).divide(exchangeRate, BigDecimal.ROUND_UP))
                + " " + properties.getCurrency());

        return pricePerM2.divide(exchangeRatesManager.getExRate(), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMaxPriceInList(List<Transaction> transactions) {
        return BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getPricePerM2().doubleValue())
                .max().orElse((double) 0));
    }

    public BigDecimal getMinimumPriceInList(List<Transaction> transactions) {
        return BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getPricePerM2().doubleValue())
                .min().orElse((double) 0));
    }

    public BigDecimal getAvaragePriceInList(List<Transaction> transactions) {
        return BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getPricePerM2().doubleValue())
                .average().orElse((double) 0));
    }

    private boolean isBestLevel(Transaction transToCheck) {
        return transToCheck.getLevel() > 1;
    }

    private boolean isBestFlatArea(List<Transaction> finallySortedList, Transaction transToCheck) {
        BigDecimal averageFlatArea = BigDecimal.valueOf(finallySortedList.stream()
                .mapToDouble(t -> t.getFlatArea().doubleValue())
                .average().orElse((double) 0));
        System.out.println("Średnia wielkość mieszkań dla dobranego zbioru to:"+getTabs(2) + averageFlatArea.setScale(2, RoundingMode.HALF_UP) + " m2");
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

    private String getTabs(int numberOfTabs){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfTabs; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }
}
