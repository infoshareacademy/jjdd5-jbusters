package com.infoshareacademy.jbusters.data;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

@ApplicationScoped
public class StatisticsManager {

    private static final Path PATH_TO_STATISTICS_FILE = Paths.get(System.getProperty("jboss.home.dir"), "data", "statistics.txt");
    private static final String SEPARATOR = ",";
    private static final int COUNT = 0;
    private static final int ALL = 1;
    private static final int AVG = 2;

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
        BigDecimal valueUpdate;
        BigDecimal newValue = new BigDecimal(value);
        BigDecimal divider = new BigDecimal(2);
        boolean shouldAddNewLine = true;

        for (int i = 0; i < existingList.size(); i++) {
            if (existingList.get(i).getCityName().equals(cityName) &&
                    existingList.get(i).getDistrictName().equals(districtName)) {

                counterIncrement = existingList.get(i).getCounter() + 1;
                String counterString = String.valueOf(counterIncrement);

                valueUpdate = BigDecimal.valueOf(existingList.get(i).getAverageValue());
                valueUpdate = valueUpdate.add(newValue).divide(divider).setScale(2, BigDecimal.ROUND_UP);
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

    public List<Statistics> generateStatisticsList() {

        List<String> existingList = null;
        try {
            existingList = Files.readAllLines(PATH_TO_STATISTICS_FILE, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public List<String> getCitesStatistics(List<Statistics> inputList) {
        Map<String, Double[]> resultCityMap = new TreeMap<>();

        inputList.stream()
                .forEach(x -> {
                    int counter = x.getCounter();
                    double all = x.getAverageValue() * counter;
                    Double[] results = new Double[2];
                    if (resultCityMap.containsKey(x.getCityName())) {
                        results = resultCityMap.get(x.getCityName());
                        results[COUNT] = results[COUNT] + counter;
                        results[ALL] = results[ALL] + all;
                    } else {
                        results[COUNT] = (double) counter;
                        results[ALL] = all;
                    }

                    resultCityMap.put(x.getCityName(), results);

                });


        return resultMapListConverter(resultCityMap);
    }

    public List<String> getDistrictsStatistics(List<Statistics> inputList) {
        Map<String, Double[]> resultDistrictMap = new TreeMap<>();
        inputList.stream()
                .forEach(x -> {
                    int counter = x.getCounter();
                    double avg = x.getAverageValue();
                    double all = x.getAverageValue() * counter;
                    Double[] results = new Double[3];

                    results[COUNT] = (double) counter;
                    results[AVG] = avg;
                    results[ALL] = all;

                    resultDistrictMap.put(x.getDistrictName(), results);
                });

        return resultMapListConverter(resultDistrictMap);
    }

    public List<String> resultMapListConverter(Map<String, Double[]> inputMap) {

        ArrayList<String> results = new ArrayList();

        DecimalFormat df = new DecimalFormat("#.##");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        inputMap.entrySet().forEach(x -> {
            String result = x.getKey();
            for (int i = 0; i < x.getValue().length; i++) {
                result += "," + df.format(x.getValue()[i]);
            }
            results.add(result);
        });

        return results;
    }

}

