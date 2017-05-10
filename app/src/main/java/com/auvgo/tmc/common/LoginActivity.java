package com.auvgo.tmc.common;

import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.p.PLogin;
import com.auvgo.tmc.train.interfaces.ViewManager_login;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.SPUtils;
import com.auvgo.tmc.utils.Url;
import com.auvgo.tmc.views.MyPickerView;
import com.auvgo.tmc.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ViewManager_login,
        CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {


    private boolean isTest = false;
    private int[] a = {2, 12};
    private boolean[] b = {true, false, false, false, false};
    /////////控件/////////
    private EditText et_cardNum, et_userName, et_psw;
    private RadioGroup rg;
    private RadioButton[] rbs;
    private EditText ip_addr;
    private TextView tv_login;
    private CheckBox saveId, savePsw, autoLogin;
    ////////变量/////////
    private PLogin mPlogin;//处理当前activity的逻辑
    private List<TestCount> list;
    private int mPosition = -1;
    private int onLineOrNot = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        if (isTest)
            list = getTestCountList();
        mPlogin = new PLogin(this, this);
    }

    private List<TestCount> getTestCountList() {
        List<TestCount> list = new ArrayList<>();
        list.add(new TestCount("王悦线上", Url.ONLINE, "wangyue8", "123456"));
        list.add(new TestCount("王悦线下", Url.OFFLINE, "wangyue", "123456"));
        list.add(new TestCount("翰宇线上", Url.ONLINE, "hanyu", "123456"));
        list.add(new TestCount("翰宇线下", Url.OFFLINE, "lihanyu", "123456"));
        list.add(new TestCount("zzzzzzz", Url.ONLINE, "wangyue8", "123456"));
        return list;
    }

    @Override
    protected void findViews() {
        rg = (RadioGroup) findViewById(R.id.login_radiogroup);
        et_cardNum = (EditText) findViewById(R.id.login_cardNum);
        et_userName = (EditText) findViewById(R.id.login_userName);
        et_psw = (EditText) findViewById(R.id.login_psw);
        tv_login = (TextView) findViewById(R.id.login_login);
        saveId = (CheckBox) findViewById(R.id.checkBox1);
        savePsw = (CheckBox) findViewById(R.id.checkBox2);
        autoLogin = (CheckBox) findViewById(R.id.checkBox3);
        ip_addr = (EditText) findViewById(R.id.ip_addr);
        rbs = new RadioButton[2];
        for (int i = 0; i < rg.getChildCount(); i++) {
            rbs[i] = (RadioButton) rg.getChildAt(i);
        }
    }

    @Override
    protected void initView() {
        String cardNum = (String) SPUtils.get(this, Constant.KEY_CARDNUM, "");
        String userName = (String) SPUtils.get(this, Constant.KEY_USERNAME, "");
        String password = (String) SPUtils.get(this, Constant.KEY_PASSWORD, "");
        boolean isAuto = (boolean) SPUtils.get(this, Constant.KEY_AUTOLOGIN, false);
        if (isTest) {
            setTestViews();
        }

        rbs[0].setChecked(true);
        et_cardNum.setText(cardNum);
        et_userName.setText(userName);
        et_psw.setText(password);
        if (!TextUtils.isEmpty(cardNum) && !TextUtils.isEmpty(userName)) {
            saveId.setChecked(true);
        }
        if (!TextUtils.isEmpty(password)) {
            savePsw.setChecked(true);
        }
        if (isAuto) {
            autoLogin.setChecked(true);
        }

        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.alpha = 1.0f; // 0.0-1.0

        getWindow().setAttributes(lp);
    }

    private void setTestViews() {
        findViewById(R.id.debug_tools).setVisibility(View.VISIBLE);
        String text = Url.getUrl(Url.OFFLINE).split(":")[1];
        ip_addr.setText(text.substring(text.length() - 3));
    }

    @Override
    protected void initListener() {
        tv_login.setOnClickListener(this);
        //小写自动转换为大写
        et_cardNum.setTransformationMethod(new AllCapTransformationMethod());
        saveId.setOnCheckedChangeListener(this);
        savePsw.setOnCheckedChangeListener(this);
        autoLogin.setOnCheckedChangeListener(this);
        rg.setOnCheckedChangeListener(this);
        if (isTest)
            findViewById(R.id.imageView).setOnClickListener(this);
    }

    @Override
    protected void setViews() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                if (!isTest) {
                    RetrofitUtil.setUrl(Url.ONLINE);
                } else {
                    setDebugUrl();
                }

                mPlogin.doLogin();
                String packageName = WXPayEntryActivity.class.getPackage().getName();
                System.out.println("packageName---" + packageName);
//                MUtils.jumpToPage(this, PayListActivity.class, -1);
                break;
            case R.id.imageView:
                DialogUtil.showPickerPopWithSureHeight(context, "选择账号", mPosition, list,
                        new MyPickerView.OnPickerViewSure() {
                            @Override
                            public void onSingleSureClick(int p) {
                                mPosition = p;
                                TestCount testCount = list.get(p);
                                et_userName.setText(testCount.getCountNum());
                                et_psw.setText(testCount.getCountPass());
                                RetrofitUtil.setUrl(testCount.getOnLineOrNot());
                                onLineOrNot = testCount.getOnLineOrNot();
                                if (onLineOrNot == 0) {
                                    ip_addr.setVisibility(View.VISIBLE);
                                } else {
                                    ip_addr.setText("");
                                    ip_addr.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onMultiSureClick(List<Integer> s) {
                            }
                        });
                break;
        }
    }

    private void setDebugUrl() {
        String s = ip_addr.getText().toString();
        if (!s.isEmpty()) {
            Url.setUrl_off(String.format("http://192.168.1.%s:8080/", s));
        }
        RetrofitUtil.setUrl(onLineOrNot);
    }

    @Override
    public String getUserName() {
        return et_userName.getText().toString().trim();
    }

    @Override
    public String getPsw() {
        return et_psw.getText().toString().trim();
    }

    @Override
    public String getCardNum() {
        return et_cardNum.getText().toString().trim();
    }

    @Override
    public boolean isSavePsw() {
        return savePsw.isChecked();
    }

    @Override
    public boolean isAutoLogin() {
        return autoLogin.isChecked();
    }

    @Override
    public boolean isSaveUserName() {
        return saveId.isChecked();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.hashCode();
        if (id == saveId.hashCode()) if (!isChecked) {
            savePsw.setChecked(false);
            autoLogin.setChecked(false);
        }
        if (id == savePsw.hashCode()) if (isChecked) {
            saveId.setChecked(true);
        } else {
            autoLogin.setChecked(false);
        }
        if (id == autoLogin.hashCode()) if (isChecked) {
            saveId.setChecked(true);
            savePsw.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        for (int i = 0; i < rbs.length; i++) {
            if (rbs[i].getId() == checkedId) {
                doUrlChose(i);
                if (i == 0) {
                    ip_addr.setVisibility(View.VISIBLE);
                    onLineOrNot = 0;
                } else {
                    ip_addr.setText("");
                    ip_addr.setVisibility(View.GONE);
                    onLineOrNot = 1;
                }
            }
        }
    }

    private void doUrlChose(int i) {
        RetrofitUtil.setUrl(i);
    }

    /**
     * 小写自动转换为大写的类。。匹配就替换的原理
     */
    private static class AllCapTransformationMethod extends ReplacementTransformationMethod {

        @Override
        protected char[] getOriginal() {
            return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        }

        @Override
        protected char[] getReplacement() {
            return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        }

    }

    @Override
    public void onGetIMEIPermission() {
        mPlogin.doNext();
    }

    private static class TestCount extends MyPickerView.Selection {
        private String name;
        private String countNum;
        private String countPass;
        private int onLineOrNot;

        public TestCount(String name, int onLineOrNot, String countNum, String countPass) {
            this.name = name;
            this.onLineOrNot = onLineOrNot;
            this.countNum = countNum;
            this.countPass = countPass;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOnLineOrNot() {
            return onLineOrNot;
        }

        public void setOnLineOrNot(int onLineOrNot) {
            this.onLineOrNot = onLineOrNot;
        }

        public String getCountNum() {
            return countNum;
        }

        public void setCountNum(String countNum) {
            this.countNum = countNum;
        }

        public String getCountPass() {
            return countPass;
        }

        public void setCountPass(String countPass) {
            this.countPass = countPass;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}

