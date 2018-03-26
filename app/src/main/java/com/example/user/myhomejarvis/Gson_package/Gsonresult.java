package com.example.user.myhomejarvis.Gson_package;

import android.util.Log;

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
        UserInfoVO vo = null;

        try {
            gsonResponse =  gson.fromJson(result,GsonResponse_Login.class);

            Log.d(TAG,"GsonResponse_Login fromjson 성공함");


        }catch (Exception e){

            e.printStackTrace();
        }

        return  gsonResponse;

    }
}
