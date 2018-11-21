import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

//        Data data = new Data();
        Menu menu = new Menu();
        Data data = new Data();
        ConsoleReader consoleReader = menu.getConsoleReader();
        NewTransactionCreator newTransactionCreator = new NewTransactionCreator(data, consoleReader);




        //////////////              PRINTUJE CALA BAZE DANYCH              /////////////////////
//      System.out.println(data.fileToData().subList(20, 50).toString());


        /////////////            PYTA UZYKTOWNIKA O PODANIE JEGO MIESZKANIA              ///////////////
        Transaction newTransaction = newTransactionCreator.loadNewTransaction();

    }
}
