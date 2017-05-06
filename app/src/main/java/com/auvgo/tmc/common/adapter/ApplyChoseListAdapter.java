package com.auvgo.tmc.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.common.interfaces.IApplyChose;

import java.util.List;

/**
 * Created by lc on 2017/3/29
 */

public class ApplyChoseListAdapter extends Baseadapter<IApplyChose, ApplyChoseListAdapter.ViewHolder> {

    public ApplyChoseListAdapter(Context context, List<? extends IApplyChose> list) {
        super(context, list, R.layout.item_apply_no_list);
    }

    public ApplyChoseListAdapter(Context context, List<? extends IApplyChose> list, int id) {
        super(context, list, id);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        IApplyChose iac = list.get(position);
        viewHolder.tv1.setText(String.format("出差单号：%s", iac.getNo()));
        viewHolder.tv2.setText(iac.getDate());
        viewHolder.tv3.setText(iac.getNames());
        if (viewHolder.tv4 != null)
            viewHolder.tv4.setText(String.format("出差原因：%s", iac.getReason()));
    }

    public static class ViewHolder {
        TextView tv1, tv2, tv3, tv4;

        public ViewHolder(View convertView) {
            tv1 = (TextView) convertView.findViewById(R.id.item_apply_no_tv1);
            tv2 = (TextView) convertView.findViewById(R.id.item_apply_no_tv2);
            tv3 = (TextView) convertView.findViewById(R.id.item_apply_no_tv3);
            tv4 = (TextView) convertView.findViewById(R.id.item_apply_no_tv4);
        }
    }
}
