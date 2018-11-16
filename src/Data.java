//Klasa zawierajaca dane wczytywane z pliku, kazdy rekord w pliku excel bedzie jedna iinstancja clasy transaction.
// klasa data powinna zawierac metody pozwalajace dodawac rekord do bazy(pliku?)

import java.io.File;
import java.util.ArrayList;

public class Data {

    ArrayList<Transaction> dataSource = new ArrayList<>();


    public void fileToData(File file){
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

