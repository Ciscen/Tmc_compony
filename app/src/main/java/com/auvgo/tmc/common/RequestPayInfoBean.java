package com.auvgo.tmc.common;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

/**
 * Created by lc on 2017/4/15
 */

public class RequestPayInfoBean extends BaseRequestBean {
    private String cid;
    private String orderno;
    private String paychannel;
    private String ordertype;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getPaychannel() {
        return paychannel;
    }

    public void setPaychannel(String paychannel) {
        this.paychannel = paychannel;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }
}
