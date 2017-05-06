package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.plane.bean.PlaneReturnDetailBean;

import java.util.List;

/**
 * Created by lc on 2017/1/9
 */

public class PlaneReturnOrderDetailPsgAdapter extends BaseAdapter {
    private List<PlaneReturnDetailBean.TuipiaoPassengersBean> list;
    private LayoutInflater inflater;

    public PlaneReturnOrderDetailPsgAdapter(Context context, List<PlaneReturnDetailBean.TuipiaoPassengersBean> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
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
        View view = inflater.inflate(R.layout.item_train_order_detail_psgs, null);
        TextView name = (TextView) view.findViewById(R.id.item_train_order_detail_name);
        TextView no = (TextView) view.findViewById(R.id.item_train_order_detail_no);
        ImageView gai = (ImageView) view.findViewById(R.id.item_train_order_detail_gai);
        ImageView tui = (ImageView) view.findViewById(R.id.item_train_order_detail_tui);
        gai.setVisibility(View.GONE);
        tui.setVisibility(View.GONE);
        name.setText(list.get(position).getName());
        no.setText(String.valueOf(list.get(position).getCertno()));
        return view;
    }
}
