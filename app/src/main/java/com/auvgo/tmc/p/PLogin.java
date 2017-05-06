package com.auvgo.tmc.p;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.common.LoginActivity;
import com.auvgo.tmc.common.PeisongListActivity;
import com.auvgo.tmc.common.bean.PayTypeResultBean;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.interfaces.ViewManager_login;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.SPUtils;
import com.auvgo.tmc.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by lc on 2016/11/8
 */

public class PLogin extends BaseP {
    private java.lang.String tag = "login------";
    private ViewManager_login vm;
    private boolean isClicked;

    public PLogin(Context context, ViewManager_login vm) {
        super(context);
        this.vm = vm;
    }

    public void doLogin() {
        checkPermission();
    }

    public void doNext() {
        if (isClicked) {
            return;
        }
        String userName = vm.getUserName();
        String psw = vm.getPsw();
        String cardNum = vm.getCardNum();
        String notice = checkEmpty(userName, psw, cardNum);
        if (!TextUtils.isEmpty(notice)) {
//            DialogUtil.showDialog(context, "提示", "确定", "", notice, null);
            ToastUtils.showTextToast(notice);
            return;
        }
        boolean isSavePsw = vm.isSavePsw();
        boolean isSaveUserName = vm.isSaveUserName();
        boolean isAuto = vm.isAutoLogin();

        if (isSavePsw) {
            SPUtils.put(context, Constant.KEY_PASSWORD, psw);
        } else {
            SPUtils.put(context, Constant.KEY_PASSWORD, "");
        }
        if (isSaveUserName) {
            SPUtils.put(context, Constant.KEY_CARDNUM, cardNum);
            SPUtils.put(context, Constant.KEY_USERNAME, userName);
        } else {
            SPUtils.put(context, Constant.KEY_CARDNUM, "");
            SPUtils.put(context, Constant.KEY_USERNAME, "");
        }
        SPUtils.put(context, Constant.KEY_AUTOLOGIN, isAuto);
        MyApplication.cardNum = cardNum;
        login(userName, psw, cardNum);
    }

    private void checkPermission() {
        LoginActivity la = (LoginActivity) context;
        if (la.hasPermission(Manifest.permission.READ_PHONE_STATE)) {
            doNext();
        } else {
            la.requestPermission(Constant.PERMISSION_CODE_IMEI, Manifest.permission.READ_PHONE_STATE);
        }
    }

    public void login(String userName, String psw, String cardNum) {
        AppUtils.doLogin(context, cardNum, userName, psw, true, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    MyApplication.mUserInfoBean = (UserBean) o;
                    ToastUtils.showTextToast("登录成功");
                    doSetAlias();
                    LogFactory.d("-----IMEI-----------" + AppUtils.getMD5(DeviceUtils.getIMEI(context)));
                    getPayment();
                } else {
                    ToastUtils.showTextToast(msg);
                    isClicked = false;
                    SPUtils.put(context, Constant.KEY_PASSWORD, "");
                    SPUtils.put(context, Constant.KEY_AUTOLOGIN, false);
                }

                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {
                isClicked = false;
                return false;
            }
        });
    }

    /*请求支付方式*/
    private void getPayment() {
        Map<String, String> map = new HashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        RetrofitUtil.getPayType(context, AppUtils.getJson(map), PayTypeResultBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    MyApplication.getApplication().setmPayType((PayTypeResultBean) o);
                    toHome();
                } else {
                    ToastUtils.showTextToast("获取支付配置失败");
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                ToastUtils.showTextToast("获取支付配置失败");
                return false;
            }
        });
    }

    /*跳往主页*/
    private void toHome() {
        jumpActivity(Constant.ACTIVITY_HOME_FLAG);
        Activity a = (Activity) context;
        a.finish();
    }


    public void doSetAlias() {
        JPushInterface.setAliasAndTags(context, AppUtils.getMD5(DeviceUtils.getIMEI(context)), null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        LogFactory.d("setAlias------->", "success");
                        break;
                    case 6002:
                        LogFactory.d("setAlias------->", "failed ");
                        break;
                    default:
                        LogFactory.d("setAlias----errorCode--->", i + "");
                        break;
                }
            }
        });
    }

    private String checkEmpty(String userName, String psw, String cardNum) {
        String notice = "";
        if (TextUtils.isEmpty(cardNum)) {
            notice = "请输入卡号";
            return notice;
        }
        if (TextUtils.isEmpty(userName)) {
            notice = "请输入用户名";
            return notice;
        }
        if (TextUtils.isEmpty(psw)) {
            notice = "请输入密码";
            return notice;
        }
        return notice;
    }

    private void jumpActivity(int flag) {
        Intent intent = new Intent();
        if (flag == Constant.ACTIVITY_HOME_FLAG) {
            intent.setClass(context, HomeActivity.class);
        }
        context.startActivity(intent);
    }


}
