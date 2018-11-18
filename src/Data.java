//Klasa zawierajaca dane wczytywane z pliku, kazdy rekord w pliku excel bedzie jedna iinstancja clasy transaction.
// klasa data powinna zawierac metody pozwalajace dodawac rekord do bazy(pliku?)

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {

    private Path csvPath = Paths.get("files", "Transactions.csv");


    ArrayList<Transaction> dataSource = new ArrayList<>();
    Transaction mineTransaction = new Transaction(LocalDate.of(2001, 05, 20), "Gdynia", "Cisowa","Chylo?ska", "RYNEK WT?RNY", BigDecimal.valueOf(150000), BigDecimal.valueOf(64,30), BigDecimal.valueOf(7300), 2, "BRAK MP", "DOBRY", "2010" );
    Transaction mineTransaction2 = new Transaction(LocalDate.of(2001, 05, 20), "city", "Cisowa","Chylo?ska", "RYNEK WT?RNY", BigDecimal.valueOf(150000), BigDecimal.valueOf(64,30), BigDecimal.valueOf(7300), 2, "BRAK MP", "DOBRY", "2010" );




    public void fileToData() throws IOException {

        List<String> csvAsStringList = Files.readAllLines(csvPath);
        csvAsStringList.remove(0);

        List<Transaction> transactionsList = new ArrayList<>();

        for (String line : csvAsStringList) {

            List<String> l = Arrays.asList(line.split(";"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");



            Transaction transaction = new Transaction(LocalDate.parse(l.get(0), formatter),l.get(1), l.get(2).replaceAll(" ", ""), l.get(3), l.get(4),new BigDecimal(l.get(5)) ,new BigDecimal(l.get(6).replaceAll(",", ".")), new BigDecimal(l.get(7)), Integer.valueOf(l.get(8)), l.get(9), l.get(10), l.get(11));

            transactionsList.add(transaction);

//            LocalDate soon = LocalDate.of(2001, 02, 15);


        }


        List<Transaction> sortedList = new ArrayList<>();
        List<Transaction> sortedList2 = new ArrayList<>();
        List<Transaction> sortedList3 = new ArrayList<>();

        for (Transaction t : transactionsList) {
            if (t.getDistrict().equalsIgnoreCase(mineTransaction.getDistrict())) {
//                System.out.println(t);
                sortedList.add(t);

            }
        }
        System.out.println();
        for (Transaction t : sortedList) {
            if (t.getLevel() == mineTransaction.getLevel()) {
                System.out.println(t);
                sortedList2.add(t);
            }
        }

        BigDecimal totalAvaragePrice = BigDecimal.valueOf(0);

        for ( Transaction t : sortedList2) {
            totalAvaragePrice = totalAvaragePrice.add((t.getPricePerM2()));
        }

        BigDecimal sizeOfList2 = BigDecimal.valueOf(sortedList2.size());
        BigDecimal avaragePrice = totalAvaragePrice.divide(sizeOfList2, BigDecimal.ROUND_HALF_DOWN);
        System.out.println("?rednia cena twojego mieszkania to " + avaragePrice + "z? za m2");




//        for (Transaction t : transactionsList) {
//            System.out.println(t);
//        }

//        System.out.println(transactionsList.get(4));
//        System.out.println(LocalDate.now());

        //wczytanie caego pliku csv -> konwersja pliku w taki sposb, aby zapenic dataBase lista Transactions. Metoda musi zapewnic
        // ze kazda komorka wiersza bedzie parsowana do odpowiedniego typu zmiennej.

    }

    public ArrayList<Transaction> filterData(){
        //metoda wypluwajca przefiltrowana liste
            return null;
    }
    public void addTransactionToData(Transaction trans){
        //metoda dodajca tranzakcje do bazy danych
    }
}

