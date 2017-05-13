package com.auvgo.tmc.plane.bean;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

import java.util.List;

/**
 * Created by lc on 2017/1/4
 */

public class RequestCreatePlaneOrder extends BaseRequestBean {
    // -----------------------订单信息 order----------------------
    private long companyid;// 公司id
    private int chuchaitype;//   0 因公  1 因私
    private String ordertype;//   ow单程  rt往返
    private String orderLevel;// 订单级别 0普通订单 1vip订单（购买保险）
    private int orderFrom;// 0 后台白屏 1前台白屏 2ios 3 安卓
    private String linkAddress;// 联系地址
    private String linkEmail;// 联系邮件
    private String linkName;// 联系人姓名
    private String linkPhone;// 联系电话
    private String costname;// 成本中心名称
    private long costid;// 成本中心id
    private String proname;// 项目名称
    private long proid;// 项目id
    private String shenqingno;// 企业审批号
    private String chailvitem;// 差旅事项
    private double totalprice;// 订单总额
    private long bookUserId;// 预订人id
    private String bookUserName;// 预订姓名
    private String bookpolicy;// 预订时的差旅标准
    private int weibeiflag;// 0 正常订单 1违背标准
    private java.lang.Integer tickettype;//   票种类型0国内1国际
    private String payType;
    private List<Route> routes;//行程
    private List<PsgBean> users;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getTickettype() {
        return tickettype;
    }

    public void setTickettype(Integer tickettype) {
        this.tickettype = tickettype;
    }

    public List<PsgBean> getUsers() {
        return users;
    }


    public void setUsers(List<PsgBean> users) {
        this.users = users;
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

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
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

    public long getCostid() {
        return costid;
    }

    public void setCostid(long costid) {
        this.costid = costid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public long getProid() {
        return proid;
    }

    public void setProid(long proid) {
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

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public static class Route {
        // -------------行程信息 routes ---------------------//
        private String dstcode;//SHA
        private String code;// 舱位代码

        private int worktime;// 政策有效作用时间范围
        private int tpbeforefee;// 起飞前退票费
        private int tpafterfee;// 起飞后退票费
        private int gqbeforefee;// 起飞前改期费
        private int gqafterfee;// 起飞后改期费
        private String includeflage;// 退改签前面包含还是后面时间包含 be/af


        private String codeDes;// 舱位描述
        private double price;// 销售价
        private String xuhao;//行程序号：单程 默认为0   往返去程0，回程为1
        private String airline;//航班号
        private String carriecode;//航空公司代码
        private String carriername;//航空公司名称
        private String planestyle;// 得到本航段的机型
        private String orgcode;// 出发城市代码
        private String orgname;// 出发城市名称
        private String arricode;// arricode
        private String arriname;// 到达城市名称
        private String arriterm;// 到达航站楼
        private String deptterm;// 出发航站楼
        private String deptdate;// 出发日期
        private String depttime;// 出发时间
        private String arridate;// 到达日期
        private String arritime;// 到达时间
        private String mealcode;// 餐食代码
        private String cangwei;//舱位代码
        private int weibeiflag;//违背表示
        private String wbreason;//违背原因
        private String pricefrom;//价格来源C:普通、K:K位、D:折上折、B:大客户、G:公务机票、W:航司B2C官网、散冲团、官网普通、官网商旅卡)

        public String getPricefrom() {
            return pricefrom;
        }

        public String getCodeDes() {
            return codeDes;
        }

        public void setCodeDes(String codeDes) {
            this.codeDes = codeDes;
        }

        public int getWorktime() {
            return worktime;
        }

        public void setWorktime(int worktime) {
            this.worktime = worktime;
        }

        public int getTpbeforefee() {
            return tpbeforefee;
        }

        public void setTpbeforefee(int tpbeforefee) {
            this.tpbeforefee = tpbeforefee;
        }

        public int getTpafterfee() {
            return tpafterfee;
        }

        public void setTpafterfee(int tpafterfee) {
            this.tpafterfee = tpafterfee;
        }

        public int getGqbeforefee() {
            return gqbeforefee;
        }

        public void setGqbeforefee(int gqbeforefee) {
            this.gqbeforefee = gqbeforefee;
        }

        public int getGqafterfee() {
            return gqafterfee;
        }

        public void setGqafterfee(int gqafterfee) {
            this.gqafterfee = gqafterfee;
        }

        public String getIncludeflage() {
            return includeflage;
        }

        public void setIncludeflage(String includeflage) {
            this.includeflage = includeflage;
        }

        public void setPricefrom(String pricefrom) {
            this.pricefrom = pricefrom;
        }

        public String getWbreason() {
            return wbreason;
        }

        public void setWbreason(String wbreason) {
            this.wbreason = wbreason;
        }

        public int getWeibeiflag() {
            return weibeiflag;
        }

        public void setWeibeiflag(int weibeiflag) {
            this.weibeiflag = weibeiflag;
        }

        private String stopnumber;// 飞机经停数,1表示有一次经停
        private double discount;// 折扣
        private String disdes;// 折扣描述
        private String flytime;// 飞行时间
        private double yprice;// Y舱价格
        private double rewardprice;// 奖励价格
        private String refundrule;// 退票规定
        private String changerule;// 更改规定
        private String signrule;// 签转规定
        private int fueltax;// 燃油费
        private int airporttax;//机建费
        private String orgcitycode;// 出发城市代码
        private String orgcityname;// 出发城市名称
        private String dstcitycode;// 到达城市代码
        private String dstcityname;// 到达城市名称

        public String getOrgcode() {
            return orgcode;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getXuhao() {
            return xuhao;
        }

        public void setXuhao(String xuhao) {
            this.xuhao = xuhao;
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

        public String getPlanestyle() {
            return planestyle;
        }

        public void setPlanestyle(String planestyle) {
            this.planestyle = planestyle;
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

        public String getCangwei() {
            return cangwei;
        }

        public void setCangwei(String cangwei) {
            this.cangwei = cangwei;
        }

        public String getStopnumber() {
            return stopnumber;
        }

        public void setStopnumber(String stopnumber) {
            this.stopnumber = stopnumber;
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

        public double getRewardprice() {
            return rewardprice;
        }

        public void setRewardprice(double rewardprice) {
            this.rewardprice = rewardprice;
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

        public void setOrgcode(String orgcode) {
            this.orgcode = orgcode;
        }

        public String getDstcode() {
            return dstcode;
        }

        public void setDstcode(String dstcode) {
            this.dstcode = dstcode;
        }

        public String getAirline() {
            return airline;
        }

        public void setAirline(String airline) {
            this.airline = airline;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }


    public static class PsgBean {
        // -------------出行人信息 users ---------------------//
        private java.lang.Long employeeid;//乘机人id
        private java.lang.String name;//乘机人姓名
        private java.lang.String passtype;//AD 成人 CH 婴儿 IN 儿童
        private java.lang.String mobile;//电话
        private java.lang.String certtype;//证件类型
        private java.lang.String deptid;//部门id
        private java.lang.String depname;//部门名称
        private java.lang.String certno;//证件号
        private String bxCode;
        private String bxName;
        private java.lang.Double bxPayMoney;//保险费,默认为0
        private java.lang.Double peisongfee;//配送费
        private java.lang.Double price;//单张票价
        private java.lang.Double fuwufee;//服务费
        private java.lang.Double totalprice;//总价
        private String zhiwei;//职位

        public String getZhiwei() {
            return zhiwei;
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

        public void setZhiwei(String zhiwei) {
            this.zhiwei = zhiwei;
        }

        public Long getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(Long employeeid) {
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

        public Double getBxPayMoney() {
            return bxPayMoney;
        }

        public void setBxPayMoney(Double bxPayMoney) {
            this.bxPayMoney = bxPayMoney;
        }

        public Double getPeisongfee() {
            return peisongfee;
        }

        public void setPeisongfee(Double peisongfee) {
            this.peisongfee = peisongfee;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getFuwufee() {
            return fuwufee;
        }

        public void setFuwufee(Double fuwufee) {
            this.fuwufee = fuwufee;
        }

        public Double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(Double totalprice) {
            this.totalprice = totalprice;
        }
    }


}
