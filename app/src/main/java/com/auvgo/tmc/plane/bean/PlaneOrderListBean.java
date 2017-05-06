package com.auvgo.tmc.plane.bean;

import java.util.List;

/**
 * Created by lc on 2017/1/10
 */

public class PlaneOrderListBean {

    /**
     * pageNum : 1
     * pageSize : 10
     * size : 10
     * startRow : 1
     * endRow : 10
     * total : 39
     * pages : 4
     * list : [{"paystatus":0,"totalprice":1440,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010918220781380","depttime":"07:00","approvestatus":0,"deptdate":"2017-01-20","statu":1,"chencheRen":"梁晨晨,王悦","airline":"MU5138"},{"paystatus":0,"totalprice":1440,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010935087141215","depttime":"07:00","approvestatus":4,"deptdate":"2017-01-20","statu":1,"chencheRen":"梁晨晨,王悦","airline":"MU5138"},{"paystatus":0,"totalprice":1170,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010967437248425","depttime":"06:40","approvestatus":0,"deptdate":"2017-01-20","statu":1,"chencheRen":"王悦","airline":"MU3662"},{"paystatus":0,"totalprice":1170,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010974371517485","depttime":"06:40","approvestatus":4,"deptdate":"2017-01-20","statu":1,"chencheRen":"王悦","airline":"MU3662"},{"paystatus":0,"totalprice":1170,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010934186814124","depttime":"06:40","approvestatus":0,"deptdate":"2017-01-20","statu":1,"chencheRen":"王悦","airline":"MU3662"},{"paystatus":0,"totalprice":1170,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010920227141202","depttime":"06:40","approvestatus":0,"deptdate":"2017-01-20","statu":1,"chencheRen":"王悦","airline":"MU3662"},{"paystatus":0,"totalprice":1170,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010957447016007","depttime":"06:35","approvestatus":0,"deptdate":"2017-01-20","statu":1,"chencheRen":"王悦","airline":"MU3924"},{"paystatus":0,"totalprice":1220,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010965520402877","depttime":"07:00","approvestatus":4,"deptdate":"2017-01-20","statu":1,"chencheRen":"梁晨晨,王悦","airline":"CZ9271"},{"paystatus":0,"totalprice":1220,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010915631873470","depttime":"07:00","approvestatus":0,"deptdate":"2017-01-20","statu":1,"chencheRen":"王悦,梁晨晨","airline":"CZ9271"},{"paystatus":0,"totalprice":2340,"orgname":"首都国际机场","arriname":"虹桥国际机场","orderno":"AUVGO010926788137237","depttime":"06:35","approvestatus":0,"deptdate":"2017-01-20","statu":1,"chencheRen":"王悦,梁晨晨","airline":"MU3924"}]
     * firstPage : 1
     * prePage : 0
     * nextPage : 2
     * lastPage : 4
     * isFirstPage : true
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : true
     * navigatePages : 8
     * navigatepageNums : [1,2,3,4]
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
    private List<ListBean> list;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
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
         * paystatus : 0
         * totalprice : 1440
         * orgname : 首都国际机场
         * arriname : 虹桥国际机场
         * orderno : AUVGO010918220781380
         * depttime : 07:00
         * approvestatus : 0
         * deptdate : 2017-01-20
         * statu : 1
         * chencheRen : 梁晨晨,王悦
         * airline : MU5138
         */

        private int paystatus;
        private double totalprice;
        private String orgname;
        private String arriname;
        private String orderno;
        private String depttime;
        private int approvestatus;
        private String deptdate;
        private int statu;
        private String chencheRen;
        private String airline;
        private String type;
        private double tpprice;

        public double getTpprice() {
            return tpprice;
        }

        public void setTpprice(double tpprice) {
            this.tpprice = tpprice;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(int paystatus) {
            this.paystatus = paystatus;
        }

        public double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(double totalprice) {
            this.totalprice = totalprice;
        }

        public String getOrgname() {
            return orgname;
        }

        public void setOrgname(String orgname) {
            this.orgname = orgname;
        }

        public String getArriname() {
            return arriname;
        }

        public void setArriname(String arriname) {
            this.arriname = arriname;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getDepttime() {
            return depttime;
        }

        public void setDepttime(String depttime) {
            this.depttime = depttime;
        }

        public int getApprovestatus() {
            return approvestatus;
        }

        public void setApprovestatus(int approvestatus) {
            this.approvestatus = approvestatus;
        }

        public String getDeptdate() {
            return deptdate;
        }

        public void setDeptdate(String deptdate) {
            this.deptdate = deptdate;
        }

        public int getStatu() {
            return statu;
        }

        public void setStatu(int statu) {
            this.statu = statu;
        }

        public String getChencheRen() {
            return chencheRen;
        }

        public void setChencheRen(String chencheRen) {
            this.chencheRen = chencheRen;
        }

        public String getAirline() {
            return airline;
        }

        public void setAirline(String airline) {
            this.airline = airline;
        }
    }
}
