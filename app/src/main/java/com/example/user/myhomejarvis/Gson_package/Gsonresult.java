package com.example.user.myhomejarvis.Gson_package;

import android.util.Log;

import com.example.user.myhomejarvis.Data_Info_package.DeviceVO;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.google.gson.Gson;

/**
 * Created by user on 2018-03-21.
 */

public class Gsonresult {

    private static final String TAG = "Gson_result";

    public GsonResponse_Join getStatus_join(String result){
        Gson gson = new Gson();
        GsonResponse_Join gsonResponse = null;

        try {
            gsonResponse =  gson.fromJson(result,GsonResponse_Join.class);


            Log.d(TAG,"GsonResponse_Join fromjson 성공함");


        }catch (Exception e){

            e.printStackTrace();
        }

        return  gsonResponse;

    }

    public GsonResponse_Login getStatus_login(String result){
        Gson gson = new Gson();
        GsonResponse_Login gsonResponse = null;

        try {
            gsonResponse =  gson.fromJson(result,GsonResponse_Login.class);

            Log.d(TAG,"GsonResponse_Login fromjson 성공함");


        }catch (Exception e){

            e.printStackTrace();
        }

        return  gsonResponse;

    }

    public GsonResponse_add_device getResponse_add_device(String result){

        Gson gson = new Gson();
        GsonResponse_add_device gsonResponse_add_device = null;

        try {

            gsonResponse_add_device = gson.fromJson(result, GsonResponse_add_device.class);

            Log.d(TAG, "gsonResponse_add_device fromJson 성공함");

        }catch (Exception e){
            e.printStackTrace();
        }

        return  gsonResponse_add_device;
    }

    public GsonResponse_Config_device getResponse_config_home(String result){

        Gson gson = new Gson();
        GsonResponse_Config_device gsonResponse_config_device =null;

        try{
            gsonResponse_config_device = gson.fromJson(result, GsonResponse_Config_device.class);

            Log.d(TAG,"gsonResponse_config_device fromjson 성공함");
        }catch (Exception e){
            e.printStackTrace();
        }
        return gsonResponse_config_device;
    }

    public GsonResponse_add_family getResponse_add_family(String result){

        Gson gson = new Gson();
        GsonResponse_add_family gsonResponse_add_family = null;

        try {

            gsonResponse_add_family = gson.fromJson(result, GsonResponse_add_family.class);

            Log.d(TAG, "gsonResponse_add_family fromJson 성공함");

        }catch (Exception e){
            e.printStackTrace();
        }

        return  gsonResponse_add_family;
    }

    public DeviceVO getDeviceDetail(String result){

        Gson gson = new Gson();
        DeviceVO deviceVO = null;

        try{
            deviceVO = gson.fromJson(result, DeviceVO.class);
            Log.d(TAG, "deviceVO fromJson 성공함");
        }catch (Exception e){
            e.printStackTrace();
        }

        return  deviceVO;
    }
}


