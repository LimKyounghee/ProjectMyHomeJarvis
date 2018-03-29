package com.example.user.myhomejarvis.Data_Info_package;

import java.io.Serializable;

/**
 * Created by user on 2018-03-28.
 */

public class DeviceVO implements Serializable{

    int device_no;
    String device_Model;
    double height;
    double width;
    double length;
    double weight;
    String device_Image;
    String device_regist_date;
    String register;
    String brand_ID;
    String category_ID;




    public DeviceVO(int device_no, String device_Model, double height, double width, double length, double weight,
                    String device_Image, String device_regist_date, String register, String brand_ID, String category_ID) {
        this.device_no = device_no;
        this.device_Model = device_Model;
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.device_Image = device_Image;
        this.device_regist_date = device_regist_date;
        this.register = register;
        this.brand_ID = brand_ID;
        this.category_ID = category_ID;
    }
    public int getDevice_no() {
        return device_no;
    }
    public void setDevice_no(int device_no) {
        this.device_no = device_no;
    }
    public String getDevice_Model() {
        return device_Model;
    }
    public void setDevice_Model(String device_Model) {
        this.device_Model = device_Model;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getLength() {
        return length;
    }
    public void setLength(double length) {
        this.length = length;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public String getDevice_Image() {
        return device_Image;
    }
    public void setDevice_Image(String device_Image) {
        this.device_Image = device_Image;
    }
    public String getDevice_regist_date() {
        return device_regist_date;
    }
    public void setDevice_regist_date(String device_regist_date) {
        this.device_regist_date = device_regist_date;
    }
    public String getRegister() {
        return register;
    }
    public void setRegister(String register) {
        this.register = register;
    }
    public String getBrand_ID() {
        return brand_ID;
    }
    public void setBrand_ID(String brand_ID) {
        this.brand_ID = brand_ID;
    }
    public String getCategory_ID() {
        return category_ID;
    }
    public void setCategory_ID(String category_ID) {
        this.category_ID = category_ID;
    }

    @Override
    public String toString() {
        return
                "\n제품 번호 = " + device_no +
                "\n모델명 = " + device_Model +
                "\n높이 = " + height +
                "\n넓이 = " + width +
                "\n길이 = " + length +
                "\n무게 = " + weight +
                "\ndevice_Image='" + device_Image;
    }
}

