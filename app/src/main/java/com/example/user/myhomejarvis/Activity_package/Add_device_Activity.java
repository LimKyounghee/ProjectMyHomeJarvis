package com.example.user.myhomejarvis.Activity_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;
import com.example.user.myhomejarvis.Gson_package.GsonResponse_add_device;
import com.example.user.myhomejarvis.ListView_Util.Add_device_LinearLayout;
import com.example.user.myhomejarvis.R;

import java.util.ArrayList;

/**
 * Created by user on 2018-03-26.
 */

public class Add_device_Activity extends AppCompatActivity {

    public static final String TAG = "Add_device_Activity";

    TextView textView;

    GridView gridView_Category;
    GridView gridView_Brand;
    GridView gridView_Device;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);

        Bundle bundle = getIntent().getBundleExtra("Grid_info");
        Log.d(TAG,"번들로 인텐트 받음");

        GsonResponse_add_device gsonResponse_add_device = new GsonResponse_add_device();
        gsonResponse_add_device.setItems((ArrayList<String>) bundle.getSerializable("Grid_items"));
        gsonResponse_add_device.setEvent((String) bundle.getSerializable("Grid_event"));

        Log.d(TAG,gsonResponse_add_device.toString());



    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        Bundle bundle = getIntent().getBundleExtra("Grid_info");
        Log.d(TAG,"번들로 인텐트 받음");

        GsonResponse_add_device gsonResponse_add_device =(GsonResponse_add_device) bundle.getSerializable("Grid_items");
        Log.d(TAG,"============="+gsonResponse_add_device.toString());

    }

    class SingerAdaptrer extends BaseAdapter{

        ArrayList<Add_device_LinearLayout> add_device_linearLayouts = new ArrayList<Add_device_LinearLayout>();

        @Override
        public int getCount() {
            return add_device_linearLayouts.size();
        }

        @Override
        public Object getItem(int position) {
            return add_device_linearLayouts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
