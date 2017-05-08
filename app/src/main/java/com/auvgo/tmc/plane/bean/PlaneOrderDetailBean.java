package com.auvgo.tmc.plane.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.auvgo.tmc.approve.interfaces.IPlaneApprove;
import com.auvgo.tmc.approve.interfaces.IRouteBean;
import com.auvgo.tmc.hotel.interfaces.IUserZhiWei;
import com.auvgo.tmc.plane.interfaces.IPassenger;
import com.auvgo.tmc.train.bean.ApprovesBean;
import com.auvgo.tmc.utils.AppUtils;

import java.util.List;

/**
 * Created by lc on 2017/1/9
 */

public class PlaneOrderDetailBean implements Parcelable, IPlaneApprove {

    /**
     * id : 276
     * companyid : 35
     * companyname : 北京艾优薇文化科技有限公司
     * companycode : AUVGO
     * tickettype : 0
     * orderFrom : 3
     * orderno : AUVGO0221260056
     * bookoffice : null
     * chuchaitype : 0
     * ordertype : ow
     * pnr : ZERVN
     * pnrstatus : HK
     * bigPnr : null
     * linkName : 王悦
     * linkAddress :
     * linkEmail : wangyue@auvgo.com
     * linkPhone : 18513772559
     * totalticketprice : 1240.0
     * totalprice : 1380.0
     * proid : 0
     * proname :
     * costid : 0
     * costname :
     * chailvitem :
     * shenqingno :
     * chupiaotype : null
     * zanhuandate : null
     * zanhuantime : null
     * needbaoxiao : null
     * peisongaddr : null
     * remark : null
     * bookuserid : 131
     * bookusername : 王悦
     * bookdept : null
     * bookdepth : null
     * piaozhengType : null
     * opuserid : null
     * opusername : null
     * opuserdept : null
     * status : 1
     * approveid : null
     * approvestatus : 0
     * peisongstatus : null
     * paystatus : 0
     * weibeiflag : 1
     * wbreason : null
     * bookpolicy : 不得高于60折;需提前7天预订;前后2小时最低价
     * chupiaotime : null
     * createtime : 1487674490000
     * routes : [{"id":290,"companyid":35,"orderno":"AUVGO0221260056","orderid":276,"xuhao":0,"airline":"CZ6412","carriecode":"CZ","carriername":"南航","planestyle":"321","orgcode":"PEK","orgname":"首都国际机场","arricode":"SHA","arriname":"虹桥国际机场","arriterm":"T2","deptterm":"T2","deptdate":"2017-01-20","depttime":"06:40","arridate":"2017-01-20","arritime":"09:00","mealcode":"免费酒水饮料","code":"Q","codeDes":null,"stopnumber":"0","price":620,"rewardprice":0,"fueltax":0,"airporttax":50,"discount":55,"disdes":"55折","flytime":"02:20","yprice":0,"refundrule":"航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费","changerule":"航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费","signrule":"不得签转","status":0,"createtime":1487674490000,"orgcitycode":"BJS","orgcityname":"北京","dstcitycode":"SHA","dstcityname":"上海"}]
     * passengers : [{"id":380,"companyid":35,"orderid":276,"orderno":"AUVGO0221260056","employeeid":131,"name":"王悦","zhiwei":1,"passtype":"AD","mobile":"18513772559","certtype":"1","deptid":"188","depname":"技术一部","certno":"410721199311240528","totalprice":null,"bxPayMoney":null,"bxName":null,"bxCode":null,"peisongfee":null,"createtime":1487674490000},{"id":381,"companyid":35,"orderid":276,"orderno":"AUVGO0221260056","employeeid":127,"name":"梁晨晨","zhiwei":2,"passtype":"AD","mobile":"13521327074","certtype":"1","deptid":"139","depname":"运营部","certno":"130684199410091321","totalprice":null,"bxPayMoney":null,"bxName":null,"bxCode":null,"peisongfee":null,"createtime":1487674490000}]
     * approves : []
     * routePass : [{"id":159,"companyid":35,"orderno":"AUVGO0221260056","routeid":290,"passid":380,"caigouPrice":620,"rewardprice":0,"salePrice":620,"bxCode":"","bxName":"","bxPayMoney":0,"peisongfee":null,"price":620,"piaohao":null,"fuwufee":20,"fueltax":0,"airporttax":50,"totalprice":690,"gaiqianstatus":0,"tuipiaostatus":0,"createtime":1487674490000},{"id":160,"companyid":35,"orderno":"AUVGO0221260056","routeid":290,"passid":381,"caigouPrice":620,"rewardprice":0,"salePrice":620,"bxCode":"","bxName":"","bxPayMoney":0,"peisongfee":null,"price":620,"piaohao":null,"fuwufee":20,"fueltax":0,"airporttax":50,"totalprice":690,"gaiqianstatus":0,"tuipiaostatus":0,"createtime":1487674490000}]
     */

    private int id;
    private int companyid;
    private String companyname;
    private String companycode;
    private int tickettype;
    private int orderFrom;
    private String orderno;
    private String bookoffice;
    private int chuchaitype;
    private String ordertype;
    private String pnr;
    private String pnrstatus;
    private String bigPnr;
    private String linkName;
    private String linkAddress;
    private String linkEmail;
    private String linkPhone;
    private double totalticketprice;
    private double totalprice;
    private int proid;
    private String proname;
    private int costid;
    private String costname;
    private String chailvitem;
    private String shenqingno;
    private Object chupiaotype;
    private Object zanhuandate;
    private Object zanhuantime;
    private Object needbaoxiao;
    private Object peisongaddr;
    private Object remark;
    private int bookuserid;
    private String bookusername;
    private Object bookdept;
    private Object bookdepth;
    private Object piaozhengType;
    private Object opuserid;
    private Object opusername;
    private Object opuserdept;
    private int status;
    private String approveid;
    private int approvestatus;
    private String peisongstatus;
    private int paystatus;
    private int weibeiflag;
    private String wbreason;
    private String bookpolicy;
    private String chupiaotime;
    private long createtime;
    private String payType;
    private List<RoutesBean> routes;
    private List<PassengersBean> passengers;
    private List<ApprovesBean> approves;
    private List<RoutePassBean> routePass;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public int getTickettype() {
        return tickettype;
    }

    public void setTickettype(int tickettype) {
        this.tickettype = tickettype;
    }

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getBookoffice() {
        return bookoffice;
    }

    public void setBookoffice(String bookoffice) {
        this.bookoffice = bookoffice;
    }

    public int getChuchaitype() {
        return chuchaitype;
    }

    public void setChuchaitype(int chuchaitype) {
        this.chuchaitype = chuchaitype;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getPnrstatus() {
        return pnrstatus;
    }

    public void setPnrstatus(String pnrstatus) {
        this.pnrstatus = pnrstatus;
    }

    public String getBigPnr() {
        return bigPnr;
    }

    public void setBigPnr(String bigPnr) {
        this.bigPnr = bigPnr;
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

    public double getTotalticketprice() {
        return totalticketprice;
    }

    public void setTotalticketprice(double totalticketprice) {
        this.totalticketprice = totalticketprice;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public int getCostid() {
        return costid;
    }

    public void setCostid(int costid) {
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

    public Object getChupiaotype() {
        return chupiaotype;
    }

    public void setChupiaotype(Object chupiaotype) {
        this.chupiaotype = chupiaotype;
    }

    public Object getZanhuandate() {
        return zanhuandate;
    }

    public void setZanhuandate(Object zanhuandate) {
        this.zanhuandate = zanhuandate;
    }

    public Object getZanhuantime() {
        return zanhuantime;
    }

    public void setZanhuantime(Object zanhuantime) {
        this.zanhuantime = zanhuantime;
    }

    public Object getNeedbaoxiao() {
        return needbaoxiao;
    }

    public void setNeedbaoxiao(Object needbaoxiao) {
        this.needbaoxiao = needbaoxiao;
    }

    public Object getPeisongaddr() {
        return peisongaddr;
    }

    public void setPeisongaddr(Object peisongaddr) {
        this.peisongaddr = peisongaddr;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
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

    public Object getBookdept() {
        return bookdept;
    }

    public void setBookdept(Object bookdept) {
        this.bookdept = bookdept;
    }

    public Object getBookdepth() {
        return bookdepth;
    }

    public void setBookdepth(Object bookdepth) {
        this.bookdepth = bookdepth;
    }

    public Object getPiaozhengType() {
        return piaozhengType;
    }

    public void setPiaozhengType(Object piaozhengType) {
        this.piaozhengType = piaozhengType;
    }

    public Object getOpuserid() {
        return opuserid;
    }

    public void setOpuserid(Object opuserid) {
        this.opuserid = opuserid;
    }

    public Object getOpusername() {
        return opusername;
    }

    public void setOpusername(Object opusername) {
        this.opusername = opusername;
    }

    public Object getOpuserdept() {
        return opuserdept;
    }

    public void setOpuserdept(Object opuserdept) {
        this.opuserdept = opuserdept;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApproveid() {
        return approveid;
    }

    public void setApproveid(String approveid) {
        this.approveid = approveid;
    }

    public int getApprovestatus() {
        return approvestatus;
    }

    public void setApprovestatus(int approvestatus) {
        this.approvestatus = approvestatus;
    }

    public String getPeisongstatus() {
        return peisongstatus;
    }

    public void setPeisongstatus(String peisongstatus) {
        this.peisongstatus = peisongstatus;
    }

    public int getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(int paystatus) {
        this.paystatus = paystatus;
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

    public String getChupiaotime() {
        return chupiaotime;
    }

    public void setChupiaotime(String chupiaotime) {
        this.chupiaotime = chupiaotime;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public List<PassengersBean> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengersBean> passengers) {
        this.passengers = passengers;
    }

    public List<ApprovesBean> getApproves() {
        return approves;
    }

    public void setApproves(List<ApprovesBean> approves) {
        this.approves = approves;
    }

    public List<RoutePassBean> getRoutePass() {
        return routePass;
    }

    public void setRoutePass(List<RoutePassBean> routePass) {
        this.routePass = routePass;
    }

    @Override
    public String getTotalpriceI() {
        return String.valueOf(totalprice);
    }

    @Override
    public String getOrderNoI() {
        return orderno;
    }

    @Override
    public int getStatusI() {
        return status;
    }

    @Override
    public int getApprovestatusI() {
        return approvestatus;
    }

    @Override
    public String getContactI() {
        return linkName;
    }

    @Override
    public String getLinkPhoneI() {
        return linkPhone;
    }

    @Override
    public String getLinkEmailI() {
        return linkEmail;
    }

    @Override
    public List<? extends IPassenger> getPassengersI() {
        return passengers;
    }

    @Override
    public List<ApprovesBean> getApprovesI() {
        return approves;
    }

    @Override
    public String getShenqingNoI() {
        return shenqingno;
    }

    @Override
    public String getCostCenterI() {
        return costname;
    }

    @Override
    public String getPronameI() {
        return proname;
    }

    @Override
    public String getChailvitemI() {
        return chailvitem;
    }

    @Override
    public String getBookPolicyI() {
        return bookpolicy;
    }

    @Override
    public String getWBReasonI() {
        return wbreason;
    }

    @Override
    public IRouteBean getRoute() {
        return routes.get(0);
    }

    @Override
    public String getBxName() {
        return routePass.get(0).getBxName();
    }


    public static class RoutesBean implements Parcelable, IRouteBean {
        /**
         * id : 290
         * companyid : 35
         * orderno : AUVGO0221260056
         * orderid : 276
         * xuhao : 0
         * airline : CZ6412
         * carriecode : CZ
         * carriername : 南航
         * planestyle : 321
         * orgcode : PEK
         * orgname : 首都国际机场
         * arricode : SHA
         * arriname : 虹桥国际机场
         * arriterm : T2
         * deptterm : T2
         * deptdate : 2017-01-20
         * depttime : 06:40
         * arridate : 2017-01-20
         * arritime : 09:00
         * mealcode : 免费酒水饮料
         * code : Q
         * codeDes : null
         * stopnumber : 0
         * price : 620.0
         * rewardprice : 0.0
         * fueltax : 0
         * airporttax : 50
         * discount : 55.0
         * disdes : 55折
         * flytime : 02:20
         * yprice : 0.0
         * refundrule : 航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费
         * changerule : 航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费
         * signrule : 不得签转
         * status : 0
         * createtime : 1487674490000
         * orgcitycode : BJS
         * orgcityname : 北京
         * dstcitycode : SHA
         * dstcityname : 上海
         */

        private int id;
        private int companyid;
        private String orderno;
        private int orderid;
        private int xuhao;
        private String airline;
        private String carriecode;
        private String carriername;
        private String planestyle;
        private String orgcode;
        private String orgname;
        private String arricode;
        private String arriname;
        private String arriterm;
        private String deptterm;
        private String deptdate;
        private String depttime;
        private String arridate;
        private String arritime;
        private String mealcode;
        private String code;
        private String codeDes;
        private String stopnumber;
        private double price;
        private double rewardprice;
        private double fueltax;
        private double airporttax;
        private double discount;
        private String disdes;
        private String flytime;
        private double yprice;
        private String refundrule;
        private String changerule;
        private String signrule;
        private int status;
        private long createtime;
        private String orgcitycode;
        private String orgcityname;
        private String dstcitycode;
        private String dstcityname;

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

        public int getXuhao() {
            return xuhao;
        }

        public void setXuhao(int xuhao) {
            this.xuhao = xuhao;
        }

        public String getAirline() {
            return airline;
        }

        public void setAirline(String airline) {
            this.airline = airline;
        }

        public String getCarriecode() {
            return carriecode;
        }

        public void setCarriecode(String carriecode) {
            this.carriecode = carriecode;
        }

        public String getCarriername() {
            return carriername;
        }

        public void setCarriername(String carriername) {
            this.carriername = carriername;
        }

        public String getPlanestyle() {
            return planestyle;
        }

        public void setPlanestyle(String planestyle) {
            this.planestyle = planestyle;
        }

        public String getOrgcode() {
            return orgcode;
        }

        public void setOrgcode(String orgcode) {
            this.orgcode = orgcode;
        }

        public String getOrgname() {
            return orgname + deptterm;
        }

        public void setOrgname(String orgname) {
            this.orgname = orgname;
        }

        public String getArricode() {
            return arricode;
        }

        public void setArricode(String arricode) {
            this.arricode = arricode;
        }

        public String getArriname() {
            return arriname + arriterm;
        }

        public void setArriname(String arriname) {
            this.arriname = arriname;
        }

        public String getArriterm() {
            return arriterm;
        }

        public void setArriterm(String arriterm) {
            this.arriterm = arriterm;
        }

        public String getDeptterm() {
            return deptterm;
        }

        public void setDeptterm(String deptterm) {
            this.deptterm = deptterm;
        }

        public String getDeptdate() {
            return deptdate;
        }

        public void setDeptdate(String deptdate) {
            this.deptdate = deptdate;
        }

        public String getDepttime() {
            return depttime;
        }

        public void setDepttime(String depttime) {
            this.depttime = depttime;
        }

        public String getArridate() {
            return arridate;
        }

        public void setArridate(String arridate) {
            this.arridate = arridate;
        }

        public String getArritime() {
            return arritime;
        }

        public void setArritime(String arritime) {
            this.arritime = arritime;
        }

        public String getMealcode() {
            return mealcode;
        }

        public void setMealcode(String mealcode) {
            this.mealcode = mealcode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeDes() {
            return AppUtils.getNoNullStr(codeDes);
        }

        public void setCodeDes(String codeDes) {
            this.codeDes = codeDes;
        }

        public String getStopnumber() {
            return stopnumber;
        }

        public void setStopnumber(String stopnumber) {
            this.stopnumber = stopnumber;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getRewardprice() {
            return rewardprice;
        }

        public void setRewardprice(double rewardprice) {
            this.rewardprice = rewardprice;
        }

        public double getFueltax() {
            return fueltax;
        }

        public void setFueltax(double fueltax) {
            this.fueltax = fueltax;
        }

        public double getAirporttax() {
            return airporttax;
        }

        public void setAirporttax(double airporttax) {
            this.airporttax = airporttax;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public String getDisdes() {
            return disdes;
        }

        public void setDisdes(String disdes) {
            this.disdes = disdes;
        }

        public String getFlytime() {
            return flytime;
        }

        public void setFlytime(String flytime) {
            this.flytime = flytime;
        }

        public double getYprice() {
            return yprice;
        }

        public void setYprice(double yprice) {
            this.yprice = yprice;
        }

        public String getRefundrule() {
            return refundrule;
        }

        public void setRefundrule(String refundrule) {
            this.refundrule = refundrule;
        }

        public String getChangerule() {
            return changerule;
        }

        public void setChangerule(String changerule) {
            this.changerule = changerule;
        }

        public String getSignrule() {
            return signrule;
        }

        public void setSignrule(String signrule) {
            this.signrule = signrule;
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

        public String getOrgcitycode() {
            return orgcitycode;
        }

        public void setOrgcitycode(String orgcitycode) {
            this.orgcitycode = orgcitycode;
        }

        public String getOrgcityname() {
            return orgcityname;
        }

        public void setOrgcityname(String orgcityname) {
            this.orgcityname = orgcityname;
        }

        public String getDstcitycode() {
            return dstcitycode;
        }

        public void setDstcitycode(String dstcitycode) {
            this.dstcitycode = dstcitycode;
        }

        public String getDstcityname() {
            return dstcityname;
        }

        public void setDstcityname(String dstcityname) {
            this.dstcityname = dstcityname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.companyid);
            dest.writeString(this.orderno);
            dest.writeInt(this.orderid);
            dest.writeInt(this.xuhao);
            dest.writeString(this.airline);
            dest.writeString(this.carriecode);
            dest.writeString(this.carriername);
            dest.writeString(this.planestyle);
            dest.writeString(this.orgcode);
            dest.writeString(this.orgname);
            dest.writeString(this.arricode);
            dest.writeString(this.arriname);
            dest.writeString(this.arriterm);
            dest.writeString(this.deptterm);
            dest.writeString(this.deptdate);
            dest.writeString(this.depttime);
            dest.writeString(this.arridate);
            dest.writeString(this.arritime);
            dest.writeString(this.mealcode);
            dest.writeString(this.code);
            dest.writeString(this.codeDes);
            dest.writeString(this.stopnumber);
            dest.writeDouble(this.price);
            dest.writeDouble(this.rewardprice);
            dest.writeDouble(this.fueltax);
            dest.writeDouble(this.airporttax);
            dest.writeDouble(this.discount);
            dest.writeString(this.disdes);
            dest.writeString(this.flytime);
            dest.writeDouble(this.yprice);
            dest.writeString(this.refundrule);
            dest.writeString(this.changerule);
            dest.writeString(this.signrule);
            dest.writeInt(this.status);
            dest.writeLong(this.createtime);
            dest.writeString(this.orgcitycode);
            dest.writeString(this.orgcityname);
            dest.writeString(this.dstcitycode);
            dest.writeString(this.dstcityname);
        }

        public RoutesBean() {
        }

        protected RoutesBean(Parcel in) {
            this.id = in.readInt();
            this.companyid = in.readInt();
            this.orderno = in.readString();
            this.orderid = in.readInt();
            this.xuhao = in.readInt();
            this.airline = in.readString();
            this.carriecode = in.readString();
            this.carriername = in.readString();
            this.planestyle = in.readString();
            this.orgcode = in.readString();
            this.orgname = in.readString();
            this.arricode = in.readString();
            this.arriname = in.readString();
            this.arriterm = in.readString();
            this.deptterm = in.readString();
            this.deptdate = in.readString();
            this.depttime = in.readString();
            this.arridate = in.readString();
            this.arritime = in.readString();
            this.mealcode = in.readString();
            this.code = in.readString();
            this.codeDes = in.readString();
            this.stopnumber = in.readString();
            this.price = in.readDouble();
            this.rewardprice = in.readDouble();
            this.fueltax = in.readDouble();
            this.airporttax = in.readDouble();
            this.discount = in.readDouble();
            this.disdes = in.readString();
            this.flytime = in.readString();
            this.yprice = in.readDouble();
            this.refundrule = in.readString();
            this.changerule = in.readString();
            this.signrule = in.readString();
            this.status = in.readInt();
            this.createtime = in.readLong();
            this.orgcitycode = in.readString();
            this.orgcityname = in.readString();
            this.dstcitycode = in.readString();
            this.dstcityname = in.readString();
        }

        public static final Creator<RoutesBean> CREATOR = new Creator<RoutesBean>() {
            @Override
            public RoutesBean createFromParcel(Parcel source) {
                return new RoutesBean(source);
            }

            @Override
            public RoutesBean[] newArray(int size) {
                return new RoutesBean[size];
            }
        };
    }

    public static class PassengersBean implements Parcelable, IUserZhiWei, IPassenger {
        /**
         * id : 380
         * companyid : 35
         * orderid : 276
         * orderno : AUVGO0221260056
         * employeeid : 131
         * name : 王悦
         * zhiwei : 1
         * passtype : AD
         * mobile : 18513772559
         * certtype : 1
         * deptid : 188
         * depname : 技术一部
         * certno : 410721199311240528
         * totalprice : null
         * bxPayMoney : null
         * bxName : null
         * bxCode : null
         * peisongfee : null
         * createtime : 1487674490000
         */

        private int id;
        private int companyid;
        private int orderid;
        private String orderno;
        private int employeeid;
        private String name;
        private int zhiwei;
        private String passtype;
        private String mobile;
        private String certtype;
        private String deptid;
        private String depname;
        private String certno;
        private String totalprice;
        private String bxPayMoney;
        private String bxName;
        private String bxCode;
        private String peisongfee;
        private long createtime;

        private boolean isChecked;

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

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
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

        public String getCerttype() {
            return certtype;
        }

        public void setCerttype(String certtype) {
            this.certtype = certtype;
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

        public String getCertno() {
            return certno;
        }

        public void setCertno(String certno) {
            this.certno = certno;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(String totalprice) {
            this.totalprice = totalprice;
        }

        public String getBxPayMoney() {
            return bxPayMoney;
        }

        public void setBxPayMoney(String bxPayMoney) {
            this.bxPayMoney = bxPayMoney;
        }

        public String getBxName() {
            return bxName;
        }

        public void setBxName(String bxName) {
            this.bxName = bxName;
        }

        public String getBxCode() {
            return bxCode;
        }

        public void setBxCode(String bxCode) {
            this.bxCode = bxCode;
        }

        public String getPeisongfee() {
            return peisongfee;
        }

        public void setPeisongfee(String peisongfee) {
            this.peisongfee = peisongfee;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.companyid);
            dest.writeInt(this.orderid);
            dest.writeString(this.orderno);
            dest.writeInt(this.employeeid);
            dest.writeString(this.name);
            dest.writeInt(this.zhiwei);
            dest.writeString(this.passtype);
            dest.writeString(this.mobile);
            dest.writeString(this.certtype);
            dest.writeString(this.deptid);
            dest.writeString(this.depname);
            dest.writeString(this.certno);
            dest.writeString(this.totalprice);
            dest.writeString(this.bxPayMoney);
            dest.writeString(this.bxName);
            dest.writeString(this.bxCode);
            dest.writeString(this.peisongfee);
            dest.writeLong(this.createtime);
            dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        }

        public PassengersBean() {
        }

        protected PassengersBean(Parcel in) {
            this.id = in.readInt();
            this.companyid = in.readInt();
            this.orderid = in.readInt();
            this.orderno = in.readString();
            this.employeeid = in.readInt();
            this.name = in.readString();
            this.zhiwei = in.readInt();
            this.passtype = in.readString();
            this.mobile = in.readString();
            this.certtype = in.readString();
            this.deptid = in.readString();
            this.depname = in.readString();
            this.certno = in.readString();
            this.totalprice = in.readString();
            this.bxPayMoney = in.readString();
            this.bxName = in.readString();
            this.bxCode = in.readString();
            this.peisongfee = in.readString();
            this.createtime = in.readLong();
            this.isChecked = in.readByte() != 0;
        }

        public static final Creator<PassengersBean> CREATOR = new Creator<PassengersBean>() {
            @Override
            public PassengersBean createFromParcel(Parcel source) {
                return new PassengersBean(source);
            }

            @Override
            public PassengersBean[] newArray(int size) {
                return new PassengersBean[size];
            }
        };

        @Override
        public String getZhiWei() {
            return zhiwei + "";
        }

        @Override
        public int getIdI() {
            return id;
        }

        @Override
        public String getCernoI() {
            return certno;
        }

        @Override
        public String getNameI() {
            return name;
        }
    }

    public static class RoutePassBean implements Parcelable {
        /**
         * id : 159
         * companyid : 35
         * orderno : AUVGO0221260056
         * routeid : 290
         * passid : 380
         * caigouPrice : 620.0
         * rewardprice : 0.0
         * salePrice : 620.0
         * bxCode :
         * bxName :
         * bxPayMoney : 0.0
         * peisongfee : null
         * price : 620.0
         * piaohao : null
         * fuwufee : 20.0
         * fueltax : 0.0
         * airporttax : 50.0
         * totalprice : 690.0
         * gaiqianstatus : 0
         * tuipiaostatus : 0
         * createtime : 1487674490000
         */

        private int id;
        private int companyid;
        private String orderno;
        private int routeid;
        private int passid;
        private double caigouPrice;
        private double rewardprice;
        private double salePrice;
        private String bxCode;
        private String bxName;
        private double bxPayMoney;
        private double price;
        private String piaohao;
        private double fuwufee;
        private double fueltax;
        private double airporttax;
        private double totalprice;
        private int gaiqianstatus;
        private int tuipiaostatus;
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

        public int getRouteid() {
            return routeid;
        }

        public void setRouteid(int routeid) {
            this.routeid = routeid;
        }

        public int getPassid() {
            return passid;
        }

        public void setPassid(int passid) {
            this.passid = passid;
        }

        public double getCaigouPrice() {
            return caigouPrice;
        }

        public void setCaigouPrice(double caigouPrice) {
            this.caigouPrice = caigouPrice;
        }

        public double getRewardprice() {
            return rewardprice;
        }

        public void setRewardprice(double rewardprice) {
            this.rewardprice = rewardprice;
        }

        public double getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(double salePrice) {
            this.salePrice = salePrice;
        }

        public String getBxCode() {
            return bxCode;
        }

        public void setBxCode(String bxCode) {
            this.bxCode = bxCode;
        }

        public String getBxName() {
            return bxName;
        }

        public void setBxName(String bxName) {
            this.bxName = bxName;
        }

        public double getBxPayMoney() {
            return bxPayMoney;
        }

        public void setBxPayMoney(double bxPayMoney) {
            this.bxPayMoney = bxPayMoney;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPiaohao() {
            return piaohao;
        }

        public void setPiaohao(String piaohao) {
            this.piaohao = piaohao;
        }

        public double getFuwufee() {
            return fuwufee;
        }

        public void setFuwufee(double fuwufee) {
            this.fuwufee = fuwufee;
        }

        public double getFueltax() {
            return fueltax;
        }

        public void setFueltax(double fueltax) {
            this.fueltax = fueltax;
        }

        public double getAirporttax() {
            return airporttax;
        }

        public void setAirporttax(double airporttax) {
            this.airporttax = airporttax;
        }

        public double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(double totalprice) {
            this.totalprice = totalprice;
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

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.companyid);
            dest.writeString(this.orderno);
            dest.writeInt(this.routeid);
            dest.writeInt(this.passid);
            dest.writeDouble(this.caigouPrice);
            dest.writeDouble(this.rewardprice);
            dest.writeDouble(this.salePrice);
            dest.writeString(this.bxCode);
            dest.writeString(this.bxName);
            dest.writeDouble(this.bxPayMoney);
            dest.writeDouble(this.price);
            dest.writeString(this.piaohao);
            dest.writeDouble(this.fuwufee);
            dest.writeDouble(this.fueltax);
            dest.writeDouble(this.airporttax);
            dest.writeDouble(this.totalprice);
            dest.writeInt(this.gaiqianstatus);
            dest.writeInt(this.tuipiaostatus);
            dest.writeLong(this.createtime);
        }

        public RoutePassBean() {
        }

        protected RoutePassBean(Parcel in) {
            this.id = in.readInt();
            this.companyid = in.readInt();
            this.orderno = in.readString();
            this.routeid = in.readInt();
            this.passid = in.readInt();
            this.caigouPrice = in.readDouble();
            this.rewardprice = in.readDouble();
            this.salePrice = in.readDouble();
            this.bxCode = in.readString();
            this.bxName = in.readString();
            this.bxPayMoney = in.readDouble();
            this.price = in.readDouble();
            this.piaohao = in.readString();
            this.fuwufee = in.readDouble();
            this.fueltax = in.readDouble();
            this.airporttax = in.readDouble();
            this.totalprice = in.readDouble();
            this.gaiqianstatus = in.readInt();
            this.tuipiaostatus = in.readInt();
            this.createtime = in.readLong();
        }

        public static final Creator<RoutePassBean> CREATOR = new Creator<RoutePassBean>() {
            @Override
            public RoutePassBean createFromParcel(Parcel source) {
                return new RoutePassBean(source);
            }

            @Override
            public RoutePassBean[] newArray(int size) {
                return new RoutePassBean[size];
            }
        };
    }

//    public static class ApprovesBean {
//
//        /**
//         * id : 319
//         * companyid : 35
//         * orderno : AUVGO0221337784
//         * employeeid : 131
//         * name : 王悦
//         * deptname : null
//         * level : 1
//         * mobile : 18513772559
//         * email : wangyue@auvgo.com
//         * openid : null
//         * status : 0
//         * yuanyin : null
//         * approvetime : null
//         * createtime : 1487671646000
//         */
//
//        private int id;
//        private int companyid;
//        private String orderno;
//        private int employeeid;
//        private String name;
//        private Object deptname;
//        private int level;
//        private String mobile;
//        private String email;
//        private Object openid;
//        private int status;
//        private Object yuanyin;
//        private Object approvetime;
//        private long createtime;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getCompanyid() {
//            return companyid;
//        }
//
//        public void setCompanyid(int companyid) {
//            this.companyid = companyid;
//        }
//
//        public String getOrderno() {
//            return orderno;
//        }
//
//        public void setOrderno(String orderno) {
//            this.orderno = orderno;
//        }
//
//        public int getEmployeeid() {
//            return employeeid;
//        }
//
//        public void setEmployeeid(int employeeid) {
//            this.employeeid = employeeid;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public Object getDeptname() {
//            return deptname;
//        }
//
//        public void setDeptname(Object deptname) {
//            this.deptname = deptname;
//        }
//
//        public int getLevel() {
//            return level;
//        }
//
//        public void setLevel(int level) {
//            this.level = level;
//        }
//
//        public String getMobile() {
//            return mobile;
//        }
//
//        public void setMobile(String mobile) {
//            this.mobile = mobile;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public Object getOpenid() {
//            return openid;
//        }
//
//        public void setOpenid(Object openid) {
//            this.openid = openid;
//        }
//
//        public int getIsdefault() {
//            return status;
//        }
//
//        public void setIsdefault(int status) {
//            this.status = status;
//        }
//
//        public Object getYuanyin() {
//            return yuanyin;
//        }
//
//        public void setYuanyin(Object yuanyin) {
//            this.yuanyin = yuanyin;
//        }
//
//        public Object getApprovetime() {
//            return approvetime;
//        }
//
//        public void setApprovetime(Object approvetime) {
//            this.approvetime = approvetime;
//        }
//
//        public long getCreatetime() {
//            return createtime;
//        }
//
//        public void setCreatetime(long createtime) {
//            this.createtime = createtime;
//        }
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.companyid);
        dest.writeString(this.companyname);
        dest.writeString(this.companycode);
        dest.writeInt(this.tickettype);
        dest.writeInt(this.orderFrom);
        dest.writeString(this.orderno);
        dest.writeString(this.bookoffice);
        dest.writeInt(this.chuchaitype);
        dest.writeString(this.ordertype);
        dest.writeString(this.pnr);
        dest.writeString(this.pnrstatus);
        dest.writeString(this.bigPnr);
        dest.writeString(this.linkName);
        dest.writeString(this.linkAddress);
        dest.writeString(this.linkEmail);
        dest.writeString(this.linkPhone);
        dest.writeDouble(this.totalticketprice);
        dest.writeDouble(this.totalprice);
        dest.writeInt(this.proid);
        dest.writeString(this.proname);
        dest.writeInt(this.costid);
        dest.writeString(this.costname);
        dest.writeString(this.chailvitem);
        dest.writeString(this.shenqingno);
        dest.writeInt(this.bookuserid);
        dest.writeString(this.bookusername);
        dest.writeInt(this.status);
        dest.writeString(this.approveid);
        dest.writeInt(this.approvestatus);
        dest.writeString(this.peisongstatus);
        dest.writeInt(this.paystatus);
        dest.writeInt(this.weibeiflag);
        dest.writeString(this.wbreason);
        dest.writeString(this.bookpolicy);
        dest.writeString(this.chupiaotime);
        dest.writeLong(this.createtime);
        dest.writeTypedList(this.routes);
        dest.writeTypedList(this.passengers);
        dest.writeTypedList(this.approves);
        dest.writeTypedList(this.routePass);
        dest.writeString(this.payType);
    }

    public PlaneOrderDetailBean() {
    }

    protected PlaneOrderDetailBean(Parcel in) {
        this.id = in.readInt();
        this.companyid = in.readInt();
        this.companyname = in.readString();
        this.companycode = in.readString();
        this.tickettype = in.readInt();
        this.orderFrom = in.readInt();
        this.orderno = in.readString();
        this.bookoffice = in.readString();
        this.chuchaitype = in.readInt();
        this.ordertype = in.readString();
        this.pnr = in.readString();
        this.pnrstatus = in.readString();
        this.bigPnr = in.readString();
        this.linkName = in.readString();
        this.linkAddress = in.readString();
        this.linkEmail = in.readString();
        this.linkPhone = in.readString();
        this.totalticketprice = in.readDouble();
        this.totalprice = in.readDouble();
        this.proid = in.readInt();
        this.proname = in.readString();
        this.costid = in.readInt();
        this.costname = in.readString();
        this.chailvitem = in.readString();
        this.shenqingno = in.readString();
        this.bookuserid = in.readInt();
        this.bookusername = in.readString();
        this.status = in.readInt();
        this.approveid = in.readString();
        this.approvestatus = in.readInt();
        this.peisongstatus = in.readString();
        this.paystatus = in.readInt();
        this.weibeiflag = in.readInt();
        this.wbreason = in.readString();
        this.bookpolicy = in.readString();
        this.chupiaotime = in.readString();
        this.createtime = in.readLong();
        this.routes = in.createTypedArrayList(RoutesBean.CREATOR);
        this.passengers = in.createTypedArrayList(PassengersBean.CREATOR);
        this.approves = in.createTypedArrayList(ApprovesBean.CREATOR);
        this.routePass = in.createTypedArrayList(RoutePassBean.CREATOR);
        this.payType = in.readString();

    }

    public static final Creator<PlaneOrderDetailBean> CREATOR = new Creator<PlaneOrderDetailBean>() {
        @Override
        public PlaneOrderDetailBean createFromParcel(Parcel source) {
            return new PlaneOrderDetailBean(source);
        }

        @Override
        public PlaneOrderDetailBean[] newArray(int size) {
            return new PlaneOrderDetailBean[size];
        }
    };
}
