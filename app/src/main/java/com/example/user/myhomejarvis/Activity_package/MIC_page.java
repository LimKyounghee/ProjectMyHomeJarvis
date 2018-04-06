package com.example.user.myhomejarvis.Activity_package;

import android.content.pm.ActivityInfo;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.Config_device;
import com.example.user.myhomejarvis.Data_Info_package.On_Off_Control_Data;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.Mic_Clover.AudioWriterPCM;
import com.example.user.myhomejarvis.Mic_Clover.NaverRecognizer;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.gson.Gson;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 2018-04-04.
 */

public class MIC_page extends AppCompatActivity{


    private static final String TAG = MIC_page.class.getSimpleName();
    private static final String CLIENT_ID = "xrQmGgq0FjKf8pVU_O8Q";
    // 1. "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    // 2. build.gradle (Module:app)에서 패키지명을 실제 개발자센터 애플리케이션 설정의 '안드로이드 앱 패키지 이름'으로 바꿔 주세요

    Bundle bundle;
    UserInfoVO vo;
    ArrayList<Config_device> config_devices = new ArrayList<Config_device>();

    String userID;

    private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;

    private TextView txtResult;
    private Button btnStart;
    private String mResult;

    private AudioWriterPCM writer;

    private TextToSpeech tts;

    // Handle speech recognition Messages.
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                txtResult.setText("Connected");
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
                txtResult.setText(mResult);
                Log.d("result" ,mResult);
                break;

            case R.id.finalResult:
                // Extract obj property typed with String array.
                // The first element is recognition result for speech.
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                List<String> results = speechRecognitionResult.getResults();
                StringBuilder strBuf = new StringBuilder();
                for(String result : results) {
                    strBuf.append(result);
                    strBuf.append("\n");
                }
                mResult = strBuf.toString();
//                txtResult.setText(mResult);

                //받은 텍스트를 가지고 컨트롤 하기
                do_config_speaking(mResult);

                Log.d("result ",mResult );
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                txtResult.setText(mResult);

                btnStart.setBackgroundResource(R.drawable.mic_controll_2);
                btnStart.setEnabled(true);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

                btnStart.setBackgroundResource(R.drawable.mic_controll_2);
                btnStart.setEnabled(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mic_controller);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bundle = getIntent().getBundleExtra("User_Info");
        config_devices = (ArrayList<Config_device>) bundle.getSerializable("config_device");
        Log.d(TAG,"djddjd"+config_devices.toString());
        vo =(UserInfoVO) bundle.getSerializable("UserInfoVO");
        userID = vo.getUserID();


        Log.d(TAG,"왔음");

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){

                    tts.setLanguage(Locale.KOREAN);

                }
            }
        });

        txtResult = (TextView) findViewById(R.id.textView_mic_result);
        btnStart = (Button) findViewById(R.id.button_start_mic);

        handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(this, handler, CLIENT_ID);
        findViewById(R.id.button_close_mic_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mResult = "";
                    txtResult.setText("Connecting...");


                    btnStart.setBackgroundResource(R.drawable.mic_controll_1);

                    naverRecognizer.recognize();
                } else {
                    Log.d(TAG, "stop and wait Final Result");
                    btnStart.setEnabled(false);

                    naverRecognizer.getSpeechRecognizer().stop();
                }
            }
        });
    }

    String[] controling_text = {"방 불 꺼줘","방 불 켜줘","거실 불 꺼줘","거실 불 켜줘","화장실 불 꺼줘",
            "화장실 불 켜줘","주방 불 꺼줘","주방 불 켜줘", "플러그 전원 꺼줘","플러그 전원 켜줘",
            "선풍기 강","선풍기 중","선풍기 약","선풍기 꺼줘"};
    String control_request;

    String[] devicetype = {"Light","Fan","Plug"};

    ArrayList<String> fan_list = new ArrayList<String>();
    ArrayList<String> light_list = new ArrayList<String>();
    ArrayList<String> plug_list = new ArrayList<String>();

    String deviceID;

    int light_num = 0;
    int fan_num=0;
    int plug_num=0;

    String type;
    String url;


    void do_config_speaking(String result){

        int indx = -1;

        for(int i = 0; i<controling_text.length; i++){

            indx = result.indexOf(controling_text[i]);
            if(indx != -1){
                control_request=controling_text[i];
            }

        }
        Log.d(TAG,"요청  " +control_request );


        //디바이스 리스트에 내가 가지고 있는 장비중 컨드롤링 할 수 있는 장비만 저장

        for(int i = 0; i<config_devices.size();i++){

            if(config_devices.get(i).getDeviceID().indexOf("Light") != -1){

                light_list.add(config_devices.get(i).getDeviceID());
                light_num++;

            }else if(config_devices.get(i).getDeviceID().indexOf("Fan") != -1){

                fan_list.add(config_devices.get(i).getDeviceID());
                fan_num++;

            }else if(config_devices.get(i).getDeviceID().indexOf("Plug") != -1){

                plug_list.add(config_devices.get(i).getDeviceID());
                plug_num++;
            }

        }

        //여기 이쁘게 정맇ㄹ 수 있으면 정리하기;\

        if(control_request.indexOf("방") != -1){

            deviceID = light_list.get(0);
            type="led_onoff";
            url = Server_URL.getLed_onoff_URL();

            if(control_request.indexOf("꺼줘") != -1){

                do_Control_device(deviceID,"꺼짐",type,url);

            }else if(control_request.indexOf("켜줘") != -1){
                do_Control_device(deviceID,"켜짐",type,url);
            }

        }else if(control_request.indexOf("거실") != -1){

            deviceID = light_list.get(1);
            type="led_onoff";
            url = Server_URL.getLed_onoff_URL();
            if(control_request.indexOf("꺼줘") != -1){

                do_Control_device(deviceID,"꺼짐",type,url);

            }else if(control_request.indexOf("켜줘") != -1){
                do_Control_device(deviceID,"켜짐",type,url);
            }

        }else if(control_request.indexOf("화장실") != -1){

            if(light_list.get(2)== null){
                txtResult.setText("화장실 불은 연결이 되어있지 않습니다.");
            }else{

                deviceID = light_list.get(2);
                type="led_onoff";
                url = Server_URL.getLed_onoff_URL();
                if(control_request.indexOf("꺼줘") != -1){

                    do_Control_device(deviceID,"꺼짐",type,url);

                }else if(control_request.indexOf("켜줘") != -1){
                    do_Control_device(deviceID,"켜짐",type,url);
                }
            }


        }else if(control_request.indexOf("주방") != -1){

            if(light_list.get(3)== null) {
                txtResult.setText("화장실 불은 연결이 되어있지 않습니다.");
            }else{
                deviceID = light_list.get(3);
                type="led_onoff";
                url = Server_URL.getLed_onoff_URL();
                if(control_request.indexOf("꺼줘") != -1){

                    do_Control_device(deviceID,"꺼짐",type,url);

                }else if(control_request.indexOf("켜줘") != -1){
                    do_Control_device(deviceID,"켜짐",type,url);
                }
            }

        }else if(control_request.indexOf("플러그") != -1){

            deviceID = plug_list.get(0);
            type="plug_onoff";
            url = Server_URL.getSmartplug_URL();
            if(control_request.indexOf("꺼줘") != -1){

                do_Control_device(deviceID,"꺼짐",type,url);

            }else if(control_request.indexOf("켜줘") != -1){
                do_Control_device(deviceID,"켜짐",type,url);
            }

        }else if(control_request.indexOf("선풍기") != -1){

            deviceID = fan_list.get(0);
            type="fan_control";
            url = Server_URL.getFan_onoff_url();
            if(control_request.indexOf("강") != -1){

                do_Control_device(deviceID,"강",type,url);

            }else if(control_request.indexOf("중") != -1){
                do_Control_device(deviceID,"중",type,url);

            }else if(control_request.indexOf("약") != -1){

                do_Control_device(deviceID,"약",type,url);

            }else if(control_request.indexOf("꺼줘") != -1){

                do_Control_device(deviceID,"꺼짐",type,url);
            }
        }else{

            txtResult.setText("다시 시도해 주세용");
        }


    }




    //서버 연겷는 것 여기에다가 한당

    void do_Control_device(String deviceID, String request_Status, String type, String url){

        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

        On_Off_Control_Data on_off_control_data = new On_Off_Control_Data();
        on_off_control_data.setUserid(userID);
        on_off_control_data.setDeviceId(deviceID);
        on_off_control_data.setEventInfo(request_Status);


        Log.d(TAG,"On_Off_Control_Data ????????" +on_off_control_data);

        Log.d(TAG," On_Off_Control_Data 에 저장" + on_off_control_data.toString());

        Gson gson = new Gson();
        String on_off_data_json = gson.toJson(on_off_control_data);

        try{
            serverConnection = new ServerConnection(on_off_data_json, url, type, threadHandler);
            Log.d(TAG,"서버랑 연결");
            serverConnection.start();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    class ThreadHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.d(TAG,"핸들러 들ㅇ어옴 연결");

            if(msg != null){

                Bundle b = msg.getData();
                String result = b.getString("data");

                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(), "서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{

                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse_join;
                    Log.d(TAG,"내가 받은 값" + result);

                    gsonResponse_join = gsonresult.getStatus_join(result);
                    Log.d(TAG,"from json 값"+gsonResponse_join);

                    if(gsonResponse_join == null){

                        Log.d(TAG,"gsonResponse_join 이 널값임");
                    }else{

                        String getEvent = gsonResponse_join.getEvent();
                        String getStatus = gsonResponse_join.getStatus();

                        Log.d(TAG,"from json 값"+gsonResponse_join);

                        switch (getEvent){

                            case "plugOnOff":

                                switch (getStatus){
                                    case "켜짐":
                                        txtResult.setText("플러그가 켜졌습니다.");
                                        break;
                                    case "꺼짐":
                                        txtResult.setText("플러그가 꺼졌습니다.");
                                        break;
                                }

                            break;

                            case "lightOnOff":

                                switch (getStatus){
                                    case "켜짐":
                                        txtResult.setText("불이 켜졌습니다.");
                                        break;
                                    case "꺼짐":
                                        txtResult.setText("불이 꺼졌습니다..");
                                        break;
                                }

                                break;

                            case "fanStatus"://어떤값이 들어ㅗ는지 모르겠음...

                                switch (getStatus){
                                    case "켜짐":
                                        txtResult.setText("불이 켜졌습니다.");
                                        break;
                                    case "꺼짐":
                                        txtResult.setText("불이 꺼졌습니다..");
                                        break;
                                }

                                break;
                        }
                        Log.d(TAG,"000000000000"+txtResult.getText().toString());
                        tts.speak(txtResult.getText().toString(),TextToSpeech.QUEUE_FLUSH, null);

                    }





                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // NOTE : initialize() must be called on start time.
        naverRecognizer.getSpeechRecognizer().initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mResult = "";
        txtResult.setText("");
        btnStart.setBackgroundResource(R.drawable.mic_controll_2);
        btnStart.setEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }

    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<MIC_page> mActivity;

        RecognitionHandler(MIC_page activity) {
            mActivity = new WeakReference<MIC_page>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MIC_page activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }
}
