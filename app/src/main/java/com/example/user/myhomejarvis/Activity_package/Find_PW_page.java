package com.example.user.myhomejarvis.Activity_package;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;

/**
 * Created by user on 2018-04-05.
 */

public class Find_PW_page extends AppCompatActivity {


    private static final String TAG = "Find_PW_page";

    String url = Server_URL.getFind_pw_URL();

    EditText find_pw_userID;
    EditText find_pw_userhone;

    Intent intent;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.button_find_PW:
                    dofindPW();
                    break;

            }
        }
    };

    void dofindPW(){

        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();

        String type1 = "userID";
        String type2 = "phone";


        String userID;
        String userphone;

        find_pw_userID = findViewById(R.id.editText_find_PW_userID);
        find_pw_userhone = findViewById(R.id.editText_find_PW_phone);

        if(find_pw_userID.getText().toString() != null && find_pw_userhone.getText().toString() != null){

            userID = find_pw_userID.getText().toString();
            userphone = find_pw_userhone.getText().toString();


            try{


                serverConnection = new ServerConnection(userID,userphone,type1,type2,url,threadHandler);
                serverConnection.start();
                Log.d(TAG,"서버랑 연결");

            }catch (Exception e){
                e.printStackTrace();
            }


        }


    }

    class ThreadHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg != null) {

                Bundle b = msg.getData();
                String result = b.getString("data");
                String status = "";

                if (("").equals(result)) {
                    Toast.makeText(getApplicationContext(), "서버와 통신 실패", Toast.LENGTH_SHORT).show();
                } else {

                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse;

                    gsonResponse = gsonresult.getStatus_join(result);

                    if (gsonResponse == null) {
                        Log.d(TAG, "gsonResponse null 값나옴");
                    } else {

                        String getEvent = gsonResponse.getEvent();
                        String getStatus = gsonResponse.getStatus();

                        switch (getEvent) {

                            case "FINDPW":

                                if (getStatus.equals("fail")) {

                                    showMessage("NoID");

                                } else {

                                    showMessage(getStatus);
                                }

                                break;

                        }

                    }

                }
            }
        }

    }

    private void showMessage(String type){
        Log.d(TAG,"showMessage 들어옴");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
//        String content="";

        if(type.equals("NoID")){

            builder.setMessage("회원가입이 되어있지 않은 번호 입니다.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {

                    intent = new Intent(getApplicationContext(),Login_Activity.class);
                    startActivity(intent);
                    finish();

                    Log.d(TAG, "확인버튼 누름");
                }
            });

        }else{

            builder.setMessage("확인하신 번호의 비밀번호는"+"\n"+type+"\n입니다.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {

                    intent = new Intent(getApplicationContext(),Login_Activity.class);
                    startActivity(intent);
                    finish();

                    Log.d(TAG, "확인버튼 누름");
                }
            });

        }



        AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.find_pw);

        findViewById(R.id.button_find_PW).setOnClickListener(handler);
    }
}
