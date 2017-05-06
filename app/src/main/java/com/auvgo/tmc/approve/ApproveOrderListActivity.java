package com.auvgo.tmc.approve;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.adapter.VPAdapter;
import com.auvgo.tmc.approve.fragment.ApproveOrderListFragment;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class ApproveOrderListActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        View.OnClickListener,
        ApproveOrderListFragment.ApproveOrderListFragmentCallback {

    private TextView toApprove_tv, approved_tv;
    //    private EditText search_et;
    private ViewPager vp;
    private List<ApproveOrderListFragment> list;
    private VPAdapter adapter;
    private View indicator;
    private int mLeftPadding;//指示条左侧的padding
    private int mCurrentPosition;
    private boolean isFirstIn = true;
    private RadioGroup rg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_approve_order_list;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new VPAdapter(getSupportFragmentManager(), list);
        ApproveOrderListFragment fr1 = new ApproveOrderListFragment();
        ApproveOrderListFragment fr2 = new ApproveOrderListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 0);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 1);
        fr1.setArguments(bundle1);
        fr2.setArguments(bundle2);
        list.add(fr1);
        list.add(fr2);
    }

    @Override
    protected void findViews() {
        toApprove_tv = (TextView) findViewById(R.id.approve_toapprove);
        approved_tv = (TextView) findViewById(R.id.approve_approved);
//        search_et = (EditText) findViewById(R.id.approve_et);
        vp = (ViewPager) findViewById(R.id.approve_vp);
        indicator = findViewById(R.id.approve_indicator);
//        train_tv = (TextView) findViewById(R.id.approve_train);
//        hotel_tv = (TextView) findViewById(R.id.approve_hotel);
//        air_tv = (TextView) findViewById(R.id.approve_air);
        rg = (RadioGroup) findViewById(R.id.approve_rg);
    }

    @Override
    protected void initView() {
        ((RadioButton) rg.getChildAt(0)).setChecked(true);
    }

    @Override
    protected void initListener() {
        vp.setOnPageChangeListener(this);
        toApprove_tv.setOnClickListener(this);
        approved_tv.setOnClickListener(this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    if (rb.getId() == checkedId) {
                        setCheck(i);
                        rb.setTextColor(getResources().getColor(R.color.appTheme1));
                    } else {
                        rb.setTextColor(getResources().getColor(R.color.color_666));
                    }
                }
            }
        });
    }

    private void setCheck(int i) {
        switch (i) {
            case 0:
                change(Constant.TRAIN);
                break;
            case 1:
                change(Constant.PLANE);
                break;
            case 2:
                change(Constant.HOTEL);
                break;
        }
    }

    @Override
    protected void setViews() {
        vp.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFirstIn) {
            isFirstIn = false;
            setIndicatorPosition();
        }
    }

    private void setIndicatorPosition() {
        indicator.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int width = indicator.getMeasuredWidth();
        LogFactory.d(width + "--------------" + indicator.getWidth());
        mLeftPadding = (DeviceUtils.getDisplayMetrics().widthPixels / 2 - indicator.getWidth()) / 2;
        indicator.setX(mLeftPadding);
    }

    @Override
    protected void getData() {
        super.getData();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        indicator.setX(mLeftPadding + position * DeviceUtils.getDisplayMetrics().widthPixels / 2 + positionOffsetPixels / 2);
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        int p = mLeftPadding + DeviceUtils.getDisplayMetrics().widthPixels / 2;

        switch (v.getId()) {
            case R.id.approve_toapprove:
                if (mCurrentPosition == 1)
                    mCurrentPosition = 0;
                break;
            case R.id.approve_approved:
                if (mCurrentPosition == 0)
                    mCurrentPosition = 1;
                break;
        }
        vp.setCurrentItem(mCurrentPosition);
    }

    private void change(String flag) {
        for (int i = 0; i < list.size(); i++)
            list.get(i).changeContent(flag);

    }

    @Override
    public void setNum(int i, int n) {
        if (i == 0)
            toApprove_tv.setText("待审批(" + n + ")");
        else
            approved_tv.setText("已审批(" + n + ")");

    }
}
