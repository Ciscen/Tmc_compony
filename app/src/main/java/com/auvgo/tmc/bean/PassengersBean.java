package com.auvgo.tmc.bean;

import com.auvgo.tmc.train.bean.UserBean;

import java.io.Serializable;
import java.util.List;
/**
 * Created by  lc on 2016/11/14
 */
public class PassengersBean implements Serializable {
    /**
     * pageNum : 1
     * pageSize : 10
     * size : 10
     * startRow : 1
     * endRow : 10
     * total : 11
     * pages : 2
     * list : [{"id":1,"name":"测试","nameen":"shenghui","firstname":"he","sex":"1","birthday":"","accno":"0013","mobile":"15710809999","email":"498283307@QQ.COM","zhiwei":"2","level":"dept","deptid":1,"deptname":"业务部","companyid":1,"kaitong":0,"username":"13311095221","password":"387F6F77283C4FC0BA63334C1EF207B6","guoji":null,"ifallowlogin":null,"ifallowbook":0,"bookrange":2,"bookdept":"/1/2/3/112/25","ifapprove":0,"approvesms":"1","approveemail":"1","openid":"31d5c5b9ab146c735ca198a7de57f139","imeicode":"c47532bbb1e2883c902071591ae1ec9b","tokencode":"","certtype":"1","certno":"430481198706236134","passportno":"","passportdate":"2015-08-20","roleid":7,"status":0,"createtime":1438597075000,"bookDeptIds":[1,2,3,112,25]},{"id":101,"name":"小四2","nameen":"","firstname":"","sex":"1","birthday":null,"accno":"10015","mobile":"","email":"","zhiwei":"2","level":"geren","deptid":2,"deptname":"业务部","companyid":1,"kaitong":0,"username":"","password":"","guoji":null,"ifallowlogin":null,"ifallowbook":null,"bookrange":null,"bookdept":"","ifapprove":null,"approvesms":null,"approveemail":null,"openid":null,"imeicode":null,"tokencode":null,"certtype":"1","certno":"45201156199205048","passportno":"","passportdate":"","roleid":null,"status":0,"createtime":1479727219000,"bookDeptIds":[]},{"id":2,"name":"宋欢","nameen":"","firstname":"","sex":"0","birthday":"","accno":"002","mobile":"18611647623","email":"","zhiwei":"2","level":"dept","deptid":2,"deptname":"业务部","companyid":1,"kaitong":1,"username":"18611647623","password":"387F6F77283C4FC0BA63334C1EF207B6","guoji":null,"ifallowlogin":null,"ifallowbook":0,"bookrange":2,"bookdept":"/1/2/3","ifapprove":0,"approvesms":"1","approveemail":"1","openid":null,"imeicode":"c47532bbb1e2883c902071591ae1ec9b","tokencode":"","certtype":"1","certno":"230422198608010725","passportno":"","passportdate":"","roleid":13,"status":0,"createtime":1438661227000,"bookDeptIds":[1,2,3]},{"id":3,"name":"小明","nameen":"","firstname":"","sex":"1","birthday":"","accno":"003","mobile":"13400134000","email":"","zhiwei":"2","level":"geren","deptid":0,"deptname":"业务部","companyid":1,"kaitong":1,"username":"admin","password":"","guoji":null,"ifallowlogin":null,"ifallowbook":0,"bookrange":null,"bookdept":"","ifapprove":1,"approvesms":null,"approveemail":null,"openid":null,"imeicode":null,"tokencode":null,"certtype":"1","certno":"230904198810150889","passportno":"234234234","passportdate":"","roleid":13,"status":0,"createtime":1438935782000,"bookDeptIds":[]},{"id":4,"name":"小明","nameen":"","firstname":"","sex":"1","birthday":"","accno":"003","mobile":"13600136000","email":"","zhiwei":"2","level":"geren","deptid":3,"deptname":"业务部","companyid":1,"kaitong":1,"username":"18611647624","password":"","guoji":null,"ifallowlogin":null,"ifallowbook":0,"bookrange":null,"bookdept":"","ifapprove":1,"approvesms":null,"approveemail":null,"openid":null,"imeicode":null,"tokencode":null,"certtype":"1","certno":"230904198801230988","passportno":"","passportdate":"","roleid":7,"status":0,"createtime":1438936129000,"bookDeptIds":[]},{"id":5,"name":"小红","nameen":"","firstname":"","sex":"0","birthday":"","accno":"004","mobile":"13222222222","email":"","zhiwei":"2","level":"geren","deptid":3,"deptname":"业务部","companyid":1,"kaitong":1,"username":"18611647625","password":"","guoji":null,"ifallowlogin":null,"ifallowbook":1,"bookrange":null,"bookdept":"","ifapprove":null,"approvesms":null,"approveemail":null,"openid":null,"imeicode":null,"tokencode":null,"certtype":"1","certno":"110111198809130890","passportno":"","passportdate":"","roleid":13,"status":0,"createtime":1438936182000,"bookDeptIds":[]}]
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private List<UserBean> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<UserBean> getList() {
        return list;
    }

    public void setList(List<UserBean> list) {
        this.list = list;
    }
}
