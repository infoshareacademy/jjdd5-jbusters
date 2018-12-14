package com.infoshareacademy.jbusters.data;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class StatisticsManager {

    private static final Path PATH_TO_STATISTICS_FILE = Paths.get(System.getProperty("jboss.home.dir"), "data", "statistics.txt");
    private static final String SEPARATOR = ",";

    public StatisticsManager() {
    }

    public void captureNameFromServlet(String cityName, String districtName, String value) throws IOException {
        if (Objects.nonNull(value) && Double.parseDouble(value) > 0) {
            addOrUpdateStatistics(cityName, districtName, value);
        }
    }

    private void addOrUpdateStatistics(String cityName, String districtName, String value) throws IOException {
        if (!Files.exists(PATH_TO_STATISTICS_FILE)) {
            Files.createFile(PATH_TO_STATISTICS_FILE);
        }

        List<Statistics> existingList = generateStatisticsList();

        int counterIncrement;
        double valueUpdate;
        boolean shouldAddNewLine = true;

        for (int i = 0; i < existingList.size(); i++) {
            if (existingList.get(i).getCityName().equals(cityName) &&
                    existingList.get(i).getDistrictName().equals(districtName)) {

                counterIncrement = existingList.get(i).getCounter() + 1;
                String counterString = String.valueOf(counterIncrement);

                valueUpdate = existingList.get(i).getAverageValue();
                valueUpdate = (valueUpdate + Double.parseDouble(value)) / 2;
                String valueString = String.valueOf(valueUpdate);

                overwriteExistingLine(i, cityName + SEPARATOR + districtName + SEPARATOR + counterString + SEPARATOR + valueString);

                shouldAddNewLine = false;
            }
        }
        if (shouldAddNewLine) {
            addNewLine(cityName, districtName, value);
        }
    }

    private void addNewLine(String cityName, String districtName, String value) throws IOException {

        String statisticsString = cityName + SEPARATOR + districtName + ",1," + value + System.lineSeparator();

        Files.write(Paths.get(String.valueOf(PATH_TO_STATISTICS_FILE)), statisticsString.getBytes(), StandardOpenOption.APPEND);
    }

    private void overwriteExistingLine(int lineNumber, String lineData) throws IOException {

        Path path = PATH_TO_STATISTICS_FILE;

        List<String> existingLine = Files.readAllLines(path, StandardCharsets.UTF_8);

        existingLine.set(lineNumber, lineData);

        Files.write(path, existingLine, StandardCharsets.UTF_8);
    }

    private List<Statistics> generateStatisticsList() throws IOException {

        List<String> existingList = Files.readAllLines(PATH_TO_STATISTICS_FILE, StandardCharsets.UTF_8);

        List<Statistics> listOfStatistics = new ArrayList<>();

        for (String rowList : existingList) {

            List<String> listTransaction = Arrays.asList(rowList.split(SEPARATOR));

            Statistics newLine = new Statistics();

            newLine.setCityName(listTransaction.get(0));
            newLine.setDistrictName(listTransaction.get(1));

            String counterString = listTransaction.get(2);
            int counter = Integer.parseInt(counterString);
            newLine.setCounter(counter);

            String averageValueString = listTransaction.get(3);
            double averagePrice = Double.parseDouble(averageValueString);
            newLine.setAverageValue(averagePrice);

            listOfStatistics.add(newLine);
        }
        return listOfStatistics;
    }

    public Map<String, Double[]> getCitesStatistics(List<Statistics> inputList) {
        Map<String, Double[]> resultCityMap = new HashMap<>();

        inputList.stream()
                .forEach(x -> {
                    int counter = x.getCounter();
                    double all = x.getAverageValue() * counter;
                    Double[] results = new Double[2];
                    if (resultCityMap.containsKey(x.getCityName())) {
                        results = resultCityMap.get(x.getCityName());
                        results[0] = results[0] + counter;
                        results[1] = results[1] + all;
                    }else{
                        results[0] = (double)counter;
                        results[1] = all;
                    }

                    resultCityMap.put(x.getCityName(), results);
                });

        return resultCityMap;
    }

    public Map<String, Double[]> getDistrictsStatistics(List<Statistics> inputList) {
        Map<String, Double[]> resultDistrictMap = new HashMap<>();

        inputList.stream()
                .forEach(x -> {
                    int counter = x.getCounter();
                    double avg = x.getAverageValue();
                    double all = x.getAverageValue() * counter;
                    Double[] results = new Double[3];

                        results[0] = (double)counter;
                        results[1] = avg;
                        results[2] = all;


                    resultDistrictMap.put(x.getCityName(), results);
                });

        return resultDistrictMap;
    }

    public List<String> resultMapListConverter(Map<String,Double[]> inputMap){

        ArrayList<String> results = new ArrayList();


        inputMap.entrySet().forEach(x -> {
           String result=x.getKey();
            for(int i=0; i<x.getValue().length;i++){
                result+=","+x.getValue()[i];
            }
            results.add(result);
        });

        return results;
    }
}
