package com.auvgo.tmc.personalcenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auvgo.tmc.R;

/**
 * Created by lc on 2016/11/26
 */

public abstract class BaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayoutId(), container, false);
        initData(this);
        findViews(view);
        initView();
        initListener();
        return view;
    }

    protected abstract int getFragmentLayoutId();

    protected abstract void initData(Fragment fragment);

    protected abstract void findViews(View view);

    protected abstract void initView();

    protected abstract void initListener();


}
