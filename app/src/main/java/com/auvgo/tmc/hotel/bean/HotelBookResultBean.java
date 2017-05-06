package com.auvgo.tmc.hotel.bean;

/**
 * Created by lc on 2017/2/28
 */

public class HotelBookResultBean {

    /**
     * orderno : 订单号
     * isapprove : true|false
     */

    private String orderno;
    private String isapprove;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getIsapprove() {
        return isapprove;
    }

    public void setIsapprove(String isapprove) {
        this.isapprove = isapprove;
    }
}
