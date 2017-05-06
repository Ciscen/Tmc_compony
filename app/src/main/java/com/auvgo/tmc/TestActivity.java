package com.auvgo.tmc;

import android.view.ViewGroup;

import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.views.TuyaView;

public class TestActivity extends BaseActivity {

    private ViewGroup ll;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ll = (ViewGroup) findViewById(R.id.test_root);
        TuyaView tuyaView = new TuyaView(this, DeviceUtils.getScreenWidth(this), DeviceUtils.getScreenHeight(this));
        ll.addView(tuyaView);
    }

    @Override
    protected void setViews() {

    }
}
