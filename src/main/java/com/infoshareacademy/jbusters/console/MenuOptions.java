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
    private BigDecimal areaDiff = properties.getAreaDiff();
    private BigDecimal areaDiffExpanded = properties.getAreaDiffExpanded();
    private int minResults = properties.getMinResultsNumber();
    private BigDecimal priceDiff = properties.getPriceDiff();

    void loadOptionsMenu() throws IOException {

        int menuChoice = 0;
        while (menuChoice != 2) {
            System.out.println("\n" + "          O P C J E" + "\n");
            System.out.println("Aktualna ilość miejsc dziesiętnych:\t" + decimalPlaces + "\n" +
                    "Aktualna waluta:\t\t\t" + currency + "\n" +
                    "Aktualny kurs:\t\t\t\t1 " + currency + " = " + exchangeRate + " PLN\n" +
                    "Filtrowanie m2 - zakres podstawowy:\t" + areaDiff + "\n" +
                    "Filtrowanie m2 - zakres rozszerzony:\t" + areaDiffExpanded + "\n" +
                    "Aktualny parametr ilości wyników:\t" + minResults + "\n" +
                    "Aktualny parametr różnicy cen:\t\t" + priceDiff + "\n\n" +
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

                System.out.println("Podaj nowy zakres filtrowania m2 - zakres podstawowy:");
                String areaDiff = consoleReader.readStringExchangeRate();
                properties.setProperty("areaDiff", areaDiff);

                System.out.println("Podaj nowy zakres filtrowania m2 - zakres rozszerzony:");
                String areaDiffExpanded = consoleReader.readStringExchangeRate();
                properties.setProperty("areaDiffExpanded", areaDiffExpanded);

                System.out.println("Podaj nowy parametr ilości wyników:");
                String minResults = consoleReader.readStringExchangeRate();
                properties.setProperty("minResults", minResults);

                System.out.println("Podaj nowy parametr różnicy cen:");
                String priceDiff = consoleReader.readStringExchangeRate();
                properties.setProperty("priceDiff", priceDiff);

                properties.store(write, "Properties File");

                System.out.println("\n:: Nowe parametry zostały zapisane ::\n");

                MenuOptions viewChanges = new MenuOptions();
                viewChanges.loadOptionsMenu();

                break;
            }
            case 2: {
                System.out.println("\n" + ":: Wybrano powrót do menu głównego ::" + "\n");
                Menu menu = new Menu();
                ConsoleViewer.clearScreen();
                menu.loadMenu();
                break;
            }
        }
    }

}
