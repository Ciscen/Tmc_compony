package com.auvgo.tmc.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.hotel.activity.HotelBreakActivity;
import com.auvgo.tmc.hotel.activity.HotelDetailActivity;
import com.auvgo.tmc.hotel.activity.HotelInfoActivity;
import com.auvgo.tmc.hotel.activity.HotelMapActivity;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.hotel.bean.HotelPolicyBean;
import com.auvgo.tmc.hotel.bean.RequestHotelDetailBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by lc on 2017/2/25
 */

public class HotelDetailCardView extends LinearLayout {
    private Context context;
    private OnHotelCardViewClick mListener;
    private TextView hotel_name, room_name, room_num, checkIn, checkOut, total_night,
            cancel, hotel, tel, map;
    private ImageView logo;
    private HotelOrderDetailBean mBean;

    public HotelDetailCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        initListener();
    }

    public void setmBean(HotelOrderDetailBean mBean) {
        this.mBean = mBean;
    }

    private void initListener() {
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBean != null) {
                    DialogUtil.showCancelDialog(context, mBean.getCancelTime());
                }
                if (mListener != null)
                    mListener.onCancelClick();
            }
        });
        map.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBean != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("lat", mBean.getLatitude());
                    bundle.putString("lng", mBean.getLongitude());
                    bundle.putString("name", mBean.getHotelName());
                    bundle.putString("addr", mBean.getHotelAddress());
                    Intent intent = new Intent(context, HotelMapActivity.class);
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                }
                if (mListener != null)
                    mListener.onLocationClick();
            }
        });
        hotel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBean != null) {
                    String hotelAddress = mBean.getHotelAddress();
                    String hotelId = mBean.getHotelId();
                    String hotelName = mBean.getHotelName();
                    Bundle bundle = new Bundle();
                    bundle.putString("hotelId", hotelId);
                    bundle.putString("hotelName", hotelName);
                    bundle.putString("addr", hotelAddress);
                    Intent intent = new Intent(context, HotelInfoActivity.class);
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                }
                if (mListener != null)
                    mListener.onHotelClick();
            }
        });
        tel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelPhone = mBean.getHotelPhone();
                AppUtils.callPhone(context, hotelPhone);
                if (mListener != null)
                    mListener.onTelClick();
            }
        });

    }

    private void requestPolicy() {
        RetrofitUtil.getHotelPolicy(context, MUtils.getRequestStringWithCityIdByPsg(mBean.getUsers(), mBean.getCityId()),
                null, new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            MyApplication.mHotelPolicy = new Gson().fromJson(bean.getData(), HotelPolicyBean.class);
                            gotoHotelDetail();
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
    }

    private void gotoHotelDetail() {
        Intent intent = new Intent(context, HotelDetailActivity.class);
        RequestHotelDetailBean rb = new RequestHotelDetailBean();
        rb.setArrivalDate(TimeUtils.getyyyy_MM_ddByTimestamp(mBean.getArrivalDate()));
        rb.setDepartureDate(TimeUtils.getyyyy_MM_ddByTimestamp(mBean.getDepartureDate()));
        rb.setHotelId(mBean.getHotelId());
        intent.putExtra("bean", rb);
        context.startActivity(intent);
    }

    private void initView() {
        View.inflate(context, R.layout.layout_hotel_card_view, this);
        findViews();
    }

    private void findViews() {
        hotel_name = (TextView) findViewById(R.id.hotel_card_view_name);
        room_name = (TextView) findViewById(R.id.hotel_card_view_room_name);
        room_num = (TextView) findViewById(R.id.hotel_card_view_room_num);
        checkIn = (TextView) findViewById(R.id.hotel_card_view_check_in);
        total_night = (TextView) findViewById(R.id.hotel_card_view_total_night);
        cancel = (TextView) findViewById(R.id.hotel_card_view_cancel);
        hotel = (TextView) findViewById(R.id.hotel_card_view_hotel);
        tel = (TextView) findViewById(R.id.hotel_card_view_tel);
        map = (TextView) findViewById(R.id.hotel_card_view_map);
        checkOut = (TextView) findViewById(R.id.hotel_card_view_check_out);
        logo = (ImageView) findViewById(R.id.hotel_card_view_breckfast);
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name.setText(hotel_name);
    }

    public void setRoom_name(String room_name) {
        this.room_name.setText(room_name);
    }

    public void setRoom_num(String room_num) {
        this.room_num.setText(room_num);
    }

    public void setCheckIn(long checkIn, long checkOut) {
        String in = TimeUtils.date2Format(new Date(checkIn), "yyyy-MM-dd");
        String out = TimeUtils.date2Format(new Date(checkOut), "yyyy-MM-dd");
        this.checkIn.setText(changePattern(in));
        this.checkOut.setText(changePattern(out));
        this.total_night.setText("(共" + TimeUtils.compareDay(in, out) + "晚)");
    }

    public void setLogoResource(int logo) {
        this.logo.setImageResource(logo);
    }

    @NonNull
    private String changePattern(String checkIn) {
        return checkIn.substring(5).replace("-", "月") + "日";
    }

    public void setmListener(OnHotelCardViewClick mListener) {
        this.mListener = mListener;
    }

    public interface OnHotelCardViewClick {
        void onCancelClick();

        void onHotelClick();

        void onTelClick();

        void onLocationClick();
    }
}
