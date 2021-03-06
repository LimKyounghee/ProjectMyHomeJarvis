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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.FindFamilyVO;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_add_family;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.ListView_Util.Find_family_LinearLayout;
import com.example.user.myhomejarvis.ListView_Util.Find_family_item_VO;
import com.example.user.myhomejarvis.LogManager;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.example.user.myhomejarvis.Server_Connection_package.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyun on 2018-03-27.
 */

public class Find_Family_Activity extends AppCompatActivity {
    Bundle bundle;
    UserInfoVO vo;
    EditText find_edit;
    ListView findFamilyListView;
    FamilyAdapter adapter;
    String num = "0123456789";
    String url = Server_URL.getFindFamily_URL();
    String phoneNum ="";
    FindFamilyVO familyVO;
    ThreadHandler2 threadHandler2 = new ThreadHandler2();

    String result ;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_btn:
                    String phone = find_edit.getText().toString();
                    phoneNum = phone;
                    LogManager.print("phone : " + phone);
                    doSendToServer(phone);

                    break;
            }
        }
    };

    public void doSendToServer(String phone) {
        ServerConnection serverConnection;
        ThreadHandler threadHandler = new ThreadHandler();
        String type = "phone";
        LogManager.print("doSendToServer phone : " + phone);

        if (!checkPhone(phone)) {
            Toast.makeText(getApplicationContext(), "휴대전화번호를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else {
            try{
                serverConnection = new ServerConnection(phone, url, type, threadHandler);
                LogManager.print("쓰래드 시작");
                serverConnection.start();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class ThreadHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg != null) {
                Bundle b = msg.getData();
                String result = b.getString("data");

                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(), "서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{
                    //Gson으로 문자열 객체로 전달하기
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_add_family gsonResponse;

                    gsonResponse = gsonresult.getResponse_add_family(result);

                    FindFamilyVO findFamilyVO = gsonResponse.getFamilyinfo();
                    familyVO = findFamilyVO; // 나중에 다른 메소드에서 사용하기 위해 멤버 변수에 저장

                    LogManager.print("findFamilyVO 객체 저장"+findFamilyVO.toString());

                    if(gsonResponse == null){
                        LogManager.print("gsonResponse null");
                    }else{
                        String getEvent = gsonResponse.getEvent();

                        if(getEvent.equals("FindFamilyInfo")){
                            if(findFamilyVO == null) {
                                Toast.makeText(getApplicationContext(), "해당 휴대전화번호로 등록된 가족이 없습니다.", Toast.LENGTH_LONG).show();
                                showMessage("search_fail");
                            }else {
                                // 리스트 뷰에 화면 출력
                                int image = R.drawable.family;
                                if (!"0000".equals(findFamilyVO.getFamilyIcon())) {
                                    image = R.drawable.family;
                                }

                                adapter.addItem(new Find_family_item_VO(findFamilyVO.getFamilyName(), image));
                                findFamilyListView.setAdapter(adapter);

                            }
                        }else {
                            showMessage("search_fail");
                        }
                    }
                }
            }
        }
    }

    //전화번호 체크하는 메소드
    public boolean checkPhone (String phone) {
        boolean flag;
        int count = 0;
        LogManager.print("checkPhone phone : " + phone);
        String[] splitPhone = phone.split("");
        if (splitPhone.length != 12) {
            LogManager.print("phone length : " + splitPhone.length);
            flag = false;
        } else {
            for (int i=0 ; i < splitPhone.length ; i++ ) {
                if (num.indexOf(splitPhone[i]) != -1) {
                    count += 1;
                }
            }
            LogManager.print("checkPhone count : " + count);
            if (splitPhone.length == count) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    private void showMessage(String type){
        LogManager.print("showMessage 들어옴");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");

        switch (type){

            case "click_family":
                builder.setMessage("해당 가족에 등록하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        sendToServerForRegistFamily(phoneNum);
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogManager.print("취소버튼 누름");
                    }
                }) ;
                break;
            case "search_fail":
                builder.setMessage("가족 검색에 실패했습니다. 다시 시도해주세요.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        LogManager.print("확인버튼 누름");
                    }
                });
                break;

        }

        AlertDialog dialog =builder.create();
        dialog.show();
    }

    public void sendToServerForRegistFamily(String phone) {
        ServerConnectionWithoutHandler serverCon;
        String data1 = phone;
        String data2 = vo.getName();   // 다시 서버에 보내야할 data를 저장
        String data3 = vo.getUserID();
        int data4 = familyVO.getFamilyID();

        LogManager.print(String.format("phone : %s, userName : %s, userID : %s, familyID : %d", data1, data2, data3, data4));
        String type1 = "phone";
        String type2 = "userName";
        String type3 = "userID";
        String type4 = "familyID";
        String url = Server_URL.getAddFamily_URL();


        try{
            serverCon = new ServerConnectionWithoutHandler(data1, data2, data3, data4, type1, type2, type3, type4, url);
            LogManager.print("쓰래드 시작");
            serverCon.start();
//            result = serverCon.getResult();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ServerConnectionWithoutHandler extends Thread {

        private final static String TAG = "ServerConnection";

        String data1;
        String data2;
        String data3;
        int data4;
        String url;
        String type1;
        String type2;
        String type3;
        String type4;
//        String result;

        public ServerConnectionWithoutHandler(String data1, String data2, String data3, int data4,
                                              String type1, String type2, String type3, String type4, String url) {
            this.data1 = data1;
            this.data2 = data2;
            this.data3 = data3;
            this.data4 = data4;
            this.type1 = type1;
            this.type2 = type2;
            this.type3 = type3;
            this.type4 = type4;
            this.url = url;
        }

        public void run() {
            result = sendToServer();
            LogManager.print("서버로 보낸 결과 : " + result);

            Message message = new Message();
            Bundle b = message.getData();
            b.putString("data", result);
//            ThreadHandler2 threadHandler2 = new ThreadHandler2();
            threadHandler2.sendMessage(message);
            Log.d(TAG,"쓰레드 핸들러 들어감 " + result);
        }

        String sendToServer(){

            Map<String, String> params = new HashMap<String, String>();
            params.put(type1,data1);
            params.put(type2,data2);
            params.put(type3,data3);
            params.put(type4,Integer.toString(data4));
            Util util = new Util();
            String result ="";

            try{

                result = util.sendPost(url,params);
            }catch (Exception e){

                e.getStackTrace();
            }

            return  result;

        }

    }

    class ThreadHandler2 extends Handler {

        @Override
        public void handleMessage(Message msg) {
            LogManager.print("핸들러 연결");
            super.handleMessage(msg);

            if (msg != null) {

                Bundle b = msg.getData();
                String result = b.getString("data");
                if (("").equals(result)) {
                    Toast.makeText(getApplicationContext(), "서버와 통신 실패", Toast.LENGTH_SHORT).show();
                } else {
                    LogManager.print("result : " + result);
                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_Join gsonResponse;

                    gsonResponse = gsonresult.getStatus_join(result);

                    if(gsonResponse == null) {
                        LogManager.print("gsonResponse null 값나옴");
                    }else{
                        //그래서 결과를 바탕으로 메세지 박스를 만든다.
                        String getEvent = gsonResponse.getEvent();
                        String getStatus = gsonResponse.getStatus();

                        if(getEvent.equals("Add Family")){

                            if(getStatus.equals("ok")){

                                Toast.makeText(getApplicationContext(), "가족이 신청을 확인할 때 까지 기다려주십시오.", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getApplicationContext(), "서버 오류. 잠시후 다시 시도해주십시오.", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Project_Main.class);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.find_family);

        bundle = getIntent().getBundleExtra("User_Info");
        vo =(UserInfoVO) bundle.getSerializable("UserInfoVO");

        findFamilyListView = (ListView)findViewById(R.id.find_family_listView);
        adapter = new FamilyAdapter();

        find_edit = findViewById(R.id.find_family_edit);
        findViewById(R.id.search_btn).setOnClickListener(handler);

        findFamilyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessage("click_family");
            }
        });
    }

    class FamilyAdapter extends BaseAdapter {
        ArrayList<Find_family_item_VO> items = new ArrayList<Find_family_item_VO>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Find_family_item_VO item) {
            items.add(item);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Find_family_LinearLayout view = new Find_family_LinearLayout(getApplicationContext());
            Find_family_item_VO item = items.get(position);
            view.setFamilyName(item.getFamilyName());
            view.setImage(item.getResId());

            return view;
        }
    }
}

