package com.auvgo.tmc.common.bean;

import android.os.Parcel;

import com.auvgo.tmc.common.interfaces.IApplyChose;
import com.auvgo.tmc.utils.TimeUtils;

import java.util.List;

/**
 * Created by lc on 2017/3/30
 */

public class ApplyNoBean {
    /**
     * pageNum : 1
     * pageSize : 20
     * size : 1
     * startRow : 1
     * endRow : 1
     * total : 1
     * pages : 1
     * list : [{"id":3,"approvalno":"20170330","approvalempno":"156","approvalempname":"111","approvaltime":1490841152000,"travelreason":"adasd","targetwhere":"SH","budgetfee":"1500","travelstart":1490841162000,"travelend":1490841165000}]
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

    public static class ListBean implements IApplyChose {
        /**
         * id : 3
         * approvalno : 20170330
         * approvalempno : 156
         * approvalempname : 111
         * approvaltime : 1490841152000
         * travelreason : adasd
         * targetwhere : SH
         * budgetfee : 1500
         * travelstart : 1490841162000
         * travelend : 1490841165000
         */

        private long id;
        private String approvalno;
        private String approvalempno;
        private String approvalempname;
        private long approvaltime;
        private String travelreason;
        private String targetwhere;
        private String budgetfee;
        private long travelstart;
        private long travelend;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getApprovalno() {
            return approvalno;
        }

        public void setApprovalno(String approvalno) {
            this.approvalno = approvalno;
        }

        public String getApprovalempno() {
            return approvalempno;
        }

        public void setApprovalempno(String approvalempno) {
            this.approvalempno = approvalempno;
        }

        public String getApprovalempname() {
            return approvalempname;
        }

        public void setApprovalempname(String approvalempname) {
            this.approvalempname = approvalempname;
        }

        public long getApprovaltime() {
            return approvaltime;
        }

        public void setApprovaltime(long approvaltime) {
            this.approvaltime = approvaltime;
        }

        public String getTravelreason() {
            return travelreason;
        }

        public void setTravelreason(String travelreason) {
            this.travelreason = travelreason;
        }

        public String getTargetwhere() {
            return targetwhere;
        }

        public void setTargetwhere(String targetwhere) {
            this.targetwhere = targetwhere;
        }

        public String getBudgetfee() {
            return budgetfee;
        }

        public void setBudgetfee(String budgetfee) {
            this.budgetfee = budgetfee;
        }

        public long getTravelstart() {
            return travelstart;
        }

        public void setTravelstart(long travelstart) {
            this.travelstart = travelstart;
        }

        public long getTravelend() {
            return travelend;
        }

        public void setTravelend(long travelend) {
            this.travelend = travelend;
        }

        @Override
        public String getReason() {
            return travelreason == null ? "" : travelreason;
        }

        @Override
        public String getNames() {
            return approvalempname == null ? "" : approvalempname;
        }

        @Override
        public String getDate() {
            if (travelstart == 0 || travelend == 0) {
                return "";
            }
            String start = TimeUtils.getMMDDByTimeStamp(travelstart);
            String end = TimeUtils.getMMDDByTimeStamp(travelend);
            return start + "-" + end;
        }

        @Override
        public String getNo() {
            return approvalno == null ? "" : approvalno;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeString(this.approvalno);
            dest.writeString(this.approvalempno);
            dest.writeString(this.approvalempname);
            dest.writeLong(this.approvaltime);
            dest.writeString(this.travelreason);
            dest.writeString(this.targetwhere);
            dest.writeString(this.budgetfee);
            dest.writeLong(this.travelstart);
            dest.writeLong(this.travelend);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readLong();
            this.approvalno = in.readString();
            this.approvalempno = in.readString();
            this.approvalempname = in.readString();
            this.approvaltime = in.readLong();
            this.travelreason = in.readString();
            this.targetwhere = in.readString();
            this.budgetfee = in.readString();
            this.travelstart = in.readLong();
            this.travelend = in.readLong();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }
}
