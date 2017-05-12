package com.auvgo.tmc.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.personalcenter.activity.OrderListActivity;
import com.auvgo.tmc.train.bean.ComSettingBean;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.KeyboardChangeListener;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by admin on 2016/11/7
 */

public abstract class BaseActivity extends FragmentActivity {
    protected Context context;
    protected static final int CODE_SUCCESS = 200;
    private final String DEFAULT_SETTING_VALUE = "2";
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = this;
        init();
    }

    private void init() {
        initData();
        findViews();
        initView();
        setViews();
        initListener();
        getData();
        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {

            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                View v = findViewById(R.id.bottom_send);
                if (v != null)
                    v.setVisibility(isShow ? View.GONE : View.VISIBLE);
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected void findViews() {
    }

    protected abstract void initView();

    protected void initListener() {
    }

    protected void getData() {
    }

    protected abstract void setViews();

    public void refreshPage() {
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.closeDialog();
    }

    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    public Gson getGson() {
        return new Gson();
    }

    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.PERMISSION_CODE_IMEI) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onGetIMEIPermission();
            } else {
                onGetIMEIPermissionFaild();
            }
        }
    }

    public void onGetIMEIPermissionFaild() {
        ToastUtils.showTextToast("获取权限失败，请到系统设置里进行权限设置");
    }

    public void onGetIMEIPermission() {
    }

    /**
     * @param clazz 订单类别，Constant下的
     */
    public void jumpToOrderList(String clazz) {
        MUtils.jumpToPage(this, HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent = new Intent(this, OrderListActivity.class);
        intent.putExtra("class", clazz);
        startActivity(intent);
    }

    public void jumpToOrderDetail(Context context, String orderNo, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("orderNo", orderNo);
        context.startActivity(intent);
    }

    public void freshOrderDetail(String orderNo) {
        finish();
        Intent intent = new Intent(this, this.getClass());
        intent.putExtra("orderNo", orderNo);
        startActivity(intent);
    }

    public void showDialog(String left, String right, String contnet, MyDialog.OnButtonClickListener listener) {
        DialogUtil.showDialog(this, "提示", left, right, contnet, listener);
    }

    public void setCostCenterAndProjectVisibility(View costCenterView, View projectView) {
        MyList<ComSettingBean.ProductSetBean.ProconfvalueBean> settings = MyApplication.mComSettingBean.
                getProductSet().getProconfValue();
        String costcenter = settings.get("costcenter");
        if (costcenter == null || costcenter.equals("2") && costCenterView != null)
            costCenterView.setVisibility(View.GONE);
        String projectinfo = settings.get("projectinfo");
        if (projectinfo == null || projectinfo.equals("2") && projectView != null)
            projectView.setVisibility(View.GONE);
    }

    public void setApplyNoVisibility(View applyNoView) {
        MyList<ComSettingBean.ProductSetBean.ProconfvalueBean> settings = MyApplication.mComSettingBean.
                getProductSet().getProconfValue();
        String travelorder = settings.get("travelorder");
        if (travelorder == null || travelorder.equals("2")) {
            applyNoView.setVisibility(View.GONE);
        }
    }

    public String getCostCenterSettingCode() {
        String costcenter = MyApplication.mComSettingBean.
                getProductSet().getProconfValue().get("costcenter");
        return costcenter == null ? DEFAULT_SETTING_VALUE : costcenter;//默认2
    }

    public String getProjectSettingCode() {
        String projectinfo = MyApplication.mComSettingBean.
                getProductSet().getProconfValue().get("projectinfo");
        return projectinfo == null ? DEFAULT_SETTING_VALUE : projectinfo;
    }

    public String getApplyNoSettingCode() {
        String travelorder = MyApplication.mComSettingBean.
                getProductSet().getProconfValue().get("travelorder");
        return travelorder == null ? DEFAULT_SETTING_VALUE : travelorder;
    }

    public String getPeiSongAddrSettingCode() {//0不现实1显示
        String peisongaddress = MyApplication.mComSettingBean.
                getProductSet().getProconfValue().get("peisongaddress");
        return peisongaddress == null ? "0" : peisongaddress;
    }

    public String getJPBxSetting() {
        String bxsetting = MyApplication.mComSettingBean.
                getProductSet().getProconfValue().get("jpinsurance");
        return bxsetting == null ? "0" : bxsetting;
    }
    public String getJDBxSetting() {
        String bxsetting = MyApplication.mComSettingBean.
                getProductSet().getProconfValue().get("jdinsurance");
        return bxsetting == null ? "0" : bxsetting;
    }
    public String getHCPBxSetting() {
        String bxsetting = MyApplication.mComSettingBean.
                getProductSet().getProconfValue().get("hcpinsurance");
        return bxsetting == null ? "0" : bxsetting;
    }

    public void setPeiSongAddr(View view) {
        String pscode = getPeiSongAddrSettingCode();
        if (pscode.equals("1")) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 设置添加临时乘客的显示状态
     *
     * @param view
     */
    public void setAddLsPsgVisibility(View view) {
        if (MyApplication.mUserInfoBean.getAddcustflage().equals("0")) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 是否是预定人
     */
    public boolean isAllowBook() {
        if (MyApplication.mUserInfoBean.getIfallowbook() == 0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
