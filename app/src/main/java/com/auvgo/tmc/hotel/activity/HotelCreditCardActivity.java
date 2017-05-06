package com.auvgo.tmc.hotel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.views.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelCreditCardActivity extends BaseActivity {
    @BindView(R.id.credit_card_iv)
    ImageView iv;
    @BindView(R.id.credit_card_tv1)
    TextView tv1;
    @BindView(R.id.credit_card_tv2)
    TextView tv2;
    @BindView(R.id.credit_card_title)
    TitleView title;
    private boolean isCvv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_credit_card;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        isCvv = getIntent().getBooleanExtra("isCvv", false);
    }

    @Override
    protected void initView() {
        if (isCvv) {
            tv2.setVisibility(View.GONE);
            iv.setImageResource(R.mipmap.creditcard_cvv);
            tv1.setText("信用卡CVV码通常在卡片背面，实例如下图：");
            title.setTitle("信用卡CVV码");
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {

    }
}
