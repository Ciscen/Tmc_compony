package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;

import java.util.List;

/**
 * Created by lc on 2016/11/21
 */

public class TrainOrderDetailPsgAdapter extends BaseAdapter {
    private List<TrainOrderDetailBean.UsersBean> list;
    private LayoutInflater inflater;

    public TrainOrderDetailPsgAdapter(Context context, List<TrainOrderDetailBean.UsersBean> list) {
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
        int gaiqianstatus = list.get(position).getGaiqianstatus();
        if (gaiqianstatus == 0 || gaiqianstatus == 0) {
            gai.setVisibility(View.GONE);
        }
        int tuipiaostatus = list.get(position).getTuipiaostatus();
        if (tuipiaostatus == 0 || tuipiaostatus == 3) {
            tui.setVisibility(View.GONE);
        }
        name.setText(list.get(position).getUserName());
        no.setText(list.get(position).getUserIds().trim());
        return view;
    }
}
