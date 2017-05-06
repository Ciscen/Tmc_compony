package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.CitiesBean;

import java.util.List;
import java.util.Locale;

/**
 * Created by LC on
 */
public class CityListAdapter extends BaseAdapter {

    private List<CitiesBean.CityBean> list;
    private LayoutInflater mInflater;

    public CityListAdapter(Context context, List<CitiesBean.CityBean> list) {
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
        int section = getSectionForPosition(position);

        if (getPositionForSection(section) == position) {
            viewHolder.headerView.setVisibility(View.VISIBLE);
            String firstChar;
            if (list.get(position).getName().startsWith("长")) {
                firstChar = "c";
            } else {
                firstChar = String.valueOf(list.get(position).getJianp().charAt(0));
            }
            viewHolder.headerView.setText(firstChar.toUpperCase(Locale.CHINA));
        } else {
            viewHolder.headerView.setVisibility(View.GONE);
        }
        viewHolder.itemView.setText(list.get(position).getName());

        return convertView;
    }

    /**
     * 根据字母得到第一个出现的索引
     *
     * @param sectionIndex
     * @return
     */
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String firstChar = "";
            if (list.get(i).getName().startsWith("长")) {
                firstChar = "c";
            } else {
                firstChar = String.valueOf(list.get(i).getJianp().charAt(0));
            }
            char upperChar = firstChar.toUpperCase(Locale.CHINA).charAt(0);
//            char upperChar = firstChar.charAt(0);
            if (upperChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据position获得字母
     *
     * @param position
     * @return
     */
    public int getSectionForPosition(int position) {
        String firstChar = "";
        if (list.get(position).getName().startsWith("长")) {
            firstChar = "c";
        } else {
            firstChar = String.valueOf(list.get(position).getJianp().charAt(0));
        }
        return firstChar.toUpperCase(Locale.CHINA).charAt(0);
    }

    /**
     * //     * 获取地名的首字母
     * //     *
     * //     * @param string 地名
     * //     * @return 地名的首字母
     * //
     */
//    public String getFirstChar(String string) {
////        String headerPinYin = PinYin.getPinYin(string);
//        String headerPinYin = AppUtils.getPinYinHeadChar(string);
//        char firstChar = headerPinYin.charAt(0);
//        return String.valueOf(firstChar);
//    }

    private static class ViewHolder {
        public TextView headerView;
        public TextView itemView;

        public ViewHolder(TextView headerView, TextView itemView) {
            this.headerView = headerView;
            this.itemView = itemView;
        }
    }
}
