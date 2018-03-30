package com.example.user.myhomejarvis.Activity_package;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.user.myhomejarvis.Permission_package.PermissionUtil;
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

                    if(PermissionUtil.checkPermissions(Admit_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            && (PermissionUtil.checkPermissions(Admit_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
                            && (PermissionUtil.checkPermissions(Admit_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION))
                            && (PermissionUtil.checkPermissions(Admit_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION))) {
                        // 권한이 있으므로 원하는 메소드를 사용
                        LogManager.print("바로 로그인 화면으로 이동");
                        Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                        startActivity(intent);
                    }else {
                        PermissionUtil.requestExternalPermissions(Admit_Activity.this);
                    }

                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtil.REQUEST_STORAGE) {
            if (PermissionUtil.verifyPermission(grantResults)) {
                // 요청한 권한을 얻었으므로, 원하는 메소드를 사용
                LogManager.print("로그인 화면으로 이동");
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }else {
                showRequestAgainDialog();
            }
        }
    }

    private void showRequestAgainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("이 권한은 꼭 필요한 권한이므로, 설정에서 활성화부탁드립니다.");
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                            Uri.parse("package: " + getPackageName()));
                    startActivity(intent);
                }catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 취소함
                LogManager.print("권한을 다시 취소함");
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.myhome_admin);

        findViewById(R.id.button_admin).setOnClickListener(handler);
    }
}
