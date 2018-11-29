package com.infoshareacademy.jbusters.data;

import java.util.Arrays;

public enum ParkingPlace {
    BRAK_MP("BRAK MP", 0),
    MIEJSCE_NAZIEMNE("MIEJSCE NAZIEMNE", 1),
    MIEJSCE_HALA("MIEJSCE POSTOJOWE W HALI GARAŻOWEJ", 2),
    GARAZ("GARAŻ JEDNOSTANOWISKOWY", 3);

    private int wage;
    private String name;

    ParkingPlace(String name, int wage) {
        this.wage = wage;
        this.name = name;
    }

    public int getWage() {
        return wage;
    }

    public String getName() {
        return name;
    }

    public static ParkingPlace fromString(String s) {
        return Arrays.stream(values())
                .filter(e -> e.getName().equalsIgnoreCase(s))
                .findFirst()
                .orElse(BRAK_MP);
    }
}
