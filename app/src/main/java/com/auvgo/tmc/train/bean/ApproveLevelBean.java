package com.auvgo.tmc.train.bean;

import java.util.List;

/**
 * Created by lc on 2016/11/23
 */

public class ApproveLevelBean {

    /**
     * id : 2
     * companyid : 1
     * name : 测试3
     * bianhao : 002
     * status : 0
     * createtime : 1438746523000
     * shenpirens : [{"id":97,"companyid":1,"approveid":2,"employeeid":2,"name":"宋欢","level":1},{"id":98,"companyid":1,"approveid":2,"employeeid":4,"name":"小明","level":2},{"id":99,"companyid":1,"approveid":2,"employeeid":9,"name":"贺圣辉","level":3}]
     */

    private int id;
    private int companyid;
    private String name;
    private String bianhao;
    private int status;
    private boolean isCheck;
    private long createtime;
    private List<ShenpirensBean> shenpirens;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public List<ShenpirensBean> getShenpirens() {
        return shenpirens;
    }

    public void setShenpirens(List<ShenpirensBean> shenpirens) {
        this.shenpirens = shenpirens;
    }

    public static class ShenpirensBean {
        /**
         * id : 97
         * companyid : 1
         * approveid : 2
         * employeeid : 2
         * name : 宋欢
         * level : 1
         */

        private int id;
        private int companyid;
        private int approveid;
        private int employeeid;
        private String name;
        private int level;

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

        public int getApproveid() {
            return approveid;
        }

        public void setApproveid(int approveid) {
            this.approveid = approveid;
        }

        public int getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(int employeeid) {
            this.employeeid = employeeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
