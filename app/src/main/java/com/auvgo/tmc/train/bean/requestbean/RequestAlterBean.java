package com.auvgo.tmc.train.bean.requestbean;

import java.util.List;

/**
 * Created by lc on 2016/12/8
 */

public class RequestAlterBean extends BaseRequestBean {

    /**
     * cid : 1
     * empid : 2
     * orderno : MDW111841887430600
     * userids : [1,2,3]
     * orderfrom : 来自安卓还是ios
     * reason : 改签原因
     * route : {"fromStation":"北京","arriveStation":"上海"}
     */
    private Double amount;// 票价
    private String cid;
    private String empid;
    private String orderno;
    private String seatcode;
    private String orderfrom = "2";
    private String reason;
    private RouteBean route;
    private List<String> userids;

    public String getSeatcode() {
        return seatcode;
    }

    public void setSeatcode(String seatcode) {
        this.seatcode = seatcode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderfrom() {
        return orderfrom;
    }

    public void setOrderfrom(String orderfrom) {
        this.orderfrom = orderfrom;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RouteBean getRoute() {
        return route;
    }

    public void setRoute(RouteBean route) {
        this.route = route;
    }

    public List<String> getUserids() {
        return userids;
    }

    public void setUserids(List<String> userids) {
        this.userids = userids;
    }

    public static class RouteBean {

        private String orderno;//   原始订单号
        private String fromStation;// 出发站名称
        private String fromStationCode;// 出发站代码
        private String fromTime;// 出发时间
        private String arriveStation;// 终点站名称
        private String arriveStationCode;// 终点站代码
        private String arriveTime;// 到达时间
        private Integer costtime;// 运行分钟
        private String trainCode;// 车次
        private String trainNo;// 列车内部编码
        private String travelTime;// 出发时间 yyyy-MM-dd
        private String runTime;// 运行时间 07:47
        private String arriveDays;//到达时天数
        private String seatCode;// 座位代码 P,M,O
        private String seatType;// 0、商务座 1、特等座 2、一等座 3、二等座 4、高级软卧 5、软卧  6、硬卧 7、软座 8、硬座 9、无座 10、其他
        private Double totalprice;// 单个乘客的费用合计,包括( 票价+订票费+保险)  改为 不收取服务费

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getFromStationCode() {
            return fromStationCode;
        }

        public void setFromStationCode(String fromStationCode) {
            this.fromStationCode = fromStationCode;
        }

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getArriveStationCode() {
            return arriveStationCode;
        }

        public void setArriveStationCode(String arriveStationCode) {
            this.arriveStationCode = arriveStationCode;
        }

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
        }

        public Integer getCosttime() {
            return costtime;
        }

        public void setCosttime(Integer costtime) {
            this.costtime = costtime;
        }

        public String getTrainCode() {
            return trainCode;
        }

        public void setTrainCode(String trainCode) {
            this.trainCode = trainCode;
        }

        public String getTrainNo() {
            return trainNo;
        }

        public void setTrainNo(String trainNo) {
            this.trainNo = trainNo;
        }

        public String getTravelTime() {
            return travelTime;
        }

        public void setTravelTime(String travelTime) {
            this.travelTime = travelTime;
        }

        public String getRunTime() {
            return runTime;
        }

        public void setRunTime(String runTime) {
            this.runTime = runTime;
        }

        public String getArriveDays() {
            return arriveDays;
        }

        public void setArriveDays(String arriveDays) {
            this.arriveDays = arriveDays;
        }


        public String getSeatCode() {
            return seatCode;
        }

        public void setSeatCode(String seatCode) {
            this.seatCode = seatCode;
        }

        public String getSeatType() {
            return seatType;
        }

        public void setSeatType(String seatType) {
            this.seatType = seatType;
        }

        public Double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(Double totalprice) {
            this.totalprice = totalprice;
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
