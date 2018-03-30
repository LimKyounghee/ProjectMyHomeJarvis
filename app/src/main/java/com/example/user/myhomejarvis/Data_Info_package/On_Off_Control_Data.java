package com.example.user.myhomejarvis.Data_Info_package;

/**
 * Created by user on 2018-03-29.
 */

public class On_Off_Control_Data {

    String userId;;
    String deviceId;
    String eventInfo;

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userid) {
        this.userId = userid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    @Override
    public String toString() {
        return "On_Off_Control_Data{" +
                "userid='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", eventInfo='" + eventInfo + '\'' +
                '}';
    }
}
