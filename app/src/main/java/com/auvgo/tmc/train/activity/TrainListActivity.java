package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.CldActivity;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainlist;
import com.auvgo.tmc.p.PTrainList;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.TitleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrainListActivity extends BaseActivity implements ViewManager_trainlist, AdapterView.OnItemClickListener {

    ////////////////View/////////////////
    @BindView(R.id.train_list_before)
    TextView before;
    @BindView(R.id.train_list_after)
    TextView after;
    @BindView(R.id.train_list_calendar)
    TextView calendar;
    @BindView(R.id.train_list_rg)
    RadioGroup rg;
    @BindView(R.id.train_list_lv)
    ListView lv;
    @BindView(R.id.train_list_title)
    TitleView title;
    @BindView(R.id.train_list_filt)
    RadioButton filt;
    @BindView(R.id.empty_view)
    View empty_view;
    View headerView;
    TextView header_route;
    TextView header_time;
    TextView header_price;
    ////////////////变量///////////////////
    private static final String HIGH2LOW = "从高到低";
    private static final String LOW2HIGH = "从低到高";
    private static final String LATE2EARLY = "从晚到早";
    private static final String EARLY2LATE = "从早到晚";
    private static final String LONG2SHORT = "从长到短";
    private static final String SHORT2LONG = "从短到长";
    ////////////////逻辑处理类///////////////////////
    private PTrainList pTrainList;
    private int currentButton = -1;//当前选中的radioButton

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_list;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pTrainList = new PTrainList(this, this);
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            String from = bundle.getString("from");
            String to = bundle.getString("to");
            String stardate = bundle.getString("startdate");
            String seatType = bundle.getString("seatType");
            int flag = bundle.getInt(Constant.INTENT_KEY_FLAG,
                    Constant.ACTIVITY_TRAIN_HOME_FLAG);
            pTrainList.setFrom(from);
            pTrainList.setTo(to);
            pTrainList.setStartdate(stardate, true);
            pTrainList.setTypeStr(seatType);
            pTrainList.setmActivityFrom(flag);
        }
    }

    @Override
    protected void findViews() {
        headerView = View.inflate(this, R.layout.layout_tain_list_header, null);
        header_route = (TextView) headerView.findViewById(R.id.train_list_header_route);
        header_time = (TextView) headerView.findViewById(R.id.train_list_header_time);
        header_price = (TextView) headerView.findViewById(R.id.train_list_header_price);
    }

    @Override
    protected void initView() {
        calendar.setText(pTrainList.getStartdate());
        checkEnable();
        lv.setEmptyView(empty_view);
        empty_view.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initListener() {
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
                        pTrainList.setOrderType(-pTrainList.getOrderType());
                        changeText(rb, finalI);
                    } else {
                        pTrainList.setOrderType(1);
                        changeText(rb, finalI);
                    }
                    currentButton = finalI;
                    pTrainList.filtList();
                }
            });
        }
        lv.setOnItemClickListener(this);
    }

    private void changeText(RadioButton rb, int finalI) {
        if (finalI == 0) {
            rb.setText(pTrainList.getOrderType() == -1 ? HIGH2LOW : LOW2HIGH);
        } else if (finalI == 1) {
            rb.setText(pTrainList.getOrderType() == -1 ? LATE2EARLY : EARLY2LATE);
        } else {
            rb.setText(pTrainList.getOrderType() == -1 ? LONG2SHORT : SHORT2LONG);
        }
    }

    private void setRGListener(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton child = (RadioButton) group.getChildAt(i);
            if (child.getId() == checkedId) {
                child.setTextColor(getResources().getColor(R.color.appTheme1));
                switch (i) {
                    case 0:
                        pTrainList.setPriceOrder(true);
                        break;
                    case 1:
                        pTrainList.setStartTimeOrder(true);
                        break;
                    case 2:
                        pTrainList.setRunTimeOrder(true);
                        break;
                }
            } else {
                child.setTextColor(getResources().getColor(R.color.color_666));
                switch (i) {
                    case 0:
                        pTrainList.setPriceOrder(false);
                        child.setText(LOW2HIGH);
                        break;
                    case 1:
                        child.setText(EARLY2LATE);
                        pTrainList.setStartTimeOrder(false);
                        break;
                    case 2:
                        child.setText(SHORT2LONG);
                        pTrainList.setRunTimeOrder(false);
                        break;
                }
            }
        }
        pTrainList.filtList();
    }


    @Override
    protected void setViews() {
    }


    private boolean checkEnable() {
        if (pTrainList.getStartdate().equals(TimeUtils.getToday())) {
            before.setTextColor(Color.parseColor("#666666"));
            return false;
        } else {
            before.setTextColor(Color.parseColor("#333333"));
            return true;
        }
    }

    @Override
    protected void getData() {
        pTrainList.getTrainList();
    }


    @Override
    public void setAdapter(BaseAdapter adapter) {
        lv.setAdapter(adapter);
        freshTitle();
    }

    public void freshTitle() {
        title.setTitle(MyApplication.fromCityName + "-" + MyApplication.toCityName);
        title.setSubTitle(this, "共" + pTrainList.getList_filted().size() + "条");
    }

    @Override
    public void setFiltState(boolean b) {
        filt.setChecked(b);
    }

    @Override
    public void setEmptyView() {
        List<TrainBean.DBean> list_filted = pTrainList.getList_filted();
        empty_view.setVisibility(list_filted == null || list_filted.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setCalenderDate(String startdate) {
        calendar.setText(startdate);
    }

    @OnClick({R.id.train_list_after, R.id.train_list_before, R.id.train_list_calendar, R.id.train_list_filt})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.train_list_after:
                pTrainList.setStartdate(TimeUtils.getTomorrow(pTrainList.getStartdate()), false);
                checkEnable();
                break;
            case R.id.train_list_before:
                if (checkEnable()) {
                    pTrainList.setStartdate(TimeUtils.getYestoday(pTrainList.getStartdate()), false);
                } else {
                    ToastUtils.showTextToast("购票时间不能小于当天");
                }
                break;
            case R.id.train_list_calendar:
                pTrainList.jumpActivity(Constant.ACTIVITY_CALENDAR_FLAG, 0);
                break;
            case R.id.train_list_filt:
                pTrainList.showFilterPop();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_TRAIN_LIST_FLAG &&
                resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            pTrainList.setStartdate(data.getStringExtra(CldActivity.KEY_RESULT_FIRST), false);
            checkEnable();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (pTrainList.getmActivityFrom() == Constant.ACTIVITY_ALTER_TRAIN_HOME_FLAG) {//改签
            pTrainList.jumpActivity(Constant.ACTIVITY_ALTER_TRAIN_DETAIL_FLAG, position);
        } else {
            pTrainList.jumpActivity(Constant.ACTIVITY_TRAIN_DETAIL_FLAG, position);
        }
    }
}
