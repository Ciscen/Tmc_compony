package com.auvgo.tmc.plane.bean;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

import java.util.List;

/**
 * Created by lc on 2017/1/13
 */

public class RequestReturnPlaneBean extends BaseRequestBean {

    /**
     * cid : 1
     * empid : 2
     * orderno : MDW111841887430600
     * userids : [1,2,3]
     * orderfrom : 来自安卓还是ios
     * tpreason : 退票原因
     */

    private String cid;
    private String empid;
    private String orderno;
    private List<String> passids;
    private String orderfrom;
    private String tpreason;
    private List<String> routeids;

    public List<String> getRouteids() {
        return routeids;
    }

    public void setRouteids(List<String> routeids) {
        this.routeids = routeids;
    }

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

    public List<String> getPassids() {
        return passids;
    }

    public void setPassids(List<String> passids) {
        this.passids = passids;
    }

    public String getOrderfrom() {
        return orderfrom;
    }

    public void setOrderfrom(String orderfrom) {
        this.orderfrom = orderfrom;
    }

    public String getTpreason() {
        return tpreason;
    }

    public void setTpreason(String tpreason) {
        this.tpreason = tpreason;
    }
}
