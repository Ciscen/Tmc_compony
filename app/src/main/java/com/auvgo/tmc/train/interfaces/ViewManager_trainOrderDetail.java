package com.auvgo.tmc.train.interfaces;

import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.ChoseApproveLevelAdapter;
import com.auvgo.tmc.adapter.TrainOrderDetailPsgAdapter;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;

/**
 * Created by lc on 2016/11/21
 */

public interface ViewManager_trainOrderDetail {

    void updateViews(TrainOrderDetailBean b, TrainOrderDetailPsgAdapter adapter_psg, ApproveStateAdapter adapter_approve_state);

    void setWeiReason(String name);

    void setProject(String name);

    void setVisibility(TrainOrderDetailBean b);

    void setAdapter(ChoseApproveLevelAdapter adapter_approve_chose);
}
