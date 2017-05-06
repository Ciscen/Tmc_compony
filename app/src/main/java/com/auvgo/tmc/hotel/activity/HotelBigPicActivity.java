package com.auvgo.tmc.hotel.activity;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.adapter.HotelBigPicAdapter;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnPageChange;

public class HotelBigPicActivity extends BaseActivity {

    @BindView(R.id.hotel_big_pic_title)
    TextView title;
    @BindView(R.id.hotel_big_pic_num)
    TextView num;
    @BindView(R.id.hotel_big_pic_vp)
    ViewPager vp;
    private List<HotelDetailBean.HotelImageListBean> list;

    private int p;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_big_pic;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        list = getIntent().getParcelableArrayListExtra("imgs");
        p = getIntent().getIntExtra("position", 0);
    }

    @Override
    protected void initView() {
        vp.setAdapter(new HotelBigPicAdapter(list, this));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {
        vp.setCurrentItem(p);
        title.setText(MUtils.getHotelPicCategary(list.get(0)));
        num.setText(vp.getCurrentItem() + 1 + "/" + list.size());
    }

    @OnPageChange(R.id.hotel_big_pic_vp)
    void onPageChange(int position) {
        num.setText(vp.getCurrentItem() + 1 + "/" + list.size());
    }

    @OnClick({R.id.activity_hotel_big_pic,R.id.hotel_big_pic_vp})
    void onclick() {
        finish();
    }


}
