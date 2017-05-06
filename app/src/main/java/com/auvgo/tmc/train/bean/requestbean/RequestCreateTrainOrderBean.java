package com.auvgo.tmc.train.bean.requestbean;

import java.util.List;

/**
 * Created by lc on 2016/11/18
 */

public class RequestCreateTrainOrderBean extends BaseRequestBean {
    private Long companyid;// 公司id
    private String orderLevel;// 订单级别 0普通订单 1vip订单（购买保险）
    private int orderFrom;// 0 后台白屏 1前台白屏 2ios 3 安卓
    private String linkAddress;// 联系地址
    private String linkEmail;// 联系邮件
    private String linkName;// 联系人姓名
    private String linkPhone;// 联系电话
    private String costname;// 成本中心名称
    private Long costid;// 成本中心id
    private String proname;// 项目名称
    private Long proid;// 项目id
    private String shenqingno;// 企业审批号
    private String chailvitem;// 差旅事项
    private Double totalprice;// 订单总额
    private Long bookUserId;// 预订人id
    private String bookUserName;// 预订姓名
    private String bookpolicy;//预订时差旅标准的字符串
    private int weibeiflag;//0正常订单1违背标准
    // -------------行程信息-----  ----------------//
    private String fromStation;// 出发站名称
    private String fromStationCode;// 出发站代码
    private String fromTime;// 出发时间
    private String arriveStation;// 终点站名称
    private String arriveStationCode;// 终点站代码
    private String arriveTime;// 到达时间
    private int costtime;// 运行分钟
    private String trainCode;// 车次
    private String trainNo;// 列车内部编码
    private String travelTime;// 出发时间 yyyy-MM-dd
    private String runTime;// 运行时间 07:47
    private java.lang.String fromcitycode;// 出发城市代码
    private java.lang.String arrivecitycode;// 到达城市代码
    private java.lang.String fromcityname;// 出发城市名称
    private java.lang.String arrivecityname;// 到达城市名称
    private String arriveDays;//到达时天数
    private String payType;
    private List<Order_psgBean> users;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

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

    public String getArriveDays() {
        return arriveDays;
    }

    public void setArriveDays(String arriveDays) {
        this.arriveDays = arriveDays;
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

    public Long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Long companyid) {
        this.companyid = companyid;
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

    public String getCostname() {
        return costname;
    }

    public void setCostname(String costname) {
        this.costname = costname;
    }

    public Long getCostid() {
        return costid;
    }

    public void setCostid(Long costid) {
        this.costid = costid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public Long getProid() {
        return proid;
    }

    public void setProid(Long proid) {
        this.proid = proid;
    }

    public String getShenqingno() {
        return shenqingno;
    }

    public void setShenqingno(String shenqingno) {
        this.shenqingno = shenqingno;
    }

    public String getChailvitem() {
        return chailvitem;
    }

    public void setChailvitem(String chailvitem) {
        this.chailvitem = chailvitem;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public Long getBookUserId() {
        return bookUserId;
    }

    public void setBookUserId(Long bookUserId) {
        this.bookUserId = bookUserId;
    }

    public String getBookUserName() {
        return bookUserName;
    }

    public void setBookUserName(String bookUserName) {
        this.bookUserName = bookUserName;
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

    public List<Order_psgBean> getUsers() {
        return users;
    }

    public void setUsers(List<Order_psgBean> users) {
        this.users = users;
    }


    public static class Order_psgBean {
        // -------------出行人信息----  -----------------//
        private String userName;// 姓名
        private String userPhone;// 电话
        private String userId;//id

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

        public String getUserIds() {
            return userIds;
        }

        public void setUserIds(String userIds) {
            this.userIds = userIds;
        }

        public Double getBxPayMoney() {
            return bxPayMoney;
        }

        public void setBxPayMoney(Double bxPayMoney) {
            this.bxPayMoney = bxPayMoney;
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

        public Long getDeptid() {
            return deptid;
        }

        public void setDeptid(Long deptid) {
            this.deptid = deptid;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
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

        public Double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(Double totalprice) {
            this.totalprice = totalprice;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        private String userIds;// 证件号码
        private Double bxPayMoney;// 保险费用,没有选择就是0元
        private int ticketType;// 车票类型： 1:成人；2:儿童；3:学生；4:残军
        private String idsType;// 证件类型：1:二代身份证；2:一代身份证；C:港澳通行证；G:台湾通行证；B:护照
        private Long deptid;// 部门id
        private String deptname;// 部门名称
        private Double amount;// 票价
        private String seatCode;// 座位代码 P,M,O
        private String seatType;// 0、商务座 1、特等座 2、一等座 3、二等座 4、高级软卧 5、软卧
        // 6、硬卧 7、软座 8、硬座 9、无座 10、其他
        private String accountName;// 12306账号
        private String accountPwd;// 12306密码
        private Double totalprice;// 单个乘客的费用合计,包括( 票价+订票费+服务费+保险)
        private int sort;// 乘客序号
        private int trainpeison;//配送费

        public int getTrainpeison() {
            return trainpeison;
        }

        public void setTrainpeison(int trainpeison) {
            this.trainpeison = trainpeison;
        }
    }
}
