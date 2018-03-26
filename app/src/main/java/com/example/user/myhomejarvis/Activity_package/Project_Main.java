package com.example.user.myhomejarvis.Activity_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.R;

/**
 * Created by user on 2018-03-21.
 */

public class Project_Main extends AppCompatActivity {

    private final static int REQUEST_CODE_MENU = 103;
    private final static String TAG = "Project_Main";

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.button_config_Myhome:
                    //여기 누르면 우리집 확인하기 페이지로 넘어감
                    Config_MyHome config_myHome = new Config_MyHome();
                    doChangePage(config_myHome);
                    break;

                case R.id.button_register_Home:
                    //여기누르면 집 등록하기 페이지로 넘어감
                    Add_Home_Activity add_home_activity = new Add_Home_Activity();
                    doChangePage(add_home_activity);
                    break;

                case R.id.add_device:
                    Add_device_Activity add_device_activity = new Add_device_Activity();
                    doChangePage(add_device_activity);
                    //여기 누르면 기기등록 페이지로 넘어감

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.button_config_Myhome).setOnClickListener(handler);
        findViewById(R.id.button_register_Home).setOnClickListener(handler);
    }
}
