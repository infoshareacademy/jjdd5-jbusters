package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu {

    private static final Logger LOGGER = LoggerFactory.getLogger(Menu.class);
    private ConsoleReader consoleReader = new ConsoleReader();
    private Data data = new Data();
    private NewTransactionCreator newTransactionCreator = new NewTransactionCreator(data, consoleReader);
    private MenuOptions subMenu = new MenuOptions();
    private FilterTransactions filterTransactions = new FilterTransactions();

    private DataLoader dataLoader;

    private Path pathToUserFile = Paths.get("data", "test.txt");
    private Path pathToFileTransactionCSV = Paths.get("data", "transaction.csv");
    private PropLoader properties = new PropLoader("app/app.properties");
    private DecimalFormat df = new DecimalFormat("###,###,###.##");
    private BigDecimal exchangeRate = properties.getExchangeRateBigDecimal();

    public Menu() {
        filterTransactions.setData(data);
        filterTransactions.init();
        this.dataLoader = new DataLoader();
    }

    void welcome() {
        ConsoleViewer.clearScreen();
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
                    "7 - Wyjście" + "\n\n" + "Podaj numer:");
            menuChoise = consoleReader.readInt(1, 7);
            menuSwitch(menuChoise);
        }
        exit();
    }

    private void menuSwitch(int Choise) throws IOException {
        switch (Choise) {
            case 1: {
                ConsoleViewer.clearScreen();
                System.out.println(":: Wybrano wprowadzenie nowego mieszkania ::\n");
                System.out.println("Podaj proszę kilka podstawowych informacji dotyczących twojego mieszkania:\n");
                newTransactionCreator.loadNewTransaction();
                break;
            }
            case 2: {
                calculatePrice();
                break;
            }
            case 3: {
                saveSession(newTransactionCreator.getNewTransaction(), pathToUserFile, true);
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
                ConsoleViewer.clearScreen();
                subMenu.loadOptionsMenu();
                break;
            }
        }
    }

    private void calculatePrice() {
        if (newTransactionCreator.getNewTransaction().getCity() == null) {
            ConsoleViewer.clearScreen();
            System.out.println(":: Wycena niemożliwa, najpierw wprowadź mieszkanie, które chcesz wycenić ::\n\n" +
                    "Mieszkanie możesz wprowadzić ręcznie, bądź wczytać z pliku, jeśli zostało wcześniej zapisane.\n");
        } else {
            List<Transaction> filteredList = filterTransactions.theGreatFatFilter(newTransactionCreator.getNewTransaction());
            if (filteredList.size() >= 11) {
                CalculatePrice calc = new CalculatePrice(newTransactionCreator.getNewTransaction(), filteredList);
                BigDecimal valueOfFlat = newTransactionCreator.getNewTransaction().getFlatArea().multiply(calc.calculatePrice());

                System.out.println("\nWycena: \n");
                System.out.println(newTransactionCreator.getNewTransaction().toStringNoPrice());
                System.out.println("\nWARTOŚĆ TWOJEGO MIESZKANIA: " + df.format(valueOfFlat.divide(exchangeRate, BigDecimal.ROUND_UP)) + " " + properties.getCurrency() + "\n");
            } else {
                System.out.println("Wybierz z poniższego menu co chcesz dalej zrobić?\n");
            }
        }
    }

    private void saveSession(Transaction newTransaction, Path pathToFile, boolean isUserFile) throws FileNotFoundException {
        try {
            if (Files.exists(pathToFile)) {
                if (checkIfFlatExist(dataLoader.createFlatsListFromFile(pathToFile, isUserFile))) {
                    ConsoleViewer.clearScreen();
                    System.out.println(":: Dodanie transakcji niemożliwe, baza już zawiera identyczny wpis ::\n");
                } else {
                    saveTransaction(newTransaction, pathToFile, isUserFile);
                }
            } else {
                saveTransaction(newTransaction, pathToFile, isUserFile);
            }
        } catch (Exception e) {
            ConsoleViewer.clearScreen();
            System.out.println(":: Błąd! Twoja transakcja jest pusta. Najpierw wprowadź transakcję by móc ją zapisać ::\n");
        }
    }

    private void loadTransaction() {

            if (!dataLoader.createFlatsListFromFile(pathToUserFile, true).isEmpty()) {

                List<Transaction> userList = dataLoader.createFlatsListFromFile(pathToUserFile, true);

                    if (!dataLoader.createFlatsListFromFile(pathToUserFile, true).isEmpty()) {
                        for (int i = 0; i < userList.size(); i++) {
                            System.out.println("\n:: MIESZKANIE NR " + (i + 1) + " " +
                                    userList.get(i).getTransactionName() +
                                    " ::::::::::::::::::::::::::::\n" + userList.get(i).toStringNoPrice());
                        }
                        System.out.println("\nPodaj nr mieszkania, które chcesz załadować");
                        int chosenFlat = consoleReader.readInt(1, userList.size());
                        ConsoleViewer.clearScreen();
                        newTransactionCreator.setNewTransaction(userList.get(chosenFlat - 1));
                        System.out.println(":: Mieskzanie nr " + chosenFlat + " o nazwie " +
                                newTransactionCreator.getNewTransaction().getTransactionName() + " zostało załadowane ::");
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

    public void saveTransaction(Transaction newTransaction, Path pathToFile, boolean isUserFile) throws IOException {
        String transactionName = "brak";
        if (isUserFile) {
            transactionName = newTransaction.getTransactionName();
        }
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


        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(String.valueOf(pathToFile), true), "UTF-8"));
        try {
            out.append(transactionString + "," + transactionName + "\n");
        } finally {
            out.close();
        }
        
        ConsoleViewer.clearScreen();
        System.out.println(":: Twoja transakcja została zapisana do pliku ::\n");
        System.out.println("Nowy wpis: " + newTransaction.toStringNoPrice());
        System.out.println("\nWybierz z poniższego menu co chcesz dalej zrobić?\n");
    }

    private void addSoldFlatToDataBase(Transaction newTransaction) throws FileNotFoundException {
        if (newTransactionCreator.getNewTransaction().getCity() == null) {
            ConsoleViewer.clearScreen();
            System.out.println(":: Wpisanie mieszkania do bazy niemożliwe, nie wprowadzono mieszkania ::\n");
            System.out.println("Wybierz opcję nr 1 z menu by wprowadzić parametry mieszkania\n");
        } else {
            newTransactionCreator.loadTime();
            newTransactionCreator.loadPrice();
            newTransactionCreator.calculatePPm2();
            newTransactionCreator.loadConstructionYear();
            saveSession(newTransaction, pathToFileTransactionCSV, false);
        }
    }

    private void exit() {
        ConsoleViewer.clearScreen();
        System.out.println(":: Zapraszamy ponownie ::\n");
    }
}
