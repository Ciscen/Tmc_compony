package com.auvgo.tmc.personalcenter.bean;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

/**
 * Created by lc on 2017/3/30
 */

public class RequestApplyNoDetailBean extends BaseRequestBean {
    private String approvalno;
    private String cid;
    private String pagesize;

    public String getApprovalno() {
        return approvalno;
    }

    public void setApprovalno(String approvalno) {
        this.approvalno = approvalno;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }
}
