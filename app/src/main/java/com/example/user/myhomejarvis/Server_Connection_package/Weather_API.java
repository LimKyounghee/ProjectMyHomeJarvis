package com.example.user.myhomejarvis.Server_Connection_package;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2018-04-01.
 */

public class Weather_API{


//     String appKey ="42f22f29-4475-48eb-b86d-6bd7732dd363";
     String appKey ="6e8c1f7b-f368-46ea-821d-fd1263a5cf21";
    final static public  String TAG = "Weather_API";

    public  String getWegher_API_Result(double latitude, double longitude){


        try{

            String apiURL = getWeatherAPI(1,latitude,longitude);

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept","application/json");
            con.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            con.setRequestProperty("appKey",appKey);

            int responseCode = con.getResponseCode();

            BufferedReader br;
            if(responseCode ==200){

                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            }else{

                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while((inputLine = br.readLine()) != null){

                response.append(inputLine);
            }
            br.close();
            Log.d(TAG,response.toString());
            return response.toString();

        }catch (Exception e){

            Log.d(TAG,e.toString());
            return  null;
        }


    }


    static String getWeatherAPI(int version, double latitude, double longitude) throws Exception{

        String base = "https://api2.sktelecom.com/weather/current/minutely";
        String parmether = "?version="+version+"&lat="+latitude+"&lon="+longitude;
        Log.d(TAG,base+parmether);

        return base+parmether;
    }

}
