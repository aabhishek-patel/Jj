package com.aabhishekpatel.jj.Model;

public class HousesModel {

    private String housesid;
    private String housesimage1;
    private String housesimage2;
    private String housesimage3;
    private String housesimage4;
    private String housesname;
    private String housesprice;
    private String housesphone;



    public HousesModel(String housesid, String housesimage1,String housesimage2,String housesimage3,String housesimage4, String housesname, String housesprice, String housesphone) {
        this.housesid = housesid;
        this.housesimage1 = housesimage1;
        this.housesimage2 = housesimage2;
        this.housesimage3 = housesimage3;
        this.housesimage4 = housesimage4;
        this.housesname = housesname;
        this.housesprice = housesprice;
        this.housesphone = housesphone;
    }

    public HousesModel() {
    }

    public String getHousesid() {
        return housesid;
    }

    public void setHousesid(String housesid) {
        this.housesid = housesid;
    }

    public String getHousesimage1() {
        return housesimage1;
    }

    public void setHousesimage1(String housesimage1) {
        this.housesimage1 = housesimage1;
    }

    public String getHousesimage2() {
        return housesimage2;
    }

    public void setHousesimage2(String housesimage2) {
        this.housesimage2 = housesimage2;
    }

    public String getHousesimage3() {
        return housesimage3;
    }

    public void setHousesimage3(String housesimage3) {
        this.housesimage3 = housesimage3;
    }

    public String getHousesimage4() {
        return housesimage4;
    }

    public void setHousesimage4(String housesimage4) {
        this.housesimage4 = housesimage4;
    }

    public String getHousesname() {
        return housesname;
    }

    public void setHousesname(String housesname) {
        this.housesname = housesname;
    }

    public String getHousesprice() {
        return housesprice;
    }

    public void setHousesprice(String housesprice) {
        this.housesprice = housesprice;
    }

    public String getHousesphone() {
        return housesphone;
    }

    public void setHousesphone(String housesphone) {
        this.housesphone = housesphone;
    }
}
