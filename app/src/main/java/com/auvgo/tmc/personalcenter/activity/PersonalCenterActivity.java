package com.auvgo.tmc.personalcenter.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.LoginActivity;
import com.auvgo.tmc.common.WaitForOpenActivity;
import com.auvgo.tmc.utils.AppCleanUtil;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalCenterActivity extends BaseActivity {
    @BindView(R.id.pc_name)
    TextView name;
    @BindView(R.id.pc_update_tv)
    TextView update_tv;
    @BindView(R.id.pc_myPlane)
    View myPlane;
    @BindView(R.id.pc_myHotel)
    View myHotel;
    @BindView(R.id.pc_myTrain)
    View myTrain;
    @BindView(R.id.pc_item_myChailvInfo)
    View item_myChailvInfo;
    @BindView(R.id.pc_item_myRoute)
    View item_myRoute;
    @BindView(R.id.pc_item_myFlight)
    View item_myFlight;
    @BindView(R.id.pc_item_myCheckRecord)
    View item_checkRecord;
    @BindView(R.id.pc_item_applyNo)
    View applyNo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void initView() {
        name.setText(MyApplication.mUserInfoBean.getName());
        update_tv.setText("版本" + AppUtils.getVersionName(this));
        setApplyNoVisibility(applyNo);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void getData() {
        super.getData();
    }

    @Override
    protected void setViews() {

    }

    @OnClick({R.id.pc_name, R.id.pc_psw, R.id.pc_exit, R.id.pc_item_myChailvInfo, R.id.pc_myHotel,
            R.id.pc_myTrain, R.id.pc_myPlane, R.id.pc_about, R.id.pc_item_myRoute,
            R.id.pc_item_myFlight, R.id.pc_item_myCheckRecord, R.id.pc_item_applyNo})
    public void onClick(View v) {

        if (v.getId() == R.id.pc_clean) {
            AppCleanUtil.cleanApplicationData(this);
            DialogUtil.showDialog(this, "提示", "确定", "", "清除成功！", null);
            return;
        }
        Intent intent = new Intent();
        Class c = null;
        switch (v.getId()) {
            case R.id.pc_name:
                c = PersonInfoActivity.class;
                break;
            case R.id.pc_psw:
                c = UpdatePasswordActivity.class;
                break;
            case R.id.pc_exit:
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                c = LoginActivity.class;
                SPUtils.put(this, Constant.KEY_AUTOLOGIN, false);
                break;
            case R.id.pc_item_myChailvInfo:
                c = MyTravalInfoActivity.class;
                break;
            case R.id.pc_myHotel:
                c = OrderListActivity.class;
                intent.putExtra("class", Constant.HOTEL);
                break;
            case R.id.pc_myTrain:
                c = OrderListActivity.class;
                intent.putExtra("class", Constant.TRAIN);
                break;
            case R.id.pc_myPlane:
                c = OrderListActivity.class;
                intent.putExtra("class", Constant.PLANE);
                break;
            case R.id.pc_about:
                c = AboutActivity.class;
                break;
            case R.id.pc_item_myRoute:
            case R.id.pc_item_myFlight:
            case R.id.pc_item_myCheckRecord:
                c = WaitForOpenActivity.class;
                break;
            case R.id.pc_item_applyNo:
                c = ApplyNoListActivity.class;
                break;
        }
        intent.setClass(this, c);
        startActivity(intent);
    }
}
