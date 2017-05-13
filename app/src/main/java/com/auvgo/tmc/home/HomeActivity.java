package com.auvgo.tmc.home;

import android.content.Intent;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.TestActivity;
import com.auvgo.tmc.approve.HotelApproveActivity;
import com.auvgo.tmc.approve.PlaneApproveActivity;
import com.auvgo.tmc.approve.TrainApproveActivity;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.bean.JpushJumpInfo;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.hotel.activity.HotelOrderDetailActivity;
import com.auvgo.tmc.p.PHome;
import com.auvgo.tmc.plane.activity.PlaneOrderDetailActivity;
import com.auvgo.tmc.train.activity.TrainOrderDetailActivity;
import com.auvgo.tmc.train.interfaces.ViewManager_home;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements ViewManager_home {

    private PHome pHome;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        pHome = new PHome(this, this);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setViews() {
    }

    @OnClick({R.id.home_zhiji, R.id.home_dongtai, R.id.home_approve, R.id.home_personal
            , R.id.home_plane, R.id.home_train, R.id.home_hotel})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_zhiji:
//                MUtils.jumpToPage(this, TestActivity.class, -1);
//                System.gc();
                pHome.jumpActivity(Constant.ACTIVITY_CHECKONLINE_FLAG);
                break;
            case R.id.home_dongtai:
                pHome.jumpActivity(Constant.ACTIVITY_FLIGHTDYNAMIC_FLAG);
                break;
            case R.id.home_approve:
                pHome.jumpActivity(Constant.ACTIVITY_APPROVE_FLAG);
                break;
            case R.id.home_personal:
                pHome.jumpActivity(Constant.ACTIVITY_PERSONAL_FLAG);
                break;
            case R.id.home_plane:
                pHome.jumpActivity(Constant.ACTIVITY_PLANE_HOME_FLAG);
                break;
            case R.id.home_train:
                pHome.jumpActivity(Constant.ACTIVITY_TRAIN_HOME_FLAG);
                break;
            case R.id.home_hotel:
                pHome.jumpActivity(Constant.ACTIVITY_HOTEL_HOME_FLAG);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        JpushJumpInfo jpushJumpInfo = MyApplication.getApplication().getjInfo();
        if (jpushJumpInfo == null) {
            return;
        }
        int flag = jpushJumpInfo.flag;
        if (flag != -1) {
            switch (flag) {
                case Constant.PushDirectionType.PUSH_DIRECTION_APPROVE://如果目标是审批列表页面
                    if (MyApplication.mUserInfoBean.getIfapprove() == 1) {
                        switch (jpushJumpInfo.extra2) {
                            case Constant.TRAIN:
                                intent.setClass(this, TrainApproveActivity.class);
                                break;
                            case Constant.PLANE:
                                intent.putExtra("type", jpushJumpInfo.extra2);
                                intent.setClass(this, PlaneApproveActivity.class);
                                break;
                            case Constant.HOTEL:
                                intent.setClass(this, HotelApproveActivity.class);
                                break;
                            default:
                                return;
                        }
                        intent.putExtra("orderNo", jpushJumpInfo.extra1);
                    } else {
                        ToastUtils.showTextToast("没有审批权限");
                        return;
                    }
                    break;
                case Constant.PushDirectionType.PUSH_DIRECTION_DETAIL:
                    switch (jpushJumpInfo.extra2) {
                        case Constant.TRAIN:
                            intent.setClass(this, TrainOrderDetailActivity.class);
                            break;
                        case Constant.PLANE:
                            intent.setClass(this, PlaneOrderDetailActivity.class);
                            break;
                        case Constant.HOTEL:
                            intent.setClass(this, HotelOrderDetailActivity.class);
                        default:
                            return;
                    }
                    intent.putExtra("orderNo", jpushJumpInfo.extra1);
                    break;
                case Constant.PushDirectionType.PUSH_DIRECTION_HOME:
                    intent.setClass(this, HomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
        jpushJumpInfo.flag = -1;
    }

//    private static class MyHandler extends Handler {
//        WeakReference<Activity> context;
//
//        public MyHandler(Activity context) {
//            this.context = new WeakReference<>(context);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Activity activity = context.get();
//        }
//    }
}
