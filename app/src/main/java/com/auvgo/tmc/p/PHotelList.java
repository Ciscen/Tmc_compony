package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.auvgo.tmc.hotel.activity.HotelDetailActivity;
import com.auvgo.tmc.hotel.activity.HotelFiltActivity;
import com.auvgo.tmc.hotel.activity.HotelKeywordActivity;
import com.auvgo.tmc.hotel.activity.HotelLocationActivity;
import com.auvgo.tmc.hotel.adapter.HotelListAdapter;
import com.auvgo.tmc.hotel.bean.HotelListBean;
import com.auvgo.tmc.hotel.bean.HotelLocationBean;
import com.auvgo.tmc.hotel.bean.RequestHotelDetailBean;
import com.auvgo.tmc.hotel.bean.RequestHotelQueryBean;
import com.auvgo.tmc.hotel.interfaces.ViewManager_hotellist;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.Calendar.CalendarActivity;
import com.auvgo.tmc.views.MyPickerView;
import com.auvgo.tmc.views.StarPopView;

import java.util.ArrayList;
import java.util.List;

import static com.auvgo.tmc.p.PHotelQuery.FIRSTTAG;
import static com.auvgo.tmc.p.PHotelQuery.SECONDTAG;

/**
 * Created by lc on 2017/2/15
 */

public class PHotelList extends BaseP {
    private ViewManager_hotellist vm;
    private List<SelectionBean> list_sort;
    private int mPosition = -1;//当前选择的排序方式
    private int[] a = {2, 12};//筛选范围
    private boolean[] b = {true, false, false, false, false};//酒店星级
    private String lowRate = "";//最小价格
    private String highRate = "";// 最大价格
    private String starRate = "";//推荐星级
    private String levelStr = "";
    private boolean isSelectedLocation;//是否已经选择了位置筛选
    private RequestHotelQueryBean bean;
    private HotelListBean mBean;
    private HotelListAdapter adapter;
    private List<HotelListBean.ListBean> list;
    private boolean isLoadMore;
    private boolean isFirst = true;
    private boolean isLoading;

    public PHotelList(Context context, ViewManager_hotellist vm) {
        super(context);
        this.vm = vm;
    }

    public void init(Intent intent) {
        this.bean = (RequestHotelQueryBean) intent.getSerializableExtra("bean");
        bean.setPageSize(20 + "");
        a = intent.getIntArrayExtra("stars");
        b = intent.getBooleanArrayExtra("levels");
        list = new ArrayList<>();
        list_sort = new ArrayList<>();
        list_sort.add(new SelectionBean("价格  低→高"));
        list_sort.add(new SelectionBean("价格  高→低"));
//        list_sort.add(new SelectionBean("口碑  高→低"));
        list_sort.add(new SelectionBean("距离  近→远"));
        adapter = new HotelListAdapter(context, list);
        vm.setDelVisibility();
        vm.setAdapter();
        checkBottomState();
    }

    public void onBottomClick(int i) {
        switch (i) {
            case 0:
                showSortPop();
                break;
            case 1:
                showStarPop();
                break;
            case 2:
                jumpTo(Constant.ACTIVITY_HOTEL_LOCATION_FLAG);
                break;
            case 3:
                jumpTo(Constant.ACTIVITY_HOTEL_FILT_FLAG);
                break;
        }
    }

    private void showStarPop() {
        DialogUtil.showStarPop(context, a, b, new StarPopView.StarPopListener() {
            @Override
            public void onSureClick(int[] positions, boolean[] stars) {
                a = positions.clone();
                b = stars.clone();
                lowRate = positions[0] * 50 + "";
                highRate = positions[1] * 50 + "";
                dealResult();
                bean.setLowRate(lowRate);
                bean.setHighRate(highRate.equals("1000") ? "" : highRate);
                bean.setStarRate(starRate);
                getHotelList();
                checkBottomState();
            }

            @Override
            public void onCancel() {
                a[0] = 0;
                a[1] = 20;
                for (int i = 0; i < b.length; i++) {
                    if (i == 0) {
                        b[0] = true;
                    } else {
                        b[i] = false;
                    }
                }
                dealResult();
                checkBottomState();
            }
        });
    }

    private void showSortPop() {
        /*
        Default艺龙默认排序 StarRankDesc 推荐星级降序 RateAsc 价格升序 RateDesc 价格降序 DistanceAsc 距离升序
         */
        DialogUtil.showExpandablePickDialog(context, "排序", mPosition, list_sort, new MyPickerView.OnPickerViewSure() {
            @Override
            public void onSingleSureClick(int p) {
                mPosition = p;
                switch (mPosition) {
                    case 0://价格升序
                        bean.setSort("RateAsc");
                        break;
                    case 1://价格降序
                        bean.setSort("RateDesc");
                        break;
                    case 2://推荐星级降序
                        bean.setSort("StarRankDesc");
                        break;
                    case 3://距离升序
                        bean.setSort("DistanceAsc");
                        break;
                }
                getHotelList();
                checkBottomState();
            }

            @Override
            public void onMultiSureClick(List<Integer> s) {

            }
        });
    }

    public void getHotelList() {
        isLoading = true;
        RetrofitUtil.getHotelList(context, AppUtils.getJson(bean), isFirst, HotelListBean.class,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (200 == status) {
                            mBean = (HotelListBean) o;
                            if (!isLoadMore) {
                                list.clear();
                            }
                            list.addAll(mBean.getList());
                            vm.onLoadFinished();
                            isFirst = false;
                            adapter.notifyDataSetChanged();
                        }
                        isLoadMore = false;
                        vm.setEmptyView();
                        vm.onLoadFinished();

                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        isLoading = false;
                        isLoadMore = false;
                        vm.setEmptyView();
                        return false;
                    }
                });
    }

    public void jumpTo(int flag) {
        Intent intent = new Intent();
        Class c = null;
        Bundle bundle = new Bundle();
        switch (flag) {
            case Constant.ACTIVITY_CALENDAR_FLAG:
                bundle.putBoolean("isSingleChose", false);
                bundle.putString("checkInDate", bean.getArrivalDate());
                bundle.putString("checkOutDate", bean.getDepartureDate());
                bundle.putString("firstTag", FIRSTTAG);
                bundle.putString("secondTag", SECONDTAG);
                intent.putExtra("bundle", bundle);
                c = CalendarActivity.class;
                break;
            case Constant.ACTIVITY_HOTEL_KEYWORD_FLAG:
                intent.putExtra("cityId", bean.getCityId());
                c = HotelKeywordActivity.class;
                break;
            case Constant.ACTIVITY_HOTEL_LOCATION_FLAG:
                intent.putExtra("cityId", bean.getCityId());
                c = HotelLocationActivity.class;
                break;
            case Constant.ACTIVITY_HOTEL_FILT_FLAG:
                intent.putExtra("cityId", bean.getCityId());
                c = HotelFiltActivity.class;
                break;
        }
        intent.setClass(context, c);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_HOTEL_LIST_FLAG);
    }

    public void receiveCldData(Intent data) {
        bean.setArrivalDate(data.getStringExtra("firstDate"));
        bean.setDepartureDate(data.getStringExtra("secondDate"));
//        fromDate = data.getStringExtra("firstDate");
//        endDate = data.getStringExtra("secondDate");

        vm.setDate();
        getHotelList();
    }

    public void receiveKeywordData(Intent data) {
        MyPickerView.Selection tlb = data.getParcelableExtra(HotelKeywordActivity.KEYWORD_KEY);
        bean.setQueryText(tlb.getName());
        vm.setKeyword();
        getHotelList();
    }

    public HotelListAdapter getAdapter() {
        return adapter;
    }

    public void onRefresh() {

        bean.setPageIndex("1");
        getHotelList();
    }

    public void onLoadMore() {
        if (mBean.isIsHaveNextPage()) {
            bean.setPageIndex(Integer.parseInt(bean.getPageIndex() == null ?
                    "1" : bean.getPageIndex()) + 1 + "");
            isLoadMore = true;
            getHotelList();
        } else {
            vm.onLoadFinished();
        }
    }

    private void dealResult() {
        starRate = "";
        levelStr = "";
        if (highRate.equals("1000")) {
            if (lowRate.equals("0")) {
                levelStr = "";
            } else {
                levelStr = "￥" + lowRate + "以上,";
            }
        } else {
            levelStr = "￥" + lowRate + "-" + "￥" + highRate + ",";
        }
        for (int i = 0; i < b.length; i++) {
            if (b[0]) {
                levelStr += "";
                break;
            }
            if (b[i]) {
                switch (i) {
                    case 1:
                        levelStr += "经济型/";
                        starRate += "0,1,2,";
                        break;
                    case 2:
                        levelStr += "舒适型/";
                        starRate += "3,";
                        break;
                    case 3:
                        levelStr += "高档型/";
                        starRate += "4,";
                        break;
                    case 4:
                        levelStr += "豪华型";
                        starRate += "5,";
                        break;
                }
            }
        }
        if (starRate.endsWith(",")) {
            starRate = starRate.substring(0, starRate.length() - 1);
        }
        if (levelStr.endsWith(",") || levelStr.endsWith("/")) {
            levelStr = levelStr.substring(0, levelStr.length() - 1);
        }
        if (levelStr.equals("")) {
            levelStr = "不限";
        }
    }

    public void onItemClick(int position) {
        HotelListBean.ListBean listBean = list.get(position);
        Intent intent = new Intent(context, HotelDetailActivity.class);
        RequestHotelDetailBean rdb = new RequestHotelDetailBean();
        rdb.setArrivalDate(bean.getArrivalDate() == null ? TimeUtils.getToday() : bean.getArrivalDate());
        rdb.setDepartureDate(bean.getDepartureDate() == null ? TimeUtils.getTomorrow() : bean.getDepartureDate());
        rdb.setHotelId(listBean.getHotelId());
        intent.putExtra("bean", rdb);
        context.startActivity(intent);
    }

    public void receiveLocation(Intent data) {
        HotelLocationBean.PositionsBean pb = data.getParcelableExtra(HotelLocationActivity.LOCATION_KEY);
        if (pb.getName().equals("不限")) {
            isSelectedLocation = false;
        } else {
            bean.setQueryText(pb.getName());
            isSelectedLocation = true;
        }
        getHotelList();
        checkBottomState();
    }

    public RequestHotelQueryBean getBean() {
        return bean;
    }

    public HotelListBean getmBean() {
        return mBean;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public List<HotelListBean.ListBean> getList() {
        return list;
    }

    public void setList(List<HotelListBean.ListBean> list) {
        this.list = list;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setmBean(HotelListBean mBean) {
        this.mBean = mBean;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setBean(RequestHotelQueryBean bean) {
        this.bean = bean;
    }

    public void receiveFiltData(Intent data) {
        bean.setBrandId(data.getStringExtra("brand"));
        bean.setFacilities(data.getStringExtra("facilities"));
        getHotelList();
        checkBottomState();
    }

    /*
    判断底部的颜色状态
     */
    public void checkBottomState() {
        if (mPosition == -1) {
            vm.setSortState(false);
        } else {
            vm.setSortState(true);
        }
        if (starRate.isEmpty()
                && (lowRate.isEmpty() || lowRate.equals("0"))
                && (highRate.isEmpty() || lowRate.equals("0"))) {
            vm.setStarState(false);
        } else {
            vm.setStarState(true);
        }
        if (!isSelectedLocation) {
            vm.setLocationState(false);
        } else {
            vm.setLocationState(true);
        }
        if (TextUtils.isEmpty(bean.getBrandId()) && TextUtils.isEmpty(bean.getFacilities())) {
            vm.setFiltState(false);
        } else {
            vm.setFiltState(true);
        }
    }
}
