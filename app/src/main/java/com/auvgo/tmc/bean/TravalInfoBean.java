package com.auvgo.tmc.bean;

/**
 * Created by lc on 2017/1/11
 */

public class TravalInfoBean {

    /**
     * trainPolicy : {"id":51,"companyid":35,"startlevel":1,"endlevel":1,"gaotie":"9/P/M/","donche":"9/P/M/","pukuai":"4/3/6/2/","controltype":"1/1/1/"}
     * hotelPolicy :
     * airPolicy : {"id":55,"companyid":35,"type":"level","startlevel":1,"endlevel":1,"startmile":0,"endmile":10000,"allowfly":1,"cabinlimit":1,"cabinzhekou":60,"flightlimit":null,"flightlowtype":1,"flighthour":4,"allowbefore":1,"beforeday":7,"status":0,"controller":1,"createtime":1484116957000}
     */

    private String trainPolicy;
    private String hotelPolicy;
    private String airPolicy;

    public String getTrainPolicy() {
        return trainPolicy;
    }

    public void setTrainPolicy(String trainPolicy) {
        this.trainPolicy = trainPolicy;
    }

    public String getHotelPolicy() {
        return hotelPolicy;
    }

    public void setHotelPolicy(String hotelPolicy) {
        this.hotelPolicy = hotelPolicy;
    }

    public String getAirPolicy() {
        return airPolicy;
    }

    public void setAirPolicy(String airPolicy) {
        this.airPolicy = airPolicy;
    }

}
