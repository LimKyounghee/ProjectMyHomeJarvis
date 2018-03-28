package com.example.user.myhomejarvis.Activity_package;

/**
 * Created by Kyun on 2018-03-27.
 */

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.user.myhomejarvis.R;

public class Create_Family_Activity extends AppCompatActivity {
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.create_family);

        bundle = getIntent().getBundleExtra("User_Info");
    }
}