package com.auvgo.tmc.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 下拉刷新的ListView
 * Created by lc on 2016/7/18.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener {

    private String formater = "yyyy-MM-dd HH:mm:ss";

    private static final int STATE_PULL_REFRESH = 0;// 下拉刷新
    private static final int STATE_RELEASE_REFRESH = 1;// 松开刷新
    private static final int STATE_REFRESHING = 2;// 正在刷新

    private final boolean pauseOnScroll = false;//滑动时是否加载图片
    private final boolean pauseOnFling = true;//飞速滑动时是否加载图片
    private boolean pushEnable = true;//是否上拉加载更多
    private boolean pullEnable = true;//是否下拉加载更多
    private boolean isFirst = true;

    private View mHeaderView;
    private int startY = -1;// 滑动起点的y坐标
    private int mHeaderViewHeight;
    private int currentHeight;
    private int mCurrrentState = STATE_PULL_REFRESH;// 当前状态
    private TextView tvTitle;
    private TextView tvTime;
    private ImageView ivArrow;
    private ProgressBar pbProgress;

    //动画
    private RotateAnimation animUp;
    private RotateAnimation animDown;
    private AlphaAnimation animDisapp;
    private AnimationSet set;

    private long startTime;
    private String lastRefreshTime;


    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
        this.addHeaderView(mHeaderView);

        tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
        tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
        ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
        pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);

        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();

        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);// 隐藏头布局

        initArrowAnim();
        this.setOnScrollListener(this);
        lastRefreshTime = getRefreshTime();
        tvTime.setText("更新时间:" + TimeUtils.getTimestampString(TimeUtils.string2Date(lastRefreshTime, formater)));

    }

    /*
     * 初始化脚布局
     */
    private void initFooterView() {
        mFooterView = View.inflate(getContext(),
                R.layout.refresh_listview_footer, null);
        this.addFooterView(mFooterView);

        mFooterView.measure(0, 0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);// 隐藏
        this.setOnScrollListener(this);
    }

    public void setFooterViewVisibility(int visibility) {
        mFooterView.setVisibility(visibility);
    }

    public void setHeaderViewVisibility(int visibility) {
        if (visibility == VISIBLE) {//通过设置padding的形式，隐藏headerView
            mHeaderView.setPadding(0, 0, 0, 0);
        } else {
            mHeaderView.clearAnimation();
            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (pullEnable) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startY = (int) ev.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (startY == -1) {// 确保startY有效
                        startY = (int) ev.getRawY();
                    }

                    if (mCurrrentState == STATE_REFRESHING) {// 正在刷新时不做处理
                        break;
                    }

                    int endY = (int) ev.getRawY();
                    int dy = endY - startY;// 移动便宜量

                    if (dy > 0 && getFirstVisiblePosition() == 0) {// 只有下拉并且当前是第一个item,才允许下拉
                        tvTime.setText("更新时间:" + TimeUtils.getTimestampString(TimeUtils.string2Date(lastRefreshTime, formater)));
                        int padding = dy / 3 - mHeaderViewHeight;// 计算padding
                        if (padding + mHeaderViewHeight > 0)
                            mHeaderView.setPadding(0, padding, 0, 0);// 设置当前padding
                        if (padding > 0 && mCurrrentState != STATE_RELEASE_REFRESH) {// 状态改为松开刷新
                            mCurrrentState = STATE_RELEASE_REFRESH;
                            refreshState();
                        } else if (padding <= 0 && mCurrrentState != STATE_PULL_REFRESH) {// 改为下拉刷新状态
                            mCurrrentState = STATE_PULL_REFRESH;
                            refreshState();
                        }
                        return true;
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    startY = -1;// 重置
                    if (mCurrrentState == STATE_RELEASE_REFRESH) {
                        mCurrrentState = STATE_REFRESHING;// 正在刷新
                        mHeaderView.setPadding(0, 0, 0, 0);// 显示
                        refreshState();
                    } else if (mCurrrentState == STATE_PULL_REFRESH) {
                        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);// 隐藏
                    }
                    if (mCurrrentState == STATE_REFRESHING) {
                        return true;
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 刷新下拉控件的布局
     */
    private void refreshState() {
        switch (mCurrrentState) {
            case STATE_PULL_REFRESH:
                tvTitle.setText("下拉刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.startAnimation(animDown);
                break;
            case STATE_RELEASE_REFRESH:
                tvTitle.setText("松开刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.startAnimation(animUp);
                break;
            case STATE_REFRESHING:
                tvTitle.setText("正在刷新...");
                ivArrow.clearAnimation();// 必须先清除动画,才能隐藏
                ivArrow.setVisibility(View.INVISIBLE);
                pbProgress.setVisibility(View.VISIBLE);
                startTime = System.currentTimeMillis();
                if (mListener != null) {
                    mListener.onRefresh();
                }
                break;

            default:
                break;
        }
    }

    /**
     * 初始化箭头动画
     */
    private void initArrowAnim() {
        // 箭头向上动画
        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);

        // 箭头向下动画
        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);

        animDisapp = new AlphaAnimation(1, 0);
        animDisapp.setDuration(1000);
        animDisapp.setFillAfter(false);
    }

    OnRefreshListener mListener;
    private View mFooterView;
    private int mFooterViewHeight;

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();// 加载下一页数据
    }

    /*
     * 收起下拉刷新上拉加载的控件
     */
    public void onRefreshComplete(final boolean success) {
        if (isFirst) {
            isFirst = false;
            return;
        }
        if (isLoadingMore) {// 正在加载更多...
            mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);// 隐藏脚布局
            isLoadingMore = false;
        } else {
            //应交互要求，下拉刷新时间不到1秒增加1秒显示时间
            long endTime = System.currentTimeMillis();
            tvTitle.setText("刷新完成");
            if (success) {
                lastRefreshTime = getRefreshTime();
                tvTime.setText("更新时间:" + TimeUtils.getTimestampString(TimeUtils.string2Date(lastRefreshTime, formater)));
            }
            if (endTime - startTime < 1000) {
                AppUtils.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeRefreshProgress(success);
                    }
                }, 0);
            } else {
                closeRefreshProgress(success);
            }

        }
    }

    /**
     * 关闭下刷新头
     */
    private void closeRefreshProgress(boolean success) {
        mCurrrentState = STATE_PULL_REFRESH;
        currentHeight = 0;
        /*
        防止没有下拉，加载完成后自动下滑
         */
        if (mHeaderView.getPaddingTop() + mHeaderViewHeight > 0) {
            closeSmoothlyByTimer();//用timer的方式关闭下拉刷新的头布局
//        closeSmoothlyByAni();//用animation的方式关闭
        }
    }

    private void closeSmoothlyByTimer() {
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentHeight = currentHeight + 1;
                        if (currentHeight >= mHeaderViewHeight) {
                            timer.cancel();
                            tvTitle.setText("下拉刷新");
                            ivArrow.setVisibility(View.VISIBLE);
                            pbProgress.setVisibility(View.INVISIBLE);
                            mHeaderView.setPadding(0, -currentHeight, 0, 0);
                        }
                        mHeaderView.setPadding(0, -currentHeight, 0, 0);
                    }
                });
            }
        };
        timer.schedule(task, 0, 2);
    }

    private void closeSmoothlyByAni() {
        animDisapp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setPadding(-mHeaderViewHeight);// 隐藏
            }

            private void setPadding(int i) {
                final Timer timer = new Timer();
                final TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentHeight = currentHeight + 1;
                                if (currentHeight >= mHeaderViewHeight) {
                                    timer.cancel();
                                }
                                mHeaderView.setPadding(0, -currentHeight, 0, 0);
                            }
                        });
                    }
                };
                timer.schedule(task, 0, 2);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvTitle.setText("下拉刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                mHeaderView.setPadding(0, -currentHeight, 0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mHeaderView.startAnimation(animDisapp);
    }

    /**
     * 获取当前时间
     */
    public String getRefreshTime() {
        SimpleDateFormat format = new SimpleDateFormat(formater);
        return format.format(new Date());
    }

    private boolean isLoadingMore;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_IDLE:
                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                if (pauseOnScroll) {
                }
                break;
            case OnScrollListener.SCROLL_STATE_FLING:
                if (pauseOnFling) {
                }
                break;
        }
        if (pushEnable) {
            if (scrollState == SCROLL_STATE_IDLE) {

                if (getLastVisiblePosition() == getCount() - 1 && !isLoadingMore) {// 滑动到最后
                    mFooterView.setPadding(0, 0, 0, 0);// 显示
                    setSelection(getCount() - 1);// 改变listview显示位置
                    isLoadingMore = true;
                    if (mListener != null) {
                        mListener.onLoadMore();
                    }
                }
            }
            if (mScrollChangeListener != null) {
                mScrollChangeListener.onRefreshScrollStateChanged(view, scrollState);
            }
        }
    }

    OnRefreshScrollStateChangedListener mScrollChangeListener;

    public void setOnRefreshScrollChangeListener(OnRefreshScrollStateChangedListener scrollChangeListener) {
        mScrollChangeListener = scrollChangeListener;
    }

    public interface OnRefreshScrollStateChangedListener {
        void onRefreshScrollStateChanged(AbsListView view, int scrollState);
    }

    OnRefreshScrollListener mRefreshScrollListener;

    public void setOnRefreshScrollListener(OnRefreshScrollListener scrollListener) {
        mRefreshScrollListener = scrollListener;
    }

    public interface OnRefreshScrollListener {
        void onScroll(AbsListView view, int firstVisibleItem,
                      int visibleItemCount, int totalItemCount);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (mRefreshScrollListener != null) {
            mRefreshScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

    }

    OnItemClickListener mItemClickListener;

    @Override
    public void setOnItemClickListener(
            OnItemClickListener listener) {
        super.setOnItemClickListener(this);

        mItemClickListener = listener;
    }

    public static final int MIN_CLICK_DELAY_TIME = 0;
    private long lastClickTime = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (mItemClickListener != null && currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            mItemClickListener.onItemClick(parent, view, position
                    - getHeaderViewsCount(), id);
            lastClickTime = currentTime;
        }
    }

    public void setPushEnable(boolean enable) {
        this.pushEnable = enable;
    }

    public void setPullEnable(boolean enable) {
        this.pullEnable = enable;
    }
}
