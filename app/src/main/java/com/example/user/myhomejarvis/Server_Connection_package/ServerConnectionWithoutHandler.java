package com.example.user.myhomejarvis.Server_Connection_package;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyun on 2018-03-28.
 */

public class ServerConnectionWithoutHandler extends Thread{

    private final static String TAG = "ServerConnection";

    String data1;
    String data2;
    String url;
    String type1;
    String type2;
    String result;

    public ServerConnectionWithoutHandler(String data1, String data2, String type1, String type2, String url) {
        this.data1 = data1;
        this.data2 = data2;
        this.type1 = type1;
        this.type2 = type2;
        this.url = url;

    }

    public ServerConnectionWithoutHandler(String data1, String type1, String url) {
        this.data1 = data1;
        this.type1 = type1;
        this.url = url;

    }

    public void run() {

        if (data2==null) {
            result = sendToServer(type1);
        } else {
            result = sendToServer(type1, type2);
        }
        Log.d(TAG, "서버로 보낸 결과 " + result);
    }

    String sendToServer(String type1, String type2){

        Map<String, String> params = new HashMap<String, String>();
        params.put(type1,data1);
        params.put(type2,data2);
        Util util = new Util();
        String result ="";

        try{

            result = util.sendPost(url,params);
        }catch (Exception e){

            e.getStackTrace();
        }

        return  result;

    }

    String sendToServer(String type1){

        Map<String, String> params = new HashMap<String, String>();
        params.put(type1,data1);
        Util util = new Util();
        String result ="";

        try{

            result = util.sendPost(url,params);
        }catch (Exception e){

            e.getStackTrace();
        }

        return  result;

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
