package com.auvgo.tmc.views.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.views.MyGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author baiyuliang
 */
public class MyCalendar3 extends FrameLayout {
    public static long nd = 1000 * 24L * 60L * 60L;//一天的毫秒数
    private Context context;

    private Activity activity;

    private List<String> gvList;//存放天

    private OnDaySelectListener callBack;//回调函数

    private String nowday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");//日期格式化

    private MyGridView gv;
    private calendarGridViewAdapter gridViewAdapter;
    private List<String> calendarPriceList;

    View view;//获取布局，开始初始化
    TextView tvTitle;
    Calendar cal;//获取日历实例

    /**
     * 当前item的月份
     */
    private Date currentMonthItem;
    /**
     * 当前点击的某一天（乘机日期）
     */
    private String userChooseInDate;


    /**
     * 构造函数
     */
    public MyCalendar3(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * 构造函数
     *
     * @param context
     */
    public MyCalendar3(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void setTheDay(Activity activity, Date currentMonthItem,/* List<String> canNotRentsList, */String userChooseInDate,/*String theNearestCannotInDate, String theLastCanInDate*/List<String> calendarPriceList) {
        this.activity = activity;
        this.currentMonthItem = currentMonthItem;
        this.userChooseInDate = userChooseInDate;
        this.calendarPriceList = calendarPriceList;
        setData();
    }


    private void init() {
        gvList = new ArrayList<>();//存放当月“天”集合
        cal = Calendar.getInstance();
        view = LayoutInflater.from(context).inflate(R.layout.comm_calendar3, this, true);
        tvTitle = (TextView) view.findViewById(R.id.tv_calendar_title);
        gv = (MyGridView) view.findViewById(R.id.gv_calendar);
        gridViewAdapter = new calendarGridViewAdapter();
        gv.setAdapter(gridViewAdapter);
    }


    /**
     * 初始化日期以及view等控件
     */
    private void setData() {
        cal.setTime(currentMonthItem);//cal设置为当天的
        cal.set(Calendar.DATE, 1);//cal设置当前day为当前月第一天
        setGvListData(countNeedHowMuchEmpety(cal), getDayNumInMonth(cal), cal.get(Calendar.YEAR), getMonth((cal.get(Calendar.MONTH) + 1)));
        tvTitle.setText(cal.get(Calendar.YEAR) + "年" + String.valueOf(currentMonthItem.getMonth() + 1) + "月");
        gridViewAdapter.notifyDataSetChanged();
        gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                String choiceDay = gvList.get(position);
                String[] date = choiceDay.split(",");
                String day = date[1];
                if (!" ".equals(day)) {
                    if (Integer.parseInt(day) < 10) {
                        day = "0" + date[1];
                    }
                    choiceDay = date[0] + "-" + day;
                    userChooseInDate = choiceDay;
                    if (callBack != null) {//调用回调函数回调数据
                        callBack.onDaySelectListener(arg1, choiceDay, position);
                    }
                }
            }
        });
    }

    /**
     * 为gridview中添加需要展示的数据
     */
    private void setGvListData(int tempSum, int dayNumInMonth, int Y, String M) {
        gvList.clear();

        String YM = Y + "-" + M;

        for (int i = 0; i < tempSum; i++) {
            gvList.add(" , ");
        }
        for (int j = 1; j <= dayNumInMonth; j++) {
            gvList.add(YM + "," + String.valueOf(j));
        }


        int tempAfter = gvList.size() % 7;
        if (tempAfter != 0) {
            tempAfter = 7 - tempAfter;
            for (int i = 0; i < tempAfter; i++) {
                gvList.add(" , ");
            }
        }
    }

    private String getMonth(int month) {
        String mon = "";
        if (month < 10) {
            mon = "0" + month;
        } else {
            mon = "" + month;
        }
        return mon;
    }

    /**
     * 获取当前月的总共天数
     */
    private int getDayNumInMonth(Calendar cal) {
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取当前月第一天在第一个礼拜的第几天，得出第一天是星期几
     */
    private int countNeedHowMuchEmpety(Calendar cal) {
        int firstDayInWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return firstDayInWeek;
    }


    /**
     * gridview中adapter的viewholder
     */
    static class GrideViewHolder {
        TextView tvDay, tv_day_min_price/*tv,*/ /*tvLunarDay*/;
        RelativeLayout rlBg;
    }

    public static final int canNotChooseColor = 0xff999999;
    public static final int canChoseColor = 0xff3A3A48;
    public static final int dateInColorBg = 0xff33B5E5;
    public static final int dateInColorFb = Color.WHITE;
    public static final int notDateInBg = R.drawable.shape_calendar_white_bg;


    /**
     * gridview的adapter
     */
    class calendarGridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return gvList.size();
        }

        @Override
        public String getItem(int position) {
            return gvList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GrideViewHolder holder;
            if (convertView == null) {
                holder = new GrideViewHolder();
                convertView = inflate(context, R.layout.common_calendar_gridview_item3, null);
                holder.tvDay = (TextView) convertView.findViewById(R.id.tv_calendar_day);
                holder.tv_day_min_price = (TextView) convertView.findViewById(R.id.tv_day_min_price);
                holder.rlBg = (RelativeLayout) convertView.findViewById(R.id.rl_date_bg);
                convertView.setTag(holder);
            } else {
                holder = (GrideViewHolder) convertView.getTag();
            }
            setTVUnselected(holder);
//            holder.tvDay.setBackgroundColor(Color.WHITE);
            String[] date = gvList.get(position).split(",");
            String parseDay = "";
            holder.tvDay.setText(date[1]);
            if (!date[1].equals(" ")) {
                parseDay = date[1];
                if (Integer.parseInt(date[1]) < 10) {
                    parseDay = "0" + date[1];
                }
            }
            convertView.setBackgroundResource(notDateInBg);
            holder.tv_day_min_price.setVisibility(View.GONE);


            String item = date[0] + "-" + parseDay;
            if (!date[1].equals(" ")) {

                //如果这个item有日期
                holder.tvDay.setTextColor(canNotChooseColor);
                holder.tvDay.setTextSize(15);
                holder.tvDay.setText(date[1]);

                if ((date[0] + "-" + parseDay).equals(nowday)) {//判断是“今天”
                    holder.tvDay.setTextColor(canChoseColor);
                    holder.tvDay.setTextSize(15);
                    holder.tvDay.setText(date[1]);

                    holder.tv_day_min_price.setVisibility(View.VISIBLE);
                    if (calendarPriceList != null && calendarPriceList.size() > 0) {
                        String price = calendarPriceList.get(position);
                        if (!TextUtils.isEmpty(price)) {
                            holder.tv_day_min_price.setText("￥" + price);
                        }
                    }
                } else {//不是今天
                    if (calendarPriceList != null && calendarPriceList.size() > 0) {
                        holder.tv_day_min_price.setVisibility(View.VISIBLE);
                        if (calendarPriceList.size() > position) {
                            holder.tv_day_min_price.setVisibility(View.VISIBLE);
                            String price = calendarPriceList.get(position);
                            if (!TextUtils.isEmpty(price)) {
                                holder.tv_day_min_price.setText("￥" + price);
                            }

                        } else {
                            holder.tv_day_min_price.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        holder.tv_day_min_price.setVisibility(View.INVISIBLE);
                    }
                    holder.tvDay.setTextColor(canChoseColor);
                    if ((position + 1) % 7 == 0 || (position + 1) % 7 == 1) {
                        holder.tvDay.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvDay.setTextColor(context.getResources().getColor(R.color.color_333));
                    }
                    holder.tvDay.setText(date[1]);
                }
                try {
                    // 若日历日期<当前日期，则不能选择
                    if (!CalendarActivity.selectBefore && //不是往前选的模式
                            !" ".equals(date[0]) && //不为空
                            (dateFormat2.parse(date[0] + "-" + parseDay).getTime() < dateFormat2.parse(nowday).getTime()
                                    || dateFormat2.parse(date[0] + "-" + parseDay).getTime() < dateFormat2.parse(CalendarActivity.limitDate == null ? nowday : CalendarActivity.limitDate).getTime())) {//今天之前
                        holder.tv_day_min_price.setVisibility(View.INVISIBLE);
                        holder.tvDay.setTextColor(canNotChooseColor);
                        holder.tvDay.setEnabled(false);
                    }
                    if (CalendarActivity.selectBefore && !" ".equals(date[0]) &&//如果是往前选的模式
                            dateFormat2.parse(date[0] + "-" + parseDay).getTime() > dateFormat2.parse(nowday).getTime()) {
                        holder.tv_day_min_price.setVisibility(INVISIBLE);
                        holder.tvDay.setTextColor(canNotChooseColor);
                        holder.tvDay.setEnabled(false);
                    }

                    if (!TextUtils.isEmpty(userChooseInDate) && item.equals(userChooseInDate) /*&& (dateFormat2.parse(userChooseInDate).getTime() >= dateFormat2.parse(nowday).getTime())*/) {
                        setTVSelected(holder);

                        if (!CalendarActivity.isSingleChose) {//如果多选
                            holder.tvDay.setText(CalendarActivity.firstTag);//默认显示第一个标签
                            if (!TextUtils.isEmpty(CalendarActivity.checkOutDate)) {
                                holder.tvDay.setText(CalendarActivity.secondTag);//如果离店日期不是空的，则为离店
                            }
                            if (CalendarActivity.choiceFlag == CalendarActivity.FIRSTCLICK) {
                                holder.tvDay.setText(CalendarActivity.secondTag);//选择入住日期
                            } else if (CalendarActivity.choiceFlag == CalendarActivity.SECONDCLICK) {
                                holder.tvDay.setText(CalendarActivity.secondTag);//选择离店日期
                            }
                        } else {
                            if (!TextUtils.isEmpty(CalendarActivity.firstTag)) {
                                holder.tvDay.setText(CalendarActivity.firstTag);
                            }
                        }
                        //酒店逻辑结束
                    } else {
                        setTVUnselected(holder);
//                        holder.tvDay.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
                        //holder.tvDay.setBackgroundColor(context.getResources().getColor(R.color.white));
                    }

                    if (!CalendarActivity.isSingleChose) {
                        if (!TextUtils.isEmpty(CalendarActivity.checkOutDate) &&
                                item.equals(CalendarActivity.checkOutDate) &&
                                dateFormat2.parse(CalendarActivity.checkInDate).getTime() < dateFormat2.parse(CalendarActivity.checkOutDate).getTime() &&
                                CalendarActivity.choiceFlag != 1) {
//                            holder.tvDay.setBackground(context.getResources().getDrawable(R.drawable.calendar_choose_bg_stroke));
//                            holder.tvDay.setTextColor(Color.WHITE);
                            setTVSelected(holder);
                            holder.tvDay.setText(CalendarActivity.secondTag);
                        }
                    }


                    /*
                    酒店逻辑判断，点击离店后入住不消失
                     */
                    if (!TextUtils.isEmpty(CalendarActivity.checkInDate) && item.equals(CalendarActivity.checkInDate)) {
                        setTVSelected(holder);
                        if (!TextUtils.isEmpty(CalendarActivity.firstTag)) {
                            holder.tvDay.setText(CalendarActivity.firstTag);
                        }
                    }

                    if (!CalendarActivity.isSingleChose) {
                        // TODO: 2016/11/15 这里修改了部分逻辑，如需还原，将上面的if代码块移到本行代码下方
                        //点击离店后，中间日期变色
                        if (!TextUtils.isEmpty(CalendarActivity.checkOutDate) && //首先如果是点击了离店
                                CalendarActivity.choiceFlag != 1 &&
                                dateFormat2.parse(item).getTime() > dateFormat2.parse(CalendarActivity.checkInDate).getTime()//大于入住时间
                                && dateFormat2.parse(item).getTime() < dateFormat2.parse(CalendarActivity.checkOutDate).getTime()) //小于离店时间
                        {
                            holder.tvDay.setBackgroundColor(getResources().getColor(R.color.color_f2f2));//对中间部分进行颜色设置
                        }
                    }
                    //酒店逻辑结束

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            return convertView;
        }

        private void setTVSelected(GrideViewHolder holder) {
            TextView tvDay = holder.tvDay;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvDay.setBackground(context.getDrawable(R.drawable.calendar_choose_bg_stroke));
            } else {
                tvDay.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_choose_bg_stroke));
            }
            tvDay.setTextColor(Color.WHITE);
        }

        private void setTVUnselected(GrideViewHolder holder) {
            TextView tvDay = holder.tvDay;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvDay.setBackground(context.getDrawable(R.color.white));
            } else {
                tvDay.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
            }
        }
    }

    /**
     * 自定义监听接口
     */
    public interface OnDaySelectListener {
        void onDaySelectListener(View view, String date, int position);
    }

    /**
     * 自定义监听接口设置对象
     */
    public void setOnDaySelectListener(OnDaySelectListener o) {
        callBack = o;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////计算阴历日期的方法区域begin///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private int year; // 农历的年份
    private int month;
    private int day;
    private String lunarMonth; // 农历的月份
    private boolean leap;
    public int leapMonth = 0; // 闰的是哪个月

    final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    final static long[] lunarInfo = new long[]{ //
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, //
            0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, //
            0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, //
            0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, //
            0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, //
            0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, //
            0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, //
            0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, //
            0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, //
            0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, //
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, //
            0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, //
            0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, //
            0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, //
            0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, //
            0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, //
            0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};

    // 农历部分假日
    final static String[] lunarHoliday = new String[]{"0101 春节", "0115 元宵", "0505 端午", "0707 情人", "0715 中元", "0815 中秋", "0909 重阳", "1208 腊八", "1224 小年", "0100 除夕"};
    // 公历部分节假日
    final static String[] solarHoliday = new String[]{ //
            "0101 元旦", "0214 情人", "0308 妇女", "0312 植树", "0315 消费者权益日", "0401 愚人", "0501 劳动", "0504 青年", //
            "0512 护士", "0601 儿童", "0701 建党", "0801 建军", "0808 父亲", "0909 毛泽东逝世纪念", "0910 教师", "0928 孔子诞辰",//
            "1001 国庆", "1006 老人", "1024 联合国日", "1112 孙中山诞辰纪念", "1220 澳门回归纪念", "1225 圣诞", "1226 毛泽东诞辰纪念"};

    // ====== 传回农历 y年的总天数
    final private static int yearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(y));
    }

    // ====== 传回农历 y年闰月的天数
    final private static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }


    // ====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
    final private static int leapMonth(int y) {
        int result = (int) (lunarInfo[y - 1900] & 0xf);
        return result;
    }

    /** */
    /**
     * 传出y年m月d日对应的农历. yearCyl3:农历年与1864的相差数 ? monCyl4:从1900年1月31日以来,闰月数
     * dayCyl5:与1900年1月31日相差的天数,再加40 ?
     * <p/>
     * isday: 这个参数为false---日期为节假日时，阴历日期就返回节假日 ，true---不管日期是否为节假日依然返回这天对应的阴历日期
     *
     * @param
     * @return
     */
    public String getLunarDate(int year_log, int month_log, int day_log, boolean isday) {
        // @SuppressWarnings("unused")
        int yearCyl, monCyl, dayCyl;
        // int leapMonth = 0;
        String nowadays;
        Date baseDate = null;
        Date nowaday = null;
        try {
            baseDate = chineseDateFormat.parse("1900年1月31日");
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use
            // Options | File Templates.
        }

        nowadays = year_log + "年" + month_log + "月" + day_log + "日";
        try {
            nowaday = chineseDateFormat.parse(nowadays);
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use
            // Options | File Templates.
        }

        // 求出和1900年1月31日相差的天数
        int offset = (int) ((nowaday.getTime() - baseDate.getTime()) / 86400000L);
        dayCyl = offset + 40;
        monCyl = 14;

        // 用offset减去每农历年的天数
        // 计算当天是农历第几天
        // i最终结果是农历的年份
        // offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 10000 && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
            monCyl += 12;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
            monCyl -= 12;
        }
        // 农历年份
        year = iYear;
        setYear(year); // 设置公历对应的农历年份

        yearCyl = iYear - 1864;
        leapMonth = leapMonth(iYear); // 闰哪个月,1-12
        leap = false;

        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // 闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                --iMonth;
                leap = true;
                daysOfMonth = leapDays(year);
            } else
                daysOfMonth = monthDays(year, iMonth);

            offset -= daysOfMonth;
            // 解除闰月
            if (leap && iMonth == (leapMonth + 1))
                leap = false;
            if (!leap)
                monCyl++;
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --iMonth;
                --monCyl;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
            --monCyl;
        }
        month = iMonth;
        setLunarMonth(chineseNumber[month - 1] + "月"); // 设置对应的阴历月份
        day = offset + 1;

        if (!isday) {
            // 如果日期为节假日则阴历日期则返回节假日
            // setLeapMonth(leapMonth);
            for (int i = 0; i < solarHoliday.length; i++) {
                // 返回公历节假日名称
                String sd = solarHoliday[i].split(" ")[0]; // 节假日的日期
                String sdv = solarHoliday[i].split(" ")[1]; // 节假日的名称
                String smonth_v = month_log + "";
                String sday_v = day_log + "";
                String smd = "";
                if (month_log < 10) {
                    smonth_v = "0" + month_log;
                }
                if (day_log < 10) {
                    sday_v = "0" + day_log;
                }
                smd = smonth_v + sday_v;
                if (sd.trim().equals(smd.trim())) {
                    return sdv;
                }
            }

            for (int i = 0; i < lunarHoliday.length; i++) {
                // 返回农历节假日名称
                String ld = lunarHoliday[i].split(" ")[0]; // 节假日的日期
                String ldv = lunarHoliday[i].split(" ")[1]; // 节假日的名称
                String lmonth_v = month + "";
                String lday_v = day + "";
                String lmd = "";
                if (month < 10) {
                    lmonth_v = "0" + month;
                }
                if (day < 10) {
                    lday_v = "0" + day;
                }
                lmd = lmonth_v + lday_v;
                if (ld.trim().equals(lmd.trim())) {
                    return ldv;
                }
            }
        }
        if (day == 1)
            return chineseNumber[month - 1] + "月";
        else
            return getChinaDayString(day);

    }

    // ====== 传回农历 y年m月的总天数
    final private static int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }

    public void setLunarMonth(String lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    /**
     * 得到当前年对应的农历年份
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static String getChinaDayString(int day) {
        String chineseTen[] = {"初", "十", "廿", "卅"};
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        if (day == 10)
            return "初十";
        else
            return chineseTen[day / 10] + chineseNumber[n];
    }

    ///////////////////////////////////////////////////////////////////////////////////////////计算阴历日期的方法区域end///////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
