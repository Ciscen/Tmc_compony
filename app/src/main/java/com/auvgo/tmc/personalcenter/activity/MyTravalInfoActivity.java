package com.auvgo.tmc.personalcenter.activity;

import android.text.TextUtils;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.TravalInfoPlanePolicyAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.bean.TravalInfoBean;
import com.auvgo.tmc.hotel.bean.HotelPolicyBean;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainPolicyBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.MyGridView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTravalInfoActivity extends BaseActivity {

    private MyGridView gv;
    private TextView train_gt, train_dc, train_pt, approve_rule, approve_leaders, name, hotel;
    private TravalInfoBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_traval_info;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void findViews() {
        gv = (MyGridView) findViewById(R.id.my_traval_info_plane_gv);
        train_gt = (TextView) findViewById(R.id.my_traval_info_train_gaotie);
        train_dc = (TextView) findViewById(R.id.my_traval_info_train_dongche);
        train_pt = (TextView) findViewById(R.id.my_traval_info_train_putong);
        name = (TextView) findViewById(R.id.my_traval_info_train_name);
        approve_rule = (TextView) findViewById(R.id.my_traval_info_train_approve_rule);
        hotel = (TextView) findViewById(R.id.my_traval_info_hotel);
        approve_leaders = (TextView) findViewById(R.id.my_traval_info_train_approve_leaders);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void setViews() {
    }

    @Override
    protected void getData() {
        super.getData();
        Map<String, String> map = new HashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("level", MyApplication.mUserInfoBean.getZhiwei());
        RetrofitUtil.getMyTravelInfo(this, AppUtils.getJson(map), TravalInfoBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (TravalInfoBean) o;
                    updateViews();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void updateViews() {
        List<String> airPolicy;
        Gson gson = new Gson();
        PlanePolicyBean ppb;
        TrainPolicyBean tpb;
        HotelPolicyBean hpb;
        if (mBean.getAirPolicy().length() != 3) {
            ppb = gson.fromJson(mBean.getAirPolicy(), PlanePolicyBean.class);
        } else {
            ppb = null;
        }
        if (mBean.getTrainPolicy().length() != 3) {
            tpb = gson.fromJson(mBean.getTrainPolicy(), TrainPolicyBean.class);
        } else {
            tpb = null;
        }
        if (mBean.getHotelPolicy().length() != 3) {
            hpb = gson.fromJson(mBean.getHotelPolicy(), HotelPolicyBean.class);
        } else {
            hpb = null;
        }
        String pps = MUtils.getPlanePolicyString(ppb);
        if (!TextUtils.isEmpty(pps)) {
            String[] split = pps.split(";");
            airPolicy = new ArrayList<>();
            Collections.addAll(airPolicy, split);
            if (airPolicy.size() % 2 == 1) {
                airPolicy.add("");
            }
            TravalInfoPlanePolicyAdapter tippadapter = new TravalInfoPlanePolicyAdapter(this, airPolicy);
            gv.setAdapter(tippadapter);
        }
        hotel.setText(MUtils.getHotelPolicyStr(hpb));
        train_gt.setText(MUtils.getTrainStrFromField(tpb == null ? "" : tpb.getGaotie()).replace("、", ","));
        train_dc.setText(MUtils.getTrainStrFromField(tpb == null ? "" : tpb.getDonche().replace("、", ",")));
        train_pt.setText(MUtils.getTrainStrFromField(tpb == null ? "" : tpb.getPukuai().replace("、", ",")));
        approve_rule.setText("审批规则:");
        name.setText(MyApplication.mUserInfoBean.getName());

    }
}
