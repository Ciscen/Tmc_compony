package com.auvgo.tmc.common;

import android.content.Intent;
import android.widget.ListView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.adapter.PeisongAdapter;
import com.auvgo.tmc.common.bean.PeisonListBean;
import com.auvgo.tmc.common.bean.RequestSetDefaultBean;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.MyDialog;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class PeisongListActivity extends BaseActivity {

    @BindView(R.id.peisong_list_lv)
    ListView lv;
    private List<PeisonListBean> list;
    private PeisongAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_peisong_list;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        list = new ArrayList<>();
        adapter = new PeisongAdapter(this, list);
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
        super.getData();
        Map<String, String> map = new HashMap<>();
        UserBean mUserInfoBean = MyApplication.mUserInfoBean;
        map.put("cid", String.valueOf(mUserInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(mUserInfoBean.getId()));
        RetrofitUtil.getPeisongAddr(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    List<PeisonListBean> l = MUtils.getListFromJson(bean.getData(),PeisonListBean[].class);
                    list.addAll(l);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    @OnItemClick(R.id.peisong_list_lv)
    public void onItemClick(final int position) {
        final PeisongAdapter.IPeisong pb = list.get(position);
        if (!pb.isDefault()) {
            showDialog("不设置", "设置", "把当前地址设定为默认地址吗？", new MyDialog.OnButtonClickListener() {
                @Override
                public void onLeftClick() {
                    setResult(pb);
                }

                @Override
                public void onRightClick() {
                    setDefault(pb.getId());
                    setResult(pb);
                }
            });
        } else {
            setResult(pb);
        }
    }

    private void setResult(PeisongAdapter.IPeisong pb) {
        String address = pb.getAddr();
        Intent intent = new Intent();
        intent.putExtra("addr", address);
        setResult(Constant.ACTIVITY_PEISONG_ADDR_FLAG, intent);
        finish();
    }

    private void setDefault(int id) {
        RequestSetDefaultBean rb = new RequestSetDefaultBean();
        UserBean mUserInfoBean = MyApplication.mUserInfoBean;
        rb.setCid(String.valueOf(mUserInfoBean.getCompanyid()));
        rb.setEmpid(String.valueOf(mUserInfoBean.getId()));
        rb.setPeisongid(String.valueOf(id));
        RetrofitUtil.setDefaultAddr(this, AppUtils.getJson(rb), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }
//
//    @Override
//    public void onRefresh() {
//        pgnum = 1;
//        getData();
//    }
//
//    @Override
//    public void onLoadMore() {
//        if (mBean.isIsLastPage()) {
//            lv.onRefreshComplete(true);
//            return;
//        }
//        pgnum++;
//        getData();
//    }
}
