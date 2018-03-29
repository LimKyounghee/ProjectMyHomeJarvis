package com.example.user.myhomejarvis.Activity_package;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.Config_device;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Config_device;
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
    String url;

    ArrayList<Config_device> config_devices = new ArrayList<Config_device>();
    ListView listView_Device;


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

        listView = findViewById(R.id.listView_config_home);
//
        adaper = new SingerAdaper();
        adaper.setItemms(config_devices);

        listView.setAdapter(adaper);
        adaper.notifyDataSetChanged();


        doShow_config_page();




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

            Log.d(TAG,"==========="+item.getDeviceID());
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
