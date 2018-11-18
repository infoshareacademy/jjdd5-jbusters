//Klasa zawierajaca dane wczytywane z pliku, kazdy rekord w pliku excel bedzie jedna iinstancja clasy transaction.
// klasa data powinna zawierac metody pozwalajace dodawac rekord do bazy(pliku?)

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public List<Transaction> fileToData() throws IOException {

        Path pathToFileTransactionCSV = Paths.get("resources", "transaction.csv");
        List<String> listFileTransactionCSV = Files.readAllLines(pathToFileTransactionCSV);
        listFileTransactionCSV.remove(0);

        DataLoader data = new DataLoader();

        return data.createTransactionList(listFileTransactionCSV);

    }

    public ArrayList<Transaction> filterData() {
        //metoda wypluwajca przefiltrowana liste
        return null;
    }

    public void addTransactionToData(Transaction trans) {
        //metoda dodajca tranzakcje do bazy danych
    }
}

