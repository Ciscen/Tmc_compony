package com.auvgo.tmc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.plane.activity.PlanTuiGaiQianActivity;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

/**
 * Created by lc on 2016/12/24
 */

public class PlaneDetailAdapter extends BaseAdapter {
    private List<PlaneListBean.CangweisBean> list;
    private LayoutInflater inflater;
    private PlaneListBean bean;
    private Context context;

    public PlaneDetailAdapter(Context context, PlaneListBean bean) {
        this.list = bean.getCangweis();
        this.bean = bean;
        this.context = context;
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
    public View getView(int position, final View convertView, ViewGroup parent) {
        final PlaneListBean.CangweisBean cb = list.get(position);
        View view = inflater.inflate(R.layout.item_plane_detail, null);
        TextView cangwei = (TextView) view.findViewById(R.id.item_plane_detail_cangwei);
        TextView tuigaiqian = (TextView) view.findViewById(R.id.item_plane_detail_tuigaiqian);
        TextView price = (TextView) view.findViewById(R.id.item_plane_detail_price);
        TextView seatnum = (TextView) view.findViewById(R.id.item_plane_detail_seatnum);
        TextView book = (TextView) view.findViewById(R.id.item_plane_detail_book);
        ImageView wei = (ImageView) view.findViewById(R.id.item_plane_detail_wei);
        View tuigai = view.findViewById(R.id.item_plane_detail_tuigaiqian);
        tuigai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlanTuiGaiQianActivity.class);
                intent.putExtra("tuipiao", cb.getRefundrule());
                intent.putExtra("gaiqian", cb.getChangerule());
                context.startActivity(intent);
            }
        });
        String codeDes = cb.getCodeDes();
        if (codeDes == null) cangwei.setText(cb.getDisdes() + "");
        else cangwei.setText(String.format("%s%s", cb.getDisdes(), codeDes.replace("全价", "")));
        price.setText("￥" + cb.getPrice());
        seatnum.setText(cb.getSeatNum().equals("A") ? "充足" : cb.getSeatNum() + "张");
        if ((!cb.getSeatNum().equals("A")) && Integer.parseInt(cb.getSeatNum()) < 10) {
            seatnum.setTextColor(context.getResources().getColor(R.color.appTheme2));
        } else {
            seatnum.setTextColor(context.getResources().getColor(R.color.black));
        }
        boolean airlineWei = bean.isAirlineWei();
        boolean cangweiWei = isCangweiWei(airlineWei, cb);
        if (airlineWei || cangweiWei) {
            wei.setVisibility(View.VISIBLE);
            cb.setWei(true);
        } else {
            wei.setVisibility(View.GONE);
            cb.setWei(false);
        }
        return view;
    }

    private boolean isCangweiWei(boolean isAirlineWei, PlaneListBean.CangweisBean cb) {
        cb.setLowestInThisFlight(true);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPrice() < cb.getPrice()) {
                cb.setLowestInThisFlight(false);
                break;
            }
        }
        if (isAirlineWei) return true;
        PlanePolicyBean mPlanePolicy = MyApplication.mPlanePolicy;
        if (mPlanePolicy == null) return false;
        if (mPlanePolicy.getCabinlimit() == 1 && cb.getDiscount() > mPlanePolicy.getCabinzhekou())
            return true;
        //开启了价格限制
        return mPlanePolicy.getFlightlimit() == 1 && !cb.isLowestInThisFlight();
    }
}
