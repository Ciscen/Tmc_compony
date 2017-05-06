package com.auvgo.tmc.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Set;

/**
 * activity栈管理的类
 */
public class ActivityTaskManager {

    private static ActivityTaskManager activityTaskManager = null;
    private HashMap<String, Activity> activityMap = null;

    private ActivityTaskManager() {
        activityMap = new HashMap<String, Activity>();
    }

    /**
     * 返回activity管理器的唯一实例对象。
     *
     * @return ActivityTaskManager
     */
    public static synchronized ActivityTaskManager getInstance() {
        if (activityTaskManager == null) {
            activityTaskManager = new ActivityTaskManager();
        }
        return activityTaskManager;
    }

    /**
     * 将一个activity添加到管理器。
     *
     * @param activity
     */
    public Activity putActivity(String name, Activity activity) {
        return activityMap.put(name, activity);
    }

    /**
     * 得到保存在管理器中的Activity对象。
     *
     * @param name
     * @return Activity
     */
    public Activity getActivity(String name) {
        return activityMap.get(name);
    }

    /**
     * 返回管理器的Activity是否为空。
     *
     * @return 当且当管理器中的Activity对象为空时返回true，否则返回false。
     */
    public boolean isEmpty() {
        return activityMap.isEmpty();
    }

    /**
     * 返回管理器中Activity对象的个数。
     *
     * @return 管理器中Activity对象的个数。
     */
    public int size() {
        return activityMap.size();
    }

    /**
     * 返回管理器中是否包含指定的名字。
     *
     * @param name 要查找的名字。
     * @return 当且仅当包含指定的名字时返回true, 否则返回false。
     */
    public boolean containsName(String name) {
        return activityMap.containsKey(name);
    }

    /**
     * 返回管理器中是否包含指定的Activity。
     *
     * @param activity 要查找的Activity。
     * @return 当且仅当包含指定的Activity对象时返回true, 否则返回false。
     */
    public boolean containsActivity(Activity activity) {
        return activityMap.containsValue(activity);
    }

    /**
     * 关闭所有活动的Activity。
     */
    public void closeAllActivity() {
        Set<String> activityNames = activityMap.keySet();
        for (String string : activityNames) {
            finisActivity(activityMap.get(string));
        }
        activityMap.clear();
    }

    /**
     * 关闭所有活动的Activity除了指定的一个之外。
     *
     * @param nameSpecified 指定的不关闭的Activity对象的名字。
     */
    public void closeAllActivityExceptOne(String nameSpecified) {
        Set<String> activityNames = activityMap.keySet();
        Activity activitySpecified = activityMap.get(nameSpecified);
        for (String name : activityNames) {
            if (name.equals(nameSpecified)) {
                continue;
            }
            finisActivity(activityMap.get(name));
        }
        activityMap.clear();
        activityMap.put(nameSpecified, activitySpecified);
    }

    /**
     * 移除Activity对象,如果它未结束则结束它。
     *
     * @param name Activity对象的名字。
     */
    public void removeActivity(String name) {
        Activity activity = activityMap.remove(name);
        finisActivity(activity);
    }

    /**
     * 关闭一个Activity
     *
     * @param activity
     */
    private final void finisActivity(Activity activity) {
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context, Boolean isBackground) {
        try {
            closeAllActivity(); // 结束所有的activity
//			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.restartPackage(context.getPackageName());
            /* Android彻底关闭当前应用(2.2版本不再有效),与当前应用相关的应用、进程、服务等也会被关闭。
			 * 会发送 ACTION_PACKAGE_RESTARTED广播
			 * 需要权限<uses-permission android:name="android.permission.RESTART_PACKAGES" />
			 */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                int currentVersion = android.os.Build.VERSION.SDK_INT;
                if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(startMain);
                    System.exit(0);
                } else {// android2.1
                    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    am.restartPackage(context.getPackageName());
                }
            }
        }
    }

}
