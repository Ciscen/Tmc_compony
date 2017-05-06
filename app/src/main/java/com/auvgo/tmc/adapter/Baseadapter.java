package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by LC on
 */
public abstract class Baseadapter<T, K> extends BaseAdapter {
    protected List<? extends T> list;
    protected LayoutInflater inflater;
    protected int resourceId;
    protected Context context;

    public Baseadapter(Context context, List<? extends T> list, int resourceId) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.resourceId = resourceId;
        this.context = context;
    }

    public List<? extends T> getList() {
        return list;
    }

    public void setList(List<? extends T> list) {
        this.list = list;
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
        K viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(resourceId, null);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (K) convertView.getTag();
        }
        bindViews(viewHolder, position);
        return convertView;
    }


    /**
     * 返回自定义的ViewHolder
     */
    protected abstract K getViewHolder(View convertView);//在此方法中，创建ViewHolder对象，将ViewHolder中的字段赋值，并返回ViewHolder对象

    /**
     * 开始设置ViewHolder里的数据，即根据position进行数据更新
     *
     * @param viewHolder
     * @param position
     */
    protected abstract void bindViews(K viewHolder, int position);

}
