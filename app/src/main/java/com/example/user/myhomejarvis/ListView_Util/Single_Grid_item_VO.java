package com.example.user.myhomejarvis.ListView_Util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 2018-03-27.
 */

public class Single_Grid_item_VO implements Serializable{

//    ArrayList<String> items;
//
//    public ArrayList<String> getItems() {
//        return items;
//    }
//
//    public void setItems(ArrayList<String> items) {
//        this.items = items;
//    }

    String content_text;

    public Single_Grid_item_VO(String content_text) {
        this.content_text = content_text;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }
}
