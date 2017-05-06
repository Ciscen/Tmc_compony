package com.auvgo.tmc.hotel.bean;

import com.auvgo.tmc.hotel.interfaces.IUserZhiWei;
import com.auvgo.tmc.train.bean.ApprovesBean;
import com.auvgo.tmc.views.MyPickerView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lc on 2017/2/27
 */

public class HotelOrderDetailBean implements Serializable {

    /**
     * id : 12
     * companyid : 35
     * companyname : 北京艾优薇文化科技有限公司
     * companycode : AUVGO
     * hoteltype : 0
     * orderfrom : 3
     * hotelfrom : elong
     * chuchaitype : 0
     * fuwufeetype : null
     * orderno : AUVGO0227401153
     * orderId : null
     * linkName : null
     * linkAddress : null
     * linkEmail : null
     * linkPhone : null
     * cityId : 0101
     * cityName : 北京
     * hotelId : 40101022
     * hotelName : 北京贝尔特酒店
     * hotelPhone : 0800-4863758
     * hotelAddress : 北京市东城区崇文门外大街3-18（毗邻天坛及北京游乐园）
     * roomTypeId : 1043
     * ratePlanId : 7814
     * arrivalDate : 1488124800000
     * departureDate : 1488211200000
     * latestArrivalTime : null
     * latestArrivalTimeString : null
     * customerIPAddress : 251854858
     * customerType : All
     * paymentType : SelfPay
     * numberOfRooms : 1
     * numberOfCustomers : 1
     * currencyCode : RMB
     * totalPrice : 428.0
     * isNeedGuarantee : false
     * guaranteeAmount : 0.0
     * cancelTime : null
     * confirmationType : null
     * noteToHotel : null
     * noteToElong : null
     * proid : null
     * proname : null
     * costid : null
     * costname : null
     * chailvitem : null
     * shenqingno : null
     * bookuserid : 131
     * bookusername : 王悦
     * bookdept : null
     * opuserid : null
     * opusername : null
     * opuserdept : null
     * approveid : 0
     * approvestatus : 3
     * status : 0
     * paystatus : 0
     * checkinstatus : null
     * weibeiflag : 0
     * wbreason :
     * bookpolicy : 一线城市/二线城市/三线城市/四线城市/
     * remark : null
     * createtime : 1488160743000
     * users : [{"id":15,"companyid":35,"orderno":"AUVGO0227401153","employeeid":131,"name":"王悦","zhiwei":1,"mobile":"18513772559","saleprice":408,"caigouprice":null,"fuwufee":0,"plusprice":null,"certtype":"1","certno":"410721199311240528","deptid":188,"deptname":null,"status":0,"createtime":1488160743000}]
     * approves : []
     */

    private int id;
    private int companyid;
    private String companyname;
    private String companycode;
    private int hoteltype;
    private int orderfrom;
    private String hotelfrom;
    private int chuchaitype;
    private String orderno;
    private String orderId;
    private String linkName;
    private String linkAddress;
    private String linkEmail;
    private String linkPhone;
    private String cityId;
    private String cityName;
    private String hotelId;
    private String hotelName;
    private String fuwufeetype;
    private double fuwufee;

    public double getFuwufee() {
        return fuwufee;
    }

    public void setFuwufee(double fuwufee) {
        this.fuwufee = fuwufee;
    }

    private String hotelPhone;
    private String hotelAddress;
    private String roomTypeId;
    private int ratePlanId;
    private long arrivalDate;
    private long departureDate;
    private String latestArrivalTime;
    private String latestArrivalTimeString;
    private String customerIPAddress;
    private String customerType;
    private String paymentType;
    private String payType;//支付方式，1欠款，2在线支付
    private int numberOfRooms;
    private int numberOfCustomers;
    private String currencyCode;
    private double totalPrice;
    private String isNeedGuarantee;
    private double guaranteeAmount;
    private String cancelTime;
    private String confirmationType;
    private String noteToHotel;
    private String noteToElong;
    private String proid;
    private String proname;
    private String costid;
    private String costname;
    private String chailvitem;
    private String shenqingno;
    private int bookuserid;
    private String bookusername;
    private String bookdept;
    private String opuserid;
    private String opusername;
    private String opuserdept;
    private int approveid;
    private int approvestatus;
    private int status;
    private int paystatus;
    private String checkinstatus;
    private int weibeiflag;
    private String wbreason;
    private String bookpolicy;
    private String remark;
    private long createtime;
    private List<UsersBean> users;
    private List<ApprovesBean> approves;
    /**
     * orderId : null
     * roomName : 贝尔特客房
     * ratePlanName : 含双早 (无线专卖)
     * latitude : 39.9049462
     * longitude : 116.4240017
     */
    private String roomName;
    private String ratePlanName;
    private String latitude;
    private String longitude;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRatePlanName() {
        return ratePlanName;
    }

    public void setRatePlanName(String ratePlanName) {
        this.ratePlanName = ratePlanName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public int getHoteltype() {
        return hoteltype;
    }

    public void setHoteltype(int hoteltype) {
        this.hoteltype = hoteltype;
    }

    public int getOrderfrom() {
        return orderfrom;
    }

    public void setOrderfrom(int orderfrom) {
        this.orderfrom = orderfrom;
    }

    public String getHotelfrom() {
        return hotelfrom;
    }

    public void setHotelfrom(String hotelfrom) {
        this.hotelfrom = hotelfrom;
    }

    public int getChuchaitype() {
        return chuchaitype;
    }

    public void setChuchaitype(int chuchaitype) {
        this.chuchaitype = chuchaitype;
    }

    public String getFuwufeetype() {
        return fuwufeetype;
    }

    public void setFuwufeetype(String fuwufeetype) {
        this.fuwufeetype = fuwufeetype;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
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

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
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

    public long getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public long getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(long departureDate) {
        this.departureDate = departureDate;
    }

    public String getLatestArrivalTime() {
        return latestArrivalTime;
    }

    public void setLatestArrivalTime(String latestArrivalTime) {
        this.latestArrivalTime = latestArrivalTime;
    }

    public String getLatestArrivalTimeString() {
        return latestArrivalTimeString;
    }

    public void setLatestArrivalTimeString(String latestArrivalTimeString) {
        this.latestArrivalTimeString = latestArrivalTimeString;
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

    public String getIsNeedGuarantee() {
        return isNeedGuarantee;
    }

    public void setIsNeedGuarantee(String isNeedGuarantee) {
        this.isNeedGuarantee = isNeedGuarantee;
    }

    public double getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(double guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getConfirmationType() {
        return confirmationType;
    }

    public void setConfirmationType(String confirmationType) {
        this.confirmationType = confirmationType;
    }

    public String getNoteToHotel() {
        return noteToHotel;
    }

    public void setNoteToHotel(String noteToHotel) {
        this.noteToHotel = noteToHotel;
    }

    public String getNoteToElong() {
        return noteToElong;
    }

    public void setNoteToElong(String noteToElong) {
        this.noteToElong = noteToElong;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getCostid() {
        return costid;
    }

    public void setCostid(String costid) {
        this.costid = costid;
    }

    public String getCostname() {
        return costname;
    }

    public void setCostname(String costname) {
        this.costname = costname;
    }

    public String getChailvitem() {
        return chailvitem;
    }

    public void setChailvitem(String chailvitem) {
        this.chailvitem = chailvitem;
    }

    public String getShenqingno() {
        return shenqingno;
    }

    public void setShenqingno(String shenqingno) {
        this.shenqingno = shenqingno;
    }

    public int getBookuserid() {
        return bookuserid;
    }

    public void setBookuserid(int bookuserid) {
        this.bookuserid = bookuserid;
    }

    public String getBookusername() {
        return bookusername;
    }

    public void setBookusername(String bookusername) {
        this.bookusername = bookusername;
    }

    public String getBookdept() {
        return bookdept;
    }

    public void setBookdept(String bookdept) {
        this.bookdept = bookdept;
    }

    public String getOpuserid() {
        return opuserid;
    }

    public void setOpuserid(String opuserid) {
        this.opuserid = opuserid;
    }

    public String getOpusername() {
        return opusername;
    }

    public void setOpusername(String opusername) {
        this.opusername = opusername;
    }

    public String getOpuserdept() {
        return opuserdept;
    }

    public void setOpuserdept(String opuserdept) {
        this.opuserdept = opuserdept;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(int paystatus) {
        this.paystatus = paystatus;
    }

    public String getCheckinstatus() {
        return checkinstatus;
    }

    public void setCheckinstatus(String checkinstatus) {
        this.checkinstatus = checkinstatus;
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

    public String getBookpolicy() {
        return bookpolicy;
    }

    public void setBookpolicy(String bookpolicy) {
        this.bookpolicy = bookpolicy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
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

    public static class UsersBean extends MyPickerView.Selection implements Serializable, IUserZhiWei {
        /**
         * id : 15
         * companyid : 35
         * orderno : AUVGO0227401153
         * employeeid : 131
         * name : 王悦
         * zhiwei : 1
         * mobile : 18513772559
         * saleprice : 408.0
         * caigouprice : null
         * fuwufee : 0
         * plusprice : null
         * certtype : 1
         * certno : 410721199311240528
         * deptid : 188
         * deptname : null
         * status : 0
         * createtime : 1488160743000
         */

        private int id;
        private int companyid;
        private String orderno;
        private int employeeid;
        private String name;
        private int zhiwei;
        private String mobile;
        private double saleprice;
        private String caigouprice;
        private double fuwufee;
        private String plusprice;
        private String certtype;
        private String certno;
        private int deptid;
        private String deptname;
        private int status;
        private long createtime;

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

        public int getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(int employeeid) {
            this.employeeid = employeeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getZhiwei() {
            return zhiwei;
        }

        public void setZhiwei(int zhiwei) {
            this.zhiwei = zhiwei;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(double saleprice) {
            this.saleprice = saleprice;
        }

        public String getCaigouprice() {
            return caigouprice;
        }

        public void setCaigouprice(String caigouprice) {
            this.caigouprice = caigouprice;
        }

        public double getFuwufee() {
            return fuwufee;
        }

        public void setFuwufee(double fuwufee) {
            this.fuwufee = fuwufee;
        }

        public String getPlusprice() {
            return plusprice;
        }

        public void setPlusprice(String plusprice) {
            this.plusprice = plusprice;
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

        public int getDeptid() {
            return deptid;
        }

        public void setDeptid(int deptid) {
            this.deptid = deptid;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
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

        @Override
        public String getZhiWei() {
            return zhiwei + "";
        }
    }
}
