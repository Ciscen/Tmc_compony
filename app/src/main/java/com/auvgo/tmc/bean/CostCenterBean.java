package com.auvgo.tmc.bean;

import java.util.List;

/**
 * Created by lc on 2016/11/21
 */

public class CostCenterBean {


    /**
     * pageNum : 1
     * pageSize : 20
     * size : 7
     * startRow : 1
     * endRow : 7
     * total : 7
     * pages : 1
     * list : [{"id":2,"companyid":1,"name":"测试项目地方111","code":"001","fuzeren":"贺圣辉","startdate":"2015-08-14","enddate":"2016-11-22","des":"","status":0,"createtime":1438665552000},{"id":14,"companyid":1,"name":"测试鲜蘑","code":"lj93930","fuzeren":"文文文文","startdate":"2016-08-19","enddate":"2016-11-24","des":"","status":0,"createtime":1471578581000},{"id":21,"companyid":1,"name":"上海项目","code":"002","fuzeren":"谢小四","startdate":"2016-11-23","enddate":"2016-11-24","des":"","status":0,"createtime":1479705342000},{"id":17,"companyid":1,"name":"大项目","code":"DXM111","fuzeren":"谢小四","startdate":"2016-10-11","enddate":"2016-11-24","des":"电工V灯光撒","status":0,"createtime":1476181755000},{"id":31,"companyid":1,"name":"客户服务费管理","code":"sdfsdf","fuzeren":"老大","startdate":"2016-11-18","enddate":"2016-11-24","des":"刚额跟给V","status":0,"createtime":1479978129000},{"id":33,"companyid":1,"name":"纷纷","code":"放","fuzeren":"方法","startdate":"2016-11-15","enddate":"2016-11-24","des":"我俄方","status":0,"createtime":1479978501000},{"id":34,"companyid":1,"name":"客户服务费管理","code":"份额风格","fuzeren":"刚跟个","startdate":"2016-11-25","enddate":"2016-11-30","des":"","status":0,"createtime":1479986441000}]
     * firstPage : 1
     * prePage : 0
     * nextPage : 0
     * lastPage : 1
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int lastPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private List<CostCenterBean.ListBean> list;
    private List<Integer> navigatepageNums;

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

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public List<CostCenterBean.ListBean> getList() {
        return list;
    }

    public void setList(List<CostCenterBean.ListBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean {
        /**
         * id : 15
         * companyid : 1
         * name : 水电费水电费
         * parentid : 4
         * code : 不知道呢
         * status : 0
         * createtime : 1456736998000
         */

        private int id;
        private int companyid;
        private String name;
        private int parentid;
        private String code;
        private int status;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        private long createtime;

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

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

    }
}

