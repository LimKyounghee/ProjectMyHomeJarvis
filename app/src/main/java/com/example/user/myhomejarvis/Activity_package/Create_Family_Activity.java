package com.example.user.myhomejarvis.Activity_package;

/**
 * Created by Kyun on 2018-03-27.
 */

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.FindFamilyVO;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_add_family;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.LogManager;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.example.user.myhomejarvis.Server_Connection_package.Util;

import java.util.HashMap;
import java.util.Map;

public class Create_Family_Activity extends AppCompatActivity {
    Bundle bundle;
    EditText family_name_edit, family_image_edit;
    String result;
    UserInfoVO vo;
    ThreadHandler threadHandler = new ThreadHandler();

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create_family_btn:
                    String familyName = family_name_edit.getText().toString();
                    String familyIcon = "0000";
                    LogManager.print("familyName : " + familyName);
                    doSendToServer(familyName, familyIcon);


                    break;
            }
        }
    };

    public void doSendToServer(String familyName, String familyIcon) {
        ServerConnectionWithoutHandler serverCon;
        String data1 = familyName;
        String data2 = familyIcon;
        String data3 = vo.getUserID();
        String type1 = "familyName";
        String type2 = "familyIcon";
        String type3 = "userID";
        String url = Server_URL.getInsertFamily_URL();


        try{
            serverCon = new ServerConnectionWithoutHandler(data1, data2, data3, type1, type2, type3, url);
            LogManager.print("쓰래드 시작");
            serverCon.start();
//            result = serverCon.get

//            while (result== null) {
//                int count = 0;
//                count++;
//                LogManager.print("count : " + count);
//            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ServerConnectionWithoutHandler extends Thread {

//        private final static String TAG = "ServerConnection";

        String data1;
        String data2;
        String data3;
        String url;
        String type1;
        String type2;
        String type3;

        public ServerConnectionWithoutHandler(String data1, String data2, String data3, String type1, String type2, String type3, String url) {
            this.data1 = data1;
            this.data2 = data2;
            this.data3 = data3;
            this.type1 = type1;
            this.type2 = type2;
            this.type3 = type3;
            this.url = url;
        }

        public void run() {
            result = sendToServer(type1, type2, type3);
            LogManager.print("서버로 보낸 결과 : " + result);

            Message message = new Message();
            Bundle b = message.getData();
            b.putString("data", result);

            threadHandler.sendMessage(message);
            LogManager.print("쓰레드 핸들러 들어감 " + result);
        }

        String sendToServer(String type1, String type2, String type3){

            Map<String, String> params = new HashMap<String, String>();
            params.put(type1,data1);
            params.put(type2,data2);
            params.put(type3,data3);
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

    class ThreadHandler extends Handler {

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
                    LogManager.print("result : " + result);
                    //Gson으로 문자열 객체로 전달하기
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_add_family gsonResponse;

                    gsonResponse = gsonresult.getResponse_add_family(result);

                    FindFamilyVO findFamilyVO = gsonResponse.getFamilyinfo();
                    LogManager.print("findFamilyVO 객체 저장"+findFamilyVO.toString());

                    if(gsonResponse == null){
                        LogManager.print("gsonResponse null");
                    }else {
                        String getEvent = gsonResponse.getEvent();

                        if (getEvent.equals("Insertfamily")) {
                            if (findFamilyVO == null) {
                                Toast.makeText(getApplicationContext(), "가족 만들기에 실패했습니다.", Toast.LENGTH_LONG).show();
                            } else {

                                // userVO를 수정해서 새로운 번들에 저장해서 이전 액티비티로 다시 전달한다.
                                UserInfoVO vo = (UserInfoVO) bundle.getSerializable("UserInfoVO");
                                vo.setFamilyID(findFamilyVO.getFamilyID());
                                Bundle new_bundle = new Bundle();
                                new_bundle.putSerializable("UserInfoVO", vo);

                                //SaredPreference에  값 저장하기
                                SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("familyID", findFamilyVO.getFamilyID());
                                editor.commit();


                                Intent intent = new Intent();
                                intent.putExtra("User_Info", new_bundle);
                                setResult(RESULT_OK, intent);

                                finish();
                            }
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
        setContentView(R.layout.create_family);

        bundle = getIntent().getBundleExtra("User_Info");
        vo = (UserInfoVO)bundle.getSerializable("UserInfoVO") ;

        family_name_edit = findViewById(R.id.create_family_edit);
        family_image_edit = findViewById(R.id.create_family_image);

        findViewById(R.id.create_family_btn).setOnClickListener(handler);
    }
}