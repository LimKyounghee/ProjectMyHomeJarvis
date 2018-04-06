package com.example.user.myhomejarvis.Activity_package;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.gson.Gson;

import java.net.URLEncoder;

/**
 * Created by user on 2018-04-06.
 */

public class Webview_Activitiy extends AppCompatActivity{

    private final static String TAG ="Webview_Activitiy";

    WebView webView;
    String url;
    Bundle bundle;
    UserInfoVO userInfoVO;

    String userID;
    String userPW;

    Intent intent;


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        //서버로 페이지 요청하기

        bundle = getIntent().getBundleExtra("User_Info");
        userInfoVO = (UserInfoVO) bundle.getSerializable("UserInfoVO") ;

        SharedPreferences pref = getSharedPreferences("jarvis", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        userID = pref.getString("userID","no");
        userPW = pref.getString("userPW", "no");

        String str = null;
        if(!userID.equals("no") && !userPW.equals("no")){

            try{
                str = "userID="+ URLEncoder.encode(userID,"UTF-8")
                        + "&pw=" + URLEncoder.encode(userPW,"UTF-8");

            }catch (Exception e){

            }

        }else {
            Log.d(TAG,"아이디와 비번이 넑밧임");
        }

        webView = findViewById(R.id.Web_View_layout);


        try {
            webView.postUrl(Server_URL.getFind_webView_URL(), str.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);


    }
}
