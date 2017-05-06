package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.AlterDetailBean;
import com.auvgo.tmc.utils.AppUtils;

import java.util.List;

/**
 * Created by lc on 2016/12/10
 */

public class AlterDetailPsgAdapter extends BaseAdapter {
    private List<AlterDetailBean.UsersBean> list;
    private LayoutInflater inflater;

    public AlterDetailPsgAdapter(Context context, List<AlterDetailBean.UsersBean> list) {
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_alter_psgs, null);
        View item_ticketNo = view.findViewById(R.id.item_alter_item_ticketNo);
        TextView name = (TextView) view.findViewById(R.id.item_alter_name);
        TextView no = (TextView) view.findViewById(R.id.item_alter_idNum);
        TextView ticketNo = (TextView) view.findViewById(R.id.item_alter_ticketNo);
        ImageView check = (ImageView) view.findViewById(R.id.item_alter_checked);
        name.setText(list.get(position).getUserName());
        no.setText(list.get(position).getUserIds());
        ticketNo.setText(AppUtils.getNoNullStr(list.get(position).getNewPiaohao()));
        check.setVisibility(View.GONE);
        item_ticketNo.setVisibility(View.GONE);
        return view;
    }
}
