package com.infoshareacademy.jbusters.console;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleReader {

    public ConsoleReader() {

    }

    public int readInt(int minValue, int maxValue) {   // podanie zakresu liczb jakie uzytkownik moze wpisac, sa one pobierane z wielkosci tablicy
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int inputInt = scanner.nextInt();
                if (inputInt > maxValue || inputInt < minValue) {
                    System.out.println("Podano złą wartość");
                } else {
                    return inputInt;
                }

            } catch (java.util.InputMismatchException e) {
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
                scanner.nextLine();
            }
        }
    }

    public String readString() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            while (!scanner.hasNext("[A-Za-zęóąśłżźćń.0-9]+")) {
                System.out.println("Błąd, wpisz ponownie: ");
                scanner.nextLine();
            }
            return scanner.nextLine();
        }
    }

    public String readDate() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            while (!scanner.hasNext("[0-9-]+")) {
                System.out.println("Błąd, wpisz ponownie: ");
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
                scanner.nextLine();
            }
        }
    }

}