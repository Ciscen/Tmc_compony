package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.TrainDetailPsgsAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.p.PTrainAlterApply;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.train.bean.requestbean.RequestAlterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyListView;
import com.auvgo.tmc.views.TrainDetailCardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainAlterConfirmActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.alter_train_confirm_ticketNo)
    TextView ticketNo;
    @BindView(R.id.alter_train_confirm_state)
    TextView state;
    @BindView(R.id.alter_train_confirm_fromCity)
    TextView startStation;
    @BindView(R.id.alter_train_confirm_toCity)
    TextView arriveStation;
    @BindView(R.id.alter_train_confirm_start_date)
    TextView startDate;
    @BindView(R.id.alter_train_confirm_start_time)
    TextView startTime;
    @BindView(R.id.alter_train_confirm_trainCode)
    TextView trainCode;
    @BindView(R.id.alter_train_confirm_seattype)
    TextView seattype;
    @BindView(R.id.alter_train_confirm_arrive_time)
    TextView arriveTime;
    @BindView(R.id.alter_train_confirm_arrive_date)
    TextView arriveDate;
    @BindView(R.id.alter_train_confirm_priceall)
    TextView price;
    @BindView(R.id.alter_train_confirm_cardView)
    TrainDetailCardView cv;
    @BindView(R.id.alter_train_confirm_psgs_lv)
    MyListView psgs_lv;
    @BindView(R.id.alter_train_confirm_commit)
    TextView commit;
    @BindView(R.id.alter_train_confirm_cover)
    View cover;
    @BindView(R.id.train_order_detail_click_bottom)
    View priceDetail;

    private List<TrainOrderDetailBean.UsersBean> psgs;
    private TrainBean.DBean mBean;//选中的火车票的实体
    private int seattypeposition;
    private TrainDetailPsgsAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_train_confirm;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        psgs = getAlterPsgs();
        adapter = new TrainDetailPsgsAdapter(this, false, psgs, R.layout.item_alter_psgs);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            mBean = (TrainBean.DBean) bundle.getSerializable("bean");
            seattypeposition = bundle.getInt("seattypeposition");
        }
    }

    private List<TrainOrderDetailBean.UsersBean> getAlterPsgs() {
        List<TrainOrderDetailBean.UsersBean> users = PTrainAlterApply.detailBean.getUsers();
        List<TrainOrderDetailBean.UsersBean> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isChecked()) {
                list.add(users.get(i));
            }
        }
        return list;
    }

    @Override
    protected void initView() {
        cover.setVisibility(View.VISIBLE);
        TrainOrderDetailBean detailBean = PTrainAlterApply.detailBean;
        ticketNo.setText(String.format("订单号：%s", detailBean.getOrderNo()));
        state.setText(MUtils.getOrgTicketStateByCode(detailBean.getStatus()));
        TrainOrderDetailBean.RouteBean route = detailBean.getRoute();
        startStation.setText(route.getFromStation());
        arriveStation.setText(route.getArriveStation());
        startDate.setText(route.getTravelTime().substring(5));
        startTime.setText(route.getFromTime());
        trainCode.setText(route.getTrainCode());
        seattype.setText(detailBean.getUsers().get(0).getSeatType());
        arriveTime.setText(route.getArriveTime());
        arriveDate.setText(TimeUtils.getDateByCostDays(route.getTravelTime().replace("-", ""), Integer.parseInt(route.getArriveDays()), "MM-dd"));
        price.setText(AppUtils.keepTwoSecimal2(String.valueOf(Float.valueOf(mBean.getCanBook().
                get(seattypeposition).get(2)) * psgs.size())));

        cv.setTraincode(mBean.getTrain_code());
        cv.setStart_date(TimeUtils.getDateByCostDays(mBean.getTrain_start_date(), 0, "MM-dd"));
        cv.setStart_station(mBean.getFrom_station_name());
        cv.setStart_time(mBean.getStart_time());
        cv.setEnd_date(TimeUtils.getDateByCostDays(mBean.getTrain_start_date(), Integer.parseInt(mBean.getArrive_days()), "MM-dd"));
        cv.setEnd_station(mBean.getTo_station_name());
        cv.setEnd_time(mBean.getArrive_time());
        cv.setSeatType(mBean.getCanBook().get(seattypeposition).get(0));
        cv.setRun_time(mBean.getRun_time().replace(":", "时") + "分");
        psgs_lv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        priceDetail.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.train_order_detail_click_bottom:
                break;
            case R.id.alter_train_confirm_commit:
                DialogUtil.showDialog(this, "提示", "取消", "确定", "您确定要提交改签申请吗？", new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        apply();
                    }
                });
                break;
        }
    }

    private void apply() {
        RequestAlterBean ab = new RequestAlterBean();
        ab.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        ab.setEmpid(String.valueOf(MyApplication.mUserInfoBean.getId()));
        ab.setOrderfrom("2");
        ab.setOrderno(PTrainAlterApply.detailBean.getOrderNo());
        ab.setReason("其他");
        ab.setUserids(getUsersIds());
        List<String> seatInfo = mBean.getCanBook().get(seattypeposition);
        ab.setSeatcode(seatInfo.get(3));
        ab.setAmount(Double.valueOf(seatInfo.get(2)));
        RequestAlterBean.RouteBean rb = new RequestAlterBean.RouteBean();
        rb.setArriveDays(mBean.getArrive_days());
        rb.setArriveStation(mBean.getTo_station_name());
        rb.setArriveStationCode(mBean.getTo_station_code());
        rb.setArriveTime(mBean.getArrive_time());
        rb.setCosttime(Integer.valueOf(mBean.getRun_time_minute()));
        rb.setFromStation(mBean.getFrom_station_name());
        rb.setFromStationCode(mBean.getFrom_station_code());
        rb.setFromTime(mBean.getStart_time());
        rb.setRunTime(mBean.getRun_time());
        rb.setSeatCode(seatInfo.get(3));
        rb.setSeatType(MUtils.getSeatTypeByCode(seatInfo.get(3)));
        rb.setTrainCode(mBean.getTrain_code());
        rb.setTravelTime(TimeUtils.changePattern(mBean.getTrain_start_date()));
        rb.setTrainNo(mBean.getTrain_no());
        ab.setRoute(rb);
        RetrofitUtil.doAlter(this, AppUtils.getJson(ab), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    final String orderNo = bean.getData();
                    if (TextUtils.isEmpty(orderNo)) {
                        showDialog("确定", "", "改签订单暂不能提交，请重新提交！", null);
                        return true;
                    }
                    showDialog("知道了", "", getString(R.string.alter_success_notice),
                            new MyDialog.OnButtonClickListener() {
                                @Override
                                public void onLeftClick() {
                                    jumpToOrderList(Constant.TRAIN);
                                    jumpToOrderDetail(context, orderNo, AlterOrderDetailActivity.class);
                                }

                                @Override
                                public void onRightClick() {

                                }
                            });
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private List<String> getUsersIds() {
        List<TrainOrderDetailBean.UsersBean> users = PTrainAlterApply.detailBean.getUsers();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isChecked()) {
                list.add(users.get(i).getUserId() + "");
            }
        }
        return list;
    }
}
