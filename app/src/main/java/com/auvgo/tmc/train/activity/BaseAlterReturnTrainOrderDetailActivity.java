package com.auvgo.tmc.train.activity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyListView;
import com.auvgo.tmc.views.TitleView;
import com.auvgo.tmc.views.TrainDetailCardView;

public abstract class BaseAlterReturnTrainOrderDetailActivity extends BaseActivity implements View.OnClickListener {
    protected Context context;
    protected String orderNoStr;
    protected TrainDetailCardView cv;
    protected View cover, gotoOrigin, pay_ll, pay_bt, cancle_bt;
    protected TextView orderNo, state, ticketNo, startStation, arriveStation, startDate,
            startTime, trainCode, seattype, arriveTime, arriveDate, price_name, price, alter_price, pay_price;
    protected TextView psg_name, psg_ticketNo;
    protected MyListView psgs_lv;
    protected View item_oldRouteInfo, item_price, item_psg, item_psgs, alter_price_ll;
    protected TitleView titleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_return_train_order_detail;
    }

    @Override
    protected void initData() {
        context = this;
        orderNoStr = getIntent().getStringExtra("orderNo");
    }

    @Override
    protected void findViews() {
        cv = (TrainDetailCardView) findViewById(R.id.alter_train_detail_cardView);
        cover = findViewById(R.id.return_train_confirm_cover);
        orderNo = (TextView) findViewById(R.id.alter_train_detail_orderNo);
        state = (TextView) findViewById(R.id.alter_train_detail_state);
        ticketNo = (TextView) findViewById(R.id.alter_train_detail_ticketNo);
        startStation = (TextView) findViewById(R.id.alter_train_detail_fromCity);
        arriveStation = (TextView) findViewById(R.id.alter_train_detail_toCity);
        startDate = (TextView) findViewById(R.id.alter_train_detail_start_date);
//        alter_price = (TextView) findViewById(R.id.train_alter_price);
//        alter_price_ll = findViewById(R.id.train_alter_price_ll);
        startTime = (TextView) findViewById(R.id.alter_train_detail_start_time);
        trainCode = (TextView) findViewById(R.id.alter_train_detail_trainCode);
        seattype = (TextView) findViewById(R.id.alter_train_detail_seattype);
        arriveTime = (TextView) findViewById(R.id.alter_train_detail_arrive_time);
        arriveDate = (TextView) findViewById(R.id.alter_train_detail_arrive_date);
        titleView = (TitleView) findViewById(R.id.return_train_order_title);
        item_price = findViewById(R.id.alter_train_detail_item_price);
        item_oldRouteInfo = findViewById(R.id.alter_train_detail_item_oldRouteInfo);
        price = (TextView) findViewById(R.id.alter_train_detail_price);
        gotoOrigin = findViewById(R.id.alter_train_detail_gotoOrigin);
        item_psg = findViewById(R.id.alter_train_detail_item_psg);
        item_psgs = findViewById(R.id.alter_train_detail_item_psgs);
        psgs_lv = (MyListView) findViewById(R.id.alter_train_detail_psgs_lv);
        psg_name = (TextView) findViewById(R.id.alter_train_detail_psg_name);
        psg_ticketNo = (TextView) findViewById(R.id.alter_train_detail_psg_ticketNo);
        price_name = (TextView) findViewById(R.id.textView2);
        pay_ll = findViewById(R.id.train_order_detail_click_bottom);
        pay_bt = findViewById(R.id.train_order_detail_button1);
        cancle_bt = findViewById(R.id.train_order_detail_button2);
        pay_price = (TextView) findViewById(R.id.train_order_detail_price_pay);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
        gotoOrigin.setOnClickListener(this);
        pay_bt.setOnClickListener(this);
        cancle_bt.setOnClickListener(this);
    }

    @Override
    protected void setViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alter_train_detail_gotoOrigin:
                gotoOrigin();
                break;
            case R.id.train_order_detail_button1:
                pay();
                break;
            case R.id.train_order_detail_button2:
                showDialog("取消", "确定", "确定取消改签吗？", new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {
                    }

                    @Override
                    public void onRightClick() {
                        cancel();
                    }
                });
                break;
        }
    }

    /*取消订单，子类继承*/
    public void cancel() {
    }

    /*支付订单，子类继承*/
    public void pay() {

    }

    protected abstract void gotoOrigin();


}
