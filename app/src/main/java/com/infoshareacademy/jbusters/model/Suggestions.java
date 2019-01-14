package com.infoshareacademy.jbusters.model;


import javax.persistence.*;

@Entity
@Table(name = "suggestions")
public class Suggestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUGGESTIONS_ID")
    private int suggestionsId;

    @Column(name = "SUGGESTIONS_CITY")
    private String suggestionsCity;

    @Column(name = "SUGGESTIONS_DISTRICT")
    private String suggestionsDistrict;


    public Suggestions() {
    }

    public Suggestions(String suggestionsCity, String suggestionsDistrict) {
        this.suggestionsCity = suggestionsCity;
        this.suggestionsDistrict = suggestionsDistrict;
    }

    public int getSuggestionsId() {
        return suggestionsId;
    }

    public void setSuggestionsId(int suggestionsId) {
        this.suggestionsId = suggestionsId;
    }

    public String getSuggestionsCity() {
        return suggestionsCity;
    }

    public void setSuggestionsCity(String suggestionsCity) {
        this.suggestionsCity = suggestionsCity;
    }

    public String getSuggestionsDistrict() {
        return suggestionsDistrict;
    }

    public void setSuggestionsDistrict(String suggestionsDistrict) {
        this.suggestionsDistrict = suggestionsDistrict;
    }
}
