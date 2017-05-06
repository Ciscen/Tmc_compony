package com.auvgo.tmc.train.interfaces;

import android.widget.BaseAdapter;

/**
 * Created by lc on 2016/11/15
 */

public interface ViewManager_trainlist {
    void setAdapter(BaseAdapter adapter);

    void setCalenderDate(String startdate);

    void freshTitle();

    void setFiltState(boolean b);

    void setEmptyView();
}
