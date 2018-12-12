package com.infoshareacademy.jbusters.data;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class StatisticsManager {

    private static Path pathToStatisticsFile = Paths.get(System.getProperty("jboss.home.dir"), "data", "statistics.txt");

    public void captureNameFromServlet(String name) throws IOException {
        addOrUpdateStatistics(name);
    }

    public String statisticsSummary() throws IOException {
        return generateStatisticsList().toString();
    }

    private void addOrUpdateStatistics(String name) throws IOException {

        if (!Files.exists(pathToStatisticsFile)) {
            Files.createFile(pathToStatisticsFile);
        }

        List<Statistics> existingList = generateStatisticsList();

        int counterIncrement;
        boolean shouldAddNewLine = true;

        for (int i = 0; i < existingList.size(); i++) {

            if (existingList.get(i).getStatisticName().equals(name)) {

                counterIncrement = existingList.get(i).getCounter()+1;

                String counterString = String.valueOf(counterIncrement);

                overwriteExistingLine(i, name + "," + counterString);

                shouldAddNewLine = false;
            }
        }

        if (shouldAddNewLine){
            addNewLine(name);
        }
    }

    private void addNewLine(String name) throws IOException {

        String statisticsString = name + ",1";

        Files.write(Paths.get(String.valueOf(pathToStatisticsFile)), statisticsString.getBytes(), StandardOpenOption.APPEND);
    }

    private void overwriteExistingLine(int lineNumber, String data) throws IOException {

        Path path = pathToStatisticsFile;

        List<String> existingLine = Files.readAllLines(path);

        existingLine.set(lineNumber, data);

        Files.write(path, existingLine);
    }

    private List<Statistics> generateStatisticsList() throws IOException {

        List<String> existingList = Files.readAllLines(pathToStatisticsFile);

        List<Statistics> listOfStatistics = new ArrayList<>();

        for (String rowList : existingList) {

            List<String> listTransaction = Arrays.asList(rowList.split(","));

            Statistics newLine = new Statistics();

            newLine.setStatisticName(listTransaction.get(0));

            String counterString = listTransaction.get(1);
            int counter = Integer.valueOf(counterString);
            newLine.setCounter(counter);

            listOfStatistics.add(newLine);
        }
        return listOfStatistics;
    }
}
