package com.auvgo.tmc.train.bean.requestbean;

import com.auvgo.tmc.MyApplication;

/**
 * Created by lc on 2016/12/12
 */

public class BaseRequestBean {
    protected String loginuserid;

    public BaseRequestBean() {
        if (MyApplication.mUserInfoBean != null) {
            loginuserid = String.valueOf(MyApplication.mUserInfoBean.getId());
        }
    }

    public String getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(String loginuserid) {
        this.loginuserid = loginuserid;
    }
}
