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
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myhomejarvis.Data_Info_package.Add_devcie;
import com.example.user.myhomejarvis.Data_Info_package.DeviceVO;
import com.example.user.myhomejarvis.Data_Info_package.Request_Info;
import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_Join;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_add_device;
import com.example.user.myhomejarvis.Gson_package.Gsonresult;
import com.example.user.myhomejarvis.ListView_Util.Add_device_LinearLayout;
import com.example.user.myhomejarvis.ListView_Util.Single_Grid_item_VO;
import com.example.user.myhomejarvis.R;
import com.example.user.myhomejarvis.RequestCode;
import com.example.user.myhomejarvis.Server_Connection_package.ServerConnection;
import com.example.user.myhomejarvis.Server_Connection_package.Server_URL;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user on 2018-03-26.
 */

public class Add_device_Activity extends AppCompatActivity {

    public static final String TAG = "Add_device_Activity";

    TextView textView;

    GridView gridView_Category;
    GridView gridView_Brand;
    GridView gridView_Device;

    SingerAdaptrer adaptrer1;
    SingerAdaptrer adaptrer2;
    SingerAdaptrer adaptrer3;

    String url;
    int userID;

    String category;
    String brand;
    String device;

    ArrayList<Single_Grid_item_VO> grid_category = new ArrayList<Single_Grid_item_VO>();
    ArrayList<Single_Grid_item_VO> grid_brand= new ArrayList<Single_Grid_item_VO>();
    ArrayList<Single_Grid_item_VO> grid_device= new ArrayList<Single_Grid_item_VO>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.add_device);


        Bundle bundle = getIntent().getBundleExtra("Grid_info");
        userID = getIntent().getIntExtra("userID",0);
        Log.d(TAG,"패밀리 아이디 : " +userID);
        Log.d(TAG,bundle.getSerializable("Grid_items").toString());
        grid_category = (ArrayList<Single_Grid_item_VO>) bundle.getSerializable("Grid_items");

        gridView_Category = findViewById(R.id.grid_category);

        adaptrer1 = new SingerAdaptrer();
        adaptrer1.setitem_vos(grid_category);

        gridView_Category.setAdapter(adaptrer1);
        adaptrer1.notifyDataSetChanged();


        gridView_Category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG,"클릭합 position : " + adaptrer1.getItem(position));

                Single_Grid_item_VO item = (Single_Grid_item_VO)(adaptrer1.getItem(position));
                Log.d(TAG,"item : " + item);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getContent_text(), Toast.LENGTH_LONG).show();
                Log.d(TAG,"Toast");
                //카테고리 저장
                category = item.getContent_text();
                Log.d(TAG,"카테고리");
                //브랜드 보내달라는 url로 간당
                url = Server_URL.getBrand();

                doServerConnect(url,"request","brand",category,brand);


            }
        });


    }

    void doCreatGird_brand(){

        gridView_Brand = findViewById(R.id.grid_brand);

        Log.d(TAG,"그리드 브랜드 뽑는당"+grid_brand.toString());
        adaptrer2 = new SingerAdaptrer();
        adaptrer2.setitem_vos(grid_brand);

        gridView_Brand.setAdapter(adaptrer2);
        adaptrer2.notifyDataSetChanged();

        gridView_Brand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Single_Grid_item_VO item = (Single_Grid_item_VO)adaptrer2.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getContent_text(), Toast.LENGTH_LONG).show();
                brand = item.getContent_text();

                url = Server_URL.getDevice();

                doServerConnect(url,"device_info","brand",category,brand);
            }
        });
    }

    void doCreatGrid_Device(){

        gridView_Device = findViewById(R.id.grid_device);
        Log.d(TAG,"그리드 브랜드 뽑는당"+grid_device.toString());

        adaptrer3 = new SingerAdaptrer();
        adaptrer3.setitem_vos(grid_device);

        gridView_Device.setAdapter(adaptrer3);
        adaptrer3.notifyDataSetChanged();

        gridView_Device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Single_Grid_item_VO item = (Single_Grid_item_VO)adaptrer3.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getContent_text(), Toast.LENGTH_LONG).show();

                device  = item.getContent_text();

                url = Server_URL.getDevice_detail();
                doServerConnect(url,"device",device);

            }
        });

    }


    void doServerConnect(String url, String jsonType, String requestInfo, String category, String brand){

        Log.d(TAG,"서버 커넷견 메소드에 들어옴");

        ServerConnection serverConnection;
        String type = jsonType;
        ThreadHandler threadHandler = new ThreadHandler();

        Request_Info request_info = new Request_Info();
        request_info.setRequest_Info(requestInfo);
        request_info.setCategory(category);
        request_info.setBrand(brand);

        Gson gson = new Gson();
        String request_info_json = gson.toJson(request_info);
        Log.d(TAG,request_info_json);
        try{

            serverConnection = new ServerConnection(request_info_json,url, type, threadHandler);
            serverConnection.start();
            Log.d(TAG,"서버연결");
        }catch (Exception e){

            e.printStackTrace();
        }


    }

    void doServerConnect(String url, String jsonType, String requestInfo){

        Log.d(TAG,"서버 커넷견 메소드에 들어옴");

        ServerConnection serverConnection;
        String type = jsonType;
        //여기서는 다른 핸들럴르 쓴당

        ThreadHandler1 threadHandler1 = new ThreadHandler1();

        try{

            serverConnection = new ServerConnection(requestInfo,url, type, threadHandler1);
            serverConnection.start();
            Log.d(TAG,"서버연결");
        }catch (Exception e){

            e.printStackTrace();
        }


    }

    void doServerConnect(String url, String jsonType, String device, int failyID){

        Log.d(TAG,"서버 커넷견 메소드에 들어옴");

        ServerConnection serverConnection;
        String type = jsonType;
        //여기서는 다른 핸들럴르 쓴당

        ThreadHandler2 threadHandler2 = new ThreadHandler2();

        Add_devcie add_devcie = new Add_devcie();
        add_devcie.setDevice(device);
        add_devcie.setFailyID(failyID);

        Gson gson = new Gson();
        String request_info_json = gson.toJson(add_devcie);
        Log.d(TAG,request_info_json);

        try{

            serverConnection = new ServerConnection(request_info_json,url, type, threadHandler2);
            serverConnection.start();
            Log.d(TAG,"서버연결");

        }catch (Exception e){

            e.printStackTrace();
        }


    }

    class ThreadHandler2 extends  Handler{

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "핸들러 연결");
            super.handleMessage(msg);

            if(msg != null){
                Bundle b = msg.getData();
                String result = b.getString("data");
                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{
                    Gsonresult gsonresult = new Gsonresult();
                    GsonResponse_Join gsonResponse_join = gsonresult.getStatus_join(result);

                    Log.d(TAG,"ㅎgson값은 이거당"+gsonResponse_join.toString());

                    if(gsonResponse_join == null) {

                        Log.d(TAG, "지슨응답에드 디바이스 값 널");
                    }else{

                        Log.d(TAG,"여기로 ㄷㄹㅇ머엄");
                        String getEvent = gsonResponse_join.getEvent();
                        String getStatus = gsonResponse_join.getStatus();

                        Log.d(TAG,"결과값" + getEvent + "0000" + getStatus);

                        switch (getEvent){

                            case "Regist Device" :

                                switch (getStatus){

                                    case "ok" :
                                        finish();
                                        break;

                                    //실패할때 경우 받아서 주기
                                }

                                break;

                        }



//                        Intent intent = new Intent(getApplicationContext(), Project_Main.class);
//                        startActivityForResult(intent , RequestCode.ADD_DEVICE);
                        //여기서 finish로 끝내도 된당

                    }

                }
            }

        }
    }





    class ThreadHandler1 extends  Handler{
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "핸들러 연결");
            super.handleMessage(msg);

            if(msg != null){
                Bundle b = msg.getData();
                String result = b.getString("data");
                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{

                    Gsonresult gsonresult = new Gsonresult();
                    DeviceVO deviceVO = gsonresult.getDeviceDetail(result);

                    Log.d(TAG,deviceVO.toString());


                    if(deviceVO == null) {

                        Log.d(TAG, "지슨응답에드 디바이스 값 널");
                    }else{

                        showMessgae(deviceVO.toString());

                    }
                }

            }

        }
    }


    class ThreadHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {

            Log.d(TAG, "핸들러 연결");
            super.handleMessage(msg);

            if(msg != null){

                Bundle b = msg.getData();
                String result = b.getString("data");
                if(("").equals(result)){
                    Toast.makeText(getApplicationContext(),"서버와 통신 실패", Toast.LENGTH_SHORT).show();
                }else{

                    Gsonresult gsonresult = new Gsonresult();

                    GsonResponse_add_device gsonResponse_add_device = gsonresult.getResponse_add_device(result);

                    Log.d(TAG,gsonResponse_add_device.toString());

                    if(gsonResponse_add_device == null){

                        Log.d(TAG,"지슨응답에드 디바이스 값 널");
                    }else{

                        String getEvent = gsonResponse_add_device.getEvent();

                        Log.d(TAG,"이벤트값" +getEvent);

                        switch (getEvent){

                            case "brand":

                                //한번 지우고 시작
                                grid_brand.clear();

                                for(String s : gsonResponse_add_device.getItems()){

                                    grid_brand.add(new Single_Grid_item_VO(s));
                                }
                                Log.d(TAG,"들어왔니?22");

                                doCreatGird_brand();


                                break;

                            case "device":
                                //한번 지우고 시작
                                grid_device.clear();

                                for(String s : gsonResponse_add_device.getItems()){
                                    grid_device.add(new Single_Grid_item_VO(s));
                                    Log.d(TAG,"디바이스" +grid_device.add(new Single_Grid_item_VO(s)));
                                }
                                Log.d(TAG,"들어왔니?");
                                doCreatGrid_Device();


                                break;


                        }
                    }

                }
            }
        }
    }

    private void showMessgae(String type){

        Log.d(TAG,"showMessage 들어옴");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");

        builder.setMessage("추가하시려는 장비의 정보\n" +type +"\n\n\n추가하시겠습니까?");
//        builder.setMessage(type);
//        builder.setMessage("\n추가하시겠습니까?");

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Log.d(TAG,"취소 눎");
//                String message = "취소 버튼이 눌렸습니다.";
//                textView.setText(message);
            }
        });

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                Log.d(TAG, "확인버튼 누름");
                //누르면 데이터 서버에 전송하고 메인페이지로 넘어온당

                url = Server_URL.getAdd_device();
                doServerConnect(url,"add_device",device,userID);




            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }



    class SingerAdaptrer extends BaseAdapter{

        ArrayList<Single_Grid_item_VO> item_vos = new ArrayList<Single_Grid_item_VO>();

        @Override
        public int getCount() {
            return item_vos.size();
        }

        public void addItem(Single_Grid_item_VO item){

            item_vos.add(item);
        }


        public void setitem_vos(ArrayList<Single_Grid_item_VO> item_vos){
            this.item_vos = item_vos;
        }
        @Override
        public Object getItem(int position) {
            return item_vos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Add_device_LinearLayout view = new Add_device_LinearLayout(getApplicationContext());


            Single_Grid_item_VO single_grid_item_vo = item_vos.get(position);
            view.setContent(single_grid_item_vo.getContent_text());

            //여기에서 컨텍스에 맞는 이미지를 뽑을수 있게 한당
            int imageId = R.drawable.mainlogo;

            String[] device_value ={
                    "Fan","Cooler","Humi","Sound","Shock","Plug",
                    "Light","Cam","Bell","DoorLock","Samsung",
                    "SKT","LG","GE"};

            int[] device_image_value={
                    R.drawable.cooler, R.drawable.fan,R.drawable.raindrop,R.drawable.volumecontrol,
                    R.drawable.mainlogo,R.drawable.plug,R.drawable.lighton,R.drawable.webcam,R.drawable.alarm,
                    R.drawable.unlocked, R.drawable.samsung,R.drawable.skt,R.drawable.lg,R.drawable.ge
            };
            for(int i = 0; i < device_value.length ; i++){

                if(single_grid_item_vo.getContent_text().equals(device_value[i])){
                    view.setImage(device_image_value[i]);
                }
            }

            return view;
        }
    }
}
