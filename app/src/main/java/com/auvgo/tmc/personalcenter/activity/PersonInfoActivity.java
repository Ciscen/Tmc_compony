package com.auvgo.tmc.personalcenter.activity;

import android.text.TextUtils;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.bean.PersonalInfoBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {
    private PersonalInfoBean pib;
    private ItemView no, name, enname, id, dept, ifApprove, level, range, costCenter, rule, tel, email;
    private View cancle, sure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void findViews() {
        no = (ItemView) findViewById(R.id.person_info_no);
        name = (ItemView) findViewById(R.id.person_info_name);
        enname = (ItemView) findViewById(R.id.person_info_enname);
        id = (ItemView) findViewById(R.id.person_info_id);
        dept = (ItemView) findViewById(R.id.person_info_depart);
        ifApprove = (ItemView) findViewById(R.id.person_info_approve);
        level = (ItemView) findViewById(R.id.person_info_level);
        range = (ItemView) findViewById(R.id.person_info_bookRange);
        costCenter = (ItemView) findViewById(R.id.person_info_costCenter);
        rule = (ItemView) findViewById(R.id.person_info_approveRule);
        tel = (ItemView) findViewById(R.id.person_info_tel);
        email = (ItemView) findViewById(R.id.person_info_email);
        cancle = findViewById(R.id.person_info_cancle);
        sure = findViewById(R.id.person_info_sure);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        super.getData();
        HashMap<String, String> map = new LinkedHashMap<>();
        UserBean userInfoBean = MyApplication.mUserInfoBean;
        map.put("cid", String.valueOf(userInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(userInfoBean.getId()));
        RetrofitUtil.getPersonalInfo(this, AppUtils.getJson(map), PersonalInfoBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                pib = (PersonalInfoBean) o;
                updateView();
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void updateView() {
        no.setContent(pib.getAccno());
        name.setContent(pib.getName());
        enname.setContent(pib.getNameen());
        id.setContent(pib.getCertno());
        dept.setContent(pib.getDeptname());
        ifApprove.setContent(pib.getIfapprove() == 0 ? "否" : "是");
        int bookrange = pib.getBookrange();
        String rangeStr = "";
        if (bookrange == 0) {
            rangeStr = "全公司";
        } else if (bookrange == 1) {
            rangeStr = "本部门";
        } else if (bookrange == 2) {
            rangeStr = "指定部门";
        }
        this.level.setContent(pib.getLevelName());
        range.setContent(rangeStr);
        costCenter.setContent(pib.getCostName());
        rule.setContent(pib.getApproveName());

        tel.setContent(pib.getMobile());
        email.setContent(pib.getEmail());
    }

    @Override
    protected void setViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_info_sure:
                if (!tel.getContent().matches(Constant.REGEX_MOBILE)) {
                    ToastUtils.showTextToast("请输入正确的手机号");
                    return;
                }
                String emailStr = email.getContent().trim();
                if (!emailStr.matches(Constant.REGEX_EMAIL) && !TextUtils.isEmpty(emailStr)) {
                    ToastUtils.showTextToast("请输入正确的邮箱");
                    return;
                }
                HashMap<String, String> map = new LinkedHashMap<>();
                map.put("cid", String.valueOf(pib.getCompanyid()));
                map.put("empid", String.valueOf(pib.getId()));
                map.put("mobile", tel.getContent());
                map.put("email", email.getContent());
                RetrofitUtil.updatePCInfo(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        String info = "";
                        if (status == 200) {
                            info = "修改成功！";
                        } else {
                            info = "修改失败！";
                        }
                        DialogUtil.showDialog(PersonInfoActivity.this, "提示", "", "确定", info, new MyDialog.OnButtonClickListener() {
                            @Override
                            public void onLeftClick() {
                            }

                            @Override
                            public void onRightClick() {
                                finish();
                            }
                        });
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
                break;
            case R.id.person_info_cancle:
                if (ifEdited()) {
                    DialogUtil.showDialog(this, "提示", "取消", "确定", "放弃修改？", new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                        }

                        @Override
                        public void onRightClick() {
                            finish();
                        }
                    });
                    return;
                }
                finish();
                break;
        }
    }

    private boolean ifEdited() {
        if (pib == null) {
            return false;
        }
        if (!tel.getContent().equals(pib.getMobile()) || !email.getContent().equals(pib.getEmail())) {
            return true;
        }
        return false;
    }
}
