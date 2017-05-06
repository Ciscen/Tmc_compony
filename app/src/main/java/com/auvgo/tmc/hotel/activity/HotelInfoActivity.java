package com.auvgo.tmc.hotel.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.adapter.HotelFacilitiesAdapter;
import com.auvgo.tmc.hotel.adapter.HotelInfoTrafficAdapter;
import com.auvgo.tmc.hotel.bean.HotelInfoBean;
import com.auvgo.tmc.hotel.bean.TrafficInfo;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelInfoActivity extends BaseActivity {

    private String hotelId;
    private String hotelName;
    private String hotelAddr;
    private HotelInfoBean mBean;
    @BindView(R.id.hotel_info_name)
    TextView name;
    @BindView(R.id.hotel_info_addr)
    TextView addr;
    @BindView(R.id.hotel_info_level)
    TextView level;
    @BindView(R.id.hotel_info_open)
    TextView open;
    @BindView(R.id.hotel_info_decorate)
    TextView decorate;
    @BindView(R.id.hotel_info_desc)
    TextView desc;
    @BindView(R.id.hotel_info_card)
    TextView card;
    @BindView(R.id.hotel_info_expande_text)
    TextView exp_txt;
    @BindView(R.id.hotel_info_book_notice)
    TextView book_notice;
    @BindView(R.id.hotel_info_check_notice)
    TextView check_notice;
    @BindView(R.id.hotel_info_back)
    ImageView back;
    @BindView(R.id.hotel_info_tel)
    ImageView tel;
    @BindView(R.id.hotel_info_expande_icon)
    ImageView exp_icon;
    @BindView(R.id.hotel_info_gv)
    GridView gv;
    @BindView(R.id.hotel_info_traffic_lv)
    ListView lv;
    @BindView(R.id.hotel_info_card_ll)
    View card_ll;
    private int infoState = 0;//0表示展开的状态

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_info;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        hotelId = bundle.getString("hotelId");
        hotelName = bundle.getString("hotelName");
        hotelAddr = bundle.getString("addr");
    }

    @Override
    protected void initView() {
    }


    @Override
    protected void getData() {
        super.getData();
        Map<String, String> map = new HashMap<>();
        map.put("hotelId", hotelId);
        RetrofitUtil.getHotelInfo(this, AppUtils.getJson(map), HotelInfoBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (HotelInfoBean) o;
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
        name.setText(hotelName);
        addr.setText(hotelAddr);
        open.setText(String.format("%s月", mBean.getEstablishmentDate().replace("-", "年")));
        decorate.setText(String.format("%s月", mBean.getRenovationDate().replace("-", "年")));
        desc.setText(mBean.getIntroEditor());
        card.setText(mBean.getCreditCards());
        if (desc.getLineCount() > 3) {
            setExp(3, View.GONE);
            infoState = 1;
        }
        gv.setAdapter(new HotelFacilitiesAdapter(this, Arrays.asList(mBean.getFacilities().split(","))));
        lv.setAdapter(new HotelInfoTrafficAdapter(getList(), this));
    }

    private List<TrafficInfo> getList() {
        String traffic = mBean.getTraffic();
        List<TrafficInfo> list = new ArrayList<>();

        if (traffic != null) {
            String[] split = traffic.split("\n- ");
            for (String aSplit : split) {
                if (aSplit.contains("距")) {
                    TrafficInfo trafficInfo = new TrafficInfo();
                    trafficInfo.setStationName(aSplit);
                    trafficInfo.setDistance("");
                    list.add(trafficInfo);
                }
            }
        }
        return list;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void setViews() {
    }

    @OnClick({R.id.hotel_info_back, R.id.hotel_info_tel, R.id.hotel_info_expande_text})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_info_back:
                finish();
                break;
            case R.id.hotel_info_tel:
                if (mBean != null)
                    AppUtils.callPhone(this, mBean.getPhone());
                break;
            case R.id.hotel_info_expande_text:
                showAndHide();
                break;
        }
    }

    private void showAndHide() {
        if (infoState == 0) {
            setExp(3, View.GONE);
            infoState = 1;
        } else {
            setExp(1000, View.VISIBLE);
            infoState = 0;
        }
    }

    private void setExp(int maxlines, int visible) {
        desc.setMaxLines(maxlines);
        card_ll.setVisibility(visible);
        exp_txt.setText(visible == View.GONE ? "展开" : "收起");
        exp_icon.setImageResource(visible == View.GONE ? R.mipmap.arrow_zhankai : R.mipmap.arrow_shouqi);
    }
}
