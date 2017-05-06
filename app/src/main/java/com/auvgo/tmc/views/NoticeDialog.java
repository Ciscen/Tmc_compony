package com.auvgo.tmc.views;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.utils.DeviceUtils;


/**
 * Created by lc on 2016/11/09
 */
public class NoticeDialog extends Dialog {
    private TextView weibei;
    private TextView price;
    private View item_wei, close;
    private Context context;
    private OnButtonClickListener listener;
    private String save, weiStr;
    private TextView tuijian;

    public interface OnButtonClickListener {
        void onLeftClick();

        void onRightClick();
    }

    /**
     * @param context
     * @param price    可以节省的钱
     * @param listener 监听
     */
    public NoticeDialog(Context context, String weiStr, String price, final OnButtonClickListener listener) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_notice);
        this.context = context;
        this.weiStr = weiStr;
        this.listener = listener;
        this.save = price;
        initViews();
    }

    /**
     * 酒店政策显示的
     *
     * @param context
     * @param contentView
     */
    public NoticeDialog(Context context, View contentView) {
        super(context, R.style.Dialog);
        setContentView(contentView);
        this.context = context;
    }

    private void initViews() {
        findViews();
        setText();
    }

    private void findViews() {
        item_wei = findViewById(R.id.dialog_notice_item_weibei);
        weibei = (TextView) findViewById(R.id.notice_dialog_weibei);
        close = findViewById(R.id.notice_dialog_close);
        price = (TextView) findViewById(R.id.dialog_notice_price);
        tuijian = (TextView) findViewById(R.id.dialog_notice_goto);
    }

    private void setText() {
        if (TextUtils.isEmpty(weiStr)) {
            item_wei.setVisibility(View.GONE);
        } else {
            weibei.setText(weiStr);
        }
        if (TextUtils.isEmpty(save)) {
            price.setVisibility(View.GONE);
            tuijian.setVisibility(View.GONE);
        } else {
            price.setText(String.format("您选择的航班还有最低价哦，我们为您推荐最划算的航班，您可以节省￥%s", save));
        }


        findViewById(R.id.dialog_notice_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoticeDialog.this.listener != null)
                    NoticeDialog.this.listener.onLeftClick();
                dismiss();
            }
        });
        tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoticeDialog.this.listener != null)
                    NoticeDialog.this.listener.onRightClick();
                dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public void show() {
        super.show();
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.width = DeviceUtils.deviceWidth() - 210; //设置宽度
//        getWindow().setAttributes(lp);
    }

}
