package com.auvgo.tmc.views;


import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.DensityUtils;


public class RangeSeekBar extends View {

    private static final float VERTICAL_POSITION = 0.5f;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static final String TAG = "RangeSeekBar";
    private static final int DEFAULT_THUMBS = 2;
    private static final float DEFAULT_STEP = 5.0f;
    float startCo = 0;
    private List<RangeSeekBarListener> listeners;
    private List<Thumb> thumbs;
    private float thumbWidth;
    private float thumbHeight;
    private float thumbHalf;
    private float pixelRangeMin;
    private float pixelRangeMax;
    private int orientation = HORIZONTAL;
    private boolean limitThumbRange;
    private int viewWidth;
    private int viewHeight;
    private float scaleRangeMin;//代表值的最小值
    private float scaleRangeMax;//代表值的最大值
    private float trackHeight;//轨道的高度
    private float scaleStep;
    private Drawable track;
    private Drawable range;
    private Drawable thumb;
    private boolean firstRun;
    private int currentThumb = 0;
    private float lowLimit = pixelRangeMin;
    private float highLimit = pixelRangeMax;
    private Paint mPaint;

    private int currentIndex = 0;//Thumb当前位置，用于内部的标记
    private int currentLowIndex = 0;//左边的Thumb位置
    private int currentHighIndex = 21;//右边Thumb位置
    private String[] prices = {"  ¥0", "¥150", "¥300", "¥500", "¥700", "¥900", "不限"};
    private float textSize = 44;
    private Context context;
    private int currentPriceLeft = 0;
    private int currentPriceRight = 1000;

    public RangeSeekBar(Context context) {
        super(context);
        init(context);

    }

    /**
     * 设置起始位置
     *
     * @param first
     * @param second
     */
    public void setStartPosition(int first, int second) {
        currentLowIndex = first;
        currentHighIndex = second;
        currentPriceLeft = 50 * first;
        currentPriceRight = 50 * second;
        thumbs.get(0).pos = scaleToPixel(currentPriceLeft);
        thumbs.get(1).pos = scaleToPixel(currentPriceRight);
        invalidate();
    }

    /**
     * Construct object, initializing with any attributes we understand from a
     * layout file. These attributes are defined in
     * SDK/assets/res/any/classes.xml.
     *
     * @see View#View(Context, AttributeSet)
     */
    public RangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        // Obtain our styled custom attributes from xml
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);


        String s = a.getString(R.styleable.RangeSeekBar_orientation);
        if (s != null)
            orientation = s.toLowerCase().contains("vertical") ? VERTICAL : HORIZONTAL;

        limitThumbRange = a.getBoolean(R.styleable.RangeSeekBar_limitThumbRange, true);

        textSize = a.getDimension(R.styleable.RangeSeekBar_textSize, 24);
        scaleRangeMin = a.getFloat(R.styleable.RangeSeekBar_scaleMin, 0);
        scaleRangeMax = a.getFloat(R.styleable.RangeSeekBar_scaleMax, 100);
        trackHeight = a.getDimension(R.styleable.RangeSeekBar_trackHeihgt, 10);
        scaleStep = Math.abs(a.getFloat(R.styleable.RangeSeekBar_scaleStep, DEFAULT_STEP));

        thumb = a.getDrawable(R.styleable.RangeSeekBar_thumb);

        range = a.getDrawable(R.styleable.RangeSeekBar_range);

        track = a.getDrawable(R.styleable.RangeSeekBar_track_rsb);

        // Register desired amount of thumbs
        int noThumbs = a.getInt(R.styleable.RangeSeekBar_thumbs, DEFAULT_THUMBS);
        thumbWidth = a.getDimension(R.styleable.RangeSeekBar_thumbWidth, 50);
        thumbHeight = a.getDimension(R.styleable.RangeSeekBar_thumbHeight, 100);
        for (int i = 0; i < noThumbs; i++) {
            Thumb th = new Thumb();
            thumbs.add(th);
        }
        a.recycle();
        initPaint();
    }

    private void init(Context context) {
        this.context = context;
        orientation = HORIZONTAL;
        limitThumbRange = true;
        scaleRangeMin = 0;
        scaleRangeMax = 100;
        scaleStep = DEFAULT_STEP;

        viewWidth = 0;
        viewHeight = 0;

        thumbWidth = 50;
        thumbHeight = 100;

        thumbs = new Vector<>();
        listeners = new Vector<>();

        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        firstRun = true;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setColor(getResources().getColor(R.color.appTheme2));
        mPaint.setAntiAlias(true);
    }

    /**
     * {@inheritDoc}
     *
     * @see View#measure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = measureWidth(widthMeasureSpec);
        viewHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight);

        //
        thumbHalf = (orientation == VERTICAL) ? (thumbHeight / 2) : (thumbWidth / 2);
        pixelRangeMin = getPaddingLeft() + thumbHalf;
        pixelRangeMax = (orientation == VERTICAL) ? viewHeight : viewWidth;
        pixelRangeMax = pixelRangeMax - getPaddingRight() - thumbHalf;

        if (firstRun) {

            // Fire listener callback
            if (listeners != null && listeners.size() > 0) {
                for (RangeSeekBarListener l : listeners) {
                    l.onCreate(currentThumb, getThumbValue(currentThumb));
                }
            }
        }
    }

    /**
     * Draw
     *
     * @see View#onDraw(Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);    // 1. Make sure parent view get to draw it's components
        drawGutter(canvas);        // 2. Draw slider gutter
        drawRange(canvas);        // 3. Draw range in gutter
        drawThumbs(canvas);        // 4. Draw thumbs
        drawText(canvas);
        if (firstRun) {
            distributeThumbsEvenly();
            firstRun = false;
        }
    }

    /**
     * 绘制底部文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        float step = (pixelRangeMax - pixelRangeMin) / ((scaleRangeMax - scaleRangeMin) / scaleStep);
//        for (int i = 0; i <= (scaleRangeMax - scaleRangeMin) / scaleStep; i++) {
//            mPaint.setColor(getResources().getColor(R.color.appTheme2));
//            canvas.drawCircle(pixelRangeMin + step * i, getMeasuredHeight() * 0.65f, 4, mPaint);
//            mPaint.setColor(getResources().getColor(R.color.color_666));
//            if (i == currentLowIndex || i == currentHighIndex) {
//                mPaint.setColor(getResources().getColor(R.color.colorPrimary));
//            }
//            canvas.drawText(50 * i + "", pixelRangeMin - 30 + step * i, getMeasuredHeight() * 0.8f, mPaint);
//        }
//        if (currentThumb == 0) {
//            canvas.drawText(50 * currentIndex + "", DensityUtils.dp2px(context, 15), getMeasuredHeight() * 0.2f, mPaint);
//            canvas.drawText(currentPriceRight + "", getMeasuredWidth() - DensityUtils.dp2px(context, 30), getMeasuredHeight() * 0.2f, mPaint);
//        } else if (currentThumb == 1) {
//            canvas.drawText(currentPriceLeft + "", DensityUtils.dp2px(context, 15), getMeasuredHeight() * 0.2f, mPaint);
//            canvas.drawText(50 * currentIndex + "", getMeasuredWidth() - DensityUtils.dp2px(context, 30), getMeasuredHeight() * 0.2f, mPaint);
//        } else if (currentThumb == -1) {
//            currentThumb = 0;
//            canvas.drawText(50 * currentLowIndex + "", DensityUtils.dp2px(context, 15), getMeasuredHeight() * 0.2f, mPaint);
//            canvas.drawText(50 * currentHighIndex + "", getMeasuredWidth() - DensityUtils.dp2px(context, 45), getMeasuredHeight() * 0.2f, mPaint);
//        }

        canvas.drawText(currentPriceLeft + "", DensityUtils.dp2px(context, 15), getMeasuredHeight() * 0.2f, mPaint);
        canvas.drawText(currentPriceRight == 1000 ? currentPriceRight + "+" : currentPriceRight + "", getMeasuredWidth() - DensityUtils.dp2px(context, 45), getMeasuredHeight() * 0.2f, mPaint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!thumbs.isEmpty()) {

            //boolean testFocus = this.requestFocus();
            //Log.d(TAG,"Focus: "+testFocus);
            float coordinate = (orientation == VERTICAL) ? event.getY() : event.getX();
            if (!isCanConnet(event.getX(), event.getY())) {
                return false;
            }
            // Find thumb closest to event coordinate on screen touch
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    currentThumb = getClosestThumb(coordinate);
                    Log.d(TAG, "Closest " + currentThumb);
                    lowLimit = getLowerThumbRangeLimit(currentThumb);
                    highLimit = getHigherThumbRangeLimit(currentThumb);
                    startCo = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (coordinate < lowLimit) {
                        coordinate = lowLimit;
                    }
                    if (coordinate > highLimit) {
                        coordinate = highLimit;
                    }

                    if (coordinate < lowLimit) {
                        if (lowLimit == highLimit && currentThumb >= thumbs.size() - 1) {
                            currentThumb = getUnstuckFrom(currentThumb);
                            setThumbPos(currentThumb, coordinate);
                            lowLimit = getLowerThumbRangeLimit(currentThumb);
                            highLimit = getHigherThumbRangeLimit(currentThumb);
                        } else
                            setThumbPos(currentThumb, lowLimit);
                        //Log.d(TAG,"Setting low "+low);
                    } else if (coordinate > highLimit) {
                        setThumbPos(currentThumb, highLimit);
                        //Log.d(TAG,"Setting high "+high);
                    } else {
//                coordinate = asStep(coordinate);
                        setThumbPos(currentThumb, coordinate);
                        //Log.d(TAG,"Setting coordinate "+coordinate);
                    }
                    float n = pixelToStep((thumbs.get(currentThumb).pos));
//                    asStep(thumbs.get(currentThumb).pos);
                    if (currentThumb == 0) {
                        currentPriceLeft = (currentIndex * 50);
                    } else {
                        currentPriceRight = (currentIndex * 50);
                    }
                    /*
                    放在这里，为为了排除手指移动到外界失效的情况
                     */
                    if (listeners != null && listeners.size() > 0) {
                        for (RangeSeekBarListener listener : listeners) {
//                            listener.onSeek(currentThumb, currentIndex);
                            listener.onSeek(currentThumb, currentThumb == 0 ? currentPriceLeft / 50 : currentPriceRight / 50);
                        }
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    /**
                     * 如果抬起手指，根据坐标判断所在最近的节点step，并设置位置
                     */
                    if (coordinate < lowLimit) {
                        coordinate = lowLimit;
                    }
                    if (coordinate > highLimit) {
                        coordinate = highLimit;
                    }
                    int targetCo = (int) asStep(coordinate);//将坐标转换为节点坐标
                    if (currentThumb == 0) {
                        currentLowIndex = currentIndex;
                    } else {
                        currentHighIndex = currentIndex;
                    }
                    Log.d("TAG", "onTouchEvent: " + currentIndex);
                    setThumbPos(currentThumb, targetCo);
                    float n1 = pixelToStep((thumbs.get(currentThumb).pos));
                    if (currentThumb == 0) {
                        currentPriceLeft = (int) (n1 * 50);
                    } else {
                        currentPriceRight = (int) (n1 * 50);
                    }
                    if (listeners != null && listeners.size() > 0) {
                        for (RangeSeekBarListener listener : listeners) {
//                            listener.onSeek(currentThumb, currentIndex);
                            listener.onSeek(currentThumb, (int) n1);
                        }
                    }
                    break;

            }

            if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                //
            }

            // Update thumb position
            // Make sure we stay in our tracks's bounds or limited by other thumbs


            // Fire listener callbacks 在这里会不停地被调用，所以暂时注掉，放到手指抬起的时候调用
//            if (listeners != null && listeners.size() > 0) {
//                for (RangeSeekBarListener l : listeners) {
//                    l.onSeek(currentThumb, getThumbValue(currentThumb));
//                }
//            }

            // Tell the view we want a complete redraw
            //invalidate();

            // Tell the system we've handled this event
            return true;
        }
        return false;
    }

    private boolean isCanConnet(float x, float y) {
        for (int i = 0; i < thumbs.size(); i++) {
            if (Math.pow(thumbs.get(i).pos - x, 2) + Math.pow(getMeasuredHeight() * VERTICAL_POSITION - y, 2) < 8000) {
                return true;
            }
        }
        return false;
    }

    private int getUnstuckFrom(int index) {
        int unstuck = 0;
        float lastVal = thumbs.get(index).val;
        for (int i = index - 1; i >= 0; i--) {
            Thumb th = thumbs.get(i);
            if (th.val != lastVal)
                return i + 1;
        }
        return unstuck;
    }

    /**
     * 根据坐标信息，转换为最近节点的坐标，用于抬起手指的thumb定位
     *
     * @param pixelValue
     * @return
     */
    private float asStep(float pixelValue) {
        return stepScaleToPixel(pixelToStep(pixelValue));
    }

    private float pixelToScale(float pixelValue) {
        float pixelRange = (pixelRangeMax - pixelRangeMin);
        float scaleRange = (scaleRangeMax - scaleRangeMin);
        float scaleValue = (((pixelValue - pixelRangeMin) * scaleRange) / pixelRange) + scaleRangeMin;
        return scaleValue;
    }

    private float scaleToPixel(float scaleValue) {
        float pixelRange = (pixelRangeMax - pixelRangeMin);
        float scaleRange = (scaleRangeMax - scaleRangeMin);
        float pixelValue = (((scaleValue - scaleRangeMin) * pixelRange) / scaleRange) + pixelRangeMin;
        return pixelValue;
    }

    private float pixelToStep(float pixelValue) {
        float stepScaleMin = 0;
        float stepScaleMax = (float) Math.floor((scaleRangeMax - scaleRangeMin) / scaleStep);
        float pixelRange = (pixelRangeMax - pixelRangeMin);
        float stepScaleRange = (stepScaleMax - stepScaleMin);
        float stepScaleValue = (((pixelValue - pixelRangeMin) * stepScaleRange) / pixelRange) + stepScaleMin;
        //Log.d(TAG,"scaleVal: "+scaleValue+" smin: "+scaleMin+" smax: "+scaleMax);
        currentIndex = Math.round(stepScaleValue);
        return currentIndex;
    }

    private float stepScaleToPixel(float stepScaleValue) {
        float stepScaleMin = 0;
        float stepScaleMax = (float) Math.floor((scaleRangeMax - scaleRangeMin) / scaleStep);
        float pixelRange = (pixelRangeMax - pixelRangeMin);
        float stepScaleRange = (stepScaleMax - stepScaleMin);
        float pixelValue = (((stepScaleValue - stepScaleMin) * pixelRange) / stepScaleRange) + pixelRangeMin;
        //Log.d(TAG,"pixelVal: "+pixelValue+" smin: "+scaleMin+" smax: "+scaleMax);
        return pixelValue;
    }

    private void calculateThumbValue(int index) {
        if (index < thumbs.size() && !thumbs.isEmpty()) {
            Thumb th = thumbs.get(index);
            th.val = pixelToScale(th.pos);
        }
    }

    private void calculateThumbPos(int index) {
        if (index < thumbs.size() && !thumbs.isEmpty()) {
            Thumb th = thumbs.get(index);
            th.pos = scaleToPixel(th.val);
        }
    }

    private float getLowerThumbRangeLimit(int index) {
        float limit = pixelRangeMin;
        if (limitThumbRange && index < thumbs.size() && !thumbs.isEmpty()) {
            Thumb th = thumbs.get(index);
            for (int i = 0; i < thumbs.size(); i++) {
                if (i < index) {
                    Thumb tht = thumbs.get(i);
                    if (tht.pos < th.pos && tht.pos >= limit) {
//                        limit = tht.pos;
                        /**
                         * 保证两者不重合
                         */
                        limit = tht.pos + (pixelRangeMax - pixelRangeMin) / ((scaleRangeMax - scaleRangeMin) / scaleStep);
                        //Log.d(TAG,"New low limit: "+limit+" i:"+i+" index: "+index);
                    }
                }
            }
        }
        return limit;
    }

    private float getHigherThumbRangeLimit(int index) {
        float limit = pixelRangeMax;
        if (limitThumbRange && index < thumbs.size() && !thumbs.isEmpty()) {
            Thumb th = thumbs.get(index);
            for (int i = 0; i < thumbs.size(); i++) {
                if (i > index) {
                    Thumb tht = thumbs.get(i);
                    if (tht.pos >= th.pos && tht.pos <= limit) {
                        limit = tht.pos - (pixelRangeMax - pixelRangeMin) / ((scaleRangeMax - scaleRangeMin) / scaleStep);
                        ;
                        //Log.d(TAG,"New high limit: "+limit+" i:"+i+" index: "+index);
                    }
                }
            }
        }
        return limit;
    }

    public void distributeThumbsEvenly() {
        if (!thumbs.isEmpty()) {
            for (int i = 0; i < thumbs.size(); i++) {
                if (i == 0) {
                    setThumbPos(i, asStep(getPaddingLeft() + (pixelRangeMax - pixelRangeMin) /
                            ((scaleRangeMax - scaleRangeMin) / scaleStep) * (currentLowIndex)));
                } else {
                    setThumbPos(i, asStep((pixelRangeMax - pixelRangeMin) /
                            ((scaleRangeMax - scaleRangeMin) / scaleStep) * (currentHighIndex)));
                }
            }
        }
//        if (!thumbs.isEmpty()) {
//            int noThumbs = thumbs.size();
//            float even = pixelRangeMax / noThumbs;
//            float lastPos = even / 2;
//            for (int i = 0; i < thumbs.size(); i++) {
//                setThumbPos(i, asStep(lastPos));
//                //Log.d(TAG,"lp: "+lastPos);
//                lastPos += even;
//            }
//        }
    }

    /**
     * 得到指定Thumb的值
     *
     * @param index
     * @return
     */
    public float getThumbValue(int index) {
        return thumbs.get(index).val;
    }

    public void setThumbValue(int index, float value) {
        thumbs.get(index).val = value;
        calculateThumbPos(index);
        // Tell the view we want a complete redraw
        invalidate();
    }

    private void setThumbPos(int index, float pos) {
        thumbs.get(index).pos = pos;
        calculateThumbValue(index);

        // Tell the view we want a complete redraw
        invalidate();
    }

    private void smootSetThumbPos(int index, float pos) {
        int position = (int) pos;
        int currentP = (int) thumbs.get(index).pos;
        for (int i = 0; i < pos - currentP; i++) {
            thumbs.get(index).pos += 1;
            invalidate();
        }
    }

    private int getClosestThumb(float coordinate) {
        int closest = 0;
        if (!thumbs.isEmpty()) {
            float shortestDistance = pixelRangeMax + thumbHalf + ((orientation == VERTICAL) ?
                    (getPaddingTop() + getPaddingBottom()) : (getPaddingLeft() + getPaddingRight()));
            // Oldschool for-loop to have access to index
            for (int i = 0; i < thumbs.size(); i++) {
                // Find thumb closest to x coordinate
                float tcoordinate = thumbs.get(i).pos;
                float distance = Math.abs(coordinate - tcoordinate);
                if (distance <= shortestDistance) {
                    shortestDistance = distance;
                    closest = i;
                    //Log.d(TAG,"shDist: "+shortestDistance+" thumb i: "+closest);
                }
            }
        }
        return closest;
    }

    private void drawGutter(Canvas canvas) {
        if (track != null) {
            //Log.d(TAG,"gutterbg: "+gutterBackground.toString());
            Rect area1 = new Rect();
            area1.left = 0 + getPaddingLeft();
//            area1.top = 0 + getPaddingTop();
            /**
             * 在这里设置轨道的区间，实现对轨道的大小控制
             */
            area1.top = (int) (getMeasuredHeight() * VERTICAL_POSITION - trackHeight / 4 + getPaddingTop());
            area1.right = getMeasuredWidth() - getPaddingRight();
            area1.bottom = (int) (getMeasuredHeight() * VERTICAL_POSITION + trackHeight / 4 - getPaddingBottom());
            track.setBounds(area1);
            track.draw(canvas);
        }
    }
    
    /*
    RectF area = new RectF();
	area.left = 0 + getPaddingLeft() + minPos;
    area.top = 0 + getPaddingTop();
    area.right = getMeasuredWidth() - getPaddingRight() + maxPos;
    area.bottom = getMeasuredHeight() - getPaddingBottom();
    
	Paint p = new Paint();
	p.setAntiAlias(true);
    p.setColor(gutterColor);
    canvas.drawRoundRect(area, 7.5f, 7.5f, p);
    */

    private void drawRange(Canvas canvas) {
        if (!thumbs.isEmpty()) {
            Thumb thLow = thumbs.get(getClosestThumb(0));
            Thumb thHigh = thumbs.get(getClosestThumb(pixelRangeMax));

            // If we only have 1 thumb - choose to draw from 0 in scale
            if (thumbs.size() == 1)
                thLow = new Thumb();
            //Log.d(TAG,"l: "+thLow.pos+" h: "+thHigh.pos);

            if (range != null) {
                Rect area1 = new Rect();

                if (orientation == VERTICAL) {
                    area1.left = 0 + getPaddingLeft();
                    area1.top = (int) thLow.pos;
                    area1.right = getMeasuredWidth() - getPaddingRight();
                    area1.bottom = (int) thHigh.pos;
                } else {
                    area1.left = (int) thLow.pos;
//		            area1.top = 0 + getPaddingTop();

                    area1.top = (int) (getMeasuredHeight() * VERTICAL_POSITION - trackHeight / 2 + getPaddingTop());
                    area1.bottom = (int) (getMeasuredHeight() * VERTICAL_POSITION + trackHeight / 2 - getPaddingBottom());
                    area1.right = (int) thHigh.pos;
//		            area1.bottom = getMeasuredHeight() - getPaddingBottom();
                }
                range.setBounds(area1);
                range.draw(canvas);
            }
        }
    }

    private void drawThumbs(Canvas canvas) {
        if (!thumbs.isEmpty()) {
            //Paint p = new Paint();
            for (Thumb th : thumbs) {
                Rect area1 = new Rect();
                //Log.d(TAG,""+th.pos);
                if (orientation == VERTICAL) {
                    area1.left = getPaddingLeft();
                    area1.top = (int) ((th.pos - thumbHalf) + getPaddingTop());
                    area1.right = getMeasuredWidth() - getPaddingRight();
                    area1.bottom = (int) ((th.pos + thumbHalf) - getPaddingBottom());
                    //Log.d(TAG,"th: "+th.pos);
                } else {
//                    area1.left = (int) ((th.pos - thumbHalf) + getPaddingLeft());
                    area1.left = (int) ((th.pos - thumbHalf));
                    area1.top = (int) (getMeasuredHeight() * VERTICAL_POSITION - thumbHeight / 2);
//                    area1.right = (int) ((th.pos + thumbHalf) - getPaddingRight());
                    area1.right = (int) ((th.pos + thumbHalf));
                    area1.bottom = (int) (getMeasuredHeight() * VERTICAL_POSITION + thumbHeight / 2);
                    //Log.d(TAG,"th: "+area1.toString());
                }
                thumb.setBounds(area1);
                thumb.draw(canvas);
            }
        }
    }


    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            //Log.d(TAG,"measureWidth() EXACTLY");
            result = specSize;
        } else {
            // Measure
            //Log.d(TAG,"measureWidth() not EXACTLY");
            result = specSize + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                //Log.d(TAG,"measureWidth() AT_MOST");
                result = Math.min(result, specSize);
                // Add our thumbWidth to the equation if we're vertical
                if (orientation == VERTICAL) {
                    int h = (int) (thumbWidth + getPaddingLeft() + getPaddingRight());
                    result = Math.min(result, h);
                }
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            //Log.d(TAG,"measureHeight() EXACTLY");
            result = specSize;
        } else {
            // Measure
            //Log.d(TAG,"measureHeight() not EXACTLY");
            result = specSize + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                //Log.d(TAG,"measureHeight() AT_MOST");
                result = Math.min(result, specSize);
                // Add our thumbHeight to the equation if we're horizontal
                if (orientation == HORIZONTAL) {
                    int h = (int) (thumbHeight + getPaddingTop() + getPaddingBottom());
                    result = Math.min(result, h);
                }
            }
        }

        return result;
    }

    public void setListener(RangeSeekBarListener listener) {
        listeners.add(listener);
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public float getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(float thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public float getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(float thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public boolean isLimitThumbRange() {
        return limitThumbRange;
    }

    public void setLimitThumbRange(boolean limitThumbRange) {
        this.limitThumbRange = limitThumbRange;
    }

    public float getScaleRangeMin() {
        return scaleRangeMin;
    }

    public void setScaleRangeMin(float scaleRangeMin) {
        this.scaleRangeMin = scaleRangeMin;
    }

    public float getScaleRangeMax() {
        return scaleRangeMax;
    }

    public void setScaleRangeMax(float scaleRangeMax) {
        this.scaleRangeMax = scaleRangeMax;
    }

    public float getScaleStep() {
        return scaleStep;
    }

    public void setScaleStep(float scaleStep) {
        this.scaleStep = scaleStep;
    }

    public Drawable getTrack() {
        return track;
    }

    public void setTrack(Drawable track) {
        this.track = track;
    }

    public Drawable getRange() {
        return range;
    }

    public void setRange(Drawable range) {
        this.range = range;
    }

    public Drawable getThumb() {
        return thumb;
    }

    public void setThumb(Drawable thumb) {
        this.thumb = thumb;
    }

    public void initThumbs(int noThumbs) {
        for (int i = 0; i < noThumbs; i++) {
            Thumb th = new Thumb();
            thumbs.add(th);
        }
    }

    public interface RangeSeekBarListener {
        public void onCreate(int index, float value);

        public void onSeek(int index, int current);
    }

    public class Thumb {
        public float val;
        public float pos;

        public Thumb() {
            val = 0;
            pos = 0;
        }
    }

}
