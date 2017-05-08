package com.auvgo.tmc.train.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lc on 2016/11/18
 */

public class TrainOrderDetailBean implements Serializable {

    /**
     * id : 686
     * companyid : 1
     * companyname : 耒阳莫斗网络科技有限公司
     * companycode : MDW
     * orderType : ow
     * orderNo : MDW112970272640710
     * orderLevel : 1
     * orderFrom : 0
     * linkAddress : null
     * linkEmail : heshenhui@auvgo.com
     * linkName : 贺圣辉
     * linkPhone : 13311095220
     * ticketnum : 2
     * costname : 技术部
     * costid : 2
     * proname : 上海项目
     * proid : 21
     * shenqingno : 1561515sfsvs
     * ticketprice : null
     * chailvitem : 测试审批
     * totalprice : 1567.6
     * bookUserId : null
     * bookUserName : null
     * opUserId : 0
     * opUserName : 系统管理员
     * opDeptId : 0
     * opDeptName : 行旅国际
     * peisong : null
     * payStatus : 0
     * payType : null
     * peisongremark : fsfs
     * bookpolicy : 测试谢小四,王悦&nbsp;&nbsp;高铁不可预订：二等座&nbsp&nbsp 动车可预订：一等座、二等座&nbsp;&nbsp 普快可预订：硬卧、软座、硬座&nbsp;&nbsp
     * weibeiflag : 1
     * wbreason : 车次取消
     * status : 0
     * approveid : 1
     * approvestatus : 0
     * createtime : 1480398384000
     * backStatus : null
     * travelTime : 2016-12-05
     * lastPayTime : null
     * failReason : null
     * refundAmount : null
     * refundType : null
     * orderId : null
     * outTicketBillno : --
     * outTicketTime : null
     * payCharges : 0.0
     * route : {"id":678,"orderno":"MDW112970272640710","orderid":686,"companyid":1,"fromStation":"北京南","fromStationCode":"VNP","fromTime":"05:33","arriveStation":"上海虹桥","arriveStationCode":"AOH","arriveTime":"13:38","costtime":333,"trainCode":"G107","trainNo":"240000G1070D","travelTime":"2016-12-05","runTime":null,"arriveDays":null,"createtime":1480398384000}
     * users : [{"id":839,"companyid":1,"orderno":"MDW112970272640710","orderid":686,"userId":108,"userName":"测试谢小四","userPhone":"15244563553","userIds":"340881199202023317","bxPayMoney":20,"fuwufei":20,"ticketCharges":0.8,"ticketType":1,"idsType":"1","deptid":null,"deptname":"业务部","amount":933,"piaohao":"--","trainBox":null,"seatNo":null,"seatType":"一等座","seatCode":"M","gaiqianstatus":0,"tuipiaostatus":0,"status":0,"createtime":1480398384000,"accountName":"","accountPwd":"","totalprice":973.8,"sort":1},{"id":840,"companyid":1,"orderno":"MDW112970272640710","orderid":686,"userId":121,"userName":"王悦","userPhone":"","userIds":"410721199311240528","bxPayMoney":20,"fuwufei":20,"ticketCharges":0.8,"ticketType":1,"idsType":"1","deptid":5,"deptname":"研发部","amount":553,"piaohao":"--","trainBox":null,"seatNo":null,"seatType":"二等座","seatCode":"O","gaiqianstatus":0,"tuipiaostatus":0,"status":0,"createtime":1480398384000,"accountName":"","accountPwd":"","totalprice":593.8,"sort":2}]
     * approves : [{"id":113,"companyid":1,"orderno":"MDW112970272640710","employeeid":7,"name":"管理员","deptname":"运营部","level":1,"mobile":"13311095220","email":"","isinert":null,"status":0,"foujueyuanyin":null,"approvetime":null,"createtime":1480398384000},{"id":114,"companyid":1,"orderno":"MDW112970272640710","employeeid":2,"name":"宋欢","deptname":"运营部","level":3,"mobile":"18611647623","email":"","isinert":null,"status":0,"foujueyuanyin":null,"approvetime":null,"createtime":1480398384000}]
     */

    private int id;
    private int companyid;
    private String companyname;
    private String companycode;
    private String orderType;
    private String orderNo;
    private String orderLevel;
    private int orderFrom;
    private String linkAddress;
    private String linkEmail;
    private String linkName;
    private String linkPhone;
    private int ticketnum;
    private String costname;
    private int costid;
    private String proname;
    private int proid;
    private String shenqingno;
    private Object ticketprice;
    private String chailvitem;
    private double totalprice;
    private Object bookUserId;
    private Object bookUserName;
    private int opUserId;
    private String opUserName;
    private int opDeptId;
    private String opDeptName;
    private Object peisong;
    private int payStatus;
    private String payType;
    private String peisongremark;
    private String bookpolicy;
    private int weibeiflag;
    private String wbreason;
    private int status;
    private int approveid;
    private int approvestatus;
    private long createtime;
    private Object backStatus;
    private String travelTime;
    private Long lastPayTime;
    private Object failReason;
    private Object refundAmount;
    private Object refundType;
    private Object orderId;
    private String outTicketBillno;
    private Object outTicketTime;
    private double payCharges;
    private String fromcitycode;//
    private String arrivecitycode;//
    private String fromcityname;//
    private String arrivecityname;//
    private RouteBean route;
    private List<UsersBean> users;
    private List<ApprovesBean> approves;

    public String getFromcitycode() {
        return fromcitycode;
    }

    public void setFromcitycode(String fromcitycode) {
        this.fromcitycode = fromcitycode;
    }

    public String getArrivecitycode() {
        return arrivecitycode;
    }

    public void setArrivecitycode(String arrivecitycode) {
        this.arrivecitycode = arrivecitycode;
    }

    public String getFromcityname() {
        return fromcityname;
    }

    public void setFromcityname(String fromcityname) {
        this.fromcityname = fromcityname;
    }

    public String getArrivecityname() {
        return arrivecityname;
    }

    public void setArrivecityname(String arrivecityname) {
        this.arrivecityname = arrivecityname;
    }

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(String orderLevel) {
        this.orderLevel = orderLevel;
    }

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getLinkEmail() {
        return linkEmail;
    }

    public void setLinkEmail(String linkEmail) {
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

    public int getTicketnum() {
        return ticketnum;
    }

    public void setTicketnum(int ticketnum) {
        this.ticketnum = ticketnum;
    }

    public String getCostname() {
        return costname;
    }

    public void setCostname(String costname) {
        this.costname = costname;
    }

    public int getCostid() {
        return costid;
    }

    public void setCostid(int costid) {
        this.costid = costid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public String getShenqingno() {
        return shenqingno;
    }

    public void setShenqingno(String shenqingno) {
        this.shenqingno = shenqingno;
    }

    public Object getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(Object ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getChailvitem() {
        return chailvitem;
    }

    public void setChailvitem(String chailvitem) {
        this.chailvitem = chailvitem;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public Object getBookUserId() {
        return bookUserId;
    }

    public void setBookUserId(Object bookUserId) {
        this.bookUserId = bookUserId;
    }

    public Object getBookUserName() {
        return bookUserName;
    }

    public void setBookUserName(Object bookUserName) {
        this.bookUserName = bookUserName;
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

    public int getOpDeptId() {
        return opDeptId;
    }

    public void setOpDeptId(int opDeptId) {
        this.opDeptId = opDeptId;
    }

    public String getOpDeptName() {
        return opDeptName;
    }

    public void setOpDeptName(String opDeptName) {
        this.opDeptName = opDeptName;
    }

    public Object getPeisong() {
        return peisong;
    }

    public void setPeisong(Object peisong) {
        this.peisong = peisong;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPeisongremark() {
        return peisongremark;
    }

    public void setPeisongremark(String peisongremark) {
        this.peisongremark = peisongremark;
    }

    public String getBookpolicy() {
        return bookpolicy;
    }

    public void setBookpolicy(String bookpolicy) {
        this.bookpolicy = bookpolicy;
    }

    public int getWeibeiflag() {
        return weibeiflag;
    }

    public void setWeibeiflag(int weibeiflag) {
        this.weibeiflag = weibeiflag;
    }

    public String getWbreason() {
        return wbreason;
    }

    public void setWbreason(String wbreason) {
        this.wbreason = wbreason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getApproveid() {
        return approveid;
    }

    public void setApproveid(int approveid) {
        this.approveid = approveid;
    }

    public int getApprovestatus() {
        return approvestatus;
    }

    public void setApprovestatus(int approvestatus) {
        this.approvestatus = approvestatus;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public Object getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(Object backStatus) {
        this.backStatus = backStatus;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public Long getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(Long lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public Object getFailReason() {
        return failReason;
    }

    public void setFailReason(Object failReason) {
        this.failReason = failReason;
    }

    public Object getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Object refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Object getRefundType() {
        return refundType;
    }

    public void setRefundType(Object refundType) {
        this.refundType = refundType;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public String getOutTicketBillno() {
        return outTicketBillno;
    }

    public void setOutTicketBillno(String outTicketBillno) {
        this.outTicketBillno = outTicketBillno;
    }

    public Object getOutTicketTime() {
        return outTicketTime;
    }

    public void setOutTicketTime(Object outTicketTime) {
        this.outTicketTime = outTicketTime;
    }

    public double getPayCharges() {
        return payCharges;
    }

    public void setPayCharges(double payCharges) {
        this.payCharges = payCharges;
    }

    public RouteBean getRoute() {
        return route;
    }

    public void setRoute(RouteBean route) {
        this.route = route;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public List<ApprovesBean> getApproves() {
        return approves;
    }

    public void setApproves(List<ApprovesBean> approves) {
        this.approves = approves;
    }

    public static class RouteBean implements Serializable {
        /**
         * id : 678
         * orderno : MDW112970272640710
         * orderid : 686
         * companyid : 1
         * fromStation : 北京南
         * fromStationCode : VNP
         * fromTime : 05:33
         * arriveStation : 上海虹桥
         * arriveStationCode : AOH
         * arriveTime : 13:38
         * costtime : 333
         * trainCode : G107
         * trainNo : 240000G1070D
         * travelTime : 2016-12-05
         * runTime : null
         * arriveDays : 0
         * createtime : 1480398384000
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

    public static class UsersBean implements Serializable {
        /**
         * id : 839
         * companyid : 1
         * orderno : MDW112970272640710
         * orderid : 686
         * userId : 108
         * userName : 测试谢小四
         * userPhone : 15244563553
         * userIds : 340881199202023317
         * bxPayMoney : 20.0
         * fuwufei : 20
         * ticketCharges : 0.8
         * ticketType : 1
         * idsType : 1
         * deptid : null
         * deptname : 业务部
         * amount : 933.0
         * piaohao : --
         * trainBox : null
         * seatNo : null
         * seatType : 一等座
         * seatCode : M
         * gaiqianstatus : 0
         * tuipiaostatus : 0
         * status : 0
         * createtime : 1480398384000
         * accountName :
         * accountPwd :
         * totalprice : 973.8
         * sort : 1
         */

        private int id;
        private int companyid;
        private String orderno;
        private int orderid;
        private int userId;
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
        private String piaohao;
        private Object trainBox;
        private Object seatNo;
        private String seatType;
        private String seatCode;
        private int gaiqianstatus;
        private int tuipiaostatus;
        private int status;
        private long createtime;
        private String accountName;
        private String accountPwd;
        private double totalprice;
        private int sort;
        private int trainpeison;
        private boolean isChecked;

        public int getTrainpeison() {
            return trainpeison;
        }

        public void setTrainpeison(int trainpeison) {
            this.trainpeison = trainpeison;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
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

        public String getPiaohao() {
            return piaohao;
        }

        public void setPiaohao(String piaohao) {
            this.piaohao = piaohao;
        }

        public Object getTrainBox() {
            return trainBox;
        }

        public void setTrainBox(Object trainBox) {
            this.trainBox = trainBox;
        }

        public Object getSeatNo() {
            return seatNo;
        }

        public void setSeatNo(Object seatNo) {
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

        public int getGaiqianstatus() {
            return gaiqianstatus;
        }

        public void setGaiqianstatus(int gaiqianstatus) {
            this.gaiqianstatus = gaiqianstatus;
        }

        public int getTuipiaostatus() {
            return tuipiaostatus;
        }

        public void setTuipiaostatus(int tuipiaostatus) {
            this.tuipiaostatus = tuipiaostatus;
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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }


}
