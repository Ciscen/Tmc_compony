package com.auvgo.tmc.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.auvgo.tmc.MyApplication;

/**
 * toast工具类
 * @author spiderman
 */
public class ToastUtils {

    private static Toast toast = null;

    public static void showTextToast(Context context, int msg) {
        showTextToast(context.getResources().getString(msg), MyApplication.getApplication(), Toast.LENGTH_SHORT);
    }

    public static void showTextToast(String msg) {
        showTextToast(msg, MyApplication.getApplication(), Toast.LENGTH_SHORT);
    }

    public static void showTextToastLongTime(String msg) {
        showTextToast(msg, MyApplication.getApplication(), Toast.LENGTH_LONG);
    }

    private static void showTextToast(String msg, Context context, int length) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, length);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    private static Toast toast1 = null;

    public static void showTextToastGCST(String msg) {
        if (toast1 == null) {
            toast1 = Toast.makeText(MyApplication.getApplication(), msg, Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast1.setText(msg);
        }
        toast1.show();
    }

}
