package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PickerListAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.p.PTrainAlterApply;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.Calendar.CalendarActivity;
import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.List;

public class AlterQueryActivity extends BaseActivity implements View.OnClickListener {
    private TextView fromCity, toCity, date, seattype;
    private View item_date, item_type, sure;
    private String fromCityCode;
    private String toCityCode;
    private String seatType;
    private String startdate;
    private View cover;
    private List<? extends MyPickerView.Selection> selections;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_query;
    }

    @Override
    protected void initData() {
        TrainOrderDetailBean bean = PTrainAlterApply.detailBean;
        // TODO: 2016/12/6 在进入页面的时候完成数据的初始化
        fromCityCode = AppUtils.getNoNullStr(bean.getFromcitycode());
        toCityCode = AppUtils.getNoNullStr(bean.getArrivecitycode());
        seatType = Constant.TrainType.TRAIN_TYPE_ALL;
        startdate = TimeUtils.getToday();
        MyApplication.fromCityName = AppUtils.getNoNullStr(bean.getFromcityname());
        MyApplication.toCityName = AppUtils.getNoNullStr(bean.getArrivecityname());
        selections = getSelections();
    }

    private List<? extends MyPickerView.Selection> getSelections() {
        ArrayList<SelectionBean> list = new ArrayList<>();
        list.add(new SelectionBean("高铁/动车", false));
        list.add(new SelectionBean("普通列车", false));
        return list;
    }

    @Override
    protected void findViews() {
        cover = findViewById(R.id.alter_train_query_cover);
        fromCity = (TextView) findViewById(R.id.alter_train_from);
        toCity = (TextView) findViewById(R.id.alter_train_to);
        date = (TextView) findViewById(R.id.alter_train_date);
        seattype = (TextView) findViewById(R.id.alter_train_traintype);
        item_date = findViewById(R.id.alter_train_query_click_date);
        item_type = findViewById(R.id.alter_train_query_click_traintype);
        sure = findViewById(R.id.alter_train_query_click_query);
    }

    @Override
    protected void initView() {
        cover.setVisibility(View.VISIBLE);
        TrainOrderDetailBean bean = PTrainAlterApply.detailBean;
        fromCity.setText(AppUtils.getNoNullStr(bean.getFromcityname()));
        toCity.setText(AppUtils.getNoNullStr(bean.getArrivecityname()));
        date.setText(TimeUtils.getToday().substring(5) + " " +
                TimeUtils.getTomorrowWeekDay(TimeUtils.getToday()));

    }

    @Override
    protected void initListener() {
        sure.setOnClickListener(this);
        item_date.setOnClickListener(this);
        item_type.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.alter_train_query_click_query:
                bundle.putString("from", fromCityCode);
                bundle.putString("to", toCityCode);
                bundle.putString("startdate", startdate);
                bundle.putString("seatType", seatType);
                bundle.putInt(Constant.INTENT_KEY_FLAG, Constant.ACTIVITY_ALTER_TRAIN_HOME_FLAG);
                intent.putExtra("bundle", bundle);
                intent.setClass(this, TrainListActivity.class);
                startActivity(intent);
                break;
            case R.id.alter_train_query_click_date:
                bundle.putString("checkInDate", startdate);
                intent.putExtra("bundle", bundle);
                intent.setClass(this, CalendarActivity.class);
                startActivityForResult(intent, Constant.ACTIVITY_ALTER_TRAIN_HOME_FLAG);
                break;
            case R.id.alter_train_query_click_traintype:
                showPickerView();
                break;
        }
    }


    private void showPickerView() {
        final PickerListAdapter adapter = getPickerListAdapter();
        DialogUtil.showPickerView(this, cover, adapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<? extends SelectionBean> list = adapter.getList();
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setChecked(!list.get(i).isChecked());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new DialogUtil.OnPickerListener() {
            @Override
            public void onSure() {
                List<? extends SelectionBean> list = adapter.getList();

                if (list.get(0).isChecked()) {
                    if (list.get(1).isChecked()) {
                        seatType = Constant.TrainType.TRAIN_TYPE_ALL;
                        seattype.setText("不限");
                    } else {
                        seatType = Constant.TrainType.TRAIN_TYPE_GT;
                        seattype.setText("高铁/动车/城际");
                    }
                } else {
                    if (list.get(1).isChecked()) {
                        seatType = Constant.TrainType.TRAIN_TYPE_PT;
                        seattype.setText("普通列车");
                    } else {
                        seatType = Constant.TrainType.TRAIN_TYPE_ALL;
                        seattype.setText("不限");
                    }
                }
            }
        });

    }

    private PickerListAdapter getPickerListAdapter() {
        ArrayList<SelectionBean> list = new ArrayList<>();
        list.add(new SelectionBean("高铁/动车", false));
        list.add(new SelectionBean("普通列车", false));
        return new PickerListAdapter(this, list, R.layout.item_picker_lv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_ALTER_TRAIN_HOME_FLAG
                && resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            String a = data.getStringExtra("date");
            date.setText(a.substring(5) + " " + TimeUtils.getTomorrowWeekDay(a));
            startdate = a;
        }
    }
}
