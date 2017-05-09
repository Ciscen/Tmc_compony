package com.auvgo.tmc.common;

import android.content.Intent;
import android.widget.ListView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.adapter.ApplyChoseListAdapter;
import com.auvgo.tmc.common.bean.ApplyNoBean;
import com.auvgo.tmc.common.bean.RequestApplyNoListBean;
import com.auvgo.tmc.common.interfaces.IApplyChose;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ApplyNoChoseActivity extends BaseActivity {

    @BindView(R.id.apply_no_chose_lv)
    ListView lv;
    private ApplyChoseListAdapter adapter;
    private List<IApplyChose> list;
    private String fromdate;
    private String backdate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_no_chose;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        list = new ArrayList<>();
        adapter = new ApplyChoseListAdapter(this, list);
        fromdate = getIntent().getStringExtra("fromdate");
        backdate = getIntent().getStringExtra("backdate");
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
        RequestApplyNoListBean b = new RequestApplyNoListBean();
        b.setUserid(String.valueOf(MyApplication.mUserInfoBean.getId()));
        b.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        b.setFromdate(fromdate);
        b.setBackdate(backdate);
        RetrofitUtil.getApplyNoList(this, AppUtils.getJson(b), ApplyNoBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == CODE_SUCCESS) {
                    if (bean.getData() == null) {
                        ToastUtils.showTextToast("暂无数据");
                        return true;
                    }
                    ApplyNoBean ab = (ApplyNoBean) o;
                    list.addAll(ab.getList());
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

    @OnItemClick(R.id.apply_no_chose_lv)
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("no", list.get(position).getNo());
        setResult(Constant.ACTIVITY_APPLY_NO_CHOSE, intent);
        finish();
    }
}
