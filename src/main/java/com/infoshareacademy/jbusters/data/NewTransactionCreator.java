package com.infoshareacademy.jbusters.data;


import com.infoshareacademy.jbusters.console.ConsoleReader;
import com.infoshareacademy.jbusters.console.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        LOGGER.info("Create new transaction: {}", newTransaction);
        System.out.println(newTransaction);         // Wypisanie podanych przez urzytkownika danych w formie transakcji
        return newTransaction;
    }

    private String loadDistrict(List<String> districtList) {
        int printLimit;
        MapSorter mapSorter = new MapSorter();
        System.out.println("Podaj w jakiej dzielnicy jest twoje mieszkanie");
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
        LOGGER.info("load district: {}", sortedDistrictList.get(consoleReader.readInt(1, printLimit) - 1));
        return sortedDistrictList.get(consoleReader.readInt(1, printLimit) - 1);
    }

    public String loadCity(List<String> cityList) {
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
        System.out.println("Podaj nazwę ulicy");
        LOGGER.info("load street");
        return consoleReader.readString();
    }

    private String loadMarket() {
        System.out.println("Podaj rodzaj rynku" + "\n" + "1 - Rynek wtórny" + "\n" + "2 - Rynek pierwotny");
        String[] marketList = {"RYNEK WTÓRNY", "RYNEK PIERWOTNY"};
        LOGGER.info("load market");
        return marketList[consoleReader.readInt(1, 2) - 1];
    }

    private BigDecimal loadSize() {
        System.out.println("Podaj wielkość mieszkania w m2");
        LOGGER.info("load size");
        return consoleReader.readBigDecimal();
    }

    private String loadParkingSpot() {
        System.out.println("Czy do mieszkania jest przypisane miejsce postojowe?");
        String[] parkingSpotList = {"BRAK MP", "MIEJSCE NAZIEMNE", "GARAŻ JEDNOSTANOWISKOWY", "MIEJSCE POSTOJOWE W HALI GARAŻOWEJ"};
        for (int i = 0; i < parkingSpotList.length; i++) {
            System.out.println(i + 1 + " - " + parkingSpotList[i]);
        }
        LOGGER.info("load parking spot");
        return parkingSpotList[consoleReader.readInt(1, 4) - 1];
    }

    private int loadLevel() {
        System.out.println("Podaj piętro, na którym jest twoje mieszkanie");
        LOGGER.info("load level");
        return consoleReader.readInt();
    }

    private String loadStandardLevel() {
        System.out.println("Podaj poziom wykończenia twojego mieszkania");
        String[] standardLevelList = {"WYSOKI", "BARDZO DOBRY", "DOBRY", "PRZECIĘTNY", "NISKI", "DEWELOPERSKI/DO WYKOŃCZENIA"};
        for (int i = 0; i < standardLevelList.length; i++) {
            System.out.println(i + 1 + " - " + standardLevelList[i]);
        }
        LOGGER.info("load standard level");
        return standardLevelList[consoleReader.readInt(1, 6) - 1];
    }

    private int loadConstructionYearCategory() {
        System.out.println("Podaj rok budowy budynku, w którym jest twoje mieszkaie");
        System.out.println("1 - przed rokiem 1970" + "\n" + "2 - między rokiem 1970 a 1990" + "\n" + "3 - po roku 1990");
        LOGGER.info("load construction year category");
        return consoleReader.readInt(1, 3);
    }

    public Transaction getNewTransaction() {
        return newTransaction;
    }

    public void setNewTransaction(Transaction newTransaction) {
        this.newTransaction = newTransaction;
    }
}
