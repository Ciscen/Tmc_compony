package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.ReturnOrderDetailBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lc on 2016/12/8
 */

public class TrainReturnDetailActivity extends BaseAlterReturnTrainOrderDetailActivity {

    private ReturnOrderDetailBean mBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewVisibility();
        getOrderDetail();
    }

    @Override
    protected void initView() {
        super.initView();
        price_name.setText("退票费：");
    }

    private void getOrderDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("tporderno", orderNoStr);
        RetrofitUtil.getTrainReturnOrderDetail(context, AppUtils.getJson(map), ReturnOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                mBean = (ReturnOrderDetailBean) o;
                updateViews();
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {

                return false;
            }
        });
    }

    private void updateViews() {
        ReturnOrderDetailBean.OrderRouteBean orderRoute = mBean.getOrderRoute();
        cv.setTraincode(mBean.getTuipiaoUser().getTrainCode());
        cv.setStart_station(orderRoute.getFromStation());
        cv.setStart_time(orderRoute.getFromTime());
        cv.setEnd_station(orderRoute.getArriveStation());
        cv.setEnd_time(orderRoute.getArriveTime());
        cv.setSeatType(mBean.getTuipiaoUser().getSeattype());
        cv.setRun_time(orderRoute.getRunTime() == null ? "null" : orderRoute.getRunTime().replace(":", "时") + "分");
        cv.setEnd_date(TimeUtils.getDateByCostDays(orderRoute.getTravelTime(), Integer.parseInt(orderRoute.getArriveDays()), "MM-dd"));
        cv.setStart_date(TimeUtils.getDateByCostDays(orderRoute.getTravelTime(), 0, "MM-dd"));

        psg_name.setText(mBean.getTuipiaoUser().getUserName() + "   " + mBean.getTuipiaoUser().getUserId());
        psg_ticketNo.setText("票号：" + "  " + mBean.getTuipiaoUser().getPiaohao());
        orderNo.setText(String.format("单号:%s", mBean.getTorderno()));
        state.setText(MUtils.getReturnStateByCode(mBean.getStatus()));
        ticketNo.setText(String.format("出票号:%s", mBean.getTuipiaoUser().getOutTicketNo()));
        price.setText(String.format("￥%s", mBean.getTuipiaoUser().getKhTuikuan()));
    }

    private void setViewVisibility() {
        item_oldRouteInfo.setVisibility(View.GONE);
        item_psgs.setVisibility(View.GONE);
        pay_bt.setVisibility(View.GONE);
        cancle_bt.setVisibility(View.GONE);
    }

    @Override
    protected void gotoOrigin() {
        Intent intent = new Intent(this, TrainOrderDetailActivity.class);
        intent.putExtra("orderNo", mBean.getOdOrderno());
        startActivity(intent);
    }
}
