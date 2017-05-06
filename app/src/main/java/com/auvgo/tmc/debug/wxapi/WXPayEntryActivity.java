package com.auvgo.tmc.debug.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.PayListActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.ToastUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    public static final String ACTION_WXPAY_RESULT = "action.wx.pay.result";

    @Override
    protected int getLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    protected void initData() {
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage(getString(R.string.pay_result_callback_msg, getString(resp)));
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(WXPayEntryActivity.this, PayListActivity.class);
                    intent.setAction(ACTION_WXPAY_RESULT);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }

    @NonNull
    private String getString(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                return "支付成功";
            case BaseResp.ErrCode.ERR_COMM:
                return "支付出现问题";
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                return "支付取消";
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                return "支付失败";
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                return "授权失败";
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                return "支付不支持";
        }
        return "未知错误";
    }
}