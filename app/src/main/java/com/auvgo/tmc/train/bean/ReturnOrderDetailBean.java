package com.auvgo.tmc.train.bean;

/**
 * Created by lc on 2016/12/9
 */

public class ReturnOrderDetailBean {
    /**
     * id : 65
     * odOrderno : AUVGO121701537502600
     * odPiaohao : E9511587471130061
     * companyid : 35
     * companyname : 北京艾优薇文化科技有限公司
     * companycode : AUVGO
     * opUserId : 0
     * opUserName : 系统管理员
     * tjUserId : null
     * tjUserName : null
     * jkOdId : 2016121700000297269
     * approvid : null
     * approvestatus : null
     * status : 2
     * orderFrom : 0
     * createtime : 1482112582000
     * payStatus : null
     * tuipiaoUser : {"id":58,"companyid":35,"orderno":"TP121966578588666","orderid":null,"userId":"430481198706236135","userName":"贺圣辉","userPhone":"13311095220","trainCode":"K1785","seattype":"硬卧","seatcode":"3","accountName":"","accountPwd":"","ticketprice":66.5,"khKoukuanrate":"0%","khShouxufei":0,"khTuikuan":66.5,"khBxTuikuan":0,"czKoukuanrate":null,"czShouxufei":null,"czTuikuan":null,"outTicketNo":"E951158747","piaohao":"E9511587471130061","tuiType":1}
     * orderRoute : {"id":991,"orderno":"AUVGO121701537502600","orderid":999,"companyid":35,"fromStation":"北京","fromStationCode":"BJP","fromTime":"14:25","arriveStation":"廊坊北","arriveStationCode":"LFP","arriveTime":"15:18","costtime":53,"trainCode":"K1785","trainNo":"24000K178501","travelTime":"2017-01-11","runTime":"00:53","arriveDays":null,"createtime":1481962241000}
     * torderno : TP121966578588666
     */

    private int id;
    private String odOrderno;
    private String odPiaohao;
    private int companyid;
    private String companyname;
    private String companycode;
    private int opUserId;
    private String opUserName;
    private Object tjUserId;
    private Object tjUserName;
    private String jkOdId;
    private Object approvid;
    private Object approvestatus;
    private int status;
    private int orderFrom;
    private long createtime;
    private Object payStatus;
    private TuipiaoUserBean tuipiaoUser;
    private OrderRouteBean orderRoute;
    private String torderno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOdOrderno() {
        return odOrderno;
    }

    public void setOdOrderno(String odOrderno) {
        this.odOrderno = odOrderno;
    }

    public String getOdPiaohao() {
        return odPiaohao;
    }

    public void setOdPiaohao(String odPiaohao) {
        this.odPiaohao = odPiaohao;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public int getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(int opUserId) {
        this.opUserId = opUserId;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public Object getTjUserId() {
        return tjUserId;
    }

    public void setTjUserId(Object tjUserId) {
        this.tjUserId = tjUserId;
    }

    public Object getTjUserName() {
        return tjUserName;
    }

    public void setTjUserName(Object tjUserName) {
        this.tjUserName = tjUserName;
    }

    public String getJkOdId() {
        return jkOdId;
    }

    public void setJkOdId(String jkOdId) {
        this.jkOdId = jkOdId;
    }

    public Object getApprovid() {
        return approvid;
    }

    public void setApprovid(Object approvid) {
        this.approvid = approvid;
    }

    public Object getApprovestatus() {
        return approvestatus;
    }

    public void setApprovestatus(Object approvestatus) {
        this.approvestatus = approvestatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public Object getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Object payStatus) {
        this.payStatus = payStatus;
    }

    public TuipiaoUserBean getTuipiaoUser() {
        return tuipiaoUser;
    }

    public void setTuipiaoUser(TuipiaoUserBean tuipiaoUser) {
        this.tuipiaoUser = tuipiaoUser;
    }

    public OrderRouteBean getOrderRoute() {
        return orderRoute;
    }

    public void setOrderRoute(OrderRouteBean orderRoute) {
        this.orderRoute = orderRoute;
    }

    public String getTorderno() {
        return torderno;
    }

    public void setTorderno(String torderno) {
        this.torderno = torderno;
    }

    public static class TuipiaoUserBean {
        /**
         * id : 58
         * companyid : 35
         * orderno : TP121966578588666
         * orderid : null
         * userId : 430481198706236135
         * userName : 贺圣辉
         * userPhone : 13311095220
         * trainCode : K1785
         * seattype : 硬卧
         * seatcode : 3
         * accountName :
         * accountPwd :
         * ticketprice : 66.5
         * khKoukuanrate : 0%
         * khShouxufei : 0.0
         * khTuikuan : 66.5
         * khBxTuikuan : 0.0
         * czKoukuanrate : null
         * czShouxufei : null
         * czTuikuan : null
         * outTicketNo : E951158747
         * piaohao : E9511587471130061
         * tuiType : 1
         */

        private int id;
        private int companyid;
        private String orderno;
        private Object orderid;
        private String userId;
        private String userName;
        private String userPhone;
        private String trainCode;
        private String seattype;
        private String seatcode;
        private String accountName;
        private String accountPwd;
        private double ticketprice;
        private String khKoukuanrate;
        private double khShouxufei;
        private double khTuikuan;
        private double khBxTuikuan;
        private Object czKoukuanrate;
        private Object czShouxufei;
        private Object czTuikuan;
        private String outTicketNo;
        private String piaohao;
        private int tuiType;

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

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public Object getOrderid() {
            return orderid;
        }

        public void setOrderid(Object orderid) {
            this.orderid = orderid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getTrainCode() {
            return trainCode;
        }

        public void setTrainCode(String trainCode) {
            this.trainCode = trainCode;
        }

        public String getSeattype() {
            return seattype;
        }

        public void setSeattype(String seattype) {
            this.seattype = seattype;
        }

        public String getSeatcode() {
            return seatcode;
        }

        public void setSeatcode(String seatcode) {
            this.seatcode = seatcode;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountPwd() {
            return accountPwd;
        }

        public void setAccountPwd(String accountPwd) {
            this.accountPwd = accountPwd;
        }

        public double getTicketprice() {
            return ticketprice;
        }

        public void setTicketprice(double ticketprice) {
            this.ticketprice = ticketprice;
        }

        public String getKhKoukuanrate() {
            return khKoukuanrate;
        }

        public void setKhKoukuanrate(String khKoukuanrate) {
            this.khKoukuanrate = khKoukuanrate;
        }

        public double getKhShouxufei() {
            return khShouxufei;
        }

        public void setKhShouxufei(double khShouxufei) {
            this.khShouxufei = khShouxufei;
        }

        public double getKhTuikuan() {
            return khTuikuan;
        }

        public void setKhTuikuan(double khTuikuan) {
            this.khTuikuan = khTuikuan;
        }

        public double getKhBxTuikuan() {
            return khBxTuikuan;
        }

        public void setKhBxTuikuan(double khBxTuikuan) {
            this.khBxTuikuan = khBxTuikuan;
        }

        public Object getCzKoukuanrate() {
            return czKoukuanrate;
        }

        public void setCzKoukuanrate(Object czKoukuanrate) {
            this.czKoukuanrate = czKoukuanrate;
        }

        public Object getCzShouxufei() {
            return czShouxufei;
        }

        public void setCzShouxufei(Object czShouxufei) {
            this.czShouxufei = czShouxufei;
        }

        public Object getCzTuikuan() {
            return czTuikuan;
        }

        public void setCzTuikuan(Object czTuikuan) {
            this.czTuikuan = czTuikuan;
        }

        public String getOutTicketNo() {
            return outTicketNo;
        }

        public void setOutTicketNo(String outTicketNo) {
            this.outTicketNo = outTicketNo;
        }

        public String getPiaohao() {
            return piaohao;
        }

        public void setPiaohao(String piaohao) {
            this.piaohao = piaohao;
        }

        public int getTuiType() {
            return tuiType;
        }

        public void setTuiType(int tuiType) {
            this.tuiType = tuiType;
        }
    }

    public static class OrderRouteBean {
        /**
         * id : 991
         * orderno : AUVGO121701537502600
         * orderid : 999
         * companyid : 35
         * fromStation : 北京
         * fromStationCode : BJP
         * fromTime : 14:25
         * arriveStation : 廊坊北
         * arriveStationCode : LFP
         * arriveTime : 15:18
         * costtime : 53
         * trainCode : K1785
         * trainNo : 24000K178501
         * travelTime : 2017-01-11
         * runTime : 00:53
         * arriveDays : null
         * createtime : 1481962241000
         */

        private int id;
        private String orderno;
        private int orderid;
        private int companyid;
        private String fromStation;
        private String fromStationCode;
        private String fromTime;
        private String arriveStation;
        private String arriveStationCode;
        private String arriveTime;
        private int costtime;
        private String trainCode;
        private String trainNo;
        private String travelTime;
        private String runTime;
        private String arriveDays;
        private long createtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public int getCompanyid() {
            return companyid;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }

        public String getFromStation() {
            return fromStation;
        }

        public void setFromStation(String fromStation) {
            this.fromStation = fromStation;
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

        public String getArriveStation() {
            return arriveStation;
        }

        public void setArriveStation(String arriveStation) {
            this.arriveStation = arriveStation;
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

        public int getCosttime() {
            return costtime;
        }

        public void setCosttime(int costtime) {
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

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }
    }
}
