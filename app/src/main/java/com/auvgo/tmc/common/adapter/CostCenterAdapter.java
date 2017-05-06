package com.auvgo.tmc.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.bean.CostCenterBean;
import com.auvgo.tmc.bean.ProjectBean;

import java.util.List;

/**
 * Created by lc on 2016/12/2
 */

public class CostCenterAdapter extends Baseadapter<CostCenterBean.ListBean, CostCenterAdapter.ViewHolder> {
    public CostCenterAdapter(Context context, List<CostCenterBean.ListBean> list, int resourceId) {
        super(context, list, resourceId);
    }

    @Override
    protected CostCenterAdapter.ViewHolder getViewHolder(View convertView) {
        CostCenterAdapter.ViewHolder viewHolder = new CostCenterAdapter.ViewHolder();
        viewHolder.check = (ImageView) convertView.findViewById(R.id.item_project_costcenter_check);
        viewHolder.no = (TextView) convertView.findViewById(R.id.item_project_costcenter_no);
        viewHolder.name = (TextView) convertView.findViewById(R.id.item_project_costcenter_name);
        return viewHolder;
    }

    @Override
    protected void bindViews(CostCenterAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.no.setText(list.get(position).getCode());
    }

    class ViewHolder {
        TextView no, name;
        ImageView check;
    }
}
