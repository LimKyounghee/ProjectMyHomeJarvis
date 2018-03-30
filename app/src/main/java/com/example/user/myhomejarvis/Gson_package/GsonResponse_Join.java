package com.example.user.myhomejarvis.Gson_package;

import java.io.Serializable;

/**
 * Created by user on 2018-03-21.
 */


public class GsonResponse_Join implements Serializable {

    private String event;
    private String status;


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}