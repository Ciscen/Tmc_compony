package com.auvgo.tmc.p;

import android.content.Context;
import android.content.Intent;

import com.auvgo.tmc.common.module.PayModule;
import com.auvgo.tmc.hotel.activity.HotelGuaranteeActivity;
import com.auvgo.tmc.hotel.activity.HotelOrderDetailActivity;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.hotel.interfaces.ViewManager_hotelOrderDetail;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.HotelStateCons;
import com.auvgo.tmc.views.MyDialog;

import java.util.HashMap;
import java.util.Map;

import static com.auvgo.tmc.constants.Constant.ApproveStatus.*;

/**
 * Created by lc on 2017/2/28
 */

public class PHotelOrderDetail extends BaseP {
    private ViewManager_hotelOrderDetail vm;
    private String orderNo;
    private HotelOrderDetailBean mBean;
    private String status_str;

    public PHotelOrderDetail(Context context, ViewManager_hotelOrderDetail vm) {
        super(context);
        this.vm = vm;
    }

    public void init(Intent intent) {
        orderNo = intent.getStringExtra("orderNo");
    }

    public void getData() {
        getOrderDetail();
    }

    private void getOrderDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.getHotelOrderDetail(context, AppUtils.getJson(map), HotelOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (HotelOrderDetailBean) o;
                    vm.updateViews();
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

    public String getState(HotelOrderDetailBean mBean) {
         /*
    匹配状态
     */
        int status = mBean.getStatus();
        int approvestatus = mBean.getApprovestatus();
        int paystatus = mBean.getPaystatus();
        boolean isGuarantee = mBean.getIsNeedGuarantee().equals("1");
        boolean selfPay = mBean.getPaymentType().equals("SelfPay");//是否是现付
        return getStateString(status, approvestatus, paystatus, selfPay);
    }

    private String getStateString(int status, int approvestatus, int paystatus, boolean selfPay) {
        status_str = "";
        if (status == HotelStateCons.HOTEL_ORDER_STATUS_CANCEL) {
            status_str = "已取消";
            vm.setButtonState("", "", false, false);
        }
         /*
        如果无需审批、或者审批通过
         */
        else if (approvestatus == APPROVE_STATUS_WUXUSHENPI ||
                approvestatus == APPROVE_STATUS_SHENPITONGGUO) {
            /*
            审批步骤结束，进行订单状态的判断
             */
            if (selfPay) {//现付
                checkOrderState(status);
            } else {//预付
                checkOrderState4PrePay(status, paystatus);
            }
        }
        /*
        如果审批否决
         */
        else if (approvestatus == APPROVE_STATUS_SHENPIFOUJUE) {
            status_str = "审批否决";
            vm.setButtonState("", "", false, false);
        }
        /*
          如果是待审批（已经闭合）
         */
        else if (approvestatus == APPROVE_STATUS_DAISONGSHEN) {
            status_str = "待审批";
            vm.setButtonState("", "", false, true);

        } else if (approvestatus == APPROVE_STATUS_SHENPIZHONG) {
            vm.setButtonState("", "", false, true);
            status_str = "审批中";
        }
        return status_str;
    }

    /**
     * 判断订单的状态、包括了担保状态
     */
    private void checkOrderState(int status) {
      /*
        现付，担保
         */
        if (status == HotelStateCons.HOTEL_ORDER_STATUS_DANBAO) {//等待担保
            status_str = "等待担保";
            vm.setButtonState("担保", "取消", true, true);
        /*
        现付不担保
        现付担保成功
        预付支付成功以后
         */
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_DANBAO_ING) {
            status_str = "担保中";
            vm.setButtonState("", "", false, true);
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_QUEREN) {//等待确认
            status_str = "等待确认";
            vm.setButtonState("", "取消", false, true);
        /*
        现付担保失败
         */
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_DANBAO_FAIL) {//担保失败
            status_str = "担保失败";
            vm.setButtonState("", "取消", false, true);
        /*
        确认中
        */
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_QUEREN_ING) {
            status_str = "确认中";
            vm.setButtonState("", "取消", false, true);
        /*
        确认失败
        */
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_QUEREN_FAIL) {
            status_str = "确认失败";
            vm.setButtonState("", "取消", false, true);
        /*
        确认成功
         */
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_CANCEL) {
            status_str = "已取消";
            vm.setButtonState("", "", false, false);
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_QUEREN_SUCCESS) {
            status_str = "已确认";
            vm.setButtonState("", "取消", false, true);
        }
    }

    /**
     * 判断订单的状态、不包括担保状态，其实可以跟上面合并
     */
    private void checkOrderState4PrePay(int status, int paystatus) {
        /*
        确认中
        */
        if (status == HotelStateCons.HOTEL_ORDER_STATUS_QUEREN_ING) {
            status_str = "确认中";
            vm.setButtonState("", "取消", false, true);
        /*
        确认失败
        */
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_QUEREN_FAIL) {
            status_str = "确认失败";
            vm.setButtonState("", "取消", false, true);
        /*
        确认成功
         */
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_QUEREN_SUCCESS) {
            status_str = "已确认";
            vm.setButtonState("", "取消", false, true);
        /*
        订单已提交、待支付
         */
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_CANCEL) {
            status_str = "已取消";
            vm.setButtonState("", "", false, false);
        } else if (status == HotelStateCons.HOTEL_ORDER_STATUS_COMMITTED) {
            checkPayStatus(paystatus);
        }
    }

    /**
     * 支付状态的判断
     */
    private void checkPayStatus(int paystatus) {
        if (paystatus == HotelStateCons.HOTEL_PAY_STATUS) {//待支付
            status_str = "待支付";
            vm.setButtonState("支付", "取消", true, true);
        } else if (paystatus == HotelStateCons.HOTEL_PAY_STATUS_FAIL) {//支付失败
            status_str = "支付失败";
            vm.setButtonState("支付", "取消", true, true);
        } else if (paystatus == HotelStateCons.HOTEL_PAY_STATUS_ING) {//支付中
            status_str = "支付中";
            vm.setButtonState("", "取消", false, true);
        } else if (paystatus == HotelStateCons.HOTEL_PAY_STATUS_SUCCESS) {//支付成功
            status_str = "支付成功";
            vm.setButtonState("", "取消", false, true);
        }
    }

    public void pay() {
        // TODO: 2017/3/2 支付
        PayModule.getInstance().pay(context, orderNo, PayModule.ORDER_TYPE_HOTEL, mBean.getPayType().equals("1"),
                AppUtils.keepNSecimal(String.valueOf(mBean.getTotalPrice()), 2), 0L, null);
    }

    public void guarantee() {
        // TODO: 2017/3/2 担保
        Intent intent = new Intent(context, HotelGuaranteeActivity.class);
        intent.putExtra("orderNo", orderNo);
        context.startActivity(intent);
    }

    public void cancel() {
        // TODO: 2017/3/2 取消订单
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);

        RetrofitUtil.cancelHotelOrder(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    showDialog("取消成功", new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {

                        }

                        @Override
                        public void onRightClick() {
                            HotelOrderDetailActivity a = (HotelOrderDetailActivity) context;
                            a.jumpToOrderList(Constant.HOTEL);
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

    private void showDialog(String s, MyDialog.OnButtonClickListener l) {
        DialogUtil.showDialog(context, "提示", "", "确定", s, l);
    }
}
