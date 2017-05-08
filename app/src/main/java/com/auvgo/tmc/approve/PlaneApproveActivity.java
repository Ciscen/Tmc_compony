package com.auvgo.tmc.approve;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.approve.interfaces.IPlaneApprove;
import com.auvgo.tmc.approve.interfaces.IRouteBean;
import com.auvgo.tmc.approve.p.PPlaneApprove;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.plane.interfaces.ViewManager_PlaneOrderDetail;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.PlaneDetailCardView;

public class PlaneApproveActivity extends BaseActivity implements View.OnClickListener, ViewManager_PlaneOrderDetail {
    private TextView orderNo_tv, ticketStatus_tv, approveStatus_tv, priceAll_tv, button1_tv, button2_tv,
            contact_tv, tel_tv, email_tv;
    private ItemView insurance_iv, applyNo_iv, costCenter_iv, project_iv, reason_iv, weiItem_iv, weiReason_iv;
    private ListView psgs_lv, approve_lv;
    private PlaneDetailCardView cv;
    private View approveInfo_vg, approveStatus_vg, priceDetail_vg;
    private PPlaneApprove pPlaneApprove;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_order_detail;
    }

    @Override
    protected void initData() {
        pPlaneApprove = new PPlaneApprove(this, this);
        pPlaneApprove.init(getIntent());
    }

    @Override
    protected void findViews() {
        orderNo_tv = (TextView) findViewById(R.id.plane_order_detail_orderNo);
        ticketStatus_tv = (TextView) findViewById(R.id.plane_order_detail_ticket_status);
        approveStatus_tv = (TextView) findViewById(R.id.plane_order_detail_approve_status);
        cv = (PlaneDetailCardView) findViewById(R.id.plane_order_detail_cv);
        priceDetail_vg = findViewById(R.id.plane_order_detail_click_pricedetail);
        priceAll_tv = (TextView) findViewById(R.id.plane_order_detail_priceall);
        button1_tv = (TextView) findViewById(R.id.plane_order_detail_button1);
        button2_tv = (TextView) findViewById(R.id.plane_order_detail_button2);
        contact_tv = (TextView) findViewById(R.id.plane_order_detail_contact);
        tel_tv = (TextView) findViewById(R.id.plane_order_detail_tel);
        email_tv = (TextView) findViewById(R.id.plane_order_detail_email);
        insurance_iv = (ItemView) findViewById(R.id.plane_order_detail_ensurance);
        applyNo_iv = (ItemView) findViewById(R.id.plane_order_detail_applyNo);
        costCenter_iv = (ItemView) findViewById(R.id.plane_order_detail_costCenter);
        project_iv = (ItemView) findViewById(R.id.plane_order_detail_project);
        reason_iv = (ItemView) findViewById(R.id.plane_order_detail_reason);
        weiItem_iv = (ItemView) findViewById(R.id.plane_order_detail_weiItem);
        weiReason_iv = (ItemView) findViewById(R.id.plane_order_detail_weiReason);
        psgs_lv = (ListView) findViewById(R.id.plane_order_detail_psg_lv);
        approve_lv = (ListView) findViewById(R.id.plane_order_detail_approve_lv);
        approveStatus_vg = findViewById(R.id.plane_order_detail_item_approveStatus);
        approveInfo_vg = findViewById(R.id.plane_order_detail_approve_info);
    }

    @Override
    protected void initView() {
        setApplyNoVisibility(applyNo_iv);
        setCostCenterAndProjectVisibility(costCenter_iv, project_iv);
    }

    @Override
    protected void initListener() {
        button1_tv.setOnClickListener(this);
        button2_tv.setOnClickListener(this);
        priceDetail_vg.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
        button1_tv.setText("通过");
        button2_tv.setText("拒绝");
        button2_tv.setBackgroundResource(R.drawable.selector_cancelclick);
        button2_tv.setTextColor(getResources().getColor(R.color.color_666));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_order_detail_button1:
                pPlaneApprove.doAgree();
                break;
            case R.id.plane_order_detail_button2:
                pPlaneApprove.doRefuse();
                break;
            case R.id.plane_order_detail_click_pricedetail:
                pPlaneApprove.showPriceDetail();
                break;
        }
    }

    @Override
    protected void getData() {
        super.getData();
        pPlaneApprove.getOrderDetail();
    }

    @Override
    public void updateViews() {
        IPlaneApprove mBean = pPlaneApprove.getmBean();
        priceAll_tv.setText(mBean.getTotalpriceI() + "");
        orderNo_tv.setText(String.format("订单号:%s", mBean.getOrderNoI()));
        ticketStatus_tv.setText(pPlaneApprove.isGQ() ?
                MUtils.getAlterStateByCode(mBean.getStatusI())
                : MUtils.getOrgTicketStateByCode(mBean.getStatusI()));
        approveStatus_tv.setText(MUtils.getApproveStateByCode(mBean.getApprovestatusI()));
        contact_tv.setText(mBean.getContactI());
        tel_tv.setText(mBean.getLinkPhoneI());
        String linkEmail = mBean.getLinkEmailI();
        if (TextUtils.isEmpty(linkEmail)) {
            email_tv.setVisibility(View.GONE);
        } else {
            email_tv.setText(linkEmail);
        }
        String bxName = mBean.getPassengersI().get(0).getBxName();
        insurance_iv.setContent(TextUtils.isEmpty(bxName) ? "- -" : bxName);
        psgs_lv.setAdapter(pPlaneApprove.getAdapter_psg());
        approve_lv.setAdapter(pPlaneApprove.getAdapter_approve_state());
        IRouteBean rb = mBean.getRoute();
        cv.setAirline(rb.getCarriername() + rb.getAirline() + "|" + rb.getPlanestyle());
        cv.setStart_date(rb.getDeptdate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(rb.getDeptdate()));
        cv.setEnd_date(rb.getArridate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(rb.getArridate()));
        cv.setStart_time(rb.getDepttime());
        cv.setEnd_time(rb.getArritime());
        cv.setOrgname(rb.getOrgname());
        cv.setArriname(rb.getArriname());
        cv.setBottom2(rb.getCodeDes() + "/" + rb.getDisdes());
        cv.setGaiStr(rb.getChangerule());
        cv.setTuiStr(rb.getRefundrule());
        String shenqingNoI = mBean.getShenqingNoI();
        if (shenqingNoI.equals("--")) {
            applyNo_iv.setVisibility(View.GONE);
            costCenter_iv.setVisibility(View.GONE);
            project_iv.setVisibility(View.GONE);
            reason_iv.setVisibility(View.GONE);
            weiItem_iv.setVisibility(View.GONE);
            weiReason_iv.setVisibility(View.GONE);
        } else {
            applyNo_iv.setContent(shenqingNoI);
            costCenter_iv.setContent(mBean.getCostCenterI());
            project_iv.setContent(mBean.getPronameI());
            reason_iv.setContent(mBean.getChailvitemI());
            weiItem_iv.setContent(mBean.getBookPolicyI());
            weiReason_iv.setContent(mBean.getWBReasonI());
        }
        if (mBean.getWBReasonI().isEmpty()) {
            weiItem_iv.setVisibility(View.GONE);
            weiReason_iv.setVisibility(View.GONE);
        }

    }

    @Override
    public void setViewState() {
        int state = pPlaneApprove.checkState();
        if (state < 0) {
            button1_tv.setVisibility(View.GONE);
            button2_tv.setVisibility(View.GONE);
            if (state == -1) {
                approveStatus_tv.setText("未识别");
            } else if (state == -2) {
                approveStatus_tv.setText("等待上级审批中");
            } else if (state == -3) {
                approveStatus_tv.setText("已通过");
            } else {
                approveStatus_tv.setText("已拒绝");
            }
        } else {
            button1_tv.setVisibility(View.VISIBLE);
            button2_tv.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(pPlaneApprove.getmBean().getShenqingNoI())) {
            applyNo_iv.setVisibility(View.GONE);
        }
    }
}
