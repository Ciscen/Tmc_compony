package com.auvgo.tmc.train.bean;

import com.auvgo.tmc.views.MyPickerView;

/**
 * Created by lc on 2016/11/22
 */

public class WbReasonBean extends MyPickerView.Selection{
    /**
     * id : 104
     * companyid : 1
     * zidianid : 5
     * name : 符合标准的票已售完
     * sort : 0
     * status : 0
     * value : 2
     */

    private int id;
    private int companyid;
    private int zidianid;
    private String name;
    private int sort;
    private int status;
    private String value;

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

    public int getZidianid() {
        return zidianid;
    }

    public void setZidianid(int zidianid) {
        this.zidianid = zidianid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
