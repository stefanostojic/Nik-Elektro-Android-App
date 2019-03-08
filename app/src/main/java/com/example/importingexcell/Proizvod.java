package com.example.importingexcell;

public class Proizvod {

    private String id;
    private String ime;
    private String kolicina;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getKolicina() {
        return kolicina;
    }

    public void setKolicina(String kolicina) {
        this.kolicina = kolicina;
    }

    public Proizvod(String id, String ime, String kolicina) {
        this.id = id;
        this.ime = ime;
        this.kolicina = kolicina;
    }

}
