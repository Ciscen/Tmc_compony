package com.auvgo.tmc.approve.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.PlaneOrderDetailPsgAdapter;
import com.auvgo.tmc.approve.interfaces.IPlaneApprove;
import com.auvgo.tmc.p.BaseP;
import com.auvgo.tmc.plane.bean.PlaneAlterDetailBean;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.interfaces.ViewManager_PlaneOrderDetail;
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
 * Created by lc on 2017/1/11
 */

public class PPlaneApprove extends BaseP {
    private ViewManager_PlaneOrderDetail vm;
    private String orderNo;
    private IPlaneApprove mBean;
    private PlaneOrderDetailPsgAdapter adapter_psg;
    private ApproveStateAdapter adapter_approve_state;
    private String type = "air";//air原始订单，airgq改签订单

    public PPlaneApprove(Context context, ViewManager_PlaneOrderDetail vm) {
        super(context);
        this.vm = vm;
    }

    public void init(Intent intent) {
        orderNo = intent.getStringExtra("orderNo");
        type = intent.getStringExtra("type");
        type = type == null ? "air" : type;
    }


    public void getOrderDetail() {
        if (type.equals("air")) {
            requestOrgOrderDetail();
        } else {
            requestGqOrderDetail();
        }
    }

    private void requestGqOrderDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("gqorderno", orderNo);
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        RetrofitUtil.getPlaneAlterDetail(context, AppUtils.getJson(map), PlaneAlterDetailBean.class,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            mBean = (PlaneAlterDetailBean) o;
                            adapter_psg = new PlaneOrderDetailPsgAdapter(context, mBean.getPassengersI(), true);
                            adapter_approve_state = new ApproveStateAdapter(context, mBean.getApprovesI());
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

    private void requestOrgOrderDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.getPlaneOrderDetail(context, AppUtils.getJson(map), PlaneOrderDetailBean.class,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            mBean = (PlaneOrderDetailBean) o;
                            adapter_psg = new PlaneOrderDetailPsgAdapter(context, mBean.getPassengersI(), true);
                            adapter_approve_state = new ApproveStateAdapter(context, mBean.getApprovesI());
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

    public int checkState() {
        List<ApprovesBean> approves = mBean.getApprovesI();
        for (int i = 0; i < approves.size(); i++) {
            ApprovesBean approvesBean = approves.get(i);
            int status = approvesBean.getStatus();
            //list排序是以审批级别排序的，如果当前的审批人id不是当前登陆人id，而且没有审批，说明还未轮到当前用户进行审批
            if (approvesBean.getEmployeeid() != MyApplication.mUserInfoBean.getId()) {//如果是上级
                if (status == 0)//如果上级未审批
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

    public void doApprove(int i) {
        final String str = i < 0 ? "已拒绝申请" : "已同意申请";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        map.put("orderno", mBean.getOrderNoI());
        map.put("result", i < 0 ? "N" : "Y");
        map.put("reason", "");
        RetrofitUtil.doPlaneApproveCommit(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
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


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public IPlaneApprove getmBean() {
        return mBean;
    }

    public void setmBean(PlaneOrderDetailBean mBean) {
        this.mBean = mBean;
    }


    public PlaneOrderDetailPsgAdapter getAdapter_psg() {
        return adapter_psg;
    }

    public void setAdapter_psg(PlaneOrderDetailPsgAdapter adapter_psg) {
        this.adapter_psg = adapter_psg;
    }

    public ApproveStateAdapter getAdapter_approve_state() {
        return adapter_approve_state;
    }

    public void setAdapter_approve_state(ApproveStateAdapter adapter_approve_state) {
        this.adapter_approve_state = adapter_approve_state;
    }

    public void showPriceDetail() {
        if (type.equals("air"))
            DialogUtil.showPlanePriceDetailPop(context, (PlaneOrderDetailBean) mBean);
    }

    public boolean isGQ() {
        return type.equals("airgq");
    }

//    public String getTotalPrice() {
//
//        //乘客人数
//        int psgNum = mBean.getPassengers().size();
//        //票价
//        double piaojia = mBean.getTotalticketprice() / psgNum;
//        //服务费
//        double fuwufei = mBean.getRoutePass().get(0).getFuwufee();
//        //机建费、税费
//        PlaneOrderDetailBean.RoutesBean routesBean = mBean.getRoutes().get(0);
//        double jijianfei = routesBean.getAirporttax() + routesBean.getFueltax();
//        //保险费
//        double baoxian = mBean.getRoutePass().get(0).getBxPayMoney();
//        return AppUtils.keepNSecimal((piaojia + fuwufei + jijianfei) * psgNum + "", 1);
//    }
}
