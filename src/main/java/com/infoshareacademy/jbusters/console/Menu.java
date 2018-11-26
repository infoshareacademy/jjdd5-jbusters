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

    private Path pathToUserFile = Paths.get("test.txt");


    public void welcome() {
        System.out.println("Witaj! Tu wycenisz swoje mieszkanie w kilku szybkich krokach." + "\n" + "\n" +
                "Wpisz odpowiedni numer by poruszać się po menu" + "\n");
    }

    public void loadMenu() throws FileNotFoundException {
        LOGGER.info("Loading menu");
        System.out.println("\n" + "          M E N U" + "\n");
        System.out.println("1 - Wprowadź nowe mieszkanie" + "\n" +
                "2 - Dokonaj wyceny" + "\n" +
                "3 - Zapisz moje mieszkanie do pliku" + "\n" +
                "4 - Załaduj moje mieszkanie" + "\n" +
                "5 - Wpisz mieszkanie do bazy" + "\n" +
                "6 - Wyjście" + "\n" + "podaj numer...");
        int menuChoise = consoleReader.readInt(1, 6);
        LOGGER.info("User chose menu nr: "+menuChoise);
        switch (menuChoise) {
            case 1: {
                System.out.println("Będziesz poproszony o podanie kilku podstawowych informacji odnośnie twojego mieszkania" + "\n");
                newTransactionCreator.loadNewTransaction();
                loadMenu();
                break;
            }
            case 2: {
                if (newTransactionCreator.getNewTransaction().getCity() == null) {
                    System.out.println("Najpierw wprowadź mieszkanie, które chcesz wycenić." + "\n" +
                            "Możesz je wprowadzić ręcznie, bądz wczytać z pliku, jeśli zostało wcześniej zapisane.");
                    loadMenu();
                } else {
                    // TU WSTAWI SIE WYWOŁANIE FUNKCI WYCENY PODANEGO MIESZKANIA
                    System.out.println("Dokonano wyceny twojego mieszkania: ");
                    System.out.println(newTransactionCreator.getNewTransaction().toString());
                    System.out.println("Wartość twojego mieszkania to - 0zł!");
                    loadMenu();
                }
                break;
            }
            case 3: {
                try {
                    saveSession(newTransactionCreator.getNewTransaction());
                    loadMenu();
                } catch (java.lang.NullPointerException e) {
                    System.out.println("Błąd! Twoja transakcja jest pusta. Najpierw wprowadź transakcję by móc ją zapisać.");
                    LOGGER.warn("No transaction to write");
                    loadMenu();
                }
                break;
            }
            case 4: {
                if (createFlatsListFromFile() != null) {
                    newTransactionCreator.setNewTransaction(loadTransaction());
                }
                loadMenu();
                break;
            }
            case 5: {
                System.out.println("Under construction");
                loadMenu();
                break;
            }
            case 6: {
                LOGGER.info("Exit program");
                exit();
                break;
            }
        }
    }

    public void saveSession(Transaction newTransaction) throws FileNotFoundException {
        if (Files.exists(getPathToUserFile())) {
            if (checkIfFlatExist(createFlatsListFromFile())) {
                System.out.println("Już istnieje taka transakcja!");
            } else {
                saveTransaction(newTransaction);
            }
        } else {
            saveTransaction(newTransaction);
        }
    }

    public Transaction loadTransaction() {
        List<Transaction> userList = createFlatsListFromFile();

        if (userList != null) {
            for (int i = 0; i < userList.size(); i++) {
                System.out.println("Mieszkanie nr " + (i + 1) + " " + userList.get(i).toString());
            }
            System.out.println("Podaj nr mieszkańia, które chcesz załadować");
            int chosenFlat = consoleReader.readInt(1, userList.size());
            LOGGER.info("Load transaction: "+userList);
            System.out.println("Twoje mieszknie zostało załadowane");
            return userList.get(chosenFlat - 1);
        }
        return null;
    }

    public boolean checkIfFlatExist(List<Transaction> userList) {
        for (int i = 0; i < userList.size(); i++) {
            if (
                    userList.get(i).getCity().equals(newTransactionCreator.getNewTransaction().getCity()) &&
                            userList.get(i).getDistrict().equals(newTransactionCreator.getNewTransaction().getDistrict()) &&
                            userList.get(i).getStreet().equals(newTransactionCreator.getNewTransaction().getStreet()) &&
                            userList.get(i).getFlatArea().equals(newTransactionCreator.getNewTransaction().getFlatArea()) &&
                            userList.get(i).getConstructionYearCategory() == (newTransactionCreator.getNewTransaction().getConstructionYearCategory()) &&
                            userList.get(i).getParkingSpot().equals(newTransactionCreator.getNewTransaction().getParkingSpot()) &&
                            userList.get(i).getStandardLevel().equals(newTransactionCreator.getNewTransaction().getStandardLevel()) &&
                            userList.get(i).getTypeOfMarket().equals(newTransactionCreator.getNewTransaction().getTypeOfMarket())) {
                return true;
            }
        }
        return false;
    }

    public void saveTransaction(Transaction newTransaction) throws FileNotFoundException {
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
        LOGGER.info("Save transaction to file.txt: "+ newTransaction);
        System.out.println(transactionString);
        System.out.println("Twoja transakcja została zapisana");
    }

    public List<Transaction> createFlatsListFromFile() {
        List<String> userFlats;
        List<Transaction> userList = null;
        try {
            userFlats = Files.readAllLines(getPathToUserFile());
            DataLoader dataLoader = new DataLoader();
            userList = dataLoader.createTransactionList(userFlats);
        } catch (IOException e) {
            System.out.println("Brak pliku z zapisanymi transakcjami użytkownika. Tego nie pomalujesz");
        }
        return userList;
    }

    public void exit() {
        System.out.println("Zapraszamy ponownie");
    }

    public ConsoleReader getConsoleReader() {
        return consoleReader;
    }

    public Path getPathToUserFile() {
        return pathToUserFile;
    }
}
