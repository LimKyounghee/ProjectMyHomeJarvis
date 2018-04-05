package com.example.user.myhomejarvis.AppWidget_package;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.user.myhomejarvis.Data_Info_package.On_Off_Control_Data;
import com.example.user.myhomejarvis.LogManager;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.example.user.myhomejarvis.Server_Connection_package.Util;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Widget_Receiver extends BroadcastReceiver {

    private SharedPreferences mViewCheckPreferences;
    private Context context;
    private RemoteViews views;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String action = intent.getAction();

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName cName = new ComponentName(context, JarvisWidget.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.jarvis_widget);
        this.views = views;  // 멤버 변수로 설정해줌 -> 아래 url 커넥션이후 쓰래드에서 사용할 수 있도록하기 위함

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

        LogManager.print("WIdget led2_id : " + led2_id + led1_id + led3_id + led4_id + plug_id + fan_id);

        switch (action) {
            case "led1":
                switch (led1_status){
                    case "켜짐":
                        LogManager.print("위젯에서 led1 켜짐 버튼 눌림");
                        do_ON_OFF_Action(led1_id, "꺼짐", userID, "led_onoff", Server_URL.getLed_onoff_URL());
                        break;
                    case "꺼짐":
                        LogManager.print("위젯에서 led1 꺼짐 버튼 눌림");
                        do_ON_OFF_Action(led1_id, "켜짐", userID, "led_onoff", Server_URL.getLed_onoff_URL());
                        break;
                }
                break;
            case "led2":
                switch (led2_status){
                    case "켜짐":
                        LogManager.print("위젯에서 led2 켜짐 버튼 눌림");
                        do_ON_OFF_Action(led2_id, "꺼짐", userID, "led_onoff", Server_URL.getLed_onoff_URL());
                        break;
                    case "꺼짐":
                        LogManager.print("위젯에서 led2 꺼짐 버튼 눌림");
                        do_ON_OFF_Action(led2_id, "켜짐", userID, "led_onoff", Server_URL.getLed_onoff_URL());
                        break;
                }
                break;
            case "led3":
                switch (led3_status){
                    case "켜짐":
                        LogManager.print("위젯에서 led3 켜짐 버튼 눌림");
                        do_ON_OFF_Action(led3_id, "꺼짐", userID, "led_onoff", Server_URL.getLed_onoff_URL());
                        break;
                    case "꺼짐":
                        LogManager.print("위젯에서 led3 꺼짐 버튼 눌림");
                        do_ON_OFF_Action(led3_id, "켜짐", userID, "led_onoff", Server_URL.getLed_onoff_URL());
                        break;
                }
                break;
            case "led4":
                switch (led4_status){
                    case "켜짐":
                        LogManager.print("위젯에서 led4 켜짐 버튼 눌림");
                        do_ON_OFF_Action(led4_id, "꺼짐", userID, "led_onoff", Server_URL.getLed_onoff_URL());
                        break;
                    case "꺼짐":
                        LogManager.print("위젯에서 led4 꺼짐 버튼 눌림");
                        do_ON_OFF_Action(led4_id, "켜짐", userID, "led_onoff", Server_URL.getLed_onoff_URL());
                        break;
                }
                break;
            case "fan":
                switch (fan_status){
                    case "강":
                        do_ON_OFF_Action(fan_id, "꺼짐", userID, "fan_control", Server_URL.getFan_onoff_url());
                        break;
                    case "중":
                        do_ON_OFF_Action(fan_id, "강", userID, "fan_control", Server_URL.getFan_onoff_url());
                        break;
                    case "약":
                        do_ON_OFF_Action(fan_id, "중", userID, "fan_control", Server_URL.getFan_onoff_url());
                        break;
                    case "꺼짐":
                        do_ON_OFF_Action(fan_id, "약", userID, "fan_control", Server_URL.getFan_onoff_url());
                        break;
                }
                break;
            case "plug":
                switch (plug_status){
                    case "켜짐":
                        do_ON_OFF_Action(plug_id, "꺼짐", userID, "plug_onoff", Server_URL.getSmartplug_URL());
                        break;
                    case "꺼짐":
                        do_ON_OFF_Action(plug_id, "켜짐", userID, "plug_onoff", Server_URL.getSmartplug_URL());
                        break;
                }
                break;
            case "update_status":
                LogManager.print("위젯 새로고침 실행");
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
                break;
        }
    }

    void do_ON_OFF_Action(String deviceID, String request_Status, String userID, String type, String url){

        ServerConnectionWithoutHandler serverConnection;

        On_Off_Control_Data on_off_control_data = new On_Off_Control_Data();
        on_off_control_data.setUserid(userID);
        on_off_control_data.setDeviceId(deviceID);
        on_off_control_data.setEventInfo(request_Status);

        LogManager.print("On_Off_Control_Data ????????" +on_off_control_data);

        LogManager.print(" On_Off_Control_Data 에 저장" + on_off_control_data.toString());

        Gson gson = new Gson();
        String on_off_data_json = gson.toJson(on_off_control_data);

        try {
            serverConnection = new ServerConnectionWithoutHandler(on_off_data_json, type, url, deviceID);
            LogManager.print("서버랑 연결");
            serverConnection.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    class ServerConnectionWithoutHandler extends Thread{

        private final static String TAG = "ServerConnection";

        String data;
        String url;
        String type;
        String deviceID;


        public ServerConnectionWithoutHandler(String data, String type, String url, String deviceID) {
            this.data = data;
            this.type = type;
            this.url = url;
            this.deviceID =deviceID;
        }

        public void run() {

            String result = sendToServer(type);

            LogManager.print("서버로 보낸 결과 " + result);
            String action ="";
            switch (result) {
                case "{\"event\":\"plugOnOff\",\"status\":\"켜짐\"}":
                    action = "plugOn";
                    break;
                case "{\"event\":\"plugOnOff\",\"status\":\"꺼짐\"}":
                    action = "plugOff";
                    break;
                case "{\"event\":\"lightOnOff\",\"status\":\"켜짐\"}":
                    switch(deviceID) {
                        case "SAMSUNG Light1":
                            action = "led1On";
                            break;
                        case "SAMSUNG Light2":
                            action = "led2On";
                            break;
                        case "LG Light1":
                            action = "led3On";
                            break;
                        case "GE Light1":
                            action = "led4On";
                            break;
                    }
                    break;
                case "{\"event\":\"lightOnOff\",\"status\":\"꺼짐\"}":
                    switch(deviceID) {
                        case "SAMSUNG Light1":
                            action = "led1Off";
                            break;
                        case "SAMSUNG Light2":
                            action = "led2Off";
                            break;
                        case "LG Light1":
                            action = "led3Off";
                            break;
                        case "GE Light1":
                            action = "led4Off";
                            break;
                    }
                    break;
                case "{\"event\":\"fanStatus\",\"status\":\"강\"}":
                    action = "fanStrong";
                    break;
                case "{\"event\":\"fanStatus\",\"status\":\"중\"}":
                    action = "fanMiddle";
                    break;
                case "{\"event\":\"fanStatus\",\"status\":\"약\"}":
                    action = "fanWeak";
                    break;
                case "{\"event\":\"fanStatus\",\"status\":\"꺼짐\"}":
                    action = "fanOff";
                    break;
            }

            Intent intent = new Intent(context, Widget_UI_Change_Receiver.class);
            intent.setAction(action);
            intent.putExtra("deviceID", deviceID);
            context.sendBroadcast(intent);
        }

        String sendToServer(String type){

            Map<String, String> params = new HashMap<String, String>();
            params.put(type,data);
            Util util = new Util();
            String result ="";

            try{
                LogManager.print("sendToServer 이상 없음");
                result = util.sendPost(url,params);
                LogManager.print("sendToServer result : " + result);
            }catch (Exception e){

                e.getStackTrace();
            }

            return  result;

        }
    }
}
