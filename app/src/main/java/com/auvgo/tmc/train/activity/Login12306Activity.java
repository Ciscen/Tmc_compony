package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.train.bean.Response12306Bean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Login12306Activity extends BaseActivity implements View.OnClickListener {

    private EditText username, psw;
    private View commit;
    private TrainBean.DBean dBean;
    private int seattypeposition;
    private int booktypeposition;
    private String fromCode;
    private String toCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login12306;
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        dBean = (TrainBean.DBean) bundle.getSerializable("bean");
        seattypeposition = bundle.getInt("seattypeposition");
        booktypeposition = bundle.getInt("booktypeposition");
        fromCode = bundle.getString("from");
        toCode = bundle.getString("to");
    }

    @Override
    protected void findViews() {
        username = (EditText) findViewById(R.id.login_12306_username);
        psw = (EditText) findViewById(R.id.login_12306_psw);
        commit = findViewById(R.id.login_12306_commit);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        commit.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
    }

    @Override
    public void onClick(View v) {
        final String un = username.getText().toString();
        final String password = psw.getText().toString();
        if (TextUtils.isEmpty(un)) {
            ToastUtils.showTextToast("请输入12306用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showTextToast("请输入12306密码");
            return;
        }

        Map<String, String> map = new LinkedHashMap<>();
        map.put("username", un);
        map.put("password", password);
        RetrofitUtil.login12306(this, AppUtils.getJson(map), Response12306Bean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Response12306Bean rob = (Response12306Bean) o;
                    if (rob.getStatus() == 200 && rob.getData().getIsPass() == 0) {
                        Intent intent = new Intent(Login12306Activity.this, TrainBookActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", dBean);//当前车次的实体类
                        bundle.putInt("seattypeposition", seattypeposition);//所选中的席别
                        bundle.putInt("booktypeposition", booktypeposition);//预订方式
                        bundle.putString("from", fromCode);//出发城市的编码
                        bundle.putString("to", toCode);//到达城市的编码
                        bundle.putString("accountName", un);
                        bundle.putString("accountPsw", password);
                        intent.putExtra("bundle", bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtils.showTextToast("用户名或密码错误");
                    }
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {

                return false;
            }
        });
    }
}
