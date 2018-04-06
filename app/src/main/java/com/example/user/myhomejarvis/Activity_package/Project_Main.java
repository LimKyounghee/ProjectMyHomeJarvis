package com.example.user.myhomejarvis.Activity_package;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.myhomejarvis.Data_Info_package.Request_Info;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_add_device;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.ListView_Util.Single_Grid_item_VO;

import com.example.user.myhomejarvis.Page_String;

import com.example.user.myhomejarvis.LogManager;

import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.RequestCode;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.example.user.myhomejarvis.Server_Connection_package.Weather_API;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by user on 2018-03-21.
 */

public class Project_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private final static String TAG = "Project_Main";
    Bundle bundle;
    UserInfoVO vo;
    private AdView mAdView;

    int userID;//이름은 유저아이딩 인데 패밀리 아이디 저장함... ㅐ밀리 아이디 써야 할지도 몰라서 ㅠ

    double myLocation_longitude;
    double myLocation_Latitude;

    String sky;
    String temperature;
    String humidity;
    String station;

    TextView stationText;
    TextView tempText;
    TextView humiText;
    ImageView weatherImage;

    TextView drawer_userID;
    TextView drawer_userName;


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    String user_ID_to_Drawer;
    String user_Name_to_Drawer;









// 위치정보 받기 시작?//////////////////////////////////



    public class WeatherTask extends AsyncTask<Void,Void,String>{

        private double lat;
        private double lon;

        public WeatherTask(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            getWeather_Info_API(s);

        }

        @Override
        protected String doInBackground(Void... voids) {

            String result;
            Weather_API weather_api = new Weather_API();
            result=  weather_api.getWegher_API_Result(lat,lon);
            return result;
        }
    }


    void getWeather_Info_API(String weather_Result){

        //한번 찍고 파싱하자

        JsonParser parser = new JsonParser();

        JsonObject parseResult = parser.parse(weather_Result).getAsJsonObject();

        JsonObject weather = parseResult.get("weather").getAsJsonObject();

        JsonArray _minutely =  weather.get("minutely").getAsJsonArray();

        if(_minutely != null){

            for(int i = 0; i<_minutely.size(); i++){

                JsonObject a = _minutely.get(i).getAsJsonObject();

                JsonObject _sky = a.get("sky").getAsJsonObject();
                sky = _sky.get("name").getAsString();
                weatherImage = findViewById(R.id.image_sky);
                String[] sky_value ={"맑음","구름조금","구름많음","구름많고 비","구름많고 눈","구름많고 비 또는 눈","흐림",
                        "흐리고 비","흐리고 눈","흐리고 비 또는 눈","흐리고 낙뢰","뇌우/비","뇌우/눈","뇌우/비 또는 눈"};
                int[] sky_image_res ={
                        R.drawable.day_clear_01,R.drawable.day_cloudy_02,R.drawable.day_partly_cloud_03,R.drawable.day_rain_04,
                        R.drawable.day_snow_05,R.drawable.rain_or_snow,R.drawable.night_cloudy_08,R.drawable.night_rain_10,
                        R.drawable.night_snow_11,R.drawable.clody_rain_or_snow,R.drawable.night_thunder_12,R.drawable.thunder_rain,
                        R.drawable.thinder_snow,R.drawable.thinder_rain_snow};

                for(int j= 0; j <sky_value.length; j++){

                    if(sky_value[j].equals(sky)){
                        weatherImage.setImageResource(sky_image_res[j]);
                        break;
                    }

                }

                Log.d(TAG,"sky0000000000" + sky);

                JsonObject _temperature = a.get("temperature").getAsJsonObject();

                temperature = _temperature.get("tc").getAsString();
                tempText = findViewById(R.id.textView_temp);
                tempText.setText(temperature);

                Log.d(TAG,"temperature0000000000" + temperature);

                humidity =a.get("humidity").getAsString();

                humiText = findViewById(R.id.textView_humi);
                humiText.setText(humidity+"%");

                Log.d(TAG,"humidity0000000" + humidity);

                JsonObject _station = a.get("station").getAsJsonObject();
                station = _station.get("name").getAsString();

                stationText = findViewById(R.id.textView_Station);
                stationText.setText(station);

                Log.d(TAG,"station0000000" + station);

                Log.d(TAG,"몇번 돌았냐?" + i+ "번");

            }
        }


    }

   private void startLocationService(){

       LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

       GPSListener gpsListener = new GPSListener();
       long minTime = 1000000;
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

              Log.d(TAG, "00000000000000000000Last Known Location : " + "Latitude : " + myLocation_Latitude + "\nLongitude:" + myLocation_longitude);


           }
       } catch(SecurityException ex) {
           ex.printStackTrace();
       }

       Log.d(TAG,"위치저보 완ㄹ + 날씨 정보도 ㅇㄷ음");
   }

   private class GPSListener implements LocationListener{

       public void onLocationChanged(Location location) {
           myLocation_Latitude = location.getLatitude();
           myLocation_longitude = location.getLongitude();

           String msg = "Latitude : "+ myLocation_Latitude + "\nLongitude:"+ myLocation_longitude;
           Log.d(TAG, msg);

           WeatherTask weatherTask = new WeatherTask(myLocation_Latitude,myLocation_longitude);
           Log.d(TAG,"햐나>????");
           weatherTask.execute();
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
        setContentView(R.layout.project_main);

        startLocationService();

        //백스페이스 막기



        bundle = getIntent().getBundleExtra("User_Info");
        if (bundle != null) {
            vo = (UserInfoVO) bundle.getSerializable("UserInfoVO");
            Log.d(TAG, vo.toString());
            userID = vo.getFamilyID();
            user_ID_to_Drawer = vo.getUserID();
            user_Name_to_Drawer = vo.getName();

            //SaredPreference에  값 저장하기
            SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("userID", vo.getUserID());
            editor.putString("userPW",vo.getPw());
            editor.putInt("familyID",vo.getFamilyID());
            editor.putString("currentPage",Page_String.PROJECT_MIAIN);
            editor.commit();


            //메뉴 설정 해주기

            toolbar =findViewById(R.id.toolbar);
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.navigation_view);

            View headerView = navigationView.getHeaderView(0);
            drawer_userID = (TextView) headerView.findViewById(R.id.textView_drawer_userID);
            drawer_userName = headerView.findViewById(R.id.textView_drawer_userName);

            drawer_userID.setText(user_ID_to_Drawer);
            drawer_userName.setText(user_Name_to_Drawer);

            //툴바 생성 및 세팅
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            //액션 토글
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);


            LinearLayout linearLayout = findViewById(R.id.main_button_LinearLayout);
            findViewById(R.id.button_config_Myhome).setOnClickListener(handler);
            findViewById(R.id.add_device).setOnClickListener(handler);
            Button register_home = findViewById(R.id.button_register_Home);
            register_home.setOnClickListener(handler);

            //광고 배너
            MobileAds.initialize(this, "ca-app-pub-9604831383254278~9396568282");

            AdView adView = new AdView(this);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId("ca-app-pub-9604831383254278/8529853958");


            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);



            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    LogManager.print("onAdLoaded : 광고 배너 테스트1");
                }


                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    LogManager.print("onFailedToLoaded : 광고 배너 실패 테스트");
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    LogManager.print("onAdOpened : 광고 배너 Opened");
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                    LogManager.print("onAdLeftApplication : 광고 배너 테스트");
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                    LogManager.print("onAdClosed : 광고 배너 Closed");
                }
            });

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.navigation_item_family_list:

                //우리집 가족 리스트 보는 것으로 간단
                Find_Family_List find_family_list = new Find_Family_List();
                doChangePage(find_family_list,RequestCode.FAMILY_LIST);

                break;

            case R.id.navigation_item_chart:

                Webview_Activitiy webview_activitiy = new Webview_Activitiy();
                doChangePage(webview_activitiy,RequestCode.WEBVIEW);

                break;

            case R.id.navigation_item_support_center:
                Toast.makeText(getApplicationContext(), item.getTitle(),Toast.LENGTH_SHORT).show();
                Log.d(TAG,"누름3!");
                break;

            case R.id.navigation_item_logout:
                Login_Activity login_activity = new Login_Activity();
                //로그아웃 할때 쉐.프 데이터 없애준당
                SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("userID", null);
                editor.putString("userPW",null);
                editor.putInt("familyID",0);
                editor.putString("currentPage", Page_String.LOGIN_PAGE);
                editor.commit();
                doChangePage(login_activity,RequestCode.LOGINPAGE);
                break;

        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPause() {
        // This method should be called in the parent Activity's onPause() method.
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // This method should be called in the parent Activity's onResume() method.
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        // This method should be called in the parent Activity's onDestroy() method.
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
