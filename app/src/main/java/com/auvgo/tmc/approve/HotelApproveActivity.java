package com.auvgo.tmc.approve;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.PickerListAdapter2;
import com.auvgo.tmc.approve.interfaces.ViewManager_hotelApprove;
import com.auvgo.tmc.approve.p.PHotelApprove;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.base.MyList;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.train.bean.ComSettingBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.HotelDetailCardView;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelApproveActivity extends BaseActivity implements ViewManager_hotelApprove {
    @BindView(R.id.hotel_approve_detail_orderno)
    TextView orderNo_tv;
    @BindView(R.id.hotel_approve_detail_state)
    TextView status_tv;
    @BindView(R.id.hotel_approve_detail_price_pay)
    TextView price_pay;
    @BindView(R.id.hotel_approve_detail_price_guarantee)
    TextView price_guarantee;
    @BindView(R.id.hotel_approve_detail_price_name)
    TextView price_name;
    @BindView(R.id.hotel_approve_detail_button1)
    TextView button1_tv;
    @BindView(R.id.hotel_approve_detail_button2)
    TextView button2_tv;
    @BindView(R.id.hotel_approve_detail_contact)
    TextView contact_tv;
    @BindView(R.id.hotel_approve_detail_tel)
    TextView tel_tv;
    @BindView(R.id.hotel_approve_detail_email)
    TextView email_tv;
    @BindView(R.id.hotel_approve_detail_applyNo)
    ItemView applyNo_iv;
    @BindView(R.id.hotel_approve_detail_costcenter)
    ItemView costCenter_iv;
    @BindView(R.id.hotel_approve_detail_project)
    ItemView project_iv;
    @BindView(R.id.hotel_approve_detail_reason)
    ItemView reason_iv;
    @BindView(R.id.hotel_approve_detail_weiItem)
    ItemView weiItem_iv;
    @BindView(R.id.hotel_approve_detail_weiReason)
    ItemView weiReason_iv;
    @BindView(R.id.hotel_approve_detail_psg_lv)
    ListView psgs_lv;
    @BindView(R.id.hotel_approve_detail_approve_lv)
    ListView approve_lv;
    @BindView(R.id.hotel_approve_detail_approve_ll)
    View approveInfo_vg;
    @BindView(R.id.hotel_approve_detail_approveStatus_ll)
    View approveStatus_vg;
    @BindView(R.id.hotel_approve_detail_guarantee_price_ll)
    View guarantee_ll;
    @BindView(R.id.hotel_approve_detail_cv)
    HotelDetailCardView cv;
    @BindView(R.id.hotel_approve_detail_time_keep)
    ItemView timeKeep;
    private PHotelApprove pHotelApprove;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_approve;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        pHotelApprove = new PHotelApprove(this, this);
        pHotelApprove.init(getIntent());
    }

    @Override
    protected void initView() {
        setCostCenterAndProjectVisibility(costCenter_iv, project_iv);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {
        psgs_lv.setFocusable(false);
        approve_lv.setFocusable(false);
    }

    @Override
    protected void getData() {
        pHotelApprove.getOrderDetail();
    }

    private List<? extends MyPickerView.Selection> getList() {
        List<SelectionBean> list = new ArrayList<>();
        HotelOrderDetailBean mBean = pHotelApprove.getmBean();
        for (int i = 0; i < mBean.getUsers().size(); i++) {
            list.add(new SelectionBean("房间" + (i + 1) + "      " + mBean.getUsers().get(i).getName()));
        }
        return list;
    }

    @Override
    public void updateViews() {
        HotelOrderDetailBean mBean = pHotelApprove.getmBean();
        int approvestatus = mBean.getApprovestatus();
        int status = mBean.getStatus();
        status_tv.setText(MUtils.getApproveStateByCode(approvestatus));
        orderNo_tv.setText(String.format("订单号：%s", mBean.getOrderno()));
        cv.setHotel_name(mBean.getHotelName());
        cv.setRoom_name(mBean.getRoomName());
        cv.setLogoResource(TextUtils.isEmpty(mBean.getRatePlanName()) ?
                R.mipmap.hotel_nobreckfast : R.mipmap.hotel_breckfast);
        cv.setCheckIn(mBean.getArrivalDate(), mBean.getDepartureDate());
        cv.setRoom_num(mBean.getNumberOfRooms() + "间");
        cv.setmBean(mBean);
        psgs_lv.setAdapter(new PickerListAdapter2(this, getList(), R.layout.item_picker_lv2));
        psgs_lv.setFocusable(false);
        timeKeep.setContent(TimeUtils.get_HH_mm(Long.valueOf(mBean.getLatestArrivalTime())));
        contact_tv.setText(mBean.getLinkName());
        tel_tv.setText(mBean.getLinkPhone());
        applyNo_iv.setContent(mBean.getShenqingno());
        costCenter_iv.setContent(mBean.getCostname());
        project_iv.setContent(mBean.getProname());
        reason_iv.setContent(mBean.getChailvitem());
        weiItem_iv.setContent(mBean.getBookpolicy());
        weiReason_iv.setContent(mBean.getWbreason());
        if (TextUtils.isEmpty(mBean.getLinkEmail())) {
            email_tv.setVisibility(View.GONE);
        } else {
            email_tv.setText(mBean.getLinkEmail());
        }
        price_pay.setText(String.format("￥%s", AppUtils.keepNSecimal(String.valueOf(mBean.getTotalPrice()), 2)));
        if (mBean.getPaymentType().equals("SelfPay")) {
            price_name.setText("到店付款");
        } else {
            price_name.setText("在线预付");
            timeKeep.setVisibility(View.GONE);
        }
        String isNeedGuarantee = mBean.getIsNeedGuarantee();
        if (isNeedGuarantee.equals("false") || isNeedGuarantee.equals("0")) {
            guarantee_ll.setVisibility(View.GONE);
        } else {
            price_guarantee.setText("￥" + AppUtils.keepNSecimal(String.valueOf(mBean.getGuaranteeAmount()), 2));
        }
        if (mBean.getWeibeiflag() == 0) {
            weiItem_iv.setVisibility(View.GONE);
            weiReason_iv.setVisibility(View.GONE);
        }
        approve_lv.setAdapter(new ApproveStateAdapter(this, mBean.getApproves()));
        approve_lv.setFocusable(false);
        psgs_lv.setFocusable(false);
    }

    @Override
    public void setViewState() {
        int state = pHotelApprove.checkState();
        if (state < 0) {
            button1_tv.setVisibility(View.GONE);
            button2_tv.setVisibility(View.GONE);
            if (state == -1) {
                status_tv.setText("未识别");
            } else if (state == -2) {
                status_tv.setText("等待上级审批中");
            } else if (state == -3) {
                status_tv.setText("已通过");
            } else {
                status_tv.setText("已拒绝");
            }
        } else {
            button1_tv.setVisibility(View.VISIBLE);
            button2_tv.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(pHotelApprove.getmBean().getShenqingno())) {
            applyNo_iv.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.hotel_approve_detail_button1, R.id.hotel_approve_detail_button2, R.id.hotel_approve_detail_click_pricedetail})
    void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_approve_detail_button1:
                pHotelApprove.doAgree();
                break;
            case R.id.hotel_approve_detail_button2:
                pHotelApprove.doRefuse();
                break;
            case R.id.hotel_approve_detail_click_pricedetail:
                pHotelApprove.showPriceDetail();
                break;
        }
    }
}
