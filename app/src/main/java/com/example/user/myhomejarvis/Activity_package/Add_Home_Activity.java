package com.example.user.myhomejarvis.Activity_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.user.myhomejarvis.Page_String;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.RequestCode;

/**
 * Created by user on 2018-03-26.
 */

public class Add_Home_Activity extends AppCompatActivity {
    Bundle bundle;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.find_family_btn :
                    intent = new Intent(getApplicationContext(), Find_Family_Activity.class);
                    intent.putExtra("User_Info", bundle);

                    startActivity(intent);
                    break;
                case R.id.create_family_btn:
                    intent = new Intent(getApplicationContext(), Create_Family_Activity.class);
                    intent.putExtra("User_Info", bundle);

                    startActivityForResult(intent, RequestCode.CREATE_FAMILY);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = null;
        switch (requestCode) {
//            case RequestCode.FIND_FAMILY :
//                bundle = data.getBundleExtra("User_Info");
//                intent = new Intent();
//                intent.putExtra("User_Info", bundle);
//                setResult(RESULT_OK, intent);
//                finish();
//                break;
            case RequestCode.CREATE_FAMILY :
                if (data != null) {
                    bundle = data.getBundleExtra("User_Info");
                    intent = new Intent(getApplicationContext(), Project_Main.class);
                    intent.putExtra("User_Info", bundle);
                    startActivity(intent);
                    finish();
                } else {
                    // 강제로 뒤로가기 버튼 눌렀을때
                }
                break;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.add_family);

        bundle = getIntent().getBundleExtra("User_Info");

        findViewById(R.id.find_family_btn).setOnClickListener(handler);
        findViewById(R.id.create_family_btn).setOnClickListener(handler);
    }
}
