package com.auvgo.tmc.plane.bean;

/**
 * Created by lc on 2016/12/23
 */

public class PlanePolicyBean {

    /**
     * id : 23
     * companyid : 35
     * type : level
     * startlevel : 1
     * endlevel : 1
     * startmile : 0
     * endmile : 10000
     * allowfly : 1
     * cabinlimit : 1
     * cabinzhekou : 60
     * flightlimit : 1
     * flightlowtype : 0
     * flighthour : null
     * allowbefore : 1
     * beforeday : 30
     * status : 0
     * createtime : 1479902546000
     */


    private int id;
    private int companyid;
    private String type;// 0 按公司统一政策 1 按员工职级政策
    private int startlevel;// 开始职级
    private int endlevel;// 职级范围
    private int startmile;// 开始里程
    private int endmile;// 结束里程
    private int allowfly;// 可否乘坐飞机 1为开启
    private int cabinlimit;// 是否开启舱位折扣 1为开启
    private int cabinzhekou;// 舱位折扣
    private int flightlimit;// 是否开启航班价格 1为开启
    private int flightlowtype;// 0航班最低价限制 1全天最低价限制 2前后x小时最低价限制
    private int flighthour;// 前后x小时的限制
    private int allowbefore;// 是否开启提前预订天数的限制
    private int beforeday;// 提前预订的天数
    private int status;
    private int controller;//管控方式0不允许1允许
    private long createtime;

    public int getController() {
        return controller;
    }

    public void setController(int controller) {
        this.controller = controller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStartlevel() {
        return startlevel;
    }

    public void setStartlevel(int startlevel) {
        this.startlevel = startlevel;
    }

    public int getEndlevel() {
        return endlevel;
    }

    public void setEndlevel(int endlevel) {
        this.endlevel = endlevel;
    }

    public int getStartmile() {
        return startmile;
    }

    public void setStartmile(int startmile) {
        this.startmile = startmile;
    }

    public int getEndmile() {
        return endmile;
    }

    public void setEndmile(int endmile) {
        this.endmile = endmile;
    }

    public int getAllowfly() {
        return allowfly;
    }

    public void setAllowfly(int allowfly) {
        this.allowfly = allowfly;
    }

    public int getCabinlimit() {
        return cabinlimit;
    }

    public void setCabinlimit(int cabinlimit) {
        this.cabinlimit = cabinlimit;
    }

    public int getCabinzhekou() {
        return cabinzhekou;
    }

    public void setCabinzhekou(int cabinzhekou) {
        this.cabinzhekou = cabinzhekou;
    }

    public int getFlightlimit() {
        return flightlimit;
    }

    public void setFlightlimit(int flightlimit) {
        this.flightlimit = flightlimit;
    }

    public int getFlightlowtype() {
        return flightlowtype;
    }

    public void setFlightlowtype(int flightlowtype) {
        this.flightlowtype = flightlowtype;
    }

    public int getFlighthour() {
        return flighthour;
    }

    public void setFlighthour(int flighthour) {
        this.flighthour = flighthour;
    }

    public int getAllowbefore() {
        return allowbefore;
    }

    public void setAllowbefore(int allowbefore) {
        this.allowbefore = allowbefore;
    }

    public int getBeforeday() {
        return beforeday;
    }

    public void setBeforeday(int beforeday) {
        this.beforeday = beforeday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
