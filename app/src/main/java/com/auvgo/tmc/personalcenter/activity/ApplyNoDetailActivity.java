package com.auvgo.tmc.personalcenter.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.personalcenter.adapter.ApplyNoDetailAdapter;
import com.auvgo.tmc.personalcenter.bean.ApplyNoDetailBean;
import com.auvgo.tmc.personalcenter.bean.RequestApplyNoDetailBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;

import java.sql.Time;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ApplyNoDetailActivity extends BaseActivity {
    @BindView(R.id.apply_no_detail_no)
    TextView no;
    @BindView(R.id.apply_no_detail_name)
    TextView name;
    @BindView(R.id.apply_no_detail_psgs)
    TextView psgs;
    @BindView(R.id.apply_no_detail_date)
    TextView date;
    @BindView(R.id.apply_no_detail_traffic_lv)
    ListView lv1;
    @BindView(R.id.apply_no_detail_hotel_lv)
    ListView lv2;
    @BindView(R.id.apply_no_detail_reason_tv)
    TextView reason;
    @BindView(R.id.apply_no_detail_item_hotel)
    View item_hotel;
    @BindView(R.id.apply_no_detail_item_traffic)
    View item_traffic;
    private String approvalno;
    private ApplyNoDetailBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_no_detail;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        approvalno = intent.getStringExtra("approvalno");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        RequestApplyNoDetailBean b = new RequestApplyNoDetailBean();
        b.setApprovalno(approvalno);
        b.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        RetrofitUtil.getApplyNoDetail(this, AppUtils.getJson(b), ApplyNoDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == CODE_SUCCESS) {
                    mBean = (ApplyNoDetailBean) o;
                    updateViews();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void updateViews() {
        no.setText(mBean.getApprovalno());
        name.setText(MyApplication.mUserInfoBean.getName());
        psgs.setText(mBean.getApprovalempname());
        date.setText(TimeUtils.getdetailTime(mBean.getTravelstart()));
        lv1.setAdapter(new ApplyNoDetailAdapter(this, mBean.getAppformTravels()));
        lv2.setAdapter(new ApplyNoDetailAdapter(this, mBean.getAppformHotels()));
        reason.setText(mBean.getTravelreason());
        item_traffic.setVisibility(mBean.getAppformTravels().size() == 0 ? View.GONE : View.VISIBLE);
        item_hotel.setVisibility(mBean.getAppformHotels().size() == 0 ? View.GONE : View.VISIBLE);
    }
}
