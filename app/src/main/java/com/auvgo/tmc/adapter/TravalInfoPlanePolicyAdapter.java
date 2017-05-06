package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;

import java.util.List;

/**
 * Created by lc on 2017/1/12
 */

public class TravalInfoPlanePolicyAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater inflater;
    private int size;

    public TravalInfoPlanePolicyAdapter(Context context, List<String> list) {
        this.list = list;
        this.size = list.size();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (list.size() + 1) / 2;
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
        View view = inflater.inflate(R.layout.item_mytravalinfo_vg, null);
        TextView tv1 = (TextView) view.findViewById(R.id.item_mytravalinfo_tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.item_mytravalinfo_tv2);
        tv1.setText(list.get(position * 2));
        tv2.setText(list.get(position * 2 + 1));
        return view;
    }
}
