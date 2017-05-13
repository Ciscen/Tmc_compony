package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.CldActivity;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.Calendar.CalendarActivity;
import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaneAlterQueryActivity extends BaseActivity {
    @BindView(R.id.alter_plane_from)
    TextView fromCity;
    @BindView(R.id.alter_plane_to)
    TextView toCity;
    @BindView(R.id.alter_plane_date)
    TextView date;
    @BindView(R.id.alter_plane_carbintype)
    TextView carbinType_tv;

    private String fromCityCode;
    private String toCityCode;
    private String carbintype;
    private String startdate;
    private String carrier;
    private List<SelectionBean> selectionBeens;
    private int mTypePosition;//当前选择的舱位

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_alter_query;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        selectionBeens = new ArrayList<>();
        selectionBeens.add(new SelectionBean("不限", false));
        selectionBeens.add(new SelectionBean("经济舱", false));
        selectionBeens.add(new SelectionBean("公务舱", false));
        selectionBeens.add(new SelectionBean("头等舱", false));

        PlaneOrderDetailBean bean = PlaneReturnApplyActivity.mBean;
        List<PlaneOrderDetailBean.RoutesBean> routes = bean.getRoutes();

        // TODO: 2016/12/6 在进入页面的时候完成数据的初始化
        fromCityCode = AppUtils.getNoNullStr(routes.get(0).getOrgcitycode());
        toCityCode = AppUtils.getNoNullStr(routes.get(0).getDstcitycode());
        carbintype = "";
        startdate = TimeUtils.getToday();
        carrier = routes.get(0).getCarriecode();
        MyApplication.fromCityName = AppUtils.getNoNullStr(routes.get(0).getOrgcityname());
        MyApplication.toCityName = AppUtils.getNoNullStr(routes.get(0).getDstcityname());
        MyApplication.fromCityCode = fromCityCode;
        MyApplication.toCityCode = toCityCode;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initView() {
        fromCity.setText(MyApplication.fromCityName);
        toCity.setText(MyApplication.toCityName);
        date.setText(TimeUtils.getToday().substring(5) + " " +
                TimeUtils.getTomorrowWeekDay(TimeUtils.getToday()));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {

    }

    @OnClick(R.id.alter_plane_query_click_date)
    public void onDateItemClick() {
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("checkInDate", startdate);
//        intent.putExtra("bundle", bundle);
//        intent.setClass(this, CalendarActivity.class);
//        startActivityForResult(intent, Constant.ACTIVITY_PLANE_ALTER_QUERY_FLAG);
        Intent intent = new Intent(context, CldActivity.class);
        intent.putExtra(CldActivity.KEY_SELECTED_DATE_1, startdate);
        startActivityForResult(intent, Constant.ACTIVITY_PLANE_ALTER_QUERY_FLAG);
    }

    @OnClick(R.id.alter_plane_carbintype)
    public void onCarbinTypeClick() {
        DialogUtil.showPickerPopWithSureHeight(this, "舱位类型", mTypePosition, selectionBeens,
                new MyPickerView.OnPickerViewSure() {
                    @Override
                    public void onSingleSureClick(int p) {
                        mTypePosition = p;
                        carbinType_tv.setText(selectionBeens.get(p).getName());
                    }

                    @Override
                    public void onMultiSureClick(List<Integer> s) {

                    }
                });

    }

    @OnClick(R.id.alter_plane_click_query)
    public void onSureClick() {
        MyApplication.isWF = false;
        Intent intent = new Intent(this, PlaneListActivity.class);
        intent.putExtra("fromCode", fromCityCode);
        intent.putExtra("toCode", toCityCode);
        intent.putExtra("fromName", MyApplication.fromCityName);
        intent.putExtra("toName", MyApplication.toCityName);
        intent.putExtra("type", mTypePosition);
        intent.putExtra("startDate", startdate);
        intent.putExtra("isAlter", true);
        intent.putExtra("carrier", carrier);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_PLANE_ALTER_QUERY_FLAG
                && resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            String a = data.getStringExtra(CldActivity.KEY_RESULT_FIRST);
            date.setText(a.substring(5) + " " + TimeUtils.getTomorrowWeekDay(a));
            startdate = a;
        }
    }
}
