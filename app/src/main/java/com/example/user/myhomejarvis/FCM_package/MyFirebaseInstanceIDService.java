package com.example.user.myhomejarvis.FCM_package;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by user on 2018-03-22.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MYFirebase_IDservice";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed tocken" + refreshedToken);
        sendRegistratioinToServer(refreshedToken);

    }

    private void sendRegistratioinToServer(String token){

        ServerUtilities.register(this,token);
    }
}
