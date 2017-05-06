package com.auvgo.tmc.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.auvgo.tmc.R;

/**
 * Created by lc on 2017/3/22
 */

public class EmptyView extends LinearLayout {
    public EmptyView(Context context) {
        super(context,null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);

    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this);
    }

}
