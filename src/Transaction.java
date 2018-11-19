//pojedynczy rekord - zawiera wszytstkie pola wymagane do wyceny mieszkania

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private LocalDate transactionDate;
    private String city;
    private String district;
    private String street;
    private String typeOfMarket;
    private BigDecimal price;
    private BigDecimal flatArea;
    private BigDecimal pricePerM2;
    private int level;
    private String parkingSpot;
    private String standardLevel;
    private String constructionYear;
    private int constructionYearCategory;

    public Transaction(LocalDate transactionDate, String city, String district, String street, String typeOfMarket, BigDecimal price, BigDecimal flatArea, BigDecimal pricePerM2, int level, String parkingSpot, String standardLevel, String constructionYear) {
        this.transactionDate = transactionDate;
        this.city = city;
        this.district = district;
        this.street = street;
        this.typeOfMarket = typeOfMarket;
        this.price = price;
        this.flatArea = flatArea;
        this.pricePerM2 = pricePerM2;
        this.level = level;
        this.parkingSpot = parkingSpot;
        this.standardLevel = standardLevel;
        this.constructionYear = constructionYear;
        //test
    }


    public String toString(){
        //wypisywanie transakcji jako string - format do ustalenia - trzeba pamietac o pliku konfiguracyjnym -
        // np to, ze wypisywanie doubli musi byc wywolywane z uzyciem formattera tak jak w string.format()
        return " ";
    }



    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getTypeOfMarket() {
        return typeOfMarket;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getFlatArea() {
        return flatArea;
    }

    public int getLevel() {
        return level;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public String getStandardLevel() {
        return standardLevel;
    }

    public int getConstructionYearCategory() {
        return constructionYearCategory;
    }


}
