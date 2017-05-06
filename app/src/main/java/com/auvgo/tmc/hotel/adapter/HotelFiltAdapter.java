package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.hotel.bean.HotelLocationBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by lc on 2017/3/6
 */

public class HotelFiltAdapter extends Baseadapter<HotelLocationBean, HotelFiltAdapter.ViewHolder> {

    public HotelFiltAdapter(Context context, List<? extends HotelLocationBean> list) {
        super(context, list, R.layout.item_hotel_filt);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.fl = (TagFlowLayout) convertView.findViewById(R.id.item_hotel_filt_fl);
        vh.title = (TextView) convertView.findViewById(R.id.item_hotel_title);
        return vh;
    }

    @Override
    protected void bindViews(final ViewHolder viewHolder, final int position) {
        viewHolder.title.setText(list.get(position).getTypeName());
        final TagAdapter<HotelLocationBean.PositionsBean> adapter
                = new TagAdapter<HotelLocationBean.PositionsBean>
                (list.get(position).getPositions()) {
            @Override
            public View getView(FlowLayout parent, int p, HotelLocationBean.PositionsBean positionsBean) {
                TextView tv = (TextView) inflater.inflate(R.layout.item_filt_gv, viewHolder.fl, false);
                HotelLocationBean.PositionsBean pb = list.get(position).getPositions().get(p);
                tv.setText(pb.getName());
                if (pb.isChecked()) {
                    tv.setBackgroundResource(R.drawable.shape_fl_checked);
                    tv.setTextColor(context.getResources().getColor(R.color.appTheme1));
                } else {
                    tv.setBackgroundResource(R.drawable.shape_fl_normal);
                    tv.setTextColor(Color.BLACK);
                }
                return tv;
            }
        };
        viewHolder.fl.setAdapter(adapter);
        viewHolder.fl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int p, FlowLayout parent) {
                List<HotelLocationBean.PositionsBean> positions = list.get(position).getPositions();
                HotelLocationBean.PositionsBean pb = positions.get(p);
                pb.setChecked(!pb.isChecked());
                if (pb.isChecked() && p != 0) {
                    positions.get(0).setChecked(false);
                }
                initState(positions);
                adapter.notifyDataChanged();
                return false;
            }
        });
    }

    private void initState(List<HotelLocationBean.PositionsBean> positions) {
        if (positions.get(0).isChecked()) {
            for (int i = 0; i < positions.size(); i++) {
                if (i > 0 && positions.get(i).isChecked()) {
                    positions.get(i).setChecked(false);
                }
            }
        } else {
            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i).isChecked()) {
                    return;
                }
            }
        }
        positions.get(0).setChecked(true);
    }

    static class ViewHolder {
        TextView title;
        TagFlowLayout fl;
    }
}
