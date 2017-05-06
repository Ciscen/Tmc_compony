package com.auvgo.tmc.train.bean;

import com.auvgo.tmc.views.MyPickerView;

/**
 * Created by lc on 2016/11/11
 * 其实可以通过接口实现适配，不过
 */

public class SelectionBean extends MyPickerView.Selection{
    private String name;//要现实的内容
    private String extra;//附加信息，例如城市，name可以是名字，extra可以是城市代码
    private int code;//数字格式的代码

    public SelectionBean(String name, String extra) {
        this.name = name;
        this.extra = extra;
    }

    public SelectionBean(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public SelectionBean(String name) {
        this.name = name;
    }

    public SelectionBean(String name, int code, boolean isChecked) {
        this.name = name;
        this.code = code;
        this.isChecked = isChecked;
    }

    public SelectionBean(String name, String extra, boolean isChecked) {
        this.name = name;
        this.extra = extra;
        this.isChecked = isChecked;
    }
    public SelectionBean(String name, String extra, int code) {
        this.name = name;
        this.extra = extra;
        this.code = code;
    }

    public SelectionBean(String name, boolean b) {
        this.name = name;
        this.isChecked = b;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
