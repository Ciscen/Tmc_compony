package com.auvgo.tmc.common.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lc on 2017/4/18
 */

public class WxInfoBean {
    /**
     * appid : wxf7c591d3fd7f6070
     * noncestr : a25382f8-4478-4ed7-8412-4d19ca27
     * package : Sign=WXPay
     * partnerid : 1445735802
     * prepayid : wx20170418140959cbf16874460273688225
     * sign : 33FAAFE8E2E0AD1AB7CD98AF6AB1739D
     * timestamp : 1492495791
     */

    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
