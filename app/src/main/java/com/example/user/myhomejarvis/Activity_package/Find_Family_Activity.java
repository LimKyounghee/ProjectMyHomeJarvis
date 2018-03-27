package com.example.user.myhomejarvis.Activity_package;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.R;

/**
 * Created by Kyun on 2018-03-27.
 */

public class Find_Family_Activity extends AppCompatActivity {
    Bundle bundle;
    UserInfoVO vo;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_btn:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.find_family);

        bundle = getIntent().getBundleExtra("User_Info");
        vo =(UserInfoVO) bundle.getSerializable("UserInfoVO");

        findViewById(R.id.search_btn).setOnClickListener(handler);
    }
}

