package com.auvgo.tmc.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.auvgo.tmc.MainActivity;
import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.plane.activity.PlaneSendActivity;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.views.MyDialog;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * Created by chenjx on 2016/7/12
 */
public class AppUtils {
    private static Gson gson;

    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuilder strBuf = new StringBuilder();
            for (byte anEncryption : encryption) {
                String str = Integer.toHexString(0xff & anEncryption);
                if (str.length() == 1) {
                    strBuf.append("0").append(str);
                } else {
                    strBuf.append(str);
                }
            }
            return strBuf.toString();


//            return new BigInteger(1, encryption).toString(16);

        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }


    ///////////////////////////////////// base64加密开始 /////////////////////////////////////////////////////////////////////////////////////////////////////

    private static char[] base64EncodeChars = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};

    private static byte[] base64DecodeChars = new byte[]{
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
            -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};


    public static String encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    ///////////////////////////////////// base64加密结束 /////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////// DES加密开始 /////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * DES加密
     *
     * @param datasource byte[]
     * @param password   String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes("GBK"));
//创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
//Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
//用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
//现在，获取数据并加密
//正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "dfadsfa".getBytes();
    }

    public byte[] desCrypto(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    ///////////////////////////////////// DES加密结束 ///////////////////////////////////////////////
    public static Context getContext() {
        return MyApplication.getApplication();
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return MyApplication.getApplication().getmMainThreadHandler();
    }


    public static void doLogin(Context context, String cardNum, String userName, String psw, boolean show, RetrofitUtil.OnResponse onResponse) {
        //将三者拼接后加密作为password
        String password = AppUtils.getMD5(userName + cardNum.toUpperCase() + psw).toUpperCase();
        //第一个参数data生成
        final String data = getFormatedString(context, userName, cardNum.toUpperCase(), password);
        LogFactory.d("loginDataStr------>", data);
        if (!SPUtils.contains(context, cardNum) || SPUtils.get(context, "cardNum", "").equals(cardNum)) {
            SPUtils.put(context, "cardNum", cardNum);
        }
        RetrofitUtil.doLogin(context, data, UserBean.class, show, onResponse);
    }

    private static String getFormatedString(Context context, String userName, String cardNum, String password) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("username", userName);
        map.put("password", password);
        map.put("kahao", cardNum);
        map.put("imeicode", AppUtils.getMD5(DeviceUtils.getIMEI(context)));
        return AppUtils.getJson(map);
    }


    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    public static String getJson(Map<String, String> map) {
        JSONObject jsonO = new JSONObject();
        if (MyApplication.mUserInfoBean != null)
            map.put("loginuserid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                jsonO.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonO.toString();

    }

    public static String getJson(Object obj) {

        Gson gson = new Gson();

        return gson.toJson(obj);
    }

    /**
     * 将分钟转换成时分
     *
     * @param minutes
     * @return
     */
    public static String minutes2hm(int minutes) {
        String result = "";
        String hStr = "";
        String mStr = "";
        if (minutes / 60 > 0) {
            hStr = minutes / 60 + "";
            if (0 == minutes % 60) {
                result = hStr + "时";
            } else {
                mStr = minutes % 60 + "";
                result = hStr + "时" + mStr + "分";
            }
        } else {
            result = minutes + "分钟";
        }
        return result;
    }

    /**
     * 四舍五入保留两位小数点
     *
     * @param numberStr 需要处理的数
     *                  保留小数点的位数
     * @return
     */
    public static String keepTwoSecimal2(String numberStr) {
        double number = Double.parseDouble(numberStr);
        BigDecimal b = new BigDecimal(number);

        String ss = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";

        String[] tt = ss.split("\\.");
        if (tt[1].length() == 1) {
            ss = ss + "0";
        }

        return ss;
    }

    /**
     * 保留n位有效数字
     *
     * @param numberStr 数字的字符串形式
     * @param n         保留的位数
     * @return
     */
    public static String keepNSecimal(String numberStr, int n) {
        double number = Double.parseDouble(numberStr);
        BigDecimal b = new BigDecimal(number);
        String ss = b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
        int pointIndex = ss.lastIndexOf(".");
        int fractionCount = ss.length() - pointIndex - 1;
        for (int i = 0; i < n - fractionCount; i++) {
            ss += 0;
        }
        return ss;
    }

    public static String keepZeroSecimal(String numberStr) {
        double number = Double.parseDouble(numberStr);
        BigDecimal b = new BigDecimal(number);

        String ss = b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue() + "";

//        String[] tt = ss.split("\\.");
//        if(tt[1].length() == 1){
//            ss = ss + "0";
//        }

        return ss;
    }

    public static String getErrorContent(JSONObject json) {
        JSONObject errorJson = null;
        String errorContent = "";
        try {
            errorJson = json.getJSONObject("M_ErrorInfo_1_0");
            errorContent = errorJson.getString("Content");
            errorContent = errorContent.substring(8, errorContent.length() - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorContent;

    }

    /**
     * 重新打开登录界面
     */
    public static void restartLogin() {
        Intent outIntent = new Intent(MyApplication.getApplication(), MainActivity.class);
        outIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        outIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getApplication().startActivity(outIntent);
        android.os.Process.killProcess(android.os.Process
                .myPid());
    }

    public static void callPhone(Context context, String number) {
        Intent intent2 = new Intent(Intent.ACTION_DIAL);
        if (TextUtils.isEmpty(number)) return;
        Uri data2 = Uri.parse("tel:" + number);
        intent2.setData(data2);
        context.startActivity(intent2);
    }

    public static String getNoNullStr(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    public static void closeSoftInput(Activity activity) {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public interface OnEmptyViewClickListener {
        void onClickEmptyView();
    }

    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 默认提示erignErrorMsg的信息，如果为空，则提示defaultMsg里面的消息，如果还为空，则提示"下单失败"
     *
     * @param context
     * @param info
     * @param defaultMsg 当erignErrorMsg为空的时候，提示的信息
     */
    public static void showErrorOrderDialog(final Context context, String info, String defaultMsg) {

        if (TextUtils.isEmpty(info)) {
            if (TextUtils.isEmpty(defaultMsg)) {
                info = "下单失败";
            } else {
                info = defaultMsg;
            }
        }
        MyDialog dialog = new MyDialog(context, "提示", "返回", "", info,
                new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onRightClick() {
                    }

                    @Override
                    public void onLeftClick() {
                        ((Activity) context).finish();
                    }
                });
        dialog.show();
    }

    /**
     * 获取应用程序包名
     */
    public static String getAppPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打电话
     *
     * @param mContext
     * @param phoneNubmer
     */
    public static void cellPhone(Context mContext, String phoneNubmer) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNubmer));
        //检查打电话权限
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mContext.startActivity(intent);
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void initSoftInput(Activity context) {
        //默认禁止弹出软键盘
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        //设置SideBar不被弹出的软键盘挤压
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @TargetApi(19)
    public static void translucentStatus(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 判断intent是否可用
     *
     * @param intent
     * @return
     */
    public static boolean intentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 下载服务是否可用
     *
     * @return
     */
    public static boolean canDownloadState(Context context) {
        try {
            int state = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 显示下载服务设置
     */
    public static void showDownloadSetting(Context context) {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(context, intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * Android 防止控件被重复点击
     *
     * @return false 重复
     */
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        if (0 < timeD && timeD < 300) {
            return true;
        }
        return false;
    }

    public static void hindBottom(Activity activity) {
//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//A
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//B
    }
}
