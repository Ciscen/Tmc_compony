package com.auvgo.tmc.train.bean;

import java.util.List;

/**
 * Created by lc on 2016/11/17
 */

public class TrainTimeBean {

    /**
     * status : 200
     * msg :
     * data : [{"start_station_name":"北京南","arrive_time":"----","station_train_code":"G149","station_name":"北京南","train_class_name":"高速","service_type":"1","start_time":"16:25","stop_over_time":"----","end_station_name":"上海虹桥","station_no":"01","is_over_here":false},{"arrive_time":"17:18","station_name":"沧州西","start_time":"17:20","stop_over_time":"2分钟","station_no":"02","is_over_here":false},{"arrive_time":"17:47","station_name":"德州东","start_time":"17:49","stop_over_time":"2分钟","station_no":"03","is_over_here":false},{"arrive_time":"18:13","station_name":"济南西","start_time":"18:19","stop_over_time":"6分钟","station_no":"04","is_over_here":false},{"arrive_time":"19:02","station_name":"滕州东","start_time":"19:04","stop_over_time":"2分钟","station_no":"05","is_over_here":false},{"arrive_time":"19:29","station_name":"徐州东","start_time":"19:31","stop_over_time":"2分钟","station_no":"06","is_over_here":false},{"arrive_time":"19:50","station_name":"宿州东","start_time":"19:52","stop_over_time":"2分钟","station_no":"07","is_over_here":false},{"arrive_time":"20:52","station_name":"南京南","start_time":"20:54","stop_over_time":"2分钟","station_no":"08","is_over_here":false},{"arrive_time":"21:13","station_name":"镇江南","start_time":"21:15","stop_over_time":"2分钟","station_no":"09","is_over_here":false},{"arrive_time":"21:45","station_name":"无锡东","start_time":"21:47","stop_over_time":"2分钟","station_no":"10","is_over_here":false},{"arrive_time":"22:03","station_name":"昆山南","start_time":"22:05","stop_over_time":"2分钟","station_no":"11","is_over_here":false},{"arrive_time":"22:22","station_name":"上海虹桥","start_time":"22:22","stop_over_time":"----","station_no":"12","is_over_here":false}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * start_station_name : 北京南
         * arrive_time : ----
         * station_train_code : G149
         * station_name : 北京南
         * train_class_name : 高速
         * service_type : 1
         * start_time : 16:25
         * stop_over_time : ----
         * end_station_name : 上海虹桥
         * station_no : 01
         * is_over_here : false
         */

        private String start_station_name;
        private String arrive_time;
        private String station_train_code;
        private String station_name;
        private String train_class_name;
        private String service_type;
        private String start_time;
        private String stop_over_time;
        private String end_station_name;
        private String station_no;
        private boolean is_over_here;

        public String getStart_station_name() {
            return start_station_name;
        }

        public void setStart_station_name(String start_station_name) {
            this.start_station_name = start_station_name;
        }

        public String getArrive_time() {
            return arrive_time;
        }

        public void setArrive_time(String arrive_time) {
            this.arrive_time = arrive_time;
        }

        public String getStation_train_code() {
            return station_train_code;
        }

        public void setStation_train_code(String station_train_code) {
            this.station_train_code = station_train_code;
        }

        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
        }

        public String getTrain_class_name() {
            return train_class_name;
        }

        public void setTrain_class_name(String train_class_name) {
            this.train_class_name = train_class_name;
        }

        public String getService_type() {
            return service_type;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStop_over_time() {
            return stop_over_time;
        }

        public void setStop_over_time(String stop_over_time) {
            this.stop_over_time = stop_over_time;
        }

        public String getEnd_station_name() {
            return end_station_name;
        }

        public void setEnd_station_name(String end_station_name) {
            this.end_station_name = end_station_name;
        }

        public String getStation_no() {
            return station_no;
        }

        public void setStation_no(String station_no) {
            this.station_no = station_no;
        }

        public boolean isIs_over_here() {
            return is_over_here;
        }

        public void setIs_over_here(boolean is_over_here) {
            this.is_over_here = is_over_here;
        }
    }
}
