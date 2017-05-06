package com.auvgo.tmc.hotel.bean;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

import java.util.List;

/**
 * Created by lc on 2017/2/24
 */

public class RequestCreateHotelOrderBean extends BaseRequestBean {

    private List<PassengerBean> users;
    private OrderBean order;

    public List<PassengerBean> getUsers() {
        return users;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public void setUsers(List<PassengerBean> users) {
        this.users = users;
    }

    public static class OrderBean {
        // -----------------------订单信息 order----------------------
        private long companyid;// 公司id
        private int chuchaitype;//0因公 1因私
        private int hoteltype;//   票种类型  0国内酒店  1国际酒店
        private int orderFrom;// 0后台白屏 1前台白屏 2ios 3 安卓
        private String hotelfrom;//艺龙elong还是行旅xinglv
        private String linkAddress;// 联系地址
        private String linkEmail;// 联系邮件
        private String linkName;// 联系人姓名
        private String linkPhone;// 联系电话
        private double totalprice;// 订单总额
        private long bookUserId;// 预订人id
        private String bookUserName;// 预订姓名
        private String bookpolicy;// 预订时的差旅标准
        private int weibeiflag;// 0正常订单 1违背标准
        private String wbreason;//违背原因
        private String cityId;// 酒店城市id
        private String cityName;// 酒店城市名称
        private String hotelId;// 酒店ID
        private String hotelName;// 酒店名称
        private String hotelPhone;// 酒店电话
        private String hotelAddress;// 酒店地址
        private String roomTypeId;// 房型ID,用于创建订单
        private int ratePlanId;// 产品ID
        private String arrivalDate;// 入住日期
        private String departureDate;// 离店日期
        private String customerIPAddress;// 用户IP
        private String customerType;// 客人类型
        private String paymentType;// SelfPay-前台自付、Prepay-预付
        private int numberOfRooms;// 房间数量
        private String payType;
        private int numberOfCustomers;// 客人数量
        private String currencyCode;// 货币类型
        private double totalPrice;// RatePlan的TotalPrice * 房间数
        private String latestArrivalTime;// 最晚入住时间 格式：yyyy-MM-dd hh:mm
        private boolean isNeedGuarantee;// 是否担保
        private Double guaranteeAmount;// 担保金额
        private int intervalDay;
        private String shenqingno;// 企业审批号

        public String getShenqingno() {
            return shenqingno;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public void setShenqingno(String shenqingno) {
            this.shenqingno = shenqingno;
        }

        public int getIntervalDay() {
            return intervalDay;
        }

        public void setIntervalDay(int intervalDay) {
            this.intervalDay = intervalDay;
        }

        public boolean isNeedGuarantee() {
            return isNeedGuarantee;
        }

        public void setNeedGuarantee(boolean needGuarantee) {
            isNeedGuarantee = needGuarantee;
        }

        public Double getGuaranteeAmount() {
            return guaranteeAmount;
        }

        public void setGuaranteeAmount(Double guaranteeAmount) {
            this.guaranteeAmount = guaranteeAmount;
        }

        public long getCompanyid() {
            return companyid;
        }

        public void setCompanyid(long companyid) {
            this.companyid = companyid;
        }

        public int getChuchaitype() {
            return chuchaitype;
        }

        public void setChuchaitype(int chuchaitype) {
            this.chuchaitype = chuchaitype;
        }

        public String getHotelfrom() {
            return hotelfrom;
        }

        public void setHotelfrom(String hotelfrom) {
            this.hotelfrom = hotelfrom;
        }

        public int getHoteltype() {
            return hoteltype;
        }

        public void setHoteltype(int hoteltype) {
            this.hoteltype = hoteltype;
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

        public double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(double totalprice) {
            this.totalprice = totalprice;
        }

        public long getBookUserId() {
            return bookUserId;
        }

        public void setBookUserId(long bookUserId) {
            this.bookUserId = bookUserId;
        }

        public String getBookUserName() {
            return bookUserName;
        }

        public void setBookUserName(String bookUserName) {
            this.bookUserName = bookUserName;
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

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getHotelId() {
            return hotelId;
        }

        public void setHotelId(String hotelId) {
            this.hotelId = hotelId;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getHotelPhone() {
            return hotelPhone;
        }

        public void setHotelPhone(String hotelPhone) {
            this.hotelPhone = hotelPhone;
        }

        public String getHotelAddress() {
            return hotelAddress;
        }

        public void setHotelAddress(String hotelAddress) {
            this.hotelAddress = hotelAddress;
        }

        public String getRoomTypeId() {
            return roomTypeId;
        }

        public void setRoomTypeId(String roomTypeId) {
            this.roomTypeId = roomTypeId;
        }

        public int getRatePlanId() {
            return ratePlanId;
        }

        public void setRatePlanId(int ratePlanId) {
            this.ratePlanId = ratePlanId;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public String getLatestArrivalTime() {
            return latestArrivalTime;
        }

        public void setLatestArrivalTime(String latestArrivalTime) {
            this.latestArrivalTime = latestArrivalTime;
        }

        public String getCustomerIPAddress() {
            return customerIPAddress;
        }

        public void setCustomerIPAddress(String customerIPAddress) {
            this.customerIPAddress = customerIPAddress;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public int getNumberOfRooms() {
            return numberOfRooms;
        }

        public void setNumberOfRooms(int numberOfRooms) {
            this.numberOfRooms = numberOfRooms;
        }

        public int getNumberOfCustomers() {
            return numberOfCustomers;
        }

        public void setNumberOfCustomers(int numberOfCustomers) {
            this.numberOfCustomers = numberOfCustomers;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }


    }

    public static class PassengerBean {
        private long employeeid;//乘机人id
        private String name;//乘机人姓名
        private String passtype;//AD 成人 CH 婴儿 IN 儿童
        private String mobile;//电话
        private String deptid;//部门id
        private String depname;//部门名称
        private String certtype;//证件类型
        private String certno;//证件号
        private Integer zhiwei;//员工职级
        private double saleprice;//销售价

        public long getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(long employeeid) {
            this.employeeid = employeeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPasstype() {
            return passtype;
        }

        public void setPasstype(String passtype) {
            this.passtype = passtype;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDeptid() {
            return deptid;
        }

        public void setDeptid(String deptid) {
            this.deptid = deptid;
        }

        public String getDepname() {
            return depname;
        }

        public void setDepname(String depname) {
            this.depname = depname;
        }

        public String getCerttype() {
            return certtype;
        }

        public void setCerttype(String certtype) {
            this.certtype = certtype;
        }

        public String getCertno() {
            return certno;
        }

        public void setCertno(String certno) {
            this.certno = certno;
        }

        public Integer getZhiwei() {
            return zhiwei;
        }

        public void setZhiwei(Integer zhiwei) {
            this.zhiwei = zhiwei;
        }

        public double getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(double saleprice) {
            this.saleprice = saleprice;
        }
    }
}
