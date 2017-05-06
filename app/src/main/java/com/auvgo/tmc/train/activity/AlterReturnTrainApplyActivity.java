package com.auvgo.tmc.train.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.p.PTrainAlterApply;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainAlterConfirm;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.MyListView;
import com.auvgo.tmc.views.TitleView;
import com.auvgo.tmc.views.TrainDetailCardView;

public class AlterReturnTrainApplyActivity extends BaseActivity
        implements ViewManager_trainAlterConfirm, AdapterView.OnItemClickListener {
    private TrainDetailCardView cardView;
    private MyListView lv;
    private PTrainAlterApply pTrainAlterApply;
    private TextView tv1, tv2, tv3;
    private TitleView titleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_alter_apply;
    }

    @Override
    protected void initData() {
        pTrainAlterApply = new PTrainAlterApply(this, this, getIntent().getBundleExtra("bundle"));
    }

    @Override
    protected void findViews() {
        cardView = (TrainDetailCardView) findViewById(R.id.train_alter_confirm_cv);
        lv = (MyListView) findViewById(R.id.train_alter_confirm_lv);
        tv1 = (TextView) findViewById(R.id.train_alter_confirm_tv1);
        tv2 = (TextView) findViewById(R.id.train_alter_confirm_tv2);
        tv3 = (TextView) findViewById(R.id.train_alter_confirm_tv3);
        titleView = (TitleView) findViewById(R.id.train_alter_confirm_title);
    }

    @Override
    protected void initView() {
        lv.setAdapter(pTrainAlterApply.getAdapter());
        pTrainAlterApply.setCardView();
        if (pTrainAlterApply.getmAction() == Constant.ACTION_RETURN) {
            titleView.setTitle("申请退票");
            tv1.setText("请选择需要退票的乘客");
            tv2.setText("需要退票的车次");
            tv3.setText("申请退票");
        }
    }

    @Override
    protected void initListener() {
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void setViews() {
    }

    //改签按钮
    public void alter(View view) {
        if (pTrainAlterApply.getmAction() == Constant.ACTION_ALTER) {
            pTrainAlterApply.alter();
        } else {
            pTrainAlterApply.returnTicket();
        }
    }

    @Override
    public void updateCard(TrainOrderDetailBean detailBean) {
        TrainOrderDetailBean.RouteBean route = detailBean.getRoute();
        cardView.setTraincode(route.getTrainCode());
        cardView.setStart_date(route.getTravelTime().substring(5) + " " + TimeUtils.getTomorrowWeekDay(route.getTravelTime()));
        cardView.setStart_station(route.getFromStation());
        cardView.setStart_time(route.getFromTime());
        cardView.setEnd_date(TimeUtils.caculateDate(route.getTravelTime(), route.getCosttime(), "MM-dd")
                + " " + TimeUtils.getTomorrowWeekDay((TimeUtils.caculateDate(route.getTravelTime(),
                route.getCosttime(), "yyyy-MM-dd"))));
        cardView.setEnd_station(route.getArriveStation());
        cardView.setEnd_time(route.getArriveTime());
        cardView.setRun_time(route.getRunTime().replace(":", "时") + "分");
        cardView.setSeatType(detailBean.getUsers().get(0).getSeatType());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pTrainAlterApply.onItemClick(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pTrainAlterApply.setDetailBean(null);
    }
}
