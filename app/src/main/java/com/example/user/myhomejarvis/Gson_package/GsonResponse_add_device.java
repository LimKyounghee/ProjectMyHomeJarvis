package com.example.user.myhomejarvis.Gson_package;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 2018-03-27.
 */

public class GsonResponse_add_device implements Serializable {

    String event;
    ArrayList<String> items;

    public GsonResponse_add_device(){}

    public GsonResponse_add_device(ArrayList<String> items) {
        this.items = items;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String category) {
        this.event = category;
    }

    @Override
    public String toString() {
        return "GsonResponse_add_device{" +
                "event='" + event + '\'' +
                ", items=" + items +
                '}';
    }
}
