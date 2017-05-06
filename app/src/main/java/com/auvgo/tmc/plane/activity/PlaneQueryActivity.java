package com.auvgo.tmc.plane.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.VPAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.plane.fragment.PlaneHomeFragment;
import com.auvgo.tmc.plane.fragment.PlaneHomeWFFragment;
import com.auvgo.tmc.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

public class PlaneQueryActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager vp;
    private TextView single_tv, wf_tv;
    private View indicator;
    private VPAdapter adapter;
    private List<Fragment> list;
    private boolean b = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_home;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new VPAdapter(getSupportFragmentManager(), list);
        PlaneHomeFragment f0 = new PlaneHomeFragment();
        PlaneHomeWFFragment f1 = new PlaneHomeWFFragment();
        list.add(f0);
        list.add(f1);
    }

    @Override
    protected void findViews() {
        vp = (ViewPager) findViewById(R.id.plane_home_vp);
        single_tv = (TextView) findViewById(R.id.plane_home_single_tv);
        wf_tv = (TextView) findViewById(R.id.plane_home_wf_tv);
        indicator = findViewById(R.id.plane_home_indicator);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (b) {
            b = false;
            ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
            layoutParams.width = DeviceUtils.deviceWidth() / 2;
            indicator.setLayoutParams(layoutParams);
            indicator.setX(0);
        }
    }

    @Override
    protected void initView() {
        vp.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        single_tv.setOnClickListener(this);
        wf_tv.setOnClickListener(this);
        vp.setOnPageChangeListener(this);
    }

    @Override
    protected void setViews() {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        indicator.setX(position * DeviceUtils.deviceWidth() / 2 + positionOffsetPixels / 2);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_home_single_tv:
                vp.setCurrentItem(0);
                single_tv.setTextColor(getResources().getColor(R.color.appTheme1));
                wf_tv.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.plane_home_wf_tv:
                vp.setCurrentItem(1);
                wf_tv.setTextColor(getResources().getColor(R.color.appTheme1));
                single_tv.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }
}
