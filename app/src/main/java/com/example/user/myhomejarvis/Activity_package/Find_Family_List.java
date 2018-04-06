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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.UserInfoListData;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.ListView_Util.Family_List_LinearLayout;
import com.example.user.myhomejarvis.R;

import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018-04-01.
 */

public class Find_Family_List extends AppCompatActivity {

    private static final String TAG = "Find_Family_List";
    Bundle bundle;
    UserInfoVO vo;

    String type;
    Integer familyID;
    String userID;
    String url;
    ListView listView;

    SingerAdapter adapter;

    ArrayList<UserInfoVO> userInfoList = new ArrayList<UserInfoVO>();




    void doShow_familyList_page(){

        type = "family_id";

        Log.d(TAG,"패밀리 아이디" +familyID);

        url = Server_URL.getFamily_list();

        String familyID_toString = familyID.toString();

        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

        try{

            serverConnection = new ServerConnection(familyID_toString,url,type,threadHandler);
            serverConnection.start();
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

                    Gsonresult gsonresult = new Gsonresult();
                    Log.d(TAG,"gsonresult 연결");

                    UserInfoListData userInfoListData= gsonresult.getResponse_family_list(result);
                    Log.d(TAG,"userInfoListData 연결");

                    userInfoList = userInfoListData.getUserInfoList();
                    listView = findViewById(R.id.listView_family);

                    adapter = new SingerAdapter();
                    adapter.setItems(userInfoList);

                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }
            }
        }
    }

    class SingerAdapter extends BaseAdapter{

        ArrayList<UserInfoVO> items = new ArrayList<UserInfoVO>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void additem(UserInfoVO item_vo){
            items.add(item_vo);
        }

        public void setItems(ArrayList<UserInfoVO> item_vos){

            this.items = item_vos;

        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Family_List_LinearLayout view = new Family_List_LinearLayout(getApplicationContext());

            UserInfoVO item = items.get(position);
            view.setUserName(item.getName());
            view.setUserID(item.getUserID());

            //이미지 추가 할 수 있으면 해주기

            return view;
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
        setContentView(R.layout.family_list);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("currentPage","Find_Family_List");
        editor.commit();

        bundle = getIntent().getBundleExtra("User_Info");
        vo =(UserInfoVO) bundle.getSerializable("UserInfoVO");

        familyID = vo.getFamilyID();
        userID = vo.getUserID();
        Log.d(TAG,"패미리 아이디" + familyID +" 유저아이디" + userID);


        listView = findViewById(R.id.listView_family);

        adapter = new SingerAdapter();
        adapter.setItems(userInfoList);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        doShow_familyList_page();

    }
}
