package com.auvgo.tmc.plane.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.common.CityListActivity;
import com.auvgo.tmc.common.CldActivity;
import com.auvgo.tmc.common.PassengerListActivity;
import com.auvgo.tmc.plane.activity.PlaneListActivity;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyPickerView;
import com.google.gson.Gson;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BasePlaneHomeFragment2 extends Fragment implements View.OnClickListener {
    private TextView psgs_tv;
    private TextView notice_tv;
    protected TextView from_tv;
    protected TextView to_tv;
    private TextView dateStart_tv;
    private TextView dateBack_tv;
    private TextView type_tv;
    private TextView date_tv;
    private View item_date0;
    private View item_date1;
    private View item_policy;
    private int mPosition = 0;
    private int mTypePosition = 0;
    protected String fromCityCode = "PEK";
    protected String toCityCode = "SHA";
    protected String start_date = "";
    protected String back_date = "";
    protected Context context;
    protected ArrayList<UserBean> psgs = new ArrayList<>();
    private List<SelectionBean> selectionBeens = new ArrayList<>();
    private int wfFlag;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public BasePlaneHomeFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plane_home_base, container, false);
        initData();
        initView(view);
        initListener(view);
        getPolicy(psgs);
        return view;
    }

    private void initListener(View view) {
        view.findViewById(R.id.plane_home_click_person).setOnClickListener(this);
        view.findViewById(R.id.plane_home_click_from).setOnClickListener(this);
        view.findViewById(R.id.plane_home_click_to).setOnClickListener(this);
        view.findViewById(R.id.plane_home_click_change).setOnClickListener(this);
        view.findViewById(R.id.plane_home_click_date1).setOnClickListener(this);
        view.findViewById(R.id.plane_home_click_date0).setOnClickListener(this);
        view.findViewById(R.id.plane_home_click_type).setOnClickListener(this);
        view.findViewById(R.id.plane_home_click_query).setOnClickListener(this);
    }


    private void initData() {
        start_date = TimeUtils.getTomorrow();
        back_date = TimeUtils.getTomorrow(start_date);
        psgs.add(MyApplication.mUserInfoBean);
        mPosition = getPosition();
        selectionBeens.add(new SelectionBean("不限"));
        selectionBeens.add(new SelectionBean("经济舱"));
        selectionBeens.add(new SelectionBean("公务舱"));
        selectionBeens.add(new SelectionBean("头等舱"));
    }

    protected abstract int getPosition();

    private void initView(View view) {
        findViews(view);
        dateStart_tv.setText(String.format("%s %s", TimeUtils.getMMdd(start_date),
                TimeUtils.getTomorrowWeekDay(start_date)));
        dateBack_tv.setText(MessageFormat.format("{0} {1}", TimeUtils.getMMdd(back_date),
                TimeUtils.getTomorrowWeekDay(back_date)));
        date_tv.setText(String.format("%s %s", TimeUtils.getMMdd(start_date), TimeUtils.getTomorrowWeekDay(start_date)));
        if (mPosition == 0) {
            item_date1.setVisibility(View.GONE);
        } else {
            item_date0.setVisibility(View.GONE);
        }
        psgs_tv.setText(psgs.get(0).getName());
        if (MyApplication.mUserInfoBean.getIfallowbook() == 0) {
            view.findViewById(R.id.plane_home_arrow).setVisibility(View.GONE);
            view.findViewById(R.id.plane_home_click_person).setEnabled(false);
        }
    }

    private void findViews(View view) {
        psgs_tv = (TextView) view.findViewById(R.id.plane_home_persons);
        notice_tv = (TextView) view.findViewById(R.id.plane_home_notice);
        from_tv = (TextView) view.findViewById(R.id.plane_home_from);
        to_tv = (TextView) view.findViewById(R.id.plane_home_to);
        dateStart_tv = (TextView) view.findViewById(R.id.plane_home_date_start);
        dateBack_tv = (TextView) view.findViewById(R.id.plane_home_date_back);
        type_tv = (TextView) view.findViewById(R.id.plane_home_type);
        date_tv = (TextView) view.findViewById(R.id.plane_home_date);
        item_date0 = view.findViewById(R.id.plane_home_click_date0);
        item_date1 = view.findViewById(R.id.plane_home_click_date1);
        item_policy = view.findViewById(R.id.plane_home_policy_ll);
    }

    private void selectPsgs() {
        Intent intent = new Intent(context, PassengerListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("psgs", psgs);
        bundle.putString("from", Constant.PLANE);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, getFragmentFlag());
    }

    private void choseFromCity() {
        Intent intent = new Intent(context, CityListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("index", 0);
        bundle.putString("from", Constant.PLANE);
        bundle.putString("cityCode", toCityCode);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 0);
    }

    private void choseToCity() {
        Intent intent = new Intent(context, CityListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("index", 1);
        bundle.putString("from", Constant.PLANE);
        bundle.putString("cityCode", fromCityCode);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 1);
    }

    private void changeCity() {
        String from = from_tv.getText().toString().trim();
        String to = to_tv.getText().toString().trim();
        from_tv.setText(to);
        to_tv.setText(from);
        from = fromCityCode;
        fromCityCode = toCityCode;
        toCityCode = from;
    }

    private void selectDate1() {
        startToSelectDateAgain(wfFlag == 0 ? TimeUtils.getToday() : start_date);
    }

    private void selectDate0() {
        Intent intent = new Intent(context, CldActivity.class);
        intent.putExtra(CldActivity.KEY_SELECTED_DATE_1, start_date);
        startActivityForResult(intent, getFragmentFlag());
    }

    //返回为Constants.ACTIVITY_FLAG_PLANE_HOME的两个区分
    private int getFragmentFlag() {
        return mPosition == 0 ? Constant.ACTIVITY_PLANE_HOME_FLAG :
                Constant.ACTIVITY_PLANE_HOME_FLAG_WF;
    }

    private void updateSelectedPsgs() {
        if (psgs != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < psgs.size(); i++) {
                sb.append(psgs.get(i).getName());
                sb.append("、");
            }
            if (sb.toString().endsWith("、")) {
                sb.deleteCharAt(sb.length() - 1);
            }
            psgs_tv.setText(sb.toString());
        }
    }

    private void getPolicy(ArrayList<UserBean> psgs) {
        RetrofitUtil.getPlanePolicy(context, MUtils.getRequestStringByPsg(psgs), null,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            if (bean.getData().length() != 3) {
                                MyApplication.mPlanePolicy = new Gson().fromJson(bean.getData(), PlanePolicyBean.class);
                                setPolicyStr();
                            } else {
                                MyApplication.mPlanePolicy = null;
                                setPolicyStr();
                            }
                        } else {
                            MyApplication.mTrainPolicy = null;
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
    }

    private void setPolicyStr() {
        String str = "差旅标准：" + MUtils.getPlanePolicyString(MyApplication.mPlanePolicy);
        notice_tv.setText(str);
        if (MyApplication.mPlanePolicy == null) {
            item_policy.setVisibility(View.GONE);
        } else {
            item_policy.setVisibility(View.VISIBLE);
        }
    }


    private void selectCarbinType() {
        DialogUtil.showPickerPopWithSureHeight(context, "舱位类型", mTypePosition, selectionBeens,
                new MyPickerView.OnPickerViewSure() {
                    @Override
                    public void onSingleSureClick(int p) {
                        mTypePosition = p;
                        type_tv.setText(selectionBeens.get(p).getName());
                    }

                    @Override
                    public void onMultiSureClick(List<Integer> s) {

                    }
                });
    }

    protected abstract void doQueryTickets();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_home_click_person:
                selectPsgs();
                break;
            case R.id.plane_home_click_from:
                choseFromCity();
                break;
            case R.id.plane_home_click_to:
                choseToCity();
                break;
            case R.id.plane_home_click_change:
                changeCity();
                break;
            case R.id.plane_home_click_date1:
                selectDate1();
                break;
            case R.id.plane_home_click_date0:
                selectDate0();
                break;
            case R.id.plane_home_click_type:
                selectCarbinType();
                break;

            case R.id.plane_home_click_query:
                MyApplication.getApplication().selectedPsgs = psgs;
                doQueryTickets();
                Intent intent = new Intent(context, PlaneListActivity.class);
                intent.putExtra("fromCode", fromCityCode);
                intent.putExtra("toCode", toCityCode);
                String fromName = from_tv.getText().toString().trim();
                intent.putExtra("fromName", fromName);
                String toName = to_tv.getText().toString().trim();
                intent.putExtra("toName", toName);
                intent.putExtra("type", mTypePosition);
                intent.putExtra("startDate", start_date);
                MyApplication.fromCityName = fromName;
                MyApplication.toCityName = toName;
                MyApplication.fromCityCode = fromCityCode;
                MyApplication.toCityCode = toCityCode;
                context.startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogFactory.d(requestCode + "---------------" + resultCode);
        if (resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            if (requestCode == Constant.ACTIVITY_PLANE_HOME_FLAG_WF) {//往返
                if (wfFlag == 0) {
                    start_date = data.getStringExtra(CldActivity.KEY_RESULT_FIRST);
                    dateStart_tv.setText(TimeUtils.getMMdd(start_date) + "  " + TimeUtils.getTomorrowWeekDay(start_date));
                    if (TimeUtils.getTimeStamp(start_date, "yyyy-MM-dd") > TimeUtils.getTimeStamp(back_date, "yyyy-MM-dd")) {
                        back_date = start_date;
                        setBackDate();
                    }
                    wfFlag = 1;// TODO: 2017/4/20 根据第一天，修改第二天的日期
                    startToSelectDateAgain(start_date);
                } else {
                    back_date = data.getStringExtra(CldActivity.KEY_RESULT_FIRST);
                    setBackDate();
                    wfFlag = 0;
                }
            }
            if (requestCode == Constant.ACTIVITY_PLANE_HOME_FLAG) {//单程
                start_date = data.getStringExtra(CldActivity.KEY_RESULT_FIRST);
                date_tv.setText(TimeUtils.getMMdd(start_date) + "  " + TimeUtils.getTomorrowWeekDay(start_date));
            }
        }
        if (resultCode == Constant.ACTIVITY_PASSENGER_FLAG) {
            ArrayList<UserBean> list = (ArrayList<UserBean>) data.getSerializableExtra("psgs");
            //将之前的清空
            psgs.clear();
            //接收新数据
            psgs.addAll(list);
            if (psgs.size() == 0) {
                psgs.add(MyApplication.mUserInfoBean);
            }
            //拿到新数据后，更新一下ui，将已经选择的乘客，显示到界面
            updateSelectedPsgs();
            //重新请求一下政策
            getPolicy(psgs);
        }
        if (resultCode == Constant.ACTIVITY_CITY_FLAG) {
            String cityName = data.getStringExtra("name");
            String cityCode = data.getStringExtra("code");
            if (requestCode == 0) {
                from_tv.setText(cityName);
                fromCityCode = cityCode;
            } else {
                to_tv.setText(cityName);
                toCityCode = cityCode;
            }
            LogFactory.d(cityName + "------->" + cityCode);
        }
    }

    private void setBackDate() {
        dateBack_tv.setText(TimeUtils.getMMdd(back_date) + "  " + TimeUtils.getTomorrowWeekDay(back_date));
    }

    private void startToSelectDateAgain(String limit) {
        Intent intent = new Intent(context, CldActivity.class);

        intent.putExtra(CldActivity.KEY_FIRST_TAG, wfFlag == 0 ? "去程" : "返程");
        intent.putExtra(CldActivity.KEY_ISSINGLE, true);
        intent.putExtra(CldActivity.KEY_INTERVAL_DAY_FIRST, limit);

        startActivityForResult(intent, getFragmentFlag());
        ToastUtils.showTextToast(wfFlag == 0 ? "请选择去程日期" : "请选择返程日期");
    }
}
