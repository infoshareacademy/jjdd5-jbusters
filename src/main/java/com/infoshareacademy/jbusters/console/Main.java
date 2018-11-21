package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.console.Menu;
import com.infoshareacademy.jbusters.data.Data;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Data data = new Data();
        Menu menu = new Menu();




        //      PRINTUJE CALA BAZE DANYCH

        data.fileToData().subList(20, 50).forEach(System.out::println);
        // PYTA UZYKTOWNIKA O PODANIE JEGO MIESZKANIA
//        com.infoshareacademy.jbusters.Transaction newTransaction = menu.loadNewTransaction();

    }
}
