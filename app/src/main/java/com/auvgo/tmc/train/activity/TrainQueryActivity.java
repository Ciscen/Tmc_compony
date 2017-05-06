package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.CldActivity;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainhome;
import com.auvgo.tmc.p.PTrainHome;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrainQueryActivity extends BaseActivity implements ViewManager_trainhome {

    @BindView(R.id.train_home_from)
    TextView tv_from;//
    @BindView(R.id.train_home_to)
    TextView tv_to;//到达城市名称
    @BindView(R.id.train_home_date)
    TextView tv_date; // 出发日期
    @BindView(R.id.train_home_datedesc)
    TextView tv_datedesc; // 日期描述  今天明天
    @BindView(R.id.train_home_traintype)
    TextView tv_traintype; // 坐席类型
    @BindView(R.id.train_home_persons)
    TextView tv_persons; //出行人员
    @BindView(R.id.train_home_notice)
    TextView tv_notice;//提示
    @BindView(R.id.train_home_policy_ll)
    View ll_policy;
    private List<UserBean> psgList;
    private PTrainHome pTrainHome;//处理逻辑
    private java.lang.String tag = "trainhomeactivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_home;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        psgList = new ArrayList<>();
        psgList.add(MyApplication.mUserInfoBean);
        pTrainHome = new PTrainHome(this, this, psgList);
    }


    @Override
    protected void initView() {
        tv_date.setText(TimeUtils.getTomorrow() + " " + TimeUtils.getTomorrowWeekDay(TimeUtils.getTomorrow()));
        updateSelectedPsgs();
        if (!isAllowBook()) {
            findViewById(R.id.train_home_click_person).setEnabled(false);
            findViewById(R.id.train_home_click_arrow).setVisibility(View.GONE);
        }
    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        super.getData();
        List<UserBean> al = new ArrayList<UserBean>();
        al.add(MyApplication.mUserInfoBean);
        pTrainHome.getPolicy(al);
    }

    @OnClick({R.id.train_home_click_from, R.id.train_home_click_to, R.id.train_home_click_change,
            R.id.train_home_click_date, R.id.train_home_click_traintype, R.id.train_home_click_person,
            R.id.train_home_click_query})
    void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.train_home_click_from:
                bundle.putInt("index", 0);//表示from发起的
                bundle.putString("from", Constant.TRAIN);
                bundle.putString("cityCode", pTrainHome.getToCityCode());
                pTrainHome.jumpActivity(Constant.ACTIVITY_CITY_FLAG, bundle);
                break;
            case R.id.train_home_click_to:
                bundle.putInt("index", 1);//表示to发起的
                bundle.putString("from", Constant.TRAIN);
                bundle.putString("cityCode", pTrainHome.getFromCityCode());
                pTrainHome.jumpActivity(Constant.ACTIVITY_CITY_FLAG, bundle);
                break;
            case R.id.train_home_click_change:
                pTrainHome.doChangeCities();
                break;
            case R.id.train_home_click_date:
                pTrainHome.jumpActivity(Constant.ACTIVITY_CALENDAR_FLAG, bundle);
                break;
            case R.id.train_home_click_traintype:
                pTrainHome.showTrainType();
                break;
            case R.id.train_home_click_person:
                if (isAllowBook()) {
                    bundle.putBoolean("isSingle", false);//标记是否是单选
                    pTrainHome.jumpActivity(Constant.ACTIVITY_PASSENGER_FLAG, bundle);
                }
                break;
            case R.id.train_home_click_query:
                pTrainHome.savePsgs(psgList);
                pTrainHome.jumpActivity(Constant.ACTIVITY_TRAIN_LIST_FLAG, bundle);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_TRAIN_HOME_FLAG
                && resultCode == Constant.ACTIVITY_CITY_FLAG) {
            String code = data.getStringExtra("code");
            String name = data.getStringExtra("name");
            int index = data.getIntExtra("index", 0);
            if (index == 0) {
                tv_from.setText(name);
                pTrainHome.setFromCityCode(code);
            } else {
                tv_to.setText(name);
                pTrainHome.setToCityCode(code);
            }
        }
        if (requestCode == Constant.ACTIVITY_TRAIN_HOME_FLAG
                && resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            String a = data.getStringExtra(CldActivity.KEY_RESULT_FIRST);
            tv_date.setText(a + " " + TimeUtils.getTomorrowWeekDay(a));
            pTrainHome.setDate(a);
        }
        if (requestCode == Constant.ACTIVITY_TRAIN_HOME_FLAG
                && resultCode == Constant.ACTIVITY_PASSENGER_FLAG) {
            //将之前的清空
            psgList.clear();
            //接收新数据
            psgList.addAll((List<UserBean>) data.getSerializableExtra("psgs"));
            if (psgList.size() == 0) {
                psgList.add(MyApplication.mUserInfoBean);
            }
            //拿到新数据后，更新一下ui，将已经选择的乘客，显示到界面
            updateSelectedPsgs();
            //重新请求一下政策
            pTrainHome.getPolicy(psgList);
        }
    }

    private void updateSelectedPsgs() {
        if (psgList != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < psgList.size(); i++) {
                sb.append(psgList.get(i).getName());
                sb.append("、");
            }

            if (sb.toString().endsWith("、")) {
                sb.deleteCharAt(sb.length() - 1);
            }
            tv_persons.setText(sb.toString());
        }
    }

    @Override
    public void chageCities() {
        String s = tv_from.getText().toString();
        tv_from.setText(tv_to.getText().toString());
        tv_to.setText(s);
    }

    @Override
    public void setPolicy(String policyString) {
        tv_notice.setText(String.format("差旅标准：\n%s", policyString));
        if (MyApplication.mTrainPolicy == null) {
            ll_policy.setVisibility(View.GONE);
        } else {
            ll_policy.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSeatType(String seatType) {
        if (seatType.equals(Constant.TrainType.TRAIN_TYPE_ALL)) {
            tv_traintype.setText("不限");
        }
        if (seatType.equals(Constant.TrainType.TRAIN_TYPE_PT)) {
            tv_traintype.setText("普通列车");
        }
        if (seatType.equals(Constant.TrainType.TRAIN_TYPE_GT)) {
            tv_traintype.setText("高铁/动车");
        }
    }

    @Override
    public String getFromCityName() {
        return tv_from.getText().toString();
    }

    @Override
    public String getToCityName() {
        return tv_to.getText().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.fromCityCode = null;
        MyApplication.toCityCode = null;
        MyApplication.fromCityName = null;
        MyApplication.toCityName = null;
    }
}
