package com.aabhishekpatel.jj.Model;

public class UserHelperClass {

    private String userid;
    private String username;
    private String phoneNo;
    private String profile;


    public UserHelperClass(String userid, String username, String phoneNo, String profile) {
        this.userid = userid;
        this.username = username;
        this.phoneNo = phoneNo;
        this.profile = profile;
    }



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
