package com.auvgo.tmc.train.bean;

import java.util.List;

/**
 * Created by lc on 2016/12/10
 */

public class AlterDetailBean {

    /**
     * id : 28
     * companyid : 1
     * companycode : MDW
     * companyname : 耒阳莫斗网络科技有限公司
     * orderLevel : 0
     * orderFrom : 2
     * linkAddress :
     * linkEmail : null
     * linkName : 宋欢
     * linkPhone : 18611647623
     * totalprice : 933.8
     * opUserId : 2
     * opUserName : 宋欢
     * payStatus : null
     * peisongremark : null
     * oldOutBillNo : E120446118
     * outBillNo : null
     * gaiqianReason : 其他
     * approvid : null
     * approvestatus : null
     * tjUserId : null
     * tjUserName : null
     * status : 1
     * oldJkOrder : 2016121000000000020
     * jkOrder : null
     * backStatus : null
     * lastConfirmTime : null
     * oldTotalPrice : 933.0
     * gaiqianTotalPrice : 933.0
     * apiKoukuan : null
     * apiJiakuan : null
     * gaiType : 1
     * tuiCharges : 0.0
     * createtime : 1481352997000
     * gaiqianRoute : {"id":26,"orderno":"GQ121022518661363","oldOrderno":"MDW121001423315385","orderid":28,"companyid":1,"trainCode":"G105","fromStation":"北京南","fromStationCode":"VNP","fromTime":"07:35","arriveStation":"上海虹桥","arriveStationCode":"AOH","arriveTime":"13:15","travelTime":"2016-12-24","trainNo":"240000G1050G","costtime":"340","runTime":"05:40","arriveDays":"0","createtime":null}
     * oldRoute : {"id":804,"orderno":"MDW121001423315385","orderid":812,"companyid":1,"fromStation":"北京南","fromStationCode":"VNP","fromTime":"07:35","arriveStation":"上海虹桥","arriveStationCode":"AOH","arriveTime":"13:15","costtime":340,"trainCode":"G105","trainNo":"240000G1050G","travelTime":"2016-12-23","runTime":"05:40","arriveDays":"0","createtime":1481340461000}
     * gorderno : GQ121022518661363
     * oorderno : MDW121001423315385
     * users : [{"id":29,"companyid":1,"orderno":"GQ121022518661363","orderid":28,"userName":"王悦","userPhone":"","userIds":"410721199311240528","bxPayMoney":0,"fuwufei":20,"ticketCharges":0.8,"ticketType":1,"idsType":"1","deptid":null,"deptname":"研发部","amount":933,"newPiaohao":null,"oldPiaohao":"E120446118101004F","trainBox":"01车厢","seatNo":"04F号","seatType":"一等座","seatCode":"M","accountName":"","accountPwd":"","totalprice":933.8}]
     */

    private int id;
    private int companyid;
    private String companycode;
    private String companyname;
    private String orderLevel;
    private String orderFrom;
    private String linkAddress;
    private Object linkEmail;
    private String linkName;
    private String linkPhone;
    private double totalprice;
    private int opUserId;
    private String opUserName;
    private Object payStatus;
    private Object peisongremark;
    private String oldOutBillNo;
    private Object outBillNo;
    private String gaiqianReason;
    private Object approvid;
    private Object approvestatus;
    private Object tjUserId;
    private Object tjUserName;
    private String status;
    private String oldJkOrder;
    private Object jkOrder;
    private Object backStatus;
    private Object lastConfirmTime;

    private double oldTotalPrice;
    private double gaiqianTotalPrice;
    private double oldAllticketprice;
    private double gaiAllticketprice;

    private Object apiKoukuan;
    private Object apiJiakuan;
    private int gaiType;
    private double tuiCharges;
    private long createtime;
    private GaiqianRouteBean gaiqianRoute;
    private OldRouteBean oldRoute;
    private String gorderno;
    private String oorderno;
    private List<UsersBean> users;
    private OrderPaymentBean orderPayment;

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

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(String orderLevel) {
        this.orderLevel = orderLevel;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public double getOldAllticketprice() {
        return oldAllticketprice;
    }

    public void setOldAllticketprice(double oldAllticketprice) {
        this.oldAllticketprice = oldAllticketprice;
    }

    public double getGaiAllticketprice() {
        return gaiAllticketprice;
    }

    public void setGaiAllticketprice(double gaiAllticketprice) {
        this.gaiAllticketprice = gaiAllticketprice;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public Object getLinkEmail() {
        return linkEmail;
    }

    public void setLinkEmail(Object linkEmail) {
        this.linkEmail = linkEmail;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
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

    public Object getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Object payStatus) {
        this.payStatus = payStatus;
    }

    public Object getPeisongremark() {
        return peisongremark;
    }

    public void setPeisongremark(Object peisongremark) {
        this.peisongremark = peisongremark;
    }

    public String getOldOutBillNo() {
        return oldOutBillNo;
    }

    public void setOldOutBillNo(String oldOutBillNo) {
        this.oldOutBillNo = oldOutBillNo;
    }

    public Object getOutBillNo() {
        return outBillNo;
    }

    public void setOutBillNo(Object outBillNo) {
        this.outBillNo = outBillNo;
    }

    public String getGaiqianReason() {
        return gaiqianReason;
    }

    public OrderPaymentBean getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(OrderPaymentBean orderPayment) {
        this.orderPayment = orderPayment;
    }

    public void setGaiqianReason(String gaiqianReason) {
        this.gaiqianReason = gaiqianReason;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOldJkOrder() {
        return oldJkOrder;
    }

    public void setOldJkOrder(String oldJkOrder) {
        this.oldJkOrder = oldJkOrder;
    }

    public Object getJkOrder() {
        return jkOrder;
    }

    public void setJkOrder(Object jkOrder) {
        this.jkOrder = jkOrder;
    }

    public Object getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(Object backStatus) {
        this.backStatus = backStatus;
    }

    public Object getLastConfirmTime() {
        return lastConfirmTime;
    }

    public void setLastConfirmTime(Object lastConfirmTime) {
        this.lastConfirmTime = lastConfirmTime;
    }

    public double getOldTotalPrice() {
        return oldTotalPrice;
    }

    public void setOldTotalPrice(double oldTotalPrice) {
        this.oldTotalPrice = oldTotalPrice;
    }

    public double getGaiqianTotalPrice() {
        return gaiqianTotalPrice;
    }

    public void setGaiqianTotalPrice(double gaiqianTotalPrice) {
        this.gaiqianTotalPrice = gaiqianTotalPrice;
    }

    public Object getApiKoukuan() {
        return apiKoukuan;
    }

    public void setApiKoukuan(Object apiKoukuan) {
        this.apiKoukuan = apiKoukuan;
    }

    public Object getApiJiakuan() {
        return apiJiakuan;
    }

    public void setApiJiakuan(Object apiJiakuan) {
        this.apiJiakuan = apiJiakuan;
    }

    public int getGaiType() {
        return gaiType;
    }

    public void setGaiType(int gaiType) {
        this.gaiType = gaiType;
    }

    public double getTuiCharges() {
        return tuiCharges;
    }

    public void setTuiCharges(double tuiCharges) {
        this.tuiCharges = tuiCharges;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public GaiqianRouteBean getGaiqianRoute() {
        return gaiqianRoute;
    }

    public void setGaiqianRoute(GaiqianRouteBean gaiqianRoute) {
        this.gaiqianRoute = gaiqianRoute;
    }

    public OldRouteBean getOldRoute() {
        return oldRoute;
    }

    public void setOldRoute(OldRouteBean oldRoute) {
        this.oldRoute = oldRoute;
    }

    public String getGorderno() {
        return gorderno;
    }

    public void setGorderno(String gorderno) {
        this.gorderno = gorderno;
    }

    public String getOorderno() {
        return oorderno;
    }

    public void setOorderno(String oorderno) {
        this.oorderno = oorderno;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class GaiqianRouteBean {
        /**
         * id : 26
         * orderno : GQ121022518661363
         * oldOrderno : MDW121001423315385
         * orderid : 28
         * companyid : 1
         * trainCode : G105
         * fromStation : 北京南
         * fromStationCode : VNP
         * fromTime : 07:35
         * arriveStation : 上海虹桥
         * arriveStationCode : AOH
         * arriveTime : 13:15
         * travelTime : 2016-12-24
         * trainNo : 240000G1050G
         * costtime : 340
         * runTime : 05:40
         * arriveDays : 0
         * createtime : null
         */

        private int id;
        private String orderno;
        private String oldOrderno;
        private int orderid;
        private int companyid;
        private String trainCode;
        private String fromStation;
        private String fromStationCode;
        private String fromTime;
        private String arriveStation;
        private String arriveStationCode;
        private String arriveTime;
        private String travelTime;
        private String trainNo;
        private String costtime;
        private String runTime;
        private String arriveDays;
        private Object createtime;

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

        public String getOldOrderno() {
            return oldOrderno;
        }

        public void setOldOrderno(String oldOrderno) {
            this.oldOrderno = oldOrderno;
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

        public String getTrainCode() {
            return trainCode;
        }

        public void setTrainCode(String trainCode) {
            this.trainCode = trainCode;
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

        public String getTravelTime() {
            return travelTime;
        }

        public void setTravelTime(String travelTime) {
            this.travelTime = travelTime;
        }

        public String getTrainNo() {
            return trainNo;
        }

        public void setTrainNo(String trainNo) {
            this.trainNo = trainNo;
        }

        public String getCosttime() {
            return costtime;
        }

        public void setCosttime(String costtime) {
            this.costtime = costtime;
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

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }
    }

    public static class OldRouteBean {
        /**
         * id : 804
         * orderno : MDW121001423315385
         * orderid : 812
         * companyid : 1
         * fromStationCode : VNP
         * fromStation : 北京南
         * fromTime : 07:35
         * arriveStation : 上海虹桥
         * arriveStationCode : AOH
         * arriveTime : 13:15
         * costtime : 340
         * trainCode : G105
         * trainNo : 240000G1050G
         * travelTime : 2016-12-23
         * runTime : 05:40
         * arriveDays : 0
         * createtime : 1481340461000
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

    public static class UsersBean {

        /**
         * id : 29
         * companyid : 1
         * orderno : GQ121022518661363
         * orderid : 28
         * userName : 王悦
         * userPhone :
         * userIds : 410721199311240528
         * bxPayMoney : 0.0
         * fuwufei : 20
         * ticketCharges : 0.8
         * ticketType : 1
         * idsType : 1
         * deptid : null
         * deptname : 研发部
         * amount : 933.0
         * newPiaohao : null
         * oldPiaohao : E120446118101004F
         * trainBox : 01车厢
         * seatNo : 04F号
         * seatType : 一等座
         * seatCode : M
         * accountName :
         * accountPwd :
         * totalprice : 933.8
         */

        private int id;
        private int companyid;
        private String orderno;
        private int orderid;
        private String userName;
        private String userPhone;
        private String userIds;
        private double bxPayMoney;
        private int fuwufei;
        private double ticketCharges;
        private int ticketType;
        private String idsType;
        private Object deptid;
        private String deptname;
        private double amount;
        private String newPiaohao;
        private String oldPiaohao;
        private String trainBox;
        private String seatNo;
        private String seatType;
        private String seatCode;
        private String accountName;
        private String accountPwd;
        private double totalprice;

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

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
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

        public String getUserIds() {
            return userIds;
        }

        public void setUserIds(String userIds) {
            this.userIds = userIds;
        }

        public double getBxPayMoney() {
            return bxPayMoney;
        }

        public void setBxPayMoney(double bxPayMoney) {
            this.bxPayMoney = bxPayMoney;
        }

        public int getFuwufei() {
            return fuwufei;
        }

        public void setFuwufei(int fuwufei) {
            this.fuwufei = fuwufei;
        }

        public double getTicketCharges() {
            return ticketCharges;
        }

        public void setTicketCharges(double ticketCharges) {
            this.ticketCharges = ticketCharges;
        }

        public int getTicketType() {
            return ticketType;
        }

        public void setTicketType(int ticketType) {
            this.ticketType = ticketType;
        }

        public String getIdsType() {
            return idsType;
        }

        public void setIdsType(String idsType) {
            this.idsType = idsType;
        }

        public Object getDeptid() {
            return deptid;
        }

        public void setDeptid(Object deptid) {
            this.deptid = deptid;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getNewPiaohao() {
            return newPiaohao;
        }

        public void setNewPiaohao(String newPiaohao) {
            this.newPiaohao = newPiaohao;
        }

        public String getOldPiaohao() {
            return oldPiaohao;
        }

        public void setOldPiaohao(String oldPiaohao) {
            this.oldPiaohao = oldPiaohao;
        }

        public String getTrainBox() {
            return trainBox;
        }

        public void setTrainBox(String trainBox) {
            this.trainBox = trainBox;
        }

        public String getSeatNo() {
            return seatNo;
        }

        public void setSeatNo(String seatNo) {
            this.seatNo = seatNo;
        }

        public String getSeatType() {
            return seatType;
        }

        public void setSeatType(String seatType) {
            this.seatType = seatType;
        }

        public String getSeatCode() {
            return seatCode;
        }

        public void setSeatCode(String seatCode) {
            this.seatCode = seatCode;
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

        public double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(double totalprice) {
            this.totalprice = totalprice;
        }
    }

    public static class OrderPaymentBean {
        private String paytype;//1月结2现金
        private double receivprice;

        public double getReceivprice() {
            return receivprice;
        }

        public void setReceivprice(double receivprice) {
            this.receivprice = receivprice;
        }


        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }
    }
}
