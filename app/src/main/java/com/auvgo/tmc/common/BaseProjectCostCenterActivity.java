package com.auvgo.tmc.common;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.views.RefreshListView;
import com.auvgo.tmc.views.TitleView;

public abstract class BaseProjectCostCenterActivity extends BaseActivity implements RefreshListView.OnRefreshListener, View.OnKeyListener {

    protected TitleView title;
    protected EditText et;
    protected View cancle;
    protected RefreshListView lv;
    protected int pageNum = 1;
    protected String keyword = "";
    protected boolean isLast;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_costcenter;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        initDatas();
        AppUtils.initSoftInput(this);
    }


    @Override
    protected void findViews() {
        title = (TitleView) findViewById(R.id.project_costcenter_title);
        et = (EditText) findViewById(R.id.project_costcenter_et);
        cancle = findViewById(R.id.project_costcenter_cancle);
        lv = (RefreshListView) findViewById(R.id.project_costcenter_lv);
    }

    @Override
    protected void initView() {
        lv.setOnRefreshListener(this);
        initViews();
    }

    @Override
    protected void initListener() {
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        et.setOnKeyListener(this);
    }

    @Override
    protected void setViews() {
        getList();
    }

    protected abstract void initDatas();

    protected abstract void initViews();

    protected abstract void getList();

    @Override
    public void onLoadMore() {
        lv.onRefreshComplete(true);
        if (isLast) {
            pageNum++;
            getList();
        }
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        getList();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            String s = et.getText().toString();
            keyword = s;
            pageNum = 1;
            getList();
        }

        return false;
    }
}
