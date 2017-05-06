package com.auvgo.tmc;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.auvgo.tmc.adapter.AnimatorListenerAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.LoginActivity;
import com.auvgo.tmc.common.adapter.GuideFragmentAdapter;
import com.auvgo.tmc.common.fragment.GuideFragment;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.SPUtils;
import com.auvgo.tmc.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends BaseActivity implements GuideFragment.OnGuideFragmentButtonListener {

    private int terminal = -1;
    private ViewPager vp;
    private ImageView iv;
    boolean isFirstLogin;
    private static final int DEFAULT_DELAY = 850;
    private static final int TRANSLATION_KEEP_TIME = 3000;
    private static final int SCALE_KEEP_TIME = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

        isFirstLogin = (boolean) SPUtils.get(this, "isFirstLogin", true);
        initIv(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (isFirstLogin) {
                    SPUtils.put(context, "isFirstLogin", false);
                    initViews();
                    playScaleAnimation();
                    MyApplication.getApplication().getmMainThreadHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iv.clearAnimation();
                            iv.setVisibility(View.GONE);
                            initViews();
                        }
                    }, SCALE_KEEP_TIME);
                } else {
                    jump();
                }
            }
        });
    }

    private void initIv(AnimatorListenerAdapter adapter) {
        iv = (ImageView) findViewById(R.id.main_launch);
        iv.setVisibility(View.VISIBLE);
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, 0);
        ta.setDuration(TRANSLATION_KEEP_TIME);
        ta.setFillAfter(true);
        iv.startAnimation(ta);
        ta.setAnimationListener(adapter);
    }

    private void playScaleAnimation() {

        AnimationSet set = new AnimationSet(true);
        AlphaAnimation aa = new AlphaAnimation(1f, 0.2f);
        ScaleAnimation sa = new ScaleAnimation(1f, 2f, 1f, 2f,
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        set.setDuration(SCALE_KEEP_TIME);
        set.setFillAfter(true);
        set.setInterpolator(new AccelerateInterpolator());
        set.addAnimation(aa);
        set.addAnimation(sa);
        iv.clearAnimation();
        iv.startAnimation(set);
    }

    private void initViews() {
        vp = (ViewPager) findViewById(R.id.main_vp);
        vp.setVisibility(View.VISIBLE);
        vp.setAdapter(new GuideFragmentAdapter(getSupportFragmentManager(), getList()));
    }

    private List<Fragment> getList() {
        List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            GuideFragment fragment = new GuideFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        return list;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void setViews() {
    }

    private void jump() {
        if (isAuto()) {
            if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
                doLogin();
            } else {
                requestPermission(Constant.PERMISSION_CODE_IMEI, Manifest.permission.READ_PHONE_STATE);
            }
        } else {
            jumpActivity(Constant.ACTIVITY_LOGIN_FLAG);
        }
    }

    private void doLogin() {
        String userName = (String) SPUtils.get(this, Constant.KEY_USERNAME, "");
        String psw = (String) SPUtils.get(this, Constant.KEY_PASSWORD, "");
        final String cardNum = (String) SPUtils.get(this, Constant.KEY_CARDNUM, "");
        if (!TextUtils.isEmpty(userName) &&
                !TextUtils.isEmpty(psw) && !TextUtils.isEmpty(cardNum)) {
            AppUtils.doLogin(this, cardNum, userName, psw, false, new RetrofitUtil.OnResponse() {
                @Override
                public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                    if (status == CODE_SUCCESS) {
                        MyApplication.mUserInfoBean = (UserBean) o;
                        ToastUtils.showTextToast("登录成功");
                        setAlias();
                        jumpActivity(Constant.ACTIVITY_HOME_FLAG);
                    } else {
                        ToastUtils.showTextToast("登录失败");
                        jumpActivity(Constant.ACTIVITY_LOGIN_FLAG);
                    }
                    return true;
                }

                @Override
                public boolean onFailed(Throwable e) {
                    jumpActivity(Constant.ACTIVITY_LOGIN_FLAG);
                    return true;
                }
            });
        } else {
            jumpActivity(Constant.ACTIVITY_LOGIN_FLAG);
        }
    }

    private void setAlias() {
        JPushInterface.setAlias(this, AppUtils.getMD5(DeviceUtils.getIMEI(this)), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        LogFactory.d("setAlias------->", "success");
                        break;
                    case 6002:
                        LogFactory.d("setAlias------->", "failed ");
                        break;
                    default:
                        LogFactory.d("setAlias----errorCode--->", i + "");
                        break;
                }
            }
        });
    }


    private void jumpActivity(int flag) {
        final Intent intent = new Intent();
        switch (flag) {
            case Constant.ACTIVITY_LOGIN_FLAG:
                intent.setClass(this, LoginActivity.class);
                break;
            case Constant.ACTIVITY_HOME_FLAG:
                intent.setClass(this, HomeActivity.class);
                break;
        }
        if (!isFirstLogin) {
            playScaleAnimation();
        }
        MyApplication.getApplication().getmMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, isFirstLogin ? 0 : DEFAULT_DELAY);
    }

    private boolean isAuto() {
        return (boolean) SPUtils.get(this, Constant.KEY_AUTOLOGIN, false);
    }

    @Override
    public void onGetIMEIPermission() {
        doLogin();
    }

    @Override
    public void onFragmentInteraction() {
        jump();
    }
}
