package com.auvgo.tmc.train.bean;

/**
 * Created by lc on 2016/11/21
 */

public class ProjectBean {
    /**
     * id : 2
     * companyid : 1
     * name : 测试项目地方111
     * code : 001
     * fuzeren : 贺圣辉
     * startdate : 2015-08-14
     * des :
     * status : 0
     * createtime : 1438665552000
     */

    private int id;
    private int companyid;
    private String name;
    private String code;
    private String fuzeren;
    private String startdate;
    private String des;
    private int status;
    private long createtime;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFuzeren() {
        return fuzeren;
    }

    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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
