package com.auvgo.tmc.hotel.interfaces;

/**
 * Created by lc on 2017/2/13
 */

public interface ViewManager_hotelquery {

    void showPolicy(String trainPolicyString);

    void updateSelectedPsgs();

    void setCityName();

    void setDate();

    void setKeyword();

    void setStarLevel();

    void setDelVisibility(int visibility);
}
