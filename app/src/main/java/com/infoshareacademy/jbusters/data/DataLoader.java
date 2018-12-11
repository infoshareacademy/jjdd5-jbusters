package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);
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
    private static final int INDEX_TRANSACTION_NAME = 13;
    private static final String separator = ",";

    public List<Transaction> createTransactionList(List<String> listFileTransakcjeCSV, String fromUserFile) {

        PropLoader properties = new PropLoader("app/src/main/resources/app.properties");

        List<Transaction> listOfTransaction = new ArrayList<>();

        for (String rowList : listFileTransakcjeCSV) {

            List<String> listTransaction = Arrays.asList(rowList.split(separator));

            Transaction newRowOfTransactionList = new Transaction();

            newRowOfTransactionList.setCity(listTransaction.get(INDEX_CITY));
            newRowOfTransactionList.setDistrict(listTransaction.get(INDEX_DISTRICT).trim());
            newRowOfTransactionList.setStreet(listTransaction.get(INDEX_STREET));
            newRowOfTransactionList.setTypeOfMarket(listTransaction.get(INDEX_TYPE_OF_MARKET));
            newRowOfTransactionList.setParkingSpot(listTransaction.get(INDEX_PARKING_SPOT));
            newRowOfTransactionList.setStandardLevel(listTransaction.get(INDEX_STANDARD_LEVEL));
            newRowOfTransactionList.setConstructionYear(listTransaction.get(INDEX_CONSTRUCTION_YEAR));

            // convert String to LocalData
            String transactionDateString = listTransaction.get(INDEX_TRANSACTION_DATE).replaceAll("\uFEFF", "");
            DateTimeFormatter transactionDateFormat = DateTimeFormatter.ofPattern("yyyy MM dd");
            LocalDate transactionDate = LocalDate.parse(transactionDateString, transactionDateFormat);
            newRowOfTransactionList.setTransactionDate(transactionDate);

            // convert String to BigDecimal
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String price = listTransaction.get(INDEX_PRICE);
            newRowOfTransactionList.setPrice(new BigDecimal(price).setScale(properties.getDecimalPlaces(), BigDecimal.ROUND_UP));
            String flatArea = listTransaction.get(INDEX_FLAT_AREA);
            newRowOfTransactionList.setFlatArea(new BigDecimal(flatArea).setScale(properties.getDecimalPlaces(), BigDecimal.ROUND_UP));
            String pricePerM2 = listTransaction.get(INDEX_PRICE_PER_M2);
            newRowOfTransactionList.setPricePerM2(new BigDecimal(pricePerM2).setScale(properties.getDecimalPlaces(), BigDecimal.ROUND_UP));

            //convert String to int
            String levelString = listTransaction.get(INDEX_LEVEL);
            int level = Integer.valueOf(levelString);
            newRowOfTransactionList.setLevel(level);

            String constructionYearCategoryString = listTransaction.get(INDEX_CONSTRUCTION_YEAR_CATEGORY);
            int constructionYearCategory = Integer.valueOf(constructionYearCategoryString);
            newRowOfTransactionList.setConstructionYearCategory(constructionYearCategory);

            if (fromUserFile.equals("yes")) {
                newRowOfTransactionList.setTransactionName(listTransaction.get(INDEX_TRANSACTION_NAME));
            }

            listOfTransaction.add(newRowOfTransactionList);
        }
        LOGGER.info("Create list transaction. List size: {}", listOfTransaction.size() + " rows");
        return listOfTransaction;
    }

    public List<Transaction> createFlatsListFromFile(Path path, String fromUserFile) {
        try {
            return createTransactionList(Files.readAllLines(path), fromUserFile);
        } catch (IOException e) {
            System.out.println("Brak pliku z zapisanymi transakcjami użytkownika. Tego nie pomalujesz");
        }
        return new ArrayList<>();
    }
}
