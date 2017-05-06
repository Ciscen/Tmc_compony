package com.auvgo.tmc.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.CitiesBean;
import com.auvgo.tmc.views.PinnedHeaderListView;

import java.util.List;
import java.util.Locale;

/**
 * Created by LC on
 */
public class CityListAdapter_pinned extends BaseAdapter implements AbsListView.OnScrollListener, PinnedHeaderListView.PinnedHeaderAdapter {

    private List<CitiesBean.CityBean> list;
    private LayoutInflater mInflater;

    public CityListAdapter_pinned(Context context, List<CitiesBean.CityBean> list) {
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    public void refresh(List<CitiesBean.CityBean> list) {
        this.list = list;
        notifyDataSetChanged();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_city_list, parent, false);
            TextView header = (TextView) convertView.findViewById(R.id.city_list_header);
            TextView item = (TextView) convertView.findViewById(R.id.city_list_item);
            viewHolder = new ViewHolder(header, item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (needTitle(position)) {
            viewHolder.headerView.setVisibility(View.VISIBLE);
            viewHolder.headerView.setText(list.get(position).getH());
        } else {
            viewHolder.headerView.setVisibility(View.GONE);
        }
        viewHolder.itemView.setText(list.get(position).getName());

        return convertView;
    }

    private boolean needTitle(int position) {
        // 第一个肯定是分类
        if (position == 0) {
            return true;
        }

        // 异常处理
        if (position < 0) {
            return false;
        }

        // 当前  // 上一个
        CitiesBean.CityBean currentEntity = list.get(position);
        CitiesBean.CityBean previousEntity = list.get(position - 1);
        if (null == currentEntity || null == previousEntity) {
            return false;
        }

        String currentTitle = currentEntity.getH();
        String previousTitle = previousEntity.getH();
        if (null == previousTitle || null == currentTitle) {
            return false;
        }

        // 当前item分类名和上一个item分类名不同，则表示两item属于不同分类
        if (currentTitle.equals(previousTitle)) {
            return false;
        }

        return true;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).controlPinnedHeader(firstVisibleItem);
        }
    }

    @Override
    public int getPinnedHeaderState(int position) {
        position--;
        if (getCount() == 0 || position < 0) {
            return PinnedHeaderListView.PinnedHeaderAdapter.PINNED_HEADER_GONE;
        }

        if (isMove(position)) {
            return PinnedHeaderListView.PinnedHeaderAdapter.PINNED_HEADER_PUSHED_UP;
        }

        return PinnedHeaderListView.PinnedHeaderAdapter.PINNED_HEADER_VISIBLE;
    }

    private boolean isMove(int position) {
        // 获取当前与下一项
        CitiesBean.CityBean currentEntity = list.get(position);
        CitiesBean.CityBean nextEntity = list.get(position + 1);
        if (null == currentEntity || null == nextEntity) {
            return false;
        }

        // 获取两项header内容
        String currentTitle = currentEntity.getH();
        String nextTitle = nextEntity.getH();
        if (null == currentTitle || null == nextTitle) {
            return false;
        }

        // 当前不等于下一项header，当前项需要移动了
        return !currentTitle.equals(nextTitle);

    }

    @Override
    public void configurePinnedHeader(View headerView, int position, int alpaha) {
        // 设置标题的内容
        CitiesBean.CityBean itemEntity = list.get(--position);
        String headerValue = itemEntity.getH();

        if (!TextUtils.isEmpty(headerValue)) {
            TextView headerTextView = (TextView) headerView.findViewById(R.id.city_list_header);
            headerTextView.setText(headerValue);
        }
    }

    /**
     * 根据字母得到第一个出现的索引
     *
     * @param sectionIndex
     * @return
     */
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            char upperChar = list.get(i).getH().charAt(0);
            if (upperChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    private static class ViewHolder {
        public TextView headerView;
        public TextView itemView;

        public ViewHolder(TextView headerView, TextView itemView) {
            this.headerView = headerView;
            this.itemView = itemView;
        }
    }
}
