package com.example.user.myhomejarvis.ListView_Util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myhomejarvis.R;

/**
 * Created by user on 2018-03-27.
 */

public class Add_device_LinearLayout  extends LinearLayout{

    TextView textView;
    ImageView imageView;

    public Add_device_LinearLayout(Context context) {
        super(context);

        init(context);
    }

    public Add_device_LinearLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);

    }

    public  void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_grid_item,this,true);

        textView = findViewById(R.id.grid_textview);
        imageView= findViewById(R.id.grid_imageview);


    }



    public void setContent(String content) {
        textView.setText(content);
    }


    public void setImage(int imageId) {

        imageView.setImageResource(imageId);
    }
}
