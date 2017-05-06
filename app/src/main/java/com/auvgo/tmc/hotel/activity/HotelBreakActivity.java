package com.auvgo.tmc.hotel.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.adapter.HotelBreakAdapter;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelBreakActivity extends BaseActivity {
    @BindView(R.id.hotel_breck_name)
    TextView name;
    @BindView(R.id.hotel_breck_pay_type)
    TextView pay_name;
    @BindView(R.id.hotel_breck_bed)
    TextView add_bed;
    @BindView(R.id.hotel_breck_pay_desc)
    TextView pay_rule;
    @BindView(R.id.hotel_break_lv)
    ListView lv;
    @BindView(R.id.hotel_breck_cancel_rule)
    TextView cancel_tv;

    private HotelDetailBean.RoomListBean.RatePlanListBean mBean;
    private String hotel_name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_break;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        mBean = getIntent().getParcelableExtra("bean");
        hotel_name = getIntent().getStringExtra("roomName");
    }

    @Override
    protected void initView() {
        name.setText(String.format("%s%s", hotel_name + "-"
                , mBean.getRatePlanName()));
        boolean selfPay = mBean.getPaymentType().equals("SelfPay");
        boolean isGuarantee = mBean.isGurantee();

        String pay_name;
        if (selfPay) {
            if (isGuarantee) {
                pay_name = "到店付(担保)";
                pay_rule.setText("预订酒店后需支付定金，成功入住后3日内返还定金，未如约入住，定金将被扣除。");
            } else {
                pay_name = "到店付";
                pay_rule.setText("到达预订酒店后，向酒店支付房费，无需在线预付房费。");
            }
        } else {
            pay_name = "预付";
            pay_rule.setText("预订酒店后立即在线预付房费。");
        }
        this.pay_name.setText(pay_name);
        add_bed.setText("- -");
        cancel_tv.setText(mBean.getCancelRule() == null ? "" : mBean.getCancelRule());
        lv.setAdapter(new HotelBreakAdapter(this, mBean.getList(), mBean.getRatePlanName()));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {

    }

    @OnClick(R.id.hotel_break_close)
    void close() {
        finish();
    }
}
