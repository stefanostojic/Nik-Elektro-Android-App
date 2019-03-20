package com.example.importingexcell;

public class Proizvod {

    private String id;
    private String ime;
    private String kolicina;
    private String sabiranje;
    private String staraKolicina;

    public Proizvod(String id, String ime, String popisanaKolicina, String sabiranje, String staraKolicina) {
        this.id = id;
        this.ime = ime;
        this.kolicina = popisanaKolicina;
        this.sabiranje = sabiranje;
        this.staraKolicina = staraKolicina;
    }

    public String getStaraKolicina() {
        return staraKolicina;
    }

    public void setStaraKolicina(String staraKolicina) {
        this.staraKolicina = staraKolicina;
    }


    public String getKolicina() {
        return kolicina;
    }

    public void setKolicina(String kolicina) {
        this.kolicina = kolicina;
    }




    public String getSabiranje() {
        return sabiranje;
    }

    public void setSabiranje(String sabiranje) {
        this.sabiranje = sabiranje;
    }


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



}
