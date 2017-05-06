package com.auvgo.tmc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

/**
 * Created by lc on 2016/12/22
 */

public class PlaneListAdapter extends Baseadapter<PlaneListBean, PlaneListAdapter.ViewHolder> {

    private List<PlaneListBean> l;

    public PlaneListAdapter(Context context, List<PlaneListBean> list_all, List<PlaneListBean> list, int resourceId) {
        super(context, list, resourceId);
        this.l = list_all;
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        TextView start_time = (TextView) convertView.findViewById(R.id.item_plane_list_start_time);
        TextView end_time = (TextView) convertView.findViewById(R.id.item_plane_list_end_time);
        TextView price = (TextView) convertView.findViewById(R.id.item_plane_list_price);
        TextView orgname = (TextView) convertView.findViewById(R.id.item_plane_list_orgname);
        TextView arriname = (TextView) convertView.findViewById(R.id.item_plane_list_arriname);
        TextView cangwei = (TextView) convertView.findViewById(R.id.item_plane_list_cangwei);
        TextView airline = (TextView) convertView.findViewById(R.id.item_plane_list_airline);
        TextView flightinfo = (TextView) convertView.findViewById(R.id.item_plane_list_flightinfo);
        TextView seatnum = (TextView) convertView.findViewById(R.id.item_plane_list_seatnum);
        TextView share = (TextView) convertView.findViewById(R.id.item_plane_list_share);
        ImageView logo = (ImageView) convertView.findViewById(R.id.item_plane_list_logo);
        ImageView wei = (ImageView) convertView.findViewById(R.id.item_plane_list_wei);
        TextView jt = (TextView) convertView.findViewById(R.id.item_plane_list_jt);
        return new ViewHolder(start_time, end_time, price, orgname,
                arriname, cangwei, airline, flightinfo, seatnum, logo, wei, share, jt);
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        PlaneListBean bean = list.get(position);
        viewHolder.airline.setText(bean.getAirline());
        viewHolder.share.setVisibility(bean.isCodeShare() ? View.VISIBLE : View.INVISIBLE);
        viewHolder.start_time.setText(bean.getDepttime());
        viewHolder.end_time.setText(bean.getArritime());
        viewHolder.price.setText(String.valueOf(bean.getLow().getPrice()));
        viewHolder.orgname.setText(bean.getOrgname());
        viewHolder.arriname.setText(bean.getArriname());
        viewHolder.cangwei.setText(bean.getLow().getCodeDes() + "(" + bean.getLow().getDisdes() + ")");
        viewHolder.flightinfo.setText(bean.getCarriername() + "(" + bean.getPlanestyle() + ")");
        viewHolder.jt.setVisibility(bean.getStopnumber().equals("0") ? View.GONE : View.VISIBLE);
        boolean isEnough = bean.getLow().getSeatNum().equals("A");
        viewHolder.seatnum.setText(isEnough ? "充足" : "仅剩" + bean.getLow().getSeatNum() + "张");
        if (!isEnough) {
            viewHolder.seatnum.setTextColor(context.getResources().getColor(R.color.appTheme2));
        } else {
            viewHolder.seatnum.setTextColor(context.getResources().getColor(R.color.color_333));
        }
        StringBuilder sb = new StringBuilder();
        if (MUtils.isCabinWei(bean, sb)) {
            viewHolder.wei.setVisibility(View.VISIBLE);
            bean.setAirlineWei(true);
//            bean.setWeiItemStr(s.endsWith("、")? s.substring(0,sb.length()-1): s);
            bean.setWeiItemStr(sb.toString());
        } else {
            viewHolder.wei.setVisibility(View.GONE);
            bean.setAirlineWei(false);
            bean.setWeiItemStr("");
        }
        String carriecode = bean.getCarriecode();
        int id = context.getResources().getIdentifier("p" + carriecode.toLowerCase(), "mipmap", context.getPackageName());
        if (id == 0) {
            viewHolder.logo.setVisibility(View.GONE);
        } else {
            viewHolder.logo.setVisibility(View.VISIBLE);
            viewHolder.logo.setImageResource(id);
        }
    }

    class ViewHolder {
        TextView start_time, end_time, price, orgname, arriname, cangwei, airline, flightinfo, seatnum, share, jt;
        ImageView logo, wei;

        public ViewHolder(TextView start_time, TextView end_time, TextView price, TextView orgname,
                          TextView arriname, TextView cangwei, TextView airline, TextView flightinfo,
                          TextView seatnum, ImageView logo, ImageView wei, TextView share, TextView jt) {
            this.start_time = start_time;
            this.end_time = end_time;
            this.price = price;
            this.orgname = orgname;
            this.arriname = arriname;
            this.cangwei = cangwei;
            this.airline = airline;
            this.flightinfo = flightinfo;
            this.seatnum = seatnum;
            this.logo = logo;
            this.wei = wei;
            this.share = share;
            this.jt = jt;
        }
    }

}
