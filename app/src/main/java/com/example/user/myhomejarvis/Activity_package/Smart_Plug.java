package com.example.user.myhomejarvis.Activity_package;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.user.myhomejarvis.Data_Info_package.Smart_Plug_Data;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.gson.Gson;

/**
 * Created by user on 2018-03-23.
 */

public class Smart_Plug extends AppCompatActivity {

    private static final String TAG = "Smart_Pluge";

    String url = Server_URL.getSmartplug_URL();

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button button = (Button)v;
            String button_text = button.getText().toString();

            switch(v.getId()){

                case R.id.button_smart_pluge1:

                    doSmartPlug("device_2",button_text);
                    break;

            }
        }
    };

    void doSmartPlug(String buttonID, String button_text){

        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

        String type = "plug_onoff";

        Bundle bundle = getIntent().getBundleExtra("User_Info");
        bundle.getSerializable("UserInfoVO");
        UserInfoVO userInfoVO = (UserInfoVO)bundle.getSerializable("UserInfoVO");
        Log.d(TAG,"유저 VO 받아옴 = "+ userInfoVO.toString());

        String userID;

        userID =  userInfoVO.getUserID();//유저 아이디 받아온당

        Log.d(TAG,"유저 아이디 = " +userInfoVO.toString());

        Smart_Plug_Data smart_plug_data = new Smart_Plug_Data();
        smart_plug_data.setUserId(userID);
        smart_plug_data.setDeviceId(buttonID);
        smart_plug_data.setEventInfo(button_text);

        Log.d(TAG,"PLug Dagta 에 저장" + smart_plug_data.toString());

        Gson gson = new Gson();
        String smart_plug_data_json = gson.toJson(smart_plug_data);
        Log.d(TAG,"json 으로 바꿈 "+smart_plug_data_json);

        try{

            serverConnection = new ServerConnection(smart_plug_data_json,url,type,threadHandler);
            Log.d(TAG, "서버랑 연결");
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

            Button button = findViewById(R.id.button_smart_pluge1);

            if(msg != null){

                Bundle b = msg.getData();
                String result = b.getString("data");
                String status;
                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(), "서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{

                    //Gson으로 문자열 객체로 전달하ㅣㄱ
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse_join;
                    Log.d(TAG,"내가 받은 값" + result);

                    gsonResponse_join = gsonresult.getStatus_join(result);

                    Log.d(TAG,"from json 값"+gsonResponse_join);

                    if(gsonResponse_join == null){
                        Log.d(TAG,"gsonResponse_join 이 널값임");
                    }else{
                        String getEvent = gsonResponse_join.getEvent();
                        String getStatus = gsonResponse_join.getStatus();
                        Log.d(TAG,"from json 값"+gsonResponse_join);

                        //나중에 버튼별로 다르게 하는것을 해보자
                        // 로직 이쁘게 ..

                        switch (getEvent){

                            case "plugOnOff":

                                switch (getStatus){

                                    case "ON":
                                        button.setText(R.string.OFF);
                                        break;

                                    case "OFF":
                                        button.setText(R.string.ON);
                                        break;


                                }
                                break;

                        }
                    }

                }
            }

        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_pluge_on_off);

        findViewById(R.id.button_smart_pluge1).setOnClickListener(handler);


    }
}
