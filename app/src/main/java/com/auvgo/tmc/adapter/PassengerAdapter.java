package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.UserBean;

import java.util.List;

/**
 * Created by lc on 2016/11/14
 */
public class PassengerAdapter extends Baseadapter<UserBean, PassengerAdapter.ViewHolder> {

    public PassengerAdapter(Context context, List<UserBean> list, int resourceId) {
        super(context, list, resourceId);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        TextView name = (TextView) convertView.findViewById(R.id.item_psg_sub_name);
        TextView id = (TextView) convertView.findViewById(R.id.item_psg_sub_id);
        ImageView check = (ImageView) convertView.findViewById(R.id.item_psg_sub_check);
        TextView dept = (TextView) convertView.findViewById(R.id.item_psg_sub_dept);
        return new ViewHolder(id, name, dept, check);
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        viewHolder.id.setText(list.get(position).getCertno() + "");
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.dept.setText(list.get(position).getDeptname());
        if (list.get(position).isChecked()) {
            viewHolder.check.setVisibility(View.VISIBLE);
        } else {
            viewHolder.check.setVisibility(View.INVISIBLE);
        }
    }


    static class ViewHolder {
        TextView name, id, dept;
        ImageView check;

        public ViewHolder(TextView id, TextView name, TextView dept, ImageView check) {
            this.id = id;
            this.name = name;
            this.check = check;
            this.dept = dept;
        }
    }
}
