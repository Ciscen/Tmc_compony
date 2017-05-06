package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

/**
 * Created by lc on 2017/3/1
 */

public class HotelPicGridAdapter extends BaseAdapter {
    private List<HotelDetailBean.HotelImageListBean> list;
    private LayoutInflater mInflater;
    private Context context;

    public HotelPicGridAdapter(List<HotelDetailBean.HotelImageListBean> list, Context context) {
        this.list = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_hotel_pic_list_gv, null);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        MUtils.loadImg(context, list.get(position).getUrl(), img, R.mipmap.hotel_room_list_icon_default);
        return view;
    }
}
