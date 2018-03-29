package com.example.user.myhomejarvis.ListView_Util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myhomejarvis.R;

/**
 * Created by user on 2018-03-28.
 */

public class Config_myhome_LinearLayout extends LinearLayout{

    String TAG = "Config_myhome_LinearLayout";

    TextView contentView;
    TextView statusView;
    ImageView list_item_imageview;
    TextView function_Num;



    public Config_myhome_LinearLayout(Context context) {
        super(context);
        init(context);
    }


    public Config_myhome_LinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_item_list, this, true);

        contentView = findViewById(R.id.single_list_deive);
        statusView = findViewById(R.id.textView_Single_List_stats);
        list_item_imageview = findViewById(R.id.list_item_imageview);
        function_Num = findViewById(R.id.textView_Function_Nu);

    }

    public void setContentView(String content){
        Log.d(TAG,"content : " + content+"" + "contentView : " + contentView +"");
        contentView.setText(content);
    }

    public void setStatusView(String status){

        statusView.setText(status);
    }

    public void setFunction_Num(int functionNum){
        function_Num.setText(""+functionNum);
    }
    public void setList_item_imageview(int imageview){
        list_item_imageview.setImageResource(imageview);
    }
}
