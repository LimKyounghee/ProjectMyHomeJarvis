package com.example.user.myhomejarvis.Data_Info_package;

/**
 * Created by user on 2018-03-20.
 */

public class Join_Info {

    private String userID;
    private String pw;
    private String name;
    private String birth;
    private String phone;
    private String email;
    private String fcmid;
    private String address;
    private int gender;


    public String getId() {
        return userID;
    }

    public void setId(String id) {
        this.userID = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFcmid() {
        return fcmid;
    }

    public void setFcmid(String fcmid) {
        this.fcmid = fcmid;
    }

    @Override
    public String toString() {
        return "Join_Info{" +
                "id='" + userID + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", fcmid='" + fcmid + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                '}';
    }
}
