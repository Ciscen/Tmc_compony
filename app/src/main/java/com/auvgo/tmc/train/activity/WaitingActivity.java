package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.personalcenter.activity.OrderListActivity;
import com.auvgo.tmc.personalcenter.activity.PersonalCenterActivity;
import com.auvgo.tmc.train.bean.CheckStateBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.TitleView;

import java.util.HashMap;
import java.util.Map;

public class WaitingActivity extends AppCompatActivity {

    private ImageView iv;
    private AnimationDrawable animationDrawable;
    private TextView tv;
    private TitleView title;
    private String text = "";
    private int statusFlag = 0;
    private String orderNo = "";
    public final int STATE_BOOK = 1;
    public final int STATE_ALTER = 2;
    public final int STATE_RETURN = 3;
    public final int STATE_CHUPIAO = 4;
    private long startTime;
    private Runnable runnable;
    private CheckStateBean mBean;
    private static final long KEEP_TIME = 1000 * 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        initData();
        initView();
        getData();
        startTime = System.currentTimeMillis();
    }

    private void getData() {
        AppUtils.getHandler().postDelayed(runnable, 2000);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            statusFlag = intent.getIntExtra("flag", 0);
            orderNo = intent.getStringExtra("orderNo");
        }
        switch (statusFlag) {
            case 0:
                ToastUtils.showTextToast("页面来源标识错误");
                break;
            case STATE_BOOK:
                text = "正在努力订座中";
                break;
            case STATE_ALTER:
                text = "正在努力改签中";
                break;
            case STATE_RETURN:
                text = "正在努力退票中";
                break;
            case STATE_CHUPIAO:
                text = "正在努力出票中";
                break;
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                getOrderStatus();
            }
        };
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.tv);
        title = (TitleView) findViewById(R.id.title);
        animationDrawable = (AnimationDrawable) iv.getDrawable();
        title.setTitle(text.substring(4, 7));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (animationDrawable.isRunning()) {
            LogFactory.d("isRunning");
        } else {
            LogFactory.d("isStoped");
            animationDrawable.start();
            MUtils.startTextAnim(tv, text);
        }
    }

    private void getOrderStatus() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        map.put("type", String.valueOf(statusFlag));
        RetrofitUtil.checkState(this, AppUtils.getJson(map), CheckStateBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                LogFactory.d(bean.getData());
                if (status == 200) {
                    mBean = (CheckStateBean) o;
                    if ((mBean.getStatus() == 0 || mBean.getStatus() == 7) && System.currentTimeMillis() - KEEP_TIME < startTime) {
                        getData();
                        return true;
                    } else if (mBean.getStatus() != 0) {//如果有结果返回
                        jumpToDetailPage();
                    } else {
                        //如果超时
                        showDialg(1);
                    }
                } else {
                    showDialg(2);//请求未成功1
                    return true;
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {
                showDialg(3);
                return true;
            }
        });

    }

    private void showDialg(final int i) {
        String str = getStrByCode(i);
        DialogUtil.showDialog(WaitingActivity.this, "提示", "确定", "", str, new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
                if (i == 1) {//请求超时
                    Intent intent = new Intent(WaitingActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Intent i2 = new Intent(WaitingActivity.this, PersonalCenterActivity.class);
                    Intent i3 = new Intent(WaitingActivity.this, OrderListActivity.class);
                    startActivity(i2);
                    startActivity(i3);
                } else if (i == 2 || i == 3) {
                    finish();
                }
            }

            @Override
            public void onRightClick() {

            }
        });
    }

    private String getStrByCode(int i) {
        if (i == 1) {
            return getString(R.string.order_committing);
        }
        if (i == 2) {
            return "服务器异常，请稍后再试";
        }
        if (i == 3) {
            return "连接错误，请检查网络";
        }
        switch (statusFlag) {
            case STATE_BOOK:
                return "订座失败！";
            case STATE_ALTER:
                return "改签失败！";
            case STATE_RETURN:
                return "退票失败！";
        }
        return "";
    }

    private void jumpToDetailPage() {
        Intent intent = new Intent();
        intent.putExtra("orderNo", orderNo);
        switch (statusFlag) {
            case STATE_BOOK:
                intent.setClass(this, TrainOrderDetailActivity.class);
                break;
            case STATE_ALTER:
                intent.setClass(this, AlterOrderDetailActivity.class);
                break;
            case STATE_RETURN:
                intent.setClass(this, TrainReturnDetailActivity.class);
                break;
            case STATE_CHUPIAO:
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(this, TrainOrderDetailActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        MUtils.stopTextAnim();
    }
}
