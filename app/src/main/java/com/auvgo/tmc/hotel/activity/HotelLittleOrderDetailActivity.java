package com.auvgo.tmc.hotel.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PickerListAdapter2;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.HotelDetailCardView;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelLittleOrderDetailActivity extends BaseActivity {

    @BindView(R.id.hotel_little_detail_state)
    TextView state;
    @BindView(R.id.hotel_little_detail_orderno_top)
    TextView orderNo_top;
    @BindView(R.id.hotel_little_detail_orderno_bottom)
    TextView orderNo_bottom;
    @BindView(R.id.hotel_little_detail_contact)
    TextView contact;
    @BindView(R.id.hotel_little_detail_tel)
    TextView tel;
    @BindView(R.id.hotel_little_detail_email)
    TextView email;
    @BindView(R.id.hotel_little_detail_time_keep)
    ItemView timeKeep;
    @BindView(R.id.hotel_little_detail_lv)
    ListView lv;
    @BindView(R.id.hotel_little_detail_state_rl)
    View state_rl;
    @BindView(R.id.hotel_little_detail_cv)
    HotelDetailCardView cv;
    @BindView(R.id.hotel_little_detail_click_pricedetail)
    View price_ll;
    @BindView(R.id.hotel_little_detail_guarantee_price_ll)
    View guarantee_ll;
    @BindView(R.id.hotel_little_detail_price_name)
    TextView price_name;
    @BindView(R.id.hotel_little_detail_price)
    TextView price_pay;
    @BindView(R.id.hotel_little_detail_price_guarantee)
    TextView price_guarantee;

    //订单号
    private String orderNo;
    //详情实体
    private HotelOrderDetailBean mBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_little_order_detail;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        orderNo = getIntent().getStringExtra("orderNo");
        mBean = (HotelOrderDetailBean) getIntent().getSerializableExtra("bean");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        if (mBean != null) {
            updateViews();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.getHotelOrderDetail(this, AppUtils.getJson(map), HotelOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (HotelOrderDetailBean) o;
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
    根据bean来设置ui
     */
    private void updateViews() {
        int status = mBean.getStatus();
        String orderStr = "订单号：" + mBean.getOrderno();
        if (status == 0) {//未确认的状态
            orderNo_bottom.setVisibility(View.GONE);
            orderNo_top.setText(orderStr);
            state.setText("等待酒店确认");
        } else {
            state_rl.setVisibility(View.GONE);
            orderNo_bottom.setText(orderStr);
            price_ll.setVisibility(View.GONE);
        }
        cv.setHotel_name(mBean.getHotelName());
        cv.setRoom_name(mBean.getRoomName());
        cv.setLogoResource(TextUtils.isEmpty(mBean.getRatePlanName()) ?
                R.mipmap.hotel_nobreckfast : R.mipmap.hotel_breckfast);
        cv.setCheckIn(mBean.getArrivalDate(), mBean.getDepartureDate());
        cv.setRoom_num(mBean.getNumberOfRooms() + "间");
        lv.setAdapter(new PickerListAdapter2(this, getList(), R.layout.item_picker_lv2));
        cv.setmBean(mBean);
        lv.setFocusable(false);
        timeKeep.setContent(mBean.getLatestArrivalTime());
        contact.setText(mBean.getLinkName());
        tel.setText(mBean.getLinkPhone());
        if (TextUtils.isEmpty(mBean.getLinkEmail())) {
            email.setVisibility(View.GONE);
        } else {
            email.setText(mBean.getLinkEmail());
        }
        price_pay.setText(String.valueOf(mBean.getTotalPrice()));
        if (mBean.getPaymentType().equals("SelfPay")) {
            price_name.setText("到店付款");
        } else {
            price_name.setText("预    付");
            timeKeep.setVisibility(View.GONE);
        }
        if (mBean.getIsNeedGuarantee().equals("false")) {
            guarantee_ll.setVisibility(View.GONE);
        } else {
            price_guarantee.setText(String.valueOf(mBean.getGuaranteeAmount()));
        }
    }

    private List<? extends MyPickerView.Selection> getList() {
        List<SelectionBean> list = new ArrayList<>();
        for (int i = 0; i < mBean.getUsers().size(); i++) {
            list.add(new SelectionBean("房间" + (i + 1) + "      " + mBean.getUsers().get(i).getName()));
        }
        return list;
    }

    @OnClick(R.id.hotel_little_detail_click_pricedetail)
    void showPriceDialog() {
        DialogUtil.showHotelPriceDialog(this, mBean);
    }
}
