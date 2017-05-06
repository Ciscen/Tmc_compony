package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.hotel.activity.HotelBreakActivity;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.utils.AppUtils;

import java.util.List;

/**
 * Created by lc on 2017/2/23
 */

public class HotelRoomDetailListAdapter extends Baseadapter<HotelDetailBean.RoomListBean.RatePlanListBean, HotelRoomDetailListAdapter.ViewHolder> {
    private OnHanZaoClickListener mListener;

    public HotelRoomDetailListAdapter(Context context, List<? extends HotelDetailBean.RoomListBean.RatePlanListBean> list) {
        super(context, list, R.layout.item_hotel_room_info);
    }

    public void setmListener(OnHanZaoClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.breckfast = (TextView) convertView.findViewById(R.id.hotel_room_info_breckfast);
        vh.cancelable = (TextView) convertView.findViewById(R.id.hotel_room_info_cancelable);
        vh.lastNum = (TextView) convertView.findViewById(R.id.hotel_room_info_lastnum);
        vh.price = (TextView) convertView.findViewById(R.id.item_hotel_room_info_price);
        vh.fan = (TextView) convertView.findViewById(R.id.item_hotel_room_info_fan);
        vh.book = (TextView) convertView.findViewById(R.id.item_hotel_room_info_book_tv);
        vh.paytype = (TextView) convertView.findViewById(R.id.item_hotel_room_info_paytype);
        vh.guarantee = (TextView) convertView.findViewById(R.id.item_hotel_room_info_guarantee);
        vh.ll = (LinearLayout) convertView.findViewById(R.id.hotel_room_info_other_ll);
        vh.wei = (ImageView) convertView.findViewById(R.id.item_hotel_room_info_wei);
        vh.out = (TextView) convertView.findViewById(R.id.item_hotel_room_info_out);
        return vh;
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, final int position) {
        final HotelDetailBean.RoomListBean.RatePlanListBean bean = list.get(position);
        viewHolder.breckfast.setText(bean.getRatePlanName());
        viewHolder.cancelable.setText(AppUtils.getNoNullStr(bean.getCancelRule()));
        int num = bean.getCurrentAlloment();
        boolean isSelfPay = bean.getPaymentType().equals("SelfPay");
        viewHolder.lastNum.setText(num < 10 ? "仅剩" + num + "间" : "");
        viewHolder.price.setText(AppUtils.keepNSecimal(String.valueOf(bean.getAverageRate()), 2));
//        viewHolder.fan.setText(isSelfPay ? "返现" : "立减" + bean.getCoupon() * -1);
        viewHolder.fan.setVisibility(View.GONE);
        viewHolder.paytype.setText(isSelfPay ? "到店付" : "预付");
        if (num < 0) {
            viewHolder.book.setVisibility(View.GONE);
            viewHolder.paytype.setVisibility(View.GONE);
            int color = context.getResources().getColor(R.color.color_ccc);
            viewHolder.breckfast.setTextColor(color);
            viewHolder.cancelable.setTextColor(color);
            viewHolder.lastNum.setVisibility(View.INVISIBLE);
        } else if (num == 0) {
            viewHolder.lastNum.setVisibility(View.INVISIBLE);
        }
        viewHolder.breckfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HotelBreakActivity.class);
                intent.putExtra("bean", bean);
                context.startActivity(intent);
            }
        });

        viewHolder.guarantee.setVisibility(bean.isGurantee() ? View.VISIBLE : View.GONE);
        viewHolder.ll.getChildAt(0).setVisibility(bean.getDrrRuleIds() != null ? View.VISIBLE : View.GONE);
        viewHolder.ll.getChildAt(1).setVisibility(bean.isInstantConfirmation() ? View.VISIBLE : View.GONE);
        int price = MyApplication.mHotelPolicy == null ? Integer.MAX_VALUE >> 2 : Integer.parseInt(MyApplication.mHotelPolicy.getPrice());
        viewHolder.wei.setVisibility(bean.getAverageRate() <= price || price < 0
                ? View.GONE : View.VISIBLE);
        viewHolder.breckfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onHanzaoClick(position);
            }
        });
    }

    public static class ViewHolder {
        TextView breckfast, cancelable, lastNum, price, fan, book, paytype, guarantee, out;
        LinearLayout ll;
        ImageView wei;
    }

    public interface OnHanZaoClickListener {
        void onHanzaoClick(int position);
    }
}
