package com.example.user.myhomejarvis.Data_Info_package;

import java.util.ArrayList;

/**
 * Created by user on 2018-04-01.
 */

public class UserInfoListData {

    String event;
    ArrayList<UserInfoVO> userInfoList;

    public UserInfoListData(String event, ArrayList<UserInfoVO> userInfoList) {
        this.event = event;
        this.userInfoList = userInfoList;
    }
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public ArrayList<UserInfoVO> getUserInfoList() {
        return userInfoList;
    }
    public void setUserInfoList(ArrayList<UserInfoVO> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
