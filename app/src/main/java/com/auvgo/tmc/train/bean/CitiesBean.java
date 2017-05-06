package com.auvgo.tmc.train.bean;

import java.util.List;

/**
 * Created by lc on 2016/11/10
 */
public class CitiesBean {

    private List<CityBean> hotcity;
    private List<CityBean> allcity;

    public List<CityBean> getHotcity() {
        return hotcity;
    }

    public void setHotcity(List<CityBean> hotcity) {
        this.hotcity = hotcity;
    }

    public List<CityBean> getAllcity() {
        return allcity;
    }

    public void setAllcity(List<CityBean> allcity) {
        this.allcity = allcity;
    }

    public static class HotcityBean {
        /**
         * id : 0
         * name : 北京
         * code : BJP
         */

        private String id;
        private String name;
        private String code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class CityBean {
        @Override
        public String toString() {
            return "CityBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    ", fullp='" + fullp + '\'' +
                    ", jianp='" + jianp + '\'' +
                    ", h='" + h + '\'' +
                    ", p1='" + p1 + '\'' +
                    '}';
        }

        /**
         * id : 0
         * name : 北京北
         * code : VAP
         * fullp : beijingbei
         * jianp : bjb
         * h : B
         * p1 : bjb
         */

        private String id;
        private String name;
        private String code;
        private String fullp;
        private String jianp;
        private String h;
        private String p1;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFullp() {
            return fullp;
        }

        public void setFullp(String fullp) {
            this.fullp = fullp;
        }

        public String getJianp() {
            return jianp;
        }

        public void setJianp(String jianp) {
            this.jianp = jianp;
        }

        public String getH() {
            return h;
        }

        public void setH(String h) {
            this.h = h;
        }

        public String getP1() {
            return p1;
        }

        public void setP1(String p1) {
            this.p1 = p1;
        }
    }
}
