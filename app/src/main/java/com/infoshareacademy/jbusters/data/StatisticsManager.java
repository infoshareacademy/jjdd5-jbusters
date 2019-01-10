package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@ApplicationScoped
public class StatisticsManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);
    private PropLoader properties;
    private String currency ="PLN";
    private static final String SEPARATOR = ",";
    private static final int COUNT = 0;
    private static final int ALL = 1;
    private static final int AVG = 2;

   @Inject
    StaticFields staticFields;

    private final Path STATISTICS_PATH = staticFields.getStatisticsFilePath();

    public StatisticsManager() {
        properties = new PropLoader();
        try {
            properties = new PropLoader(StaticFields.getAppPropertiesURL().openStream());
            currency = properties.getCurrency();
        } catch (Exception e) {
            LOGGER.error("Missing properties file in path {}", StaticFields.getAppPropertiesURL().toString());
        }
    }

    public void captureNameFromServlet(String cityName, String districtName, String value) throws IOException {
        if (Objects.nonNull(value) && Double.parseDouble(value) > 0) {
            addOrUpdateStatistics(cityName, districtName, value);
        }
    }

    private void addOrUpdateStatistics(String cityName, String districtName, String value) throws IOException {
        if (!Files.exists(STATISTICS_PATH)) {
            LOGGER.warn("Statistics file missing under following path: {}", STATISTICS_PATH.toString());
            Files.createFile(STATISTICS_PATH);
            LOGGER.info("Empty statistics file created under following path: {}", STATISTICS_PATH.toString());
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

        Files.write(Paths.get(String.valueOf(STATISTICS_PATH)), statisticsString.getBytes(Charset.forName("UTF-8")), StandardOpenOption.APPEND);

        LOGGER.info("New statistics line added: {}", statisticsString);
    }

    private void overwriteExistingLine(int lineNumber, String lineData) throws IOException {

        List<String> existingLine = Files.readAllLines(STATISTICS_PATH, StandardCharsets.UTF_8);

        existingLine.set(lineNumber, lineData);

        Files.write(STATISTICS_PATH, existingLine, StandardCharsets.UTF_8);

        LOGGER.info("Statistics line {} updated with: {}", lineNumber, lineData);
    }

    public List<Statistics> generateStatisticsList() {

        List<String> existingList = null;
        try {
            existingList = Files.readAllLines(STATISTICS_PATH, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.info("Statistics file missing under following path: {}", STATISTICS_PATH.toString());
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

        inputMap.entrySet().forEach(x -> {
            String result = x.getKey();
            result += "|" + staticFields.formatWithLongDF(x.getValue()[COUNT]);
            //loop for money values like avg, all etc.
            if(x.getValue().length>COUNT+1) {
                for (int i = COUNT + 1; i < x.getValue().length; i++) {
                    result += "|" + staticFields.formatWithLongDF(x.getValue()[i])+" "+ currency;
                }
            }
            results.add(result);
        });

        return results;
    }

}

