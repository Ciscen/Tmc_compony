package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.UserBean;

import java.util.List;

/**
 * Created by LC on
 */
public class PsgChosedAdapter extends BaseAdapter {

    private List<UserBean> list;
    private LayoutInflater inflater;

    public PsgChosedAdapter(Context context, List<UserBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
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
        View view = inflater.inflate(R.layout.item_choosed_passenger, null);
        TextView name = (TextView) view.findViewById(R.id.tv_choosed_passenger);
        name.setText(list.get(position).getName());
        return view;
    }

}
