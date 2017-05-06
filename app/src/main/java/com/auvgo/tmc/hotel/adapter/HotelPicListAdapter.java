package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.hotel.activity.HotelBigPicActivity;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by lc on 2017/3/1
 */

public class HotelPicListAdapter extends Baseadapter<List<HotelDetailBean.HotelImageListBean>, HotelPicListAdapter.ViewHolder> {

    public HotelPicListAdapter(Context context, List<ArrayList<HotelDetailBean.HotelImageListBean>> list) {
        super(context, list, R.layout.item_hotel_pic_list);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.gv = (GridView) convertView.findViewById(R.id.item_hotel_pic_list_gv);
        vh.title = (TextView) convertView.findViewById(R.id.item_hotel_pic_list_title);
        return vh;
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, final int position) {
        List<HotelDetailBean.HotelImageListBean> l = this.list.get(position);
        if (l.size() > 0) {
            viewHolder.title.setVisibility(View.VISIBLE);
            viewHolder.gv.setVisibility(View.VISIBLE);
            String title = l.get(0).getImageTitle();
            if (title == null) {
                title = "其他";
            } else if (title.equals("外观")) {
            } else if (title.equals("客房")) {
            } else {
                title = "设施";
            }
            viewHolder.title.setText(String.format(Locale.CHINESE, "%s(%d)", title, l.size()));
            viewHolder.gv.setAdapter(new HotelPicGridAdapter(l, context));
            viewHolder.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    Intent intent = new Intent(context, HotelBigPicActivity.class);
                    intent.putParcelableArrayListExtra("imgs", (ArrayList<? extends Parcelable>) list.get(position));
                    intent.putExtra("position", pos);
                    context.startActivity(intent);
                }
            });
        } else {
            viewHolder.title.setVisibility(View.GONE);
            viewHolder.gv.setVisibility(View.GONE);
        }
    }

     static class ViewHolder {
        TextView title;
        GridView gv;
    }
}
