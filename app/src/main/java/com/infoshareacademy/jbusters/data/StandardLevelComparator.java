package com.infoshareacademy.jbusters.data;

import java.util.Comparator;

public class StandardLevelComparator implements Comparator<StandardLevel> {

    public StandardLevelComparator() {
    }

    @Override
    public int compare(StandardLevel o1, StandardLevel o2) {
        return o1.getWage() - o2.getWage();
    }
}
