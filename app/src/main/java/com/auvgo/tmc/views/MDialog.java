package com.auvgo.tmc.views;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.DeviceUtils;

/**
 * Created by lc on 2016/11/17
 */

public class MDialog extends Dialog {

    public MDialog(Context context) {
        super(context);
    }

    public MDialog(Context context, int layout, int themeResId) {
        super(context, themeResId);
        setContentView(layout);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT; //设置宽度
        getWindow().setAttributes(lp);
    }
}
