package com.auvgo.tmc.common.bean;

/**
 * Created by lc on 2017/4/10
 */

public class PayTypeResultBean {
    /**
     * id : 8
     * companyid : 35
     * fukuankemu : 1
     * qiyong : Y
     * fangshi : MONTH
     * zhouqi : 2
     * datebegin : 1
     * dateend : 15
     * jiesuanri : 15
     * autokaizhang : Y
     * validdate : null
     * remark :
     * createtime : null
     */

    private int id;
    private int companyid;
    private String fukuankemu;
    private String qiyong;
    private String fangshi;
    private int zhouqi;
    private int datebegin;
    private int dateend;
    private int jiesuanri;
    private String autokaizhang;
    private Object validdate;
    private String remark;
    private Object createtime;

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

    public String getFukuankemu() {
        return fukuankemu;
    }

    public void setFukuankemu(String fukuankemu) {
        this.fukuankemu = fukuankemu;
    }

    public String getQiyong() {
        return qiyong;
    }

    public void setQiyong(String qiyong) {
        this.qiyong = qiyong;
    }

    public String getFangshi() {
        return fangshi;
    }

    public void setFangshi(String fangshi) {
        this.fangshi = fangshi;
    }

    public int getZhouqi() {
        return zhouqi;
    }

    public void setZhouqi(int zhouqi) {
        this.zhouqi = zhouqi;
    }

    public int getDatebegin() {
        return datebegin;
    }

    public void setDatebegin(int datebegin) {
        this.datebegin = datebegin;
    }

    public int getDateend() {
        return dateend;
    }

    public void setDateend(int dateend) {
        this.dateend = dateend;
    }

    public int getJiesuanri() {
        return jiesuanri;
    }

    public void setJiesuanri(int jiesuanri) {
        this.jiesuanri = jiesuanri;
    }

    public String getAutokaizhang() {
        return autokaizhang;
    }

    public void setAutokaizhang(String autokaizhang) {
        this.autokaizhang = autokaizhang;
    }

    public Object getValiddate() {
        return validdate;
    }

    public void setValiddate(Object validdate) {
        this.validdate = validdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Object createtime) {
        this.createtime = createtime;
    }
}
