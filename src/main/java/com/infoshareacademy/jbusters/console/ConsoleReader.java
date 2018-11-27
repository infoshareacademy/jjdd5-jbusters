package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.data.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleReader.class);

    public int readInt(int minValue, int maxValue) {   // podanie zakresu liczb jakie uzytkownik moze wpisac, sa one pobierane z wielkosci tablicy
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int inputInt = scanner.nextInt();
                if (inputInt > maxValue || inputInt < minValue) {
                    System.out.println("Podano złą wartość");
                    LOGGER.warn("Incorrect value was provided. User has given: {}", inputInt);
                } else {
                    return inputInt;
                }

            } catch (java.util.InputMismatchException e) {
                LOGGER.warn("Incorrect value was provided ", e.getMessage());
                System.out.println("Podaj liczbę ");
                scanner.nextLine();
            }
        }
    }

    public BigDecimal readBigDecimal() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                return BigDecimal.valueOf(scanner.nextDouble());
            } catch (java.util.InputMismatchException e) {
                System.out.println("Podaj liczbę ");
                LOGGER.warn("Incorrect value was provided ", e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public String readString() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            while (!scanner.hasNext("[A-Za-zęóąśłżźćń.0-9]+")) {
                System.out.println("Błąd, wpisz ponownie: ");
                LOGGER.warn("Incorrect value was provided ");
                scanner.nextLine();
            }
            return scanner.nextLine();
        }
    }

    public int readInt() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                return scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Podaj liczbę ");
                LOGGER.warn("Incorrect value was provided ", e.getMessage());
                scanner.nextLine();
            }
        }
    }

}