package com.example.user.myhomejarvis.Activity_package;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.Request_Info;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_add_device;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.gson.Gson;

/**
 * Created by user on 2018-03-21.
 */

public class Project_Main extends AppCompatActivity {

    private final static int REQUEST_CODE_MENU = 103;
    private final static String TAG = "Project_Main";

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String url;

            switch (v.getId()){



                case R.id.button_config_Myhome:
                    //여기 누르면 우리집 확인하기 페이지로 넘어감
                    Config_MyHome config_myHome = new Config_MyHome();
//                    String url = null;//url추가하자
                    url = Server_URL.getJoin_URL();///이거 바꾸자
                    doServerConnect(url,"request","config_Home");
                    doChangePage(config_myHome);
                    break;

                case R.id.button_register_Home:
                    //여기누르면 집 등록하기 페이지로 넘어감
                    Add_Home_Activity add_home_activity = new Add_Home_Activity();
                    doChangePage(add_home_activity);
                    break;

                case R.id.add_device:
                    Add_device_Activity add_device_activity = new Add_device_Activity();
//                    String url = null;//url추가하자
                    url = Server_URL.getCategory();//여기도 바꾸장
                    doServerConnect(url,"request","category");
//                    doChangePage(add_device_activity);
                    //여기 누르면 기기등록 페이지로 넘어감
                    //누를때 서버에게 카테고리 정보 받아서 그리드에 넣을수 있게 해야함


                    break;
            }
        }
    };

    void doChangePage(Object page_Name){

        Log.d(TAG,page_Name.getClass().getName());

        Bundle bundle = getIntent().getBundleExtra("User_Info");
        Log.d(TAG,"번들로 인텐트 받음");
       // bundle.getSerializable("UserInfoVO");
        Log.d(TAG,"번들로  정보 저장");

        UserInfoVO vo =(UserInfoVO) bundle.getSerializable("UserInfoVO");
        Log.d(TAG,"VO객체 저장 됐냐?" +vo.toString() );

        bundle.putSerializable("UserInfoVO",vo);

        Intent intent = new Intent(getApplicationContext(),page_Name.getClass());
        Log.d(TAG,"인텐트로 넘어갈려함");

        intent.putExtra("User_Info",bundle);
        // 사용자 정보 전달한다.
        startActivityForResult(intent,REQUEST_CODE_MENU);
        Log.d(TAG,"전구 페이지로 넘어가냐?");

    }

    //페이지 넘어갈때 서버와 연동해야 할 것들은 여기를 거친다.
    //기기추가와 현재상태페이지 넘어갈때 해야함

    void doServerConnect(String url,String jsoinType, String requestInfo ){

        Log.d(TAG,"서버 커넥션 메소드에 들어옴");
        ServerConnection serverConnection;
        String type = jsoinType;//보낼때 타입은 그냥 지정해준다.
        ThreadHandler threadHandler = new ThreadHandler();

        Request_Info request_info = new Request_Info();
        request_info.setRequest_Info(requestInfo);

        Gson gson = new Gson();
        String request_info_json = gson.toJson(request_info);
        Log.d(TAG,request_info_json);

        try{

            serverConnection = new ServerConnection(request_info_json,url,type, new ThreadHandler());
            serverConnection.start();
            Log.d(TAG,"서버와 연 결");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class ThreadHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG,"핸들러 연결");
            super.handleMessage(msg);

            if(msg != null){

                Bundle b = msg.getData();
                String result = b.getString("data");
                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{

                    Gsonresult gsonresult = new Gsonresult();
                    Log.d(TAG,"Gsonresult 연결");

                    GsonResponse_add_device gsonResponse_add_device = gsonresult.getResponse_add_device(result);
                    Log.d(TAG,"GsonResponse_add_device 연결");

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Grid_items",gsonResponse_add_device.getItems());
                    bundle.putSerializable("Grid_event",gsonResponse_add_device.getEvent());

                    Log.d(TAG,gsonResponse_add_device.toString());


                    if(gsonResponse_add_device == null){
                        Log.d(TAG,"gsonResponse_add_device 널값이다");
                    }else{

                        String getEvent = gsonResponse_add_device.getEvent();

                        Log.d(TAG,"이벤트 값  1111111"+getEvent );

                        switch (getEvent){

                            case "category":
                                //여기서 기기 변경 ㅘ면으로 넘어가기
                                Log.d(TAG,"스위치에 들어옴 널값이다");

                                Intent intent = new Intent(getApplicationContext(),Add_device_Activity.class);
                                intent.putExtra("Grid_info",bundle);
                                startActivityForResult(intent,REQUEST_CODE_MENU);

                                Log.d(TAG,"기기변경 페이지러 너민당");
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
        setContentView(R.layout.main);

        findViewById(R.id.button_config_Myhome).setOnClickListener(handler);
        findViewById(R.id.add_device).setOnClickListener(handler);

        Bundle bundle = getIntent().getBundleExtra("User_Info");
        UserInfoVO vo =(UserInfoVO) bundle.getSerializable("UserInfoVO");

        Log.d(TAG,vo.toString());

        //FamilyID확인하고 값이 0000이 아니면 집 추가 버튼 보이지 않게 하고  0000이면 집 추가 버튼 보이게 한다.

        if(!vo.getFamilyID().equals("0000")){

            Button register_home = findViewById(R.id.button_register_Home);
            register_home.setVisibility(View.INVISIBLE);
            LinearLayout linearLayout = findViewById(R.id.main_button_LinearLayout);
            linearLayout.removeView(register_home);

        }else{
            findViewById(R.id.button_register_Home).setOnClickListener(handler);
        }
    }
}
