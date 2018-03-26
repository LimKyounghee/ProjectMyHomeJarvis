package com.example.user.myhomejarvis.Data_Info_package;

/**
 * Created by user on 2018-03-22.
 */

public class LED_Data {
    String userId;
    String deviceId;
    String eventInfo;

    public LED_Data() {};

    public LED_Data(String userId, String deviceId, String eventInfo) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.eventInfo = eventInfo;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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


}