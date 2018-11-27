package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.DataLoader;
import com.infoshareacademy.jbusters.data.NewTransactionCreator;
import com.infoshareacademy.jbusters.data.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu {

    private static final Logger LOGGER = LoggerFactory.getLogger(Menu.class);
    private ConsoleReader consoleReader = new ConsoleReader();
    private Data data = new Data();
    private NewTransactionCreator newTransactionCreator = new NewTransactionCreator(data, consoleReader);
    private MenuOptions subMenu = new MenuOptions();

    private DataLoader dataLoader;

    private Path pathToUserFile = Paths.get("src", "main", "resources", "test.txt");
    private Path pathToFileTransactionCSV = Paths.get("src", "main", "resources", "transaction.csv");


    public Menu() {
        this.dataLoader = new DataLoader();
    }

    void welcome() {
        System.out.println("Witaj! Tu wycenisz swoje mieszkanie w kilku szybkich krokach." + "\n" + "\n" +
                "Wpisz odpowiedni numer by poruszać się po menu" + "\n");
    }

    void loadMenu() throws IOException {

        int menuChoise = 0;
        while (menuChoise != 7) {
            System.out.println("\n" + "          M E N U" + "\n");
            System.out.println("1 - Wprowadź nowe mieszkanie" + "\n" +
                    "2 - Dokonaj wyceny" + "\n" +
                    "3 - Zapisz moje mieszkanie do pliku" + "\n" +
                    "4 - Załaduj moje mieszkanie" + "\n" +
                    "5 - Wpisz mieszkanie do bazy" + "\n" +
                    "6 - Opcje" + "\n" +
                    "7 - Wyjście" + "\n" + "podaj numer...");
            menuChoise = consoleReader.readInt(1, 7);
            menuSwitch(menuChoise);
        }
        exit();
    }

    private void menuSwitch(int Choise) throws IOException {
        switch (Choise) {
            case 1: {
                System.out.println("Będziesz poproszony o podanie kilku podstawowych informacji odnośnie twojego mieszkania" + "\n");
                newTransactionCreator.loadNewTransaction();
                break;
            }
            case 2: {
                calculatePrice();
                break;
            }
            case 3: {
                saveSession(newTransactionCreator.getNewTransaction(), pathToUserFile);
                break;
            }
            case 4: {
                loadTransaction();
                break;
            }
            case 5: {
                addSoldFlatToDataBase(newTransactionCreator.getNewTransaction());
                break;
            }
            case 6: {
                subMenu.loadOptionsMenu();
                break;
            }
        }
    }

    private void calculatePrice() {
        if (newTransactionCreator.getNewTransaction().getCity() == null) {
            System.out.println("Najpierw wprowadź mieszkanie, które chcesz wycenić." + "\n" +
                    "Możesz je wprowadzić ręcznie, bądz wczytać z pliku, jeśli zostało wcześniej zapisane.");
        } else {
            // TU WSTAWI SIE WYWOŁANIE FUNKCI WYCENY PODANEGO MIESZKANIA
            System.out.println("Dokonano wyceny twojego mieszkania: ");
            System.out.println(newTransactionCreator.getNewTransaction().toString());
            System.out.println("Wartość twojego mieszkania to - 0zł!");
        }
    }

    private void saveSession(Transaction newTransaction, Path pathToFile) throws FileNotFoundException {
        try {
            if (Files.exists(pathToFile)) {
                if (checkIfFlatExist(dataLoader.createFlatsListFromFile(pathToFile))) {
                    System.out.println("Już istnieje taka transakcja!");
                } else {
                    saveTransaction(newTransaction, pathToFile);
                }
            } else {
                saveTransaction(newTransaction, pathToFile);
            }
        } catch (java.lang.NullPointerException e) {
            System.out.println("Błąd! Twoja transakcja jest pusta. Najpierw wprowadź transakcję by móc ją zapisać.");
        }
    }

    private void loadTransaction() {

        if (!dataLoader.createFlatsListFromFile(pathToUserFile).isEmpty()) {

            List<Transaction> userList = dataLoader.createFlatsListFromFile(pathToUserFile);

            if (!dataLoader.createFlatsListFromFile(pathToUserFile).isEmpty()) {
                for (int i = 0; i < userList.size(); i++) {
                    System.out.println("Mieszkanie nr " + (i + 1) + " " + userList.get(i).toString());
                }
                System.out.println("Podaj nr mieszkańia, które chcesz załadować");
                int chosenFlat = consoleReader.readInt(1, userList.size());
                System.out.println("Twoje mieszknie zostało załadowane");
                newTransactionCreator.setNewTransaction(userList.get(chosenFlat - 1));
            }
        }
    }

    private boolean checkIfFlatExist(List<Transaction> userList) {
        for (int i = 0; i < userList.size(); i++) {
            if (isSame(newTransactionCreator.getNewTransaction(), userList, i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSame(Transaction userTransaction, List<Transaction> userList, int i) {
        return (userList.get(i).getCity().equalsIgnoreCase(userTransaction.getCity()) &&
                userList.get(i).getDistrict().equalsIgnoreCase(userTransaction.getDistrict()) &&
                userList.get(i).getStreet().equalsIgnoreCase(userTransaction.getStreet()) &&
                userList.get(i).getFlatArea().equals(userTransaction.getFlatArea()) &&
                userList.get(i).getConstructionYearCategory() == (userTransaction.getConstructionYearCategory()) &&
                userList.get(i).getParkingSpot().equalsIgnoreCase(userTransaction.getParkingSpot()) &&
                userList.get(i).getStandardLevel().equalsIgnoreCase(userTransaction.getStandardLevel()) &&
                userList.get(i).getTypeOfMarket().equalsIgnoreCase(userTransaction.getTypeOfMarket()));
    }

    private void saveTransaction(Transaction newTransaction, Path pathToFile) throws FileNotFoundException {
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
                new File(String.valueOf(pathToFile)),
                true));
        fileWriter.append(transactionString + "\n");
        fileWriter.close();

        System.out.println(transactionString);
        System.out.println("Twoja transakcja została zapisana");
    }

    private void addSoldFlatToDataBase(Transaction newTransaction) throws FileNotFoundException {
        if (newTransactionCreator.getNewTransaction().getCity() == null) {
            System.out.println("Nie wprowadzono mieszkania. Wybierz opcje nr 1 z menu by wprowadzic parametry mieszkania.");
        } else {
            newTransactionCreator.loadTime();
            newTransactionCreator.loadPrice();
            newTransactionCreator.calculatePPm2();
            newTransactionCreator.loadConstructionYear();
            saveSession(newTransaction, pathToFileTransactionCSV);
        }
    }

    private void exit() {
        System.out.println("Zapraszamy ponownie");
    }
}
