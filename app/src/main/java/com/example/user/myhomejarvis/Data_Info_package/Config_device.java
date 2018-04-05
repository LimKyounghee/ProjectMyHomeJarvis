package com.example.user.myhomejarvis.Data_Info_package;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 2018-03-29.
 */

public class Config_device {

    private String deviceID;
    private String deviceStatus;
    int function;
    private String device_Image;

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

}
