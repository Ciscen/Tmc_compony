package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.auvgo.tmc.utils.DESUtils;
import com.auvgo.tmc.utils.DensityUtils;
import com.auvgo.tmc.views.MyCheckbox;

import java.util.List;

/**
 * Created by lc on 2016/11/16
 */

public class FilterStationAdapter extends BaseAdapter {
    private List<String> list_all;
    private List<String> list_selected;
    private Context context;
    private MyCheckbox.OnClickListener listener;

    public FilterStationAdapter(Context context, List<String> list_all, List<String> list_selected) {
        this.list_all = list_all;
        this.list_selected = list_selected;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list_all.size();
    }

    public void setListener(MyCheckbox.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Object getItem(int position) {
        return list_all.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        MyCheckbox view = new MyCheckbox(context);
        view.setPadding(DensityUtils.dp2px(context, 31), 0, DensityUtils.dp2px(context, 31), 0);
        String s = list_all.get(position);
        view.setText(s);
        if (list_selected.contains(s)) {
            view.setChecked(true);
        }
        if (listener != null) {
            view.setOnclickListener(new MyCheckbox.OnClickListener() {
                @Override
                public void onClick(View v, int pos) {
                    listener.onClick(convertView, position);
                }
            });
        }
        return view;
    }
}
