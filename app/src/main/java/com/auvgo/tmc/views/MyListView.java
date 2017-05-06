package com.auvgo.tmc.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lc on 2016/11/11
 */
public class MyListView extends ListView {
    public boolean isOnMeasure;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        isOnMeasure = true;
//        int expandSpec = MeasureSpec.makeMeasureSpec(
//                1000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }

    public boolean isOnMeasure() {
        return isOnMeasure;
    }

}
