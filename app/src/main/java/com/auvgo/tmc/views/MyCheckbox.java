package com.auvgo.tmc.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.R;

/**
 * Created by lc on 2016/11/16
 */

public class MyCheckbox extends LinearLayout implements View.OnClickListener {
    private boolean isChecked;
    private CheckBox checkBox;
    private TextView tv;
    private OnClickListener listener;
    public interface OnClickListener {
        void onClick(View v, int position);
    }

    public MyCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCheckbox);
        CharSequence text = ta.getText(R.styleable.MyCheckbox_text);
        tv.setText(text);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_checkbox, this, true);
        checkBox = (CheckBox) findViewById(R.id.view_checkbox_cb);
        tv = (TextView) findViewById(R.id.view_checkbox_tv);
        setOnClickListener(this);
    }

    public MyCheckbox(Context context) {
        super(context);
        initView(context);
    }

    public void setText(String text) {
        tv.setText(text);
    }
    public String getText() {
        return tv.getText().toString();
    }
    public void setOnclickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
        isChecked = checkBox.isChecked();
    }

    @Override
    public void onClick(View v) {
        setChecked(!isChecked());
        if (listener != null) {
            listener.onClick(v, 0);
        }
    }
}
