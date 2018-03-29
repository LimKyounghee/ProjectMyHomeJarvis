package com.example.user.myhomejarvis.Gson_package;

import com.example.user.myhomejarvis.Data_Info_package.Config_device;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 2018-03-29.
 */

public class GsonResponse_Config_device implements Serializable {

    String event;
    ArrayList<Config_device> data;

    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public ArrayList<Config_device> getData() {
        return data;
    }
    public void setData(ArrayList<Config_device> data) {
        this.data = data;
    }
}
