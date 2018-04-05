package com.example.user.myhomejarvis.Activity_package;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.Join_Info;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

/**
 * Created by user on 2018-03-20.
 */

public class Join_Activity extends AppCompatActivity {

    public static final int REQUEST_CODE_MENUE = 101;// 인텐트로 보낼때 어떻게 할것인가...

    String url = Server_URL.getJoin_URL();

    Join_Info join_info = new Join_Info();//조인 클래스 불러오기
//    Gson gson = new GsonBuilder().create();

    //쓰레드 핸드러
    ThreadHandler threadHandler = new ThreadHandler();

    private static final String TAG = "Join_Activity";
    EditText join_id;
    EditText join_pw;
    EditText join_repw;
    EditText join_name;
    EditText join_birth;
    EditText join_phone;
    EditText join_email;
    EditText join_address;
    RadioGroup radioGroup;
    String fcm_Id;


///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////View.OnTouchListener touch_handler
    View.OnTouchListener touch_handler = new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    doConfigPW();
                    break;
            }
            return false;
        }
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////View.OnClickListener handler
    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.join_btn:
                    Log.d(TAG,"회원가입 버튼 누름");
                    doJoin();
                    break;

                case R.id.button_config_id:
                    Log.d(TAG,"아이디 중복 확인 누름");
                    doConfig_id();
                    break;
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////doConfigPW()
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void doConfigPW(){

        join_pw = findViewById(R.id.join_pw);
        join_repw = findViewById(R.id.join_repw);

        if(join_pw.getText().toString().equals("")){
            //비밀번호가 널값이면 비번 먼저 설정하라고 메세지 박스 뜬당
            showMessage("rePW");

        }else{

            if(join_pw.getText().toString().equals(join_repw.getText().toString())){

                //여기서는 그대로
                Resources resources = getResources();
                Drawable drawable = resources.getDrawable(R.drawable.editbackimg);
                join_repw.setBackground(drawable);
            }else{
                //여기서는 색변경
                Resources resources = getResources();
                Drawable drawable = resources.getDrawable(R.drawable.editbackimg_repw);
                join_repw.setBackground(drawable);


                
            }
        }


    }


    void doConfig_id(){

        ServerConnection serverConnection;
        String type = "userID";
        String config_ID;

        join_id = findViewById(R.id.join_id);
        String url11 = Server_URL.getIdConfig_URL();

       if(join_id.getText().toString() != null){

           config_ID = join_id.getText().toString();

           Gson gson = new Gson();
           String config_id_json = gson.toJson(config_ID);
           Log.d(TAG,config_id_json);

           try {

               serverConnection = new ServerConnection(config_id_json,url11,type,threadHandler);
               serverConnection.start();
               Log.d(TAG, "서버랑 연결");
//
//                thread = new ConnctionThread(join_info_json,url);
//                thread.start();
           }catch (Exception e){
               e.printStackTrace();
           }


       }


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////doJoin()
    void doJoin(){


        ServerConnection serverConnection;
        String type = "join_info";


//        String url = "http://192.168.0.8:8080/JavisProject/signin.do";
//        String url = "http://192.168.0.6:8088/IoTSample/monitortest.do";
        join_id = findViewById(R.id.join_id);
        join_pw = findViewById(R.id.join_pw);
        join_name = findViewById(R.id.join_name);
        join_birth = findViewById(R.id.join_birth);
        join_phone = findViewById(R.id.join_phone);
        join_email = findViewById(R.id.join_email);
        join_address = findViewById(R.id.join_address);
        radioGroup = findViewById(R.id.radioGroup);
        fcm_Id=FirebaseInstanceId.getInstance().getToken();

        if(join_id.getText().toString().equals("")  || join_pw.getText().toString().equals("")
                || join_name.getText().toString().equals("") || join_birth.getText().toString().equals("")
                || join_phone.getText().toString().equals("") || join_email.getText().toString().equals("")
                || join_address.getText().toString().equals("")  || radioGroup.getCheckedRadioButtonId() == 0) {
            //한개라도 비어있으면 체크라하는 메세지 박스 뜨게 한당

            showMessage("submitbutton");

        }else{

            join_info.setId(join_id.getText().toString());
            join_info.setPw(join_pw.getText().toString());
            join_info.setName(join_name.getText().toString());
            join_info.setBirth(join_birth.getText().toString());
            join_info.setPhone(join_phone.getText().toString());
            join_info.setEmail(join_email.getText().toString());
            join_info.setAddress(join_address.getText().toString());
            join_info.setFcmid(fcm_Id);//fcm_id추가!!!!!!!!!!!!!
            Log.d(TAG,"FCM ID"+fcm_Id);




            //성별 구하기
            int gender = radioGroup.getCheckedRadioButtonId();//숫자로 받는다...
            Log.d(TAG,"성별"+gender);
            if(gender == 0x7f07006b) {

                join_info.setGender(0);//남자
            }else{
                join_info.setGender(1);//여자
            }

            Log.d(TAG,join_info.toString());


            Gson gson = new Gson();
            String join_info_json = gson.toJson(join_info);
            Log.d(TAG,join_info_json);

            try {

                serverConnection = new ServerConnection(join_info_json,url,type,threadHandler);
                serverConnection.start();
                Log.d(TAG, "서버랑 연결");
//
//                thread = new ConnctionThread(join_info_json,url);
//                thread.start();
            }catch (Exception e){
                e.printStackTrace();
            }


        }



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
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{
                    //Gson으로 문자열 객체로 전달하기
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse;

                    gsonResponse = gsonresult.getStatus_join(result);

                    if(gsonResponse == null) {
                        Log.d(TAG,"gsonResponse null 값나옴");
                    }else{
                        //그래서 결과를 바탕으로 메세지 박스를 만든다.
                        String getEvent = gsonResponse.getEvent();
                        String getStatus = gsonResponse.getStatus();

                        switch (getEvent){

                            case "Sign In":

                                switch (getStatus){
                                    case "ok":
                                        showMessage("Join_In_Ok");
                                        break;

                                    case "false":
                                        showMessage("Join_In_false");
                                        break;
                                }
                                break;


                            case "idChecker":

                                switch (getStatus){

                                    case "ok":
                                        showMessage("id_ok");
                                        break;

                                    case "fail" :
                                        showMessage("id_false");
                                        join_id.getText().clear();
                                        break;

                                }

                                break;

                        }

//                        if(getEvent.equals("Sign In")){
//
//                            if(getStatus.equals("ok")){
//
//                                showMessage("Join_In_Ok");
//                            }else{
//                                showMessage("Join_In_false");
//                            }
//
//                        }
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
//        String content="";

        switch (type){

            case "submitbutton":

//                content = "회원가입을 하시려면 모든 항목을 작성해 주십시오";

                builder.setMessage("회원가입을 하시려면 모든 항목을 작성해 주십시오");
//        builder.setIcon(); 아이콘은 좀 나중에 해보쟞
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Log.d(TAG, "확인버튼 누름");
                    }
                });

                break;

            case "rePW":
//                content = "비밀번호 먼저 설정 하십시오";
                builder.setMessage("비밀번호 먼저 설정 하십시오");
//        builder.setIcon(); 아이콘은 좀 나중에 해보쟞
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Log.d(TAG, "확인버튼 누름");
                    }
                });

                break;

            case "Join_In_Ok":
//                content = "회원가입에 성공했습니다.";
                builder.setMessage("회원가입에 성공했습니다.");
                //로그인 페이지로 이동
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
//                        startActivityForResult(intent,REQUEST_CODE_MENUE);
                        startActivity(intent);
                        finish();

                        Log.d(TAG, "확인버튼 누름");
                    }
                });

                break;

            case "Join_In_false" :
//                content = "회원가입에 실패했습니다. 다시 작성해주세요";
                builder.setMessage("회원가입에 실패했습니다. 다시 작성해주세요");
                //회원가입 페이지 다시 열기
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Intent intent = new Intent(getApplicationContext(),Join_Activity.class);
//                        startActivityForResult(intent,REQUEST_CODE_MENUE);
                        startActivity(intent);
                        finish();

                        Log.d(TAG, "확인버튼 누름");
                    }
                });
                break;

            case "id_ok":
//                content = "비밀번호 먼저 설정 하십시오";
                builder.setMessage("사용할 수 있는 아이디 입니다.");
//        builder.setIcon(); 아이콘은 좀 나중에 해보쟞
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Log.d(TAG, "확인버튼 누름");
                    }
                });

                break;

            case "id_false":
//                content = "비밀번호 먼저 설정 하십시오";
                builder.setMessage("사용할 수 없는 아이디 입니다. 다시 설정해 주십시오");
//        builder.setIcon(); 아이콘은 좀 나중에 해보쟞
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////onCreate()


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.myhome_join);

        //비밀번호 확인 클릭시 테두리 색 비밀번호에 맞춰서 알려주기
        findViewById(R.id.join_repw).setOnTouchListener(touch_handler);
//        findViewById(R.id.join_repw).setOnKeyListener(keyListener);
        findViewById(R.id.button_config_id).setOnClickListener(handler);


        findViewById(R.id.join_btn).setOnClickListener(handler);


    }
}


