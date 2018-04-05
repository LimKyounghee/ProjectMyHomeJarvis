package com.example.user.myhomejarvis.AppWidget_package;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.user.myhomejarvis.Activity_package.Login_Activity;
import com.example.user.myhomejarvis.Activity_package.Project_Main;
import com.example.user.myhomejarvis.AppWidget_package.Widget_Receiver;
import com.example.user.myhomejarvis.LogManager;
import com.example.user.myhomejarvis.R;

/**
 * Implementation of App Widget functionality.
 */
public class JarvisWidget extends AppWidgetProvider {

    private SharedPreferences mViewCheckPreferences;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.jarvis_widget);
        // setting_btn을 눌렀을 경우
        views.setOnClickPendingIntent(R.id.widget_setting_btn, PendingIntent.getActivity(context, 1000 ,  new Intent(context, Project_Main.class), 0));
        // 장비 추가 버튼을 눌렀을 경우
        views.setOnClickPendingIntent(R.id.add_device_btn, PendingIntent.getActivity(context, 1000 ,  new Intent(context, Project_Main.class), 0));


        //장비 버튼이 눌렀을 경우
        views.setOnClickPendingIntent(R.id.widget_led1_btn, PendingIntent.getBroadcast(context, 1000, new Intent(context, Widget_Receiver.class).setAction("led1"), 0));
        views.setOnClickPendingIntent(R.id.widget_led2_btn, PendingIntent.getBroadcast(context, 1000, new Intent(context, Widget_Receiver.class).setAction("led2"), 0));
        views.setOnClickPendingIntent(R.id.widget_led3_btn, PendingIntent.getBroadcast(context, 1000, new Intent(context, Widget_Receiver.class).setAction("led3"), 0));
        views.setOnClickPendingIntent(R.id.widget_led4_btn, PendingIntent.getBroadcast(context, 1000, new Intent(context, Widget_Receiver.class).setAction("led4"), 0));
        views.setOnClickPendingIntent(R.id.widget_fan_btn, PendingIntent.getBroadcast(context, 1000, new Intent(context, Widget_Receiver.class).setAction("fan"), 0));
        views.setOnClickPendingIntent(R.id.widget_plug_btn, PendingIntent.getBroadcast(context, 1000, new Intent(context, Widget_Receiver.class).setAction("plug"), 0));
        views.setOnClickPendingIntent(R.id.widget_return_btn, PendingIntent.getBroadcast(context, 1000, new Intent(context, Widget_Receiver.class).setAction("update_status"), 0));
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        LogManager.print("위젯에서 최초 실행시");
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName cName = new ComponentName(context, JarvisWidget.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.jarvis_widget);

        mViewCheckPreferences = context.getSharedPreferences("jarvis", 0);
        String fan_id = mViewCheckPreferences.getString("fan_device_id","");
        String plug_id = mViewCheckPreferences.getString("plug_device_id", "");
        String led1_id = mViewCheckPreferences.getString("led1_device_id", "");
        String led2_id = mViewCheckPreferences.getString("led2_device_id", "");
        String led3_id = mViewCheckPreferences.getString("led3_device_id", "");
        String led4_id = mViewCheckPreferences.getString("led4_device_id", "");
        String fan_status = mViewCheckPreferences.getString("fan_status", "");
        String plug_status = mViewCheckPreferences.getString("plug_status", "");
        String led1_status = mViewCheckPreferences.getString("led1_status", "");
        String led2_status = mViewCheckPreferences.getString("led2_status", "");
        String led3_status = mViewCheckPreferences.getString("led3_status", "");
        String led4_status = mViewCheckPreferences.getString("led4_status", "");
        String userID = mViewCheckPreferences.getString("userID", "");

        LogManager.print("WIdget led2_id : " + led2_id);

        if (led1_status.equals("켜짐")) {
            views.setInt(R.id.widget_room1_led, "setBackgroundResource", R.drawable.lighton);
            views.setTextViewText(R.id.widget_led1_btn, "켜짐");
            views.setInt(R.id.widget_led1_btn, "setBackgroundResource", R.drawable.button2);
        } else {
            views.setInt(R.id.widget_room1_led, "setBackgroundResource", R.drawable.lightoff);
            views.setTextViewText(R.id.widget_led1_btn, "꺼짐");
            views.setInt(R.id.widget_led1_btn, "setBackgroundResource", R.drawable.button3);
        }
        if (led2_status.equals("켜짐")) {
            views.setInt(R.id.widget_room2_led, "setBackgroundResource", R.drawable.lighton);
            views.setTextViewText(R.id.widget_led2_btn, "켜짐");
            views.setInt(R.id.widget_led2_btn, "setBackgroundResource", R.drawable.button2);
        } else {
            views.setInt(R.id.widget_room2_led, "setBackgroundResource", R.drawable.lightoff);
            views.setTextViewText(R.id.widget_led2_btn, "꺼짐");
            views.setInt(R.id.widget_led2_btn, "setBackgroundResource", R.drawable.button3);
        }
        if (led3_status.equals("켜짐")) {
            views.setInt(R.id.widget_livingroom_led, "setBackgroundResource", R.drawable.lighton);
            views.setTextViewText(R.id.widget_led3_btn, "켜짐");
            views.setInt(R.id.widget_led3_btn, "setBackgroundResource", R.drawable.button2);
        } else {
            views.setInt(R.id.widget_livingroom_led, "setBackgroundResource", R.drawable.lightoff);
            views.setTextViewText(R.id.widget_led3_btn, "꺼짐");
            views.setInt(R.id.widget_led3_btn, "setBackgroundResource", R.drawable.button3);
        }
        if (led4_status.equals("켜짐")) {
            views.setInt(R.id.widget_restroom_led, "setBackgroundResource", R.drawable.lighton);
            views.setTextViewText(R.id.widget_led4_btn, "켜짐");
            views.setInt(R.id.widget_led4_btn, "setBackgroundResource", R.drawable.button2);
        } else {
            views.setInt(R.id.widget_restroom_led, "setBackgroundResource", R.drawable.lightoff);
            views.setTextViewText(R.id.widget_led4_btn, "꺼짐");
            views.setInt(R.id.widget_led4_btn, "setBackgroundResource", R.drawable.button3);
        }
        if (fan_status.equals("강")) {
            views.setTextViewText(R.id.widget_fan_btn, "강");
            views.setInt(R.id.widget_fan_btn, "setBackgroundResource", R.drawable.button2);
        } else if (fan_status.equals("중")){
            views.setTextViewText(R.id.widget_fan_btn, "중");
            views.setInt(R.id.widget_fan_btn, "setBackgroundResource", R.drawable.button2);
        } else if (fan_status.equals("약")){
            views.setTextViewText(R.id.widget_fan_btn, "약");
            views.setInt(R.id.widget_fan_btn, "setBackgroundResource", R.drawable.button2);
        } else {
            views.setTextViewText(R.id.widget_fan_btn, "꺼짐");
            views.setInt(R.id.widget_fan_btn, "setBackgroundResource", R.drawable.button3);
        }
        if (plug_status.equals("켜짐")) {
            views.setTextViewText(R.id.widget_plug_btn, "켜짐");
            views.setInt(R.id.widget_plug_btn, "setBackgroundResource", R.drawable.button2);
        } else {
            views.setTextViewText(R.id.widget_plug_btn, "꺼짐");
            views.setInt(R.id.widget_plug_btn, "setBackgroundResource", R.drawable.button3);
        }
        widgetManager.updateAppWidget(cName, views);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

