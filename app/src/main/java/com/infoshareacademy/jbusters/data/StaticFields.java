package com.infoshareacademy.jbusters.data;

import javax.ejb.Local;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class StaticFields {

    private static StaticFields staticFields = new StaticFields();

    private static DecimalFormat df = new DecimalFormat("###,###.##",new DecimalFormatSymbols(Locale.ENGLISH));

    private StaticFields() {
    }

    public StaticFields getStatics(){
        return staticFields;
    }
    public DecimalFormat getDecimalFormat(){
        return df;
    }
}
