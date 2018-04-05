package com.example.user.myhomejarvis.Data_Info_package;

<<<<<<< HEAD
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
=======
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
>>>>>>> f06e8f72d0fe402c5166c21dc377537057421a27

/**
 * Created by user on 2018-03-29.
 */

public class Config_device implements Serializable{

    private String deviceID;
    private String deviceStatus;
    int function;
    private String device_Image;

    public Config_device () {

    }
    public Config_device (Parcel in) {

        deviceID = in.readString();
        deviceStatus = in.readString();
        function = in.readInt();
        device_Image = in.readString();
    }

    public Config_device(String deviceID, String deviceStatus, int function, String device_Image) {
        this.deviceID = deviceID;
        this.deviceStatus = deviceStatus;
        this.function = function;
        this.device_Image = device_Image;

    }


    public String getDeviceID() {
        return deviceID;
    }
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
    public String getDeviceStatus() {
        return deviceStatus;
    }
    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
    public int getFunction() {
        return function;
    }
    public void setFunction(int function) {
        this.function = function;
    }
    public String getDevice_Image() {
        return device_Image;
    }
    public void setDevice_Image(String device_Image) {
        this.device_Image = device_Image;
    }

    @Override
    public String toString() {
        return "Config_device{" +
                "deviceID='" + deviceID + '\'' +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", function=" + function +
                ", device_Image='" + device_Image + '\'' +
                '}';
    }
}

