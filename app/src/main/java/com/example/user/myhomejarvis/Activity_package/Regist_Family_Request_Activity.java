package com.example.user.myhomejarvis.Activity_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.LogManager;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.example.user.myhomejarvis.Server_Connection_package.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyun on 2018-03-29.
 */

public class Regist_Family_Request_Activity extends AppCompatActivity {
    String userID;
    String familyID;
    String result;
    ThreadHandler2 threadHandler2 = new ThreadHandler2();

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String data = "";
            switch (v.getId()) {
                case R.id.accept_btn:
                    data = "accept";
                    doSendToServer(data);
                    break;
                case R.id.reject_btn:
                    data = "reject";
                    doSendToServer(data);
                    break;
            }
        }
    };

    public void doSendToServer(String data) {
        ServerConnection serverCon;
        String data1 = data;
        String data2 = userID;
        String data3 = familyID;
        String type1 = "returnToServerForRegistFamily";
        String type2 = "userID";
        String type3 = "familyID";
        String url = Server_URL.getReturnFamilyRegist_URL();

        try {
            serverCon = new ServerConnection(data1, data2, data3, type1, type2, type3, url);
            LogManager.print("쓰래드 시작");
            serverCon.start();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    class ServerConnection extends Thread {

        private final static String TAG = "ServerConnection";

        String data1;
        String data2;
        String data3;
//        int data4;
        String url;
        String type1;
        String type2;
        String type3;
//        String type4;
//        String result;

        public ServerConnection(String data1, String data2, String data3,
                                String type1, String type2, String type3, String url) {
            this.data1 = data1;
            this.data2 = data2;
            this.data3 = data3;
//            this.data4 = data4;
            this.type1 = type1;
            this.type2 = type2;
            this.type3 = type3;
//            this.type4 = type4;
            this.url = url;
        }

        public void run() {
            result = sendToServer();
            LogManager.print("서버로 보낸 결과 : " + result);

            Message message = new Message();
            Bundle b = message.getData();
            b.putString("data", result);
//            ThreadHandler2 threadHandler2 = new ThreadHandler2();
            threadHandler2.sendMessage(message);
            Log.d(TAG,"쓰레드 핸들러 들어감 " + result);
        }

        String sendToServer(){

            Map<String, String> params = new HashMap<String, String>();
            params.put(type1,data1);
            params.put(type2,data2);
            params.put(type3,data3);
//            params.put(type4,Integer.toString(data4));
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

    class ThreadHandler2 extends Handler {

        @Override
        public void handleMessage(Message msg) {
            LogManager.print("핸들러 연결");
            super.handleMessage(msg);

            if (msg != null) {

                Bundle b = msg.getData();
                String result = b.getString("data");
                if (("").equals(result)) {
                    Toast.makeText(getApplicationContext(), "서버와 통신 실패", Toast.LENGTH_SHORT).show();
                } else {
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse;

                    gsonResponse = gsonresult.getStatus_join(result);

                    if(gsonResponse == null) {
                        LogManager.print("gsonResponse null 값나옴");
                    }else{
                        //그래서 결과를 바탕으로 메세지 박스를 만든다.
                        String getEvent = gsonResponse.getEvent();
                        String getStatus = gsonResponse.getStatus();

                        if(getEvent.equals("FamilyRegistResult")){

                            if(getStatus.equals("accept")) {
                                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                                startActivity(intent);
                                finish();
                            }else if (getStatus.equals("reject")){
                                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "서버 오류로 가족 등록에 실패했습니다. 다시 요청을 기다려주세요", Toast.LENGTH_LONG).show();
                            }

                        }else {
                            if(getStatus.equals("accept")) {
                                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                                startActivity(intent);
                                finish();
                            }else if (getStatus.equals("reject")){
                                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "서버 오류로 가족 등록에 실패했습니다. 다시 요청을 기다려주세요", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.request_regist_family);

        // notification에서 intent에 저장한 정보를 불러온다.
        Intent intent = getIntent();
        String msg = intent.getStringExtra("message");
        userID = intent.getStringExtra("userID");
        familyID = intent.getStringExtra("familyID");
        LogManager.print("regist_family_request_activity // userID : " + userID + ", familyID : " + familyID);


        TextView request_text = findViewById(R.id.regist_family);
        request_text.setText(msg);

        findViewById(R.id.accept_btn).setOnClickListener(handler);
        findViewById(R.id.reject_btn).setOnClickListener(handler);

    }
}
