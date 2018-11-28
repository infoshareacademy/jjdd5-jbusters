package com.infoshareacademy.jbusters.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleReader.class);
    private static final String CURRENCY_PATTERN = "/^AED|AFN|ALL|AMD|ANG|AOA|ARS|AUD|AWG|AZN|BAM|" +
            "BBD|BDT|BGN|BHD|BIF|BMD|BND|BOB|BRL|BSD|BTN|BWP|BYR|BZD|CAD|CDF|CHF|" +
            "CLP|CNY|COP|CRC|CUC|CUP|CVE|CZK|DJF|DKK|DOP|DZD|EGP|ERN|ETB|EUR|FJD|" +
            "FKP|GBP|GEL|GGP|GHS|GIP|GMD|GNF|GTQ|GYD|HKD|HNL|HRK|HTG|HUF|IDR|ILS|" +
            "IMP|INR|IQD|IRR|ISK|JEP|JMD|JOD|JPY|KES|KGS|KHR|KMF|KPW|KRW|KWD|KYD|" +
            "KZT|LAK|LBP|LKR|LRD|LSL|LYD|MAD|MDL|MGA|MKD|MMK|MNT|MOP|MRO|MUR|MVR|" +
            "MWK|MXN|MYR|MZN|NAD|NGN|NIO|NOK|NPR|NZD|OMR|PAB|PEN|PGK|PHP|PKR|PLN|" +
            "PYG|QAR|RON|RSD|RUB|RWF|SAR|SBD|SCR|SDG|SEK|SGD|SHP|SLL|SOS|SPL|SRD|" +
            "STD|SVC|SYP|SZL|THB|TJS|TMT|TND|TOP|TRY|TTD|TVD|TWD|TZS|UAH|UGX|USD|" +
            "UYU|UZS|VEF|VND|VUV|WST|XAF|XCD|XDR|XOF|XPF|YER|ZAR|ZMW|ZWD$/";
    private static final String EXCHANGE_RATE_PATTERN = "^(0|([1-9][0-9]*))(\\.[0-9]+)?$";
    public static final String STRING_PATTERN = "[A-Za-zęóąśłżźćń.0-9]+";
    public static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String DECIMAL_PLACES_PATTERN = "[0-2]{1}";


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
        return askForString(STRING_PATTERN);
    }

    public String readDate() {
        return askForString(DATE_PATTERN);
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
        return askForString(DECIMAL_PLACES_PATTERN);
    }

    public String readStringCurrency() {
        return askForString(CURRENCY_PATTERN);
    }

    public String readStringExchangeRate() {
        return askForString(EXCHANGE_RATE_PATTERN);
    }

    public String askForString(String pattern) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String enteredString = scanner.nextLine();
            if (!enteredString.matches(pattern)) {
                System.out.println("Błąd, wpisz ponownie: ");
                LOGGER.warn("Incorrect value was provided ");
            } else {
                return enteredString;
            }
        }
    }
}
