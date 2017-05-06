package com.auvgo.tmc.approve.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.bean.TrainOrderListBean;

import java.util.List;

/**
 * Created by lc on 2016/11/30
 */

public class ApproveOrderListAdapter_train extends Baseadapter<TrainOrderListBean.ListBean, ApproveOrderListAdapter_train.ViewHolder> {

    public ApproveOrderListAdapter_train(Context context, List<TrainOrderListBean.ListBean> list) {
        super(context, list, R.layout.item_approve_order);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.applyNo = (TextView) convertView.findViewById(R.id.item_approve_applyNo);
        vh.psgs = (TextView) convertView.findViewById(R.id.item_approve_psgs);
        vh.date = (TextView) convertView.findViewById(R.id.item_approve_date);
        vh.route = (TextView) convertView.findViewById(R.id.item_approve_route);
        return vh;
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        TrainOrderListBean.ListBean listBean = list.get(position);
        viewHolder.applyNo.setText("出差单号:" + listBean.getOrderno());
        viewHolder.date.setText(listBean.getTravelTime());
        viewHolder.psgs.setText(listBean.getChencheRen());
        viewHolder.route.setText(listBean.getFromStation() + "-" + listBean.getArriveStation());
    }

    class ViewHolder {
        TextView applyNo, psgs, date, route;
    }
}
