package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.NewTransactionCreator;

import java.io.IOException;

public class Menu {
    //zawiera wszytskie odwoania do funkcji programu

    private ConsoleReader consoleReader = new ConsoleReader();
    private Data data = new Data();
    private NewTransactionCreator newTransactionCreator = new NewTransactionCreator(data, consoleReader);


    public Menu() throws IOException {

    }

    public void newSearch() {

    }

    public void welcome() {
        System.out.println("Witaj! Tu wycenisz swoje mieszkanie w kilku szybkich krokach." + "\n" +
                "Wpisz odpowiedni numer by poruszac sie po menu" + "\n");
    }

    public void startProgram() {

        System.out.println("MENU"+ "\n");
        System.out.println("1 - Wycen moje mieszkanie" +"\n" +
                            "2 - Zapisz moje mieszkanie do pliku" + "\n" +
                            "3 - Zaladuj moje mieszkanie" + "\n" +
                            "4 - Wpisz mieszkanie do bazy" + "\n" +
                            "5 - Wyjscie"+ "\n" + "podaj numer...");
        int menuChoise = consoleReader.readInt(1,5);

        switch (menuChoise) {
            case 1: {
                System.out.println("Bedziesz poproszony o podanie kilku podstawowych informacji odnosnie twojego mieszkania" + "\n");
                newTransactionCreator.loadNewTransaction();
            }
            case 2: {
                System.out.println("Under construction");
                startProgram();
                break;
            }
            case 3: {
                System.out.println("Under construction");
                startProgram();
                break;
            }
            case 4: {
                System.out.println("Under construction");
                startProgram();
                break;
            }
            case 5: {
                System.out.println("Zapraszamy ponownie");
                break;
            }
        }
    }


    public void saveSession() {


    }

    public void exit() {

    }

    public ConsoleReader getConsoleReader() {
        return consoleReader;
    }

}
