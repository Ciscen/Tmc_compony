package com.auvgo.tmc.plane.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.p.PPlaneOrderDetail;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.interfaces.ViewManager_PlaneOrderDetail;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.PlaneDetailCardView;

import static com.auvgo.tmc.constants.Constant.ApproveStatus.*;
import static com.auvgo.tmc.constants.Constant.AirTicketStatus.*;

public class PlaneOrderDetailActivity extends BaseActivity implements ViewManager_PlaneOrderDetail, View.OnClickListener {
    private TextView orderNo_tv, ticketStatus_tv, approveStatus_tv, priceAll_tv, button1_tv, button2_tv,
            contact_tv, tel_tv, email_tv;
    private ItemView insurance_iv, applyNo_iv, costCenter_iv, project_iv, reason_iv, weiItem_iv, weiReason_iv, peisong_iv;
    private ListView psgs_lv, approve_lv;
    private PlaneDetailCardView cv;
    private View approveInfo_vg, approveStatus_vg, priceDetail_vg;
    private PPlaneOrderDetail pPlaneOrderDetail;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_order_detail;
    }

    @Override
    protected void initData() {
        pPlaneOrderDetail = new PPlaneOrderDetail(this, this);
        pPlaneOrderDetail.init(getIntent());
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
        peisong_iv = (ItemView) findViewById(R.id.plane_order_detail_peisong_addr);
    }

    @Override
    protected void initView() {
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
    }

    @Override
    protected void getData() {
        super.getData();
        pPlaneOrderDetail.getOrderDetail();
    }

    @Override
    public void updateViews() {
        PlaneOrderDetailBean mBean = pPlaneOrderDetail.getmBean();
        orderNo_tv.setText(String.format("订单号:%s", mBean.getOrderno()));
        ticketStatus_tv.setText(MUtils.getOrgTicketStateByCode(mBean.getStatus()));
        approveStatus_tv.setText(MUtils.getApproveStateByCode(mBean.getApprovestatus()));
        if (mBean.getPaystatus() == Constant.PayStatus.PAY_STATUS_DAIZHIFU) {
            ticketStatus_tv.setText("待支付");
        }
        contact_tv.setText(mBean.getLinkName());
        tel_tv.setText(mBean.getLinkPhone());
        String linkEmail = mBean.getLinkEmail();
        if (TextUtils.isEmpty(linkEmail)) {
            email_tv.setVisibility(View.GONE);
        } else {
            email_tv.setText(linkEmail);
        }
        if (mBean.getWeibeiflag() == 0) {
            weiItem_iv.setVisibility(View.GONE);
            weiReason_iv.setVisibility(View.GONE);
        }
        String bxName = mBean.getRoutePass().get(0).getBxName();
        insurance_iv.setContent(TextUtils.isEmpty(bxName) ? "- -" : bxName);
        psgs_lv.setAdapter(pPlaneOrderDetail.getAdapter());
        PlaneOrderDetailBean.RoutesBean rb = mBean.getRoutes().get(0);
        cv.setAirline(rb.getCarriername() + rb.getAirline() + "|" + rb.getPlanestyle());
        cv.setStart_date(rb.getDeptdate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(rb.getDeptdate()));
        cv.setEnd_date(rb.getArridate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(rb.getArridate()));
        cv.setStart_time(rb.getDepttime());
        cv.setEnd_time(rb.getArritime());
        cv.setOrgname(rb.getOrgname());
        cv.setArriname(rb.getArriname());
        cv.setCangwei(rb.getCodeDes());
        cv.setTuiStr(rb.getRefundrule());
        cv.setGaiStr(rb.getChangerule());

        applyNo_iv.setContent(mBean.getShenqingno());
        costCenter_iv.setContent(mBean.getCostname());
        project_iv.setContent(mBean.getProname());
        reason_iv.setContent(mBean.getChailvitem());
        weiItem_iv.setContent(mBean.getBookpolicy());
        weiReason_iv.setContent(mBean.getWbreason());
        priceAll_tv.setText(String.valueOf(mBean.getTotalprice()));
        approve_lv.setAdapter(pPlaneOrderDetail.getAdapter_approve_state());
        peisong_iv.setContent(mBean.getLinkAddress());
    }

    @Override
    public void setViewState() {
        /*
        根据出票状态、审批状态进行控件显示状态的判断
         */
        PlaneOrderDetailBean mBean = pPlaneOrderDetail.getmBean();
        /*
        审批信息的显示隐藏
         */
        int approvestatus = mBean.getApprovestatus();//审批状态
        int status = mBean.getStatus();//票的状态
        int paystatus = mBean.getPaystatus();//支付状态
        if (approvestatus == APPROVE_STATUS_WUXUSHENPI) {
            approveInfo_vg.setVisibility(View.GONE);
            approveStatus_vg.setVisibility(View.GONE);
        }
        /*
        底部两个按钮的状态控制
         */
        if (status == TICKET_STATUS_YIDINGZUO) {//已订座
            //审批通过或者无需审批
            setCancleText();
            if (approvestatus == APPROVE_STATUS_SHENPITONGGUO || approvestatus == APPROVE_STATUS_WUXUSHENPI) {
                button1_tv.setText(mBean.getPayType().equals("1") ? "确认出票" : "去支付");//1月结，直接出票。2在线支付，需要个人用支付宝微信银联支付
                button2_tv.setText("取消订单");
            }
            if (approvestatus == APPROVE_STATUS_SHENPIFOUJUE || approvestatus == APPROVE_STATUS_SHENPIZHONG) {
                button2_tv.setText("取消订单");
                button1_tv.setVisibility(View.GONE);
            }
        } else if (status == TICKET_STATUS_YICHUPIAO) {//已出票
            button1_tv.setText("退票");
            button2_tv.setText("改签");
            button2_tv.setBackground(getResources().getDrawable(R.drawable.selector_button_blue_noradius));
            /*订座中或者出票中*/
        } else if (status == TICKET_STATUS_DINGZUOZHONG || status == TICKET_STATUS_CHUPIAOZHONG) {
            button1_tv.setText("");
            button2_tv.setText("");
            button1_tv.setVisibility(View.GONE);
            button2_tv.setVisibility(View.GONE);
        } else {//其他情况，包括失败、出票中
            button1_tv.setVisibility(View.GONE);
            button2_tv.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(mBean.getShenqingno())) {
            applyNo_iv.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(mBean.getLinkAddress())) {
            peisong_iv.setVisibility(View.GONE);
        }
    }

    private void setCancleText() {
        button2_tv.setTextColor(getResources().getColor(R.color.color_333));
        button2_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_cancelclick));
    }

    @Override
    public void onClick(View v) {
        PlaneOrderDetailBean mBean = pPlaneOrderDetail.getmBean();
        int status = mBean.getStatus();
        int approvestatus = mBean.getApprovestatus();
        int state = 0;
        if (status == TICKET_STATUS_YIDINGZUO) {//已订座
            //审批通过或者无需审批
            if (approvestatus == APPROVE_STATUS_SHENPITONGGUO || approvestatus == APPROVE_STATUS_WUXUSHENPI) {
                state = 1;//确认出票、取消订单
            }
            if (approvestatus == APPROVE_STATUS_SHENPIFOUJUE || approvestatus == APPROVE_STATUS_SHENPIZHONG) {
                state = 2;//取消订单
            }
        } else if (status == TICKET_STATUS_YICHUPIAO) {//已出票
            state = 3;//退票、改签
        } else {//其他情况，包括失败、出票中
            state = 4;//隐藏
        }
        switch (v.getId()) {
            case R.id.plane_order_detail_button1:
                if (state == 1) {
                    pPlaneOrderDetail.confirmOutTicket();
                }
                if (state == 3) {
                    pPlaneOrderDetail.tuipiao();
                }
                break;
            case R.id.plane_order_detail_button2:
                if (state == 3) {
                    pPlaneOrderDetail.gaiqian();
                } else {
                    DialogUtil.showDialog(this, "提示", "取消", "确定", "确定取消订单吗？", new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                        }

                        @Override
                        public void onRightClick() {
                            pPlaneOrderDetail.cancleOrder();
                        }
                    });
                }
                break;
            case R.id.plane_order_detail_click_pricedetail:
                pPlaneOrderDetail.showPriceDetailPop();
                break;
        }
    }
}
