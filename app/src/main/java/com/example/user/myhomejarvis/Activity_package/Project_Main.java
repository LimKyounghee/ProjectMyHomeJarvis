package com.example.user.myhomejarvis.Activity_package;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.pm.ActivityInfo;
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
import com.example.user.myhomejarvis.ListView_Util.Single_Grid_item_VO;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.RequestCode;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by user on 2018-03-21.
 */

public class Project_Main extends AppCompatActivity {

    private final static String TAG = "Project_Main";
    Bundle bundle;
    UserInfoVO vo;

    int userID;//이름은 유저아이딩 인데 패밀리 아이디 저장함... ㅐ밀리 아이디 써야 할지도 몰라서 ㅠ

    double myLocation_longitude;
    double myLocation_Latitude;

// 위치정보 받기 시작?//////////////////////////////////

   private void startLocationService(){

       LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

       GPSListener gpsListener = new GPSListener();
       long minTime = 10000;
       float minDistance = 0;

       try {

           manager.requestLocationUpdates(
                   LocationManager.GPS_PROVIDER,
                   minTime,
                   minDistance,
                   gpsListener);


           manager.requestLocationUpdates(
                   LocationManager.NETWORK_PROVIDER,
                   minTime,
                   minDistance,
                   gpsListener);


           Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
           if (lastLocation != null) {
               myLocation_Latitude = lastLocation.getLatitude();
               myLocation_longitude = lastLocation.getLongitude();

              Log.d(TAG, "Last Known Location : " + "Latitude : " + myLocation_Latitude + "\nLongitude:" + myLocation_longitude);
           }
       } catch(SecurityException ex) {
           ex.printStackTrace();
       }

       Log.d(TAG,"위치저보 완ㄹ");

   }

   private class GPSListener implements LocationListener{

       public void onLocationChanged(Location location) {
           myLocation_Latitude = location.getLatitude();
           myLocation_longitude = location.getLongitude();

           String msg = "Latitude : "+ myLocation_Latitude + "\nLongitude:"+ myLocation_longitude;
           Log.d(TAG, msg);

       }

       public void onProviderDisabled(String provider) {
       }

       public void onProviderEnabled(String provider) {
       }

       public void onStatusChanged(String provider, int status, Bundle extras) {
       }


   }

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String url;

            switch (v.getId()){



                case R.id.button_config_Myhome:
                    //여기 누르면 우리집 확인하기 페이지로 넘어감
                    Config_MyHome config_myHome = new Config_MyHome();
                    doChangePage(config_myHome, RequestCode.CONFIG_MYHOME);
                    break;

                case R.id.button_register_Home:
                    //여기누르면 집 등록하기 페이지로 넘어감
                    Add_Home_Activity add_home_activity = new Add_Home_Activity();

                    doChangePage(add_home_activity, RequestCode.ADD_MYHOME);
                    break;

                case R.id.add_device:
                    if(vo.getFamilyID() != 0) {    //가족 ID 를 등록해야 장비 등록가능 하도록
                        Add_device_Activity add_device_activity = new Add_device_Activity();
                        //                    String url = null;//url추가하자
                        url = Server_URL.getCategory();//여기도 바꾸장
                        doServerConnect(url, "request", "category");
                        //                    doChangePage(add_device_activity);
                        //여기 누르면 기기등록 페이지로 넘어감
                        //누를때 서버에게 카테고리 정보 받아서 그리드에 넣을수 있게 해야함
                    } else {
                        Toast.makeText(getApplicationContext(), "가족을 등록해야 장비를 등록할 수 있습니다. \n가족등록을 먼저 하십시오.", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.button_find_familyList:
                    Find_Family_List find_family_list = new Find_Family_List();
                    doChangePage(find_family_list,RequestCode.FAMILY_LIST);

                    break;
            }
        }
    };

    void doChangePage(Object page_Name, int requestCode){

        Log.d(TAG,page_Name.getClass().getName());

//        bundle.putSerializable("UserInfoVO",vo);

        Intent intent = new Intent(getApplicationContext(),page_Name.getClass());
        Log.d(TAG,"인텐트로 넘어갈려함");

        intent.putExtra("User_Info",bundle);


        // 사용자 정보 전달한다.

        if (requestCode == RequestCode.ADD_MYHOME) {
            startActivity(intent);
            finish();
        } else {
            startActivityForResult(intent, requestCode);
            Log.d(TAG, "전구 페이지로 넘어가냐?");
        }

    }

    //페이지 넘어갈때 서버와 연동해야 할 것들은 여기를 거친다.
    //기기추가와 현재상태페이지 넘어갈때 해야함

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case RequestCode.CONFIG_MYHOME:                       // config_home에서 돌아왔을때
                break;
            case RequestCode.ADD_DEVICE:                       // add_device에서 돌아왔을때
                break;
        }

    }

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

                    bundle.putSerializable("Grid_items",gsonResponse_add_device);
                    ArrayList<Single_Grid_item_VO> single_grid_item_vos = new ArrayList<Single_Grid_item_VO>();
                    for(String s : gsonResponse_add_device.getItems()){

                        single_grid_item_vos.add(new Single_Grid_item_VO(s));

                    }
                    bundle.putSerializable("Grid_items",single_grid_item_vos);
//                    bundle.putSerializable("Grid_items",gsonResponse_add_device.getItems());
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
                                intent.putExtra("userID",userID);

                                startActivityForResult(intent,RequestCode.ADD_DEVICE);

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);

        startLocationService();

        LinearLayout linearLayout = findViewById(R.id.main_button_LinearLayout);
        findViewById(R.id.button_config_Myhome).setOnClickListener(handler);
        findViewById(R.id.add_device).setOnClickListener(handler);
        Button register_home = findViewById(R.id.button_register_Home);
        register_home.setOnClickListener(handler);

        //이거는 바꿔야 한당 가족 리스트 보는 ㅂ튼!@
        findViewById(R.id.button_find_familyList).setOnClickListener(handler);

        bundle = getIntent().getBundleExtra("User_Info");
        if (bundle != null) {
            vo = (UserInfoVO) bundle.getSerializable("UserInfoVO");

            //SaredPreference에  값 저장하기
            SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("userID", vo.getUserID());
            editor.commit();

            userID = vo.getFamilyID();

            Log.d(TAG, vo.toString());

            //FamilyID확인하고 값이 0000이 아니면 집 추가 버튼 보이지 않게 하고  0000이면 집 추가 버튼 보이게 한다.


            if (vo.getFamilyID() != 0) {

                register_home.setVisibility(View.INVISIBLE);
                linearLayout.removeView(register_home);
            } else {
//            findViewById(R.id.button_register_Home).setOnClickListener(handler);
            }
        }else {
            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);
            finish();
        }
    }

    void setUI(){

    }
}
