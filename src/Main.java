import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        Data data = new Data();
        Menu menu = new Menu();




        //////////////              PRINTUJE CALA BAZE DANYCH              /////////////////////
//      System.out.println(data.fileToData().subList(20, 50).toString());


        /////////////            PYTA UZYKTOWNIKA O PODANIE JEGO MIESZKANIA              ///////////////
        Transaction newTransaction = menu.loadNewTransaction();

    }
}
