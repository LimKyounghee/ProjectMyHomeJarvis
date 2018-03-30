package com.example.user.myhomejarvis.Activity_package;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.Config_device;
import com.example.user.myhomejarvis.Data_Info_package.On_Off_Control_Data;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Config_device;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.ListView_Util.Config_myhome_LinearLayout;
import com.example.user.myhomejarvis.ListView_Util.Single_Grid_item_VO;
import com.example.user.myhomejarvis.R;
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

    ArrayList<Config_device> config_devices = new ArrayList<Config_device>();
    ListView listView_Device;
    int this_position;


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_myhome);

        bundle = getIntent().getBundleExtra("User_Info");
        vo =(UserInfoVO) bundle.getSerializable("UserInfoVO");

        familyID = vo.getFamilyID();
        userID = vo.getUserID();

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

//                    while(indx == -1){
//
//                        int  i = 0;
//                        indx = device_Name.indexOf(device_list[i]);
//                        device = device_list[i];
//                        Log.d(TAG,"device 정보 와일문 안에서 " + device);
//                        i++;
//                    }
//                    Log.d(TAG,"device 정보  와일문 통과후" + device);

                    if(device != null) {
                        //디바이스 값이 널이 아닐때
                        Log.d(TAG,"device 정보 이프문 안에서 " + device);

                        switch (device) {

                            case "Light":
                                type = "led_onoff";
                                url = Server_URL.getLed_onoff_URL();

                                // 전구 온오프 기능 할 수 있게 서버에 전달

                                switch (status){

                                    case "ON":
                                        do_ON_OFF_Action(device_Name,status,type);
                                        break;

                                    case "OFF":
                                        do_ON_OFF_Action(device_Name,status,type);
                                        break;
                                }


                                break;

                            case "Plug":

                                type = "plug_onoff";
                                url = Server_URL.getSmartplug_URL();

                                //플러그 온오프 기능 할 수 있게 서버에 전달

                                switch (status){

                                    case "ON":
                                        do_ON_OFF_Action(device_Name,status,type);
                                        break;

                                    case "OFF":
                                        do_ON_OFF_Action(device_Name,status,type);
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

                    while(indx != -1){

                        int  i = 0;
                        indx = device_Name.indexOf(device_list[i]);
                        device = device_list[i];
                        Log.d(TAG,"device 정보 와일문 안에서 " + device);
                        i++;
                    }
                    Log.d(TAG,"device 정보  와일문 통과후" + device);

                    switch (device){

                        case "Fan" :

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
                                switch (getEvent){

                                    case "ON":
                                        get_status="ON";
                                        configDevice.setDeviceStatus(get_status);
                                        adaper.notifyDataSetChanged();

                                        break;

                                    case "OFF":

                                        get_status="OFF";
                                        configDevice.setDeviceStatus(get_status);
                                        adaper.notifyDataSetChanged();
                                        break;
                                }

                                break;

                            case "lightOnOff":

                                //전구 제어 할때
                                switch (getEvent){

                                    case "ON":

                                        get_status="ON";
                                        configDevice.setDeviceStatus(get_status);
                                        adaper.notifyDataSetChanged();

                                        break;

                                    case "OFF":

                                        get_status="OFF";
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

            return view;

        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }
}
