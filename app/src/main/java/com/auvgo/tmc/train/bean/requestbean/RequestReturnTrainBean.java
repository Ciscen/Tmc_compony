package com.auvgo.tmc.train.bean.requestbean;

import java.util.List;

/**
 * Created by lc on 2016/12/7
 */

public class RequestReturnTrainBean extends  BaseRequestBean {

    /**
     * cid : 1
     * empid : 2
     * orderno : MDW120710512781886
     * userids : 2
     * orderfrom : 2
     */

    private String cid;
    private String empid;
    private String orderno;
    private List<String> userids;
    private String orderfrom;

    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public List<String> getUserids() {
        return userids;
    }

    public void setUserids(List<String> userids) {
        this.userids = userids;
    }

    public String getOrderfrom() {
        return orderfrom;
    }

    public void setOrderfrom(String orderfrom) {
        this.orderfrom = orderfrom;
    }
}
