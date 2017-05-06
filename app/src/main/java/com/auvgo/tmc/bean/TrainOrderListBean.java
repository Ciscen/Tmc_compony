package com.auvgo.tmc.bean;

import java.util.List;

/**
 * Created by lc on 2016/12/1
 */

public class TrainOrderListBean {

    /**
     * pageNum : 1
     * pageSize : 10
     * size : 7
     * startRow : 1
     * endRow : 7
     * total : 7
     * pages : 1
     * list : [{"travelTime":"2016-12-01","paystatus":0,"totalprice":899.5,"arriveTime":"10:43","orderno":"MDW120118585044802","approvestatus":4,"statu":1,"fromTime":"19:33","chencheRen":"王悦","train_code":"T109","fromStation":"北京","arriveStation":"上海"},{"travelTime":"2016-12-01","paystatus":0,"totalprice":899.5,"arriveTime":"10:43","orderno":"MDW120145816576558","approvestatus":4,"statu":2,"fromTime":"19:33","chencheRen":"宋欢","train_code":"T109","fromStation":"北京","arriveStation":"上海"},{"travelTime":"2016-12-01","paystatus":0,"totalprice":899.5,"arriveTime":"10:43","orderno":"MDW120110408178575","approvestatus":4,"statu":2,"fromTime":"19:33","chencheRen":"宋欢","train_code":"T109","fromStation":"北京","arriveStation":"上海"},{"travelTime":"2016-12-01","paystatus":0,"totalprice":1748,"arriveTime":"23:55","orderno":"MDW120145246112866","approvestatus":0,"statu":4,"fromTime":"19:00","chencheRen":"宋欢","train_code":"G7","fromStation":"北京南","arriveStation":"上海虹桥"},{"travelTime":"2016-12-01","paystatus":0,"totalprice":1748,"arriveTime":"23:55","orderno":"MDW120151817842737","approvestatus":0,"statu":4,"fromTime":"19:00","chencheRen":"宋欢","train_code":"G7","fromStation":"北京南","arriveStation":"上海虹桥"},{"travelTime":"2016-12-01","paystatus":0,"totalprice":953,"arriveTime":"22:49","orderno":"MDW120161772180313","approvestatus":0,"statu":4,"fromTime":"17:15","chencheRen":"宋欢","train_code":"G153","fromStation":"北京南","arriveStation":"上海虹桥"},{"travelTime":"2016-12-01","paystatus":0,"totalprice":1748,"arriveTime":"23:22","orderno":"MDW120111305134061","approvestatus":0,"statu":4,"fromTime":"17:43","chencheRen":"宋欢","train_code":"G157","fromStation":"北京南","arriveStation":"上海虹桥"}]
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
         * travelTime : 2016-12-01
         * paystatus : 0
         * totalprice : 899.5
         * arriveTime : 10:43
         * orderno : MDW120118585044802
         * approvestatus : 4
         * statu : 1
         * fromTime : 19:33
         * chencheRen : 王悦
         * train_code : T109
         * fromStation : 北京
         * arriveStation : 上海
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
        private String train_code;
        private String fromStation;
        private String arriveStation;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getTrain_code() {
            return train_code;
        }

        public void setTrain_code(String train_code) {
            this.train_code = train_code;
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
