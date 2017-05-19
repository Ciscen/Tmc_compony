package com.auvgo.tmc.hotel.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.PickerListAdapter2;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.hotel.interfaces.ViewManager_hotelOrderDetail;
import com.auvgo.tmc.p.PHotelOrderDetail;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.HotelDetailCardView;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelOrderDetailActivity extends BaseActivity implements ViewManager_hotelOrderDetail {
    @BindView(R.id.hotel_order_detail_orderno)
    TextView orderNo_tv;
    @BindView(R.id.hotel_order_detail_state)
    TextView status_tv;
    @BindView(R.id.hotel_order_detail_price_pay)
    TextView price_pay;
    @BindView(R.id.hotel_order_detail_price_guarantee)
    TextView price_guarantee;
    @BindView(R.id.hotel_order_detail_price_name)
    TextView price_name;
    @BindView(R.id.hotel_order_detail_button1)
    TextView button1_tv;
    @BindView(R.id.hotel_order_detail_button2)
    TextView button2_tv;
    @BindView(R.id.hotel_order_detail_contact)
    TextView contact_tv;
    @BindView(R.id.hotel_order_detail_tel)
    TextView tel_tv;
    @BindView(R.id.hotel_order_detail_email)
    TextView email_tv;
    @BindView(R.id.hotel_order_detail_applyNo)
    ItemView applyNo_iv;
    @BindView(R.id.hotel_order_detail_costcenter)
    ItemView costCenter_iv;
    @BindView(R.id.hotel_order_detail_project)
    ItemView project_iv;
    @BindView(R.id.hotel_order_detail_reason)
    ItemView reason_iv;
    @BindView(R.id.hotel_order_detail_weiItem)
    ItemView weiItem_iv;
    @BindView(R.id.hotel_order_detail_weiReason)
    ItemView weiReason_iv;
    @BindView(R.id.hotel_order_detail_psg_lv)
    ListView psgs_lv;
    @BindView(R.id.hotel_order_detail_approve_lv)
    ListView approve_lv;
    @BindView(R.id.hotel_order_detail_approve_ll)
    View approveInfo_vg;
    @BindView(R.id.hotel_order_detail_approveStatus_ll)
    View approveStatus_vg;
    @BindView(R.id.hotel_order_detail_guarantee_price_ll)
    View guarantee_ll;
    @BindView(R.id.hotel_order_detail_cv)
    HotelDetailCardView cv;
    @BindView(R.id.hotel_order_detail_time_keep)
    ItemView timeKeep;
    /*
    presenter
     */
    private PHotelOrderDetail pHotelOrderDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_order_detail;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        pHotelOrderDetail = new PHotelOrderDetail(this, this);
        pHotelOrderDetail.init(getIntent());
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
        super.getData();
        pHotelOrderDetail.getData();
    }

    @Override
    public void updateViews() {
        HotelOrderDetailBean mBean = pHotelOrderDetail.getmBean();
        status_tv.setText(pHotelOrderDetail.getState(mBean));
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
        timeKeep.setContent(TimeUtils.get_HH_mm(Long.parseLong(mBean.getLatestArrivalTime())));
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
        price_pay.setText(String.format("￥%s", String.valueOf(mBean.getTotalPrice())));
        if (mBean.getPaymentType().equals("SelfPay")) {
            price_name.setText("到店付款:");
        } else {
            price_name.setText("在线预付:");
            timeKeep.setVisibility(View.GONE);
        }
        String isNeedGuarantee = mBean.getIsNeedGuarantee();
        if (isNeedGuarantee.equals("false") || isNeedGuarantee.equals("0")) {
            guarantee_ll.setVisibility(View.GONE);
        } else {
            price_guarantee.setText(String.format("￥%s", String.valueOf(mBean.getGuaranteeAmount())));
        }
        if (mBean.getWeibeiflag() == 0) {
            weiItem_iv.setVisibility(View.GONE);
            weiReason_iv.setVisibility(View.GONE);
        }
        approveInfo_vg.setVisibility(mBean.getApprovestatus() == Constant.ApproveStatus.APPROVE_STATUS_WUXUSHENPI ? View.GONE : View.VISIBLE);
        if (mBean.getApprovestatus() == 0) approveInfo_vg.setVisibility(View.GONE);
        approve_lv.setAdapter(new ApproveStateAdapter(this, mBean.getApproves()));
        approve_lv.setFocusable(false);
        psgs_lv.setFocusable(false);
        if (TextUtils.isEmpty(pHotelOrderDetail.getmBean().getShenqingno())) {
            applyNo_iv.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.hotel_order_detail_button1, R.id.hotel_order_detail_button2, R.id.hotel_order_detail_click_pricedetail})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_order_detail_button1:
                if (button1_tv.getText().toString().contains("支付")) {
                    pHotelOrderDetail.pay();
                } else if (button1_tv.getText().equals("担保")) {
                    pHotelOrderDetail.guarantee();
                }
                break;
            case R.id.hotel_order_detail_button2:
                if (button2_tv.getText().equals("取消")) {
                    DialogUtil.showDialog(this, "提示", "取消", "确定", "确定取消订单吗？",
                            new MyDialog.OnButtonClickListener() {
                                @Override
                                public void onLeftClick() {
                                }

                                @Override
                                public void onRightClick() {
                                    pHotelOrderDetail.cancel();
                                }
                            });
                }
                break;
            case R.id.hotel_order_detail_click_pricedetail:
                DialogUtil.showHotelPriceDialog(this, pHotelOrderDetail.getmBean());
                break;
        }
    }

    public void setButtonState(String t1, String t2, boolean b1, boolean b2) {
        if (b1) {
            button1_tv.setVisibility(View.VISIBLE);
            button1_tv.setText(t1);
        } else {
            button1_tv.setVisibility(View.GONE);
        }
        if (b2) {
            button2_tv.setVisibility(View.VISIBLE);
            button2_tv.setText(TextUtils.isEmpty(t2) ? "取消" : t2);
        } else {
            button2_tv.setVisibility(View.GONE);
        }
    }

    private List<? extends MyPickerView.Selection> getList() {
        List<SelectionBean> list = new ArrayList<>();
        HotelOrderDetailBean mBean = pHotelOrderDetail.getmBean();
        for (int i = 0; i < mBean.getUsers().size(); i++) {
            list.add(new SelectionBean("房间" + (i + 1) + "      " + mBean.getUsers().get(i).getName()));
        }
        return list;
    }
}
