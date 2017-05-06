package com.auvgo.tmc.train.bean.requestbean;

import java.util.List;

/**
 * Created by lc on 2016/11/11
 */

public class RequestOfPolicy  extends  BaseRequestBean{

    /**
     * level : ["1","4","0"]
     * cid : 1
     */

    private String cid;
    private List<String> level;
    private String cityid;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<String> getLevel() {
        return level;
    }

    public void setLevel(List<String> level) {
        this.level = level;
    }
}
