package com.auvgo.tmc.utils;

/**
 * Created by lc on 2016/11/8
 */

public class Url {
//            private static final String url_off = "http://192.168.1.202:8080/";//老大
    private static final String url_off = "http://192.168.1.201:8080/";//小四
    private static final String url_online = "http://api.auvgo.com/";//正式
//    private static final String url_off = "http://192.168.1.204:8080/";//临时

    public static final int OFFLINE = 0;
    public static final int ONLINE = 1;

    public static String getUrl(int flag) {
        return flag == OFFLINE ? url_off : url_online;
    }

}

