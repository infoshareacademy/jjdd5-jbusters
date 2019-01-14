package com.infoshareacademy.jbusters.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="districts_wages")
public class DistrictWage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISTRICT_ID")
    private int districtId;

    @Column(name = "CITY_NAME")
    @NotNull
    private String cityName;

    @Column(name = "DISTRICT_NAME")
    @NotNull
    private String districtName;

    @Column(name = "WAGE")
    private int wage;


    public DistrictWage() {
    }

    public DistrictWage(@NotNull String cityName, @NotNull String districtName, int wage) {
        this.cityName = cityName;
        this.districtName = districtName;
        this.wage = wage;
    }


    public int getDistrictId() {
        return districtId;
    }

    @NotNull
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@NotNull String cityName) {
        this.cityName = cityName;
    }

    @NotNull
    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(@NotNull String districtName) {
        this.districtName = districtName;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }
}
