package com.auvgo.tmc.train.interfaces;

import android.app.FragmentTransaction;

import com.auvgo.tmc.adapter.ChoseApproveLevelAdapter;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.train.bean.WbReasonBean;

/**
 * Created by lc on 2016/12/29
 */

public interface ViewManager_planesend  {

    String getApplyNoStr();

    String getReasonStr();

    String getWeiReasonStr();

    CharSequence getCostCenterStr();

    CharSequence getProjectStr();


    void updateViews(ChoseApproveLevelAdapter adapter, PlaneOrderDetailBean mBean);

    void setTotalPrice(double totalPrice);

    void setWbReason(String wbReasonBean);
}
