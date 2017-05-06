package com.auvgo.tmc.plane.fragment;

import com.auvgo.tmc.MyApplication;

public class PlaneHomeFragment extends BasePlaneHomeFragment2 {

    public PlaneHomeFragment() {
    }

    @Override
    protected int getPosition() {
        return 0;
    }

    @Override
    protected void doQueryTickets() {
        MyApplication.isWF = false;
    }
}
