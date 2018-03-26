package com.example.user.myhomejarvis;

import android.util.Log;

/**
 * Created by Kyun on 2018-03-21.
 */

public class LogManager {
    private static final String TAG = "MainActivity";
    private static final boolean DEBUG = true;
    public static void print(String msg) {
        if(DEBUG) {
            Log.v(TAG, msg);
        }
    }
}