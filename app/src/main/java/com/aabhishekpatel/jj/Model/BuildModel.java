package com.aabhishekpatel.jj.Model;

public class BuildModel {

    private String buildid;
    private String buildimage1;
    private String buildimage2;
    private String buildimage3;
    private String buildimage4;
    private String buildname;
    private String buildprice;
    private String buildphone;


    public BuildModel(String buildid, String buildimage1, String buildimage2, String buildimage3, String buildimage4,String buildname, String buildprice, String buildphone) {
        this.buildid = buildid;
        this.buildimage1 = buildimage1;
        this.buildimage2 = buildimage2;
        this.buildimage3 = buildimage3;
        this.buildimage4 = buildimage4;
        this.buildname = buildname;
        this.buildprice = buildprice;
        this.buildphone = buildphone;

    }

    public BuildModel() {
    }

    public String getBuildid() {
        return buildid;
    }

    public void setBuildid(String buildid) {
        this.buildid = buildid;
    }

    public String getBuildimage1() {
        return buildimage1;
    }

    public void setBuildimage1(String buildimage1) {
        this.buildimage1 = buildimage1;
    }

    public String getBuildimage2() {
        return buildimage2;
    }

    public void setBuildimage2(String buildimage2) {
        this.buildimage2 = buildimage2;
    }

    public String getBuildimage3() {
        return buildimage3;
    }

    public void setBuildimage3(String buildimage3) {
        this.buildimage3 = buildimage3;
    }

    public String getBuildimage4() {
        return buildimage4;
    }

    public void setBuildimage4(String buildimage4) {
        this.buildimage4 = buildimage4;
    }

    public String getBuildname() {
        return buildname;
    }

    public void setBuildname(String buildname) {
        this.buildname = buildname;
    }

    public String getBuildprice() {
        return buildprice;
    }

    public void setBuildprice(String buildprice) {
        this.buildprice = buildprice;
    }

    public String getBuildphone() {
        return buildphone;
    }

    public void setBuildphone(String buildphone) {
        this.buildphone = buildphone;
    }
}
