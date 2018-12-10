package com.infoshareacademy.jbusters.data;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilterTransactionsTest {

    private List<Transaction> tempTransactions = new ArrayList<>();

    @Test
    public void removeOutliersTest() {
        for (int i = 0; i < 30; i++){

            tempTransactions.add(createTransaction(new BigDecimal(270000)));

    }
        tempTransactions.add(createTransaction(new BigDecimal(5400*45)));
        tempTransactions.add(createTransaction(new BigDecimal(6600*45)));
        tempTransactions.add(createTransaction(new BigDecimal(4000*45)));

        tempTransactions.add(createTransaction(new BigDecimal(4100*45)));
        tempTransactions.add(createTransaction(new BigDecimal(4200*45)));
        tempTransactions.add(createTransaction(new BigDecimal(4300*45)));
        tempTransactions.add(createTransaction(new BigDecimal(7700*45)));
        tempTransactions.add(createTransaction(new BigDecimal(7800*45)));
        tempTransactions.add(createTransaction(new BigDecimal(7900*45)));
        tempTransactions.add(createTransaction(new BigDecimal(8000*45)));

        FilterTransactions ft = new FilterTransactions(tempTransactions);
        tempTransactions=ft.removeOutliers(tempTransactions,new BigDecimal(600));

        Assert.assertEquals(36,tempTransactions.size());


    }

    public Transaction createTransaction(BigDecimal price){

        Transaction trans = new Transaction();
        trans.setCity("Gdynia");
        trans.setDistrict("Witomino");
        trans.setTransactionDate(LocalDate.of(2018, 06, 20));
        trans.setStreet("Dabrowkowska");
        trans.setTypeOfMarket("RYNEK WTÓRNY");
        trans.setPrice(price);
        trans.setFlatArea(new BigDecimal(45));
        trans.setPricePerM2(trans.getPrice().divide(trans.getFlatArea(),RoundingMode.HALF_UP));
        trans.setLevel(3);
        trans.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        trans.setStandardLevel(StandardLevel.DOBRY.getName());
        trans.setConstructionYear("1980");
        trans.setConstructionYearCategory(2);

        return trans;
    }


}