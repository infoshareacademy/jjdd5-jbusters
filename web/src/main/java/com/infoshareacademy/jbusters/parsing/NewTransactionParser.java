package com.infoshareacademy.jbusters.parsing;

import com.infoshareacademy.jbusters.model.NewTransaction;

import javax.faces.bean.RequestScoped;

@RequestScoped
public class NewTransactionParser {

    public String parseToCsv(NewTransaction newTransaction) {


        return String.join(",",
                newTransaction.getNewTransactionDataTransaction().toString().replaceAll("-", " "),
                newTransaction.getNewTransactionCity(),
                newTransaction.getNewTransactionDistrict(),
                newTransaction.getNewTransactionStreet(),
                newTransaction.getNewTransactionTypeOfMarket(),
                newTransaction.getNewTransactionPrice().toString(),
                newTransaction.getNewTransactionFlatArea().toString(),
                newTransaction.getNewTransactionPricePerM2().toString(),
                String.valueOf(newTransaction.getNewTransactionLevel()),
                newTransaction.getNewTransactionParkingSpot(),
                newTransaction.getNewTransactionStandardLevel(),
                newTransaction.getNewTransactionConstructionYear(),
                String.valueOf(newTransaction.getNewTransactionConstructionYearCategory()),
                newTransaction.getNewTransactionDescription(),
                String.valueOf(newTransaction.isNewTransactionImportant()));
    }
}

