package com.example.user.myhomejarvis.FCM_package;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by user on 2018-03-26.
 */

public class ServerUtilities {
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    static final String TAG = "FCMDemo";
    static final String SERVER_URL = "http://1.234.7.150:8080";
    static boolean register(final Context context, final String instanceID) {
        Log.i(TAG, "registering device (instanceID = " + instanceID + ")");

        String serverUrl = SERVER_URL + "/register.jsp";
        //?먯떊???꾪솕踰덊샇 ?살뼱?ㅺ린
        TelephonyManager manager = (TelephonyManager)
                context.getSystemService(context.TELEPHONY_SERVICE);

        String phoneNumber = "01012341234";
        try {
            phoneNumber = manager.getLine1Number();
        }catch(SecurityException e){
            Log.v(TAG, "phone e : " + e);
        }

        Log.i(TAG, "registering tel = " + phoneNumber );

        Map<String, String> params = new HashMap<String, String>();

        params.put("instanceID", instanceID);
        params.put("tel", phoneNumber);
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);

        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                post(serverUrl, params);
                return true;
            } catch (IOException e) {

                Log.v(TAG, "Failed to register on attempt " + i, e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.v(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.v(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return false;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        return false;
    }

    static void unregister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");

        String serverUrl = SERVER_URL + "/unregister.jsp";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        try {
            post(serverUrl, params);
        } catch (IOException e) {
        }
    }

    private static void post(String endpoint, Map<String, String> params)
            throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


}
