package com.example.user.myhomejarvis.ListView_Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myhomejarvis.R;

/**
 * Created by Kyun on 2018-03-27.
 */

public class Find_family_LinearLayout extends LinearLayout {
    TextView textView;
    ImageView imageView;

    public Find_family_LinearLayout(Context context) {
        super(context);
        init(context);
    }

    public  Find_family_LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.find_family_item, this, true);

        textView = (TextView) findViewById(R.id.find_family_name_view);
        imageView = (ImageView)findViewById(R.id.find_family_image);
    }
    public void setFamilyName(String fname) {
        textView.setText(fname);
    }
    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}
