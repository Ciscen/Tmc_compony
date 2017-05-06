package com.auvgo.tmc.common;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.bean.ProjectBean;
import com.auvgo.tmc.common.adapter.ProjectAdapter;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by lc on 2016/11/30
 */

public class ProjectAcitivity extends BaseProjectCostCenterActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private java.lang.String tag = "ProjectAcitivity";
    private List<ProjectBean.ListBean> list;
    private ProjectAdapter adapter;
    private ProjectBean pb;

    protected void initDatas() {
        list = new ArrayList<>();
        adapter = new ProjectAdapter(this, list, R.layout.item_project_costcenter_select);
    }

    protected void initViews() {
        title.setTitle("项目中心");
        title.setMore(R.mipmap.plus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogFactory.d(tag, "moreClicked");
            }
        });
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void getList() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("keyword", keyword);
        map.put("pagenum", pageNum + "");
        String json = AppUtils.getJson(map);
        RetrofitUtil.getProject(this, json, ProjectBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                lv.onRefreshComplete(true);
                if (status == 200) {
                    pb = (ProjectBean) o;
                    if (pb.getPageNum() == 1) {
                        list.clear();
                    }
                    isLast = pb.getPages() == pb.getPageNum();
                    list.addAll(pb.getList());
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
        setResult(Constant.ACTIVITY_PROJECTSELECT_FLAG, intent);
        finish();
    }
}
