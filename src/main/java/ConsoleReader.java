import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleReader {
    // klasa do komunikacji z uzytkownikiem tu beda wszystkie metody do obslugi wprowadzania konkretnych danych.


    public ConsoleReader() {

    }

    public int readInt(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public BigDecimal readBigDecimal() {
        Scanner scanner = new Scanner(System.in);
        return BigDecimal.valueOf(scanner.nextDouble());
    }

    public String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


}
