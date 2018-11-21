package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataLoader {

    private static final int INDEX_TRANSACTION_DATE = 0;
    private static final int INDEX_CITY = 1;
    private static final int INDEX_DISTRICT = 2;
    private static final int INDEX_STREET = 3;
    private static final int INDEX_TYPE_OF_MARKET = 4;
    private static final int INDEX_PRICE = 5;
    private static final int INDEX_FLAT_AREA = 6;
    private static final int INDEX_PRICE_PER_M2 = 7;
    private static final int INDEX_LEVEL = 8;
    private static final int INDEX_PARKING_SPOT = 9;
    private static final int INDEX_STANDARD_LEVEL = 10;
    private static final int INDEX_CONSTRUCTION_YEAR = 11;
    private static final int INDEX_CONSTRUCTION_YEAR_CATEGORY = 12;

    public List<Transaction> createTransactionList(List<String> listFileTransakcjeCSV) {

        List<Transaction> listOfTransaction = new ArrayList<>();

        for (String rowList : listFileTransakcjeCSV) {

            List<String> listTransaction = Arrays.asList(rowList.split(","));

            Transaction newRowOfTransactionList = new Transaction();

            newRowOfTransactionList.setCity(listTransaction.get(INDEX_CITY));
            newRowOfTransactionList.setDistrict(listTransaction.get(INDEX_DISTRICT));
            newRowOfTransactionList.setStreet(listTransaction.get(INDEX_STREET));
            newRowOfTransactionList.setTypeOfMarket(listTransaction.get(INDEX_TYPE_OF_MARKET));
            newRowOfTransactionList.setParkingSpot(listTransaction.get(INDEX_PARKING_SPOT));
            newRowOfTransactionList.setStandardLevel(listTransaction.get(INDEX_STANDARD_LEVEL));
            newRowOfTransactionList.setConstructionYear(listTransaction.get(INDEX_CONSTRUCTION_YEAR));

            // convert String to LocalData
            String transactionDateString = listTransaction.get(INDEX_TRANSACTION_DATE);
            DateTimeFormatter transactionDateFormat = DateTimeFormatter.ofPattern("yyyy MM dd");
            LocalDate transactionDate = LocalDate.parse(transactionDateString, transactionDateFormat);
            newRowOfTransactionList.setTransactionDate(transactionDate);

            // convert String to BigDecimal
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String price = listTransaction.get(INDEX_PRICE);
            newRowOfTransactionList.setPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_UP));
            String flatArea = listTransaction.get(INDEX_FLAT_AREA);
            newRowOfTransactionList.setFlatArea(new BigDecimal(flatArea).setScale(2, BigDecimal.ROUND_UP));
            String pricePerM2 = listTransaction.get(INDEX_PRICE_PER_M2);
            newRowOfTransactionList.setPricePerM2(new BigDecimal(pricePerM2).setScale(2, BigDecimal.ROUND_UP));

            //convert String to int
            String levelString = listTransaction.get(INDEX_LEVEL);
            int level = Integer.valueOf(levelString);
            newRowOfTransactionList.setLevel(level);

            String constructionYearCategoryString = listTransaction.get(INDEX_CONSTRUCTION_YEAR_CATEGORY);
            int constructionYearCategory = Integer.valueOf(constructionYearCategoryString);
            newRowOfTransactionList.setConstructionYearCategory(constructionYearCategory);

            listOfTransaction.add(newRowOfTransactionList);
        }
        return listOfTransaction;
    }
}
