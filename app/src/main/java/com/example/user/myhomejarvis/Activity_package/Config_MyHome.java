package com.example.user.myhomejarvis.Activity_package;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.SharedPreferences;

import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.Config_device;
import com.example.user.myhomejarvis.Data_Info_package.On_Off_Control_Data;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Fragment_package.Config_myhome_fragmenr_fan_button;
import com.example.user.myhomejarvis.Fragment_package.Config_myhome_fragmenr_home_picure;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Config_device;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.ListView_Util.Config_myhome_LinearLayout;
import com.example.user.myhomejarvis.ListView_Util.Single_Grid_item_VO;

import com.example.user.myhomejarvis.Page_String;

import com.example.user.myhomejarvis.LogManager;

import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.RequestCode;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.example.user.myhomejarvis.Server_Connection_package.Util;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2018-03-26.
 */

public class Config_MyHome extends AppCompatActivity {

    private static final String TAG = "Config_MyHome";

    Fragment fragment_picture = new Config_myhome_fragmenr_home_picure();
    Fragment fragment_fan_button = new Config_myhome_fragmenr_fan_button();
    String request_fragment;

    Bundle bundle;
    UserInfoVO vo;
    TextView contentText;
    TextView statusText;

    ListView listView;
    SingerAdaper adaper;

    String result;
    String type;
    Integer familyID;
    String userID;
    String url;

    String[] device_list = {"Fan","Cooler","Humi","Sound","Shock","Plug","Light","Cam","Bell","DoorLock"};

    ArrayList<Config_device> config_devices = new ArrayList<Config_device>();
//    ArrayList<String> user_devices_to_mic_page =  new ArrayList<String>();
    ListView listView_Device;
    int this_position;

    boolean isFan_fragment = false;
    boolean ispicture_fragment = false;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.button_mic_page:

                    MIC_page mic_page = new MIC_page();
                    Intent intent = new Intent(getApplicationContext(),mic_page.getClass());
//                    bundle.putParcelableArrayList("config_device",);
                    bundle.putSerializable("config_device",config_devices);
                    intent.putExtra("User_Info",bundle);
                    Log.d(TAG,"마이크 페이지로 ㄱ나당");
                    startActivityForResult(intent, RequestCode.MICPAGE);


            }
        }
    };

    void doFragmentPage(String deviceID){
        //프레그 먼트 페이지를 쓰도록한

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("deviceID",deviceID);
        bundle.putString("userID",userID);

        String fragmentTag = null;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        if(isFan_fragment == false && ispicture_fragment ==false){
            //둘다 false일때 누른 값으로 실행한다.
            if(("fan").equals(request_fragment)){

                fragment = fragment_fan_button;
                fragmentTag = "fan";
                Log.d(TAG,"fragmentTeag ===" +fragmentTag);
                isFan_fragment = true;
                ispicture_fragment = false;

            }else{
                fragment = fragment_picture;
                fragmentTag = "picture";
                Log.d(TAG,"fragmentTeag ===" +fragmentTag);
                isFan_fragment = false;
                ispicture_fragment = true;

            }

        }else if (isFan_fragment == true && ispicture_fragment ==false){
            //팬 페이지 실행중 팬이 눌리면 암것도 안함
            // 다른거 눌리면 페이지 바꾸게 한다.

            if(("fan").equals(request_fragment)){


                Log.d(TAG,"누름1111111111111111111122222222222222");
            }else{
                fragment = fragment_picture;
                fragmentTag = "picture";
                Log.d(TAG,"fragmentTeag ===" +fragmentTag);
                isFan_fragment = false;
                ispicture_fragment = true;

            }
        }else if(isFan_fragment == false && ispicture_fragment ==true){

            //

            if(("fan").equals(request_fragment)){

                fragment = fragment_fan_button;
                fragmentTag = "fan";
                Log.d(TAG,"fragmentTeag ===" +fragmentTag);
                isFan_fragment = true;
                ispicture_fragment = false;
            }else{

                Log.d(TAG,"누름11111111111111111111");

            }
        }


        Log.d(TAG,"fragmentTag" +fragmentTag);

        if(fragment.isRemoving()){
            fragment.getArguments().putAll(bundle);
        }else{

            if(getSupportFragmentManager().findFragmentByTag(fragmentTag) != null) {
                fragmentManager.beginTransaction().remove(getFragmentManager().findFragmentByTag(fragmentTag)).commit();
            }
//            fragmentManager.beginTransaction().remove(fragment).commit();
            fragment.setArguments(bundle);
        }


        fragmentTransaction.replace(R.id.fragment_picture_or_button,fragment,fragmentTag).addToBackStack(null).commit();

    }



    void doShow_config_page(){

        //페이지를 시작하면 서버에서 정보를 받아 리스트 뷰를 만들게 한당

        type = "config_home";
//        familyID = getIntent().getIntExtra("userID",0);
        Log.d(TAG,"패밀리 아이디" +familyID);

        url = Server_URL.getConfig_Home();

        String familyID_toString = familyID.toString();
        //패밀리 아이디가 int형 이기 때문에 toString으로 바꿔준당

        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

        try {

            serverConnection = new ServerConnection(familyID_toString, url, type, threadHandler);
            serverConnection.start();
            Log.d(TAG,"서버와 연결");


        }catch (Exception e){

            e.printStackTrace();
        }



    }

    class ThreadHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg != null){

                Bundle b = msg.getData();
                String result = b.getString("data");

                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{
                    //gson값 풀어줘서 한당
                    Gsonresult gsonresult = new Gsonresult();
                    Log.d(TAG,"gsonresult 연결");

                    GsonResponse_Config_device gsonResponse_config_device = gsonresult.getResponse_config_home(result);
                    Log.d(TAG,"gsonResponse_config_device 연결");

                    config_devices=  gsonResponse_config_device.getData();
                    //여기서 VOS정의함

                    for(int i = config_devices.size()-1; i>=0; i--){

                        int idx = config_devices.get(i).getDeviceID().indexOf("Bell");
                        int idx2 = config_devices.get(i).getDeviceID().indexOf("DoorLock");

                        if(idx != -1 || idx2 != -1){
                            config_devices.remove(i);
                        }

                    }
                    Log.d(TAG,"디바이스 정보 " +config_devices.toString());

                    listView_Device = findViewById(R.id.listView_config_home);

                    adaper = new SingerAdaper();
                    adaper.setItemms(config_devices);

                    listView_Device.setAdapter(adaper);
                    adaper.notifyDataSetChanged();


                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Project_Main.class);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_myhome);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("currentPage", Page_String.CONFIG_MYHOME);
        editor.commit();

        bundle = getIntent().getBundleExtra("User_Info");
        vo =(UserInfoVO) bundle.getSerializable("UserInfoVO");

        familyID = vo.getFamilyID();
        userID = vo.getUserID();
        Log.d(TAG,"유저 ㅇ이디" + userID);

        request_fragment = "선풍기 아님";
        doFragmentPage("no");
        findViewById(R.id.button_mic_page).setOnClickListener(handler);


        listView = findViewById(R.id.listView_config_home);
//
        adaper = new SingerAdaper();
        adaper.setItemms(config_devices);

        listView.setAdapter(adaper);
        adaper.notifyDataSetChanged();
        doShow_config_page();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                this_position = position;
                Config_device item = (Config_device)adaper.getItem(position);

                int funtion_Num = item.getFunction();
                String status = item.getDeviceStatus();
                String device_Name = item.getDeviceID();
                Log.d(TAG,"디바이스 명 "+ device_Name);
                String[] device_list = {"Fan","Cooler","Humi","Sound","Shock","Plug","Light","Cam"};

                // 습도는 보여줄 필요가 있음. 쿨러는 켜저있는지 꺼져있는지. 하지만 컨ㅌ롤 안함.. 나중에 할수 있게함...
                //소리는 모니터링만..... 서버에서 수치를 나한테 보내준당

                //진동저건ㄴ,, 안할듯...
                //플러그는 온오프 기능
                //전구도 온오프
                //캠은???== 초인종 눌리면....아마 필ㅇ없음


                String device = null;

                String type = null;
                //타입은 스위치 케이스 문 안에서 해줌


                if(funtion_Num == 1) {
                    //여기서 온 오프 할 수 있게 하기
//
                    request_fragment = "선풍기 아님";

                    if(isFan_fragment == false && ispicture_fragment ==true){

                    }else{
                        doFragmentPage(device_Name);
                    }
//                    doFragmentPage(device);

                    int indx = -1;

                    for(int i = 0; i <device_list.length;i++){

                        indx = device_Name.indexOf(device_list[i]);
                        Log.d(TAG,"device 정보 포문 안에서 " + device);
                        if(indx != -1){
                            device = device_list[i];
                            Log.d(TAG,"device 정보 포문 안에서 " + device);
                            break;
                        }
                    }
                    Log.d(TAG,"device 정보  와일문 통과후" + device);


                    if(device != null) {
                        //디바이스 값이 널이 아닐때
                        Log.d(TAG,"device 정보 이프문 안에서 " + device);

                        switch (device) {

                            case "Light":
                                type = "led_onoff";
                                url = Server_URL.getLed_onoff_URL();

                                // 전구 온오프 기능 할 수 있게 서버에 전달

                                switch (status){

                                    case "켜짐":
                                        do_ON_OFF_Action(device_Name,"꺼짐",type);
                                        break;

                                    case "꺼짐":
                                        do_ON_OFF_Action(device_Name,"켜짐",type);
                                        break;
                                }


                                break;

                            case "Plug":

                                type = "plug_onoff";
                                url = Server_URL.getSmartplug_URL();

                                //플러그 온오프 기능 할 수 있게 서버에 전달

                                switch (status){

                                    case "켜짐":
                                        do_ON_OFF_Action(device_Name,"꺼짐",type);
                                        break;

                                    case "꺼짐":
                                        do_ON_OFF_Action(device_Name,"켜짐",type);
                                        break;
                                }
                                break;

                            case "Sound":

                                //소리는 클릭해서 보여줄 필요가 없음
                                break;

                            case "Humi":


                                // 습도도 클릭해서 보여줄 필요가 없음
                                break;

                            case "Cooler" :


                                //쿨러 역시.. 하지만 나중에 컨트롤링 할 수 있게 할 수도 있당

                                break;

                        }

                    }

                }else{
                    //1이 아니면 다른 페이지에서 그 기기를 볼 수 있게 하기

                    int indx = 0;

                    for(int i = 0; i <device_list.length;i++){

                        indx = device_Name.indexOf(device_list[i]);
                        Log.d(TAG,"device 정보 포문 안에서 " + device);
                        if(indx != -1){
                            device = device_list[i];
                            Log.d(TAG,"device 정보 포문 안에서 " + device);
                            break;
                        }
                    }

                    switch (device){

                        case "Fan" :

                            request_fragment = "fan";

                            if(isFan_fragment == true && ispicture_fragment ==false){

                            }else{
                                doFragmentPage(device_Name);
                            }
//                            doFragmentPage(device_Name);
                            //일단 다른페이지 로가서 할 것은 선풍기 밖에 엄ㅅㅂ음
                            break;
                    }


                }

            }
        });

    }

    void do_ON_OFF_Action(String deviceID, String request_Status, String type){

        ServerConnection serverConnection;
        ThreadHadler2 threadHadler2 = new ThreadHadler2();

        On_Off_Control_Data on_off_control_data = new On_Off_Control_Data();
        on_off_control_data.setUserid(userID);
        on_off_control_data.setDeviceId(deviceID);
        on_off_control_data.setEventInfo(request_Status);


        Log.d(TAG,"On_Off_Control_Data ????????" +on_off_control_data);

        Log.d(TAG," On_Off_Control_Data 에 저장" + on_off_control_data.toString());

        Gson gson = new Gson();
        String on_off_data_json = gson.toJson(on_off_control_data);

        try {
            serverConnection = new ServerConnection(on_off_data_json, url,type, threadHadler2);
            Log.d(TAG,"서버랑 연결");
            serverConnection.start();
        }catch(Exception e){
            e.printStackTrace();
        }




    }

    class ThreadHadler2 extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"핸들러 들어옴 연결");

            if(msg != null){

                Bundle b = msg.getData();
                String result = b.getString("data");

                if(("".equals(result))){

                    Toast.makeText(getApplicationContext(), "서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{

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

                        Config_device configDevice = config_devices.get(this_position);
                        Log.d(TAG,"컨트롤링 핸들러 누른후 내가 눌른 포지션이 어디일까? " + this_position);
                        String get_status;

                        switch (getEvent){

                            case "plugOnOff" :

                                //플러그 제어할때
                                switch (getStatus){

                                    case "켜짐":
                                        get_status="켜짐";
                                        configDevice.setDeviceStatus(get_status);
                                        adaper.notifyDataSetChanged();

                                        break;

                                    case "꺼짐":

                                        get_status="꺼짐";
                                        configDevice.setDeviceStatus(get_status);
                                        adaper.notifyDataSetChanged();
                                        break;
                                }

                                break;

                            case "lightOnOff":

                                //전구 제어 할때
                                switch (getStatus){

                                    case "켜짐":

                                        get_status="켜짐";
                                        configDevice.setDeviceStatus(get_status);
                                        adaper.notifyDataSetChanged();

                                        break;

                                    case "꺼짐":

                                        get_status="꺼짐";
                                        configDevice.setDeviceStatus(get_status);
                                        adaper.notifyDataSetChanged();

                                        break;
                                }
                                break;



                        }


                    }



                }

            }

        }
    }









    class SingerAdaper extends BaseAdapter {

        ArrayList<Config_device> itemms = new ArrayList<Config_device>();

        @Override
        public int getCount() {
            return itemms.size();
        }

        public void addItem(Config_device item_vo) {

            itemms.add(item_vo);
        }

        public void setItemms(ArrayList<Config_device> item_vos) {

            this.itemms = item_vos;
        }

        @Override
        public Object getItem(int position) {
            return itemms.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Config_myhome_LinearLayout view = new Config_myhome_LinearLayout(getApplicationContext());

            Config_device item = itemms.get(position);
            view.setContentView(item.getDeviceID());
            view.setStatusView(item.getDeviceStatus());
            view.setFunction_Num(item.getFunction());

            SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            if(item.getDeviceID().equals("LG Fan1")) {
                LogManager.print("선풍기 shared에 저장 완료");
                editor.putString("fan_device_id", item.getDeviceID());
                editor.putString("fan_status", item.getDeviceStatus());
                editor.commit();
            }
            if (item.getDeviceID().equals("SAMSUNG Light1")) {
                LogManager.print("전등1 shared에 저장 완료");
                editor.putString("led1_device_id", item.getDeviceID());
                editor.putString("led1_status", item.getDeviceStatus());
                editor.commit();
            }
            if (item.getDeviceID().equals("SAMSUNG Light2")) {
                LogManager.print("전등2 shared에 저장 완료");
                editor.putString("led2_device_id", item.getDeviceID());
                editor.putString("led2_status", item.getDeviceStatus());
                editor.commit();
            }
            if (item.getDeviceID().equals("LG Light1")) {
                LogManager.print("전등3 shared에 저장 완료");
                editor.putString("led3_device_id", item.getDeviceID());
                editor.putString("led3_status", item.getDeviceStatus());
                editor.commit();
            }
            if (item.getDeviceID().equals("GE Light1")) {
                LogManager.print("전등4 shared에 저장 완료");
                editor.putString("led4_device_id", item.getDeviceID());
                editor.putString("led4_status", item.getDeviceStatus());
                editor.commit();
            }
            if (item.getDeviceID().equals("SKT Plug1")) {
                LogManager.print("플러그 shared에 저장 완료");
                editor.putString("plug_device_id", item.getDeviceID());
                editor.putString("plug_status", item.getDeviceStatus());
                editor.commit();
            }

            int imageID =  R.drawable.mainlogo;
            int[] device_image_value = {

                    R.drawable.cooler,R.drawable.fan, R.drawable.raindrop,R.drawable.volumecontrol,
                    R.drawable.mainlogo,R.drawable.plug, R.drawable.lighton, R.drawable.webcam,
                    R.drawable.alarm,R.drawable.unlocked
            };

            String device = item.getDeviceID();

            int indx = -1;

            for(int i = 0; i <device_list.length;i++){

                indx = device.indexOf(device_list[i]);
                Log.d(TAG,"device 정보 포문 안에서 " + device);
                if(indx != -1){
                    device = device_list[i];
                    view.setList_item_imageview(device_image_value[i]);
                    Log.d(TAG,"device 정보 포문 안에서 " + device);
                    break;
                }
            }
            Log.d(TAG,"device 정보  이미지 통과후" + device);




            //이미지 추가 할 수 있으면 해주기

            return view;

        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }
}
