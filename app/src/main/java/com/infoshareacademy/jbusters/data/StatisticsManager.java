package com.infoshareacademy.jbusters.data;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class StatisticsManager {

    private static final Path PATH_TO_STATISTICS_FILE = Paths.get(System.getProperty("jboss.home.dir"), "data", "statistics.txt");
    private static final String SEPARATOR = ",";

    public void captureNameFromServlet(String cityName, String districtName, String value) throws IOException {
        if(Objects.nonNull(value) && Double.parseDouble(value) > 0) {
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
                valueUpdate = valueUpdate.add(newValue).divide(divider).setScale(2,BigDecimal.ROUND_UP);
                String valueString = String.valueOf(valueUpdate);

                overwriteExistingLine(i, cityName + SEPARATOR + districtName + SEPARATOR + counterString + SEPARATOR + valueString);

                shouldAddNewLine = false;
            }
        }
        if (shouldAddNewLine){
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
}
