package com.auvgo.tmc.approve.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.bean.HotelOrderListBean;
import com.auvgo.tmc.utils.TimeUtils;

import java.util.List;

/**
 * Created by lc on 2017/1/10
 */
public class ApproveOrderListAdapter_hotel extends Baseadapter<HotelOrderListBean.ListBean, ApproveOrderListAdapter_hotel.ViewHolder> {

    public ApproveOrderListAdapter_hotel(Context context, List<HotelOrderListBean.ListBean> list) {
        super(context, list, R.layout.item_approve_order_hotel);
    }

    @Override
    protected ApproveOrderListAdapter_hotel.ViewHolder getViewHolder(View convertView) {
        ApproveOrderListAdapter_hotel.ViewHolder vh = new ApproveOrderListAdapter_hotel.ViewHolder();
        vh.applyNo = (TextView) convertView.findViewById(R.id.item_approve_applyNo);
        vh.psgs = (TextView) convertView.findViewById(R.id.item_approve_psgs);
        vh.date = (TextView) convertView.findViewById(R.id.item_approve_date);
        vh.route = (TextView) convertView.findViewById(R.id.item_approve_route);
        return vh;
    }

    @Override
    protected void bindViews(ApproveOrderListAdapter_hotel.ViewHolder viewHolder, int position) {
        HotelOrderListBean.ListBean listBean = list.get(position);
        String s1 = TimeUtils.getyyyy_MM_ddByTimestamp(listBean.getArrivalDate());
        String s2 = TimeUtils.getyyyy_MM_ddByTimestamp(listBean.getDepartureDate());

        viewHolder.applyNo.setText(String.format("出差单号：%s", listBean.getOrderno()));
        viewHolder.date.setText(s1.substring(5)
                + " 至 " + s2.substring(5)
                + "  共" + TimeUtils.compareDay(s1, s2) + "晚");
        viewHolder.psgs.setText(listBean.getChencheRen());
        viewHolder.route.setText(listBean.getHotelName());
    }

    class ViewHolder {
        TextView applyNo, psgs, date, route;
    }
}
