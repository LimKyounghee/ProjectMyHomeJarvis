package com.example.user.myhomejarvis.Data_Info_package;

import java.io.Serializable;

/**
 * Created by user on 2018-03-21.
 */

public class UserInfoVO implements Serializable{

    private int userNo;
    private String userID;
    private String pw;
    private String name;
    private String birth;
    private int gender;
    private String address;
    private String email;
    private String phone;
    private String fcmID;
    private String familyID;
    private String joinDate;

    public UserInfoVO(){

    }

    public UserInfoVO(String userID, String pw, String name, String birth, int gender, String address, String email,
                      String phone) {
        this.userID = userID;
        this.pw = pw;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public UserInfoVO(int userNo, String userID, String pw, String name, String birth, int gender, String address,
                      String email, String phone, String fcmID, String joinDate,String familyID) {
        this.userNo = userNo;
        this.userID = userID;
        this.pw = pw;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.fcmID = fcmID;
        this.joinDate = joinDate;
        this.familyID = familyID;
    }

    public String getFcmID() {
        return fcmID;
    }

    public void setFcmID(String fcmID) {
        this.fcmID = fcmID;
    }

    public String getFamilyID() {
        return familyID;
    }

    public void setFamilyID(String familyID) {
        this.familyID = familyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "userNo=" + userNo +
                ", userID='" + userID + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", fcmID='" + fcmID + '\'' +
                ", familyID='" + familyID + '\'' +
                ", joinDate='" + joinDate + '\'' +
                '}';
    }
}
