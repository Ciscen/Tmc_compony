package com.auvgo.tmc.personalcenter.activity;

import android.content.Intent;
import android.widget.ListView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.adapter.ApplyChoseListAdapter;
import com.auvgo.tmc.common.bean.ApplyNoBean;
import com.auvgo.tmc.common.bean.RequestApplyNoListBean;
import com.auvgo.tmc.common.interfaces.IApplyChose;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ApplyNoListActivity extends BaseActivity {

    @BindView(R.id.apply_no_lv)
    ListView lv;
    private List<IApplyChose> list;
    private ApplyChoseListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_no_list;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        list = new ArrayList<>();
        adapter = new ApplyChoseListAdapter(this, list, R.layout.item_apply_no_list_pc);
    }

    @Override
    protected void initView() {
        lv.setAdapter(adapter);
    }

    @Override
    protected void setViews() {
    }

    @Override
    protected void getData() {
        RequestApplyNoListBean ranb = new RequestApplyNoListBean();
        UserBean mUserInfoBean = MyApplication.mUserInfoBean;
        ranb.setCid(String.valueOf(mUserInfoBean.getCompanyid()));
        ranb.setUserid(String.valueOf(mUserInfoBean.getId()));
        ranb.setFromdate("");
        ranb.setBackdate("");
        RetrofitUtil.getApplyNoList(this, AppUtils.getJson(ranb), ApplyNoBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == CODE_SUCCESS) {
                    ApplyNoBean anb = (ApplyNoBean) o;
                    list.addAll(anb.getList());
                    updateViews();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void updateViews() {
        adapter.notifyDataSetChanged();
    }

    @OnItemClick(R.id.apply_no_lv)
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ApplyNoDetailActivity.class);
        intent.putExtra("approvalno", list.get(position).getNo());
        startActivity(intent);
    }
}
