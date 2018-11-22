package com.infoshareacademy.jbusters.console;


import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.NewTransactionCreator;
import com.infoshareacademy.jbusters.data.Transaction;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Menu menu = new Menu();
        Data data = new Data();
        ConsoleReader consoleReader = menu.getConsoleReader();
        NewTransactionCreator newTransactionCreator = new NewTransactionCreator(data, consoleReader);


        //////////////              PRINTUJE CALA BAZE DANYCH              /////////////////////
//        data.fileToData().subList(20, 50).forEach(System.out::println);

        /////////////            PYTA UZYKTOWNIKA O PODANIE JEGO MIESZKANIA              ///////////////
        Transaction newTransaction = newTransactionCreator.loadNewTransaction();

        //newTransactionCreator.loadNewTransaction();

    }
}
