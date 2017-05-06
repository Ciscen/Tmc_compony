package com.auvgo.tmc.common;

import android.content.Intent;
import android.view.View;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddLsPsgActivity extends BaseActivity {
    @BindView(R.id.add_ls_name)
    ItemView name;
    @BindView(R.id.add_ls_idtype)
    ItemView id_type;
    @BindView(R.id.add_ls_cerno)
    ItemView cerno;
    @BindView(R.id.add_ls_tel)
    ItemView tel;

    private List<SelectionBean> types;
    private int mPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_ls_psg;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        types = new ArrayList<>();
        initTypes();
    }

    private void initTypes() {
        types.add(new SelectionBean("身份证", "NI"));
        types.add(new SelectionBean("护照", ""));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setViews() {

    }

    @OnClick({R.id.add_ls_idtype, R.id.add_ls_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.add_ls_idtype:
                showPickerDialog();
                break;
            case R.id.add_ls_sure:
                onSure();
                break;
        }
    }

    private void onSure() {
        AppUtils.closeSoftInput(this);
        String nameStr = name.getContent();
        String certype = types.get(mPosition).getExtra();
        String cernoStr = cerno.getContent();
        String telStr = tel.getContent();
        UserBean ub = new UserBean();
        ub.setName(nameStr);
        ub.setCerttype(certype);
        ub.setCertno(cernoStr);
        ub.setMobile(telStr);
        Intent intent = new Intent();
        intent.putExtra("bean", ub);
        setResult(Constant.ACTIVITY_ADD_LS_PSG_FLAG, intent);
        finish();
    }

    private void showPickerDialog() {
        DialogUtil.showExpandablePickDialog(this, "证件类型", mPosition, types, new MyPickerView.OnPickerViewSure() {
            @Override
            public void onSingleSureClick(int p) {
                mPosition = p;
                id_type.setContent(types.get(p).getName());
            }

            @Override
            public void onMultiSureClick(List<Integer> s) {

            }
        });
    }
}
