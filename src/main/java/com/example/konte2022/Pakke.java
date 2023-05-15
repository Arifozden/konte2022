package com.example.konte2022;

public class Pakke {
    private String LID;
    private String eier;
    private double vekt;
    private double volum;

    public Pakke(String LID, String eier, double vekt, double volum) {
        this.LID = LID;
        this.eier = eier;
        this.vekt = vekt;
        this.volum = volum;
    }

    public Pakke(){}

    public String getLID() {
        return LID;
    }

    public void setLID(String LID) {
        this.LID = LID;
    }

    public String getEier() {
        return eier;
    }

    public void setEier(String eier) {
        this.eier = eier;
    }

    public double getVekt() {
        return vekt;
    }

    public void setVekt(double vekt) {
        this.vekt = vekt;
    }

    public double getVolum() {
        return volum;
    }

    public void setVolum(double volum) {
        this.volum = volum;
    }
}
