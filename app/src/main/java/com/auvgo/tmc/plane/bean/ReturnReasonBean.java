package com.auvgo.tmc.plane.bean;

import com.auvgo.tmc.views.MyPickerView;

/**
 * Created by lc on 2017/1/13
 */

public class ReturnReasonBean extends MyPickerView.Selection{

    /**
     * id : 197
     * companyid : 35
     * zidianid : 6
     * name : 与客户约定时间变更
     * value : 001
     * sort : null
     * status : 0
     */

    private int id;
    private int companyid;
    private int zidianid;
    private String name;
    private String value;
    private Object sort;
    private int status;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
