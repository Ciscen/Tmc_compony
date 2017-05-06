package com.auvgo.tmc.approve.interfaces;

import com.auvgo.tmc.plane.interfaces.IPassenger;
import com.auvgo.tmc.train.bean.ApprovesBean;

import java.util.List;

/**
 * Created by lc on 2017/3/16
 */

public interface IPlaneApprove {
    String getTotalpriceI();

    String getOrderNoI();

    int getStatusI();

    int getApprovestatusI();

    String getContactI();

    String getLinkPhoneI();

    String getLinkEmailI();

    List<? extends IPassenger> getPassengersI();

    List<ApprovesBean> getApprovesI();

    String getShenqingNoI();

    String getCostCenterI();

    String getPronameI();

    String getChailvitemI();

    String getBookPolicyI();

    String getWBReasonI();
    IRouteBean getRoute();
}
