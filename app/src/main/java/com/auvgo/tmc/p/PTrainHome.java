package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PickerListAdapter;
import com.auvgo.tmc.common.CldActivity;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainPolicyBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.common.CityListActivity;
import com.auvgo.tmc.common.PassengerListActivity;
import com.auvgo.tmc.train.interfaces.ViewManager_trainhome;
import com.auvgo.tmc.train.activity.TrainListActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.Calendar.CalendarActivity;
import com.auvgo.tmc.views.MyPickerView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 2016/11/10
 */

public class PTrainHome extends BaseP {

    private ViewManager_trainhome vm;
    private String fromCityCode = "BJP";
    private String toCityCode = "SHH";

    private String date = TimeUtils.getTomorrow();
    private List<UserBean> psgList;//表示当前页面已经选择的psglist
    private String seatType = "GCDZTKYL0123456789";
    private List<? extends MyPickerView.Selection> selections;
    private List<Integer> mPositions;

    public PTrainHome(Context context, ViewManager_trainhome vm, List<UserBean> psgList) {
        super(context);
        this.vm = vm;
        this.psgList = psgList;
        MyApplication.getApplication().selectedPsgs.clear();
        selections = getSelections();
        mPositions = new ArrayList<>();
        mPositions.add(0);
    }


    private List<? extends MyPickerView.Selection> getSelections() {
        ArrayList<SelectionBean> list = new ArrayList<>();
        list.add(new SelectionBean("不限车次", true));
        list.add(new SelectionBean("高铁/动车", false));
        list.add(new SelectionBean("普通列车", false));
        return list;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToCityCode() {
        return toCityCode;
    }

    public String getFromCityCode() {
        return fromCityCode;
    }

    public void jumpActivity(int flag, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra("bundle", bundle);
        Activity ac = (Activity) this.context;
        switch (flag) {
            case Constant.ACTIVITY_CITY_FLAG:
                intent.setClass(context, CityListActivity.class);
                ac.startActivityForResult(intent, Constant.ACTIVITY_TRAIN_HOME_FLAG);
                break;
            case Constant.ACTIVITY_CALENDAR_FLAG:
                intent.putExtra(CldActivity.KEY_SELECTED_DATE_1, date);
                intent.setClass(context, CldActivity.class);
                ac.startActivityForResult(intent, Constant.ACTIVITY_TRAIN_HOME_FLAG);
                break;
            case Constant.ACTIVITY_PASSENGER_FLAG:
                bundle.putSerializable("psgs", (Serializable) psgList);
                intent.setClass(context, PassengerListActivity.class);
                ac.startActivityForResult(intent, Constant.ACTIVITY_TRAIN_HOME_FLAG);
                break;
            case Constant.ACTIVITY_TRAIN_LIST_FLAG:
                bundle.putString("from", fromCityCode);
                bundle.putString("to", toCityCode);
                bundle.putString("startdate", date);
                bundle.putString("seatType", seatType);
                MyApplication.fromCityCode = fromCityCode;
                MyApplication.toCityCode = toCityCode;
                MyApplication.fromCityName = vm.getFromCityName();
                MyApplication.toCityName = vm.getToCityName();
                intent.setClass(context, TrainListActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    public String getDate() {
        return date;
    }

    public void doChangeCities() {
        String s = fromCityCode;
        fromCityCode = toCityCode;
        toCityCode = s;
        vm.chageCities();
    }

    public void showTrainType() {
        DialogUtil.showMultiPickerView(context, "", selections, mPositions, new MyPickerView.OnPickerViewSure() {
            @Override
            public void onSingleSureClick(int p) {
            }

            @Override
            public void onMultiSureClick(List<Integer> s) {
                if (s.contains(0) || (s.contains(1) && s.contains(2))) {
                    seatType = Constant.TrainType.TRAIN_TYPE_ALL;
                } else if (s.contains(1)) {
                    seatType = Constant.TrainType.TRAIN_TYPE_GT;
                } else if (s.contains(2)) {
                    seatType = Constant.TrainType.TRAIN_TYPE_PT;
                }
                vm.setSeatType(seatType);
                mPositions = s;
            }
        });

    }


    public void setFromCityCode(String code) {
        fromCityCode = code;
    }

    public void setToCityCode(String toCityCode) {
        this.toCityCode = toCityCode;
    }

    public void getPolicy(List<UserBean> lpb) {
        RetrofitUtil.getPolicy(context, MUtils.getRequestStringByPsg(lpb), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                LogFactory.d("mTrainPolicy========" + bean.getData());
                if (status == 200) {
                    if (bean.getData().length() != 3) {
                        MyApplication.mTrainPolicy = new Gson().fromJson(bean.getData(), TrainPolicyBean.class);
                    } else {
                        MyApplication.mTrainPolicy = null;
                    }
                }
                vm.setPolicy(MUtils.getTrainPolicyString(MyApplication.mTrainPolicy));
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }


    public void savePsgs(List<UserBean> psgList) {
        MyApplication.getApplication().selectedPsgs.clear();
        MyApplication.getApplication().selectedPsgs.addAll(psgList);
    }
}
