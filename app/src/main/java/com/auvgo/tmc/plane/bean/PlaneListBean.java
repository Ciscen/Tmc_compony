package com.auvgo.tmc.plane.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lc on 2016/12/22
 */

public class PlaneListBean implements Serializable {
    /**
     * airline : MU3924
     * carriecode : MU
     * carriername : 东航
     * orgcode : PEK
     * orgname : 北京首都国际机场
     * arricode : SHA
     * arriname : 上海虹桥国际机场
     * deptdate : 2017-01-20
     * depttime : 06:35
     * arritime : 08:50
     * arridate : 2017-01-20
     * arriterm : T2
     * deptterm : T3
     * asr : true
     * codeShare : true
     * sharecarrier : HO1252
     * iswifi : null
     * meal : true
     * mealcode : 快餐
     * nwst : 无机上网路服务信息
     * planestyle : 321
     * stopnumber : 0
     * flydistance : -1
     * flytime : 02:15
     * fueltax : 0
     * airporttax : 50
     * ontime : 0
     * cangweis : [{"code":"Y","extcode":null,"codeDes":"全价经济舱","price":1000,"seatNum":"A","discount":100,"disdes":"100折","refundrule":"航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费","changerule":"航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费","signrule":"不得签转","rewardprice":0,"pfrom":0}]
     * low : {"code":"Y","extcode":null,"codeDes":"全价经济舱","price":1000,"seatNum":"A","discount":100,"disdes":"100折","refundrule":"航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费","changerule":"航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费","signrule":"不得签转","rewardprice":0,"pfrom":0}
     */

    private String airline;
    private String carriecode;
    private String carriername;
    private String orgcode;
    private String orgname;
    private String arricode;
    private String arriname;
    private String deptdate;
    private String depttime;
    private String arritime;
    private String arridate;
    private String arriterm;
    private String deptterm;
    private boolean asr;
    private boolean codeShare;
    private String sharecarrier;
    private Object iswifi;
    private boolean meal;
    private String mealcode;
    private String nwst;
    private String planestyle;
    private String stopnumber;
    private int flydistance;
    private String flytime;
    private int fueltax;
    private int airporttax;
    private int ontime;
    private LowBean low;
    private List<CangweisBean> cangweis;
    private boolean isAirlineWei;
    private String weiItemStr;//违背事项的描述
    private boolean isContainsLowestCarbin;//是否包含全天最低价的舱位

    public boolean isContainsLowestCarbin() {
        return isContainsLowestCarbin;
    }

    public void setContainsLowestCarbin(boolean containsLowestCarbin) {
        isContainsLowestCarbin = containsLowestCarbin;
    }

    public String getWeiItemStr() {
        return weiItemStr;
    }

    public void setWeiItemStr(String weiItemStr) {
        this.weiItemStr = weiItemStr;
    }

    public boolean isAirlineWei() {
        return isAirlineWei;
    }

    public void setAirlineWei(boolean airlineWei) {
        isAirlineWei = airlineWei;
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

    public String getArritime() {
        return arritime;
    }

    public void setArritime(String arritime) {
        this.arritime = arritime;
    }

    public String getArridate() {
        return arridate;
    }

    public void setArridate(String arridate) {
        this.arridate = arridate;
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

    public boolean isAsr() {
        return asr;
    }

    public void setAsr(boolean asr) {
        this.asr = asr;
    }

    public boolean isCodeShare() {
        return codeShare;
    }

    public void setCodeShare(boolean codeShare) {
        this.codeShare = codeShare;
    }

    public String getSharecarrier() {
        return sharecarrier;
    }

    public void setSharecarrier(String sharecarrier) {
        this.sharecarrier = sharecarrier;
    }

    public Object getIswifi() {
        return iswifi;
    }

    public void setIswifi(Object iswifi) {
        this.iswifi = iswifi;
    }

    public boolean isMeal() {
        return meal;
    }

    public void setMeal(boolean meal) {
        this.meal = meal;
    }

    public String getMealcode() {
        return mealcode;
    }

    public void setMealcode(String mealcode) {
        this.mealcode = mealcode;
    }

    public String getNwst() {
        return nwst;
    }

    public void setNwst(String nwst) {
        this.nwst = nwst;
    }

    public String getPlanestyle() {
        return planestyle;
    }

    public void setPlanestyle(String planestyle) {
        this.planestyle = planestyle;
    }

    public String getStopnumber() {
        return stopnumber;
    }

    public void setStopnumber(String stopnumber) {
        this.stopnumber = stopnumber;
    }

    public int getFlydistance() {
        return flydistance;
    }

    public void setFlydistance(int flydistance) {
        this.flydistance = flydistance;
    }

    public String getFlytime() {
        return flytime;
    }

    public void setFlytime(String flytime) {
        this.flytime = flytime;
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

    public int getOntime() {
        return ontime;
    }

    public void setOntime(int ontime) {
        this.ontime = ontime;
    }

    public LowBean getLow() {
        return low;
    }

    public void setLow(LowBean low) {
        this.low = low;
    }

    public List<CangweisBean> getCangweis() {
        return cangweis;
    }

    public void setCangweis(List<CangweisBean> cangweis) {
        this.cangweis = cangweis;
    }


    public static class LowBean extends CangweisBean implements Serializable {
        /**
         * code : Y
         * extcode : null
         * codeDes : 全价经济舱
         * price : 1000
         * farebase:Y
         * seatNum : A
         * discount : 100
         * disdes : 100折
         * refundrule : 航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费
         * changerule : 航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费
         * signrule : 不得签转
         * rewardprice : 0
         * pfrom : 0
         */

//        private String code;
//        private Object extcode;
//        private String codeDes;
//        private double price;
//        private String seatNum;
//        private int discount;
//        private String disdes;
//        private String farebase;
//        private String refundrule;
//        private String changerule;
//        private String signrule;
//        private int rewardprice;
//        private int pfrom;

//        public String getFarebase() {
//            return farebase;
//        }
//
//        public void setFarebase(String farebase) {
//            this.farebase = farebase;
//        }
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public Object getExtcode() {
//            return extcode;
//        }
//
//        public void setExtcode(Object extcode) {
//            this.extcode = extcode;
//        }
//
//        public String getCodeDes() {
//            return codeDes;
//        }
//
//        public void setCodeDes(String codeDes) {
//            this.codeDes = codeDes;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public void setPrice(double price) {
//            this.price = price;
//        }
//
//        public String getSeatNum() {
//            return seatNum;
//        }
//
//        public void setSeatNum(String seatNum) {
//            this.seatNum = seatNum;
//        }
//
//        public int getDiscount() {
//            return discount;
//        }
//
//        public void setDiscount(int discount) {
//            this.discount = discount;
//        }
//
//        public String getDisdes() {
//            return disdes;
//        }
//
//        public void setDisdes(String disdes) {
//            this.disdes = disdes;
//        }
//
//        public String getRefundrule() {
//            return refundrule;
//        }
//
//        public void setRefundrule(String refundrule) {
//            this.refundrule = refundrule;
//        }
//
//        public String getChangerule() {
//            return changerule;
//        }
//
//        public void setChangerule(String changerule) {
//            this.changerule = changerule;
//        }
//
//        public String getSignrule() {
//            return signrule;
//        }
//
//        public void setSignrule(String signrule) {
//            this.signrule = signrule;
//        }
//
//        public int getRewardprice() {
//            return rewardprice;
//        }
//
//        public void setRewardprice(int rewardprice) {
//            this.rewardprice = rewardprice;
//        }
//
//        public int getPfrom() {
//            return pfrom;
//        }
//
//        public void setPfrom(int pfrom) {
//            this.pfrom = pfrom;
//        }
    }

    public static class CangweisBean implements Serializable {
        /**
         * code : Y
         * extcode : null
         * codeDes : 全价经济舱
         * price : 1000
         * seatNum : A
         * farebase:Y
         * discount : 100
         * disdes : 100折
         * refundrule : 航班规定离站时间2小时之前：免收退票费，航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格10%的退票手续费
         * changerule : 航班规定离站时间2小时之前：免费更改;航班规定离站时间前2小时之内及航班起飞后：收取实际票面价格5%的变更手续费
         * signrule : 不得签转
         * rewardprice : 0
         * pfrom : 0
         */
        private String farebase;
        private String code;
        private Object extcode;
        private String codeDes;
        private double price;
        private String seatNum;
        private int discount;
        private String disdes;
        private String refundrule;
        private String changerule;
        private String signrule;
        private int rewardprice;
        private String pfrom;
        private boolean isWei;
        private boolean isLowestInThisFlight;//是否是最低舱位

        public boolean isLowestInThisFlight() {
            return isLowestInThisFlight;
        }

        public void setLowestInThisFlight(boolean lowestInThisFlight) {
            isLowestInThisFlight = lowestInThisFlight;
        }

        public boolean isWei() {
            return isWei;
        }

        public void setWei(boolean wei) {
            isWei = wei;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Object getExtcode() {
            return extcode;
        }

        public void setExtcode(Object extcode) {
            this.extcode = extcode;
        }

        public String getCodeDes() {
            return codeDes;
        }

        public void setCodeDes(String codeDes) {
            this.codeDes = codeDes;
        }

        public String getFarebase() {
            return farebase;
        }

        public void setFarebase(String farebase) {
            this.farebase = farebase;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSeatNum() {
            return seatNum;
        }

        public void setSeatNum(String seatNum) {
            this.seatNum = seatNum;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public String getDisdes() {
            return discount < 100 ? discount / 10 + "折" : discount == 100 ? "全价" : "";
        }

        public void setDisdes(String disdes) {
            this.disdes = disdes;
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

        public int getRewardprice() {
            return rewardprice;
        }

        public void setRewardprice(int rewardprice) {
            this.rewardprice = rewardprice;
        }

        public String getPfrom() {
            return pfrom;
        }

        public void setPfrom(String pfrom) {
            this.pfrom = pfrom;
        }
    }
}
