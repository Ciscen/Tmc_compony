package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.PlaneOrderDetailPsgAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.module.PayModule;
import com.auvgo.tmc.plane.activity.PlaneOrderDetailActivity;
import com.auvgo.tmc.plane.activity.PlaneReturnApplyActivity;
import com.auvgo.tmc.plane.activity.PlaneSendActivity;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.interfaces.ViewManager_PlaneOrderDetail;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2017/1/4
 */

public class PPlaneOrderDetail extends BaseP {
    private ViewManager_PlaneOrderDetail vm;
    private PlaneOrderDetailBean mBean;
    private PlaneOrderDetailPsgAdapter adapter;
    private ApproveStateAdapter adapter_approve_state;
    private String orderNo = "";

    public PPlaneOrderDetail(Context context, ViewManager_PlaneOrderDetail vm) {
        super(context);
        this.vm = vm;
    }

    public void init(Intent intent) {
        mBean = intent.getParcelableExtra("bean");
        orderNo = intent.getStringExtra("orderNo");
    }

    public void getOrderDetail() {
        if (mBean != null) {
            freshView();
            return;
        }
        final Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.getPlaneOrderDetail(context, AppUtils.getJson(map), PlaneOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (PlaneOrderDetailBean) o;
                    if (mBean.getApprovestatus() == Constant.ApproveStatus.APPROVE_STATUS_DAISONGSHEN) {
                        jumpToSend(mBean);
                    } else {
                        freshView();
                    }
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void jumpToSend(PlaneOrderDetailBean bean) {
        Intent intent = new Intent(context, PlaneSendActivity.class);
        intent.putExtra("bean", bean);
        ((Activity) context).finish();
        context.startActivity(intent);
    }

    private void freshView() {
        adapter = new PlaneOrderDetailPsgAdapter(context, mBean);
        adapter_approve_state = new ApproveStateAdapter(context, mBean.getApproves());
        vm.setViewState();
        vm.updateViews();
    }

    public PlaneOrderDetailBean getmBean() {
        return mBean;
    }

    public void setmBean(PlaneOrderDetailBean mBean) {
        this.mBean = mBean;
    }

    public ApproveStateAdapter getAdapter_approve_state() {
        return adapter_approve_state;
    }

    public void setAdapter_approve_state(ApproveStateAdapter adapter_approve_state) {
        this.adapter_approve_state = adapter_approve_state;
    }

    public PlaneOrderDetailPsgAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PlaneOrderDetailPsgAdapter adapter) {
        this.adapter = adapter;
    }

    public void confirmOutTicket() {
        PayModule.getInstance().pay(context, orderNo == null ? mBean.getOrderNoI() : orderNo,
                PayModule.ORDER_TYPE_AIR, mBean.getPayType().equals("1"),
                new PayModule.OnPayResultListener() {
                    @Override
                    public void onPaySuccess() {
                        BaseActivity baseActivity = (BaseActivity) context;
                        baseActivity.freshOrderDetail(orderNo == null ? mBean.getOrderNoI() : orderNo);
                    }

                    @Override
                    public void onPayFailed(int code, String msg) {
                    }
                });
    }

    public void tuipiao() {
        // TODO: 2017/2/28 退票
        if (checkIfAlterReturn(1)) {
            Intent intent = new Intent(context, PlaneReturnApplyActivity.class);
            intent.putExtra("bean", mBean);
            context.startActivity(intent);
        } else {
            DialogUtil.showDialog(context, "提示", "知道了", "", context.getString(R.string.noGqTpNotice), null);
        }
    }

    public void gaiqian() {
        // TODO: 2017/1/10 改签
        if (checkIfAlterReturn(0)) {
            Intent intent = new Intent(context, PlaneReturnApplyActivity.class);
            intent.putExtra("isAlter", true);
            intent.putExtra("bean", mBean);
            context.startActivity(intent);
        } else {
            DialogUtil.showDialog(context, "提示", "知道了", "", context.getString(R.string.noGqTpNotice), null);
        }
    }


    //检测是否可以改签或者退票，0改签  1退票..未改签、退票返回true
    private boolean checkIfAlterReturn(int i) {
        List<PlaneOrderDetailBean.PassengersBean> users = mBean.getPassengers();
        for (int i1 = 0; i1 < users.size(); i1++) {
            int gaiqianstatus = MUtils.getGaiqianstatusByPsgId(mBean, users.get(i1).getId());
            int tuipiaostatus = MUtils.getTuipiaostatusByPsgId(mBean, users.get(i1).getId());
            if (i == 0) {
                if (tuipiaostatus == 0 && gaiqianstatus == 0) {//0未退票  未改簽，1已退票  改簽中，2改签成功，3改签失败。
                    return true;
                }
            }
            if (i == 1) {
                if (tuipiaostatus == 0 && gaiqianstatus == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void cancleOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", mBean.getOrderno());
        RetrofitUtil.canclePlaneOrder(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    DialogUtil.showDialog(context, "提示", "确定", "", "取消成功", new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                            Activity a = (Activity) context;
                            a.finish();
                        }

                        @Override
                        public void onRightClick() {

                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public void showPriceDetailPop() {
        DialogUtil.showPlanePriceDetailPop(context, mBean);
    }
}
