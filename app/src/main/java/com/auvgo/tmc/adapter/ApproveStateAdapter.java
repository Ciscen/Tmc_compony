package com.auvgo.tmc.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.ApprovesBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;

import java.util.List;

/**
 * Created by lc on 2016/11/24
 */

public class ApproveStateAdapter extends BaseAdapter {
    private List<ApprovesBean> list;
    private Context context;
    private LayoutInflater inflater;

    public ApproveStateAdapter(Context context, List<ApprovesBean> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
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
        View view = inflater.inflate(R.layout.item_approve_state, null);
        TextView no = (TextView) view.findViewById(R.id.item_approve_state_no);
        TextView name = (TextView) view.findViewById(R.id.item_approve_state_name);
        TextView tel = (TextView) view.findViewById(R.id.item_approve_state_tel);
        TextView state = (TextView) view.findViewById(R.id.item_approve_state_state);
        no.setText(position + 1 + "");
        ApprovesBean approveStateBean = list.get(position);
        name.setText(approveStateBean.getName());
        String mobile = approveStateBean.getMobile();
        tel.setText(TextUtils.isEmpty(mobile) ? "没有手机号信息" : mobile);
        int status = approveStateBean.getStatus();
        switch (status) {
            case 0:
                state.setText("待审批");
                break;
            case 1:
                state.setText("通过");
                break;
            case 2:
                state.setText("否决");
                break;

        }
        return view;
    }
}
