package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.DataLoader;
import com.infoshareacademy.jbusters.data.NewTransactionCreator;
import com.infoshareacademy.jbusters.data.Transaction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu {

    private ConsoleReader consoleReader = new ConsoleReader();
    private Data data = new Data();
    private NewTransactionCreator newTransactionCreator = new NewTransactionCreator(data, consoleReader);

    public Menu() {

    }

    public void newSearch() {

    }

    public void welcome() {
        System.out.println("Witaj! Tu wycenisz swoje mieszkanie w kilku szybkich krokach." + "\n" + "\n" +
                "Wpisz odpowiedni numer by poruszać się po menu" + "\n");
    }

    public void loadMenu() throws FileNotFoundException {

        System.out.println("\n" + "          M E N U" + "\n");
        System.out.println("1 - Wyceń moje mieszkanie" + "\n" +
                "2 - Zapisz moje mieszkanie do pliku" + "\n" +
                "3 - Załaduj moje mieszkanie" + "\n" +
                "4 - Wpisz mieszkanie do bazy" + "\n" +
                "5 - Wyjście" + "\n" + "podaj numer...");
        int menuChoise = consoleReader.readInt(1, 5);

        switch (menuChoise) {
            case 1: {
                if (newTransactionCreator.getNewTransaction().getCity() == null) {
                    System.out.println("Będziesz poproszony o podanie kilku podstawowych informacji odnośnie twojego mieszkania" + "\n");
                    newTransactionCreator.loadNewTransaction();
                    loadMenu();
                } else {
                    // jesli mieszkanie bedzie zaladowane tu wstawi sie wywolanie metody wyliczenia wartosci
                    System.out.println("Już wpisałeś nowe mieszkanie, bądź załadowałeś z pliku");
                    System.out.println(newTransactionCreator.getNewTransaction().toString());
                    loadMenu();
                }
                break;
            }
            case 2: {
                try {
                    saveSession(newTransactionCreator.getNewTransaction());
                    loadMenu();
                } catch (java.lang.NullPointerException e) {
                    System.out.println("Błąd! Twoja transakcja jest pusta. Najpierw wprowadź transakcję by móc ją zapisać.");
                    loadMenu();
                }
                break;
            }
            case 3: {
                newTransactionCreator.setNewTransaction(loadTransaction());
                loadMenu();
                break;
            }
            case 4: {
                System.out.println("Under construction");
                loadMenu();
                break;
            }
            case 5: {
                exit();
                break;
            }
        }
    }

    public void saveSession(Transaction newTransaction) throws FileNotFoundException {

        String transactionString = Stream.of(
                newTransaction.getTransactionDate().toString().replaceAll("-", " "),
                newTransaction.getCity(),
                newTransaction.getDistrict(),
                newTransaction.getStreet(),
                newTransaction.getTypeOfMarket(),
                newTransaction.getPrice().toString(),
                newTransaction.getFlatArea().toString(),
                newTransaction.getPricePerM2().toString(),
                String.valueOf(newTransaction.getLevel()),
                newTransaction.getParkingSpot(),
                newTransaction.getStandardLevel(),
                newTransaction.getConstructionYear(),
                String.valueOf(newTransaction.getConstructionYearCategory()))
                .collect(Collectors.joining(","));

        PrintWriter fileWriter = new PrintWriter(new FileOutputStream(
                new File("test.txt"),
                true));
        fileWriter.append(transactionString + "\n");
        fileWriter.close();

        System.out.println(transactionString);
        System.out.println("Twoja transakcja została zapisana");
    }

    public Transaction loadTransaction() {
        Path pathToUserFile = Paths.get("test.txt");
        List<String> userFlasts = null;
        try {
            userFlasts = Files.readAllLines(pathToUserFile);
            DataLoader dataLoader = new DataLoader();
            List<Transaction> userList = dataLoader.createTransactionList(userFlasts);

            for (int i = 0; i < userList.size(); i++ ) {
                System.out.println("Mieszkanie nr " + (i+1) + " " + userList.get(i).toString());
            }
            System.out.println("Podaj nr mieszkańia, które chcesz załadować");
            int chosenFlat = consoleReader.readInt(1, userList.size());
            System.out.println("Twoje mieszknie zostało załadowane");

            return userList.get(chosenFlat-1);
        } catch (IOException e) {
            System.out.println("Error with loading data: ");
            e.printStackTrace();
        }
        return null;
    }

    public void exit() {
        System.out.println("Zapraszamy ponownie");
    }

    public ConsoleReader getConsoleReader() {
        return consoleReader;
    }

}
