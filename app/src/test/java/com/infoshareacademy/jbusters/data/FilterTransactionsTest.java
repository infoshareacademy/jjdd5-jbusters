package com.infoshareacademy.jbusters.data;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilterTransactionsTest {

    private List<Transaction> tempTransactions = new ArrayList<>();
private Transaction trans = new Transaction();
    @Test
    public void removeOutliersTest() {
        for (int i = 0; i < 30; i++){
            trans.setCity("Gdynia");
        trans.setDistrict("Witomino");
        trans.setTransactionDate(LocalDate.of(2018, 06, 20));
        trans.setStreet("Morska");
        trans.setTypeOfMarket("RYNEK WTÓRNY");
        trans.setPrice(BigDecimal.valueOf(Math.random() * (350000 - 10000) + 10000));
        trans.setFlatArea(BigDecimal.valueOf(Math.random() * (50 - 35) + 35));
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

        FilterTransactions ft =new FilterTransactions(tempTransactions);

        Assert.assertEquals(30, ft.removeOutliers(tempTransactions,BigDecimal.valueOf(600)).size());

    }


}