package com.example.konte2022;

public class Lager {private int LID;
    private String navn;
    private String adresse;

    public Lager(int LID, String navn, String adresse) {
        this.LID = LID;
        this.navn = navn;
        this.adresse = adresse;
    }

    public Lager() { }

    public int getLID() {
        return LID;
    }

    public void setLID(int LID) {
        this.LID = LID;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
