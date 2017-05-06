package com.auvgo.tmc.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.DensityUtils;

/**
 * Created by lc on 2016/11/25
 */

public class ItemView extends LinearLayout {
    private TextView title, tv;
    private EditText et;
    private boolean isInput;
    private View item_et;
    private ImageView del;
    private String contentStr = "";
    private String hintStr = "";
    private String titleStr = "";
    private String color = "#666666";
    private boolean isPassword;
    private View divider;//分割线

    public ItemView(Context context, boolean isInput) {
        super(context);
        this.isInput = isInput;
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        contentStr = typedArray.getString(R.styleable.ItemView_content);
        hintStr = typedArray.getString(R.styleable.ItemView_hint);
        titleStr = typedArray.getString(R.styleable.ItemView_itemTitle);
        color = typedArray.getString(R.styleable.ItemView_itemTitleColor);
        isInput = typedArray.getBoolean(R.styleable.ItemView_inputable, false);
        isPassword = typedArray.getBoolean(R.styleable.ItemView_isPassword, false);
        float leftpadding = typedArray.getDimension(R.styleable.ItemView_leftpadding, -1);
        boolean hasDivider = typedArray.getBoolean(R.styleable.ItemView_hasDivider, true);
        if (!hasDivider) {
            this.divider.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(color))
            title.setTextColor(Color.parseColor(color));
        title.setText(titleStr);
        if (leftpadding != -1)
            title.setPadding((int) leftpadding, 0, 0, 0);
        if (isInput) {
            if (isPassword) {
                et.setSingleLine(true);
                et.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            item_et.setVisibility(VISIBLE);
            et.setHint(hintStr);
            tv.setVisibility(GONE);
            et.setText(contentStr);
        } else {
            tv.setText(contentStr);
            tv.setHint(hintStr);
            item_et.setVisibility(GONE);
            tv.setVisibility(VISIBLE);
        }
        del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        if (TextUtils.isEmpty(contentStr)) {
            del.setVisibility(INVISIBLE);
        }
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    del.setVisibility(INVISIBLE);
                } else {
                    del.setVisibility(VISIBLE);
                }

            }
        });
        typedArray.recycle();
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_item_view, this);
        title = (TextView) findViewById(R.id.item_view_title);
        tv = (TextView) findViewById(R.id.item_view_tv);
        et = (EditText) findViewById(R.id.item_view_et);
        item_et = findViewById(R.id.item_view_edititem);
        del = (ImageView) findViewById(R.id.item_view_del);
        divider = findViewById(R.id.item_view_divider);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public String getContent() {
        if (isInput)
            return et.getText().toString();
        return tv.getText().toString();
    }

    public void setContent(String content) {
        if (isInput)
            et.setText(content);
        tv.setText(content);
    }

    public void setInput(boolean isInput) {
        this.isInput = isInput;
    }

    public void setHint(String hint) {
        if (isInput) {
            et.setHint(hint);
        } else {
            tv.setHint(hint);
        }
    }
}
