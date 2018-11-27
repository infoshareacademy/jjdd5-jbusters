package com.infoshareacademy.jbusters.data;

import java.util.Comparator;

public class ParkingPlaceComparator implements Comparator<ParkingPlace> {

    ParkingPlaceComparator(){};
    @Override
    public int compare(ParkingPlace o1, ParkingPlace o2) {
        return o1.getWage()-o2.getWage();
    }
}
