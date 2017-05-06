package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.p.PPlaneList;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.plane.interfaces.ViewManager_PlaneList;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.Calendar.CalendarActivity;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.TitleView;

import java.util.ArrayList;
import java.util.List;

public class PlaneListActivity extends BaseActivity implements ViewManager_PlaneList,
        View.OnClickListener, AdapterView.OnItemClickListener {

    ////////////////View/////////////////
    private TextView before, after;
    private TextView calendar;
    private RadioGroup rg;
    private ListView lv;
    private View routeInfo;//去程信息
    private TextView route_date, route_name, route_time;
    private TitleView title;
    private View cover;
    private View filt;
    private View empty_view;
    ////////////////变量///////////////////
    private int currentButton = 1;
    private static final String HIGH2LOW = "从高到低";
    private static final String LOW2HIGH = "从低到高";
    private static final String LATE2EARLY = "从晚到早";
    private static final String EARLY2LATE = "从早到晚";

    ////////////////逻辑处理类///////////////////////
    private PPlaneList pPlaneList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_list;
    }

    @Override
    protected void initData() {
        pPlaneList = new PPlaneList(this, this);
        pPlaneList.initData(getIntent());
    }

    @Override
    protected void findViews() {
        empty_view = findViewById(R.id.empty_view);
        routeInfo = findViewById(R.id.plane_list_go_route_info);
        route_name = (TextView) findViewById(R.id.plane_list_route_name);
        route_date = (TextView) findViewById(R.id.plane_list_route_date);
        route_time = (TextView) findViewById(R.id.plane_list_route_time);
        before = (TextView) findViewById(R.id.plane_list_before);
        after = (TextView) findViewById(R.id.plane_list_after);
        calendar = (TextView) findViewById(R.id.plane_list_calendar);
        lv = (ListView) findViewById(R.id.plane_list_lv);
        cover = findViewById(R.id.plane_list_cover);
        filt = findViewById(R.id.plane_list_filt);
        title = (TitleView) findViewById(R.id.plane_list_title);
        rg = (RadioGroup) findViewById(R.id.plane_list_rg);
        ViewGroup ll = (ViewGroup) findViewById(R.id.plane_list_ll);
    }

    @Override
    protected void initView() {
        cover.setVisibility(View.VISIBLE);
        calendar.setText(pPlaneList.getStartdate());
        PlaneRouteBeanWF firstRoute = pPlaneList.getFirstRoute();
        if (MyApplication.isWF && firstRoute != null) {
            routeInfo.setVisibility(View.VISIBLE);
            PlaneListBean bean = firstRoute.getBean();
            route_date.setText(String.format("%s日", TimeUtils.getMMdd(bean.getDeptdate()).replace("-", "月")));
            route_time.setText(bean.getDepttime() + "-" + bean.getArritime());
            route_name.setText(String.format("%s%s", bean.getCarriername(), bean.getAirline()));
            title.setTitle(MyApplication.toCityName + "-" + MyApplication.fromCityName);
            title.setSubTitle(this, "返程");
        } else {
            routeInfo.setVisibility(View.GONE);
            title.setTitle(MyApplication.fromCityName + "-" + MyApplication.toCityName);
        }
        lv.setAdapter(pPlaneList.getAdapter());
        lv.setEmptyView(empty_view);
        checkEnable();
        RadioButton rb = ((RadioButton) rg.getChildAt(1));
        rb.setChecked(true);
        empty_view.setVisibility(View.INVISIBLE);
    }

    private boolean checkEnable() {
        if (pPlaneList.getStartdate().equals(TimeUtils.getToday())) {
            before.setTextColor(Color.parseColor("#666666"));
            return false;
        } else {
            before.setTextColor(Color.parseColor("#333333"));
            return true;
        }
    }

    @Override
    protected void initListener() {
        filt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pPlaneList.showFilterPop();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setRGListener(group, checkedId);
            }
        });
        for (int i = 0; i < rg.getChildCount(); i++) {
            final RadioButton rb = (RadioButton) rg.getChildAt(i);
            final int finalI = i;
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == currentButton) {
                        pPlaneList.setOrderType(-pPlaneList.getOrderType());
                        changeText(rb, finalI);
                    } else {
                        pPlaneList.setOrderType(1);
                        changeText(rb, finalI);
                    }
                    currentButton = finalI;
                    pPlaneList.filtList();
                }
            });
        }
        before.setOnClickListener(this);
        after.setOnClickListener(this);
        calendar.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }

    private void changeText(RadioButton rb, int finalI) {
        if (finalI == 0) {
            rb.setText(pPlaneList.getOrderType() == -1 ? HIGH2LOW : LOW2HIGH);
        } else {
            rb.setText(pPlaneList.getOrderType() == -1 ? LATE2EARLY : EARLY2LATE);
        }
    }

    private void setRGListener(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton child = (RadioButton) group.getChildAt(i);
            if (child.getId() == checkedId) {
                switch (i) {
                    case 0:
                        pPlaneList.setPriceOrder(true);
                        break;
                    case 1:
                        pPlaneList.setStartTimeOrder(true);
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        pPlaneList.setPriceOrder(false);
                        child.setText(LOW2HIGH);
                        break;
                    case 1:
                        child.setText(EARLY2LATE);
                        pPlaneList.setStartTimeOrder(false);
                        break;
                }
            }
        }
        pPlaneList.filtList();
    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        super.getData();
        pPlaneList.getPlaneList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_list_after:
                pPlaneList.setStartdate(TimeUtils.getTomorrow(pPlaneList.getStartdate()));
                calendar.setText(pPlaneList.getStartdate());
                pPlaneList.getPlaneList();
                checkEnable();
                break;
            case R.id.plane_list_before:
                if (checkEnable()) {
                    pPlaneList.setStartdate(TimeUtils.getYestoday(pPlaneList.getStartdate()));
                } else {
                    ToastUtils.showTextToast("购票时间不能小于当天");
                    return;
                }
                pPlaneList.getPlaneList();
                calendar.setText(pPlaneList.getStartdate());
                break;
            case R.id.plane_list_calendar:
                Intent intent = new Intent(this, CalendarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("checkInDate", pPlaneList.getStartdate());
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, Constant.ACTIVITY_PLANE_LIST_FLAG);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final PlaneListBean plb = pPlaneList.getList_filted().get(position);
        if (plb.isCodeShare()) {
            DialogUtil.showDialog(this, "", "取消", "继续",
                    String.format("\n您选择的是共享航班,实际承运航班号是%s，请按实际承运航班办理值机！"
                            , plb.getSharecarrier()), new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {

                        }

                        @Override
                        public void onRightClick() {
                            jump(plb);
                        }
                    });
        } else {
            jump(plb);
        }
    }

    private void jump(PlaneListBean plb) {
        Intent intent = new Intent(this, PlaneDetailActivity.class);
        intent.putExtra("bean", plb);
//        intent.putExtra("list", pPlaneList.getList_all());
        intent.putExtra("isReturn", pPlaneList.isReturn());
        if (pPlaneList.isReturn()) {
            intent.putExtra("firstRoute", pPlaneList.getFirstRoute());
        }
        intent.putExtra("isAlter", pPlaneList.isAlter());
        startActivity(intent);
    }

    @Override
    public void freshTitle() {
        title.setTitle(pPlaneList.getFromName() + "-" + pPlaneList.getToName());

        title.setSubTitle(this, pPlaneList.isReturn() ? "返程 共" + pPlaneList.getList_filted().size() + "条" :
                "共" + pPlaneList.getList_filted().size() + "条");

    }

    @Override
    public void setEmptyViews() {
        List<PlaneListBean> list_filted = pPlaneList.getList_filted();
        empty_view.setVisibility(list_filted == null || list_filted.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_PLANE_LIST_FLAG && resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            pPlaneList.setStartdate(data.getStringExtra("date"));
            calendar.setText(pPlaneList.getStartdate());
            getData();
        }
    }
}
