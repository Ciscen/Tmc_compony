package com.auvgo.tmc;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.support.multidex.MultiDexApplication;

import com.auvgo.tmc.bean.JpushJumpInfo;
import com.auvgo.tmc.common.bean.PayTypeResultBean;
import com.auvgo.tmc.hotel.bean.HotelPolicyBean;
import com.auvgo.tmc.hotel.service.LocationService;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.train.bean.ComSettingBean;
import com.auvgo.tmc.train.bean.TrainPolicyBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.ActivityTaskManager;
import com.auvgo.tmc.views.CustomProgressDialog;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by lc on 2016/11/8
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication mApplication;
    public static double lowestPrice = 0;
    public static String mHotelCityId;
    public static String mHotelCityName;
    private CustomProgressDialog progressDialog;
    private ActivityTaskManager activityTaskManager;
    public static boolean isDebug = true;
    private Handler mMainThreadHandler;//handler
    private PayTypeResultBean mPayType;
    private JpushJumpInfo jInfo;//在点击通知栏的时候，标记要进入的页面。
    /*
    公司配置信息
     */
    public static ComSettingBean mComSettingBean;
    public static UserBean mUserInfoBean;//当前登陆用户
    public static String cardNum = "";//卡号
    /*
    火车票政策
     */
    public static TrainPolicyBean mTrainPolicy;
    /*
    飞机票政策
     */
    public static PlanePolicyBean mPlanePolicy;
    /*
    酒店政策
     */
    public static HotelPolicyBean mHotelPolicy;

    public static boolean isWF;//是否是往返
    public static PlaneRouteBeanWF firstRoute;//第一程的实体
    public List<UserBean> selectedPsgs;//在首页选中的那些乘客
    public static String fromCityCode;//出发城市码
    public static String fromCityName;//出发城市名称
    public static String toCityCode;//到达城市码
    public static String toCityName;//到达城市名称
    public static String returnDate;//返程日期
    public static String gotoDate;//去程日期
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        initBaseData();
        initJpush();
        initBD();
//        CrashHandler.getInstance().init(this);
    }

    private void initBD() {//百度地图
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
    }

    public JpushJumpInfo getjInfo() {
        return jInfo;
    }

    public void setjInfo(JpushJumpInfo jInfo) {
        this.jInfo = jInfo;
    }

    private void initJpush() {
        JPushInterface.setDebugMode(isDebug);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    public boolean isLogined() {
        return mUserInfoBean != null;
    }

    private void initBaseData() {
        mApplication = this;
        selectedPsgs = new ArrayList<>();
        mMainThreadHandler = new Handler();
    }

    public static MyApplication getApplication() {
        return mApplication;
    }

    public ActivityTaskManager getAppManager() {
        if (activityTaskManager == null) {
            activityTaskManager = ActivityTaskManager.getInstance();
        }
        return activityTaskManager;
    }

    /**
     * 启动加载进度条
     */
    public void startProgressDialog(Context cxt, boolean canCancle, String desc) {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            if (progressDialog == null) {
                progressDialog = CustomProgressDialog.createDialog(cxt, canCancle, desc);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭加载进度条
     */
    public void stopProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PayTypeResultBean getmPayType() {
        return mPayType;
    }

    public void setmPayType(PayTypeResultBean mPayType) {
        this.mPayType = mPayType;
    }

    public Handler getmMainThreadHandler() {
        return mMainThreadHandler;
    }
}
