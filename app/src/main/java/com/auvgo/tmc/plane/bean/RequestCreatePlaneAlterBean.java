package com.auvgo.tmc.plane.bean;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

import java.util.List;

/**
 * Created by lc on 2017/1/19
 */

public class RequestCreatePlaneAlterBean extends BaseRequestBean {

    public String getWeibeiflag() {
        return weibeiflag;
    }

    public void setWeibeiflag(String weibeiflag) {
        this.weibeiflag = weibeiflag;
    }

    public String getWbreason() {
        return wbreason;
    }

    public void setWbreason(String wbreason) {
        this.wbreason = wbreason;
    }

    /**
     * cid : 1
     * empid : 2
     * orderno : MDW111841887430600
     * passids : [1,2,3]
     * routeids : [1,2]
     * orderfrom : 来自安卓还是ios
     * route : [{}]
     * weibeiflag
     * wbreason
     */

    private String cid;
    private String empid;
    private String weibeiflag;
    private String wbreason;
    private String orderno;
    private String orderfrom;
    private List<String> passids;
    private List<String> routeids;
    private List<RequestCreatePlaneOrder.Route> route;

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

    public String getOrderfrom() {
        return orderfrom;
    }

    public void setOrderfrom(String orderfrom) {
        this.orderfrom = orderfrom;
    }

    public List<String> getPassids() {
        return passids;
    }

    public void setPassids(List<String> passids) {
        this.passids = passids;
    }

    public List<String> getRouteids() {
        return routeids;
    }

    public void setRouteids(List<String> routeids) {
        this.routeids = routeids;
    }

    public List<RequestCreatePlaneOrder.Route> getRoute() {
        return route;
    }

    public void setRoute(List<RequestCreatePlaneOrder.Route> route) {
        this.route = route;
    }


}
