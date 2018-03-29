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
    public ServerConnection(String data, String url, String type, Handler threadHandler){

        this.data = data;
        this.url = url;
        this.threadHandler = threadHandler;
        this.type = type;


    }


    public void run(){


        String result = sendToServer(type);
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
}
