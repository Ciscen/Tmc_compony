package com.auvgo.tmc.hotel.interfaces;

/**
 * Created by lc on 2017/2/15
 */

public interface ViewManager_hotellist {

    void setDate();
    void setKeyword();

    void setAdapter();

    void onLoadFinished();

    void setDelVisibility();

    void setSortState(boolean b);

    void setStarState(boolean b);

    void setLocationState(boolean b);

    void setFiltState(boolean b);

    void setEmptyView();
}
