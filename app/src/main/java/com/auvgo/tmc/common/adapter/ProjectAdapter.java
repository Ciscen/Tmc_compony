package com.auvgo.tmc.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.bean.ProjectBean;

import java.util.List;

/**
 * Created by lc on 2016/12/2
 */

public class ProjectAdapter extends Baseadapter<ProjectBean.ListBean, ProjectAdapter.ViewHolder> {
    public ProjectAdapter(Context context, List<ProjectBean.ListBean> list, int resourceId) {
        super(context, list, resourceId);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.check = (ImageView) convertView.findViewById(R.id.item_project_costcenter_check);
        viewHolder.no = (TextView) convertView.findViewById(R.id.item_project_costcenter_no);
        viewHolder.name = (TextView) convertView.findViewById(R.id.item_project_costcenter_name);
        return viewHolder;
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.no.setText(list.get(position).getCode());
    }

    class ViewHolder {
        TextView no, name;
        ImageView check;
    }
}
