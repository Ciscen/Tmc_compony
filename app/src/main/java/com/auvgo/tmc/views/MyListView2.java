package com.auvgo.tmc.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ListView;

/**
 * Created by lc on 2016/11/11
 */
public class MyListView2 extends ListView {
    public boolean isOnMeasure;

    public MyListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView2(Context context) {
        super(context);
    }

    public MyListView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = 0;

        int size = MeasureSpec.getSize(heightMeasureSpec);
        float maxHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());
        if (size > maxHeight) {
            expandSpec = MeasureSpec.makeMeasureSpec((int) maxHeight, MeasureSpec.EXACTLY);
        } else {
            expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }

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
