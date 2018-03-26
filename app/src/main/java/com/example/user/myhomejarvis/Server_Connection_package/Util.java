package com.example.user.myhomejarvis.Server_Connection_package;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by user on 2018-03-20.
 */

public class Util {

    private static final String TAG = "Util_JAVA";

    public String sendPost(String sendurl,Map<String,String> params)throws  Exception {

        URL url;
        try {
            url = new URL(sendurl);
        } catch (MalformedURLException e) {
            throw new IllegalAccessException("invalid url");
        }
        Log.v(TAG, "key count : " + params.keySet().size());
        StringBuilder bodyBudilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBudilder.append(param.getKey()).append('=').append(param.getValue());

            Log.d(TAG, param.getKey() + "=" + param.getValue());

            if (iterator.hasNext()) {
                bodyBudilder.append('&');
            }

        }

        String body = bodyBudilder.toString();
        Log.v(TAG, "Posting " + body + " to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;

        Log.v(TAG, "try 전 ");

        try {
            Log.v(TAG, "트라이문 안으로 들어옴 아직 Http COnn 저ㅗㄴ");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            OutputStream os = new DataOutputStream(conn.getOutputStream());
            out.write(bytes);
            out.flush();

            int status = conn.getResponseCode();

            //handle the response;
            status = conn.getResponseCode();
            Log.v(TAG, "Post parameters : " + body);
            Log.v(TAG, "Response Code : " + status);

            StringBuffer response = new StringBuffer();

            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //뭔가 받는 메소드 만들기

            } else {
                throw new IOException("Post failed with error code" + status);
            }
            Log.v(TAG, response.toString());

            out.close();

            return response.toString();
        } catch (Exception e) {
            Log.v(TAG, "e : " + e);
            return "dfdf";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

    }

}
