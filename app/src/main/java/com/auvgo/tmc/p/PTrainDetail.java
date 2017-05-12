package com.auvgo.tmc.p;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.auvgo.tmc.adapter.TrainDetailAdapter;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.train.interfaces.ViewManager_traindetail;
import com.auvgo.tmc.train.activity.Login12306Activity;
import com.auvgo.tmc.train.activity.NoticeActivity;
import com.auvgo.tmc.train.activity.TrainBookActivity;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;

/**
 * Created by lc on 2016/11/16
 */
public class PTrainDetail extends BaseP {

    private TrainBean.DBean mBean;
    private String fromCode, toCode;
    private TrainDetailAdapter adapter;
    private ViewManager_traindetail vm;

    public PTrainDetail(Context context, ViewManager_traindetail vm) {
        super(context);
        this.vm = vm;
    }

    public TrainBean.DBean getmBean() {
        return mBean;
    }

    public void setmBean(TrainBean.DBean mBean) {
        this.mBean = mBean;
        adapter = new TrainDetailAdapter(mBean.getCanBook(), context, mBean.getTrain_code());
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public void setadapter() {
        vm.setadapter(adapter);
    }

    public void getTrainTime() {
        MUtils.showTimePop(context,
                TimeUtils.changePattern(mBean.getTrain_start_date()),
                mBean.getFrom_station_code(),
                mBean.getTo_station_code(),
                mBean.getTrain_no(), mBean.getFrom_station_name(), mBean.getTo_station_name());
    }

    public void jumpActivity(int groupPosition, int childPosition) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", mBean);//当前车次的实体类
        bundle.putInt("seattypeposition", groupPosition);//所选中的席别
        bundle.putInt("booktypeposition", childPosition);//预订方式
        bundle.putString("from", fromCode);//出发城市的编码
        bundle.putString("to", toCode);//到达城市的编码
        intent.putExtra("bundle", bundle);
        switch (childPosition) {
//            case 0:
//                //送票上门
//                intent.setClass(context, TrainBookActivity.class);
//                break;
            case 0:
                //行旅预订
                intent.setClass(context, TrainBookActivity.class);
                break;
            case 1:
                //12306登陆验证
                intent.setClass(context, Login12306Activity.class);
                break;
        }
        context.startActivity(intent);
    }

    public void jumpToNotice() {
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }
}
