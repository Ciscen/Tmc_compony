package com.auvgo.tmc.plane.bean;

import java.util.List;

/**
 * Created by lc on 2017/1/16
 */

public class PlaneReturnDetailBean {
    /**
     * id : 22
     * oldorderno : AUVGO0120476574
     * tporderno : TP0120438788
     * companyid : 35
     * companycode : AUVGO
     * companyname : 北京艾优薇文化科技有限公司
     * tjuserid : null
     * tjusername : null
     * orderfrom : 0
     * opuserid : 0
     * opusername : 系统管理员
     * linkName : 贺圣辉
     * linkAddress : null
     * linkEmail :
     * linkPhone : 15361541684
     * approveid : 53
     * approvestatus : 0
     * keepseat : null
     * ziyuantp : null
     * tpreason : 与客户约定时间变更
     * status : 0
     * paystatus : 0
     * createtime : 1484882539000
     * tuipiaoPassengers : [{"id":13,"oldorderno":"AUVGO0120476574","tporderno":"TP0120438788","tporderid":22,"companyid":35,"passid":133,"name":"牟爽","employeeid":133,"deptid":null,"certype":"NI","certno":"230227199711282126","caigouPrice":620,"rewardprice":0,"salePrice":620,"bxPayMoney":20,"peisongfee":null,"price":620,"fueltax":0,"airporttaxt":null,"cgTpfeelv":5,"cgTpprice":31,"tpfeelv":5,"tpprice":31,"status":0,"createtime":1484882539000},{"id":14,"oldorderno":"AUVGO0120476574","tporderno":"TP0120438788","tporderid":22,"companyid":35,"passid":134,"name":"周欣念","employeeid":134,"deptid":null,"certype":"NI","certno":"420822199302276119","caigouPrice":620,"rewardprice":0,"salePrice":620,"bxPayMoney":20,"peisongfee":null,"price":620,"fueltax":0,"airporttaxt":null,"cgTpfeelv":5,"cgTpprice":31,"tpfeelv":5,"tpprice":31,"status":0,"createtime":1484882539000},{"id":15,"oldorderno":"AUVGO0120476574","tporderno":"TP0120438788","tporderid":22,"companyid":35,"passid":133,"name":"牟爽","employeeid":133,"deptid":null,"certype":"NI","certno":"230227199711282126","caigouPrice":620,"rewardprice":0,"salePrice":620,"bxPayMoney":20,"peisongfee":null,"price":620,"fueltax":0,"airporttaxt":null,"cgTpfeelv":5,"cgTpprice":31,"tpfeelv":5,"tpprice":31,"status":0,"createtime":1484882539000},{"id":16,"oldorderno":"AUVGO0120476574","tporderno":"TP0120438788","tporderid":22,"companyid":35,"passid":134,"name":"周欣念","employeeid":134,"deptid":null,"certype":"NI","certno":"420822199302276119","caigouPrice":620,"rewardprice":0,"salePrice":620,"bxPayMoney":20,"peisongfee":null,"price":620,"fueltax":0,"airporttaxt":null,"cgTpfeelv":5,"cgTpprice":31,"tpfeelv":5,"tpprice":31,"status":0,"createtime":1484882539000}]
     * order : null
     * routes : [{"id":170,"companyid":35,"orderno":"AUVGO0120476574","orderid":156,"xuhao":0,"airline":"CZ6412","carriecode":"CZ","carriername":"南航","planestyle":"321","orgcode":"PEK","orgname":"首都国际机场","arricode":"SHA","arriname":"虹桥国际机场","arriterm":"T2","deptterm":"T2","deptdate":"2017-01-13","depttime":"06:40","arridate":"2017-01-13","arritime":"09:00","mealcode":"免费酒水饮料","code":"Q","codeDes":"经济舱","stopnumber":"0","price":620,"rewardprice":0,"fueltax":0,"airporttax":50,"discount":55,"disdes":"55折","flytime":"02:20","yprice":1130,"refundrule":"航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费","changerule":"航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费","signrule":"不得签转","status":0,"createtime":1484882371000,"orgcitycode":null,"orgcityname":null,"dstcitycode":null,"dstcityname":null}]
     */

    private int id;
    private String oldorderno;
    private String tporderno;
    private int companyid;
    private String companycode;
    private String companyname;
    private Object tjuserid;
    private Object tjusername;
    private int orderfrom;
    private int opuserid;
    private String opusername;
    private String linkName;
    private Object linkAddress;
    private String linkEmail;
    private String linkPhone;
    private int approveid;
    private int approvestatus;
    private Object keepseat;
    private Object ziyuantp;
    private String tpreason;
    private int status;
    private int paystatus;
    private long createtime;
    private Object order;
    private List<TuipiaoPassengersBean> tuipiaoPassengers;
    private List<RoutesBean> routes;

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

    public String getTporderno() {
        return tporderno;
    }

    public void setTporderno(String tporderno) {
        this.tporderno = tporderno;
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

    public Object getTjuserid() {
        return tjuserid;
    }

    public void setTjuserid(Object tjuserid) {
        this.tjuserid = tjuserid;
    }

    public Object getTjusername() {
        return tjusername;
    }

    public void setTjusername(Object tjusername) {
        this.tjusername = tjusername;
    }

    public int getOrderfrom() {
        return orderfrom;
    }

    public void setOrderfrom(int orderfrom) {
        this.orderfrom = orderfrom;
    }

    public int getOpuserid() {
        return opuserid;
    }

    public void setOpuserid(int opuserid) {
        this.opuserid = opuserid;
    }

    public String getOpusername() {
        return opusername;
    }

    public void setOpusername(String opusername) {
        this.opusername = opusername;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public Object getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(Object linkAddress) {
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

    public Object getKeepseat() {
        return keepseat;
    }

    public void setKeepseat(Object keepseat) {
        this.keepseat = keepseat;
    }

    public Object getZiyuantp() {
        return ziyuantp;
    }

    public void setZiyuantp(Object ziyuantp) {
        this.ziyuantp = ziyuantp;
    }

    public String getTpreason() {
        return tpreason;
    }

    public void setTpreason(String tpreason) {
        this.tpreason = tpreason;
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

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public List<TuipiaoPassengersBean> getTuipiaoPassengers() {
        return tuipiaoPassengers;
    }

    public void setTuipiaoPassengers(List<TuipiaoPassengersBean> tuipiaoPassengers) {
        this.tuipiaoPassengers = tuipiaoPassengers;
    }

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public static class TuipiaoPassengersBean {
        /**
         * id : 13
         * oldorderno : AUVGO0120476574
         * tporderno : TP0120438788
         * tporderid : 22
         * companyid : 35
         * passid : 133
         * name : 牟爽
         * employeeid : 133
         * deptid : null
         * certype : NI
         * certno : 230227199711282126
         * caigouPrice : 620
         * rewardprice : 0
         * salePrice : 620
         * bxPayMoney : 20
         * peisongfee : null
         * price : 620
         * fueltax : 0
         * airporttaxt : null
         * cgTpfeelv : 5
         * cgTpprice : 31
         * tpfeelv : 5
         * tpprice : 31
         * status : 0
         * createtime : 1484882539000
         */

        private int id;
        private String oldorderno;
        private String tporderno;
        private int tporderid;
        private int companyid;
        private int passid;
        private String name;
        private int employeeid;
        private Object deptid;
        private String certype;
        private String certno;
        private int caigouPrice;
        private int rewardprice;
        private int salePrice;
        private int bxPayMoney;
        private String bxName;
        private String bxCode;

        private Object peisongfee;
        private int price;
        private int fueltax;
        private Object airporttaxt;
        private int cgTpfeelv;
        private double cgTpprice;
        private int tpfeelv;
        private double tpprice;
        private int status;
        private long createtime;

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

        public String getTporderno() {
            return tporderno;
        }

        public void setTporderno(String tporderno) {
            this.tporderno = tporderno;
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

        public int getTporderid() {
            return tporderid;
        }

        public void setTporderid(int tporderid) {
            this.tporderid = tporderid;
        }

        public int getCompanyid() {
            return companyid;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }

        public int getPassid() {
            return passid;
        }

        public void setPassid(int passid) {
            this.passid = passid;
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

        public Object getDeptid() {
            return deptid;
        }

        public void setDeptid(Object deptid) {
            this.deptid = deptid;
        }

        public String getCertype() {
            return certype;
        }

        public void setCertype(String certype) {
            this.certype = certype;
        }

        public String getCertno() {
            return certno;
        }

        public void setCertno(String certno) {
            this.certno = certno;
        }

        public int getCaigouPrice() {
            return caigouPrice;
        }

        public void setCaigouPrice(int caigouPrice) {
            this.caigouPrice = caigouPrice;
        }

        public int getRewardprice() {
            return rewardprice;
        }

        public void setRewardprice(int rewardprice) {
            this.rewardprice = rewardprice;
        }

        public int getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(int salePrice) {
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getFueltax() {
            return fueltax;
        }

        public void setFueltax(int fueltax) {
            this.fueltax = fueltax;
        }

        public Object getAirporttaxt() {
            return airporttaxt;
        }

        public void setAirporttaxt(Object airporttaxt) {
            this.airporttaxt = airporttaxt;
        }

        public int getCgTpfeelv() {
            return cgTpfeelv;
        }

        public void setCgTpfeelv(int cgTpfeelv) {
            this.cgTpfeelv = cgTpfeelv;
        }

        public double getCgTpprice() {
            return cgTpprice;
        }

        public void setCgTpprice(double cgTpprice) {
            this.cgTpprice = cgTpprice;
        }

        public int getTpfeelv() {
            return tpfeelv;
        }

        public void setTpfeelv(int tpfeelv) {
            this.tpfeelv = tpfeelv;
        }

        public double getTpprice() {
            return tpprice;
        }

        public void setTpprice(double tpprice) {
            this.tpprice = tpprice;
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

    public static class RoutesBean {
        /**
         * id : 170
         * companyid : 35
         * orderno : AUVGO0120476574
         * orderid : 156
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
         * deptdate : 2017-01-13
         * depttime : 06:40
         * arridate : 2017-01-13
         * arritime : 09:00
         * mealcode : 免费酒水饮料
         * code : Q
         * codeDes : 经济舱
         * stopnumber : 0
         * price : 620
         * rewardprice : 0
         * fueltax : 0
         * airporttax : 50
         * discount : 55
         * disdes : 55折
         * flytime : 02:20
         * yprice : 1130
         * refundrule : 航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费
         * changerule : 航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费
         * signrule : 不得签转
         * status : 0
         * createtime : 1484882371000
         * orgcitycode : null
         * orgcityname : null
         * dstcitycode : null
         * dstcityname : null
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
        private int price;
        private int rewardprice;
        private int fueltax;
        private int airporttax;
        private int discount;
        private String disdes;
        private String flytime;
        private int yprice;
        private String refundrule;
        private String changerule;
        private String signrule;
        private int status;
        private long createtime;
        private Object orgcitycode;
        private Object orgcityname;
        private Object dstcitycode;
        private Object dstcityname;

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getRewardprice() {
            return rewardprice;
        }

        public void setRewardprice(int rewardprice) {
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

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
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

        public int getYprice() {
            return yprice;
        }

        public void setYprice(int yprice) {
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

        public Object getOrgcitycode() {
            return orgcitycode;
        }

        public void setOrgcitycode(Object orgcitycode) {
            this.orgcitycode = orgcitycode;
        }

        public Object getOrgcityname() {
            return orgcityname;
        }

        public void setOrgcityname(Object orgcityname) {
            this.orgcityname = orgcityname;
        }

        public Object getDstcitycode() {
            return dstcitycode;
        }

        public void setDstcitycode(Object dstcitycode) {
            this.dstcitycode = dstcitycode;
        }

        public Object getDstcityname() {
            return dstcityname;
        }

        public void setDstcityname(Object dstcityname) {
            this.dstcityname = dstcityname;
        }
    }
}
