package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.hotel.bean.TrafficInfo;

import java.util.List;

/**
 * Created by lc on 2017/3/13
 */

public class HotelInfoTrafficAdapter extends BaseAdapter {
    private List<TrafficInfo> list;
    private LayoutInflater mInflater;

    public HotelInfoTrafficAdapter(List<TrafficInfo> list, Context context) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
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
        View v = mInflater.inflate(R.layout.item_hotel_info, null);
        TextView desc = (TextView) v.findViewById(R.id.item_hotel_info_desc);
        TextView distance = (TextView) v.findViewById(R.id.item_hotel_info_distance);
        desc.setText(list.get(position).getStationName());
        distance.setText(list.get(position).getDistance());
        return v;
    }
}
