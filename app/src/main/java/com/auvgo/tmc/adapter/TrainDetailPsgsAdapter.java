package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;

import java.util.List;

/**
 * Created by lc on 2016/12/5
 */

public class TrainDetailPsgsAdapter extends Baseadapter<TrainOrderDetailBean.UsersBean, TrainDetailPsgsAdapter.ViewHoler> {


    private boolean canSelect = true;

    public TrainDetailPsgsAdapter(Context context, List<TrainOrderDetailBean.UsersBean> list, int resourceId) {
        super(context, list, resourceId);
    }

    public TrainDetailPsgsAdapter(Context context, List<TrainOrderDetailBean.UsersBean> list) {
        super(context, list, R.layout.item_alter_psgs);
    }

    public TrainDetailPsgsAdapter(Context context, boolean canSelect, List<TrainOrderDetailBean.UsersBean> list, int resourceId) {
        super(context, list, resourceId);
        this.canSelect = canSelect;
    }


    @Override
    protected ViewHoler getViewHolder(View convertView) {
        TextView name = (TextView) convertView.findViewById(R.id.item_alter_name);
        TextView idNo = (TextView) convertView.findViewById(R.id.item_alter_idNum);
        TextView ticketNo = (TextView) convertView.findViewById(R.id.item_alter_ticketNo);
        ImageView checked = (ImageView) convertView.findViewById(R.id.item_alter_checked);
        return new ViewHoler(name, idNo, ticketNo, checked);
    }

    @Override
    protected void bindViews(ViewHoler viewHolder, int position) {
        viewHolder.name.setText(list.get(position).getUserName());
        viewHolder.idNo.setText(list.get(position).getUserIds());
        viewHolder.ticketNo.setText(list.get(position).getPiaohao());
        if (list.get(position).isChecked()) {
            viewHolder.checked.setImageResource(R.mipmap.checked_icon);
        } else {
            viewHolder.checked.setImageResource(R.mipmap.unchecked_icon);
        }
        if (!canSelect) {
            viewHolder.checked.setVisibility(View.GONE);
            viewHolder.ticketNo.setTextColor(context.getResources().getColor(R.color.color_666));
        }
    }

    class ViewHoler {
        TextView name, idNo, ticketNo;
        ImageView checked;

        public ViewHoler(TextView name, TextView idNo, TextView ticketNo, ImageView checked) {
            this.name = name;
            this.idNo = idNo;
            this.ticketNo = ticketNo;
            this.checked = checked;
        }
    }

}
