package com.infoshareacademy.jbusters.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "appProperties")
public class AppProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APP_PROPERTY_ID")
    private int apId;

    @Column(name = "APP_PROPERTY_NAME")
    @NotNull
    private String apName;

    @Column(name = "APP_PROPERTY_VALUE")
    @NotNull
    private String apValue;

    public AppProperty() {
    }

    public AppProperty(String apName, String apValue) {
        this.apName = apName;
        this.apValue = apValue;
    }

    public int getApId() {
        return apId;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public String getApValue() {
        return apValue;
    }

    public void setApValue(String apValue) {
        this.apValue = apValue;
    }
}
