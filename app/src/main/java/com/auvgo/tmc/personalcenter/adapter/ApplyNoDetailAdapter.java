package com.auvgo.tmc.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.personalcenter.bean.ApplyNoDetailBean;
import com.auvgo.tmc.personalcenter.interfaces.IApplyNoDetail;

import java.util.List;

/**
 * Created by lc on 2017/3/30
 */

public class ApplyNoDetailAdapter extends BaseAdapter {
    private List<? extends IApplyNoDetail> list;
    private LayoutInflater mInflater;

    public ApplyNoDetailAdapter(Context context, List<? extends IApplyNoDetail> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
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
        View v = mInflater.inflate(R.layout.item_apply_detail, null, false);
        TextView tv1 = (TextView) v.findViewById(R.id.item_apply_detail_tv1);
        TextView tv2 = (TextView) v.findViewById(R.id.item_apply_detail_tv2);
        TextView tv3 = (TextView) v.findViewById(R.id.item_apply_detail_tv3);
        IApplyNoDetail iApplyNoDetail = list.get(position);
        tv1.setText(iApplyNoDetail.getTv0());
        tv2.setText(iApplyNoDetail.getTv1());
        tv3.setText(iApplyNoDetail.getTv2());
        return v;
    }
}
