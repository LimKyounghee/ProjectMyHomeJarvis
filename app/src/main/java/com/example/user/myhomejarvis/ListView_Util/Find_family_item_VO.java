package com.example.user.myhomejarvis.ListView_Util;

/**
 * Created by Kyun on 2018-03-27.
 */

public class Find_family_item_VO {
    String familyName;
    int resId;

    public Find_family_item_VO(String familyName, int resId) {
        this.familyName = familyName;
        this.resId = resId;
    }
    public Find_family_item_VO(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getResId() {
        return resId;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
