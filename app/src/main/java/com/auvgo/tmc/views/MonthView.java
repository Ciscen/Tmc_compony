package com.auvgo.tmc.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;


import com.auvgo.tmc.BuildConfig;
import com.auvgo.tmc.R;

import java.util.List;

/**
 * Created by lc on 2017/4/17
 */

public class MonthView extends FrameLayout {
    private TextView title;
    private GridView gv;
    private GVAdapter adapter;
    private List<MonthBean> days;
    private Context context;
    private static int COLOR_BLUE = 0xff3F51B5;
    private static int COLOR_333 = 0xff333333;
    private static int COLOR_WHITE = 0xffffffff;
    private static int COLOR_GRAY_INSIDE_BG = 0xfff2f2f2;//中间变色部分背景
    private static int COLOR_CANNOT_CLICK = 0xff999999;
    private OnMonthViewItemClick listener;

    public MonthView(@NonNull Context context) {
        super(context, null);
    }

    public MonthView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView(context);
        initLisener();
    }

    private void initLisener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                    listener.onItemClick(position, days.get(position));
            }
        });
    }

    private void initData(Context context) {
        this.context = context;
//        Resources resources = context.getResources();
//        COLOR_BLUE = resources.getColor(R.color.checkedColor);
//        COLOR_333 = resources.getColor(R.color.uncheckedColor);
//        COLOR_WHITE = resources.getColor(R.color.white);
//        COLOR_GRAY_INSIDE_BG = resources.getColor(R.color.color_f2f2);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_month_view, this, true);
        title = (TextView) findViewById(R.id.calendar_month_title_tv);
        gv = (GridView) findViewById(R.id.calendar_month_gv);
    }

    /**
     * 设置、更新数据
     */
    public void setData(List<MonthBean> days) {
        this.days = days;
        if (adapter == null) {
            adapter = new GVAdapter(context, days);
            gv.setAdapter(adapter);
        } else {
            adapter.update(days);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            gv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        String date = days.get(10).getDate();
        String[] split = date.split("-");
        title.setText(split[0] + "年" + split[1] + "月");
    }

    public void setListener(OnMonthViewItemClick listener) {
        this.listener = listener;
    }

    private static class GVAdapter extends BaseAdapter {
        private List<MonthBean> days;
        private LayoutInflater mInflater;

        GVAdapter(Context context, List<MonthBean> days) {
            mInflater = LayoutInflater.from(context);
            this.days = days;
        }

        @Override
        public int getCount() {
            return days.size();
        }

        @Override
        public Object getItem(int position) {
            return days.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_calendar_day, null);
                vh = new ViewHolder();
                vh.date = (TextView) convertView.findViewById(R.id.item_calendar_day);
                vh.desc = (TextView) convertView.findViewById(R.id.item_calendar_desc);
                vh.vg = convertView.findViewById(R.id.item_calendar_ll);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            MonthBean monthBean = days.get(position);
            String date = monthBean.getDate();
            vh.date.setText(date.isEmpty() ? date : date.substring(8));
            if (!TextUtils.isEmpty(monthBean.desc)) {
                vh.desc.setVisibility(VISIBLE);
                vh.desc.setText(monthBean.desc);
            } else {
                vh.desc.setVisibility(GONE);
            }
            /*设置item背景及字体颜色*/
            setItemBg(vh, monthBean.dateStateFlag, position);
            if (!TextUtils.isEmpty(monthBean.extra)) {
                vh.date.setText(monthBean.extra);
            }
            return convertView;
        }

        private void setItemBg(ViewHolder v, int checkFlag, int position) {
            int bgcolor = COLOR_WHITE;
            int textcolor = COLOR_333;
            switch (checkFlag) {
                case MonthBean.NORMAL:
                    textcolor = COLOR_333;
                    bgcolor = COLOR_WHITE;
                    break;
                case MonthBean.START_DATE:
                    textcolor = COLOR_WHITE;
                    bgcolor = COLOR_BLUE;
                    break;
                case MonthBean.END_DATE:
                    textcolor = COLOR_WHITE;
                    bgcolor = COLOR_BLUE;
                    break;
                case MonthBean.INSIDE_DATE:
                    textcolor = COLOR_333;
                    bgcolor = COLOR_GRAY_INSIDE_BG;
                    break;
                case MonthBean.CANNON_CLICK:
                    textcolor = COLOR_CANNOT_CLICK;
                    bgcolor = COLOR_WHITE;
                    break;
            }
            /*
            非选中的周末
             */
            if (checkFlag == MonthBean.NORMAL) {
                if ((position + 1) % 7 == 0 || (position + 1) % 7 == 1) {
                    textcolor = COLOR_BLUE;
                } else {
                    textcolor = COLOR_333;
                }
            }

            v.vg.setBackgroundColor(bgcolor);
            v.date.setTextColor(textcolor);
        }


        public void update(List<MonthBean> days) {
            this.days = days;
        }

        static class ViewHolder {
            TextView date, desc;
            View vg;
        }
    }

    public static class MonthBean {
        /*就是日期*/
        String date;
        /*供以后扩展用，比如显示价格*/
        String desc;
        /*表示按下后显示的字*/
        String extra;
        long dateMills;
        int dateStateFlag;
        public static final int NORMAL = 0;
        public static final int START_DATE = 1;
        public static final int INSIDE_DATE = 2;
        public static final int END_DATE = 3;
        public static final int CANNON_CLICK = 4;

        public String getDate() {
            return date == null ? "" : date;
        }

        public long getDateMills() {
            return dateMills;
        }

        public void setDateMills(long dateMills) {
            this.dateMills = dateMills;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getExtra() {
            return extra == null ? "" : extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public int getDateStateFlag() {
            return dateStateFlag;
        }

        public void setDateStateFlag(int dateStateFlag) {
            this.dateStateFlag = dateStateFlag;
        }
    }

    public interface OnMonthViewItemClick {
        void onItemClick(int position, MonthBean date);
    }
}
