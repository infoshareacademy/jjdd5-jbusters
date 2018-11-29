package com.infoshareacademy.jbusters.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TestBasic {

    public static void main(String[] args) {
        Transaction userTrans = new Transaction();
        userTrans.setTransactionDate(LocalDate.now());
        userTrans.setCity("Gdynia");
        userTrans.setDistrict(" Chylonia");
        userTrans.setStreet("Morska");
        userTrans.setTypeOfMarket("RYNEK WTÓRNY");
        userTrans.setFlatArea(new BigDecimal(45.0));
        userTrans.setLevel(4);
        userTrans.setParkingSpot("BRAK MP");
        userTrans.setStandardLevel("DOBRY");
        userTrans.setConstructionYear("1980");
        userTrans.setConstructionYearCategory(2);
        userTrans.setPrice(new BigDecimal(0.0));
        userTrans.setPricePerM2(new BigDecimal(0.0));

        Data dataSet = new Data();


        FilterTransactions filter= new FilterTransactions(dataSet.getTransactionsBase());

       List<Transaction> basic =  filter.basicFilter(userTrans);
        System.out.println(basic.size());
       //basic.stream().forEach(System.out::println);
        List<Transaction> district = filter.singleDistrictFilter(basic,userTrans);

        List<Transaction> flatArea = filter.flatAreaFilter(district,userTrans, new BigDecimal("20.0"));
        //filter.removeOutliers(flatArea,new BigDecimal("600")).stream().forEach(System.out::println);

        //flatArea.forEach(System.out::println);
      List<Transaction> invalidFree = filter.invalidTransactionsRemover(flatArea);
        //invalidFree.forEach(System.out::println);



    }
}

