package com.auvgo.tmc.plane.bean;

import java.io.Serializable;

/**
 * Created by lc on 2016/12/26
 */

public class PlaneRouteBeanWF implements Serializable {
   private PlaneListBean bean;
    private int cangwei;

    public PlaneListBean getBean() {
        return bean;
    }

    public void setBean(PlaneListBean bean) {
        this.bean = bean;
    }

    public int getCangwei() {
        return cangwei;
    }

    public void setCangwei(int cangwei) {
        this.cangwei = cangwei;
    }
}
