package com.example.user.myhomejarvis.Activity_package;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.user.myhomejarvis.R;

import com.example.user.myhomejarvis.LogManager;
import android.Manifest;

/**
 * Created by user on 2018-03-24.
 */

public class Admit_Activity extends AppCompatActivity{

    int[] grantResult;
    int permission_count = 0;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_admin:
                    permissionCheck();  // 위험 권한 부여 메소드 호출

                    // 권한을 모두 부여했는지 확인하는 소스
                    for(int i = 0 ; i < grantResult.length; i++) {
                        if (grantResult[i] == PackageManager.PERMISSION_GRANTED) {
                            permission_count ++;
                        }
                    }
                    LogManager.print(Integer.toString(permission_count));
                    // 모두 권한을 부여했으면 로그인 화면으로 넘어감
                    if (permission_count == grantResult.length) {
                        Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                        startActivity(intent);
                    }

                    break;
            }
        }
    };
    // 위험 권한 부여 요청하기 위한 메소드
    public void permissionCheck() {
        int location_fine_permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int location_coarse_permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int storage_read_permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int storage_write_permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (location_fine_permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1); //<---- 권한 부여 요청 대화상자 띄우기
            }
        }

        if (location_coarse_permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1); //<---- 권한 부여 요청 대화상자 띄우기
            }
        }

        if (storage_read_permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1); //<---- 권한 부여 요청 대화상자 띄우기
            }
        }

        if (storage_write_permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); //<---- 권한 부여 요청 대화상자 띄우기
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        this.grantResult = grantResults;

//        for(int i = 0 ; i < grantResults.length; i++) {
//            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                permission_count ++;
//            }
//        }
//
//        if (permission_count == grantResults.length) {
//
//        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.myhome_admin);

        findViewById(R.id.button_admin).setOnClickListener(handler);
    }

}
