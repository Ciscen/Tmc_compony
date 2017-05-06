package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.RecommentPlaneListAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.TitleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommentPlaneActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TitleView title;
    private ListView lv;
    private ArrayList<PlaneListBean> list;
    private boolean isReturn;
    private PlaneRouteBeanWF firstRoute;
    private RecommentPlaneListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recomment_plane;
    }

    @Override
    protected void initData() {
        list = (ArrayList<PlaneListBean>) getIntent().getSerializableExtra("list");
        isReturn = getIntent().getBooleanExtra("isReturn", false);
        if (isReturn)
            firstRoute = (PlaneRouteBeanWF) getIntent().getSerializableExtra("firstRoute");
        adapter = new RecommentPlaneListAdapter(this, list);
    }

    @Override
    protected void findViews() {
        title = (TitleView) findViewById(R.id.plane_recomment_title);
        lv = (ListView) findViewById(R.id.plane_recomment_lv);
    }

    @Override
    protected void initView() {
        lv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void setViews() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PlaneListBean bean = list.get(position);
        boolean b = bean.isAirlineWei();
        if (b) {//违背
            if (MyApplication.mPlanePolicy.getController() == 1) {//违背，可以预订
                doNext(position);
            } else {//违背不可以预订
                DialogUtil.showDialog(this, "提示", "取消", "", "违背航班不可预订", null);
            }
        } else {
            doNext(position);
        }
    }

    private void doNext(int position) {
        Intent intent = new Intent();
        PlaneRouteBeanWF b = new PlaneRouteBeanWF();
        b.setBean(list.get(position));
        b.setCangwei(position);
        if (MyApplication.isWF) {
            if (!isReturn) {
                intent.putExtra("isReturn", true);
                intent.putExtra("firstRoute", b);
                intent.putExtra("fromCode", MyApplication.toCityCode);
                intent.putExtra("toCode", MyApplication.fromCityCode);
                intent.putExtra("fromName", MyApplication.toCityName);
                intent.putExtra("toName", MyApplication.fromCityName);
                intent.putExtra("startDate", MyApplication.returnDate);
                MyApplication.firstRoute = b;
                intent.setClass(this, PlaneListActivity.class);
            } else {
                intent.putExtra("firstRoute", firstRoute);
                intent.putExtra("secondRoute", b);
                intent.setClass(this, PlaneBookActivity.class);
            }
        } else {
            intent.putExtra("firstRoute", b);
            intent.setClass(this, PlaneBookActivity.class);
        }
        startActivity(intent);
    }
}
