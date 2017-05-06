package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.hotel.interfaces.IHotelListBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by lc on 2017/2/16
 */

public class HotelListAdapter extends Baseadapter<IHotelListBean, HotelListAdapter.ViewHolder> {
    public HotelListAdapter(Context context, List<? extends IHotelListBean> list) {
        super(context, list, R.layout.item_hotel_list);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.hotelName = (TextView) convertView.findViewById(R.id.item_hotel_list_name);
        vh.mark = (TextView) convertView.findViewById(R.id.item_hotel_list_mark);
        vh.score = (TextView) convertView.findViewById(R.id.item_hotel_list_score);
        vh.level = (TextView) convertView.findViewById(R.id.item_hotel_list_level);
        vh.comment = (TextView) convertView.findViewById(R.id.item_hotel_list_comments);
        vh.pos = (TextView) convertView.findViewById(R.id.item_hotel_list_pos);
        vh.price = (TextView) convertView.findViewById(R.id.item_hotel_list_price);
        vh.others = (ViewGroup) convertView.findViewById(R.id.item_hotel_list_other_ll);
        vh.img = (ImageView) convertView.findViewById(R.id.item_hotel_list_img);
        vh.distance = (TextView) convertView.findViewById(R.id.item_hotel_list_distance);
        return vh;
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        IHotelListBean beanImpl = list.get(position);
        viewHolder.hotelName.setText(beanImpl.getHotelName());
        viewHolder.mark.setText(beanImpl.getMark());
        viewHolder.mark.setVisibility(View.GONE);//暂时隐藏
        viewHolder.score.setText(beanImpl.getScore());
        viewHolder.comment.setText(beanImpl.getComments());
        viewHolder.level.setText(beanImpl.getLevel());
        viewHolder.pos.setText(beanImpl.getPos());
        viewHolder.price.setText(beanImpl.getPrice());
        viewHolder.distance.setText(beanImpl.getDistance() == 0 ? "" : "距您" + beanImpl.getDistance() + "m");
//        viewHolder.others.getChildAt(0).setVisibility();
        Glide.with(context).load(beanImpl.getImg()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.hotel_list_imgdefault)
                .into(viewHolder.img);
    }

    static class ViewHolder {
        TextView hotelName, mark, score, comment, level, pos, price, distance;
        ImageView img;
        ViewGroup others;
    }
}
