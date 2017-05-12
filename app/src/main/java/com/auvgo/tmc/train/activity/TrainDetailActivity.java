package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.TrainDetailAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.train.interfaces.ViewManager_traindetail;
import com.auvgo.tmc.p.PTrainDetail;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainDetailActivity extends BaseActivity implements ViewManager_traindetail, View.OnClickListener, ExpandableListView.OnChildClickListener {

    @BindView(R.id.plane_detail_airline)
    TextView traincode;
    @BindView(R.id.plane_detail_food)
    TextView notice;
    @BindView(R.id.train_detail_start_station)
    TextView start_station;
    @BindView(R.id.train_detail_end_station)
    TextView end_station;
    @BindView(R.id.train_detail_start_time)
    TextView start_time;
    @BindView(R.id.train_detail_start_date)
    TextView start_date;
    @BindView(R.id.train_detail_end_time)
    TextView end_time;
    @BindView(R.id.train_detail_end_date)
    TextView end_date;
    @BindView(R.id.train_detail_time)
    TextView time;
    @BindView(R.id.train_detail_runtime)
    TextView run_time;
    @BindView(R.id.train_detail_elv)
    ExpandableListView elv;

    private PTrainDetail pTrainDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_detail;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        pTrainDetail = new PTrainDetail(this, this);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            pTrainDetail.setmBean((TrainBean.DBean) bundle.getSerializable("bean"));
            pTrainDetail.setFromCode(bundle.getString("from"));
            pTrainDetail.setToCode(bundle.getString("to"));
        }
    }


    @Override
    protected void initView() {
        TrainBean.DBean dBean = pTrainDetail.getmBean();
        traincode.setText(dBean.getTrain_code());
        start_station.setText(dBean.getFrom_station_name());
        start_time.setText(dBean.getStart_time());
        end_station.setText(dBean.getTo_station_name());
        String date = dBean.getTrain_start_date();
        start_date.setText(TimeUtils.getDateByCostDays(date, 0, "MM-dd") + "  " + TimeUtils.getTomorrowWeekDay(date));
        String endDate = TimeUtils.getDateByCostDays(date, Integer.parseInt(dBean.getArrive_days()), "MM-dd");
        end_date.setText(endDate + "  " + TimeUtils.getTomorrowWeekDay(TimeUtils.getDateByCostDays(date,
                Integer.parseInt(dBean.getArrive_days()), "yyyy-MM-dd")));
        end_time.setText(dBean.getArrive_time());
        run_time.setText(dBean.getRun_time().replace(":", "时") + "分钟");
    }

    @Override
    protected void initListener() {
        time.setOnClickListener(this);
        elv.setOnChildClickListener(this);
        notice.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
        pTrainDetail.setadapter();
    }

    @Override
    public void setadapter(TrainDetailAdapter adapter) {
        elv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.train_detail_time:
                pTrainDetail.getTrainTime();
                break;
            case R.id.plane_detail_food:
                pTrainDetail.jumpToNotice();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {

        boolean b = MUtils.isCanbook(pTrainDetail.getmBean().getCanBook().get(groupPosition).get(3),
                pTrainDetail.getmBean().getTrain_code());
        if (b) {
            if (MUtils.isSeatWei(pTrainDetail.getmBean().getCanBook().get(groupPosition).get(3))) {
                DialogUtil.showDialog(this, "违背提示", "取消", "继续", "您违背了" +
                        MUtils.getWeibeiItemByTrainCode(pTrainDetail.getmBean().getTrain_code().substring(0, 1))
                        + "的标准，请问继续预订吗", new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        pTrainDetail.jumpActivity(groupPosition, childPosition);
                    }
                });
            } else {
                pTrainDetail.jumpActivity(groupPosition, childPosition);
            }
        } else {
            DialogUtil.showDialog(this, "违背提示", "取消", "", "不允许预订该违背车次", null);
        }
        return false;
    }
}
