package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.common.CityListActivity;
import com.auvgo.tmc.common.CldActivity;
import com.auvgo.tmc.common.PassengerListActivity;
import com.auvgo.tmc.hotel.activity.HotelListActivity;
import com.auvgo.tmc.hotel.bean.CityBean;
import com.auvgo.tmc.hotel.bean.HotelPolicyBean;
import com.auvgo.tmc.hotel.bean.RequestHotelQueryBean;
import com.auvgo.tmc.hotel.interfaces.ViewManager_hotelquery;
import com.auvgo.tmc.hotel.activity.HotelKeywordActivity;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.Calendar.CalendarActivity;
import com.auvgo.tmc.views.MyPickerView;
import com.auvgo.tmc.views.StarPopView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2017/2/13
 */

public class PHotelQuery extends BaseP {
    private ViewManager_hotelquery vm;

    private String checkInDate = "";
    private String checkOutDate = "";
    private String cityName = "北京";
    private String cityCode = "0101";
    private String queryText = "";
    private String brandId = "";
    private String lowRate = "";//最小价格
    private String highRate = "";// 最大价格
    private String starRate = "";//推荐星级
    private String levelStr = "";
    private String longitude = "";//经度
    private String latitude = "";//纬度
    private int[] a = {0, 20};
    private boolean[] b = {true, false, false, false, false};

    private ArrayList<UserBean> psgs;
    public final static String FIRSTTAG = "入住";
    public final static String SECONDTAG = "离店";

    public void getCityCodeByName() {
        Map<String, String> map = new HashMap<>();
        map.put("cityname", cityName);
        RetrofitUtil.getCityIdByName(context, AppUtils.getJson(map), CityBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    CityBean cb = (CityBean) o;
                    cityCode = cb.getCityid();
                    getPolicy(new PHotelQuery.OnPolicyGotListener() {
                        @Override
                        public void onPolicyGot() {
                            jumpTo(Constant.ACTIVITY_HOTEL_LIST_FLAG);
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public interface OnPolicyGotListener {
        void onPolicyGot();
    }

    public PHotelQuery(Context context, ViewManager_hotelquery vm) {
        super(context);
        this.vm = vm;
        initData();
    }

    private void initData() {
        checkInDate = TimeUtils.getToday();
        checkOutDate = TimeUtils.getTomorrow();
        psgs = new ArrayList<>();
        psgs.add(MyApplication.mUserInfoBean);
    }

    public void getPolicy(final OnPolicyGotListener listener) {
        RetrofitUtil.getHotelPolicy(context, MUtils.getRequestStringWithCityIdByPsg(psgs, cityCode), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    if (bean.getData().length() != 3) {
                        MyApplication.mHotelPolicy = new Gson().fromJson(bean.getData(), HotelPolicyBean.class);
                    } else {
                        MyApplication.mHotelPolicy = null;
                    }
                }
                if (listener != null)
                    listener.onPolicyGot();
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public String getPsgsNames() {
        StringBuilder sb = new StringBuilder();
        int size = psgs.size();
        for (int i = 0; i < size; i++) {
            sb.append(psgs.get(i).getName());
            if (i < size - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void jumpTo(int flag) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Class c = null;
        switch (flag) {
            case Constant.ACTIVITY_CALENDAR_FLAG:
                intent.putExtra(CldActivity.KEY_ISSINGLE, false);
                intent.putExtra(CldActivity.KEY_SELECTED_DATE_1, checkInDate);
                intent.putExtra(CldActivity.KEY_SELECTED_DATE_2, checkOutDate);
                intent.putExtra(CldActivity.KEY_FIRST_TAG, FIRSTTAG);
                intent.putExtra(CldActivity.KEY_SECOND_TAG, SECONDTAG);
                c = CldActivity.class;
                break;
            case Constant.ACTIVITY_CITY_FLAG:
                bundle.putString("from", Constant.HOTEL);
                intent.putExtra("bundle", bundle);
                c = CityListActivity.class;
                break;
            case Constant.ACTIVITY_PASSENGER_FLAG:
                bundle.putSerializable("psgs", psgs);
                bundle.putInt("nums", 10);
                bundle.putString("from", Constant.HOTEL);
                c = PassengerListActivity.class;
                intent.putExtra("bundle", bundle);
                break;
            case Constant.ACTIVITY_HOTEL_KEYWORD_FLAG:
                intent.putExtra("cityId", cityCode);
                c = HotelKeywordActivity.class;
                break;
            case Constant.ACTIVITY_HOTEL_LIST_FLAG:
                MyApplication.mHotelCityId = cityCode;
                MyApplication.mHotelCityName = cityName;
                RequestHotelQueryBean bean = new RequestHotelQueryBean();
                bean.setArrivalDate(checkInDate);
                bean.setDepartureDate(checkOutDate);
                bean.setCityId(cityCode);
                bean.setQueryText(queryText);
                bean.setBrandId(brandId);
                bean.setLowRate(lowRate);
                bean.setHighRate(highRate.equals("1000") ? "" : highRate);
                bean.setStarRate(starRate);
                bean.setLatitude(latitude);
                bean.setLongitude(longitude);
                intent.putExtra("stars", a);
                intent.putExtra("levels", b);
                intent.putExtra("bean", bean);
                c = HotelListActivity.class;
                MyApplication.getApplication().selectedPsgs = psgs;
                break;
        }
        intent.setClass(context, c);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_HOTEL_HOME_FLAG);
    }

    public void receivePsgData(Intent data) {
        //将之前的清空
        psgs.clear();
        //接收新数据
        psgs.addAll((List<UserBean>) data.getSerializableExtra("psgs"));
        if (psgs.size() == 0)
            psgs.add(MyApplication.mUserInfoBean);
        //拿到新数据后，更新一下ui，将已经选择的乘客，显示到界面
        vm.updateSelectedPsgs();
    }

    public void receiveCldData(Intent data) {
        checkInDate = data.getStringExtra(CldActivity.KEY_RESULT_FIRST);
        checkOutDate = data.getStringExtra(CldActivity.KEY_RESULT_SECOND);
        vm.setDate();
    }

    public void receiveCityData(Intent data) {
        String code = data.getStringExtra("code");
        if (!code.equals(cityCode)) {
            queryText = "";
            vm.setKeyword();
            vm.setDelVisibility(View.GONE);
        }
        cityCode = code;
        cityName = data.getStringExtra("name");
        longitude = "";
        latitude = "";
        vm.setCityName();
    }

    public void receiveKeywordData(Intent data) {
        MyPickerView.Selection tlb = data.getParcelableExtra(HotelKeywordActivity.KEYWORD_KEY);
        queryText = tlb.getName();
        vm.setKeyword();
        vm.setDelVisibility(View.VISIBLE);
    }

    public void showStarPop() {
        DialogUtil.showStarPop(context, a, b, new StarPopView.StarPopListener() {
            @Override
            public void onSureClick(int[] positions, boolean[] stars) {
                a = positions.clone();
                b = stars.clone();
                dealResult();
                vm.setStarLevel();
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
                vm.setStarLevel();
            }
        });
    }

    private void dealResult() {
        lowRate = a[0] * 50 + "";
        highRate = a[1] * 50 + "";
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
//        if (levelStr.equals("")) {
//            levelStr = "不限";
//        }
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public ArrayList<UserBean> getPsgs() {
        return psgs;
    }

    public void setPsgs(ArrayList<UserBean> psgs) {
        this.psgs = psgs;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getLevelStr() {
        return levelStr;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }
}
