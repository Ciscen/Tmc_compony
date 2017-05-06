package com.auvgo.tmc.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auvgo.tmc.R;

/**
 * Created by lc on 2016/11/9
 */

public class TitleView extends RelativeLayout {
    private int color;//背景色
    private ImageView back, more;
    private TextView title, subTitle;
    private OnClickListener listener;


    public TitleView(Context context) {
        super(context);
        initView(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        CharSequence title = ta.getText(R.styleable.TitleView_title);
        CharSequence subTitle = ta.getText(R.styleable.TitleView_subTitle);
        Drawable drawable = ta.getDrawable(R.styleable.TitleView_moreimage);
        color = ta.getColor(R.styleable.TitleView_bgColor, getResources().getColor(R.color.appTheme1));
        if (!TextUtils.isEmpty(title)) {
            this.title.setText(title);
        }
        if (!TextUtils.isEmpty(subTitle)) {
            toBe2Titles(subTitle);
        }
        if (drawable != null) {
            more.setVisibility(VISIBLE);
            more.setImageDrawable(drawable);
        }
        findViewById(R.id.title_rl).setBackgroundColor(color);
        if (color != getResources().getColor(R.color.appTheme1)) {
            this.title.setTextColor(getResources().getColor(R.color.color_333));
            back.setImageResource(R.mipmap.arrow_left);
        }
        ta.recycle();
    }

    private void initView(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_title, this, true);
        back = (ImageView) findViewById(R.id.title_back);
        more = (ImageView) findViewById(R.id.title_more);
        title = (TextView) findViewById(R.id.title_title);
        subTitle = (TextView) findViewById(R.id.title_subTitle);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    Activity a = (Activity) context;
                    a.finish();
                } else {
                    listener.onClick(v);
                }
            }
        });
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubTitle(Context context, String subTitle) {
        toBe2Titles(subTitle);
    }

    private void toBe2Titles(CharSequence subTitle) {
        this.subTitle.setVisibility(VISIBLE);
        this.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        this.subTitle.setText(subTitle);
    }

    /**
     * 设置预留的标题栏最右边的图片
     *
     * @param resource 图片的id，如果为0，则为默认或者xml已经指定的
     * @param listener 图片的点击事件
     */
    public void setMore(int resource, OnClickListener listener) {
        if (resource != 0) {
            more.setImageResource(resource);
        } else {
            more.setVisibility(GONE);
        }
        more.setOnClickListener(listener);
    }

    public void setTitleClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setBackground(Drawable drawable) {
        findViewById(R.id.title_rl).setBackgroundDrawable(drawable);
    }

    public void setBackground(int color) {
        findViewById(R.id.title_rl).setBackgroundColor(color);
    }
}
