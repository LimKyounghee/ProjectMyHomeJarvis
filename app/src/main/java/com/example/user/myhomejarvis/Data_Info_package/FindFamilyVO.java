package com.example.user.myhomejarvis.Data_Info_package;

import java.io.Serializable;

/**
 * Created by Kyun on 2018-03-28.
 */

public class FindFamilyVO implements Serializable{
    private int familyID;
    private String familyName;
    private String familyIcon;
    private String familyRegist;

    public FindFamilyVO() {}

    public FindFamilyVO(int familyID, String familyName, String familyIcon, String familyRegist) {
        this.familyID = familyID;
        this.familyName = familyName;
        this.familyIcon = familyIcon;
        this.familyRegist = familyRegist;
    }

    public FindFamilyVO(String familyName, String familyIcon, int familyID) {
        this.familyID = familyID;
        this.familyName = familyName;
        this.familyIcon = familyIcon;

    }

    public FindFamilyVO(String familyName, String familyIcon) {
        this.familyName = familyName;
        this.familyIcon = familyIcon;

    }

    public int getFamilyID() {
        return familyID;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getFamilyIcon() {
        return familyIcon;
    }

    public String getFamilyRegist() {
        return familyRegist;
    }

    public void setFamilyIcon(String familyIcon) {
        this.familyIcon = familyIcon;
    }

    public void setFamilyRegist(String familyRegist) {
        this.familyRegist = familyRegist;
    }

    public void setFamilyID(int familyID) {
        this.familyID = familyID;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public String toString() {
        return "FindFamilyVO{" +
                "familyID=" + familyID +
                ", familyName='" + familyName + "\'" +
                ", familyIcon='" + familyIcon + "\'" +
                ", familyRegist='" + familyRegist + "\'" +
                '}';
    }
}
