package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.auvgo.tmc.hotel.activity.HotelInfoActivity;
import com.auvgo.tmc.hotel.activity.HotelMapActivity;
import com.auvgo.tmc.hotel.activity.HotelPicListActivity;
import com.auvgo.tmc.hotel.activity.HotelRoomDetailActivity;
import com.auvgo.tmc.hotel.adapter.HotelRoomListAdapter;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.hotel.bean.RequestHotelDetailBean;
import com.auvgo.tmc.hotel.interfaces.ViewManager_hoteldetail;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.Calendar.CalendarActivity;

import java.util.ArrayList;
import java.util.List;

import static com.auvgo.tmc.p.PHotelQuery.FIRSTTAG;
import static com.auvgo.tmc.p.PHotelQuery.SECONDTAG;

/**
 * Created by lc on 2017/2/17
 */

public class PHotelDetail extends BaseP {
    private ViewManager_hoteldetail vm;
    private RequestHotelDetailBean rBean;
    private HotelDetailBean mBean;
    private HotelRoomListAdapter adapter;

    public PHotelDetail(Context context, ViewManager_hoteldetail vm) {
        super(context);
        this.vm = vm;
    }

    public void getData() {
        RetrofitUtil.getHotelDetail(context, AppUtils.getJson(rBean), HotelDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (HotelDetailBean) o;
                    adapter = new HotelRoomListAdapter(context, mBean.getRoomList());
                    vm.updateView();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public void initData(Intent intent) {
        rBean = intent.getParcelableExtra("bean");
    }

    public HotelRoomListAdapter getAdapter() {
        return adapter;
    }

    public HotelDetailBean getmBean() {
        return mBean;
    }

    public void jumpTo(int flag) {
        if (mBean == null) return;
        Intent intent = new Intent();
        Class c = null;
        Bundle bundle = new Bundle();
        switch (flag) {
            case Constant.ACTIVITY_CALENDAR_FLAG:
                bundle.putBoolean("isSingleChose", false);
                bundle.putString("checkInDate", mBean.getArrivalDate());
                bundle.putString("checkOutDate", mBean.getDepartureDate());
                bundle.putString("firstTag", FIRSTTAG);
                bundle.putString("secondTag", SECONDTAG);
                intent.putExtra("bundle", bundle);
                c = CalendarActivity.class;
                break;
            case Constant.ACTIVITY_HOTEL_INFO_FLAG:
                if (mBean == null) return;
                bundle.putString("hotelName", mBean.getHotelName());
                bundle.putString("addr", mBean.getAddress());
                bundle.putString("hotelId", mBean.getHotelId());
                c = HotelInfoActivity.class;
                break;
            case Constant.ACTIVITY_HOTEL_PIC_LIST_FLAG:
                c = HotelPicListActivity.class;
                bundle.putParcelableArrayList("imgs", mBean.getHotelImageList());
                break;
            case Constant.ACTIVITY_HOTEL_MAP_FLAG:
                c = HotelMapActivity.class;
                bundle.putString("lat", mBean.getLatitude());
                bundle.putString("lng", mBean.getLongitude());
                bundle.putString("name", mBean.getHotelName());
                bundle.putString("addr", mBean.getAddress());
                break;
        }
        intent.setClass(context, c);
        intent.putExtra("bundle", bundle);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_HOTEL_LIST_FLAG);
    }

    public void receiveCld(Intent data) {
        String date1 = data.getStringExtra("firstDate");
        mBean.setArrivalDate(date1);
        rBean.setArrivalDate(date1);
        String date2 = data.getStringExtra("secondDate");
        mBean.setDepartureDate(date2);
        rBean.setDepartureDate(date2);
        vm.setData();
        getData();
    }

    public void jumpToRoomDetail(int position) {
        Intent intent = new Intent(context, HotelRoomDetailActivity.class);
        ArrayList<HotelDetailBean.HotelImageListBean> imgList = matchImgList(mBean.getRoomList().get(position).getRoomId());
        intent.putExtra("bean", mBean);
        intent.putExtra("roomPos", position);
        intent.putParcelableArrayListExtra("imgs", imgList);
        context.startActivity(intent);
    }

    private ArrayList<HotelDetailBean.HotelImageListBean> matchImgList(String roomId) {
        ArrayList<HotelDetailBean.HotelImageListBean> list = new ArrayList<>();
        List<HotelDetailBean.HotelImageListBean> imgList = mBean.getHotelImageList();
        if (imgList != null) {
            for (int i = 0; i < imgList.size(); i++) {
                String roomId1 = imgList.get(i).getRoomId();
                if (roomId1 != null && roomId1.equals(roomId == null ? "" : roomId)) {
                    list.add(imgList.get(i));
                }
            }
        }
        return list;
    }
}
