package com.auvgo.tmc.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.auvgo.tmc.R;


/*
 *ListView右侧导航面板
 */
public class HotelSideBar extends View {
    private int textColor = getResources().getColor(R.color.colorPrimary);
    //是否点击
    private boolean showBkg = false;
    //监听面板是否点击接口
    OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    private String[] b = {"热门", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    //选择的值
    int choose = -1;
    //画笔
    Paint paint = new Paint();

    private View mDialog;

    public HotelSideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HotelSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HotelSideBar(Context context) {
        super(context);
    }

    public void setDialog(View dialog) {
        mDialog = dialog;
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //如果面板处于点击状态就将面板的背景色绘制为灰色
        if (showBkg) {
//            canvas.drawColor(bkgColor);
            this.setBackgroundResource(R.drawable.side_bar_bg);
        } else {
            this.setBackgroundResource(R.drawable.transparent);
        }
        //获得Ｖｉｅｗ的高
        int height = getHeight();
        //获得Ｖｉｅｗ的宽
        int width = getWidth();
        //计算得出每一个字体大概的高度
        int singleHeight = (height) / (b.length + 1);

        //为了上下空白可以均分,作计算
        int topEmpty = singleHeight / 2 + height % (b.length + 1) / 2;

        for (int i = 0; i < b.length; i++) {
            //设置锯齿
            paint.setAntiAlias(true);
            //设置字体大小
            paint.setTextSize(singleHeight * 3 / 4);
            if (i == 0) {
                paint.setTextSize(singleHeight * 7 / 12);
            }
            //点击的字体和26个字母中的任意一个相等就
            paint.setColor(textColor);
            if (i == choose) {
                //绘制点击的字体的颜色为#f57c00

                paint.setFakeBoldText(true);
            }
            //得到字体的X坐标
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            //得到字体的Y坐标
            float yPos = singleHeight * i + singleHeight + topEmpty;
            //将字体绘制到面板上
            canvas.drawText(b[i], xPos, yPos, paint);
            //还原画布
            paint.reset();
        }
    }

    /**
     * 点击事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //得到点击的状态
        final int action = event.getAction();
        //点击的Y坐标
        final float y = event.getY();

        final int oldChoose = choose;
        //监听
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        //得到当前的值
        final int c = (int) (y / getHeight() * b.length);

        //根据点击的状态不同做出不同的处理
        switch (action) {
            //按下已经开始
            case MotionEvent.ACTION_DOWN:
                //将开关设置为true
                showBkg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        //当当前点击的值绑定监听  这个监听在本页面中做的是接口。实际调用是在MainActiv中。也就是说我们调用这个接口会执行MainActivtiy的方法
                        listener.onTouchingLetterChanged(b[c]);
                        choose = c;
                        //刷新界面
                        invalidate();
                    }
                }

                break;
            //松开为完成点击
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onTouchingLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                    }
                }
                break;

            //完成松开  还原数据 并刷新界面
            case MotionEvent.ACTION_UP:
                showBkg = false;
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.setVisibility(View.INVISIBLE);
                    }
                }, 300);
                choose = -1;
                invalidate();
                break;
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

}
