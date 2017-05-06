package com.auvgo.tmc.personalcenter.bean;

import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.personalcenter.interfaces.IApplyNoDetail;
import com.auvgo.tmc.utils.TimeUtils;

import java.util.List;

/**
 * Created by lc on 2017/3/30
 */

public class ApplyNoDetailBean {


    /**
     * id : 7
     * approvalno : 20170338
     * approvalempno : 158
     * approvalempname : 王悦
     * approvaltime : 1490976000000
     * travelreason : 回北京总部
     * targetwhere : BJ
     * budgetfee : 1700
     * travelstart : 1490976000000
     * travelend : 1490976000000
     * companyid : 37
     * employeeid : 158
     * appformTravels : [{"id":17,"companyid":37,"approvalno":"20170338","travelby":"train","begincityname":"北京","stopcityname":"上号","travelbegin":"BJ","travelstop":"SH","begintime":1490976000000,"stoptime":1490976000000},{"id":18,"companyid":37,"approvalno":"20170338","travelby":"air","begincityname":"上海","stopcityname":"海南","travelbegin":"SH","travelstop":"HN","begintime":1490976000000,"stoptime":1490976000000},{"id":19,"companyid":37,"approvalno":"20170338","travelby":"air","begincityname":"海南","stopcityname":"北京","travelbegin":"HN","travelstop":"BJ","begintime":1490976000000,"stoptime":1490976000000},{"id":20,"companyid":37,"approvalno":"20170338","travelby":"train","begincityname":"北京","stopcityname":"上号","travelbegin":"BJ","travelstop":"SH","begintime":1491580800000,"stoptime":1491580800000},{"id":21,"companyid":37,"approvalno":"20170338","travelby":"air","begincityname":"上海","stopcityname":"海南","travelbegin":"SH","travelstop":"HN","begintime":1491580800000,"stoptime":1491580800000}]
     * appformHotels : [{"id":18,"companyid":37,"approvalno":"20170338","sleepby":"酒店","nights":"7","starttime":1490976000000,"endtime":1490976000000,"citycode":"SH","cityname":"上海"},{"id":19,"companyid":37,"approvalno":"20170338","sleepby":"酒店","nights":"7","starttime":1490976000000,"endtime":1490976000000,"citycode":"HN","cityname":"海南"},{"id":20,"companyid":37,"approvalno":"20170338","sleepby":"酒店","nights":"7","starttime":1491580800000,"endtime":1491580800000,"citycode":"SH","cityname":"上海"},{"id":21,"companyid":37,"approvalno":"20170338","sleepby":"酒店","nights":"7","starttime":1491580800000,"endtime":1491580800000,"citycode":"HN","cityname":"海南"}]
     */

    private int id;
    private String approvalno;
    private String approvalempno;
    private String approvalempname;
    private long approvaltime;
    private String travelreason;
    private String targetwhere;
    private String budgetfee;
    private long travelstart;
    private long travelend;
    private int companyid;
    private int employeeid;
    private List<AppformTravelsBean> appformTravels;
    private List<AppformHotelsBean> appformHotels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public List<AppformTravelsBean> getAppformTravels() {
        return appformTravels;
    }

    public void setAppformTravels(List<AppformTravelsBean> appformTravels) {
        this.appformTravels = appformTravels;
    }

    public List<AppformHotelsBean> getAppformHotels() {
        return appformHotels;
    }

    public void setAppformHotels(List<AppformHotelsBean> appformHotels) {
        this.appformHotels = appformHotels;
    }

    public static class AppformTravelsBean implements IApplyNoDetail {
        /**
         * id : 17
         * companyid : 37
         * approvalno : 20170338
         * travelby : train
         * begincityname : 北京
         * stopcityname : 上号
         * travelbegin : BJ
         * travelstop : SH
         * begintime : 1490976000000
         * stoptime : 1490976000000
         */

        private int id;
        private int companyid;
        private String approvalno;
        private String travelby;
        private String begincityname;
        private String stopcityname;
        private String travelbegin;
        private String travelstop;
        private long begintime;
        private long stoptime;

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

        public String getApprovalno() {
            return approvalno;
        }

        public void setApprovalno(String approvalno) {
            this.approvalno = approvalno;
        }

        public String getTravelby() {
            return travelby;
        }

        public void setTravelby(String travelby) {
            this.travelby = travelby;
        }

        public String getBegincityname() {
            return begincityname;
        }

        public void setBegincityname(String begincityname) {
            this.begincityname = begincityname;
        }

        public String getStopcityname() {
            return stopcityname;
        }

        public void setStopcityname(String stopcityname) {
            this.stopcityname = stopcityname;
        }

        public String getTravelbegin() {
            return travelbegin;
        }

        public void setTravelbegin(String travelbegin) {
            this.travelbegin = travelbegin;
        }

        public String getTravelstop() {
            return travelstop;
        }

        public void setTravelstop(String travelstop) {
            this.travelstop = travelstop;
        }

        public long getBegintime() {
            return begintime;
        }

        public void setBegintime(long begintime) {
            this.begintime = begintime;
        }

        public long getStoptime() {
            return stoptime;
        }

        public void setStoptime(long stoptime) {
            this.stoptime = stoptime;
        }


        @Override
        public String getTv0() {
            return begintime == 0 ? "" : TimeUtils.getMMDDByTimeStamp(begintime);
        }

        @Override
        public String getTv1() {
            return travelby == null ? "" : travelby.equals(Constant.PLANE) ? "飞机" : "火车";
        }

        @Override
        public String getTv2() {
            return stoptime == 0 ? "" : TimeUtils.getMMDDByTimeStamp(stoptime);
        }
    }

    public static class AppformHotelsBean implements IApplyNoDetail {
        /**
         * id : 18
         * companyid : 37
         * approvalno : 20170338
         * sleepby : 酒店
         * nights : 7
         * starttime : 1490976000000
         * endtime : 1490976000000
         * citycode : SH
         * cityname : 上海
         */

        private int id;
        private int companyid;
        private String approvalno;
        private String sleepby;
        private String nights;
        private long starttime;
        private long endtime;
        private String citycode;
        private String cityname;

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

        public String getApprovalno() {
            return approvalno;
        }

        public void setApprovalno(String approvalno) {
            this.approvalno = approvalno;
        }

        public String getSleepby() {
            return sleepby;
        }

        public void setSleepby(String sleepby) {
            this.sleepby = sleepby;
        }

        public String getNights() {
            return nights;
        }

        public void setNights(String nights) {
            this.nights = nights;
        }

        public long getStarttime() {
            return starttime;
        }

        public void setStarttime(long starttime) {
            this.starttime = starttime;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        @Override
        public String getTv0() {
            return TimeUtils.getMMDDByTimeStamp(starttime);
        }

        @Override
        public String getTv1() {
            return nights + "晚";
        }

        @Override
        public String getTv2() {
            return TimeUtils.getMMDDByTimeStamp(endtime) + "    " + cityname;
        }
    }
}
