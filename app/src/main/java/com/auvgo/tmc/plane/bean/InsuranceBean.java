package com.auvgo.tmc.plane.bean;

import com.auvgo.tmc.views.MyPickerView;

/**
 * Created by lc on 2017/2/8
 */

public class InsuranceBean extends MyPickerView.Selection{

    /**
     * id : 1
     * name : 中国平安保险
     * code : PINGAN
     * caigouPrice : 10
     * salePrice : 20
     * describle : 啥都保啊...
     * createtime : 1486455844000
     * lastUpdate : 1486455847000
     */

    private int id;
    private String name;
    private String code;
    private int caigouPrice;
    private int salePrice;
    private String describle;
    private long createtime;
    private long lastUpdate;

    public InsuranceBean(String name) {
        this.name = name;
    }

    public InsuranceBean(String name, String code, int salePrice) {
        this.name = name;
        this.code = code;
        this.salePrice = salePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCaigouPrice() {
        return caigouPrice;
    }

    public void setCaigouPrice(int caigouPrice) {
        this.caigouPrice = caigouPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
