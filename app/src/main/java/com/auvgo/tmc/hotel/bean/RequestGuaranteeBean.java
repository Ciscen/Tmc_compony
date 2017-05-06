package com.auvgo.tmc.hotel.bean;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

/**
 * Created by lc on 2017/3/2
 */

public class RequestGuaranteeBean extends BaseRequestBean {

    /**
     * orderno : 订单号
     * cardno : 信用卡号
     * cvv :
     * expiration : 4位年+2位月
     * holderName : 持卡人
     * idtype : 身份证 IdentityCard护照 Passport其他 Other
     * idno : 身份证号
     */

    private String orderno;
    private String cardno;
    private String cvv;
    private String expiration;
    private String holderName;
    private String idtype;
    private String idno;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }
}
