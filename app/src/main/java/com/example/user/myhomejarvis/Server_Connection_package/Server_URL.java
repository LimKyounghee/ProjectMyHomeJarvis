package com.example.user.myhomejarvis.Server_Connection_package;

/**
 * Created by user on 2018-03-23.
 */

public class Server_URL {

    private static final String smartplug_URL = "http://192.168.0.8:8080/JavisProject/plug.in";
    private static final String join_URL = "http://192.168.0.8:8080/JavisProject/signin.in";
    private static final String login_URL = "http://192.168.0.8:8080/JavisProject/login.in";
    private static final String led_onoff_URL = "http://192.168.0.8:8080/JavisProject/light.in";
    private static final String category = "http://192.168.0.8:8080/JavisProject/category.in";
    private static final String brand = "http://192.168.0.8:8080/JavisProject/brand.in";
    private static final String device = "http://192.168.0.8:8080/JavisProject/devices.in";
    private static final String findFamily_URL = "http://192.168.0.8:8080/JavisProject/findfamilyinfo.in";
    private static final String device_detail = "http://192.168.0.8:8080/JavisProject/devicedetail.in";
    private static final String add_device = "http://192.168.0.8:8080/JavisProject/registfamilydevice.in";
    private static final String config_Home = "http://192.168.0.8:8080/JavisProject/configurationhome.in";
    private static final String addFamily_URL = "http://192.168.0.8:8080/JavisProject/addfamily.in";
    private static final String insertFamily_URL = "http://192.168.0.8:8080/JavisProject/insertfamily.in";
    private static final String returnFamilyRegist_URL = "http://192.168.0.8:8080/JavisProject/returnfamilyregistresult.in";
    private static final String fan_onoff_url = "http://192.168.0.8:8080/JavisProject/fan.in";
    private static final String family_list = "http://192.168.0.8:8080/JavisProject/familylist.in";
    private static final String DoorRequest_URL = "http://192.168.0.8:8080/JavisProject/doorrquest.in";
    private static final String idConfig_URL = "http://192.168.0.8:8080/JavisProject/idchecker.in";
    private static final String find_id_URL = "http://192.168.0.8:8080/JavisProject/findid.in";
    private static final String find_pw_URL = "http://192.168.0.8:8080/JavisProject/findpw.in";
    private static final String find_webView_URL = "http://192.168.0.8:8080/JavisProject/passmainpage.in";

    public static String getFind_webView_URL() {
        return find_webView_URL;
    }

    public static String getFind_pw_URL() {
        return find_pw_URL;
    }

    public static String getFind_id_URL() {
        return find_id_URL;
    }

    public static String getIdConfig_URL() {
        return idConfig_URL;
    }

    public static String getFamily_list() {
        return family_list;
    }

    public static String getSmartplug_URL() {
        return smartplug_URL;
    }


    public static String getFan_onoff_url() {
        return fan_onoff_url;
    }

    public static String getJoin_URL() {
        return join_URL;
    }

    public static String getLogin_URL() {
        return login_URL;
    }

    public static String getLed_onoff_URL() {return led_onoff_URL;}

    public static String getCategory() {return category;}


    public static String getBrand() {
        return brand;
    }

    public static String getDevice() {
        return device;
    }

    public static String getFindFamily_URL() {return findFamily_URL;}

    public static String getAddFamily_URL() {return addFamily_URL;}


    public static String getDevice_detail() {
        return device_detail;
    }

    public static String getAdd_device() {
        return add_device;
    }

    public static String getConfig_Home() {
        return config_Home;}

    public static String getInsertFamily_URL() {return  insertFamily_URL;}

    public static String getReturnFamilyRegist_URL() {
        return returnFamilyRegist_URL;

    }
    public static String getDoorRequest_URL() {
        return DoorRequest_URL;
    }
}


