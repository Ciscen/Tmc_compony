package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.AlterTrainDetailAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.p.PTrainAlterApply;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyListView;
import com.auvgo.tmc.views.TrainDetailCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class TrainAlterDetailActivity extends BaseActivity {
    @BindView(R.id.alter_train_detail_carView)
    TrainDetailCardView cv;
    @BindView(R.id.alter_train_detail_lv)
    MyListView lv;

    private TrainBean.DBean mBean;
    private AlterTrainDetailAdapter adapter;
    private String fromCode, toCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_train_detail;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mBean = (TrainBean.DBean) bundle.getSerializable("bean");
        fromCode = bundle.getString("from");
        toCode = bundle.getString("to");
        adapter = new AlterTrainDetailAdapter(this, mBean.getCanBook());
    }

    @Override
    protected void initView() {
        cv.setTraincode(mBean.getTrain_code());
        cv.setStart_date(TimeUtils.getDateByCostDays(mBean.getTrain_start_date(), 0, "MM-dd"));
        cv.setStart_station(mBean.getFrom_station_name());
        cv.setStart_time(mBean.getStart_time());
        cv.setEnd_date(TimeUtils.getDateByCostDays(mBean.getTrain_start_date(), Integer.parseInt(mBean.getArrive_days()), "MM-dd"));
        cv.setEnd_station(mBean.getTo_station_name());
        cv.setEnd_time(mBean.getArrive_time());
        cv.setRun_time(mBean.getRun_time().replace(":", "时") + "分");
        lv.setAdapter(adapter);
    }

    @Override
    protected void setViews() {

    }

    @OnItemClick(R.id.alter_train_detail_lv)
    public void onItemClick(final int position) {
        String seatType = mBean.getCanBook().get(position).get(3);
        boolean b = MUtils.isCanbook(seatType, mBean.getTrain_code());
//        TrainOrderDetailBean detailBean = PTrainAlterApply.detailBean;
//        if (detailBean == null || !detailBean.getUsers().get(0).getSeatCode().equals(seatType)) {
//            ToastUtils.showTextToast("不能改签不同席别");
//            return;
//        }
//        if (MUtils.isWoPu(seatType)) {
//            ToastUtils.showTextToast("不能改签卧铺");
//            return;
//        }
        if (b) {
            if (MUtils.isSeatWei(seatType)) {
                DialogUtil.showDialog(this, "违背提示", "取消", "继续", "您违背了" +
                        MUtils.getWeibeiItemByTrainCode(mBean.getTrain_code().substring(0, 1))
                        + "的标准，请问继续预订吗", new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        jump(position);
                    }
                });
            } else {
                jump(position);
            }
        } else {
            DialogUtil.showDialog(this, "违背提示", "取消", "", "不允许预订该违背车次", null);
        }
    }

    private void jump(int position) {
        Intent intent = new Intent(this, TrainAlterConfirmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", mBean);//当前车次的实体类
        bundle.putInt("seattypeposition", position);//所选中的席别
        bundle.putString("to", toCode);//到达城市的编码
        bundle.putString("from", fromCode);//出发城市的编码
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }
}
