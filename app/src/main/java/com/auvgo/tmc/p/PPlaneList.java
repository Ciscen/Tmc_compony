package com.auvgo.tmc.p;

import android.content.Context;
import android.content.Intent;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PlaneListAdapter;
import com.auvgo.tmc.plane.activity.PlaneReturnApplyActivity;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.plane.interfaces.ViewManager_PlaneList;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.PlaneFilterView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2016/12/21
 */

public class PPlaneList extends BaseP {
    private ViewManager_PlaneList vm;//ViewManager
    ////////////////请求所需数据///////////////////
    private String fromCode, toCode, fromName, toName, startdate, carrier;

    /////////////////返程需要数据////////////////////////////
    private boolean isReturn;//返程的时候传入true即可
    private PlaneRouteBeanWF firstRoute;//去程的bean

    ///////////筛选///////////////
    private boolean isPriceOrder;
    private boolean isStartTimeOrder = true;
    private int orderType = 1;//表示正序、倒叙
    private ArrayList<PlaneListBean> list_all;
    private List<PlaneListBean> list_filted;
    private int mActivityFrom = 0;
    private PlaneListAdapter adapter;
    private List<SelectionBean> sa, sb, sc;
    private List<Integer> a, c;
    private int b;
    private boolean isAlter;//是否是改签来的

    public PPlaneList(Context context, ViewManager_PlaneList vm) {
        super(context);
        this.vm = vm;
    }

    public void initData(Intent intent) {

        fromCode = intent.getStringExtra("fromCode");
        toCode = intent.getStringExtra("toCode");
        fromName = intent.getStringExtra("fromName");
        toName = intent.getStringExtra("toName");
        startdate = intent.getStringExtra("startDate");
        isReturn = intent.getBooleanExtra("isReturn", false);
        isAlter = intent.getBooleanExtra("isAlter", false);
        carrier = intent.getStringExtra("carrier");
        b = intent.getIntExtra("type", 0);
        if (isReturn) {
            firstRoute = (PlaneRouteBeanWF) intent.getSerializableExtra("firstRoute");
        }
        list_filted = new ArrayList<>();
        list_all = new ArrayList<>();
        adapter = new PlaneListAdapter(context, list_all, list_filted, R.layout.item_plane_list);
        a = new ArrayList<>();
        c = new ArrayList<>();
        sa = new ArrayList<>();
        sb = new ArrayList<>();
        sc = new ArrayList<>();

        sa.add(new SelectionBean("不限", 0, true));
        sa.add(new SelectionBean("上午 00:00-06:00", 1));
        sa.add(new SelectionBean("下午 06:00-12:00", 2));
        sa.add(new SelectionBean("下午 12:00-18:00", 3));
        sa.add(new SelectionBean("下午 18:00-24:00", 4));
        sb.add(new SelectionBean("不限", ""));
        sb.add(new SelectionBean("经济舱", "Y"));
        sb.add(new SelectionBean("公务舱", "C"));
        sb.add(new SelectionBean("头等舱", "F"));
        sb.get(b).setChecked(true);
    }

    public void getPlaneList() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("from", fromCode);
        map.put("to", toCode);
        map.put("startdate", startdate);
        if (isAlter)
            map.put("carrier", PlaneReturnApplyActivity.mBean.getRoutes().get(0).getCarriecode());
        RetrofitUtil.getPlaneList(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    List<PlaneListBean> l = new Gson().fromJson(bean.getData(), new TypeToken<List<PlaneListBean>>() {
                    }.getType());
                    list_all.clear();
                    list_all.addAll(l);
                    list_filted.addAll(list_all);
                    initComponies();
                    filtList();
                    adapter.notifyDataSetChanged();
                    vm.freshTitle();
                }
                vm.setEmptyViews();
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                vm.setEmptyViews();
                return false;
            }
        });
    }

    public boolean isPriceOrder() {
        return isPriceOrder;
    }

    public void setPriceOrder(boolean priceOrder) {
        isPriceOrder = priceOrder;
    }

    public boolean isStartTimeOrder() {
        return isStartTimeOrder;
    }

    public void setStartTimeOrder(boolean startTimeOrder) {
        isStartTimeOrder = startTimeOrder;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public PlaneListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PlaneListAdapter adapter) {
        this.adapter = adapter;
    }

    public PlaneRouteBeanWF getFirstRoute() {
        return firstRoute;
    }

    public void setFirstRoute(PlaneRouteBeanWF firstRoute) {
        this.firstRoute = firstRoute;
    }

    public String getFromName() {
        return fromName;
    }

    public boolean isAlter() {
        return isAlter;
    }

    public void setAlter(boolean alter) {
        isAlter = alter;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToName() {
        return toName;
    }

    public List<PlaneListBean> getList_filted() {
        return list_filted;
    }

    public ArrayList<PlaneListBean> getList_all() {
        return list_all;
    }

    public void setList_filted(List<PlaneListBean> list_filted) {
        this.list_filted = list_filted;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public void showFilterPop() {
        DialogUtil.showPlaneFilterPop(context, sa, sb, sc, new PlaneFilterView.OnPlaneFilterViewListener() {
            @Override
            public void onSure(List<Integer> a, int b, List<Integer> c) {
                PPlaneList.this.a = a;
                PPlaneList.this.b = b;
                PPlaneList.this.c = c;
                filtList();
            }

            @Override
            public void onCancle() {
            }
        });
    }

    public void filtList() {
        list_filted.clear();
        for (int i = 0; i < list_all.size(); i++) {
            if (matchSeat(i) && matchTime(i) && matchAirCarrier(i)) {//加入到要显示list的条件
                list_filted.add(list_all.get(i));
            }
        }
        if (isPriceOrder) {
            Collections.sort(list_filted, new Comparator<PlaneListBean>() {
                @Override
                public int compare(PlaneListBean lhs, PlaneListBean rhs) {
                    double p1 = lhs.getLow().getPrice();
                    double p2 = rhs.getLow().getPrice();
                    return (int) ((p1 - p2) * orderType);
                }
            });
        }
        if (isStartTimeOrder) {
            Collections.sort(list_filted, new Comparator<PlaneListBean>() {
                @Override
                public int compare(PlaneListBean lhs, PlaneListBean rhs) {
                    long l1 = TimeUtils.getTimeStamp(lhs.getDepttime(), "HH:mm");
                    long l2 = TimeUtils.getTimeStamp(rhs.getDepttime(), "HH:mm");
                    return (int) (l1 - l2) * orderType;
                }
            });
        }
        if (list_filted.size() == 0) {
            ToastUtils.showTextToast("未查询到结果，换个条件试试吧");
        }
        adapter.notifyDataSetChanged();
        vm.freshTitle();
    }

    private boolean matchSeat(int i) {
        List<PlaneListBean.CangweisBean> cangweis = list_all.get(i).getCangweis();
        if (b == 0)
            return true;
        SelectionBean bean = sb.get(b);
        for (int i1 = 0; i1 < cangweis.size(); i1++) {
            if (cangweis.get(i1).getFarebase().equals(bean.getExtra())) {
                return true;
            }
        }
        return false;
    }

    private boolean matchTime(int i) {
        String start_time = list_all.get(i).getDepttime();
        long timeStamp = TimeUtils.getTimeStamp(start_time, "HH:mm");
        long tm0 = TimeUtils.getTimeStamp("00:00", "HH:mm");
        long tm6 = TimeUtils.getTimeStamp("06:00", "HH:mm");
        long tm12 = TimeUtils.getTimeStamp("12:00", "HH:mm");
        long tm18 = TimeUtils.getTimeStamp("18:00", "HH:mm");
        long tm24 = TimeUtils.getTimeStamp("24:00", "HH:mm");
        if (a.size() == 0) return true;
        for (int i1 = 0; i1 < a.size(); i1++) {
            Integer index = a.get(i1);
            switch (index) {
                case 0:
                    return true;
                case 1:
                    if (timeStamp >= tm0 && timeStamp <= tm6) return true;
                    break;
                case 2:
                    if (timeStamp >= tm6 && timeStamp <= tm12) return true;
                    break;
                case 3:
                    if (timeStamp >= tm12 && timeStamp <= tm18) return true;
                    break;
                case 4:
                    if (timeStamp >= tm18 && timeStamp <= tm24) return true;
                    break;
            }
        }
        return false;
    }

    /*
    匹配航空公司
     */
    private boolean matchAirCarrier(int i) {
        String carrierCode = list_all.get(i).getCarriecode();
        if (c.size() == 0) {
            return true;
        }
        for (int i1 = 0; i1 < c.size(); i1++) {
            if (c.get(i1) == 0) {
                return true;
            }
            if (sc.get(c.get(i1)).getExtra().equals(carrierCode)) {
                return true;
            }
        }
        return false;
    }

    public void jumpActivity(int activityCalendarFlag, int i) {

    }


    private void initComponies() {
        double lowestPrice = 100000;
        sc.add(new SelectionBean("不限", true));
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < list_all.size(); i++) {
            PlaneListBean planeListBean = list_all.get(i);
            String carriecode = planeListBean.getCarriecode();
            if (!map.containsKey(carriecode)) {
                sc.add(new SelectionBean(planeListBean.getCarriername(), carriecode));
                map.put(carriecode, "");
            }
            double price = planeListBean.getLow().getPrice();
            if (price < lowestPrice) {
                lowestPrice = price;
            }
        }
        MyApplication.lowestPrice = lowestPrice;
        map.clear();
        map = null;

    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderType() {
        return orderType;
    }
}
