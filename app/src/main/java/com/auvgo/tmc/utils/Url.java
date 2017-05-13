package com.auvgo.tmc.utils;

/**
 * Created by lc on 2016/11/8
 */

public class Url {
    //            private static final String url_off = "http://192.168.1.202:8080/";//老大
//    private static String url_off = "http://192.168.1.203:8080/";//小四
    private static String url_off = "http://124.254.45.234:8080/";//小四线上
    private static String url_online = "http://api.auvgo.com/";//正式
//    private static final String url_off = "http://192.168.1.204:8080/";//临时

    public static final int OFFLINE = 0;
    public static final int ONLINE = 1;

    public static String getUrl(int flag) {
        return flag == OFFLINE ? url_online : url_online;
    }

    public static void setUrl_off(String url) {
        url_off = url;
    }

}

