package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.bean.PeisonListBean;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by lc on 2016/11/9
 */

public class BaseP {
    protected Context context;

    public Gson getGson() {
        return new Gson();
    }

    public BaseP(Context context) {
        this.context = context;
    }

    public void freshOrderDetail(Context context, String orderNo) {
        ((BaseActivity) context).finish();
        Intent intent = new Intent(context, this.getClass());
        intent.putExtra("orderNo", orderNo);
        context.startActivity(intent);
    }
    /**
     * 是否是预定人
     */
    public boolean isAllowBook() {
        if (MyApplication.mUserInfoBean.getIfallowbook() == 0) {
            return false;
        } else {
            return true;
        }

    }
}
