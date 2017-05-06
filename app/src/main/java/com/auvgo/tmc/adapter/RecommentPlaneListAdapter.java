package com.auvgo.tmc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

/**
 * Created by lc on 2017/1/4
 */

public class RecommentPlaneListAdapter extends Baseadapter<PlaneListBean, RecommentPlaneListAdapter.ViewHolder> {

    public RecommentPlaneListAdapter(Context context, List<PlaneListBean> list, int resourceId) {
        super(context, list, resourceId);
    }

    public RecommentPlaneListAdapter(Context context, List<PlaneListBean> list) {
        super(context, list, R.layout.item_recomment_list);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        TextView airline = (TextView) convertView.findViewById(R.id.item_plane_recomment_airline);
        TextView discount = (TextView) convertView.findViewById(R.id.item_plane_recomment_discount);
        TextView starttime = (TextView) convertView.findViewById(R.id.item_plane_recomment_start_time);
        TextView endtime = (TextView) convertView.findViewById(R.id.item_plane_recomment_end_time);
        TextView orgname = (TextView) convertView.findViewById(R.id.item_plane_recomment_orgname);
        TextView arriname = (TextView) convertView.findViewById(R.id.item_plane_recomment_arriname);
        TextView price = (TextView) convertView.findViewById(R.id.item_plane_recomment_price);
        TextView seatnum = (TextView) convertView.findViewById(R.id.item_plane_recomment_seatnum);
        TextView book = (TextView) convertView.findViewById(R.id.item_plane_recomment_book);
        ImageView logo = (ImageView) convertView.findViewById(R.id.item_plane_recomment_logo);
        ImageView wei = (ImageView) convertView.findViewById(R.id.item_plane_recomment_wei);

        return new ViewHolder(airline, discount, starttime, endtime, orgname, arriname, price, seatnum, book, logo, wei);
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        PlaneListBean bean = list.get(position);
        viewHolder.airline.setText(bean.getAirline());
        viewHolder.discount.setText(bean.getLow().getCodeDes() + "(" + bean.getLow().getDiscount() + "折)");
        viewHolder.starttime.setText(bean.getDepttime());
        viewHolder.endtime.setText(bean.getArritime());
        viewHolder.orgname.setText(bean.getOrgname());
        viewHolder.arriname.setText(bean.getArriname());
        viewHolder.price.setText(String.valueOf(bean.getLow().getPrice()));
        String seatNum = bean.getLow().getSeatNum();
        if (seatNum.equals("A")) {
            seatNum = "充足";
        } else {
            if (Integer.parseInt(seatNum) < 10) {
                seatNum = "仅剩" + seatNum + "张";
                viewHolder.seatnum.setTextColor(context.getResources().getColor(R.color.appTheme2));
            } else {
                viewHolder.seatnum.setTextColor(Color.BLACK);
                seatNum = seatNum + "张";
            }
        }
        viewHolder.seatnum.setText(seatNum);

        int id = context.getResources().getIdentifier("p" + bean.getCarriecode().toLowerCase(),
                "mipmap", context.getPackageName());
        if (id == 0) {
            viewHolder.logo.setVisibility(View.GONE);
        } else {
            viewHolder.logo.setImageResource(id);
        }
        StringBuilder sb = new StringBuilder();
        boolean cabinWei = MUtils.isCabinWei(bean, sb);
        bean.setAirlineWei(cabinWei);
        bean.setWeiItemStr(sb.toString());
        viewHolder.wei.setVisibility(cabinWei ? View.VISIBLE : View.GONE);
    }

    static class ViewHolder {
        TextView airline, discount, starttime, endtime, orgname, arriname, price, seatnum, book;
        ImageView logo, wei;

        public ViewHolder(TextView airline, TextView discount, TextView starttime, TextView endtime,
                          TextView orgname, TextView arriname, TextView price, TextView seatnum,
                          TextView book, ImageView logo, ImageView wei) {
            this.airline = airline;
            this.discount = discount;
            this.starttime = starttime;
            this.endtime = endtime;
            this.orgname = orgname;
            this.arriname = arriname;
            this.price = price;
            this.seatnum = seatnum;
            this.book = book;
            this.logo = logo;
            this.wei = wei;
        }
    }
}
