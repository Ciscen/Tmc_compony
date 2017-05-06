package com.auvgo.tmc.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.auvgo.tmc.MainActivity;
import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.bean.JpushExtraBean;
import com.auvgo.tmc.bean.JpushJumpInfo;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.LogFactory;
import com.google.gson.Gson;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lc on 2016/11/29
 */

public class JpushReceiver extends BroadcastReceiver {
    private NotificationManager nm;
    private String TAG = "JpushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm)
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogFactory.d(TAG, "JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogFactory.d(TAG, "接受到推送下来的自定义消息");
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogFactory.d(TAG, "接受到推送下来的通知");
            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogFactory.d(TAG, "用户点击打开了通知");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//{"type": "train","orderno": "AUVGO121908251036022","optype": "0"}
            if (!TextUtils.isEmpty(extras)) {
                JpushExtraBean b = new Gson().fromJson(extras, JpushExtraBean.class);
                if (b != null)
                    jumpToTerminal(context, b);

            }
        } else
            LogFactory.d(TAG, "Unhandled intent - " + intent.getAction());
    }

    /*
    flag暂时未用
     */
    private void jumpToTerminal(Context context, JpushExtraBean b) {
        int flag = -1;
        if (b.getOptype() != null) {
            switch (b.getOptype()) {
                case "0":
                    flag = Constant.PushDirectionType.PUSH_DIRECTION_APPROVE;
                    break;
                case "1":
                    flag = Constant.PushDirectionType.PUSH_DIRECTION_DETAIL;
                    break;
                case "2":
                    flag = Constant.PushDirectionType.PUSH_DIRECTION_DETAIL;
                    break;
            }
        } else {
            flag = Constant.PushDirectionType.PUSH_DIRECTION_HOME;
        }

        Intent intent = new Intent();
        if (MyApplication.getApplication().isLogined()) {
            intent.setClass(context, HomeActivity.class);
        } else {
            intent.setClass(context, MainActivity.class);
        }
        JpushJumpInfo info = new JpushJumpInfo(flag);
        info.extra2 = b.getType();
        info.extra1 = b.getOrderno();
        MyApplication.getApplication().setjInfo(info);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogFactory.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        LogFactory.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogFactory.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            LogFactory.e("Unexpected: extras is not a valid json");
            return;
        }
    }
}
