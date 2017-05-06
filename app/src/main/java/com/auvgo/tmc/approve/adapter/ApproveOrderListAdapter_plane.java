package com.auvgo.tmc.approve.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.plane.bean.PlaneOrderListBean;

import java.util.List;

/**
 * Created by lc on 2017/1/10
 */
public class ApproveOrderListAdapter_plane extends Baseadapter<PlaneOrderListBean.ListBean, ApproveOrderListAdapter_plane.ViewHolder> {

    public ApproveOrderListAdapter_plane(Context context, List<PlaneOrderListBean.ListBean> list) {
        super(context, list, R.layout.item_approve_order);
    }

    @Override
    protected ApproveOrderListAdapter_plane.ViewHolder getViewHolder(View convertView) {
        ApproveOrderListAdapter_plane.ViewHolder vh = new ApproveOrderListAdapter_plane.ViewHolder();
        vh.applyNo = (TextView) convertView.findViewById(R.id.item_approve_applyNo);
        vh.psgs = (TextView) convertView.findViewById(R.id.item_approve_psgs);
        vh.date = (TextView) convertView.findViewById(R.id.item_approve_date);
        vh.route = (TextView) convertView.findViewById(R.id.item_approve_route);
        return vh;
    }

    @Override
    protected void bindViews(ApproveOrderListAdapter_plane.ViewHolder viewHolder, int position) {
        PlaneOrderListBean.ListBean listBean = list.get(position);
        viewHolder.applyNo.setText("出差单号:" + listBean.getOrderno());
        viewHolder.date.setText(listBean.getDeptdate());
        viewHolder.psgs.setText(listBean.getChencheRen());
        viewHolder.route.setText(listBean.getOrgname() + "-" + listBean.getArriname());
    }

    class ViewHolder {
        TextView applyNo, psgs, date, route;
    }
}
