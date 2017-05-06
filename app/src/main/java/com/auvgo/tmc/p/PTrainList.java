package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.TrainListAdapter;
import com.auvgo.tmc.common.CldActivity;
import com.auvgo.tmc.train.activity.TrainAlterDetailActivity;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainlist;
import com.auvgo.tmc.train.activity.TrainDetailActivity;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.Calendar.CalendarActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2016/11/15
 */

public class PTrainList extends BaseP {

    private ViewManager_trainlist vm;

    ////////////////请求所需数据///////////////////
    private String from, to, startdate;

    ////////////////过滤所需数据///////////////////
    private String typeStr;//表示车次类型的字符串
    private int orderType = 1;//排序方式
    private List<Integer> offTimes;//表示出发时间的集合
    private List<String> station_selected;
    private boolean isStartTimeOrder;
    private boolean isPriceOrder;
    private boolean isRunTimeOrder;
    //////////////内容数据////////////////////
    private List<TrainBean.DBean> list_total;//全部数据
    private List<TrainBean.DBean> list_filted;//过滤后的数据
    private List<String> stations_all;
    private TrainListAdapter adapter;

    //////////////变量/////////////////////
    private int mActivityFrom = 0;


    public PTrainList(Context context, ViewManager_trainlist vm) {
        super(context);
        this.vm = vm;
        list_total = new ArrayList<>();
        list_filted = new ArrayList<>();
        offTimes = new ArrayList<>();
        offTimes.add(0);
        offTimes.add(1);
        offTimes.add(2);
        offTimes.add(3);
        station_selected = new ArrayList<>();
        stations_all = new ArrayList<>();
        adapter = new TrainListAdapter(context, list_filted, R.layout.item_train_list);
    }

    public List<TrainBean.DBean> getList_total() {
        return list_total;
    }

    public void setList_total(List<TrainBean.DBean> list_total) {
        this.list_total = list_total;
    }

    public int getmActivityFrom() {
        return mActivityFrom;
    }

    public void setmActivityFrom(int mActivityFrom) {
        this.mActivityFrom = mActivityFrom;
    }

    public boolean isRunTimeOrder() {
        return isRunTimeOrder;
    }

    public void setRunTimeOrder(boolean runTimeOrder) {
        isRunTimeOrder = runTimeOrder;
    }

    public boolean isPriceOrder() {
        return isPriceOrder;
    }

    public void setPriceOrder(boolean priceOrder) {
        isPriceOrder = priceOrder;
    }

    public int getOrderType() {
        return orderType;
    }

    public List<TrainBean.DBean> getList_filted() {
        return list_filted;
    }

    public void setList_filted(List<TrainBean.DBean> list_filted) {
        this.list_filted = list_filted;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public boolean isStartTimeOrder() {
        return isStartTimeOrder;
    }

    public void setStartTimeOrder(boolean startTimeOrder) {
        isStartTimeOrder = startTimeOrder;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public ViewManager_trainlist getVm() {
        return vm;
    }

    public void setVm(ViewManager_trainlist vm) {
        this.vm = vm;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate, boolean isFirst) {
        this.startdate = startdate;
        if (!isFirst) {
            vm.setCalenderDate(startdate);
            getTrainList();
        }
    }

    public void getTrainList() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("from", from);
        map.put("to", to);
        map.put("startdate", startdate);
        RetrofitUtil.getTrainList(context, AppUtils.getJson(map), TrainBean.class,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        list_total.clear();
                        LogFactory.d("trainlist======================" + bean.getData());
                        TrainBean tb = (TrainBean) o;
                        List<TrainBean.DBean> d = tb.getD();
                        if (d != null) {
                            for (int i = 0; i < d.size(); i++) {
                                if (d.get(i).getCanBook().size() > 0) {
                                    list_total.add(d.get(i));
                                    String from_station_name = d.get(i).getFrom_station_name();
                                    String to_station_name = d.get(i).getTo_station_name();
                                    if (!stations_all.contains(from_station_name)) {
                                        stations_all.add(from_station_name);
                                        station_selected.add(from_station_name);
                                    }
                                    if (!stations_all.contains(to_station_name)) {
                                        stations_all.add(to_station_name);
                                        station_selected.add(to_station_name);
                                    }

                                }

                            }
                            Collections.sort(station_selected, new Comparator<String>() {
                                @Override
                                public int compare(String lhs, String rhs) {
                                    if (lhs.charAt(0) > rhs.charAt(0)) {
                                        return 1;
                                    } else if (lhs.charAt(0) < rhs.charAt(0)) {
                                        return -1;
                                    }
                                    return 0;
                                }
                            });
                        }
                        filtList();
                        vm.setAdapter(adapter);
                        vm.setEmptyView();
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        vm.setEmptyView();
                        return false;
                    }
                });
    }


    public void filtList() {
        list_filted.clear();
        for (int i = 0; i < list_total.size(); i++) {
            if (matchSeat(i) && matchTime(i) && matchStation(i)) {//加入到要显示list的条件
                list_filted.add(list_total.get(i));
            }
        }
        if (isPriceOrder) {
            Collections.sort(list_filted, new Comparator<TrainBean.DBean>() {
                @Override
                public int compare(TrainBean.DBean lhs, TrainBean.DBean rhs) {

                    List<List<String>> lhsCanBook = lhs.getCanBook();
                    List<List<String>> rhsCanBook = rhs.getCanBook();
                    if (lhsCanBook.size() == 0) return -1;
                    if (rhsCanBook.size() == 0) return 1;

                    float lp = Float.parseFloat(lhsCanBook.get(0).get(2));
                    float rp = Float.parseFloat(rhsCanBook.get(0).get(2));
                    return (int) (lp - rp) * orderType;
                }
            });
        }
        if (isRunTimeOrder) {
            Collections.sort(list_filted, new Comparator<TrainBean.DBean>() {
                @Override
                public int compare(TrainBean.DBean lhs, TrainBean.DBean rhs) {
                    float lp = Float.parseFloat(lhs.getRun_time_minute());
                    float rp = Float.parseFloat(rhs.getRun_time_minute());
                    return (int) (lp - rp) * orderType;
                }
            });
        }
        if (isStartTimeOrder) {
            Collections.sort(list_filted, new Comparator<TrainBean.DBean>() {
                @Override
                public int compare(TrainBean.DBean lhs, TrainBean.DBean rhs) {

                    long l1 = TimeUtils.getTimeStamp(lhs.getStart_time(), "HH:mm");
                    long l2 = TimeUtils.getTimeStamp(rhs.getStart_time(), "HH:mm");
                    return (int) (l1 - l2) * orderType;
                }
            });
        }
        if (list_filted.size() == 0) {
            ToastUtils.showTextToast("未查询到结果，换个条件试试吧");
        }
        adapter.notifyDataSetChanged();
        vm.freshTitle();
        checkFiltButtonState();
    }

    /**
     * 判断是否有筛选数据，更新底部筛选按钮的状态
     */
    private void checkFiltButtonState() {
        if (TextUtils.isEmpty(typeStr) || typeStr.equals(Constant.TrainType.TRAIN_TYPE_ALL)
                && (offTimes.size() == 0 || offTimes.size() == 4)
                && station_selected.size() == stations_all.size()) {
            vm.setFiltState(false);
        } else {
            vm.setFiltState(true);
        }

    }

    private boolean matchSeat(int i) {
        return typeStr.contains(list_total.get(i).getTrain_code().substring(0, 1));
    }

    private boolean matchTime(int i) {
        String start_time = list_total.get(i).getStart_time();
        long timeStamp = TimeUtils.getTimeStamp(start_time, "HH:mm");
        long tm0 = TimeUtils.getTimeStamp("00:00", "HH:mm");
        long tm6 = TimeUtils.getTimeStamp("06:00", "HH:mm");
        long tm12 = TimeUtils.getTimeStamp("12:00", "HH:mm");
        long tm18 = TimeUtils.getTimeStamp("18:00", "HH:mm");
        long tm24 = TimeUtils.getTimeStamp("24:00", "HH:mm");
        for (int i1 = 0; i1 < offTimes.size(); i1++) {
            Integer index = offTimes.get(i1);
            switch (index) {
                case 0:
                    if (timeStamp >= tm0 && timeStamp <= tm6) return true;
                    break;
                case 1:
                    if (timeStamp >= tm6 && timeStamp <= tm12) return true;
                    break;
                case 2:
                    if (timeStamp >= tm12 && timeStamp <= tm18) return true;
                    break;
                case 3:
                    if (timeStamp >= tm18 && timeStamp <= tm24) return true;
                    break;
            }
        }
        return false;
    }

    private boolean matchStation(int i) {
        String from_station_name = list_total.get(i).getFrom_station_name();
        String to_station_name = list_total.get(i).getTo_station_name();
        return station_selected.contains(to_station_name) || station_selected.contains(from_station_name);
    }

    public void showFilterPop() {
        DialogUtil.showFilterPop(context, typeStr, offTimes, station_selected, stations_all,
                new DialogUtil.FilterPopListener() {

                    @Override
                    public void onSureClick(String typeStr_copy, List<Integer> offTimes_copy, List<String> station_selected_copy) {
                        typeStr = typeStr_copy;
                        offTimes = offTimes_copy;
                        station_selected = station_selected_copy;
                        filtList();
                    }
                });
    }

    /**
     * @param activityCalendarFlag
     * @param position             只在itemclick中有用
     */
    public void jumpActivity(int activityCalendarFlag, int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (activityCalendarFlag) {
            case Constant.ACTIVITY_CALENDAR_FLAG:
                Activity a = (Activity) context;
                intent.putExtra(CldActivity.KEY_SELECTED_DATE_1, startdate);
                intent.setClass(context, CldActivity.class);
                a.startActivityForResult(intent, Constant.ACTIVITY_TRAIN_LIST_FLAG);
                break;
            case Constant.ACTIVITY_TRAIN_DETAIL_FLAG:
                intent.setClass(context, TrainDetailActivity.class);
                bundle.putSerializable("bean", list_filted.get(position));
                bundle.putString("from", from);
                bundle.putString("to", to);
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
                break;
            case Constant.ACTIVITY_ALTER_TRAIN_DETAIL_FLAG:
                intent.setClass(context, TrainAlterDetailActivity.class);
                bundle.putSerializable("bean", list_filted.get(position));
                bundle.putString("from", from);
                bundle.putString("to", to);
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
                break;
        }
    }
}
