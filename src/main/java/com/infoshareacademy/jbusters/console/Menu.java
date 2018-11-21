package console;


import java.io.IOException;

public class Menu {
    //zawiera wszytskie odwoania do funkcji programu



    private ConsoleReader consoleReader = new ConsoleReader();
    public Menu() throws IOException {

    }

    public void newSearch(){

        System.out.println("Witaj! Tu wycenisz swoje mieszkanie w kilku szybkich krokach. " +
                "Zacznij od przygotowania podstawowych danych odno?nie swojego mieszkania");

    }

    public void saveSession(){

    }

    public void exit(){

    }

    public ConsoleReader getConsoleReader() {
        return consoleReader;
    }

}
