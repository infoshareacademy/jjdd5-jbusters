package com.infoshareacademy.jbusters.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROPERTY_ID")
    private int propertyId;

    @Column(name = "PROPERTY_NAME")
    @NotNull
    private String propertyName;

    @Column(name = "VALUE")
    @NotNull
    private String propertyValue;


    public int getPropertyId() {
        return propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}