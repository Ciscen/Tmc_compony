package com.auvgo.tmc.approve.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.approve.interfaces.ViewManager_hotelApprove;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.p.BaseP;
import com.auvgo.tmc.train.bean.ApprovesBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.MyDialog;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2017/3/1
 */

public class PHotelApprove extends BaseP {
    private ViewManager_hotelApprove vm;
    private String orderNo;
    private HotelOrderDetailBean mBean;

    public PHotelApprove(Context context, ViewManager_hotelApprove vm) {
        super(context);
        this.vm = vm;
    }

    public void init(Intent intent) {
        orderNo = intent.getStringExtra("orderNo");
    }

    public void getOrderDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.getHotelOrderDetail(context, AppUtils.getJson(map), HotelOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (HotelOrderDetailBean) o;
                    vm.updateViews();
                    vm.setViewState();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public HotelOrderDetailBean getmBean() {
        return mBean;
    }

    public int checkState() {
        List<ApprovesBean> approves = mBean.getApproves();
        for (int i = 0; i < approves.size(); i++) {
            ApprovesBean approvesBean = approves.get(i);
            int status = approvesBean.getStatus();
            //list排序是以审批级别排序的，如果当前的审批人id不是当前登陆人id，而且没有审批，说明还未轮到当前用户进行审批
            if (approvesBean.getEmployeeid() != MyApplication.mUserInfoBean.getId()) {//如果是上级
                if (status == 0)//如果上级为审批
                    return -2;
            } else {//如果是当前用户
                switch (status) {
                    case 0://如果当前用户未审批
                        return 1;
                    case 1://如果当前用户通过
                        return -3;
                    case 2://如果当前用户拒绝
                        return -4;
                }
            }
        }
        return -1;
    }

    public void doAgree() {
        DialogUtil.showDialog(context, "提示", "取消", "确定", "确定通过申请？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                doApprove(1);
            }
        });
    }

    public void doRefuse() {
        DialogUtil.showDialog(context, "提示", "取消", "确定", "确定拒绝申请？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                doApprove(-1);
            }
        });
    }

    private void doApprove(int i) {
        final String str = i < 0 ? "已拒绝申请" : "已同意申请";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        map.put("orderno", mBean.getOrderno());
        map.put("result", i < 0 ? "N" : "Y");
        map.put("reason", "");
        RetrofitUtil.doHotelCommitApprove(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200)
                    DialogUtil.showDialog(context, "提示", "确定", "", str, new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                            Activity a = (Activity) context;
                            a.finish();
                        }

                        @Override
                        public void onRightClick() {

                        }
                    });
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public void showPriceDetail() {
        DialogUtil.showHotelPriceDialog(context, mBean);
    }
}
