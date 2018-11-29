package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.data.PropLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

public class MenuOptions {

    private ConsoleReader consoleReader = new ConsoleReader();
    private PropLoader properties = new PropLoader("app.properties");

    private int decimalPlaces = properties.getDecimalPlaces();
    private String currency = properties.getCurrency();
    private BigDecimal exchangeRate = new BigDecimal(properties.getExchangeRate());

    void loadOptionsMenu() throws IOException {

        int menuChoice = 0;
        while (menuChoice != 2) {
            System.out.println("\n" + "          O P C J E" + "\n");
            System.out.println("Aktualna ilość miejsc dziesiętnych:\t" + decimalPlaces + "\n" +
                    "Aktualna waluta:\t\t\t" + currency + "\n" +
                    "Aktualny kurs:\t\t\t\t1 " + currency + " = " + exchangeRate + " PLN\n\n" +
                    "1 - Zmiana powyższych ustawień i zapis" + "\n" +
                    "2 - Powrót do menu głównego" + "\n" +
                    "podaj numer:");
            menuChoice = consoleReader.readInt(1, 2);
            optionsMenuSwitch(menuChoice);
        }
        System.exit(0);
    }


    private void optionsMenuSwitch(int Choice) throws IOException {
        switch (Choice) {
            case 1: {
                Properties properties = new Properties();
                FileWriter write = new FileWriter("app.properties");

                System.out.println("\n" + ":: Wybrano zmianę ustawień ::" + "\n");

                System.out.println("Podaj nową ilość miejsc dziesiętnych (cyfry od 0 do 2):");
                String decimalPlaces = consoleReader.readStringDecimalPlaces();

                properties.setProperty("decimalPlaces", decimalPlaces);

                System.out.println("Podaj nową walutę (3 duże litery):");
                String currency = consoleReader.readStringCurrency();
                properties.setProperty("currency", currency);

                System.out.println("Podaj nowy kurs ? PLN = 1 " + currency + " (format #.##):");
                String exchangeRate = consoleReader.readStringExchangeRate();
                properties.setProperty("exchangeRate", exchangeRate);

                properties.store(write, "Properties File");

                System.out.println("\n:: Nowe parametry zostały zapisane ::\n");

                MenuOptions viewChanges = new MenuOptions();
                viewChanges.loadOptionsMenu();

                break;
            }
            case 2: {
                System.out.println("\n" + ":: Wybrano powrót do menu głównego ::" + "\n");
                Menu menu = new Menu();
                menu.loadMenu();
                break;
            }
        }
    }
}
