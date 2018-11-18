import java.math.BigDecimal;
import java.time.LocalDate;

public class Menu {
    //zawiera wszytskie odwoania do funkcji programu

    private ConsoleReader consoleReader = new ConsoleReader();


    public Menu() {

    }

    public void newSearch(){


        System.out.println("Witaj! Tu wycenisz swoje mieszkanie w kilku szybkich krokach. " +
                "Zacznij od przygotowania podstawowych danych odno?nie swojego mieszkania");


    }

    public Transaction loadNewTransaction(){
        Transaction newTransaction = new Transaction(LocalDate.now(), loadCity(), loadDisctrict(), loadStreet(), loadMarket(),
                BigDecimal.valueOf(0), loadSize(), BigDecimal.valueOf(0), loadLevel(), loadParkingSpot(), loadStandardLevel(), loadConstructionYear());
        System.out.println(newTransaction);
        return newTransaction;
    }



    private String loadDisctrict(){
        System.out.println("Podaj w jakiej dzielnicy jest twoje mieszkanie");
        String[] districtList = {"Babie Doły", "Cwarzno-Wiczlino", "Chylonia", "Cisowa", "Dąbrowa", "Działki Leśne", "Grabówek",
                            "Kamienna Góra", "Karwiny", "Leszczynki", "Mały Kack", "Obłóże", "Oksywie", "Orłowo", "Pogóże",
                             "Pustki Cisowskie", "Redłowo", "Sopot", "Śródmieście z Portem", "Wieli Kack", "Witomino",
                            "Wzgórze Św. Maksymiliana"};

        for (int i = 0; i < districtList.length; i++){
            System.out.println(i + 1 + " - " + districtList[i]);
        }
        return districtList[consoleReader.readInt()-1];
    }
    
    private String loadCity(){
        System.out.println("Podaj nazwę miasta, w którym mieszkasz");
        String[] cityList = {"Gdynia", "Sopot"};

        for (int i = 0; i < cityList.length; i++) {
            System.out.println(i + 1 + " - " + cityList[i]);
        }
        return cityList[consoleReader.readInt()-1];
    }

    private String loadStreet() {
        System.out.println("Podaj nazwę ulicy");
        return consoleReader.readString();
    }

    private String loadMarket() {
        System.out.println("Podaj rodzaj rynku" + "\n" + "1 - Rynek wtórny" + "\n" + "2 - Rynek pierwotny");
        String[] marketList = {"RYNEK WTÓRNY", "RYNEK PIERWOTNY"};
        return marketList[consoleReader.readInt()-1];
    }

    private BigDecimal loadSize() {
        System.out.println("Podaj wielkość mieszkania w m2");
        return consoleReader.readBigDecimal();
    }

    private String loadParkingSpot() {
        System.out.println("Czy do mieszkania jest przypisane miejsce postojowe?");
        String[] parkingSpotList = {"BRAK MP", "MIEJSCE NAZIEMNE", "GARAŻ JEDNOSTANOWISKOWY", "MIEJSCE POSTOJOWE W HALI GARAŻOWEJ"};
        for (int i = 0; i < parkingSpotList.length; i++) {
            System.out.println(i + 1 + " - " + parkingSpotList[i]);
        }
        return parkingSpotList[consoleReader.readInt()-1];
    }

    private int loadLevel() {
        System.out.println("Podaj piętro, na którym jest twoje mieszkanie");
        return consoleReader.readInt();
    }

    private String loadStandardLevel() {
        System.out.println("Podaj poziom wykończenia twojego mieszkania");
        String[] standardLevelList = {"WYSOKI", "BARDZO DOBRY", "DOBRY", "PRZECIĘTNY", "NISKI", "DEWELOPERSKI/DO WYKOŃCZENIA"};
        for (int i = 0; i <standardLevelList.length; i++) {
            System.out.println(i + 1 + " - " + standardLevelList[i]);
        }
        return standardLevelList[consoleReader.readInt()-1];
    }

    private String loadConstructionYear() {
        System.out.println("Podaj rok budowy budynku, w którym jest twoje mieszkaie");
        System.out.println("1 - przed rokiem 1970" + "\n" + "2 - między rokiem 1970 a 1990" + "\n" + "3 - po roku 1990");
        return consoleReader.readString();
    }


    public void saveSession(){

    }

    public void exit(){

    }

}
