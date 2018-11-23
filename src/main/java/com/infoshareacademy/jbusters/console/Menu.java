package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.NewTransactionCreator;
import com.infoshareacademy.jbusters.data.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu {
    //zawiera wszytskie odwoania do funkcji programu

    private ConsoleReader consoleReader = new ConsoleReader();
    private Data data = new Data();
    private NewTransactionCreator newTransactionCreator = new NewTransactionCreator(data, consoleReader);


    public Menu() throws IOException {

    }

    public void newSearch() {

    }

    public void welcome() {
        System.out.println("Witaj! Tu wycenisz swoje mieszkanie w kilku szybkich krokach." + "\n" +
                "Wpisz odpowiedni numer by poruszac sie po menu" + "\n");
    }

    public void startProgram() throws FileNotFoundException {

        System.out.println("MENU"+ "\n");
        System.out.println("1 - Wycen moje mieszkanie" +"\n" +
                            "2 - Zapisz moje mieszkanie do pliku" + "\n" +
                            "3 - Zaladuj moje mieszkanie" + "\n" +
                            "4 - Wpisz mieszkanie do bazy" + "\n" +
                            "5 - Wyjscie"+ "\n" + "podaj numer...");
        int menuChoise = consoleReader.readInt(1,5);

        switch (menuChoise) {
            case 1: {
                System.out.println("Bedziesz poproszony o podanie kilku podstawowych informacji odnosnie twojego mieszkania" + "\n");
                newTransactionCreator.loadNewTransaction();
            }
            case 2: {
                System.out.println("Under construction");
                saveSession(newTransactionCreator.getNewTransaction());
                startProgram();
                break;
            }
            case 3: {
                System.out.println("Under construction");
                startProgram();
                break;
            }
            case 4: {
                System.out.println("Under construction");
                startProgram();
                break;
            }
            case 5: {
                System.out.println("Zapraszamy ponownie");
                break;
            }
        }
    }


    public void saveSession(Transaction newTransaction) throws FileNotFoundException {
//        Transaction newTransaction = newTransactionCreator.getNewTransaction();

        String transactionString = Stream.of(
                newTransaction.getTransactionDate().toString(),
                newTransaction.getCity(),
                newTransaction.getDistrict(),
                newTransaction.getStreet(),
                newTransaction.getTypeOfMarket(),
                newTransaction.getPrice().toString(),
                newTransaction.getFlatArea().toString(),
                newTransaction.getPricePerM2().toString(),
                String.valueOf(newTransaction.getLevel()) ,
                newTransaction.getParkingSpot(),
                newTransaction.getStandardLevel(),
                newTransaction.getConstructionYear(),
                String.valueOf(newTransaction.getConstructionYearCategory()))
        .collect(Collectors.joining(","));

        PrintWriter fileWriter = new PrintWriter(new File("test.txt"));
        fileWriter.append(transactionString);
        fileWriter.close();

        System.out.println(transactionString);
    }

    public void exit() {

    }

    public ConsoleReader getConsoleReader() {
        return consoleReader;
    }

}
