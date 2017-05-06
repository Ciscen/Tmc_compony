package com.auvgo.tmc.bean;

import java.util.List;

/**
 * Created by lc on 2016/11/30
 */
public class OrderListBean {
    /**
     * pageNum : 1
     * pageSize : 10
     * size : 3
     * startRow : 1
     * endRow : 3
     * total : 3
     * pages : 1
     * list : [{"travelTime":"2016-12-22","paystatus":0,"totalprice":573.8,"arriveTime":"13:10","orderno":"MDW112847315112085","approvestatus":0,"statu":2,"fromTime":"05:10","chencheRen":"王悦","type":"train","fromStation":"北京南","arriveStation":"上海虹桥"},{"travelTime":"2016-12-22","paystatus":0,"totalprice":573.8,"arriveTime":"13:10","orderno":"MDW112870571185661","approvestatus":0,"statu":0,"fromTime":"05:10","chencheRen":"王悦","type":"train","fromStation":"北京南","arriveStation":"上海虹桥"},{"travelTime":"2016-12-05","paystatus":0,"totalprice":1567.6,"arriveTime":"13:38","orderno":"MDW112970272640710","approvestatus":0,"statu":0,"fromTime":"05:33","chencheRen":"测试谢小四,王悦","type":"train","fromStation":"北京南","arriveStation":"上海虹桥"}]
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
         * travelTime : 2016-12-22
         * paystatus : 0
         * totalprice : 573.8
         * arriveTime : 13:10
         * orderno : MDW112847315112085
         * approvestatus : 0
         * statu : 2
         * fromTime : 05:10
         * chencheRen : 王悦
         * type : train
         * fromStation : 北京南
         * arriveStation : 上海虹桥
         */

        private String travelTime;
        private int paystatus;
        private double totalprice;
        private String arriveTime;
        private String orderno;
        private int approvestatus;
        private int statu;
        private String fromTime;
        private String chencheRen;
        private String type;
        private String fromStation;
        private String arriveStation;
        private String traincode;
        private double tpprice;

        public double getTpprice() {
            return tpprice;
        }

        public void setTpprice(double tpprice) {
            this.tpprice = tpprice;
        }

        public String getTraincode() {
            return traincode;
        }

        public void setTraincode(String traincode) {
            this.traincode = traincode;
        }

        public String getTravelTime() {
            return travelTime;
        }

        public void setTravelTime(String travelTime) {
            this.travelTime = travelTime;
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

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public int getApprovestatus() {
            return approvestatus;
        }

        public void setApprovestatus(int approvestatus) {
            this.approvestatus = approvestatus;
        }

        public int getStatu() {
            return statu;
        }

        public void setStatu(int statu) {
            this.statu = statu;
        }

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getChencheRen() {
            return chencheRen;
        }

        public void setChencheRen(String chencheRen) {
            this.chencheRen = chencheRen;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFromStation() {
            return fromStation;
        }

        public void setFromStation(String fromStation) {
            this.fromStation = fromStation;
        }

        public String getArriveStation() {
            return arriveStation;
        }

        public void setArriveStation(String arriveStation) {
            this.arriveStation = arriveStation;
        }
    }
}
