package com.example.user.myhomejarvis.Server_Connection_package;

/**
 * Created by user on 2018-03-23.
 */

public class Server_URL {

    private static final String smartplug_URL = "http://192.168.0.8:8080/JavisProject/plug.in";
    private static final String led_URL = "http://192.168.0.8:8080/JavisProject/light.in";
    private static final String join_URL = "http://192.168.0.8:8080/JavisProject/signin.in";
    private static final String login_URL = "http://192.168.0.8:8080/JavisProject/login.in";
    private static final String led_onoff_URL = "http://192.168.0.8:8080/JavisProject/light.in";
    private static final String category = "http://192.168.0.8:8080/JavisProject/category.in";
    private static final String findFamily_URL = "http://192.168.0.8:8080/JavisProject/findfamilyinfo.in";
    private static final String addFamily_URL = "http://192.168.0.8:8080/JavisProject/addfamilyinfo.in";

    public static String getSmartplug_URL() {
        return smartplug_URL;
    }



    public static String getLed_URL() {
        return led_URL;
    }

    public static String getJoin_URL() {
        return join_URL;
    }

    public static String getLogin_URL() {
        return login_URL;
    }

    public static String getLed_onoff_URL() {return led_onoff_URL;}

    public static String getCategory() {return category;}

    public static String getFindFamily_URL() {return findFamily_URL;}

    public static String getAddFamily_URL() {return addFamily_URL;}
}


