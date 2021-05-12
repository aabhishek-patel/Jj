package com.aabhishekpatel.jj.Model;

public class InteriorModel {

    private String interiorid;
    private String interiorimage1;
    private String interiorimage2;
    private String interiorimage3;
    private String interiorimage4;
    private String interiorname;
    private String interiorprice;
    private String interiorphone;


    public InteriorModel(String interiorid, String interiorimage1, String interiorimage2, String interiorimage3, String interiorimage4, String interiorname, String interiorprice, String interiorphone) {
        this.interiorid = interiorid;
        this.interiorimage1 = interiorimage1;
        this.interiorimage2 = interiorimage2;
        this.interiorimage3 = interiorimage3;
        this.interiorimage4 = interiorimage4;
        this.interiorname = interiorname;
        this.interiorprice = interiorprice;
        this.interiorphone = interiorphone;

    }

    public InteriorModel() {
    }

    public String getInteriorid() {
        return interiorid;
    }

    public void setInteriorid(String interiorid) {
        this.interiorid = interiorid;
    }

    public String getInteriorimage1() {
        return interiorimage1;
    }

    public void setInteriorimage1(String interiorimage1) {
        this.interiorimage1 = interiorimage1;
    }

    public String getInteriorimage2() {
        return interiorimage2;
    }

    public void setInteriorimage2(String interiorimage2) {
        this.interiorimage2 = interiorimage2;
    }

    public String getInteriorimage3() {
        return interiorimage3;
    }

    public void setInteriorimage3(String interiorimage3) {
        this.interiorimage3 = interiorimage3;
    }

    public String getInteriorimage4() {
        return interiorimage4;
    }

    public void setInteriorimage4(String interiorimage4) {
        this.interiorimage4 = interiorimage4;
    }

    public String getInteriorname() {
        return interiorname;
    }

    public void setInteriorname(String interiorname) {
        this.interiorname = interiorname;
    }

    public String getInteriorprice() {
        return interiorprice;
    }

    public void setInteriorprice(String interiorprice) {
        this.interiorprice = interiorprice;
    }

    public String getInteriorphone() {
        return interiorphone;
    }

    public void setInteriorphone(String interiorphone) {
        this.interiorphone = interiorphone;
    }
}
