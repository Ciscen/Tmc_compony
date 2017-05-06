package com.auvgo.tmc.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by lc on 2017/3/6
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                20000, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
