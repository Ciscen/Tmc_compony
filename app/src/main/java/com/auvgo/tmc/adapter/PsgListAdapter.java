package com.auvgo.tmc.adapter;

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

import java.util.List;

/**
 * Created by lc on 2016/11/18
 */

public class PsgListAdapter extends BaseAdapter {

    private List<UserBean> list;
    private LayoutInflater inflater;
    private OnPsgChangeListener listener;

    public interface OnPsgChangeListener {
        void onPsgChange();
    }

    public PsgListAdapter(Context context, List<UserBean> list, OnPsgChangeListener listener) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_train_book_psg, null);
        TextView name = (TextView) view.findViewById(R.id.item_train_book_psg_name);
        TextView no = (TextView) view.findViewById(R.id.item_train_book_psg_no);
        ImageView del = (ImageView) view.findViewById(R.id.item_train_book_psg_del);
        del.setVisibility(MyApplication.mUserInfoBean.getIfallowbook() == 0 ? View.GONE : View.VISIBLE);
        UserBean userBean = list.get(position);
        name.setText(userBean.getName());
        no.setText(userBean.getCertno());
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                listener.onPsgChange();
            }
        });
        return view;
    }
}
