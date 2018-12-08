package com.infoshareacademy.jbusters.data;


import com.infoshareacademy.jbusters.console.ConsoleReader;
import com.infoshareacademy.jbusters.console.ConsoleViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

public class NewTransactionCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewTransactionCreator.class);
    private Transaction newTransaction = new Transaction();
    private final Data data;
    private final ConsoleReader consoleReader;


    public NewTransactionCreator(Data data, ConsoleReader consoleReader) {
        this.data = data;
        this.consoleReader = consoleReader;
    }

    public Transaction loadNewTransaction() {
        newTransaction.setTransactionDate(LocalDate.now());
        newTransaction.setCity(loadCity(data.cityList(data.getTransactionsBase())));
        newTransaction.setDistrict(loadDistrict(data.districtList(data.getTransactionsBase(), newTransaction)));
        newTransaction.setStreet(loadStreet());
        newTransaction.setTypeOfMarket(loadMarket());
        newTransaction.setPrice(BigDecimal.valueOf(0));
        newTransaction.setFlatArea(loadSize());
        newTransaction.setPricePerM2(BigDecimal.valueOf(0));
        newTransaction.setLevel(loadLevel());
        newTransaction.setParkingSpot(loadParkingSpot());
        newTransaction.setStandardLevel(loadStandardLevel());
        newTransaction.setConstructionYearCategory(loadConstructionYearCategory());
        newTransaction.setTransactionName(loadTransactionName());

        LOGGER.info("Create new transaction: {}", newTransaction);
        ConsoleViewer.clearScreen();
        System.out.println(":: Podsumowanie wprowadzonych parametrów mieszkania ::\n");
        System.out.println(newTransaction);         // Wypisanie podanych przez urzytkownika danych w formie transakcji
        System.out.println("\n:: Wybierz z poniższego menu co chesz dalej zrobić? ::\n");
        return newTransaction;
    }

    private String loadDistrict(List<String> districtList) {
        int printLimit;
        MapSorter mapSorter = new MapSorter();
        System.out.println("\nPodaj w jakiej dzielnicy jest twoje mieszkanie");
        char[] charsArray = consoleReader.readString().toCharArray();                      // urzytkownik wpisuje dzielnice jako string i zamieniany jest na charArray
        Map<String, Integer> districtMap = new HashMap<>();
        compare(districtList, charsArray, districtMap);
        Map<String, Integer> sortedMap = mapSorter.sorter(districtMap);                     // sortowanie mapy wzgledem value od najwiekszego do najmniejszego
        List<String> sortedDistrictList = new ArrayList<>(sortedMap.keySet());

        if (sortedDistrictList.size() < 5) {
            printLimit = sortedDistrictList.size();
        } else {
            printLimit = 5;
        }
        for (int i = 0; i < printLimit; i++) {
            System.out.println(i + 1 + " - " + sortedDistrictList.get(i));                  // wypisanie na ekran 5 dzielnic z najwiekszym counterem
        }
        LOGGER.info("load district: {}");
        return sortedDistrictList.get(consoleReader.readInt(1, printLimit) - 1);
    }

    private String loadCity(List<String> cityList) {
        int printLimit;
        MapSorter mapSorter = new MapSorter();
        System.out.println("Podaj nazwę miasta, w którym mieszkasz");
        char[] charsArray = consoleReader.readString().toCharArray();
        Map<String, Integer> cityMap = new HashMap<>();
        compare(cityList, charsArray, cityMap);
        Map<String, Integer> sortedMap = mapSorter.sorter(cityMap);
        List<String> sortedCityList = new ArrayList<>(sortedMap.keySet());
        if (sortedCityList.size() < 5) {
            printLimit = sortedCityList.size();
        } else {
            printLimit = 5;
        }

        for (int i = 0; i < printLimit; i++) {
            System.out.println(i + 1 + " - " + sortedCityList.get(i));
        }
        LOGGER.info("load city");
        return sortedCityList.get(consoleReader.readInt(1, printLimit) - 1);
    }

    private void compare(List<String> districtList, char[] charsArray, Map<String, Integer> districtMap) {
        for (int i = 0; i < districtList.size(); i++) {
            Integer counter = 0;
            for (int j = 0; j < charsArray.length; j++) {
                if (districtList.get(i).indexOf(charsArray[j]) != -1) {
                    counter++;                                                 // pętla porównuje ile z podanych liter znajdire się w nazwie dzielnic
                }
                districtMap.put(districtList.get(i), counter);                  // tworzona jest mapa - dzielnica jako key, counter jako value
            }
        }
    }

    private String loadStreet() {
        System.out.println("\nPodaj nazwę ulicy");
        LOGGER.info("load street");
        return consoleReader.readString();
    }

    private String loadMarket() {
        System.out.println("\nPodaj rodzaj rynku" + "\n" + "1 - Rynek wtórny" + "\n" + "2 - Rynek pierwotny");
        String[] marketList = {"RYNEK WTÓRNY", "RYNEK PIERWOTNY"};
        LOGGER.info("load market");
        return marketList[consoleReader.readInt(1, 2) - 1];
    }

    private BigDecimal loadSize() {
        System.out.println("\nPodaj wielkość mieszkania w m2");
        LOGGER.info("load size");
        return consoleReader.readBigDecimal();
    }

    private String loadParkingSpot() {
        System.out.println("\nCzy do mieszkania jest przypisane miejsce postojowe?");
        String[] parkingSpotList = {"BRAK MP", "MIEJSCE NAZIEMNE", "GARAŻ JEDNOSTANOWISKOWY", "MIEJSCE POSTOJOWE W HALI GARAŻOWEJ"};
        for (int i = 0; i < parkingSpotList.length; i++) {
            System.out.println(i + 1 + " - " + parkingSpotList[i]);
        }
        LOGGER.info("load parking spot");
        return parkingSpotList[consoleReader.readInt(1, 4) - 1];
    }

    private int loadLevel() {
        System.out.println("\nPodaj piętro, na którym jest twoje mieszkanie");
        LOGGER.info("load level");
        return consoleReader.readInt();
    }

    private String loadStandardLevel() {
        System.out.println("\nPodaj poziom wykończenia twojego mieszkania");
        String[] standardLevelList = {"WYSOKI", "BARDZO DOBRY", "DOBRY", "PRZECIĘTNY", "NISKI", "DEWELOPERSKI/DO WYKOŃCZENIA"};
        for (int i = 0; i < standardLevelList.length; i++) {
            System.out.println(i + 1 + " - " + standardLevelList[i]);
        }
        LOGGER.info("load standard level");
        return standardLevelList[consoleReader.readInt(1, 6) - 1];
    }

    private int loadConstructionYearCategory() {
        System.out.println("\nPodaj rok budowy budynku, w którym znajduje się Twoje mieszkaie");
        System.out.println("1 - przed rokiem 1970" + "\n" + "2 - między rokiem 1970 a 1990" + "\n" + "3 - po roku 1990");
        LOGGER.info("load construction year category");
        return consoleReader.readInt(1, 3);
    }

    public void loadPrice() {
        System.out.println("\nPodaj wartość za jaką została sprzedana Twoja nieruchomość");
        newTransaction.setPrice(consoleReader.readBigDecimal());
    }

    public void calculatePPm2() {
        newTransaction.setPricePerM2(newTransaction.getPrice().divide(newTransaction.getFlatArea(), RoundingMode.HALF_UP));
    }

    public void loadConstructionYear() {
        System.out.println("\nWpisz rok budowy budynku, w którym znajduje się Twoja nieruchomość");
        newTransaction.setConstructionYear(consoleReader.readString());
    }

    public void loadTime() {
        ConsoleViewer.clearScreen();
        System.out.println(":: Wybrano opcję wpisania transakcji do bazy ::\n");
        System.out.println("Wpisz datę sprzedaży nieruchomości w formacie YYYY-MM-dd");
        try {
            newTransaction.setTransactionDate(LocalDate.parse(consoleReader.readDate()));
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("\nZły format daty!");
            loadTime();
        }
    }

    private String loadTransactionName() {
        System.out.println("Wpisz nazwę swojej nieruchomości, by móc ja łatwiej identyfikować");
        return consoleReader.readString();
    }

    public Transaction getNewTransaction() {
        return newTransaction;
    }

    public void setNewTransaction(Transaction newTransaction) {
        this.newTransaction = newTransaction;
    }
}
