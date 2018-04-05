package com.example.user.myhomejarvis.AppWidget_package;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.user.myhomejarvis.LogManager;
import com.example.user.myhomejarvis.R;

public class Widget_UI_Change_Receiver extends BroadcastReceiver {
    private Context context;
    private RemoteViews views;
    private SharedPreferences mViewCheckPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String action = intent.getAction();
        String deviceID = intent.getStringExtra("deviceID");
        LogManager.print("widget_UI_change_Receiver action : " + action);
        LogManager.print("widget_UI_change_Receiver deviceID : " + deviceID);
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName cName = new ComponentName(context, JarvisWidget.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.jarvis_widget);
        this.views = views;  // 멤버 변수로 설정해줌 -> 아래 url 커넥션이후 쓰래드에서 사용할 수 있도록하기 위함
        mViewCheckPreferences = context.getSharedPreferences("jarvis", 0);
        SharedPreferences.Editor editor = mViewCheckPreferences.edit();
        switch(action) {
            case "plugOn":
                LogManager.print("위젯에서 플러그 켜짐으로 UI 바꾸기 실행");
                views.setTextViewText(R.id.widget_plug_btn, "켜짐");
                views.setInt(R.id.widget_plug_btn, "setBackgroundResource", R.drawable.button2);
                editor.putString("plug_status", "켜짐");
                editor.commit();
                break;
            case "plugOff":
                LogManager.print("위젯에서 플러그 꺼짐으로 UI 바꾸기 실행");
                views.setTextViewText(R.id.widget_plug_btn, "꺼짐");
                views.setInt(R.id.widget_plug_btn, "setBackgroundResource", R.drawable.button3);
                editor.putString("plug_status", "꺼짐");
                editor.commit();
                break;
            case "led1On":
                LogManager.print("위젯에서 전등1 켜짐으로 UI 바꾸기 실행");
                views.setInt(R.id.widget_room1_led, "setBackgroundResource", R.drawable.lighton);
                views.setTextViewText(R.id.widget_led1_btn, "켜짐");
                views.setInt(R.id.widget_led1_btn, "setBackgroundResource", R.drawable.button2);
                editor.putString("led1_status", "켜짐");
                editor.commit();
                break;
            case "led2On":
                LogManager.print("위젯에서 전등2 켜짐으로 UI 바꾸기 실행");
                views.setInt(R.id.widget_room2_led, "setBackgroundResource", R.drawable.lighton);
                views.setTextViewText(R.id.widget_led2_btn, "켜짐");
                views.setInt(R.id.widget_led2_btn, "setBackgroundResource", R.drawable.button2);
                editor.putString("led2_status", "켜짐");
                editor.commit();
                break;
            case "led3On":
                LogManager.print("위젯에서 전등3 켜짐으로 UI 바꾸기 실행");
                views.setInt(R.id.widget_livingroom_led, "setBackgroundResource", R.drawable.lighton);
                views.setTextViewText(R.id.widget_led3_btn, "켜짐");
                views.setInt(R.id.widget_led3_btn, "setBackgroundResource", R.drawable.button2);
                editor.putString("led3_status", "켜짐");
                editor.commit();
                break;
            case "led4On":
                LogManager.print("위젯에서 전등4 켜짐으로 UI 바꾸기 실행");
                views.setInt(R.id.widget_restroom_led, "setBackgroundResource", R.drawable.lighton);
                views.setTextViewText(R.id.widget_led4_btn, "켜짐");
                views.setInt(R.id.widget_led4_btn, "setBackgroundResource", R.drawable.button2);
                editor.putString("led4_status", "켜짐");
                editor.commit();
                break;
            case "led1Off":
                views.setInt(R.id.widget_room1_led, "setBackgroundResource", R.drawable.lightoff);
                views.setTextViewText(R.id.widget_led1_btn, "꺼짐");
                views.setInt(R.id.widget_led1_btn, "setBackgroundResource", R.drawable.button3);
                editor.putString("led1_status", "꺼짐");
                editor.commit();
                break;
            case "led2Off":
                views.setInt(R.id.widget_room2_led, "setBackgroundResource", R.drawable.lightoff);
                views.setTextViewText(R.id.widget_led2_btn, "꺼짐");
                views.setInt(R.id.widget_led2_btn, "setBackgroundResource", R.drawable.button3);
                editor.putString("led2_status", "꺼짐");
                editor.commit();
                break;
            case "led3Off":
                views.setInt(R.id.widget_livingroom_led, "setBackgroundResource", R.drawable.lightoff);
                views.setTextViewText(R.id.widget_led3_btn, "꺼짐");
                views.setInt(R.id.widget_led3_btn, "setBackgroundResource", R.drawable.button3);
                editor.putString("led3_status", "꺼짐");
                editor.commit();
                break;
            case "led4Off":
                views.setInt(R.id.widget_restroom_led, "setBackgroundResource", R.drawable.lightoff);
                views.setTextViewText(R.id.widget_led4_btn, "꺼짐");
                views.setInt(R.id.widget_led4_btn, "setBackgroundResource", R.drawable.button3);
                editor.putString("led4_status", "꺼짐");
                editor.commit();
                break;

            case "fanStrong":
                LogManager.print("위젯에서 선풍기 강으로 UI 바꾸기 실행");
                views.setTextViewText(R.id.widget_fan_btn, "강");
                views.setInt(R.id.widget_fan_btn, "setBackgroundResource", R.drawable.button2);
                editor.putString("fan_status", "강");
                editor.commit();
                break;
            case "fanMiddle":
                views.setTextViewText(R.id.widget_fan_btn, "중");
                views.setInt(R.id.widget_fan_btn, "setBackgroundResource", R.drawable.button2);
                editor.putString("fan_status", "중");
                editor.commit();
                break;
            case "fanWeak":
                views.setTextViewText(R.id.widget_fan_btn, "약");
                views.setInt(R.id.widget_fan_btn, "setBackgroundResource", R.drawable.button2);
                editor.putString("fan_status", "약");
                editor.commit();
                break;
            case "fanOff":
                views.setTextViewText(R.id.widget_fan_btn, "꺼짐");
                views.setInt(R.id.widget_fan_btn, "setBackgroundResource", R.drawable.button3);
                editor.putString("fan_status", "꺼짐");
                editor.commit();
                break;
        }
        widgetManager.updateAppWidget(cName, views);

    }
}
