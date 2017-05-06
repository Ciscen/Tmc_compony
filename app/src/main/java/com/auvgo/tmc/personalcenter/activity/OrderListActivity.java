package com.auvgo.tmc.personalcenter.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.VPAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.personalcenter.fragment.HotelOrderListFragment;
import com.auvgo.tmc.personalcenter.fragment.PlaneOrderListFragment;
import com.auvgo.tmc.personalcenter.fragment.TrainOrderListFragment;
import com.auvgo.tmc.personalcenter.interfaces.FreshByTypeFragment;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, View.OnClickListener, TrainOrderListFragment.OnFragmentInteractionListener
        , PlaneOrderListFragment.OnFragmentInteractionListener {

    private RadioGroup rg;
    private TextView title_single;
    private TextView normal_tv, alter_tv, return_tv;
    private View indicator;
    private View search, tabbar;
    private ViewPager vp;
    private List<Fragment> list;
    private VPAdapter adapter;
    private String clazz;//表示订单的类型，区分火车票，机票，酒店
    private int mType = 0;//用户类型，表示是否可以查看全部订单的权限,0没有，1有
    private String mLevel = "geren";//个人0、全部1，当前选中的页面
    private int mLeftPadding;
    private int mCurrentPosition = 0;//viewpager的位置
    private boolean isFirstIn = true;
    private static int ITEM_COUNT = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_order_list;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new VPAdapter(getSupportFragmentManager(), list);
        clazz = getIntent().getStringExtra("class");
        ITEM_COUNT = clazz != null && clazz.equals("hotel") ? 1 : 3;
        if (!MyApplication.mUserInfoBean.getLevel().equals("geren")) {
            mType = 1;
        }
    }

    @Override
    protected void findViews() {
        tabbar = findViewById(R.id.home_order_list_tab_fl);
        rg = (RadioGroup) findViewById(R.id.home_order_list_title_rg);
        title_single = (TextView) findViewById(R.id.home_order_list_title_tv);
        normal_tv = (TextView) findViewById(R.id.home_order_list_normal);
        alter_tv = (TextView) findViewById(R.id.home_order_list_alter);
        return_tv = (TextView) findViewById(R.id.home_order_list_retrun);
        indicator = findViewById(R.id.home_order_list_indicator);
        search = findViewById(R.id.title_orderlist_search);
        vp = (ViewPager) findViewById(R.id.home_order_list_vp);
        initFragments();
    }

    private void initFragments() {
        if (clazz == null) {
            clazz = Constant.TRAIN;
        }
        for (int i = 0; i < ITEM_COUNT; i++) {
            Fragment f = null;
            switch (clazz) {
                case Constant.TRAIN:
                    f = new TrainOrderListFragment();
                    break;
                case Constant.PLANE:
                    f = new PlaneOrderListFragment();
                    break;
                case Constant.HOTEL:
                    // TODO: 2017/2/27
                    f = new HotelOrderListFragment();
                    break;
            }
            Bundle b = new Bundle();
            b.putInt("ticketType", i);
            b.putString("levelType", mLevel);
            if (f != null) {
                f.setArguments(b);
            }
            list.add(f);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        if (mType == 0) {
            rg.setVisibility(View.GONE);
            title_single.setVisibility(View.VISIBLE);
        } else {
            rg.setVisibility(View.VISIBLE);
            title_single.setVisibility(View.GONE);
        }
        if (ITEM_COUNT == 1) tabbar.setVisibility(View.GONE);
        vp.setAdapter(adapter);
    }

    private void setIndicatorPosition() {
        indicator.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int width = indicator.getMeasuredWidth();
        LogFactory.d(width + "--------------" + indicator.getWidth());
        mLeftPadding = (DeviceUtils.getDisplayMetrics().widthPixels / 3 - indicator.getWidth()) / 2;
        indicator.setX(mLeftPadding);
    }

    @Override
    protected void initListener() {
        rg.setOnCheckedChangeListener(this);
        vp.setOnPageChangeListener(this);
        normal_tv.setOnClickListener(this);
        return_tv.setOnClickListener(this);
        search.setOnClickListener(this);
        alter_tv.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
        vp.setOffscreenPageLimit(3);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (isFirstIn) {
                setIndicatorPosition();
                isFirstIn = false;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RadioButton rb = (RadioButton) group.getChildAt(i);
            if (group.getChildAt(i).getId() == checkedId) {
                rb.setChecked(true);
                rb.setTextColor(Color.BLACK);
                if (i == 0) {
                    mLevel = "geren";
                } else {
                    mLevel = "all";
                }
                freshFragment();
            } else {
                rb.setChecked(false);
                rb.setTextColor(Color.WHITE);
            }
        }
    }

    private void freshFragment() {
        for (int i = 0; i < list.size(); i++) {
            ((FreshByTypeFragment) list.get(i)).setTypeAndFresh(mLevel);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        indicator.setX(mLeftPadding + position * DeviceUtils.getDisplayMetrics().widthPixels / 3 + positionOffsetPixels / 3);
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void finish(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_order_list_normal:
                vp.setCurrentItem(0);
                break;
            case R.id.home_order_list_retrun:
                vp.setCurrentItem(1);
                break;
            case R.id.home_order_list_alter:
                vp.setCurrentItem(2);
                break;
            case R.id.title_orderlist_search:
                Intent intent = new Intent(this, OrderFilterActivity.class);
                intent.putExtra("position", mCurrentPosition);
                intent.putExtra("from", clazz);
                startActivityForResult(intent, Constant.ACTIVITY_TRAIN_ORDER_LIST_FLAG);
                break;
        }
    }

    @Override
    public void onRequestComplete(int ticketType, int i) {
        switch (ticketType) {
            case TrainOrderListFragment.NORMAL:
                normal_tv.setText("正常单(" + i + ")");
                break;
            case TrainOrderListFragment.RETURNL:
                return_tv.setText("退票单(" + i + ")");
                break;
            case TrainOrderListFragment.ALTER:
                alter_tv.setText("改签单(" + i + ")");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_TRAIN_ORDER_LIST_FLAG
                && resultCode == Constant.ACTIVITY_TRAIN_ORDER_FILTER_FLAG) {
            String name = data.getStringExtra("name");
            String dateType = data.getStringExtra("dateType");
            String startDate = data.getStringExtra("startDate");
            String endDate = data.getStringExtra("endDate");
            String orderStatus = data.getStringExtra("orderStatus");
            OrderFilterActivity.FilterBean fb = data.getParcelableExtra("filterBean");
            switch (clazz) {
                case Constant.TRAIN:
                    ((TrainOrderListFragment) list.get(mCurrentPosition)).doFilter(name, dateType, startDate, endDate, fb);
                    break;
                case Constant.PLANE:
                    ((PlaneOrderListFragment) list.get(mCurrentPosition)).doFilter(name, dateType, startDate, endDate, fb);
                    break;
                case Constant.HOTEL:
                    ((HotelOrderListFragment) list.get(mCurrentPosition)).doFilter(name, dateType, startDate, endDate, fb);
                    break;
            }

        }
    }
}
