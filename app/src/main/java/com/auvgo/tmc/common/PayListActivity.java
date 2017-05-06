package com.auvgo.tmc.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.module.AuthResult;
import com.auvgo.tmc.common.module.PayModule;
import com.auvgo.tmc.common.module.PayResult;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.hotel.activity.HotelOrderDetailActivity;
import com.auvgo.tmc.plane.activity.PlaneAlterDetailActivity;
import com.auvgo.tmc.plane.activity.PlaneOrderDetailActivity;
import com.auvgo.tmc.train.activity.AlterOrderDetailActivity;
import com.auvgo.tmc.train.activity.TrainOrderDetailActivity;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.debug.wxapi.WXPayEntryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PayListActivity extends BaseActivity {

    @BindView(R.id.pay_notice)
    TextView notice;
    @BindView(R.id.pay_price)
    TextView price;
    @BindView(R.id.pay_qiankuan)
    View qiankuan;
    @BindView(R.id.pay_alipay)
    View alipay;
    @BindView(R.id.pay_union)
    View union;
    @BindView(R.id.pay_wxpay)
    View wxpay;

    private String orderNo;
    private String fromFlag;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PayModule.ALIPAY_SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /*
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtils.showTextToast("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showTextToast("支付失败");
                    }
                    jump();
                    LogFactory.d(resultInfo + "---" + resultStatus);
                    break;
                }
                case PayModule.ALIPAY_SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtils.showTextToast("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtils.showTextToast("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                case PayModule.UNIONPAY_SDK_AUTH_FLAG:
                    MyApplication.getApplication().stopProgressDialog();
                    break;
            }
        }

    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_list;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderNo = intent.getStringExtra("orderNo");
        fromFlag = intent.getStringExtra("fromFlag");
    }

    @Override
    protected void findViews() {
        notice = (TextView) findViewById(R.id.pay_notice);
        price = (TextView) findViewById(R.id.pay_price);
        qiankuan = findViewById(R.id.pay_qiankuan);
        alipay = findViewById(R.id.pay_alipay);
        union = findViewById(R.id.pay_union);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setViews() {
    }

    @OnClick({R.id.pay_qiankuan, R.id.pay_alipay, R.id.pay_union, R.id.pay_wxpay})
    public void onItemClick(View v) {
        switch (v.getId()) {
            case R.id.pay_qiankuan:
                break;
            case R.id.pay_alipay:
                Alipay();
                break;
            case R.id.pay_union:
                unionPay();
                break;
            case R.id.pay_wxpay:
                wxPay();
                break;
        }
    }


    private void wxPay() {
        PayModule.getInstance().wxpay(this, orderNo, fromFlag);
    }

    private void Alipay() {
        PayModule.getInstance().alipay(this, orderNo, fromFlag, mHandler);
    }

    private void unionPay() {
        PayModule.getInstance().unionpay(this, orderNo, fromFlag);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {

            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 此处的verify建议送去商户后台做验签
                    // 如要放在手机端验，则代码必须支持更新证书
                    boolean ret = verify(dataOrg, sign, PayModule.mMode);
                    if (ret) {
                        // 验签成功，显示支付结果
                        msg = "支付成功！";
                    } else {
                        // 验签失败
                        msg = "支付失败！";
                    }
                } catch (JSONException e) {
                }
            }
            // 结果result_data为成功时，去商户后台查询一下再展示成功
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "支付取消";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                jump();
            }
        });
        builder.create().show();
    }

    private void jump() {
        finish();
        switch (fromFlag) {
            case PayModule.ORDER_TYPE_AIR:
                jumpToOrderList(Constant.PLANE);
                jumpToOrderDetail(context, orderNo, PlaneOrderDetailActivity.class);
                break;
            case PayModule.ORDER_TYPE_AIR_GQ:
                jumpToOrderList(Constant.PLANE);
                jumpToOrderDetail(context, orderNo, PlaneAlterDetailActivity.class);
                break;
            case PayModule.ORDER_TYPE_TRAIN:
                jumpToOrderList(Constant.TRAIN);
                jumpToOrderDetail(context, orderNo, TrainOrderDetailActivity.class);
                break;
            case PayModule.ORDER_TYPE_TRAIN_GQ:
                jumpToOrderList(Constant.TRAIN);
                jumpToOrderDetail(context, orderNo, AlterOrderDetailActivity.class);
                break;
            case PayModule.ORDER_TYPE_HOTEL:
                jumpToOrderList(Constant.HOTEL);
                jumpToOrderDetail(context, orderNo, HotelOrderDetailActivity.class);
                break;
        }
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action =intent.getAction();
        if (!TextUtils.isEmpty(action) && action.equals(WXPayEntryActivity.ACTION_WXPAY_RESULT)) {
            jump();
        }
    }
}
