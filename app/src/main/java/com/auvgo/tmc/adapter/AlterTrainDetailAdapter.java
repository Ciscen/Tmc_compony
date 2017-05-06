package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;

import java.util.List;

/**
 * Created by lc on 2016/12/6
 */

public class AlterTrainDetailAdapter extends BaseAdapter {
    private List<List<String>> list;
    private Context context;

    public AlterTrainDetailAdapter(Context context, List<List<String>> list) {
        this.list = list;
        this.context = context;
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
        View view = View.inflate(context, R.layout.item_train_detail_group, null);
        TextView seattype = (TextView) view.findViewById(R.id.item_train_detail_group_seattype);
        TextView lastnum = (TextView) view.findViewById(R.id.item_train_detail_group_lastnum);
        TextView price = (TextView) view.findViewById(R.id.item_train_detail_group_price);
        TextView book = (TextView) view.findViewById(R.id.item_train_detail_group_book);
        ImageView wei = (ImageView) view.findViewById(R.id.item_train_detail_group_wei);
        List<String> strings = list.get(position);
        seattype.setText(strings.get(0));
        lastnum.setText(strings.get(1) + "张");
        price.setText(strings.get(2));
        if (isWei(list.get(position).get(3))) {
            wei.setVisibility(View.VISIBLE);
        } else {
            wei.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private boolean isWei(String s) {
        if (MyApplication.mTrainPolicy == null) return false;
        //  判断传入的s是否在规则给定的坐席里面，在false，不在true
        if (MyApplication.mTrainPolicy.getDonche().contains(s)) {
            return false;
        }
        if (MyApplication.mTrainPolicy.getGaotie().contains(s)) {
            return false;
        }
        if (MyApplication.mTrainPolicy.getPukuai().contains(s)) {
            return false;
        }
        return true;
    }
}
