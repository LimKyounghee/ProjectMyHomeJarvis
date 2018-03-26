package com.example.user.myhomejarvis.Data_Info_package;

/**
 * Created by user on 2018-03-23.
 */
public class Smart_Plug_Data {
    String userId;
    String deviceId;
    String eventInfo;

    public Smart_Plug_Data() {};

    public Smart_Plug_Data(String userId, String deviceId, String eventInfo) {
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