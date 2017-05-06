package com.auvgo.tmc.bean;

import java.util.List;

/**
 * Created by lc on 2017/2/28
 */

public class HotelOrderListBean {

    /**
     * pageNum : 1
     * pageSize : 10
     * size : 10
     * startRow : 1
     * endRow : 10
     * total : 58
     * pages : 6
     * list : [{"paystatus":0,"totalprice":170,"hotelName":"北京国宾酒店","orderno":"AUVGO0228265454","approvestatus":3,"arrivalDate":1488211200000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":350,"hotelName":"北京贝尔特酒店","orderno":"AUVGO0228431827","approvestatus":3,"arrivalDate":1488211200000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":170,"hotelName":"北京国宾酒店","orderno":"AUVGO0228303773","approvestatus":3,"arrivalDate":1488211200000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":170,"hotelName":"北京国宾酒店","orderno":"AUVGO0228246703","approvestatus":3,"arrivalDate":1488211200000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":200,"hotelName":"北京西苑饭店","orderno":"AUVGO0227388641","approvestatus":3,"arrivalDate":1488124800000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":200,"hotelName":"北京西苑饭店","orderno":"AUVGO0227551022","approvestatus":3,"arrivalDate":1488124800000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":200,"hotelName":"北京西苑饭店","orderno":"AUVGO0227357823","approvestatus":3,"arrivalDate":1488124800000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":200,"hotelName":"北京西苑饭店","orderno":"AUVGO0227135113","approvestatus":3,"arrivalDate":1488124800000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":200,"hotelName":"北京西苑饭店","orderno":"AUVGO0227882818","approvestatus":3,"arrivalDate":1488124800000,"statu":0,"chencheRen":"王悦"},{"paystatus":0,"totalprice":350,"hotelName":"北京贝尔特酒店","orderno":"AUVGO0227176613","approvestatus":3,"arrivalDate":1488124800000,"statu":0,"chencheRen":"王悦"}]
     * firstPage : 1
     * prePage : 0
     * nextPage : 2
     * lastPage : 6
     * isFirstPage : true
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : true
     * navigatePages : 8
     * navigatepageNums : [1,2,3,4,5,6]
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
         * totalprice : 170.0
         * hotelName : 北京国宾酒店
         * orderno : AUVGO0228265454
         * approvestatus : 3
         * arrivalDate : 1488211200000
         * statu : 0
         * chencheRen : 王悦
         * createtime
         */

        private int paystatus;
        private double totalprice;
        private String hotelName;
        private String orderno;
        private int approvestatus;
        private long createtime;
        private long arrivalDate;
        private long departureDate;

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public long getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(long departureDate) {
            this.departureDate = departureDate;
        }

        private int statu;
        private String chencheRen;

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

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
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

        public long getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(long arrivalDate) {
            this.arrivalDate = arrivalDate;
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
    }
}
