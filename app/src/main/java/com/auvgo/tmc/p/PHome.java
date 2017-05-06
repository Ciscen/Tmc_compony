package com.auvgo.tmc.p;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.base.MyList;
import com.auvgo.tmc.approve.ApproveOrderListActivity;
import com.auvgo.tmc.common.WaitForOpenActivity;
import com.auvgo.tmc.hotel.activity.HotelQueryActivity;
import com.auvgo.tmc.plane.activity.PlaneQueryActivity;
import com.auvgo.tmc.train.bean.ComSettingBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.common.LoginActivity;
import com.auvgo.tmc.train.interfaces.ViewManager_home;
import com.auvgo.tmc.personalcenter.activity.PersonalCenterActivity;
import com.auvgo.tmc.train.activity.TrainQueryActivity;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * Created by lc on 2016/11/9
 */

public class PHome extends BaseP {

    private ViewManager_home vm;
    private View cover;

    public View getCover() {
        return cover;
    }

    public void setCover(View cover) {
        this.cover = cover;
    }

    public PHome(Context context, ViewManager_home viewManager) {
        super(context);
        this.vm = viewManager;
    }

    public void jumpActivity(int flag) {
        getComponySetting(flag);
    }

    private void doNext(int flag) {
        Intent intent = new Intent();
        Class c = null;
        // TODO: 2016/11/9 页面跳转 注意修改return
        switch (flag) {
            case Constant.ACTIVITY_PLANE_HOME_FLAG:
                c = PlaneQueryActivity.class;
                break;
            case Constant.ACTIVITY_HOTEL_HOME_FLAG:
                c = HotelQueryActivity.class;
                break;
            case Constant.ACTIVITY_TRAIN_HOME_FLAG:
                c = TrainQueryActivity.class;
                break;
            case Constant.ACTIVITY_APPROVE_FLAG:
                if (MyApplication.mUserInfoBean.getIfapprove() == 1) {
                    c = ApproveOrderListActivity.class;
                } else {
                    ToastUtils.showTextToast("没有审批权限");
                    return;
                }
                break;
            case Constant.ACTIVITY_PERSONAL_FLAG:
                c = PersonalCenterActivity.class;
                break;
            case Constant.ACTIVITY_CHECKONLINE_FLAG:
                c = WaitForOpenActivity.class;
                break;
            case Constant.ACTIVITY_FLIGHTDYNAMIC_FLAG:
                c = WaitForOpenActivity.class;
                break;
            case Constant.ACTIVITY_LOGIN_FLAG:
                c = LoginActivity.class;
                break;
        }
        intent.setClass(context, c);
        context.startActivity(intent);
    }

    public void getComponySetting(final int flag) {
        if (MyApplication.mUserInfoBean == null) return;
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("cid", MyApplication.mUserInfoBean.getCompanyid() + "");
        RetrofitUtil.getComSetting(context, AppUtils.getJson(map), ComSettingBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    ComSettingBean b = (ComSettingBean) o;
                    Type type = new TypeToken<MyList<ComSettingBean.ProductSetBean.ProconfvalueBean>>() {
                    }.getType();
                    MyList<ComSettingBean.ProductSetBean.ProconfvalueBean> arr = new Gson().fromJson(b.getProductSet().getProconfvalue(), type);
                    b.getProductSet().setProconfValue(arr);
                    MyApplication.mComSettingBean = b;
                    doNext(flag);
                } else {
                    doNext(Constant.ACTIVITY_LOGIN_FLAG);
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                doNext(Constant.ACTIVITY_LOGIN_FLAG);
                return false;
            }
        });

    }


}
