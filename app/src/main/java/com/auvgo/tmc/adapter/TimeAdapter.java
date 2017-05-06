package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.TrainTimeBean;

import java.util.List;

/**
 * Created by lc on 2016/11/17
 */

public class TimeAdapter extends BaseAdapter {
    private List<TrainTimeBean.DataBean> list;
    private LayoutInflater inflater;
    private String fromStation, toStation;
    private Context context;
    private int p1 = -1;
    private int p2 = -1;

    public TimeAdapter(Context context, List<TrainTimeBean.DataBean> list, String from_station_name, String to_station_name) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        fromStation = from_station_name;
        this.context = context;
        toStation = to_station_name;
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
        ViewHolder vh = convertView == null ? null : (ViewHolder) convertView.getTag();
        int color = context.getResources().getColor(R.color.color_333);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_time, null);
            TextView station = (TextView) convertView.findViewById(R.id.item_time_station);
            TextView arrive = (TextView) convertView.findViewById(R.id.item_time_arrive);
            TextView off = (TextView) convertView.findViewById(R.id.item_time_off);
            TextView stay = (TextView) convertView.findViewById(R.id.item_time_stay);
            vh = new ViewHolder(station, arrive, off, stay);
            convertView.setTag(vh);
        }
        TrainTimeBean.DataBean dataBean = list.get(position);


        if (dataBean.getStation_name().equals(fromStation)) {
            p1 = position;
        }
        if (dataBean.getStation_name().equals(toStation)) {
            p2 = position;
        }

        if (p1 == -1) {
            color = context.getResources().getColor(R.color.color_999);
        } else if (p1 == position || p2 == position) {
            color = context.getResources().getColor(R.color.appTheme2);
        } else if (p1 >= 0) {
            if (p2 >= 0) {
                color = context.getResources().getColor(R.color.color_999);
            } else {
                color = context.getResources().getColor(R.color.color_333);
            }
        }


        vh.station.setTextColor(color);
        vh.arrive.setTextColor(color);
        vh.off.setTextColor(color);
        vh.stay.setTextColor(color);
        vh.station.setText(dataBean.getStation_name());
        vh.arrive.setText(dataBean.getArrive_time());
        vh.off.setText(dataBean.getStart_time());
        vh.stay.setText(dataBean.getStop_over_time());
        return convertView;
    }

    static class ViewHolder {
        TextView station, arrive, off, stay;

        public ViewHolder(TextView station, TextView arrive, TextView off, TextView stay) {
            this.station = station;
            this.arrive = arrive;
            this.off = off;
            this.stay = stay;
        }
    }
}
