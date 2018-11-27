package com.infoshareacademy.jbusters.console;

import com.infoshareacademy.jbusters.data.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        String pattern = "[A-Za-zęóąśłżźćń.0-9]+";

        while (true) {
            while (!scanner.hasNext(pattern)) {
                System.out.println("Błąd, wpisz ponownie: ");
                scanner.nextLine();
            }
            return scanner.nextLine();
        }
    }

    public String readDate() {
        Scanner scanner = new Scanner(System.in);
        String pattern = "^\\d{4}-\\d{2}-\\d{2}$";

        while (true) {
            while (!scanner.hasNext(pattern)) {
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

    public String readStringDecimalPlaces() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            while (!scanner.hasNext("[0-2]{1}")) {
                System.out.println("Błąd, wpisz ponownie: ");
                scanner.nextLine();
            }
            return scanner.nextLine();
        }
    }

    public String readStringCurrency() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            //RegExp list from: https://github.com/d4nyll/iso-helper/blob/master/iso-4217-currencies/regex
            while (!scanner.hasNext("/^AED|AFN|ALL|AMD|ANG|AOA|ARS|AUD|AWG|AZN|BAM|" +
                    "BBD|BDT|BGN|BHD|BIF|BMD|BND|BOB|BRL|BSD|BTN|BWP|BYR|BZD|CAD|CDF|CHF|" +
                    "CLP|CNY|COP|CRC|CUC|CUP|CVE|CZK|DJF|DKK|DOP|DZD|EGP|ERN|ETB|EUR|FJD|" +
                    "FKP|GBP|GEL|GGP|GHS|GIP|GMD|GNF|GTQ|GYD|HKD|HNL|HRK|HTG|HUF|IDR|ILS|" +
                    "IMP|INR|IQD|IRR|ISK|JEP|JMD|JOD|JPY|KES|KGS|KHR|KMF|KPW|KRW|KWD|KYD|" +
                    "KZT|LAK|LBP|LKR|LRD|LSL|LYD|MAD|MDL|MGA|MKD|MMK|MNT|MOP|MRO|MUR|MVR|" +
                    "MWK|MXN|MYR|MZN|NAD|NGN|NIO|NOK|NPR|NZD|OMR|PAB|PEN|PGK|PHP|PKR|PLN|" +
                    "PYG|QAR|RON|RSD|RUB|RWF|SAR|SBD|SCR|SDG|SEK|SGD|SHP|SLL|SOS|SPL|SRD|" +
                    "STD|SVC|SYP|SZL|THB|TJS|TMT|TND|TOP|TRY|TTD|TVD|TWD|TZS|UAH|UGX|USD|" +
                    "UYU|UZS|VEF|VND|VUV|WST|XAF|XCD|XDR|XOF|XPF|YER|ZAR|ZMW|ZWD$/")) {
                System.out.println("Błąd, wpisz ponownie: ");
                scanner.nextLine();
            }
            return scanner.nextLine();
        }
    }

    public String readStringExchangeRate() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String enteredValue = scanner.nextLine();
            if (!enteredValue.matches("^(0|([1-9][0-9]*))(\\.[0-9]+)?$")) {
                System.out.println("Błąd, wpisz ponownie: ");
            } else {
                return enteredValue;
            }
        }
    }
}
