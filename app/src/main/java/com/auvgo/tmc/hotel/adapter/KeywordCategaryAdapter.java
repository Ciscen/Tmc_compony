package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.hotel.bean.HotelKeywordBean;

import java.util.List;

/**
 * Created by lc on 2017/3/9
 */

public class KeywordCategaryAdapter extends BaseAdapter {
    private List<HotelKeywordBean> list;
    private Context context;
    private int CHECKED_COLOR;
    private int UNCHECKED_COLOR;

    public KeywordCategaryAdapter(Context context, List<HotelKeywordBean> list) {
        this.context = context;
        this.list = list;
        CHECKED_COLOR = context.getResources().getColor(R.color.white);
        UNCHECKED_COLOR = context.getResources().getColor(R.color.color_f5f5);
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
        TextView tv = (TextView) View.inflate(context, R.layout.item_categary, null);
        HotelKeywordBean hotelKeywordBean = list.get(position);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
        tv.setLayoutParams(lp);
        tv.setText(hotelKeywordBean.getName());
        tv.setBackgroundColor(hotelKeywordBean.isChecked() ? CHECKED_COLOR : UNCHECKED_COLOR);
        return tv;
    }
}
