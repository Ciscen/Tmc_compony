package com.auvgo.tmc.personalcenter.activity;

import android.content.Intent;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.personalcenter.FindPasswordActivity;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.SPUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.update_password_nowPassword)
    ItemView nowPassword;
    @BindView(R.id.update_password_newPassword)
    ItemView newPassword;
    @BindView(R.id.update_password_newPassword2)
    ItemView newPassword2;
    @BindView(R.id.update_password_forget)
    View forget;
    @BindView(R.id.update_password_sure)
    View sure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
    }

    @Override
    protected void findViews() {
        nowPassword = (ItemView) findViewById(R.id.update_password_nowPassword);
        newPassword = (ItemView) findViewById(R.id.update_password_newPassword);
        newPassword2 = (ItemView) findViewById(R.id.update_password_newPassword2);
        sure = findViewById(R.id.update_password_sure);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
        forget.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    protected void setViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_password_sure:
                int l1 = newPassword.getContent().trim().length();
                int l2 = newPassword2.getContent().trim().length();
                if (l1 > 16 || l1 < 6 || l2 < 6 || l2 > 16) {
                    ToastUtils.showTextToast("密码长度必须6-16位");
                    return;
                }
                if (!newPassword.getContent().equals(newPassword2.getContent())) {
                    ToastUtils.showTextToast("两次输入密码不一致");
                    return;
                }
                HashMap<String, String> map = new LinkedHashMap<>();
                map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
                map.put("empid", String.valueOf(MyApplication.mUserInfoBean.getId()));
                map.put("oldpass", AppUtils.getMD5(MyApplication.mUserInfoBean.getUsername() +
                        SPUtils.get(this, "cardNum", "").toString().toUpperCase() + nowPassword.getContent().trim()).toUpperCase());
                map.put("newpass", AppUtils.getMD5(MyApplication.mUserInfoBean.getUsername() +
                        SPUtils.get(this, "cardNum", "").toString().toUpperCase() + newPassword.getContent().trim()).toUpperCase());
                RetrofitUtil.updatePassword(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, final int status, String msg, Object o) {
                        String info = msg;
                        if (status == 200) {
                            info = "修改成功";
                        }
                        DialogUtil.showDialog(UpdatePasswordActivity.this, "提示", "", "确定", info, new MyDialog.OnButtonClickListener() {
                            @Override
                            public void onLeftClick() {

                            }

                            @Override
                            public void onRightClick() {
                                if (status == 200) {
                                    finish();
                                }
                            }
                        });
                        return true;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {

                        return false;
                    }
                });
                break;
            case R.id.update_password_forget:
                Intent intent = new Intent(this, FindPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
