package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

/**
 * Created by lc on 2016/11/17
 */

public class TrainDetailAdapter extends BaseExpandableListAdapter {
    private List<List<String>> canBook;
    private LayoutInflater inflater;
    private Context context;
    private String trainCode;

    public TrainDetailAdapter(List<List<String>> canBook, Context context, String train_code) {
        this.canBook = canBook;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.trainCode = train_code;
    }

    @Override
    public int getGroupCount() {
        return canBook.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 2;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return canBook.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_train_detail_group, null);
        TextView seattype = (TextView) view.findViewById(R.id.item_train_detail_group_seattype);
        TextView lastnum = (TextView) view.findViewById(R.id.item_train_detail_group_lastnum);
        TextView price = (TextView) view.findViewById(R.id.item_train_detail_group_price);
        TextView book = (TextView) view.findViewById(R.id.item_train_detail_group_book);
        ImageView wei = (ImageView) view.findViewById(R.id.item_train_detail_group_wei);
        List<String> strings = canBook.get(groupPosition);
        seattype.setText(strings.get(0));
        String s = strings.get(1);
        if (s.equals("有")) {
            lastnum.setText("有");
            lastnum.setTextColor(context.getResources().getColor(R.color.color_333));
        } else {
            lastnum.setText(s);
            lastnum.setTextColor(context.getResources().getColor(R.color.appTheme2));
        }
        price.setText(strings.get(2));
        if (isExpanded)
            book.setText("收起");
        else
            book.setText("预订");
        if (isWei(canBook.get(groupPosition).get(3))) {
            wei.setVisibility(View.VISIBLE);
        } else {
            wei.setVisibility(View.INVISIBLE);
        }
        if (!MUtils.isCanbook(canBook.get(groupPosition).get(3), trainCode)) {
            book.setBackgroundResource(R.drawable.shape_button_gray);
        }
        return view;
    }

    private boolean isWei(String s) {
        if (MyApplication.mTrainPolicy == null) return false;
        //  判断传入的s是否在规则给定的坐席里面，在false，不在true
        if (MyApplication.mTrainPolicy.getDonche().contains(s)) {
            return false;
        }
        if (MyApplication.mTrainPolicy.getGaotie().contains(s)) {
            return false;
        }
        if (MyApplication.mTrainPolicy.getPukuai().contains(s)) {
            return false;
        }

        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_train_detail_sub, null);
        ImageView img = (ImageView) view.findViewById(R.id.item_train_detail_sub_img);
        TextView tv = (TextView) view.findViewById(R.id.item_train_detail_sub_tv);
        TextView tv2 = (TextView) view.findViewById(R.id.item_train_detail_sub_tv2);
        TextView book = (TextView) view.findViewById(R.id.item_train_detail_book);
//        if (childPosition == 0) {
//            img.setImageResource(R.mipmap.train_sendticket);
//            tv.setText("火车票+送票上门");
//            tv2.setText("配送费¥" + MyApplication.mComSettingBean.getFuwufei().getTrainpeison() + "/张，提前3天预订");
//        }
        if (childPosition == 0) {
            img.setImageResource(R.mipmap.train_xl);
            tv.setText("行旅预订");
            tv2.setText("7*24小时预订服务");
        }
        if (childPosition == 1) {
            img.setImageResource(R.mipmap.train_12306);
            tv.setText("12306预订");
            tv2.setText("需12306预订，出票成功率更高");
        }
        if (!MUtils.isCanbook(canBook.get(groupPosition)
                .get(3), trainCode)) {
            book.setBackgroundResource(R.drawable.shape_button_gray);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
