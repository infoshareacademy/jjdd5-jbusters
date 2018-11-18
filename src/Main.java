import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Data data = new Data();
        Menu menu = new Menu();
        ConsoleReader consoleReader = new ConsoleReader();



//        menu.loadNewTransaction();

//        consoleReader.readString();
//        menu.newSearch();
        data.fileToData();

    }
}
