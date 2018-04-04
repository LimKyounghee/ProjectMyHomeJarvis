package com.example.user.myhomejarvis.Activity_package;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.user.myhomejarvis.Permission_package.PermissionUtil;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.LogManager;

/**
 * Created by user on 2018-03-24.
 */

public class Loading_Activity extends AppCompatActivity {

    public static final int REQUEST_CODE_MENU = 105;
    TextView loading;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.myhome_mainepage);

        loading = findViewById(R.id.loadingView);
        progressBar = findViewById(R.id.progressBar);
        doAction1();

    }

    void doAction1() {
//        진행바 : 0 ~ 100
//        0부터 시작해서 100에 끝나고, 1씩 채워진다고 설정
//        값이 바뀔때 마다 UI를 변경하도록
//        CountTask task = new CountTask();
//        task.execute(20,80,2);
        new CountTask().execute(0,100,1);

//        Intent intent = new Intent(getApplicationContext(), AdmitActivity.class);
//        startActivity(intent);

    }

    class CountTask extends AsyncTask<Integer, Integer, String> {
//         로직은 doInBackground 에서만 , UI는 Execute, ProgressUpdata에서만...

        @Override
        protected void onPreExecute() {  // 사전 UI변경
            super.onPreExecute();

            loading.setText("로딩중입니다.....");
        }

        @Override
        protected void onPostExecute(String s) {  // 사후 UI변경
            super.onPostExecute(s);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            loading.setText(String.format("로딩중입니다..... %s",""));
            progressBar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int start = integers[0];
            int end = integers[1];
            int step = integers[2];

            int num = start;
            while(num <= end) {
                num += step;
                LogManager.print("num : " + num);
                if (num == 34 || num == 72 ) {
                    SystemClock.sleep(600);
                }
                publishProgress(num);
                SystemClock.sleep(60);
            }
            // 권한이 있으면 바로 로그인 화면으로  없으면 권한 부여화면으로 이동
            if(PermissionUtil.checkPermissions(Loading_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && (PermissionUtil.checkPermissions(Loading_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    && (PermissionUtil.checkPermissions(Loading_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION))
                    && (PermissionUtil.checkPermissions(Loading_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION))
                    && (PermissionUtil.checkPermissions(Loading_Activity.this, Manifest.permission.RECORD_AUDIO))) {
                // 권한이 있으므로 원하는 메소드를 사용
                LogManager.print("바로 로그인 화면으로 이동");
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(getApplicationContext(), Admit_Activity.class);
                startActivity(intent);
                finish();
            }
            return "succes";
        }
    }

}
