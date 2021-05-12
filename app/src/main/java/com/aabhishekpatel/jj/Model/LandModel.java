package com.aabhishekpatel.jj.Model;

public class LandModel {

    private String landid;
    private String landimage1;
    private String landimage2;
    private String landimage3;
    private String landimage4;
    private String landname;
    private String landprice;
    private String landphone;

    public LandModel(String landimage1) {

    }

    public LandModel(String landid, String landimage1,String landimage2,String landimage3,String landimage4, String landname, String landprice, String landphone) {
        this.landid = landid;
        this.landimage1 = landimage1;
        this.landimage2 = landimage2;
        this.landimage3 = landimage3;
        this.landimage4 = landimage4;
        this.landname = landname;
        this.landprice = landprice;
        this.landphone = landphone;
    }

    public LandModel() {
    }

    public String getLandid() {
        return landid;
    }

    public void setLandid(String landid) {
        this.landid = landid;
    }

    public String getLandimage1() {
        return landimage1;
    }

    public void setLandimage1(String landimage1) {
        this.landimage1 = landimage1;
    }

    public String getLandimage2() {
        return landimage2;
    }

    public void setLandimage2(String landimage2) {
        this.landimage2 = landimage2;
    }

    public String getLandimage3() {
        return landimage3;
    }

    public void setLandimage3(String landimage3) {
        this.landimage3 = landimage3;
    }

    public String getLandimage4() {
        return landimage4;
    }

    public void setLandimage4(String landimage4) {
        this.landimage4 = landimage4;
    }

    public String getLandname() {
        return landname;
    }

    public void setLandname(String landname) {
        this.landname = landname;
    }

    public String getLandprice() {
        return landprice;
    }

    public void setLandprice(String landprice) {
        this.landprice = landprice;
    }

    public String getLandphone() {
        return landphone;
    }

    public void setLandphone(String landphone) {
        this.landphone = landphone;
    }
}
