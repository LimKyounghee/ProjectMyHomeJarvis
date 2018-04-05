package com.example.user.myhomejarvis.Permission_package;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.example.user.myhomejarvis.LogManager;

/**
 * Created by Kyun on 2018-03-27.
 */

public class PermissionUtil {

    public static final int REQUEST_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {                                  //필요한 퍼미션들
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
//            Manifest.permission.WRITE_SETTINGS
    };

    public static boolean checkPermissions(Activity activity, String permission) {     // 퍼미션이 있으면 true, 없으면 false를 리턴하는 메소드
        int permissionResult = ActivityCompat.checkSelfPermission(activity, permission);
        if (permissionResult == PackageManager.PERMISSION_GRANTED) return true;
        else return false;
    }

    public static void requestExternalPermissions(Activity activity) {                  // 퍼미션을 요청하는 메소드
        ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_STORAGE);  // requestPermissions라는 내장 메소드를 사용해서 퍼미션을 요청한다.
    }

    public  static boolean verifyPermission(int[] grantresults) {
        LogManager.print("grant results lenght : "+Integer.toString(grantresults.length));
        if (grantresults.length < 1 ) {
            return false;
        }
        for (int result : grantresults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                LogManager.print("" + result);
                return false;
            }
        }
        return true;
    }
}
