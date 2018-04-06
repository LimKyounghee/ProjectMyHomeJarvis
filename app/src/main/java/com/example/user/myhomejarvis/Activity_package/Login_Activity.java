package com.example.user.myhomejarvis.Activity_package;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.user.myhomejarvis.Data_Info_package.Login_Info;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Login;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

/**
 * Created by user on 2018-03-21.
 */

public class Login_Activity extends AppCompatActivity {

    public static final int REQUEST_CODE_MENUE = 102;

    public static final String TAG = "Login_Activity";
    String url = Server_URL.getLogin_URL();

    ThreadHandler threadHandler = new ThreadHandler();

    Login_Info login_info = new Login_Info();

    EditText login_ID;
    EditText login_PW;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////View.OnClickListener handler()

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){

                case R.id.button_find_ID:
                    doLogin();
                    break;

                case R.id.button_id_config:
                    doPageChange("id_config");
                    break;

                case R.id.button_pw_config:
                    doPageChange("pw_config");
                    break;

                case R.id.button_login_to_join:
                    doPageChange("login_to_join");
                    break;

            }
        }
    };
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////doPageChage()

    void doPageChange(String type){
        Intent intent;
        switch (type){

            case "id_config":
                intent = new Intent(getApplicationContext(),Find_ID_page.class);
//                startActivityForResult(intent,REQUEST_CODE_MENUE);
                startActivity(intent);
                finish();
                break;
                //여기는 아이디 찾기 페이지로 간다

            case "pw_config":
                intent = new Intent(getApplicationContext(),Find_PW_page.class);
//                startActivityForResult(intent,REQUEST_CODE_MENUE);
                startActivity(intent);
                finish();
                //여기는 비번 찾기 페이지로 간당
                break;

            case "login_to_join":
                intent = new Intent(getApplicationContext(),Join_Activity.class);
//                startActivityForResult(intent,REQUEST_CODE_MENUE);
                startActivity(intent);
                finish();
                //여기는 회원가입 페이지로 간당
                break;
        }



    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////doLogin()

    void doLogin(){

//        ConnectionThread thread;
//        String url = "http://192.168.0.8:8080/JavisProject/login.do";

        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

        String type = "login_info";

        login_ID = findViewById(R.id.editText_login_ID);
        login_PW = findViewById(R.id.editText_login_PW);

        if(login_ID.getText().toString().equals("") || login_PW.getText().toString().equals("")) {
            //비어있으면 체크 메세지 ㅡㄷ게 한당

            showMessage("submitbutton");
        }else{
            login_info.setId(login_ID.getText().toString());
            login_info.setPw(login_PW.getText().toString());


            Log.d(TAG,login_info.toString());

            Gson gson = new Gson();
            String login_info_json = gson.toJson(login_info);
            Log.d(TAG,login_info_json);

            try{

                serverConnection = new ServerConnection(login_info_json,url,type,threadHandler);
                serverConnection.start();
//                thread = new ConnectionThread(login_info_json, url);
//                thread.start();;
            }catch (Exception e){
                e.printStackTrace();
            }

        }



    }

    void doLogin(String userID, String userPW){


        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

        String type = "login_info";


        login_info.setId(userID);
        login_info.setPw(userPW);


        Log.d(TAG,login_info.toString());

        Gson gson = new Gson();
        String login_info_json = gson.toJson(login_info);
        Log.d(TAG,login_info_json);

        try{

            serverConnection = new ServerConnection(login_info_json,url,type,threadHandler);
            serverConnection.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////ThreadHandler()

    class ThreadHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg != null){

                Bundle b = msg.getData();
                String result = b.getString("data");
                String status ="";
                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(), "서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{
                    //Gson으로 문자열 객체로 전달하기
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Login gsonResponse;

                    gsonResponse = gsonresult.getStatus_login(result);

                    UserInfoVO vo = gsonResponse.getVo();

                    if( vo != null) {
                        //인텐트 객체로 보낸당 일단 성공!
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("UserInfoVO", vo);
                        //근데 USERINFOVO를 SErializable해서 그냥 넣어도 에러는 안뜨는거 같다 ㅎ


                        Log.d(TAG, "vo 객체 저장" + vo.toString());


                        if (gsonResponse == null) {
                            Log.d(TAG, "gsonResponse null");
                        } else {

                            String getEvent = gsonResponse.getEvent();
                            String getStatus = gsonResponse.getStatus();
//                        List<User_Info> getVo = gsonResponse.getVo();

                            if (getEvent.equals("Log In")) {

                                if (getStatus.equals("ok")) {
                                    //메인 화면으로 넘어가기
                                    Intent intent = new Intent(getApplicationContext(), Project_Main.class);
                                    intent.putExtra("User_Info", bundle);
                                    //이거 받을때 쓰는 코드

                                    //Bundle bundle = getIntent().getExtras();
//                                bundle.getSerializable("UserInfoVO");

                                    startActivity(intent);

                                    Log.d(TAG, "메인으로 넘어감");
                                    //로그인 정보도 넘겨주기
                                    //여기에다가 넘겨준다

                                    finish();


                                } else {
                                    showMessage("Login_false");
                                }
                            }

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다. 잠시후에 시도해 주십시오.", Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////showMessage()

    private void showMessage(String type){
        Log.d(TAG,"showMessage 들어옴");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");

        switch (type){

            case "submitbutton":
                builder.setMessage("모든 항목을 작성해 주십시오");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Log.d(TAG, "확인버튼 누름");
                    }
                });
                break;

        }

        AlertDialog dialog = builder.create();
        dialog.show();



    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String userID = pref.getString("userID","NO");
        String userPW = pref.getString("userPW","NO");

        if(userID.equals("NO") || userPW.equals("NO")){

            setContentView(R.layout.myhome_login);

            findViewById(R.id.button_login_to_join).setOnClickListener(handler);
            findViewById(R.id.button_id_config).setOnClickListener(handler);
            findViewById(R.id.button_pw_config).setOnClickListener(handler);

            findViewById(R.id.button_find_ID).setOnClickListener(handler);
            Log.d(TAG, "id : " + FirebaseInstanceId.getInstance().getToken());
        }else{

            doLogin(userID,userPW);
            //바로 로그인 한당
        }










//        findViewById(R.id.button_login_to_join).setOnClickListener(handler);
//        findViewById(R.id.button_id_config).setOnClickListener(handler);
//        findViewById(R.id.button_pw_config).setOnClickListener(handler);
//
//        findViewById(R.id.button_login).setOnClickListener(handler);
//        Log.d(TAG, "id : " + FirebaseInstanceId.getInstance().getToken());

    }
}
