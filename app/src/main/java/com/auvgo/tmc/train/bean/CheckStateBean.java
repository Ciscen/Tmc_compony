package com.auvgo.tmc.train.bean;

/**
 * Created by lc on 2016/12/10
 */

public class CheckStateBean {

    /**
     * status : 0  0未订座   1成功    4失败
     * orderno : MDW121063502061866
     * approvestatus : 3
     */

    private int status;
    private String orderno;
    private int approvestatus;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getApprovestatus() {
        return approvestatus;
    }

    public void setApprovestatus(int approvestatus) {
        this.approvestatus = approvestatus;
    }
}
