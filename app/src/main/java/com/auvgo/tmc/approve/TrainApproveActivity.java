package com.auvgo.tmc.approve;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.TrainOrderDetailPsgAdapter;
import com.auvgo.tmc.approve.interfaces.ViewManager_approve;
import com.auvgo.tmc.approve.p.PTrainApprove;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.base.MyList;
import com.auvgo.tmc.train.bean.ComSettingBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;

public class TrainApproveActivity extends BaseActivity implements ViewManager_approve {

    //    @ViewById(R.id.approve_order_ticketNo)
    TextView ticketNo;
    //    @ViewById(R.id.approve_order_orderNo)
    TextView orderNo;
    //    @ViewById(R.id.approve_order_state)
    TextView state;
    //    @ViewById(R.id.approve_order_contact)
    TextView contact;
    //    @ViewById(R.id.approve_order_tel)
    TextView tel;
    //    @ViewById(R.id.approve_order_email)
    TextView email;
    //    @ViewById(R.id.approve_order_applyNo)
    TextView applyNo;
    //    @ViewById(R.id.approve_order_costCenter)
    TextView costCenter;
    //    @ViewById(R.id.approve_order_item)
    TextView item;//项目
    //    @ViewById(R.id.approve_order_weiItem)
    TextView weiItem;//违背项目
    //    @ViewById(R.id.approve_order_reason)
    TextView chailvReason;
    //    @ViewById(R.id.approve_order_weiReason)
    TextView weiReason;//违背原因
    //    @ViewById(R.id.approve_order_priceall)
    TextView totalprice;//总价
    //    @ViewById(R.id.approve_order_commit)
    TextView commit;//同意按钮
    //    @ViewById(R.id.approve_order_cancle)
    TextView refuse;//拒绝按钮
    //    @ViewById(R.id.approve_order_approve_lv)
    ListView lv_approves;
    //    @ViewById(R.id.approve_order_psgs_lv)
    ListView lv_psgs;
    //    @ViewById(R.id.approve_order_item_approveStatus)
    LinearLayout item_approve_state;
    //    @ViewById(R.id.approve_order_detail_container)
    ViewGroup container;
    //    @ViewById(R.id.train_order_detail_cover)
    View cover;
    //    @ViewById(R.id.approve_sv)
    ScrollView sv;
    View item_costCenter;
    View item_applyNo;
    View item_project;
    private PTrainApprove pTrainApprove;
    private int h;
    private boolean b = true;//控制windowfocuschange

    @Override
    protected int getLayoutId() {
        return R.layout.activity_approve;
    }

    @Override
    protected void initData() {
        pTrainApprove = new PTrainApprove(this, this);
        Intent intent = getIntent();
        pTrainApprove.setmOrderNo(intent.getStringExtra("orderNo"));
        pTrainApprove.setmOrderType(intent.getStringExtra("orderType"));
    }

    @Override
    protected void findViews() {
        ticketNo = (TextView) findViewById(R.id.approve_order_ticketNo);
        orderNo = (TextView) findViewById(R.id.approve_order_orderNo);
        state = (TextView) findViewById(R.id.approve_order_state);
        contact = (TextView) findViewById(R.id.approve_order_contact);
        tel = (TextView) findViewById(R.id.approve_order_tel);
        email = (TextView) findViewById(R.id.approve_order_email);
        applyNo = (TextView) findViewById(R.id.approve_order_applyNo);
        costCenter = (TextView) findViewById(R.id.approve_order_costCenter);
        item = (TextView) findViewById(R.id.approve_order_item);
        weiItem = (TextView) findViewById(R.id.approve_order_weiItem);
        chailvReason = (TextView) findViewById(R.id.approve_order_reason);
        weiReason = (TextView) findViewById(R.id.approve_order_weiReason);
        totalprice = (TextView) findViewById(R.id.approve_order_priceall);
        commit = (TextView) findViewById(R.id.approve_order_commit);
        refuse = (TextView) findViewById(R.id.approve_order_cancle);
        lv_approves = (ListView) findViewById(R.id.approve_order_approve_lv);
        lv_psgs = (ListView) findViewById(R.id.approve_order_psgs_lv);
        item_approve_state = (LinearLayout) findViewById(R.id.approve_order_item_approveStatus);
        container = (ViewGroup) findViewById(R.id.approve_order_detail_container);
        cover = findViewById(R.id.train_order_detail_cover);
        sv = (ScrollView) findViewById(R.id.approve_sv);
        item_costCenter = findViewById(R.id.approve_order_item_costCenter);
        item_applyNo = findViewById(R.id.approve_order_item_applyNo);
        item_project = findViewById(R.id.approve_order_item_project);
    }

    @Override
    protected void initView() {
        cover.setVisibility(View.VISIBLE);
        lv_approves.setFocusable(false);
        findViewById(R.id.plane_detail_food).setVisibility(View.GONE);

        setCostCenterAndProjectVisibility(item_costCenter, item_project);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void setViews() {
    }

    @Override
    protected void getData() {
        super.getData();
        pTrainApprove.getData();
    }

    public void onCommitClick(View view) {
        LogFactory.d("onCommitClick");
        pTrainApprove.doAgree();
    }

    public void onRefuseClick(View view) {
        LogFactory.d("onRefuseClick");
        pTrainApprove.doRefuse();
    }

    public void showPriceDetail(View view) {
        pTrainApprove.showPriceDialog(h);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (b) {
//            b = false;
//            h = commit.getHeight();
//            sv.scrollTo(0, 0);
//            LogFactory.d("commitheight---->", h + "");
//        }
    }

    @Override
    public void updateViews(TrainOrderDetailBean mBean) {
        int status = mBean.getApprovestatus();
        String stateName = MUtils.getApproveStateByCode(status);
        int s = pTrainApprove.checkState(mBean);
        commit.setVisibility(s == 1 ? View.VISIBLE : View.GONE);
        refuse.setVisibility(s == 1 ? View.VISIBLE : View.GONE);
        ticketNo.setText("取票号:" + mBean.getOutTicketBillno());
        orderNo.setText("订单号:" + mBean.getOrderNo());
        state.setText(stateName);
        contact.setText(mBean.getLinkName());
        tel.setText(mBean.getLinkPhone());
        if (TextUtils.isEmpty(mBean.getLinkEmail())) {
            email.setVisibility(View.GONE);
        } else {
            email.setText(mBean.getLinkEmail());
        }
        applyNo.setText(mBean.getShenqingno());
        chailvReason.setText(mBean.getChailvitem());
        costCenter.setText(mBean.getCostname());
        item.setText(mBean.getProname());
        if (isWei(mBean.getWeibeiflag())) {
            String bookpolicy = mBean.getBookpolicy();
            weiItem.setText(bookpolicy.endsWith(",") ? bookpolicy.substring(0, bookpolicy.length() - 1) : bookpolicy);
            weiReason.setText(mBean.getWbreason());
        } else {
            weiReason.setVisibility(View.GONE);
            weiItem.setVisibility(View.GONE);
        }
        totalprice.setText(String.valueOf(mBean.getTotalprice()));
        lv_approves.setAdapter(new ApproveStateAdapter(this, mBean.getApproves()));
        lv_psgs.setAdapter(new TrainOrderDetailPsgAdapter(this, mBean.getUsers()));

        // TODO: 2016/12/1 需要根据情况判断
        updateTrainDetailCard(mBean);
        setViewState(mBean);
    }

    private void setViewState(TrainOrderDetailBean mBean) {
        int state = pTrainApprove.checkState(mBean);
        if (state < 0) {
            commit.setVisibility(View.GONE);
            refuse.setVisibility(View.GONE);
            if (state == -1) {
                this.state.setText("未识别");
            } else if (state == -2) {
                this.state.setText("等待上级审批中");
            } else if (state == -3) {
                this.state.setText("已通过");
            } else {
                this.state.setText("已拒绝");
            }
        } else {
            commit.setVisibility(View.VISIBLE);
            refuse.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(pTrainApprove.getmBean().getShenqingno())) {
            item_applyNo.setVisibility(View.GONE);
        }
    }

    //更新车次详情
    private void updateTrainDetailCard(TrainOrderDetailBean mBean) {
        TextView trainCode = (TextView) container.findViewById(R.id.plane_detail_airline);
        TextView notice = (TextView) container.findViewById(R.id.plane_detail_food);
        TextView fromStation = (TextView) container.findViewById(R.id.train_detail_start_station);
        TextView toStation = (TextView) container.findViewById(R.id.train_detail_end_station);
        TextView fromTime = (TextView) container.findViewById(R.id.train_detail_start_time);
        TextView toTime = (TextView) container.findViewById(R.id.train_detail_end_time);
        TextView fromDate = (TextView) container.findViewById(R.id.train_detail_start_date);
        TextView toDate = (TextView) container.findViewById(R.id.train_detail_end_date);
        TextView time = (TextView) container.findViewById(R.id.train_detail_time);
        TextView costTime = (TextView) container.findViewById(R.id.train_detail_runtime);
        TrainOrderDetailBean.RouteBean route = mBean.getRoute();
        trainCode.setText(route.getTrainCode());
        fromStation.setText(route.getFromStation());
        toStation.setText(route.getArriveStation());
        fromTime.setText(route.getFromTime());
        toTime.setText(route.getArriveTime());
        time.setText(mBean.getUsers().get(0).getSeatType());
        time.setTextColor(Color.BLACK);
        fromDate.setText(route.getTravelTime().substring(5) + " " + TimeUtils.getTomorrowWeekDay(route.getTravelTime()));
        toDate.setText(TimeUtils.caculateDate(route.getTravelTime(), route.getCosttime(), "MM-dd")
                + " " + TimeUtils.getTomorrowWeekDay((TimeUtils.caculateDate(route.getTravelTime(),
                route.getCosttime(), "yyyy-MM-dd"))));
        costTime.setText(route.getRunTime() == null ? "--:--" : route.getRunTime().replace(":", "时") + "分");
    }

    private boolean isWei(int weibeiflag) {
        return weibeiflag == 1;
    }

//    private String getStateName(int status) {
//
//        switch (status) {
//            case 0:
//                return "未提交";
//            case 1:
//                return "已通过";
//            case 2:
//                return "已拒绝";
//            case 3:
//                return "无需审批";
//            case 4:
//                return "待审批";
//        }
//        return "未匹配？";
//    }
}
