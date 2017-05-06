package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.utils.TimeUtils;

import java.util.List;

/**
 * Created by lc on 2017/3/6
 */

public class HotelBreakAdapter extends BaseAdapter {
    private List<HotelDetailBean.NightlyRatesBean> list;
    private LayoutInflater mInflater;
    private String ratename;

    public HotelBreakAdapter(Context context, List<HotelDetailBean.NightlyRatesBean> list, String ratename) {
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.ratename = ratename;
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
        View view = mInflater.inflate(R.layout.item_break, null);
        TextView date = (TextView) view.findViewById(R.id.item_break_date);
        TextView hanzao = (TextView) view.findViewById(R.id.item_break_hanzao);
        TextView price = (TextView) view.findViewById(R.id.item_break_price);
        HotelDetailBean.NightlyRatesBean nrb = list.get(position);
        date.setText(TimeUtils.getyyyy_MM_ddByTimestamp(nrb.getRateDate()));
        price.setText("￥" + nrb.getPrice());
        hanzao.setText(ratename);
        return view;
    }
}
