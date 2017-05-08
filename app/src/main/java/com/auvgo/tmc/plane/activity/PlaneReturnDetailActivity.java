package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.PlaneOrderDetailPsgAdapter;
import com.auvgo.tmc.adapter.PlaneReturnOrderDetailPsgAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.plane.bean.PlaneReturnDetailBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.PlaneDetailCardView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auvgo.tmc.constants.Constant.ApproveStatus.*;


public class PlaneReturnDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView orderNo_tv, tuipiaoReason_tv, status_tv, contact_tv, tel_tv, email_tv, price_tv, gotoOrg;
    private PlaneDetailCardView cv;
    private ListView lv_psgs, lv_approve;
    private ItemView insurance_iv, applyNo_iv, costCenter_iv, project_iv, reason_iv, weiItem_iv, weiReason_iv;
    private View approveInfo_vg, approveStatus_vg, priceDetail_vg;
    private String orderNo;
    private PlaneOrderDetailPsgAdapter adapter;
    private ApproveStateAdapter adapter_approve_state;
    private PlaneReturnDetailBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_return_detail;
    }

    @Override
    protected void initData() {
        orderNo = getIntent().getStringExtra("orderNo");
    }

    @Override
    protected void findViews() {
        orderNo_tv = (TextView) findViewById(R.id.plane_return_detail_orderno);
        tuipiaoReason_tv = (TextView) findViewById(R.id.plane_return_detail_tuipiao_reason);
        status_tv = (TextView) findViewById(R.id.plane_return_detail_status);
        cv = (PlaneDetailCardView) findViewById(R.id.plane_return_detail_cv);
        priceDetail_vg = findViewById(R.id.plane_return_detail_pricedetail);
        price_tv = (TextView) findViewById(R.id.plane_return_detail_priceall);
        contact_tv = (TextView) findViewById(R.id.plane_return_detail_contact);
        tel_tv = (TextView) findViewById(R.id.plane_return_detail_tel);
        email_tv = (TextView) findViewById(R.id.plane_return_detail_email);
        insurance_iv = (ItemView) findViewById(R.id.plane_return_detail_insurance);
        lv_psgs = (ListView) findViewById(R.id.plane_return_detail_psgs_lv);
        applyNo_iv = (ItemView) findViewById(R.id.plane_return_detail_applyNo);
        costCenter_iv = (ItemView) findViewById(R.id.plane_return_detail_costCenter);
        project_iv = (ItemView) findViewById(R.id.plane_return_detail_project);
        reason_iv = (ItemView) findViewById(R.id.plane_return_detail_reason);
        weiItem_iv = (ItemView) findViewById(R.id.plane_return_detail_weiItem);
        weiReason_iv = (ItemView) findViewById(R.id.plane_return_detail_weiReason);
        lv_approve = (ListView) findViewById(R.id.plane_return_detail_approve_lv);
        approveStatus_vg = findViewById(R.id.plane_return_detail_item_approveStatus);
        approveInfo_vg = findViewById(R.id.plane_return_detail_approveinfo);
        gotoOrg = (TextView) findViewById(R.id.plane_return_detail_gotoOrigin);
    }

    @Override
    protected void initView() {
        lv_psgs.setFocusable(false);
    }

    @Override
    protected void initListener() {
        priceDetail_vg.setOnClickListener(this);
        gotoOrg.setOnClickListener(this);
    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        super.getData();
        Map<String, String> map = new HashMap<>();
        map.put("tporderno", orderNo);
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        RetrofitUtil.getPlaneReturnDetail(this, AppUtils.getJson(map), PlaneReturnDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (PlaneReturnDetailBean) o;
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

        /*
        审批信息的显示隐藏
         */
        int approvestatus = mBean.getApprovestatus();
        int status = mBean.getStatus();
        if (approvestatus == APPROVE_STATUS_WUXUSHENPI) {
            approveInfo_vg.setVisibility(View.GONE);
            approveStatus_vg.setVisibility(View.GONE);
        }

        orderNo_tv.setText(String.format("订单号:%s", mBean.getTporderno()));
        status_tv.setText(MUtils.getReturnStateByCode(mBean.getStatus()));
        tuipiaoReason_tv.setText(mBean.getTpreason());
        List<PlaneReturnDetailBean.TuipiaoPassengersBean> tuipiaoPassengers = mBean.getTuipiaoPassengers();
        contact_tv.setText(mBean.getLinkName());
        tel_tv.setText(mBean.getLinkPhone());

        String linkEmail = mBean.getLinkEmail();
        if (TextUtils.isEmpty(linkEmail)) {
            email_tv.setVisibility(View.GONE);
        } else {
            email_tv.setText(linkEmail);
        }
        String bxName = tuipiaoPassengers.get(0).getBxName();
        insurance_iv.setContent(TextUtils.isEmpty(bxName) ? "- -" : bxName);
        lv_psgs.setAdapter(new PlaneReturnOrderDetailPsgAdapter(this, mBean.getTuipiaoPassengers()));
        price_tv.setText(AppUtils.keepNSecimal(
                mBean.getTuipiaoPassengers().get(0).getTpprice() * mBean.getTuipiaoPassengers().size() + "",
                2));
        PlaneReturnDetailBean.RoutesBean rb = mBean.getRoutes().get(0);
        cv.setAirline(rb.getCarriername() + rb.getAirline() + "|" + rb.getPlanestyle());
        cv.setStart_date(rb.getDeptdate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(rb.getDeptdate()));
        cv.setEnd_date(rb.getArridate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(rb.getArridate()));
        cv.setStart_time(rb.getDepttime());
        cv.setEnd_time(rb.getArritime());
        cv.setOrgname(rb.getOrgname() + rb.getDeptterm());
        cv.setArriname(rb.getArriname() + rb.getArriterm());
        cv.setCangwei2(rb.getCodeDes() + "/" + rb.getDisdes());
//        applyNo_iv.setContent(order.getShenqingno());
//        costCenter_iv.setContent(order.getCostname());
//        project_iv.setContent(order.getProname());
//        reason_iv.setContent(order.getChailvitem());
//        weiItem_iv.setContent(order.getBookpolicy());
//        weiReason_iv.setContent(order.getWbreason());
//        price_tv.setText(String.valueOf(order.getTotalprice()));
//        if (order.getApproves() == null || order.getApproves().size() == 0) {
//            approveStatus_vg.setVisibility(View.GONE);
//        } else {
//            lv_approve.setAdapter(new ApproveStateAdapter(this, order.getApproves()));
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_return_detail_pricedetail:
                break;
            case R.id.plane_return_detail_gotoOrigin:
                if (mBean != null) {
                    Intent intent = new Intent(this, PlaneOrderDetailActivity.class);

                    intent.putExtra("orderNo", mBean.getOldorderno());
                    startActivity(intent);
                }
                break;
        }
    }
}
