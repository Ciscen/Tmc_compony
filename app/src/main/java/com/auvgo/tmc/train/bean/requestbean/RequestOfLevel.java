package com.auvgo.tmc.train.bean.requestbean;

import java.util.List;

/**
 * Created by lc on 2016/11/11
 */

public class RequestOfLevel extends  BaseRequestBean {

    /**
     * level : ["1","4","0"]
     * cid : 1
     */

    private int cid;

    public List<Integer> getEmpids() {
        return empids;
    }

    public void setEmpids(List<Integer> empids) {
        this.empids = empids;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    private List<Integer> empids;

}
