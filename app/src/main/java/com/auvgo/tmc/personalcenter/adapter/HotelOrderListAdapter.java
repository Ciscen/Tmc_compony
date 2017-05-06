package com.auvgo.tmc.personalcenter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.bean.HotelOrderListBean;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;

import java.util.List;

/**
 * Created by lc on 2017/2/28
 */

public class HotelOrderListAdapter extends Baseadapter<HotelOrderListBean.ListBean, HotelOrderListAdapter.ViewHolder> {
    public HotelOrderListAdapter(Context context, List<? extends HotelOrderListBean.ListBean> list) {
        super(context, list, R.layout.item_hotel_order_list);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.name = (TextView) convertView.findViewById(R.id.item_hotel_order_list_name);
        vh.users = (TextView) convertView.findViewById(R.id.item_hotel_order_list_users);
        vh.state = (TextView) convertView.findViewById(R.id.item_hotel_order_list_state);
        vh.date = (TextView) convertView.findViewById(R.id.item_hotel_order_list_date);
        vh.price = (TextView) convertView.findViewById(R.id.item_hotel_order_list_price);
        vh.booktime = (TextView) convertView.findViewById(R.id.item_hotel_order_list_booktime);
        return vh;
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        HotelOrderListBean.ListBean lb = list.get(position);
        viewHolder.name.setText(lb.getHotelName());
        viewHolder.state.setText(MUtils.getHotelStateByCode(lb.getStatu(), lb.getApprovestatus(), lb.getPaystatus()));
        viewHolder.users.setText(lb.getChencheRen());
        String s1 = TimeUtils.getyyyy_MM_ddByTimestamp(lb.getArrivalDate());
        String s2 = TimeUtils.getyyyy_MM_ddByTimestamp(lb.getDepartureDate());
        viewHolder.date.setText(s1.substring(5)
                + " 至 " + s2.substring(5)
                + "  共" + TimeUtils.compareDay(s1, s2) + "晚");
        viewHolder.price.setText(String.format("￥%s", lb.getTotalprice()));
        viewHolder.booktime.setText(String.format("预定时间：%s", TimeUtils.getyyyyMMddHHmm(lb.getCreatetime())));
    }

    public static class ViewHolder {
        TextView name, users, state, date, price, booktime;
    }
}
