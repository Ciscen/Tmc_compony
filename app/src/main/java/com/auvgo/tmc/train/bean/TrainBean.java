package com.auvgo.tmc.train.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lc on 2016/11/15
 */

public class TrainBean {
    /**
     * c : 0
     * i : 0
     * m :
     * s : true
     * w : false
     * d : [{"access_byidcard":"true","arrive_days":"0","arrive_time":"19:44","can_buy_now":"false","edz_num":"367","edz_price":"553.0","end_station_code":"AOH","end_station_name":"上海虹桥","from_station_code":"VNP","from_station_name":"北京南","from_station_no":"01","gjrw_num":"--","gjrw_price":"","qtxb_num":"--","qtxb_price":"","run_time":"06:04","run_time_minute":"364","rw_num":"--","rw_price":"","rz_num":"--","rz_price":"","sale_date_time":"车站购买","sale_time":"1230","seat_types":"OM9","start_time":"13:40","start_station_code":"VNP","start_station_name":"北京南","swz_num":"23","swz_price":"1748.0","tdz_num":"--","tdz_price":"","to_station_code":"AOH","to_station_name":"上海虹桥","to_station_no":"11","train_code":"G139","train_no":"240000G1390M","train_start_date":"20161115","train_type":"","wz_num":"--","wz_price":"","ydz_num":"66","ydz_price":"933.0","yw_num":"--","yw_price":"","yz_num":"--","yz_price":"","canBook":[["商务座","23","1748.0","9"],["一等座","66","933.0","M"],["二等座","367","553.0","O"]]},{"access_byidcard":"true","arrive_days":"0","arrive_time":"18:48","can_buy_now":"false","edz_num":"0","edz_price":"553.0","end_station_code":"AOH","end_station_name":"上海虹桥","from_station_code":"VNP","from_station_name":"北京南","from_station_no":"01","gjrw_num":"--","gjrw_price":"","qtxb_num":"--","qtxb_price":"","run_time":"04:48","run_time_minute":"288","rw_num":"--","rw_price":"","rz_num":"--","rz_price":"","sale_date_time":"","sale_time":"1230","seat_types":"OM9","start_time":"14:00","start_station_code":"VNP","start_station_name":"北京南","swz_num":"0","swz_price":"1748.0","tdz_num":"--","tdz_price":"","to_station_code":"AOH","to_station_name":"上海虹桥","to_station_no":"03","train_code":"G3","train_no":"24000000G306","train_start_date":"20161115","train_type":"","wz_num":"--","wz_price":"","ydz_num":"0","ydz_price":"933.0","yw_num":"--","yw_price":"","yz_num":"--","yz_price":"","canBook":[]},{"access_byidcard":"true","arrive_days":"0","arrive_time":"19:37","can_buy_now":"false","edz_num":"0","edz_price":"553.0","end_station_code":"HGH","end_station_name":"杭州东","from_station_code":"VNP","from_station_name":"北京南","from_station_no":"01","gjrw_num":"--","gjrw_price":"","qtxb_num":"--","qtxb_price":"","run_time":"05:32","run_time_minute":"332","rw_num":"--","rw_price":"","rz_num":"--","rz_price":"","sale_date_time":"","sale_time":"1230","seat_types":"OM9","start_time":"14:05","start_station_code":"VNP","start_station_name":"北京南","swz_num":"0","swz_price":"1748.0","tdz_num":"--","tdz_price":"","to_station_code":"AOH","to_station_name":"上海虹桥","to_station_no":"09","train_code":"G43","train_no":"2400000G4306","train_start_date":"20161115","train_type":"","wz_num":"--","wz_price":"","ydz_num":"0","ydz_price":"933.0","yw_num":"--","yw_price":"","yz_num":"--","yz_price":"","canBook":[]},{"access_byidcard":"true","arrive_days":"0","arrive_time":"20:13","can_buy_now":"false","edz_num":"176","edz_price":"553.0","end_station_code":"AOH","end_station_name":"上海虹桥","from_station_code":"VNP","from_station_name":"北京南","from_station_no":"01","gjrw_num":"--","gjrw_price":"","qtxb_num":"--","qtxb_price":"","run_time":"06:03","run_time_minute":"363","rw_num":"--","rw_price":"","rz_num":"--","rz_price":"","sale_date_time":"车站购买","sale_time":"1230","seat_types":"OM9","start_time":"14:10","start_station_code":"VNP","start_station_name":"北京南","swz_num":"9","swz_price":"1748.0","tdz_num":"--","tdz_price":"","to_station_code":"AOH","to_station_name":"上海虹桥","to_station_no":"13","train_code":"G141","train_no":"240000G1410D","train_start_date":"20161115","train_type":"","wz_num":"--","wz_price":"","ydz_num":"108","ydz_price":"933.0","yw_num":"--","yw_price":"","yz_num":"--","yz_price":"","canBook":[["商务座","9","1748.0","9"],["一等座","108","933.0","M"],["二等座","176","553.0","O"]]}]
     */

    private String c;
    private String i;
    private String m;
    private String s;
    private String w;
    private List<DBean> d;

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public List<DBean> getD() {
        return d;
    }

    public void setD(List<DBean> d) {
        this.d = d;
    }

    public static class DBean implements Serializable {
        /**
         * access_byidcard : true
         * arrive_days : 0
         * arrive_time : 19:44
         * can_buy_now : false
         * edz_num : 367
         * edz_price : 553.0
         * end_station_code : AOH
         * end_station_name : 上海虹桥
         * from_station_code : VNP
         * from_station_name : 北京南
         * from_station_no : 01
         * gjrw_num : --
         * gjrw_price :
         * qtxb_num : --
         * qtxb_price :
         * run_time : 06:04
         * run_time_minute : 364
         * rw_num : --
         * rw_price :
         * rz_num : --
         * rz_price :
         * sale_date_time : 车站购买
         * sale_time : 1230
         * seat_types : OM9
         * start_time : 13:40
         * start_station_code : VNP
         * start_station_name : 北京南
         * swz_num : 23
         * swz_price : 1748.0
         * tdz_num : --
         * tdz_price :
         * to_station_code : AOH
         * to_station_name : 上海虹桥
         * to_station_no : 11
         * train_code : G139
         * train_no : 240000G1390M
         * train_start_date : 20161115
         * train_type :
         * wz_num : --
         * wz_price :
         * ydz_num : 66
         * ydz_price : 933.0
         * yw_num : --
         * yw_price :
         * yz_num : --
         * yz_price :
         * canBook : [["商务座","23","1748.0","9"],["一等座","66","933.0","M"],["二等座","367","553.0","O"]]
         */

        private String access_byidcard;
        private String arrive_days;
        private String arrive_time;
        private String can_buy_now;
        private String edz_num;
        private String edz_price;
        private String end_station_code;
        private String end_station_name;
        private String from_station_code;
        private String from_station_name;
        private String from_station_no;
        private String gjrw_num;
        private String gjrw_price;
        private String qtxb_num;
        private String qtxb_price;
        private String run_time;
        private String run_time_minute;
        private String rw_num;
        private String rw_price;
        private String rz_num;
        private String rz_price;
        private String sale_date_time;
        private String sale_time;
        private String seat_types;
        private String start_time;
        private String start_station_code;
        private String start_station_name;
        private String swz_num;
        private String swz_price;
        private String tdz_num;
        private String tdz_price;
        private String to_station_code;
        private String to_station_name;
        private String to_station_no;
        private String train_code;
        private String train_no;
        private String train_start_date;
        private String train_type;
        private String wz_num;
        private String wz_price;
        private String ydz_num;
        private String ydz_price;
        private String yw_num;
        private String yw_price;
        private String yz_num;
        private String yz_price;
        private List<List<String>> canBook;

        public String getAccess_byidcard() {
            return access_byidcard;
        }

        public void setAccess_byidcard(String access_byidcard) {
            this.access_byidcard = access_byidcard;
        }

        public String getArrive_days() {
            return arrive_days;
        }

        public void setArrive_days(String arrive_days) {
            this.arrive_days = arrive_days;
        }

        public String getArrive_time() {
            return arrive_time;
        }

        public void setArrive_time(String arrive_time) {
            this.arrive_time = arrive_time;
        }

        public String getCan_buy_now() {
            return can_buy_now;
        }

        public void setCan_buy_now(String can_buy_now) {
            this.can_buy_now = can_buy_now;
        }

        public String getEdz_num() {
            return edz_num;
        }

        public void setEdz_num(String edz_num) {
            this.edz_num = edz_num;
        }

        public String getEdz_price() {
            return edz_price;
        }

        public void setEdz_price(String edz_price) {
            this.edz_price = edz_price;
        }

        public String getEnd_station_code() {
            return end_station_code;
        }

        public void setEnd_station_code(String end_station_code) {
            this.end_station_code = end_station_code;
        }

        public String getEnd_station_name() {
            return end_station_name;
        }

        public void setEnd_station_name(String end_station_name) {
            this.end_station_name = end_station_name;
        }

        public String getFrom_station_code() {
            return from_station_code;
        }

        public void setFrom_station_code(String from_station_code) {
            this.from_station_code = from_station_code;
        }

        public String getFrom_station_name() {
            return from_station_name;
        }

        public void setFrom_station_name(String from_station_name) {
            this.from_station_name = from_station_name;
        }

        public String getFrom_station_no() {
            return from_station_no;
        }

        public void setFrom_station_no(String from_station_no) {
            this.from_station_no = from_station_no;
        }

        public String getGjrw_num() {
            return gjrw_num;
        }

        public void setGjrw_num(String gjrw_num) {
            this.gjrw_num = gjrw_num;
        }

        public String getGjrw_price() {
            return gjrw_price;
        }

        public void setGjrw_price(String gjrw_price) {
            this.gjrw_price = gjrw_price;
        }

        public String getQtxb_num() {
            return qtxb_num;
        }

        public void setQtxb_num(String qtxb_num) {
            this.qtxb_num = qtxb_num;
        }

        public String getQtxb_price() {
            return qtxb_price;
        }

        public void setQtxb_price(String qtxb_price) {
            this.qtxb_price = qtxb_price;
        }

        public String getRun_time() {
            return run_time;
        }

        public void setRun_time(String run_time) {
            this.run_time = run_time;
        }

        public String getRun_time_minute() {
            return run_time_minute;
        }

        public void setRun_time_minute(String run_time_minute) {
            this.run_time_minute = run_time_minute;
        }

        public String getRw_num() {
            return rw_num;
        }

        public void setRw_num(String rw_num) {
            this.rw_num = rw_num;
        }

        public String getRw_price() {
            return rw_price;
        }

        public void setRw_price(String rw_price) {
            this.rw_price = rw_price;
        }

        public String getRz_num() {
            return rz_num;
        }

        public void setRz_num(String rz_num) {
            this.rz_num = rz_num;
        }

        public String getRz_price() {
            return rz_price;
        }

        public void setRz_price(String rz_price) {
            this.rz_price = rz_price;
        }

        public String getSale_date_time() {
            return sale_date_time;
        }

        public void setSale_date_time(String sale_date_time) {
            this.sale_date_time = sale_date_time;
        }

        public String getSale_time() {
            return sale_time;
        }

        public void setSale_time(String sale_time) {
            this.sale_time = sale_time;
        }

        public String getSeat_types() {
            return seat_types;
        }

        public void setSeat_types(String seat_types) {
            this.seat_types = seat_types;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStart_station_code() {
            return start_station_code;
        }

        public void setStart_station_code(String start_station_code) {
            this.start_station_code = start_station_code;
        }

        public String getStart_station_name() {
            return start_station_name;
        }

        public void setStart_station_name(String start_station_name) {
            this.start_station_name = start_station_name;
        }

        public String getSwz_num() {
            return swz_num;
        }

        public void setSwz_num(String swz_num) {
            this.swz_num = swz_num;
        }

        public String getSwz_price() {
            return swz_price;
        }

        public void setSwz_price(String swz_price) {
            this.swz_price = swz_price;
        }

        public String getTdz_num() {
            return tdz_num;
        }

        public void setTdz_num(String tdz_num) {
            this.tdz_num = tdz_num;
        }

        public String getTdz_price() {
            return tdz_price;
        }

        public void setTdz_price(String tdz_price) {
            this.tdz_price = tdz_price;
        }

        public String getTo_station_code() {
            return to_station_code;
        }

        public void setTo_station_code(String to_station_code) {
            this.to_station_code = to_station_code;
        }

        public String getTo_station_name() {
            return to_station_name;
        }

        public void setTo_station_name(String to_station_name) {
            this.to_station_name = to_station_name;
        }

        public String getTo_station_no() {
            return to_station_no;
        }

        public void setTo_station_no(String to_station_no) {
            this.to_station_no = to_station_no;
        }

        public String getTrain_code() {
            return train_code;
        }

        public void setTrain_code(String train_code) {
            this.train_code = train_code;
        }

        public String getTrain_no() {
            return train_no;
        }

        public void setTrain_no(String train_no) {
            this.train_no = train_no;
        }

        public String getTrain_start_date() {
            return train_start_date;
        }

        public void setTrain_start_date(String train_start_date) {
            this.train_start_date = train_start_date;
        }

        public String getTrain_type() {
            return train_type;
        }

        public void setTrain_type(String train_type) {
            this.train_type = train_type;
        }

        public String getWz_num() {
            return wz_num;
        }

        public void setWz_num(String wz_num) {
            this.wz_num = wz_num;
        }

        public String getWz_price() {
            return wz_price;
        }

        public void setWz_price(String wz_price) {
            this.wz_price = wz_price;
        }

        public String getYdz_num() {
            return ydz_num;
        }

        public void setYdz_num(String ydz_num) {
            this.ydz_num = ydz_num;
        }

        public String getYdz_price() {
            return ydz_price;
        }

        public void setYdz_price(String ydz_price) {
            this.ydz_price = ydz_price;
        }

        public String getYw_num() {
            return yw_num;
        }

        public void setYw_num(String yw_num) {
            this.yw_num = yw_num;
        }

        public String getYw_price() {
            return yw_price;
        }

        public void setYw_price(String yw_price) {
            this.yw_price = yw_price;
        }

        public String getYz_num() {
            return yz_num;
        }

        public void setYz_num(String yz_num) {
            this.yz_num = yz_num;
        }

        public String getYz_price() {
            return yz_price;
        }

        public void setYz_price(String yz_price) {
            this.yz_price = yz_price;
        }

        public List<List<String>> getCanBook() {
            return canBook;
        }

        public void setCanBook(List<List<String>> canBook) {
            this.canBook = canBook;
        }
    }
}
