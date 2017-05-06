package com.auvgo.tmc.plane.fragment;

import com.auvgo.tmc.MyApplication;

/**
 * Created by lc on 2016/12/20
 */
public class PlaneHomeWFFragment extends BasePlaneHomeFragment2 {


    @Override
    protected int getPosition() {
        return 1;
    }

    @Override
    protected void doQueryTickets() {
        MyApplication.isWF = true;
        MyApplication.gotoDate = start_date;
        MyApplication.returnDate = back_date;
    }
}
