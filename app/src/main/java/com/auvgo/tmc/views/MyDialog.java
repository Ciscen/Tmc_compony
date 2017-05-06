package com.auvgo.tmc.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.DeviceUtils;


/**
 * Created by lc on 2016/11/09
 */
public class MyDialog extends Dialog {
    private TextView title_tv;
    private View divider;
    private TextView tv1;
    private TextView tv2;
    private TextView content_tv;
    private Context context;
    private OnButtonClickListener listener;
    private String title, left, right, content;

    public interface OnButtonClickListener {
        void onLeftClick();

        void onRightClick();
    }

    /**
     * @param context
     * @param left     左键
     * @param right    右键
     * @param content  内容
     * @param listener 监听
     */
    public MyDialog(Context context, String left, String right, String content, final OnButtonClickListener listener) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_mine);
        this.context = context;
        this.listener = listener;
        this.left = left;
        this.right = right;
        this.content = content;

        initViews();

    }

    private void initViews() {
        findViews();
        setText();
    }

    private void findViews() {
        divider = findViewById(R.id.dialog_divider);
        tv1 = (TextView) findViewById(R.id.MyDialog_click_one);
        tv2 = (TextView) findViewById(R.id.MyDialog_click_two);
        content_tv = (TextView) findViewById(R.id.MyDialog_massage);
        title_tv = (TextView) findViewById(R.id.MyDialog_title);
        content_tv.setText(content);
    }

    private void setText() {
        if (!TextUtils.isEmpty(left)) {
            tv1.setText(left);
            tv1.setVisibility(View.VISIBLE);
        } else {
            tv1.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(right)) {
            tv2.setText(right);
            tv2.setVisibility(View.VISIBLE);
        } else {
            tv2.setText("");
            tv2.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(right) || TextUtils.isEmpty(left)) {
            divider.setVisibility(View.GONE);
        } else {
            divider.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(title)) {
            title_tv.setText(title);
            title_tv.setVisibility(View.VISIBLE);
        }
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyDialog.this.listener != null)
                    MyDialog.this.listener.onLeftClick();
                dismiss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyDialog.this.listener != null)
                    MyDialog.this.listener.onRightClick();
                dismiss();
            }
        });
    }

    public MyDialog(Context context, String title, String left, String right, String content, OnButtonClickListener listener) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_mine);
        this.context = context;
        this.listener = listener;
        this.left = left;
        this.right = right;
        this.content = content;
        this.title = title;
        initViews();
    }


    @Override
    public void show() {
        super.show();
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.width = DeviceUtils.deviceWidth() - 210; //设置宽度
//        getWindow().setAttributes(lp);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Context getmContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    //改动后生效
    public void invalicade() {
        initViews();
    }
}
