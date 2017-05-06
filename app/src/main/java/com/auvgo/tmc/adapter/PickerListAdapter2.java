package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.views.MyPickerView;

import java.util.List;

/**
 * Created by lc on 2016/11/11
 */

public class PickerListAdapter2 extends Baseadapter<MyPickerView.Selection, PickerListAdapter2.ViewHolder> {

    public PickerListAdapter2(Context context, List<? extends MyPickerView.Selection> list) {
        super(context, list, R.layout.item_picker_lv);
    }

    public PickerListAdapter2(Context context, List<? extends MyPickerView.Selection> list,int id) {
        super(context, list, id);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) convertView.findViewById(R.id.item_picker_text);
        viewHolder.img = (ImageView) convertView.findViewById(R.id.item_picker_img);
        return viewHolder;
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        viewHolder.img.setVisibility(list.get(position).isChecked() ? View.VISIBLE : View.INVISIBLE);
        viewHolder.name.setText(list.get(position).getName());
    }

    static class ViewHolder {
        TextView name;
        ImageView img;
    }
}
