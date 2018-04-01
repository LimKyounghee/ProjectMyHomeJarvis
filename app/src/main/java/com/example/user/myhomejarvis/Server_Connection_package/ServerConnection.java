package com.example.user.myhomejarvis.Server_Connection_package;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2018-03-22.
 */

public class ServerConnection extends  Thread{
    //이걸 쓰려면 인자를 세개를 받는당

    private final static String TAG = "ServerConnection";

    String data;
    String url;
    String type;
    Handler threadHandler;
    String data1;
    String data2;
    String type1;
    String type2;
    public ServerConnection(String data, String url, String type, Handler threadHandler){

        this.data = data;
        this.url = url;
        this.threadHandler = threadHandler;
        this.type = type;
    }

    public ServerConnection(String data1, String data2, String type1, String type2, String url, Handler threadHandler) {
        this.data1 = data1;
        this.data2 = data2;
        this.type1 = type1;
        this.type2 = type2;
        this.url = url;
        this.threadHandler = threadHandler;
    }


    public void run(){
        String result = "";
        if (data2==null) {
            result = sendToServer(type);
        } else {
            result = sendToServer(type1, type2);
        }

        Log.d(TAG,"서버로 보낸 결과 " + result);

        Message message = new Message();
        Bundle b = message.getData();
        b.putString("data", result);
        threadHandler.sendMessage(message);
        Log.d(TAG,"쓰레드 핸들러 들어감 " + result);


    }

    String sendToServer(String type){

        Map<String, String> params = new HashMap<String, String>();
        params.put(type,data);
        Util util = new Util();
        String result ="";

        try{

            result = util.sendPost(url,params);
        }catch (Exception e){

            e.getStackTrace();
        }

        return  result;

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
}
