package com.auvgo.tmc.views.Calendar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.MyDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 使用介绍：
 * 使用Bundle传递数据
 * 1.如果是单选，传入isSingleChose = true,或者不传，直接接受返回的Intent中的date即可,已选日期传入checkInDate
 * 2.如果是双选，传入isSingleChose = false,checkInDate,checkOutDate,firstTag,secondTag。
 * 接收firstDate,secondDate
 */
public class CalendarActivity extends FragmentActivity implements MyCalendar3.OnDaySelectListener {

    String nowday;
    String tomorrow;
    SimpleDateFormat simpleDateFormat, sd1, sd2;

    private ListView rvCalendar;
    private ArrayList<String> canNotRentsList;
    private ArrayList<String> tempDate;

    private String userChooseInDate = "";

    private MonthAdapter adapter;
    private ArrayList<String> serverDate;

    private List<String> calendarList;

    private String date = "";
    public static final int FIRSTCLICK = 1;//第一次点击后的状态值
    public static final int SECONDCLICK = 2;//第二次点击后的状态值
    public static String checkInDate = "";//第一个选择日期
    public static String checkOutDate = "";//第二个选择日期
    private int activityFlag = 0;//标记来自于哪个页面的请求
    public static boolean isSingleChose = true;//标记是否是单选模式
    public static String firstTag;
    public static String secondTag;
    public static int choiceFlag = 0;//标记点击日历时的逻辑，0表示还未点击，1表示已经选择入住时间，2表示选择了离店时间
    public static boolean selectBefore;//是否往后选
    public static boolean isHotel;
    public static String limitDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dealIntent();
        initView();
        initData();
        List<String> listDate = getDateList();
        adapter = new MonthAdapter(listDate, calendarList);
        rvCalendar.setAdapter(adapter);
        if (selectBefore) {
            rvCalendar.setSelection(12);
        }
    }

    /**
     * 处理intent
     */
    private void dealIntent() {
        isSingleChose = true;
        firstTag = "";
        secondTag = "";
        if (null != getIntent()) {
            Bundle bundle = getIntent().getBundleExtra("bundle");
            isSingleChose = bundle.getBoolean("isSingleChose", true);
            checkInDate = bundle.getString("checkInDate");
            selectBefore = bundle.getBoolean("selectBefore", false);
            isHotel = bundle.getBoolean("isHotel", false);
            limitDate = bundle.getString("limitDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if (!isSingleChose) {
                checkOutDate = bundle.getString("checkOutDate");
                firstTag = bundle.getString("firstTag");
                secondTag = bundle.getString("secondTag");
            }
            if (checkInDate == null) {
                checkInDate = "";
            }
            if (TextUtils.isEmpty(checkOutDate)) {
                checkOutDate = TimeUtils.getTomorrow();
            }
        }
    }

    private void initView() {
        setContentView(R.layout.activity_calendar3);

        //  从意图对象中取出数据
        Intent intent = getIntent();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowday = simpleDateFormat.format(new Date());
        tomorrow = TimeUtils.getTomorrow();
        sd1 = new SimpleDateFormat("yyyy");
        sd2 = new SimpleDateFormat("dd");
        rvCalendar = (ListView) findViewById(R.id.rv_calendar);
        //创建集合存储所有不可租日期
        canNotRentsList = new ArrayList<>();
        tempDate = new ArrayList<>();
        serverDate = new ArrayList<>();
    }

    /**
     * 获取价格日历
     */
    private void getPriceCalendar() {
    }

    public void initData() {
        calendarList = new ArrayList<>();
    }


    /**
     * 点击日历后的接口回调
     */
    @Override
    public void onDaySelectListener(View view, String date, int position) {
        try {
            if (!selectBefore &&
                    (simpleDateFormat.parse(date).getTime() < simpleDateFormat.parse(nowday).getTime() ||
                            simpleDateFormat.parse(date).getTime() < simpleDateFormat.parse(limitDate == null ? nowday : limitDate).getTime())) {
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!intervalIs20(date) && isHotel) {
            DialogUtil.showDialog(this, "提示", "", "确定",
                    "如果您需要入住酒店超过20天，请致电400-606-0011，我们7X24小时为您服务！", null);
            return;
        }
        userChooseInDate = date;
        adapter.notifyDataSetChanged();


        if (!isSingleChose) {//如果是多选
            if (choiceFlag == 0) {//如果还未选择
                //TODO 选择入住时间的逻辑
                checkInDate = date;
                choiceFlag = FIRSTCLICK;
            } else if (choiceFlag == FIRSTCLICK) {//如果已经选择了入住
                //TODO 选择离店时间的逻辑
                try {
                    if (simpleDateFormat.parse(date).getTime() <= simpleDateFormat.parse(checkInDate).getTime()) {//如果所选离店日期早于入住日期
                        checkInDate = date;
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                checkOutDate = date;
                choiceFlag = SECONDCLICK;
                if (!isSingleChose) {
                    Intent intent = new Intent();
                    intent.putExtra("firstDate", checkInDate);
                    intent.putExtra("secondDate", checkOutDate);
                    setResult(Constant.ACTIVITY_CALENDAR_FLAG, intent);
                }
                finish();
            }
        } else {
            checkInDate = date;
            Intent intent = new Intent();
            intent.putExtra("date", userChooseInDate);
            setResult(Constant.ACTIVITY_CALENDAR_FLAG, intent);
            finish();
        }
    }

    private boolean intervalIs20(String date) {
        return !(!TextUtils.isEmpty(date) && !TextUtils.isEmpty(checkInDate)
                && (TimeUtils.compareDay(checkInDate, date) >= 20));
    }


    public List<String> getDateList() {
        List<String> list = new ArrayList<String>();
        Date date = new Date();
        int nowMon = date.getMonth() + 1;
        String yyyy = sd1.format(date);
        String dd = sd2.format(date);

        int nowYear = Integer.parseInt(yyyy);
        if (selectBefore) {
            nowYear--;
            if (nowMon == 12) {
                nowYear++;
                nowMon = 1;
            } else {
                nowMon++;
            }
        }
        for (int i = 0; i < 12; i++) {
            int stepYear = 0;
            int stepMonth = nowMon + i;
            // 往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = nowYear + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = nowYear + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
            if (i != 0) {
                dd = "01";
            }
            list.add(stepYear + "-" + getMon(stepMonth) + "-" + dd);
        }

        return list;
    }

    public String getMon(int mon) {
        String month = "";
        if (mon < 10) {
            month = "0" + mon;
        } else {
            month = "" + mon;
        }
        return month;
    }

    class MonthAdapter extends BaseAdapter {
        private List<String> list;
        private List<String> calendarList = new ArrayList<String>();

        public MonthAdapter(List<String> list, List<String> calendarList) {
            this.list = list;
            this.calendarList = calendarList;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = new MyCalendar3(CalendarActivity.this);
            Date currentMonthItem = null;
            try {
                currentMonthItem = simpleDateFormat.parse(list.get(position));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int dayOfFirstMonty = TimeUtils.getDD(System.currentTimeMillis());
            List<String> monthCalendarList = new ArrayList<>();
            if (null != calendarList && 0 != calendarList.size()) {

                if (0 == position) {
                    String currentDateStr = list.get(position);
                    currentDateStr = currentDateStr.substring(0, currentDateStr.length() - 2) + "01";
                    try {
                        currentMonthItem = simpleDateFormat.parse(currentDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                monthCalendarList = getMonthPriceList(position, dayOfFirstMonty, TimeUtils.getDayOfYear(System.currentTimeMillis()), currentMonthItem, calendarList);
            }
            ((MyCalendar3) convertView).setTheDay(CalendarActivity.this, currentMonthItem, /*canNotRentsList,*/ userChooseInDate,/*, theNearestCannotInDate, theLastCanInDate*/monthCalendarList);
            ((MyCalendar3) convertView).setOnDaySelectListener(CalendarActivity.this);
            return convertView;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (canNotRentsList != null) {
            canNotRentsList.clear();
            canNotRentsList = null;
        }
        if (tempDate != null) {
            tempDate.clear();
            tempDate = null;
        }
        if (serverDate != null) {
            serverDate.clear();
            serverDate = null;
        }
        if (choiceFlag == FIRSTCLICK) {
            checkInDate = "";
            checkOutDate = "";
            choiceFlag = 0;
        }
        choiceFlag = 0;
        firstTag = null;
        secondTag = null;
        limitDate = null;
//        if (checkInDate != null) {
//            checkInDate = "";
//        }
//        if (checkOutDate != null) {
//            checkOutDate = "";
//        }
        Log.d("TAG", "onDestroy: ");
    }

    private List<String> getMonthPriceList(int position, int firstMonthDays, int firstMonthDaysOfYear, Date currentDate, List<String> outPriceList) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(currentDate);
        int daysOfMonth = cal.getActualMaximum(Calendar.DATE);
        int currentMonthDaysOfYear = cal.get(Calendar.DAY_OF_YEAR);
        List<String> priceList = new ArrayList<>();
        priceList.addAll(outPriceList);
        List<String> emptyDaysInFirstWeekList = new ArrayList<String>();
        List<String> subList = new ArrayList<String>();
        int emptyDaysInFirstWeek = 0;

        int start = 0;
        LogFactory.i("MonthPosition=====" + position);
        if (0 == position) {
            daysOfMonth = daysOfMonth - firstMonthDays + 1;

            emptyDaysInFirstWeek = countNeedHowMuchEmpety(cal) + firstMonthDays - 1;
            if (emptyDaysInFirstWeek > 0) {
                for (int i = 0; i < emptyDaysInFirstWeek; i++) {
                    emptyDaysInFirstWeekList.add("");
                }
            }
            subList = priceList.subList(0, daysOfMonth);
            if (emptyDaysInFirstWeekList.size() > 0) {
                subList.addAll(0, emptyDaysInFirstWeekList);
            }
            return subList;
        } else {
            emptyDaysInFirstWeek = countNeedHowMuchEmpety(cal);
            if (emptyDaysInFirstWeek > 0) {
                for (int i = 0; i < emptyDaysInFirstWeek; i++) {
                    emptyDaysInFirstWeekList.add("");
                }
            }
            if (currentMonthDaysOfYear < firstMonthDaysOfYear) {
                if (0 == TimeUtils.getYear(System.currentTimeMillis()) % 4) {
                    currentMonthDaysOfYear += 366;
                } else {
                    currentMonthDaysOfYear += 365;
                }

            }
            LogFactory.i("firstMonthDaysOfYear" + firstMonthDays);
            LogFactory.i("currentMonthDaysOfYear" + currentMonthDaysOfYear);
            start = currentMonthDaysOfYear - firstMonthDaysOfYear;
        }
        if (priceList.size() > start) {
            if ((start + daysOfMonth) > priceList.size()) {
                subList = priceList.subList(start, priceList.size());
                if (emptyDaysInFirstWeekList.size() > 0) {
                    LogFactory.i("上边加了" + emptyDaysInFirstWeekList + "个空字符串");
                    subList.addAll(0, emptyDaysInFirstWeekList);
                }
                return subList;
            } else {
                LogFactory.i("start + daysOfMonth=======" + (start + daysOfMonth));
                subList = priceList.subList(start, start + daysOfMonth);
                if (emptyDaysInFirstWeekList.size() > 0) {
                    LogFactory.i("下边加了" + emptyDaysInFirstWeekList + "个空字符串");
                    subList.addAll(0, emptyDaysInFirstWeekList);
                }
                return subList;
            }
        } else {
            return new ArrayList<String>();
        }
    }

    private int countNeedHowMuchEmpety(Calendar cal) {
        int firstDayInWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return firstDayInWeek;
    }
}
