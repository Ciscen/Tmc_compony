package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.bean.RequestGuaranteeBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelGuaranteeActivity extends BaseActivity {
    @BindView(R.id.guarantee_card_number)
    ItemView card_num;

    private String orderNo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_guarantee;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        orderNo = getIntent().getStringExtra("orderNo");
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

    @OnClick(R.id.guarantee_next)
    void onClick() {
        getherData();
    }

    private void getherData() {

        final String card_num_str = card_num.getContent();

        Map<String, String> map = new HashMap<>();
        map.put("cardno", card_num_str);
        RetrofitUtil.checkCreditCardValidate(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Intent intent = new Intent(HotelGuaranteeActivity.this, HotelGuarantee2Activity.class);
                    intent.putExtra("orderNo", orderNo);
                    intent.putExtra("cardNo", card_num_str);
                    intent.putExtra("isCvv", !bean.getData().equals("false"));
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
//        commit(rgb);
    }


}

