package com.auvgo.tmc.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.ViewUtils;


/**
 * Created by LC on
 */
 //TODO 优化的时候需要把蒙板去了
public class MyCustomDialog extends PopupWindow {
    private boolean b = true;
    private View contentView;//内容的View
    private View coverView;//蒙板
    private String result;
    private OnLoadData loadData;
    private int yoff;
    private OndismissListener listener;

    public interface OnLoadData {
        void onLoad(View contentView, String result);
    }

    /**
     * 请求的数据，可以在回调中，利用该数据，对view的内容进行刷新
     *
     * @param contentView
     * @param coverView
     */
    public MyCustomDialog(View contentView, View coverView) {
        super(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.contentView = contentView;
        this.coverView = coverView;
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        // 设置弹出窗体显示时的动画，从底部向上弹出
        setAnimationStyle(R.style.mypopwindow_anim_style);
    }
    /**
     * 请求的数据，可以在回调中，利用该数据，对view的内容进行刷新
     *
     * @param contentView
     */
    public MyCustomDialog(View contentView) {
        super(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.contentView = contentView;
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        // 设置弹出窗体显示时的动画，从底部向上弹出
        setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    /**
     * @param b    是否自定义位置
     * @param contentView
     * @param dismissl
     */
    public MyCustomDialog(boolean b, View contentView, OndismissListener dismissl) {
        super(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.contentView = contentView;
        this.listener = dismissl;
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.mypopwindow_anim_style);
        this.b = b;
    }

    public void setYoff(int yoff) {
        this.yoff = yoff;
    }

    public void show() {
        if (loadData != null) {
            loadData.onLoad(contentView, result);
        }
        ObjectAnimator alpha = null;
        if (coverView != null) {
            alpha = ObjectAnimator.ofFloat(coverView, "alpha", 0, 0.5f);
        }
        if (b) {
            if (coverView != null) {
                alpha.setDuration(500);
            }
            ViewUtils.removeSelfFromParent(contentView);
            showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            if (coverView != null) {
                alpha.setDuration(200);
            }
            showAtLocation(contentView, Gravity.TOP | Gravity.LEFT, 0, yoff);
        }
        if (coverView != null) {
            //让蒙板半透明遮盖
            alpha.start();
        }
    }

    public interface OndismissListener {
        void onDismiss();
    }

    public void setDismissListener(OndismissListener listener) {
        this.listener = listener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (listener != null) {
            listener.onDismiss();
        }
        if (coverView != null) {
            coverView.setAlpha(0);//蒙板透明
        }
    }
}
