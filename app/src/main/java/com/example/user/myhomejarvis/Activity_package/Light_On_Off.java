package com.example.user.myhomejarvis.Activity_package;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.user.myhomejarvis.Data_Info_package.LED_Data;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.gson.Gson;

/**
 * Created by user on 2018-03-22.
 */

public class Light_On_Off extends AppCompatActivity {

    private static final String TAG = "Light_On_Off";

    String url = Server_URL.getLed_URL();


    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button button = (Button) v;
            String button_text = button.getText().toString();

            switch (v.getId()){

                case R.id.button_light1:

                    doLight("device_1",button_text);
                    break;

                    //디바이스 아이디 바꾸기 나중에!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                case R.id.button_light2:
                    doLight("device_2",button_text);
                    break;

                case R.id.button_light3:
                    doLight("device_3",button_text);
                    break;

                case R.id.button_light4:
                    doLight("device_4",button_text);
                    break;
            }
        }
    };

    void doLight(String buttonID,String button_text){

        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

//        String url = "http://192.168.0.8:8080/JavisProject/light.do";//나중에 .do 크래스 만들자
        String type = "led_onoff";

        Bundle bundle = getIntent().getBundleExtra("User_Info");
        bundle.getSerializable("UserInfoVO");
        //번들로 정보 받아오기
        //그리고 유저 아이디 뽑아오자
        UserInfoVO userInfoVO = (UserInfoVO) bundle.getSerializable("UserInfoVO");
        Log.d(TAG,"유저 VO 받아옴 = "+ userInfoVO.toString());

        String userID;

        userID =  userInfoVO.getUserID();//유저 아이디 받아온당

        Log.d(TAG,"유저 아이디 = " +userInfoVO.toString());

        LED_Data led_data = new LED_Data();
        led_data.setUserId(userID);
        led_data.setDeviceId(buttonID);
        led_data.setEventInfo(button_text);

        Log.d(TAG,"LED Dagta 에 저장" + led_data.toString());

        Gson gson = new Gson();
        String led_data_json = gson.toJson(led_data);
        Log.d(TAG,"json 으로 바꿈 "+led_data_json);
        // 서버에 정보 보낸다.

        try {
            serverConnection = new ServerConnection(led_data_json, url,type, threadHandler);
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


            Button button = findViewById(R.id.button_light1);
            //버튼을 여러개 연결을 어떻게 효울적으로 할 수 있으락?



            if(msg != null){
                Bundle b = msg.getData();
                String result = b.getString("data");
                String status ="";
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
                        //결과를 바탕으로 메세지 박스를 만든다.
                        String getEvent = gsonResponse_join.getEvent();
                        String getStatus = gsonResponse_join.getStatus();
                        Log.d(TAG,"from json 값"+gsonResponse_join);

                        switch (getEvent){

                            case "lightOnOff":

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_on_off);
        Log.d(TAG,"전구 페이지로 넘어감!?");


        findViewById(R.id.button_light1).setOnClickListener(handler);
        findViewById(R.id.button_light2).setOnClickListener(handler);
        findViewById(R.id.button_light3).setOnClickListener(handler);
        findViewById(R.id.button_light4).setOnClickListener(handler);
    }
}
