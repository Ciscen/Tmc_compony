package com.auvgo.tmc.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.MonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CldActivity extends Activity {
    /*VIEW*/
    private ListView lv;

    /*FIELD*/
    private String mMonthIntervalStart;/*描述日历显示区间,开始月份*/
    private String mMonthIntervalEnd;/*描述日历显示区间,结束月份*/
    private int mMonthes;/*描述日历显示区间,共经历几个月*/
    private String mDayIntervalStart;/*描述日历有效区间，开始日期*/
    private String mDayIntervalEnd; /*描述日历有效区间，结束日期*/
    private String mSelectedDate1;/*已经选择的日期，第一个*/
    private String mSelectedDate2;/*已经选择的日期，第二个*/
    private String mFirstTag;/*第一个按下的标识*/
    private String mSecondTag;/*第二个按下的标识*/


    private boolean mIsSingle;/*是否是单选模式*/
    private int mClickFlag = 0;/*点击标识*/
    private List<List<MonthView.MonthBean>> list;//存放时间日期
    private CalenderAdapter adapter;
    private Calendar c = Calendar.getInstance();//用于处理时间
    private Handler mHandler = new Handler();

    long day_start_mills;
    long day_end_mills;
    long selected1_mills;
    long selected2_mills;

    /*CONSTANT FIELD*/
    private static final String DATE_PATTEN = "yyyy-MM-dd";
    private static final int WAIT_FOR_FIRST = 0;
    private static final int WAIT_FOR_SECOND = 1;
    public static final String KEY_INTERVAL_MONTH = "KEY_INTERVAL_MONTH"; /*KEY,描述日历显示区间,开始月份*/
    public static final String KEY_INTERVAL_MONTHES = "KEY_INTERVAL_MONTH_SECOND"; /*KEY,描述日历显示区间,结束月份*/
    public static final String KEY_INTERVAL_DAY_FIRST = "KEY_INTERVAL_DAY_FIRST"; /*KEY,描述日历有效区间，开始日期*/
    public static final String KEY_INTERVAL_DAY_SECOND = "KEY_INTERVAL_DAY_SECOND"; /*KEY,描述日历有效区间，结束日期*/
    public static final String KEY_SELECTED_DATE_1 = "KEY_SELECTED_DATE_1"; /*KEY,描述第一个选择的日期*/
    public static final String KEY_SELECTED_DATE_2 = "KEY_SELECTED_DATE_2"; /*KEY,描述第二个选择的日期*/
    public static final String KEY_FIRST_TAG = "KEY_FIRST_TAG"; /*KEY,描述第二个选择的日期*/
    public static final String KEY_SECOND_TAG = "KEY_SECOND_TAG"; /*KEY,描述第二个选择的日期*/
    public static final String KEY_ISSINGLE = "KEY_ISSINGLE";/*KEY,是否是单选*/
    public static final String KEY_RESULT_FIRST = "KEY_RESULT_FIRST";/*KEY,结果中第一个日期*/
    public static final String KEY_RESULT_SECOND = "KEY_RESULT_SECOND";/*KEY,结果中第二个日期*/
    public static final int RESULT_CODE = 0x1;/*KEY,结果中第二个日期*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initIntent();
        initData();
        initListener();
        Log.d(TAG, "onCreate: 0000");
    }

    private void initIntent() {
        c.setTimeInMillis(System.currentTimeMillis());
        Intent intent = getIntent();
        if (intent != null) {
            mMonthIntervalStart = intent.getStringExtra(KEY_INTERVAL_MONTH);
            mMonthes = intent.getIntExtra(KEY_INTERVAL_MONTHES, 12);
            mDayIntervalStart = intent.getStringExtra(KEY_INTERVAL_DAY_FIRST);
            mDayIntervalEnd = intent.getStringExtra(KEY_INTERVAL_DAY_SECOND);
            mSelectedDate1 = intent.getStringExtra(KEY_SELECTED_DATE_1);
            mSelectedDate2 = intent.getStringExtra(KEY_SELECTED_DATE_2);
            mFirstTag = intent.getStringExtra(KEY_FIRST_TAG);
            mSecondTag = intent.getStringExtra(KEY_SECOND_TAG);
            mIsSingle = intent.getBooleanExtra(KEY_ISSINGLE, true);

            c = Calendar.getInstance();
            if (TextUtils.isEmpty(mMonthIntervalStart)) {
                mMonthIntervalStart = TimeUtils.getToday();
            }
            mMonthIntervalEnd = getMonthEnd();
            if (TextUtils.isEmpty(mDayIntervalStart)) {
                mDayIntervalStart = TimeUtils.getToday();
            }
            if (TextUtils.isEmpty(mDayIntervalEnd)) {
                mDayIntervalEnd = TimeUtils.getLastDayOfMonth(mMonthIntervalEnd);
            }
            if (mSelectedDate1 == null) {
                mSelectedDate1 = "";
            }
            if (mSelectedDate2 == null) {
                mSelectedDate2 = "";
            }
        }

        day_start_mills = TimeUtils.getTimeStamp(mDayIntervalStart, DATE_PATTEN);
        day_end_mills = TimeUtils.getTimeStamp(mDayIntervalEnd, DATE_PATTEN);
        selected1_mills = TimeUtils.getTimeStamp(mSelectedDate1.isEmpty() ? TimeUtils.getToday() : mSelectedDate1, DATE_PATTEN);
        selected2_mills = mSelectedDate2.isEmpty() ? 0 : TimeUtils.getTimeStamp(mSelectedDate2, DATE_PATTEN);
    }

    private String getMonthEnd() {
        c.setTimeInMillis(TimeUtils.getTimeStamp(mMonthIntervalStart, DATE_PATTEN));
        c.add(Calendar.MONTH, mMonthes);
        return TimeUtils.getyyyy_MM_dd(c);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new CalenderAdapter(this, list);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTimeInMillis(TimeUtils.getTimeStamp(mMonthIntervalStart, DATE_PATTEN));
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<List<MonthView.MonthBean>> lists = fillList();
                notifyData(lists);
            }
        }).start();
    }

    private void notifyData(final List<List<MonthView.MonthBean>> lists) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                initView(lists);
            }
        });
    }

    /**
     * 向list填充数据
     */
    private List<List<MonthView.MonthBean>> fillList() {
        List<List<MonthView.MonthBean>> list2 = null;
        if (mMonthes > 12) {
            list2 = new ArrayList<>();
        }
        for (int i = 0; i < mMonthes; i++) {
            //c.add(Calendar.MONTH, 1);//设置成当前月份
            int d = TimeUtils.getDaysInMonthByCalendar(c);//当前月一共多少天
            c.set(Calendar.DATE, 1);//第一天开始
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);//一个星期的第几天
            List<MonthView.MonthBean> l = new ArrayList<>();
            for (int j = 0; j < dayOfWeek - 1; j++) {
                MonthView.MonthBean mb = new MonthView.MonthBean();
                mb.setDateStateFlag(MonthView.MonthBean.NORMAL);
                l.add(mb);
            }
            for (int j = 0; j < d; j++) {
                MonthView.MonthBean mb = new MonthView.MonthBean();
                long timeInMillis = c.getTimeInMillis();
                String date = TimeUtils.getyyyy_MM_dd(c);
                mb.setDate(date);
                mb.setDateMills(timeInMillis);
                mb.setExtra(getClickFlagByDate(date));
                mb.setDateStateFlag(getCheckFlagByDate(timeInMillis));
                l.add(mb);
                c.add(Calendar.DATE, 1);
            }
            int dayOfWeek2 = c.get(Calendar.DAY_OF_WEEK);
            for (int j = 0; j < 7 - dayOfWeek2; j++) {
                MonthView.MonthBean mb = new MonthView.MonthBean();
                mb.setDateStateFlag(MonthView.MonthBean.NORMAL);
                l.add(mb);
            }
            if (i < 12) {
                list.add(l);
            } else {
                if (i == 12)
                    notifyData(null);
                list2.add(l);
            }
        }
        return list2;
    }

    private int getCheckFlagByDate(long currentMills) {
        if (currentMills < day_start_mills)
            return MonthView.MonthBean.CANNON_CLICK;
        if (currentMills > day_end_mills)
            return MonthView.MonthBean.CANNON_CLICK;
        if (currentMills == selected1_mills)
            return MonthView.MonthBean.START_DATE;
        if (currentMills == selected2_mills)
            return MonthView.MonthBean.END_DATE;
        if (currentMills > selected1_mills && currentMills < selected2_mills)
            return MonthView.MonthBean.INSIDE_DATE;
        return MonthView.MonthBean.NORMAL;
    }

    /**
     * 根据时间是否与传入的选中的日期相同，设置对应的tag
     */
    private String getClickFlagByDate(String date) {
        if (date.isEmpty()) return "";
        if (date.equals(mSelectedDate1))
            return mFirstTag;
        if (date.equals(mSelectedDate2))
            return mSecondTag;
        return "";
    }

    private void initView(List<List<MonthView.MonthBean>> lists) {
        if (lv == null)
            lv = (ListView) findViewById(R.id.calendar_lv);
        if (lists != null) {
            list.addAll(lists);
        }
        lv.setAdapter(adapter);
    }

    private void initListener() {
    }

    private class CalenderAdapter extends BaseAdapter {
        private List<List<MonthView.MonthBean>> aList;
        private LayoutInflater mInflater;

        public CalenderAdapter(Context context, List<List<MonthView.MonthBean>> aList) {
            this.aList = aList;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return aList.size();
        }

        @Override
        public Object getItem(int position) {
            return aList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_calendar_month, parent, false);
                vh = new ViewHolder();
                vh.mv = (MonthView) convertView.findViewById(R.id.item_calendar_mv);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            final List<MonthView.MonthBean> monthBeenList = aList.get(position);
            vh.mv.setData(monthBeenList);
            vh.mv.setListener(new MonthView.OnMonthViewItemClick() {
                @Override
                public void onItemClick(int p, MonthView.MonthBean date) {
                    MonthView.MonthBean monthBean = monthBeenList.get(p);
                    int dateStateFlag = monthBean.getDateStateFlag();
                    long timeStamp = monthBean.getDateMills();
                    if (dateStateFlag != MonthView.MonthBean.CANNON_CLICK) {
                        if (mClickFlag == WAIT_FOR_FIRST || mIsSingle) {
                            choseFirst(monthBean);
                            mClickFlag = WAIT_FOR_SECOND;
                        } else {
                            if (timeStamp <= selected1_mills) {
                                choseFirst(monthBean);
                            } else {
                                choseSecond(monthBean);
                            }
                        }
                        initList();
                        if (mIsSingle) {
                            setResultAndFinish();
                        } else if (selected2_mills != 0) {
                            setResultAndFinish();
                        }
                    }
                }
            });

            return convertView;
        }

        private void choseSecond(MonthView.MonthBean monthBean) {
            mSelectedDate2 = monthBean.getDate();
            selected2_mills = TimeUtils.getTimeStamp(mSelectedDate2, DATE_PATTEN);
        }

        private void choseFirst(MonthView.MonthBean monthBean) {
            mSelectedDate1 = monthBean.getDate();
            mSelectedDate2 = "";
            selected1_mills = TimeUtils.getTimeStamp(mSelectedDate1, DATE_PATTEN);
            selected2_mills = 0;
        }

        private void initList() {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    int s = aList.size();
                    for (int i = 0; i < s; i++) {
                        List<MonthView.MonthBean> monthBeenList = aList.get(i);
                        int size = monthBeenList.size();
                        for (int j = 0; j < size; j++) {
                            MonthView.MonthBean monthBean = monthBeenList.get(j);
                            monthBean.setExtra(getClickFlagByDate(monthBean.getDate()));
                            int checkFlag = getCheckFlagByDate(monthBean.getDateMills());
                            monthBean.setDateStateFlag(checkFlag);
                        }
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            };
            new Thread(r).start();
        }

        class ViewHolder {
            MonthView mv;
        }
    }

    private void setResultAndFinish() {
        Intent intent = new Intent();
        intent.putExtra(KEY_RESULT_FIRST, mSelectedDate1);
        intent.putExtra(KEY_RESULT_SECOND, mSelectedDate2);
        setResult(Constant.ACTIVITY_CALENDAR_FLAG, intent);
        finish();
    }
}
