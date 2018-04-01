package com.example.user.myhomejarvis.ListView_Util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myhomejarvis.R;

import org.w3c.dom.Text;

/**
 * Created by user on 2018-04-01.
 */

public class Family_List_LinearLayout extends LinearLayout {

    String TAG = "Family_List_LinearLayout";

    TextView userIDView;
    TextView userNameView;
    ImageView user_imageView;


    public Family_List_LinearLayout(Context context) {
        super(context);
        init(context);
    }

    public Family_List_LinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_family_list, this, true);

        userNameView = findViewById(R.id.single_family_name);
        userIDView = findViewById(R.id.textView_Single_User_ID);
        user_imageView = findViewById(R.id.list_family_imageview);

    }

    public void setUserID(String userID) {
        Log.d(TAG,"content : " + userID+"" + "contentView : " + userIDView +"");
        userIDView.setText(userID);
    }

    public void setUserName(String userName) {
        Log.d(TAG,"content : " + userName+"" + "contentView : " + userNameView +"");
        userNameView.setText(userName);
    }

    public void setUser_image(int user_image) {
        user_imageView.setImageResource(user_image);
    }
}
