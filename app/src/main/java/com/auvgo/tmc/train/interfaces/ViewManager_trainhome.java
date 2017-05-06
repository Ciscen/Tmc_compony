package com.auvgo.tmc.train.interfaces;

/**
 * Created by lc on 2016/11/10
 */

public interface ViewManager_trainhome {
    void chageCities();

    void setPolicy(String policyString);

    void setSeatType(String seatType);

    String getFromCityName();

    String getToCityName();
}
