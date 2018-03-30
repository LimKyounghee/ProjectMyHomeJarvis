package com.example.user.myhomejarvis.Fragment_package;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.On_Off_Control_Data;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.gson.Gson;

/**
 * Created by user on 2018-03-30.
 */

public class Config_myhome_fragmenr_fan_button extends Fragment{

    String userID;//받아오기
    private static final String TAG = "fragmenr_fan_button";
    String url;
    String deviceID;//받아오기
    String type;//받아오기 = 여긴ㄴ 그냥 지정


    public Config_myhome_fragmenr_fan_button() {
    }

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.button_fan_weak :
                    doServerConnect(deviceID,"약");
                    break;

                case R.id.button_fan_strong :
                    doServerConnect(deviceID,"강");
                    break;

                case R.id.button_fan_mid :
                    doServerConnect(deviceID,"중");
                    break;

                case R.id.button_fan_off :
                    doServerConnect(deviceID,"꺼짐");
                    break;

            }
        }
    };


    void doServerConnect(String deviceID, String request_Status){

        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

        On_Off_Control_Data on_off_control_data = new On_Off_Control_Data();
        on_off_control_data.setUserid(userID);
        on_off_control_data.setDeviceId(deviceID);
        on_off_control_data.setEventInfo(request_Status);

        Log.d(TAG," On_Off_Control_Data 에 저장" + on_off_control_data.toString());

        Gson gson = new Gson();
        String on_off_data_json = gson.toJson(on_off_control_data);

        try{

            serverConnection = new ServerConnection(on_off_data_json,url,type, threadHandler);
            Log.d(TAG,"서버랑 연결");
            serverConnection.start();
        }catch(Exception e){

            e.printStackTrace();

        }
    }

    class ThreadHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"핸들러 들어옴 연결");

            if(msg != null){

                Bundle b = msg.getData();
                String result = b.getString("data");

                if(("").equals(result)){
                    Log.d(TAG,"서버와 통실 실패");

                }else{

                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse_join;
                    Log.d(TAG,"내가 받은 값 " + result);

                    gsonResponse_join = gsonresult.getStatus_join(result);
                    Log.d(TAG,"from json 값" + gsonResponse_join);

                    if(gsonResponse_join == null) {
                        Log.d(TAG, "gsonResponse_join 이 널값임");

                    }else{

                        String getEvent = gsonResponse_join.getEvent();
                        String getStatus = gsonResponse_join.getStatus();
                        Log.d(TAG,"from json 값"+gsonResponse_join);

                        // 이게 끝나고 어떻게 할 ㅅ것인가...


                    }


                }
            }
        }
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.config_myhome_fragement_fan_button, container, false);

        userID = getArguments().getString("userID");
        deviceID = getArguments().getString("deviceID");
        Log.d(TAG,deviceID+"0000000000000000000000000000"+userID);
        type = "fan_control";
        url = Server_URL.getFan_onoff_url();

        view.findViewById(R.id.button_fan_weak).setOnClickListener(handler);
        view.findViewById(R.id.button_fan_strong).setOnClickListener(handler);
        view.findViewById(R.id.button_fan_mid).setOnClickListener(handler);
        view.findViewById(R.id.button_fan_off).setOnClickListener(handler);

        return view;

    }
}
