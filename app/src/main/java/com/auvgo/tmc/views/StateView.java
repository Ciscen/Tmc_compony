package com.auvgo.tmc.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.R;

/**
 * Created by lc on 2017/2/18
 */

public class StateView extends LinearLayout {

    private Drawable imgOn;
    private Drawable imgOff;
    private String textStr;
    private boolean isOn;

    private TextView text;
    private ImageView img;

    public StateView(Context context) {
        super(context);
        init(context);
    }


    public StateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StateView);
        imgOff = ta.getDrawable(R.styleable.StateView_imgOff);
        imgOn = ta.getDrawable(R.styleable.StateView_imgOn);
        textStr = ta.getString(R.styleable.StateView_viewName);
        isOn = ta.getBoolean(R.styleable.StateView_isOn, false);
        ta.recycle();
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_state_view, this);
        text = (TextView) findViewById(R.id.stateView_text);
        img = (ImageView) findViewById(R.id.stateView_icon);
        initState();
    }

    private void initState() {
        img.setImageDrawable(isOn ? imgOn : imgOff);
        text.setText(textStr);
        text.setTextColor(isOn ? getResources().getColor(R.color.color_666) : getResources().getColor(R.color.color_ccc));
    }

    public void setImgOn(Drawable imgOn) {
        this.imgOn = imgOn;
        initState();
    }

    public void setImgOff(Drawable imgOff) {
        this.imgOff = imgOff;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
        initState();
    }

    public String getTextStr() {
        return textStr;
    }

    public void setTextStr(String textStr) {
        this.textStr = textStr;
    }
}
