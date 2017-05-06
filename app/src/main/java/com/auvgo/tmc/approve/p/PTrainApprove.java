package com.auvgo.tmc.approve.p;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.approve.interfaces.ViewManager_approve;
import com.auvgo.tmc.p.BaseP;
import com.auvgo.tmc.train.bean.ApprovesBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.MyDialog;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2016/12/1
 */

public class PTrainApprove extends BaseP {

    private final ViewManager_approve vm;
    private TrainOrderDetailBean mBean;
    private String mOrderNo;
    private String mOrderType;//表示机票、火车票、酒店的区分

    public PTrainApprove(Context context, ViewManager_approve vm) {
        super(context);
        this.vm = vm;
    }

    public String getmOrderNo() {
        return mOrderNo;
    }

    public TrainOrderDetailBean getmBean() {
        return mBean;
    }

    public void setmBean(TrainOrderDetailBean mBean) {
        this.mBean = mBean;
    }

    public void setmOrderNo(String mOrderNo) {
        this.mOrderNo = mOrderNo;
    }

    public String getmOrderType() {
        return mOrderType;
    }

    public void setmOrderType(String mOrderType) {
        this.mOrderType = mOrderType;
    }

    public void getData() {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("orderno", mOrderNo);
        RetrofitUtil.getOrder(context, AppUtils.getJson(map), TrainOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                mBean = (TrainOrderDetailBean) o;
                vm.updateViews(mBean);
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public void doAgree() {
        DialogUtil.showDialog(context, "提示", "取消", "确定", "确定同意申请？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                commit("Y", "");
            }
        });
    }

    private void commit(final String y, String s) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        map.put("orderno", mOrderNo);
        map.put("result", y);
        map.put("reason", s);

        RetrofitUtil.doTrainCommitApprove(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200)
                    DialogUtil.showDialog(context, "提示", "确定", "", "已" + (y.equals("Y") ? "同意" : "拒绝") + "申请", new MyDialog.OnButtonClickListener() {
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

    public void doRefuse() {
        DialogUtil.showDialog(context, "提示", "取消", "确定", "确定拒绝申请？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                commit("N", "");
            }
        });
    }

    public int checkState(TrainOrderDetailBean mBean) {
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

    public void showPriceDialog(int h) {
        int size = mBean.getUsers().size();
        /*
        服务费
         */
        int serFee = mBean.getUsers().get(0).getFuwufei();//服务费
        String serFee1 = serFee + "x" + size;
        String serFeeTotal = AppUtils.keepNSecimal(String.valueOf(serFee * size), 1);
        /*
        票价
         */
        double amount = mBean.getUsers().get(0).getAmount();
        String ticketFee = amount + "x" + size;//票价
        String totalFee = AppUtils.keepNSecimal(String.valueOf(amount * size), 1);
        /*
        配送费
         */
        String peisongfei = mBean.getUsers().get(0).getTrainpeison() == 0 ? "" : mBean.getUsers().get(0).getTrainpeison() + "x" + size;
        String peisongfeiTotal = AppUtils.keepNSecimal(String.valueOf(mBean.getUsers().get(0).getTrainpeison() * size), 1);

        DialogUtil.showPriceDialog(context, h, serFee1, serFeeTotal,
                peisongfei, peisongfeiTotal, ticketFee, totalFee, null);
//        DialogUtil.dimWindows(context);
    }

}
