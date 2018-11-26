package com.infoshareacademy.jbusters.data;

public enum StandardLevel {
    DEWELOPERSKI("DEWELOPERSKI/DO WYKOŃCZENIA",0),
    NISKI("NISKI",1),
    PRZECIETNY("PRZECIĘTNY",2),
    DOBRY("DOBRY",3),
    BARDZO_DOBRY("BARDZO DOBRY",4),
    WYSOKI("WYSOKI",5);
    private String name;
    private int wage;

    StandardLevel(String name, int wage){
        this.name=name;
        this.wage=wage;
    }

    public String getName() {
        return name;
    }

    public int getWage() {
        return wage;
    }
}
