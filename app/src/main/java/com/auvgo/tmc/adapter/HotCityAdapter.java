package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.CitiesBean;

import java.util.List;


/**
 * Created by LC on .
 */
public class HotCityAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CitiesBean.CityBean> hotCityList;


    public void refresh(List<CitiesBean.CityBean> hotCityList) {
        this.hotCityList = hotCityList;
        notifyDataSetChanged();
    }

    public HotCityAdapter(Context context, List<CitiesBean.CityBean> hotCityList) {
        inflater = LayoutInflater.from(context);
        this.hotCityList = hotCityList;
    }

    @Override
    public int getCount() {
        return hotCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_hot_city, null);
            // 城市名称
            holder.tv_city_name = (TextView) convertView.findViewById(R.id.tv_city_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_city_name.setText(hotCityList.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        TextView tv_city_name;
    }
}
