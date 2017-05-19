package com.auvgo.tmc.common.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.PayListActivity;
import com.auvgo.tmc.common.RequestPayInfoBean;
import com.auvgo.tmc.common.bean.WxInfoBean;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.hotel.activity.HotelOrderDetailActivity;
import com.auvgo.tmc.plane.activity.PlaneAlterDetailActivity;
import com.auvgo.tmc.train.activity.AlterOrderDetailActivity;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lc on 2017/4/10
 */

public class PayModule {
    private static PayModule payModule;
    private OnPayResultListener mListener;

    /***支付订单的类型*****/
    public static final String ORDER_TYPE_AIR = "air";
    public static final String ORDER_TYPE_AIR_GQ = "airgq";
    public static final String ORDER_TYPE_HOTEL = "hotel";
    public static final String ORDER_TYPE_TRAIN = "train";
    public static final String ORDER_TYPE_TRAIN_GQ = "traingq";

    /****支付渠道****/
    public static final String CHANNAL_ALIPAY = "alipay";
    public static final String CHANNAL_WXPAY = "weixinpay";
    public static final String CHANNAL_UNION = "unionpay";

    public static final int ALIPAY_SDK_PAY_FLAG = 1;//支付标识
    public static final int ALIPAY_SDK_AUTH_FLAG = 2;//授权标识
    public static final int UNIONPAY_SDK_AUTH_FLAG = 3;//银联支付标识
    /*unionPay模拟获取订单信息的地址*****/
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";
    public static final String mMode = "00";//测试01，正式00
    private IWXAPI api;

    private PayModule() {
    }

    /**
     * 支付，根据isSelf自动判断支付方式
     *
     * @param isMonthPay 是否是月结，如果不是，进入支付列表，如果是，直接出票
     */
    public void pay(final Context context, final String orderNo, final String fromFlag,
                    boolean isMonthPay, String price, Long lastPaytime,
                    OnPayResultListener mListener) {
        this.mListener = mListener;
        if (isMonthPay) {
            DialogUtil.showDialog(context, "提示", "取消", "确定",
                    "确定" + (fromFlag.equals(Constant.HOTEL) ? "支付？" : "出票？"),
                    new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                        }

                        @Override
                        public void onRightClick() {
                            payByFlag(context, fromFlag, orderNo);
                        }
                    });
        } else {
            gotoPaylist(context, orderNo, fromFlag, price, lastPaytime);
        }
    }

    public void gotoPaylist(Context context, String orderNo, String fromFlag, String price, Long lastPaytime) {
        Intent intent = new Intent(context, PayListActivity.class);
        intent.putExtra("orderNo", orderNo);
        intent.putExtra("fromFlag", fromFlag);
        intent.putExtra("price", price);
        intent.putExtra("lastPaytime", lastPaytime);
        context.startActivity(intent);
    }

    private void payByFlag(Context context, String fromFlag, String orderNo) {
        switch (fromFlag) {
            case PayModule.ORDER_TYPE_HOTEL:
                pay_hotel(context, orderNo);
                break;
            case PayModule.ORDER_TYPE_TRAIN:
                pay_train(context, orderNo);
                break;
            case PayModule.ORDER_TYPE_AIR:
                pay_plane(context, orderNo);
                break;
            case PayModule.ORDER_TYPE_AIR_GQ:
                pay_train_gq(context, orderNo);
                break;
            case PayModule.ORDER_TYPE_TRAIN_GQ:
                pay_plane_gq(context, orderNo);
                break;
        }
    }

    public void pay_plane_gq(final Context context, final String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("gqorderno", orderNo);
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        RetrofitUtil.confirmPlaneGaiqian(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    BaseActivity a = (BaseActivity) context;
                    a.finish();
                    a.jumpToOrderDetail(context, orderNo, PlaneAlterDetailActivity.class);
                    if (mListener != null)
                        mListener.onPaySuccess();
                } else {
                    if (mListener != null)
                        mListener.onPayFailed(status, msg);
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                if (mListener != null)
                    mListener.onPayFailed(-1, context.getString(R.string.pay_failed_msg));
                return true;
            }
        });
    }

    /**
     * 火车票改签月结
     */
    public void pay_train_gq(final Context context, final String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("gqorderno", orderNo);
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        RetrofitUtil.confirmTrainGaiqian(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    ToastUtils.showTextToast(bean.getData());

                    BaseActivity a = (BaseActivity) context;
                    a.finish();
                    a.jumpToOrderDetail(context, orderNo, AlterOrderDetailActivity.class);
                    if (mListener != null)
                        mListener.onPaySuccess();
                } else {
                    if (mListener != null)
                        mListener.onPayFailed(status, msg);
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {
                if (mListener != null)
                    mListener.onPayFailed(-1, context.getString(R.string.pay_failed_msg));
                return true;
            }
        });
    }

    /**
     * 企业月结方式
     */
    public void pay_hotel(final Context context, String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.hotelPay(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    HotelOrderDetailActivity a = (HotelOrderDetailActivity) context;
                    a.jumpToOrderList(Constant.HOTEL);
                    if (mListener != null)
                        mListener.onPaySuccess();
                } else {
                    if (mListener != null)
                        mListener.onPayFailed(status, msg);
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {
                if (mListener != null)
                    mListener.onPayFailed(-1, context.getString(R.string.pay_failed_msg));
                return true;
            }
        });
    }

    /**
     * 企业月结方式
     */
    public void pay_plane(final Context context, String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.confirmPlaneTicket(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
//                    DialogUtil.showDialog(context, "提示", "", "确定", "已经提交成功，点击确认回到列表", new MyDialog.OnButtonClickListener() {
//                        @Override
//                        public void onLeftClick() {
//
//                        }
//
//                        @Override
//                        public void onRightClick() {
//                            MUtils.jumpToPage(context, HomeActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            Intent intent = new Intent(context, OrderListActivity.class);
//                            intent.putExtra("class", Constant.PLANE);
//                            context.startActivity(intent);
//                        }
//                    });

                    if (mListener != null)
                        mListener.onPaySuccess();
                } else {
                    if (mListener != null)
                        mListener.onPayFailed(status, msg);
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {
                if (mListener != null)
                    mListener.onPayFailed(-1, context.getString(R.string.pay_failed_msg));
                return true;
            }
        });
    }

    /**
     * 企业月结方式
     */
    public void pay_train(final Context context, final String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.confirmTrainTicket(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
//                    DialogUtil.showDialog(context, "提示", "", "确定", "已经提交成功，点击确认回到列表", new MyDialog.OnButtonClickListener() {
//                        @Override
//                        public void onLeftClick() {
//
//                        }
//
//                        @Override
//                        public void onRightClick() {
//                            TrainOrderDetailActivity a = (TrainOrderDetailActivity) context;
//                            a.jumpToOrderList(Constant.TRAIN);
//                        }
//                    });
                    BaseActivity a = (BaseActivity) context;
                    a.jumpToOrderList(Constant.TRAIN);
                    if (mListener != null)
                        mListener.onPaySuccess();
                } else {
                    if (mListener != null)
                        mListener.onPayFailed(status, msg);
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {
                if (mListener != null)
                    mListener.onPayFailed(-1, context.getString(R.string.pay_failed_msg));
                return true;
            }
        });
    }

    public static PayModule getInstance() {
        if (payModule == null) {
            payModule = new PayModule();
        }
        return payModule;
    }

    public interface OnPayResultListener {
        void onPaySuccess();

        void onPayFailed(int code, String msg);
    }

    private String getRequestStr(String orderNo, String fromFlag, String channal) {
        RequestPayInfoBean rb = new RequestPayInfoBean();
        rb.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        rb.setOrderno(orderNo);
        rb.setOrdertype(fromFlag);
        rb.setPaychannel(channal);
        return AppUtils.getJson(rb);
    }

    /**
     * 支付宝支付
     */
    public void alipay(final Activity activity, String orderNo, final String fromFlag, final Handler handler) {
        RetrofitUtil.getPayInfo(activity, getRequestStr(orderNo, fromFlag, CHANNAL_ALIPAY), null,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            doAlipay(activity, bean.getData(), handler);
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
    }


    private void doAlipay(final Activity activity, final String orderInfo, final Handler handler) {
        if (orderInfo == null) return;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = ALIPAY_SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 微信支付
     */
    public void wxpay(final Context context, String orderNo, String fromFlag) {
        api = WXAPIFactory.createWXAPI(context, Constant.APP_ID_WX);
        api.registerApp(Constant.APP_ID_WX);
        RetrofitUtil.getPayInfo(context, getRequestStr(orderNo, fromFlag, CHANNAL_WXPAY), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    doWxPay(context, bean.getData());
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void doWxPay(Context context, String payStr) {
        Gson gson = new Gson();
        WxInfoBean wb = gson.fromJson(payStr, WxInfoBean.class);
        PayReq req = new PayReq();
        req.appId = wb.getAppid();
        req.partnerId = wb.getPartnerid();
        req.prepayId = wb.getPrepayid();
        req.nonceStr = wb.getNoncestr();
        req.timeStamp = wb.getTimestamp();
        req.packageValue = wb.getPackageX();
        req.sign = wb.getSign();
        req.extData = "app data"; // optional
        Toast.makeText(context, "正在支付...", Toast.LENGTH_SHORT).show();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    /**
     * 银联支付
     */
    public void unionpay(final Context context, String orderNo, String fromFlag) {
        RetrofitUtil.getPayInfo(context, getRequestStr(orderNo, fromFlag, CHANNAL_UNION), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    startUnionPay(context, bean.getData());
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void startUnionPay(Context context, String tn) {
        if (TextUtils.isEmpty(tn)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("错误提示");
            builder.setMessage("网络连接失败,请重试!");
            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            /*************************************************
             * 步骤2：通过银联工具类启动支付插件
             ************************************************/
            UPPayAssistEx.startPay(context, null, null, tn, PayModule.mMode);
        }
    }

}
