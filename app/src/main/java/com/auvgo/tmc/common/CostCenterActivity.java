package com.auvgo.tmc.common;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.bean.CostCenterBean;
import com.auvgo.tmc.common.adapter.CostCenterAdapter;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by lc on 2016/12/2
 */

public class CostCenterActivity extends BaseProjectCostCenterActivity implements AdapterView.OnItemClickListener {
    private java.lang.String tag = "CostCenterActivity";
    private CostCenterBean cb;
    private List<CostCenterBean.ListBean> list;
    private CostCenterAdapter adapter;

    @Override
    protected void initDatas() {
        list = new ArrayList<>();
        adapter = new CostCenterAdapter(this, list, R.layout.item_project_costcenter_select);
    }

    @Override
    protected void initViews() {
        title.setTitle("成本中心");
        title.setMore(0, null);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void getList() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("keyword", keyword);
        map.put("pagenum", pageNum + "");
        String json = AppUtils.getJson(map);
        RetrofitUtil.getCostCenter(this, json, CostCenterBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                lv.onRefreshComplete(true);
                if (status == 200) {
                    cb = (CostCenterBean) o;
                    if (cb.getPageNum() == 1) {
                        list.clear();
                    }
                    isLast = cb.getPages() == cb.getPageNum();
                    list.addAll(cb.getList());
                    adapter.notifyDataSetChanged();
                    LogFactory.d(bean.getData());
                } else {
                    isLast = false;
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                lv.onRefreshComplete(true);
                isLast = false;
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("name", list.get(position).getName());
        intent.putExtra("id", list.get(position).getId());
        setResult(Constant.ACTIVITY_COSTCENTERSELECT_FLAG, intent);
        finish();
    }


}
