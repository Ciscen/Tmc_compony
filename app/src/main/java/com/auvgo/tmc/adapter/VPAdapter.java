package com.auvgo.tmc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lc on 2016/11/26
 */

public class VPAdapter extends FragmentPagerAdapter {
    private List<? extends Fragment> list;

    public VPAdapter(FragmentManager fm, List<? extends Fragment> list) {
        super(fm);
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

}
