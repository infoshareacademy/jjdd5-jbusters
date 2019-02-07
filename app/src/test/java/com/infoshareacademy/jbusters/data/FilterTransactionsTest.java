package com.infoshareacademy.jbusters.data;

//import org.junit.Assert;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


//@RunWith(MockitoJUnitRunner.class)
public class FilterTransactionsTest {



    private List<Transaction> tempTransactions = new ArrayList();

  // @Test
    public void removeOutliersTest() {

        //avg flat
        tempTransactions.add(createTransaction(new BigDecimal(6000*45)));

        //transactions around avg+-30%
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

        FilterTransactions ft = new FilterTransactions();
        tempTransactions=ft.removeOutliers(tempTransactions,new BigDecimal(600));

        Assertions.assertEquals(7,tempTransactions.size());


    }


//@Test
//    public void basicFilterTest(){
//        tempTransactions=new ArrayList();
//        LocalDate now = LocalDate.now();
//        tempTransactions.add(createTransactionByDate(now));
//        tempTransactions.add(createTransactionByDate(now.minusYears(2).plusDays(1)));
//        tempTransactions.add(createTransactionByDate(now.minusYears(2)));
//        tempTransactions.add(createTransactionByDate(now.minusYears(2).minusDays(1)));
//        tempTransactions.add(createTransactionByDate(now.minusYears(3)));
//        tempTransactions.add(createTransactionByDate(now.minusYears(4)));
//        tempTransactions.add(createTransactionByDate(now.minusYears(5)));
//        tempTransactions.add(createTransactionByDate(now.minusYears(6)));
//
//        FilterTransactions ft = new FilterTransactions();
//        ft.init();
//        tempTransactions = ft.theGreatFatFilter(createTransactionByDate(now));
//
//        Assertions.assertEquals(3,tempTransactions.size());
//
//
//    }


    private Transaction createTransaction(BigDecimal price){

        Transaction trans = new Transaction();
        trans.setCity("Gdynia");
        trans.setDistrict("Witomino");
        trans.setTransactionDate(LocalDate.of(2018, 6, 20));
        trans.setStreet("Dabrowkowska");
        trans.setTypeOfMarket("RYNEK WTÓRNY");
        trans.setPrice(price);
        trans.setFlatArea(new BigDecimal(45));
        trans.setPricePerM2(trans.getPrice().divide(trans.getFlatArea(), RoundingMode.HALF_UP));
        trans.setLevel(3);
        trans.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        trans.setStandardLevel(StandardLevel.DOBRY.getName());
        trans.setConstructionYear("1980");
        trans.setConstructionYearCategory(2);

        return trans;
    }
    private Transaction createTransactionByDate(LocalDate date){

        Transaction trans = new Transaction();
        trans.setCity("Gdynia");
        trans.setDistrict("Witomino");
        trans.setTransactionDate(date);
        trans.setStreet("Dabrowkowska");
        trans.setTypeOfMarket("RYNEK WTÓRNY");
        trans.setPrice(new BigDecimal(100000));
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