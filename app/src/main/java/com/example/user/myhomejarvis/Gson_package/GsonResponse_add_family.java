package com.example.user.myhomejarvis.Gson_package;

import com.example.user.myhomejarvis.Data_Info_package.FindFamilyVO;

import java.io.Serializable;

/**
 * Created by Kyun on 2018-03-28.
 */

public class GsonResponse_add_family implements Serializable {

    private String event;
    private FindFamilyVO familyInfo;

   public String getEvent() { return event; }

   public void setEvent(String event) { this.event = event; }

   public FindFamilyVO getFamilyinfo() {return familyInfo;}

   public void setFamilyinfo(FindFamilyVO familyInfo) {this.familyInfo = familyInfo;}




}
