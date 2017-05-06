package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.utils.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by lc on 2017/2/21
 */

public class HotelRoomListAdapter extends Baseadapter<HotelDetailBean.RoomListBean, HotelRoomListAdapter.ViewHolder> {
    public HotelRoomListAdapter(Context context, List<? extends HotelDetailBean.RoomListBean> list, int resourceId) {
        super(context, list, resourceId);
    }

    public HotelRoomListAdapter(Context context, List<? extends HotelDetailBean.RoomListBean> list) {
        super(context, list, R.layout.item_hotel_detail_roomlist);
    }

    @Override
    protected HotelRoomListAdapter.ViewHolder getViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.name = (TextView) convertView.findViewById(R.id.item_hotel_room_list_name);
        vh.desc = (TextView) convertView.findViewById(R.id.item_hotel_room_list_desc);
        vh.price = (TextView) convertView.findViewById(R.id.item_hotel_room_list_price);
        vh.icon = (ImageView) convertView.findViewById(R.id.item_hotel_room_list_icon);
        vh.wei = (ImageView) convertView.findViewById(R.id.item_hotel_room_list_wei);
        vh.fan = (ImageView) convertView.findViewById(R.id.item_hotel_room_list_fan);
        return vh;
    }

    @Override
    protected void bindViews(HotelRoomListAdapter.ViewHolder viewHolder, int position) {
        HotelDetailBean.RoomListBean rlb = list.get(position);
        viewHolder.name.setText(rlb.getName());
        viewHolder.desc.setText(rlb.getArea() + "㎡|" + rlb.getBedType());
        viewHolder.price.setText(String.format("￥%s", AppUtils.keepNSecimal(String.valueOf(rlb.getLowRate()), 2)));
        int price = MyApplication.mHotelPolicy == null ? Integer.MAX_VALUE >> 2 :
                Integer.parseInt(MyApplication.mHotelPolicy.getPrice());
        viewHolder.wei.setVisibility(rlb.getLowRate() <=
                price || price < 0
                ? View.GONE : View.VISIBLE);
        Glide.with(context).load(rlb.getImageUrl())
                .asBitmap().placeholder(R.mipmap.hotel_room_list_icon_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.icon);
        viewHolder.fan.setVisibility(View.GONE);
    }

    static class ViewHolder {
        TextView name, desc, price;
        ImageView icon, wei, fan;
    }
}
