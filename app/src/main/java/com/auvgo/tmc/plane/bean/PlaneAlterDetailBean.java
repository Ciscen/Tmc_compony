package com.auvgo.tmc.plane.bean;

import com.auvgo.tmc.approve.interfaces.IPlaneApprove;
import com.auvgo.tmc.approve.interfaces.IRouteBean;
import com.auvgo.tmc.plane.interfaces.IPassenger;
import com.auvgo.tmc.train.bean.ApprovesBean;
import com.auvgo.tmc.utils.AppUtils;

import java.util.List;

/**
 * Created by lc on 2017/1/19
 */

public class PlaneAlterDetailBean implements IPlaneApprove {
    /**
     * id : 23
     * oldorderno : AUVGO0118508743
     * gqorderno : GQ0119076588
     * companyid : 35
     * companycode : AUVGO
     * companyname : 北京艾优薇文化科技有限公司
     * tjuserid : 131
     * tjusername : 王悦
     * orderfrom : 2
     * opuserid : null
     * opusername : null
     * approveid : 52
     * approvestatus : 1
     * gqreason : null
     * status : 0
     * paystatus : 0
     * createtime : 1484804553000
     * linkName : null
     * linkAddress :
     * linkEmail :
     * linkPhone :
     * oldroutes : [{"id":161,"companyid":35,"orderno":"AUVGO0118508743","orderid":null,"xuhao":0,"airline":"CA3202","carriecode":"CA","carriername":"国航","planestyle":"321","orgcode":"PEK","orgname":"首都国际机场","arricode":"SHA","arriname":"虹桥国际机场","arriterm":"T2","deptterm":"T3","deptdate":"2017-01-20","depttime":"06:35","arridate":"2017-01-20","arritime":"08:50","mealcode":"快餐","code":"Y","codeDes":null,"stopnumber":"0","price":1380,"rewardprice":0,"fueltax":0,"airporttax":50,"discount":100,"disdes":"100折","flytime":"02:15","yprice":0,"refundrule":"航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费","changerule":"航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费","signrule":"不得签转","status":0,"createtime":1484731131000,"orgcitycode":"BJS","orgcityname":"北京","dstcitycode":"SHA","dstcityname":"上海"}]
     * routes : [{"id":7,"routeid":null,"companyid":35,"gqorderno":"GQ0119076588","oldorderno":"AUVGO0118508743","xuhao":0,"airline":"MU3662","carriecode":"MU","carriername":"东航","planestyle":"321","orgcode":"PEK","orgname":"首都国际机场","arricode":"SHA","arriname":"虹桥国际机场","arriterm":"T2","deptterm":"T2","deptdate":"2017-01-20","depttime":"06:40","arridate":"2017-01-20","arritime":"09:00","mealcode":"免费酒水饮料","code":"Y","codeDes":"全价经济舱","stopnumber":"0","price":1130,"rewardprice":0,"fueltax":0,"airporttax":50,"discount":100,"disdes":"100折","flytime":"02:20","yprice":0,"refundrule":"航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费","changerule":"航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费","signrule":"不得签转","status":0,"createtime":1484804554000}]
     * passengers : [{"id":11,"passid":null,"oldorderno":"AUVGO0118508743","gqorderno":"GQ0119076588","gqorderid":145,"companyid":35,"name":"王悦","employeeid":131,"certype":null,"certno":"410721199311240528","caigouPrice":null,"rewardprice":null,"salePrice":null,"bxPayMoney":null,"peisongfee":null,"price":null,"fueltax":null,"airporttaxt":null,"fuwufee":null,"gqprice":null,"status":0,"createtime":1484804554000}]
     * approves : []
     */

    private int id;
    private String oldorderno;
    private String gqorderno;
    private int companyid;
    private String companycode;
    private String companyname;
    private int tjuserid;
    private String tjusername;
    private int orderfrom;
    private Object opuserid;
    private Object opusername;
    private int approveid;


    private int approvestatus;
    private Object gqreason;
    private int status;
    private double tuiCharges;
    private double oldTotalPrice;
    private double gaiqianTotalPrice;

    private int paystatus;
    private long createtime;
    private String linkName;
    private String linkAddress;
    private String linkEmail;
    private String linkPhone;
    private List<OldroutesBean> oldroutes;
    private List<RoutesBean> routes;
    private List<PassengersBean> passengers;
    private List<ApprovesBean> approves;

    private OrderPaymentBean orderPayment;

    public PlaneAlterDetailBean() {
    }

    public OrderPaymentBean getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(OrderPaymentBean orderPayment) {
        this.orderPayment = orderPayment;
    }

    public double getTuiCharges() {
        return tuiCharges;
    }

    public double getOldTotalPrice() {
        return oldTotalPrice;
    }

    public double getGaiqianTotalPrice() {
        return gaiqianTotalPrice;
    }

    public void setGaiqianTotalPrice(double gaiqianTotalPrice) {
        this.gaiqianTotalPrice = gaiqianTotalPrice;
    }

    public void setOldTotalPrice(double oldTotalPrice) {
        this.oldTotalPrice = oldTotalPrice;
    }

    public void setTuiCharges(double tuiCharges) {
        this.tuiCharges = tuiCharges;
    }

    @Override
    public String toString() {
        return "PlaneAlterDetailBean{" +
                "id=" + id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldorderno() {
        return oldorderno;
    }

    public void setOldorderno(String oldorderno) {
        this.oldorderno = oldorderno;
    }

    public String getGqorderno() {
        return gqorderno;
    }

    public void setGqorderno(String gqorderno) {
        this.gqorderno = gqorderno;
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

    public int getTjuserid() {
        return tjuserid;
    }

    public void setTjuserid(int tjuserid) {
        this.tjuserid = tjuserid;
    }

    public String getTjusername() {
        return tjusername;
    }

    public void setTjusername(String tjusername) {
        this.tjusername = tjusername;
    }

    public int getOrderfrom() {
        return orderfrom;
    }

    public void setOrderfrom(int orderfrom) {
        this.orderfrom = orderfrom;
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

    public Object getGqreason() {
        return gqreason;
    }

    public void setGqreason(Object gqreason) {
        this.gqreason = gqreason;
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

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
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

    public List<OldroutesBean> getOldroutes() {
        return oldroutes;
    }

    public void setOldroutes(List<OldroutesBean> oldroutes) {
        this.oldroutes = oldroutes;
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

    @Override
    public String getTotalpriceI() {
        double khYingshou = passengers.get(0).getKhYinshou();
        if (khYingshou != 0) {
            return AppUtils.keepNSecimal(khYingshou * passengers.size() + "", 2);
        }
        return "0.00";
    }

    @Override
    public String getOrderNoI() {
        return gqorderno;
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
        return "--";
    }

    @Override
    public String getCostCenterI() {
        return "--";
    }

    @Override
    public String getPronameI() {
        return "--";
    }

    @Override
    public String getChailvitemI() {
        return "--";
    }

    @Override
    public String getBookPolicyI() {
        return "--";
    }

    @Override
    public String getWBReasonI() {
        return "--";
    }

    @Override
    public IRouteBean getRoute() {
        return routes.get(0);
    }

    @Override
    public String getBxName() {
        return "";
    }

    public static class OldroutesBean {
        /**
         * id : 161
         * companyid : 35
         * orderno : AUVGO0118508743
         * orderid : null
         * xuhao : 0
         * airline : CA3202
         * carriecode : CA
         * carriername : 国航
         * planestyle : 321
         * orgcode : PEK
         * orgname : 首都国际机场
         * arricode : SHA
         * arriname : 虹桥国际机场
         * arriterm : T2
         * deptterm : T3
         * deptdate : 2017-01-20
         * depttime : 06:35
         * arridate : 2017-01-20
         * arritime : 08:50
         * mealcode : 快餐
         * code : Y
         * codeDes : null
         * stopnumber : 0
         * price : 1380.0
         * rewardprice : 0.0
         * fueltax : 0
         * airporttax : 50
         * discount : 100.0
         * disdes : 100折
         * flytime : 02:15
         * yprice : 0.0
         * refundrule : 航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费
         * changerule : 航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费
         * signrule : 不得签转
         * status : 0
         * createtime : 1484731131000
         * orgcitycode : BJS
         * orgcityname : 北京
         * dstcitycode : SHA
         * dstcityname : 上海
         */

        private int id;
        private int companyid;
        private String orderno;
        private Object orderid;
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
        private int fueltax;
        private int airporttax;
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

        public Object getOrderid() {
            return orderid;
        }

        public void setOrderid(Object orderid) {
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
            return orgname;
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
            return arriname;
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
            return codeDes;
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

        public int getFueltax() {
            return fueltax;
        }

        public void setFueltax(int fueltax) {
            this.fueltax = fueltax;
        }

        public int getAirporttax() {
            return airporttax;
        }

        public void setAirporttax(int airporttax) {
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
    }

    public static class RoutesBean implements IRouteBean {
        /**
         * id : 7
         * routeid : null
         * companyid : 35
         * gqorderno : GQ0119076588
         * oldorderno : AUVGO0118508743
         * xuhao : 0
         * airline : MU3662
         * carriecode : MU
         * carriername : 东航
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
         * code : Y
         * codeDes : 全价经济舱
         * stopnumber : 0
         * price : 1130.0
         * rewardprice : 0.0
         * fueltax : 0
         * airporttax : 50
         * discount : 100.0
         * disdes : 100折
         * flytime : 02:20
         * yprice : 0.0
         * refundrule : 航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费
         * changerule : 航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费
         * signrule : 不得签转
         * status : 0
         * createtime : 1484804554000
         */

        private int id;
        private Object routeid;
        private int companyid;
        private String gqorderno;
        private String oldorderno;
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
        private int fueltax;
        private int airporttax;
        private double discount;
        private String disdes;
        private String flytime;
        private double yprice;
        private String refundrule;
        private String changerule;
        private String signrule;
        private int status;
        private long createtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getRouteid() {
            return routeid;
        }

        public void setRouteid(Object routeid) {
            this.routeid = routeid;
        }

        public int getCompanyid() {
            return companyid;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }

        public String getGqorderno() {
            return gqorderno;
        }

        public void setGqorderno(String gqorderno) {
            this.gqorderno = gqorderno;
        }

        public String getOldorderno() {
            return oldorderno;
        }

        public void setOldorderno(String oldorderno) {
            this.oldorderno = oldorderno;
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
            return codeDes;
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

        public int getFueltax() {
            return fueltax;
        }

        public void setFueltax(int fueltax) {
            this.fueltax = fueltax;
        }

        public int getAirporttax() {
            return airporttax;
        }

        public void setAirporttax(int airporttax) {
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
    }

    public static class PassengersBean implements IPassenger {
        /**
         * id : 11
         * passid : null
         * oldorderno : AUVGO0118508743
         * gqorderno : GQ0119076588
         * gqorderid : 145
         * companyid : 35
         * name : 王悦
         * employeeid : 131
         * certype : null
         * certno : 410721199311240528
         * caigouPrice : null
         * rewardprice : null
         * salePrice : null
         * bxPayMoney : null
         * peisongfee : null
         * price : null
         * fueltax : null
         * airporttaxt : null
         * fuwufee : null
         * gqprice : null
         * status : 0
         * createtime : 1484804554000
         */

        private int id;
        private Object passid;
        private String oldorderno;
        private String gqorderno;
        private int gqorderid;
        private int companyid;
        private String name;
        private int employeeid;
        private Object certype;
        private String certno;
        private Object caigouPrice;
        private Object rewardprice;
        private Object salePrice;
        private int bxPayMoney;
        private String bxName;
        private String bxCode;
        private Object peisongfee;
        private Object price;
        private Object fueltax;
        private Object airporttaxt;
        private Object fuwufee;
        private Object gqprice;
        private int status;
        private double khYinshou;
        private long createtime;

        public double getKhYinshou() {
            return khYinshou;
        }

        public void setKhYinshou(double khYinshou) {
            this.khYinshou = khYinshou;
        }

        public int getId() {
            return id;
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

        public void setId(int id) {
            this.id = id;
        }

        public Object getPassid() {
            return passid;
        }

        public void setPassid(Object passid) {
            this.passid = passid;
        }

        public String getOldorderno() {
            return oldorderno;
        }

        public void setOldorderno(String oldorderno) {
            this.oldorderno = oldorderno;
        }

        public String getGqorderno() {
            return gqorderno;
        }

        public void setGqorderno(String gqorderno) {
            this.gqorderno = gqorderno;
        }

        public int getGqorderid() {
            return gqorderid;
        }

        public void setGqorderid(int gqorderid) {
            this.gqorderid = gqorderid;
        }

        public int getCompanyid() {
            return companyid;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(int employeeid) {
            this.employeeid = employeeid;
        }

        public Object getCertype() {
            return certype;
        }

        public void setCertype(Object certype) {
            this.certype = certype;
        }

        public String getCertno() {
            return certno;
        }

        public void setCertno(String certno) {
            this.certno = certno;
        }

        public Object getCaigouPrice() {
            return caigouPrice;
        }

        public void setCaigouPrice(Object caigouPrice) {
            this.caigouPrice = caigouPrice;
        }

        public Object getRewardprice() {
            return rewardprice;
        }

        public void setRewardprice(Object rewardprice) {
            this.rewardprice = rewardprice;
        }

        public Object getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(Object salePrice) {
            this.salePrice = salePrice;
        }

        public int getBxPayMoney() {
            return bxPayMoney;
        }

        public void setBxPayMoney(int bxPayMoney) {
            this.bxPayMoney = bxPayMoney;
        }

        public Object getPeisongfee() {
            return peisongfee;
        }

        public void setPeisongfee(Object peisongfee) {
            this.peisongfee = peisongfee;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getFueltax() {
            return fueltax;
        }

        public void setFueltax(Object fueltax) {
            this.fueltax = fueltax;
        }

        public Object getAirporttaxt() {
            return airporttaxt;
        }

        public void setAirporttaxt(Object airporttaxt) {
            this.airporttaxt = airporttaxt;
        }

        public Object getFuwufee() {
            return fuwufee;
        }

        public void setFuwufee(Object fuwufee) {
            this.fuwufee = fuwufee;
        }

        public Object getGqprice() {
            return gqprice;
        }

        public void setGqprice(Object gqprice) {
            this.gqprice = gqprice;
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

    public static class OrderPaymentBean {
        private double receivprice;
        private String paytype;

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
