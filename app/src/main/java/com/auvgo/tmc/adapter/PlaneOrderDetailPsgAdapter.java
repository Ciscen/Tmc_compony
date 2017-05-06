package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.interfaces.IPassenger;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

/**
 * Created by lc on 2017/1/9
 */

public class PlaneOrderDetailPsgAdapter extends BaseAdapter {
    private List<? extends IPassenger> list;
    private LayoutInflater inflater;
    private PlaneOrderDetailBean mBean;
    private boolean b;

    public PlaneOrderDetailPsgAdapter(Context context, List<PlaneOrderDetailBean.PassengersBean> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * @param b 是否隐藏退改小标
     */
    public PlaneOrderDetailPsgAdapter(Context context, List<? extends IPassenger> list, boolean b) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.b = b;
    }

    public PlaneOrderDetailPsgAdapter(Context context, PlaneOrderDetailBean mBean) {
        inflater = LayoutInflater.from(context);
        this.list = mBean.getPassengers();
        this.mBean = mBean;
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
        int id = list.get(position).getIdI();
        int gaiqianstatus = MUtils.getGaiqianstatusByPsgId(mBean, id);
        int tuipiaostatus = MUtils.getTuipiaostatusByPsgId(mBean, id);

        if (mBean == null || gaiqianstatus == 0) {
            gai.setVisibility(View.GONE);
        }
        if (mBean == null || tuipiaostatus == 0) {
            tui.setVisibility(View.GONE);
        }
        if (b) {
            tui.setVisibility(View.GONE);
            gai.setVisibility(View.GONE);
        }
        name.setText(list.get(position).getNameI());
        no.setText(String.valueOf(list.get(position).getCernoI()));
        return view;
    }
}
