//Klasa zawierajaca dane wczytywane z pliku, kazdy rekord w pliku excel bedzie jedna iinstancja clasy transaction.
// klasa data powinna zawierac metody pozwalajace dodawac rekord do bazy(pliku?)

import sun.reflect.generics.tree.Tree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Data {

    public  Data() throws IOException {

    }

    public List<Transaction> fileToData() throws IOException {

        Path pathToFileTransactionCSV = Paths.get("resources", "transaction.csv");
        List<String> listFileTransactionCSV = Files.readAllLines(pathToFileTransactionCSV);
        listFileTransactionCSV.remove(0);

        DataLoader data = new DataLoader();

        return data.createTransactionList(listFileTransactionCSV);

    }


//    public String toString() {
//        for (Transaction transactionsListView : ) {
//            System.out.println(transactionsListView);
//        }
//    }

    // Metoda do wyciągania z bazy danych listy dzielnic bez duplikatów + w kolejnkości alfabetycznej
    // i przekazuje ja do wyswietlenia w menu
    public List<String> districList(List<Transaction> transactionsBase) {
        List<String> districtList = new ArrayList<>();
        for (int i = 0; i < transactionsBase.size(); i++) {
            districtList.add(transactionsBase.get(i).getDistrict());
        }
        TreeSet<String> noDuplicates = new TreeSet<>(districtList);
        districtList = new ArrayList<>(noDuplicates);

        return districtList;
    }


    public ArrayList<Transaction> filterData() {
        //metoda wypluwajca przefiltrowana liste
        return null;
    }

    public void addTransactionToData(Transaction trans) {
        //metoda dodajca tranzakcje do bazy danych
    }
}

