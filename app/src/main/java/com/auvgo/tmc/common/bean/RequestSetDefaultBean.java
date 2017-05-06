package com.auvgo.tmc.common.bean;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

/**
 * Created by lc on 2017/4/19
 */

public class RequestSetDefaultBean extends BaseRequestBean {
    private String empid;
    private String peisongid;
    private String cid;

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getPeisongid() {

        return peisongid;
    }

    public void setPeisongid(String peisongid) {
        this.peisongid = peisongid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
