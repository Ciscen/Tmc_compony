package com.auvgo.tmc.personalcenter;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPasswordActivity extends BaseActivity {

    @BindView(R.id.find_psw_tel)
    EditText tel;
    @BindView(R.id.find_psw_code)
    EditText code;
    @BindView(R.id.find_psw_commit)
    View commit;
    @BindView(R.id.find_psw_obtain_code)
    TextView obtainCode;
    private int time;
    private final int INTERVAL_TIME = 10;
    private int UNABLE_COLOR;
    private int ENABLE_COLOR;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_password;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ENABLE_COLOR = getResources().getColor(R.color.appTheme1);
        UNABLE_COLOR = getResources().getColor(R.color.color_999);
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

    @OnClick({R.id.find_psw_obtain_code, R.id.find_psw_commit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_psw_obtain_code:
                time = INTERVAL_TIME;
                obtainCode.setTextColor(UNABLE_COLOR);
                obtainCode.setEnabled(false);
                time();
                break;
            case R.id.find_psw_commit:
                break;
        }
    }

    private void time() {
        MyApplication.getApplication().getmMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (time <= 0) {
                    obtainCode.setTextColor(ENABLE_COLOR);
                    obtainCode.setText("获取验证码");
                    obtainCode.setEnabled(true);
                } else {
                    time--;
                    obtainCode.setText(time + "s");
                    time();
                }
            }
        }, 1000);
    }
}
