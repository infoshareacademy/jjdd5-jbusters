package com.infoshareacademy.jbusters.data;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilterTransactionsTest {


    @Test
    public void removeOutliersTest() {
        List<Transaction> tempTransactions = new ArrayList<>();
        Transaction trans = new Transaction();

        for (int i = 0; i < 30; i++) {
            double price = Math.random() * (350000 - 10000) + 10000;
            double area = Math.random() * (50 - 35) + 35;
            trans.setCity("Gdynia");
            trans.setDistrict("Witomino");
            trans.setTransactionDate(LocalDate.of(2018, 6, 20));
            trans.setStreet("Morska");
            trans.setTypeOfMarket("RYNEK WTÓRNY");
            trans.setPrice(BigDecimal.valueOf(price));
            trans.setFlatArea(BigDecimal.valueOf(area));
            trans.setPricePerM2(trans.getPrice().divide(trans.getFlatArea(), RoundingMode.HALF_UP));
            trans.setLevel(3);
            trans.setParkingSpot(ParkingPlace.BRAK_MP.getName());
            trans.setStandardLevel(StandardLevel.DOBRY.getName());
            trans.setConstructionYear("1980");
            trans.setConstructionYearCategory(2);
            tempTransactions.add(trans);
        }

        trans.setCity("Gdynia");
        trans.setDistrict("Witomino");
        trans.setTransactionDate(LocalDate.of(2018, 06, 20));
        trans.setStreet("Morska");
        trans.setTypeOfMarket("RYNEK WTÓRNY");
        trans.setPrice(new BigDecimal(200));
        trans.setFlatArea(new BigDecimal(40));
        trans.setPricePerM2(trans.getPrice().divide(trans.getFlatArea()));
        trans.setLevel(3);
        trans.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        trans.setStandardLevel(StandardLevel.DOBRY.getName());
        trans.setConstructionYear("1980");
        trans.setConstructionYearCategory(2);
        tempTransactions.add(trans);
        System.out.println(trans);

        trans.setCity("Gdynia");
        trans.setDistrict("Witomino");
        trans.setTransactionDate(LocalDate.of(2018, 06, 20));
        trans.setStreet("Morska");
        trans.setTypeOfMarket("RYNEK WTÓRNY");
        trans.setPrice(new BigDecimal(10000000));
        trans.setFlatArea(new BigDecimal(40));
        trans.setPricePerM2(trans.getPrice().divide(trans.getFlatArea()));
        trans.setLevel(3);
        trans.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        trans.setStandardLevel(StandardLevel.DOBRY.getName());
        trans.setConstructionYear("1980");
        trans.setConstructionYearCategory(2);
        tempTransactions.add(trans);

        FilterTransactions ft = new FilterTransactions(tempTransactions);

        Assert.assertEquals(30, ft.removeOutliers(tempTransactions, BigDecimal.valueOf(600)).size());

    }


}