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

//    public Transaction(LocalDate transactionDate, String city, String district, String street, String typeOfMarket, BigDecimal price, BigDecimal flatArea, BigDecimal pricePerM2, int level, String parkingSpot, String standardLevel, String constructionYear) {
//        this.transactionDate = transactionDate;
//        this.city = city;
//        this.district = district;
//        this.street = street;
//        this.typeOfMarket = typeOfMarket;
//        this.price = price;
//        this.flatArea = flatArea;
//        this.pricePerM2 = pricePerM2;
//        this.level = level;
//        this.parkingSpot = parkingSpot;
//        this.standardLevel = standardLevel;
//        this.constructionYear = constructionYear;
//    }

    @Override
    public String toString() {
        return "Transaction{"+"\n" +
                "transactionDate=" + transactionDate +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", typeOfMarket='" + typeOfMarket + '\'' +
                ", price=" + price +
                ", flatArea=" + flatArea +
                ", pricePerM2=" + pricePerM2 +
                ", level=" + level +
                ", parkingSpot='" + parkingSpot + '\'' +
                ", standardLevel='" + standardLevel + '\'' +
                ", constructionYear='" + constructionYear + '\'' +
                ", constructionYearCategory=" + constructionYearCategory +
                '}' +"\n";
    }


//    public String toString(){
//        //wypisywanie transakcji jako string - format do ustalenia - trzeba pamietac o pliku konfiguracyjnym -
//        // np to, ze wypisywanie doubli musi byc wywolywane z uzyciem formattera tak jak w string.format()
//        return " ";
//    }

    // ************GETTER AND SETTER***********


    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTypeOfMarket() {
        return typeOfMarket;
    }

    public void setTypeOfMarket(String typeOfMarket) {
        this.typeOfMarket = typeOfMarket;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFlatArea() {
        return flatArea;
    }

    public void setFlatArea(BigDecimal flatArea) {
        this.flatArea = flatArea;
    }

    public BigDecimal getPricePerM2() {
        return pricePerM2;
    }

    public void setPricePerM2(BigDecimal pricePerM2) {
        this.pricePerM2 = pricePerM2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getStandardLevel() {
        return standardLevel;
    }

    public void setStandardLevel(String standardLevel) {
        this.standardLevel = standardLevel;
    }

    public String getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(String constructionYear) {
        this.constructionYear = constructionYear;
    }

    public int getConstructionYearCategory() {
        return constructionYearCategory;
    }

    public void setConstructionYearCategory(int constructionYearCategory) {
        this.constructionYearCategory = constructionYearCategory;
    }
}
