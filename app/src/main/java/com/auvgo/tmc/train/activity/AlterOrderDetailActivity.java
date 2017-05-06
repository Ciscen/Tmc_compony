package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.adapter.AlterDetailPsgAdapter;
import com.auvgo.tmc.common.module.PayModule;
import com.auvgo.tmc.train.bean.AlterDetailBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;

import java.util.HashMap;
import java.util.Map;

import static com.auvgo.tmc.constants.Constant.TrainAlterStatus.*;

/**
 * Created by lc on 2016/12/8
 */

public class AlterOrderDetailActivity extends BaseAlterReturnTrainOrderDetailActivity {
    private AlterDetailBean mBean;
    private AlterDetailPsgAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setViewVisibility();
        getOrderDetail();
    }

    private void initViews() {
        titleView.setTitle("改签详情");
        price_name.setText("改签费：");
//        alter_price_ll.setVisibility(View.VISIBLE);
    }

    private void getOrderDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("gqorderno", orderNoStr);
        RetrofitUtil.getAlterOrderDetail(context, AppUtils.getJson(map), AlterDetailBean.class,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            mBean = (AlterDetailBean) o;
                            updateViews();
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
    }

    private void updateViews() {
        AlterDetailBean.GaiqianRouteBean gqRoute = mBean.getGaiqianRoute();
        cv.setRun_time(gqRoute.getRunTime().replace(":", "时") + "分");
        cv.setTraincode(gqRoute.getTrainCode());
        cv.setStart_date(gqRoute.getTravelTime().substring(5));
        cv.setStart_station(gqRoute.getFromStation());
        cv.setStart_time(gqRoute.getFromTime());
        cv.setEnd_date(TimeUtils.getDateByCostDays(gqRoute.getTravelTime(),
                Integer.parseInt(gqRoute.getArriveDays()), "MM-dd"));
        cv.setEnd_station(gqRoute.getArriveStation());
        cv.setEnd_time(gqRoute.getArriveTime());
        cv.setSeatType(mBean.getUsers().get(0).getSeatType());
        orderNo.setText(String.format("订单号：%s", mBean.getGorderno()));
        state.setText(MUtils.getAlterStateByCode(Integer.parseInt(mBean.getStatus())));
        AlterDetailBean.OldRouteBean oldRoute = mBean.getOldRoute();
        ticketNo.setText(String.format("取票单号：%s", mBean.getOldOutBillNo()));
        startStation.setText(oldRoute.getFromStation());
        arriveStation.setText(oldRoute.getArriveStation());
        startDate.setText(oldRoute.getTravelTime().substring(5));
        startTime.setText(oldRoute.getFromTime());
        trainCode.setText(oldRoute.getTrainCode());
        arriveTime.setText(oldRoute.getArriveTime());
        arriveDate.setText(oldRoute.getArriveDays() == null ? "null" : TimeUtils.getDateByCostDays(
                oldRoute.getTravelTime(), Integer.parseInt(oldRoute.getArriveDays()), "MM-dd"));
        psgs_lv.setAdapter(new AlterDetailPsgAdapter(this, mBean.getUsers()));
//        if (Integer.parseInt(mBean.getStatus()) == 2) {
//            alter_price_ll.setVisibility(View.VISIBLE);
//            double v = mBean.getOldAllticketprice() - mBean.getGaiAllticketprice()
//                    - mBean.getTuiCharges();
//            if (v < 0) {
//                price_name.setText("退回：");
//            }
//            alter_price.setText(AppUtils.keepNSecimal(Math.abs(v) + "", 1));
//        }
        pay_price.setText(AppUtils.keepNSecimal(String.valueOf(mBean.getOrderPayment().getReceivprice()), 2));
        /*底部价格，按钮的显示控制*/
        pay_ll.setVisibility(mBean.getStatus().equals("4") ? View.VISIBLE : View.GONE);
        if (mBean.getStatus().equals(TRAIN_GQ_GAIQIANCHENGGONG + "")) {/*如果改签成功，底部价格显示，按钮不显示*/
            pay_ll.setVisibility(View.VISIBLE);
            pay_bt.setVisibility(View.GONE);
            cancle_bt.setVisibility(View.GONE);
        }
    }

    private void setViewVisibility() {
        item_price.setVisibility(View.GONE);
        item_psg.setVisibility(View.GONE);
    }

    @Override
    protected void gotoOrigin() {
        if (mBean != null) {
            Intent intent = new Intent(this, TrainOrderDetailActivity.class);
            intent.putExtra("orderNo", mBean.getOorderno());
            startActivity(intent);
        }
    }

    @Override
    public void pay() {
        final PayModule instance = PayModule.getInstance();
        AlterDetailBean.OrderPaymentBean orderPayment = mBean.getOrderPayment();
        if (orderPayment.getReceivprice() == 0 || orderPayment.getPaytype().equals("1")) {//价格是0元，或者是月结的话，走月结
            showDialog("取消", "确定", "确定支付？", new MyDialog.OnButtonClickListener() {
                @Override
                public void onLeftClick() {

                }

                @Override
                public void onRightClick() {
                    instance.pay_train_gq(context, orderNoStr);
                }
            });

        } else {
            instance.gotoPaylist(this, orderNoStr, PayModule.ORDER_TYPE_TRAIN_GQ);
        }
    }

    @Override
    public void cancel() {
        Map<String, String> map = new HashMap<>();
        map.put("gqorderno", orderNoStr);
        RetrofitUtil.cancleTrainGaiqian(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    finish();
                    ToastUtils.showTextToast("取消成功");
                    jumpToOrderDetail(context, orderNoStr, AlterOrderDetailActivity.class);
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

}
