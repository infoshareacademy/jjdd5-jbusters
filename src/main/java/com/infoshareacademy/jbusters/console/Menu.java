package com.infoshareacademy.jbusters.console;


import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Menu {
    //zawiera wszytskie odwoania do funkcji programu



    private ConsoleReader consoleReader = new ConsoleReader();
    public Menu() throws IOException {

    }

    public void newSearch(){

        System.out.println("Witaj! Tu wycenisz swoje mieszkanie w kilku szybkich krokach. " +
                "Zacznij od przygotowania podstawowych danych odno?nie swojego mieszkania");

    }

    public void saveSession(){

    }

    public void exit(){

    }

    public ConsoleReader getConsoleReader() {
        return consoleReader;
    }

}
