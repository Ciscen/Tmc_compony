package com.auvgo.tmc.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.LogFactory;

/**
 * Created by lc on 2017/2/16
 */

public class StarPopView extends LinearLayout {
    private RangeSeekBar seekBar;
    private View sure;
    private View cancle;
    private Context context;
    private TextView[] stars;
    private int[] a;
    private boolean[] b;
    private int colorBlue;
    private int colorDefault;
    private int colorWhite;
    private StarPopListener listener;

    public StarPopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StarPopView(Context context, int[] a, boolean[] b) {
        super(context);
        this.a = a;
        this.b = b;
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        initColors();
        View.inflate(context, R.layout.layout_hotel_star_pop, this);
        initTextViews();
        seekBar = (RangeSeekBar) findViewById(R.id.RangeSeekBar);
        sure = findViewById(R.id.hotel_pop_sure);
        cancle = findViewById(R.id.hotel_pop_cancle);
        initPosition();
        initListener();
    }

    private void initPosition() {
        MyApplication.getApplication().getmMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                seekBar.setStartPosition(a[0], a[1]);
            }
        }, 100);
    }

    public void setListener(StarPopListener listener) {
        this.listener = listener;
    }

    private void initListener() {
        seekBar.setListener(new RangeSeekBar.RangeSeekBarListener() {
            @Override
            public void onCreate(int index, float value) {

            }

            @Override
            public void onSeek(int index, int current) {
                LogFactory.d("StarPopView========index=" + index + "position======" + current);
                a[index] = current;
            }
        });
        sure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSureClick(a, b);
            }
        });
        cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                a[0] = 0;
                a[1] = 20;
                for (int i = 0; i < b.length; i++) {
                    if (i == 0) {
                        b[0] = true;
                    } else {
                        b[i] = false;
                    }
                }
                seekBar.setStartPosition(0, 20);
                freshStars();
                listener.onCancel();
            }
        });
    }

    private void initColors() {
        colorBlue = getResources().getColor(R.color.appTheme1);
        colorDefault = Color.BLACK;
        colorWhite = Color.WHITE;
    }

    private void initTextViews() {
        findStars();
        freshStars();
    }

    private void findStars() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.hotel_pop_stars);
        stars = new TextView[5];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = (TextView) viewGroup.getChildAt(i);
        }
    }

    private void freshStars() {
        for (int i = 0; i < stars.length; i++) {
            final int finalI = i;
            stars[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == 0 && b[0]) {//如果全部已经选中，再次点击全部，无效
                        return;
                    } else {
                        b[finalI] = !b[finalI];
                    }
                    if (finalI != 0 && b[0]) {
                        b[0] = false;
                    }
                    if (finalI == 0 && !b[0]) {
                        b[0] = true;
                    }
                    updateDate(finalI);//纠正数组的内容
                }
            });
            if (b[i]) {
                stars[i].setBackgroundColor(colorBlue);
                stars[i].setTextColor(colorWhite);
            } else {
                stars[i].setBackgroundColor(colorWhite);
                stars[i].setTextColor(colorDefault);
            }
        }
    }

    private void updateDate(int finalI) {
        if (b[0]) {
            for (int i = 1; i < b.length; i++) {
                b[i] = false;
            }
        }
        boolean isAll = true;
        for (int i = 1; i < b.length; i++) {
            if (!b[i]) {
                isAll = false;
                break;
            }
        }
        //排除情况：全部未选中
        boolean allCancel = true;
        for (int i = 0; i < b.length; i++) {
            if (b[i]) {
                allCancel = false;
            }
        }
        if (allCancel) {
            b[finalI] = true;
        }
        if (isAll) {
            b[0] = true;
            for (int i = 1; i < b.length; i++) {
                b[i] = false;
            }
        }
        freshStars();
    }

    public void setStartPosition(int[] aa, boolean[] bb) {
        this.a = aa;
        this.b = bb;
        MyApplication.getApplication().getmMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                seekBar.setStartPosition(a[0], a[1]);
            }
        }, 100);

    }

    public interface StarPopListener {
        void onSureClick(int[] positions, boolean[] stars);

        void onCancel();
    }

}
