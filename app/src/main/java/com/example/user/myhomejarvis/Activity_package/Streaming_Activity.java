package com.example.user.myhomejarvis.Activity_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.LogManager;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;

/**
 * Created by Kyun on 2018-04-01.
 */

public class Streaming_Activity extends AppCompatActivity {
    WebView streamingView;
    Button watch_cctv_btn, open_door_btn, close_cctv_btn;
    String userID;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = "";
            switch(v.getId()) {
                case R.id.watch_cctv_btn:
                    ThreadHandlerCCTVOn threadHandler = new ThreadHandlerCCTVOn();
                    msg = "cctv_on";
                    Toast.makeText(getApplicationContext(), "스트리밍서버에 접속중입니다... 잠시만 기다려주세요.", Toast.LENGTH_LONG).show();
                    doSendToServer(msg, threadHandler);
                    break;
                case R.id.open_door_btn:
                    ThreadHandlerOpenDoor threadHandler2 = new ThreadHandlerOpenDoor();
                    msg = "open_door_and_cctv_off";
                    doSendToServer(msg, threadHandler2);
                    break;
                case R.id.close_cctv_btn:
                    ThreadHandlerCCTVOff threadHandler3 = new ThreadHandlerCCTVOff();
                    msg = "cctv_off";
                    doSendToServer(msg, threadHandler3);
                    break;
            }
        }
    };

    public void doSendToServer(String msg, Handler threadhandler) {
        ServerConnection serverConnection;
        String type1 = "doorRequest";
        String type2 = "userID";
        String data1 = msg;
        String data2 = userID;
        LogManager.print("doSendToServer msg : " + msg);
        String url = Server_URL.getDoorRequest_URL();

        try{
            serverConnection = new ServerConnection(data1, data2, type1, type2, url, threadhandler);
            LogManager.print("쓰래드 시작");
            serverConnection.start();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    class ThreadHandlerCCTVOn extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg != null){
                Bundle b = msg.getData();
                String result = b.getString("data");

                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{
                    //Gson으로 문자열 객체로 전달하기
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse;

                    gsonResponse = gsonresult.getStatus_join(result);

                    if(gsonResponse == null) {
                        LogManager.print("gsonResponse null 값나옴");
                    }else{
                        //그래서 결과를 바탕으로 메세지 박스를 만든다.
                        String getEvent = gsonResponse.getEvent();
                        String getStatus = gsonResponse.getStatus();

                        if(getEvent.equals("Streaming On")){

                            if(getStatus.equals("ok")){

                                try {
                                    Thread.sleep(5000);
                                }catch (InterruptedException e) {
                                    e.getStackTrace();
                                }
                                streamingView.loadUrl("http://192.168.0.15:5000/video_stream");
                                watch_cctv_btn.setVisibility(View.INVISIBLE);
                                open_door_btn.setVisibility(View.VISIBLE);
                                close_cctv_btn.setVisibility(View.VISIBLE);

                            }else{
                                Toast.makeText(getApplicationContext(), "서버와 접속이 끊겼습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), "서버와 접속이 끊겼습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }

    class ThreadHandlerOpenDoor extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg != null){
                Bundle b = msg.getData();
                String result = b.getString("data");

                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{
                    //Gson으로 문자열 객체로 전달하기
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse;

                    gsonResponse = gsonresult.getStatus_join(result);

                    if(gsonResponse == null) {
                        LogManager.print("gsonResponse null 값나옴");
                    }else{
                        //그래서 결과를 바탕으로 메세지 박스를 만든다.
                        String getEvent = gsonResponse.getEvent();
                        String getStatus = gsonResponse.getStatus();

                        if(getEvent.equals("Open Door")){

                            if(getStatus.equals("ok")){
//                                streamingView.loadUrl("http://192.168.0.15:5000/video_stream");
                                streamingView.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(getApplicationContext(), Project_Main.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "서버와 접속이 끊겼습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), "서버와 접속이 끊겼습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }

    class ThreadHandlerCCTVOff extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg != null){
                Bundle b = msg.getData();
                String result = b.getString("data");

                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{
                    //Gson으로 문자열 객체로 전달하기
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse;

                    gsonResponse = gsonresult.getStatus_join(result);

                    if(gsonResponse == null) {
                        LogManager.print("gsonResponse null 값나옴");
                    }else{
                        //그래서 결과를 바탕으로 메세지 박스를 만든다.
                        String getEvent = gsonResponse.getEvent();
                        String getStatus = gsonResponse.getStatus();

                        if(getEvent.equals("Streaming Off")){

                            if(getStatus.equals("ok")){
//                                streamingView.loadUrl("http://192.168.0.15:5000/video_stream");
                                streamingView.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(getApplicationContext(), Project_Main.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "서버와 접속이 끊겼습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), "서버와 접속이 끊겼습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.streaming);

//        Intent intent = getIntent();
//        familyID = intent.getStringExtra("familyID");
//        LogManager.print("regist_family_request_activity // familyID : " + familyID);

        SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
        userID = pref.getString("userID", "");
        LogManager.print("userID : " + userID);

        streamingView = findViewById(R.id.cctv_webView);
        watch_cctv_btn = findViewById(R.id.watch_cctv_btn);
        open_door_btn = findViewById(R.id.open_door_btn);
        close_cctv_btn = findViewById(R.id.close_cctv_btn);
        watch_cctv_btn.setOnClickListener(handler);
        open_door_btn.setOnClickListener(handler);
        close_cctv_btn.setOnClickListener(handler);

        open_door_btn.setVisibility(View.INVISIBLE);
        close_cctv_btn.setVisibility(View.INVISIBLE);



    }
}
