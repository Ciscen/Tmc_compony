package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.ToastUtils;

import java.util.List;

/**
 * Created by lc on 2017/2/24
 */

public class HotelBookPsgAdapter extends BaseAdapter {
    private List<UserBean> list;
    private LayoutInflater inflater;
    private Context context;
    private OnPsgClickListener listener;
    private int roomNum;

    public HotelBookPsgAdapter(Context context, List<UserBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return roomNum;
    }

    public void setListener(OnPsgClickListener listener) {
        this.listener = listener;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_hotel_book_psg, null, false);
        TextView name = (TextView) view.findViewById(R.id.item_hotel_book_psg_name);
        ImageView del = (ImageView) view.findViewById(R.id.item_hotel_book_psg_del);
        ImageView head = (ImageView) view.findViewById(R.id.item_hotel_book_psg_head);
        name.setText(list.get(position) == null ? "" : list.get(position).getName());
//        del.setVisibility(MyApplication.mUserInfoBean.getIfallowbook() == 0 ? View.GONE : View.VISIBLE);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0 && list.size() == 1) {
                    ToastUtils.showTextToast("房间数不能少于1间");
                    return;
                }
                list.remove(position);
                roomNum--;
                notifyDataSetChanged();
                if (listener != null) listener.onDelClick(roomNum);
            }
        });
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onHeadClick(position);
            }
        });
        return view;
    }

    public interface OnPsgClickListener {
        void onDelClick(int roomnum);

        void onHeadClick(int position);
    }
}
