package com.auvgo.tmc.train.bean.requestbean;

/**
 * Created by lc on 2016/11/23
 */

public class RequestSendApproveBean  extends  BaseRequestBean{

    /**
     * orderno : XLGJ111524446350044
     * costname :
     * costid :
     * proname :
     * proid :
     * shenqingno :
     * chailvitem :
     * approveid :
     */

    private String orderno;
    private String costname;
    private String costid;
    private String proname;
    private String proid;
    private String shenqingno;
    private String chailvitem;
    private String approveid;

    public String getWbreason() {
        return wbreason;
    }

    public void setWbreason(String wbreason) {
        this.wbreason = wbreason;
    }

    private String wbreason;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getCostname() {
        return costname;
    }

    public void setCostname(String costname) {
        this.costname = costname;
    }

    public String getCostid() {
        return costid;
    }

    public void setCostid(String costid) {
        this.costid = costid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getShenqingno() {
        return shenqingno;
    }

    public void setShenqingno(String shenqingno) {
        this.shenqingno = shenqingno;
    }

    public String getChailvitem() {
        return chailvitem;
    }

    public void setChailvitem(String chailvitem) {
        this.chailvitem = chailvitem;
    }

    public String getApproveid() {
        return approveid;
    }

    public void setApproveid(String approveid) {
        this.approveid = approveid;
    }
}
