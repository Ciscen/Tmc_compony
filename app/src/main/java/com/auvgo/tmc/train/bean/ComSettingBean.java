package com.auvgo.tmc.train.bean;

import com.auvgo.tmc.base.MyList;

/**
 * Created by lc on 2016/11/11
 */

public class ComSettingBean {


    /**
     * productSet : {"id":1,"companyid":1,"proconfvalue":[{"name":"needxcd","value":"0"},{"name":"showzhcp","value":"1"},{"name":"autosendcpemail","value":"1"},{"name":"kaiqihbydtx","value":"1"},{"name":"kaiqitgq","value":"1"},{"name":"autoclk","value":"1"},{"name":"showtgq","value":"1"},{"name":"shiming","value":"0"},{"name":"kaiqiccsq","value":"0"},{"name":"projectinfo","value":"2"},{"name":"travelreason","value":"0"},{"name":"costcenter","value":"3"}]}
     * fuwufei : {"id":2,"companyid":1,"gndd":10,"gnweb":10,"gnapp":10,"gnhoteltype":"order","gnhoteldd":10,"gnhotelweb":10,"gnhotelapp":10,"gjhoteltype":"order","traindd":10,"trainweb":10,"trainapp":10}
     */

    private ProductSetBean productSet;
    private FuwufeiBean fuwufei;

    public ProductSetBean getProductSet() {
        return productSet;
    }

    public void setProductSet(ProductSetBean productSet) {
        this.productSet = productSet;
    }

    public FuwufeiBean getFuwufei() {
        return fuwufei;
    }

    public void setFuwufei(FuwufeiBean fuwufei) {
        this.fuwufei = fuwufei;
    }


    public static class ProductSetBean {
        /**
         * id : 1
         * companyid : 1
         * proconfvalue : [{"name":"needxcd","value":"0"},{"name":"showzhcp","value":"1"},{"name":
         * "autosendcpemail","value":"1"},{"name":"kaiqihbydtx","value":"1"},{"name":"kaiqitgq","value":"1"},
         * {"name":"autoclk","value":"1"},{"name":"showtgq","value":"1"},{"name":"shiming","value":"0"},
         * {"name":"kaiqiccsq","value":"0"},{"name":"projectinfo","value":"2"},{"name":"travelreason","value":"0"},
         * {"name":"costcenter","value":"3"}]
         */

        private int id;
        private int companyid;
        private String proconfvalue;
        private MyList<ProconfvalueBean> proconfValue;

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

        public String getProconfvalue() {
            return proconfvalue;
        }

        public void setProconfvalue(String proconfvalue) {
            this.proconfvalue = proconfvalue;
        }

        public MyList<ProconfvalueBean> getProconfValue() {
            return proconfValue;
        }

        public void setProconfValue(MyList<ProconfvalueBean> proconfvalue) {
            this.proconfValue = proconfvalue;
        }

        public static class ProconfvalueBean {
            /**
             * name : needxcd
             * value : 0
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    public static class FuwufeiBean {
        /**
         * id : 2
         * companyid : 1
         * gndd : 10
         * gnweb : 10
         * gnapp : 10
         * gnhoteltype : order
         * gnhoteldd : 10
         * gnhotelweb : 10
         * gnhotelapp : 10
         * gjhoteltype : order
         * traindd : 10
         * trainweb : 10
         * trainapp : 10
         */

        private int id;
        private int companyid;
        private int gndd;
        private int gnweb;
        private double gnapp;
        private String gnhoteltype;
        private int gnhoteldd;
        private int gnhotelweb;
        private double gnhotelapp;
        private String gjhoteltype;
        private int traindd;
        private int trainweb;
        private int trainapp;
        private int trainpeison;//配送费  火车票

        public int getTrainpeison() {
            return trainpeison;
        }

        public void setTrainpeison(int trainpeison) {
            this.trainpeison = trainpeison;
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

        public int getGndd() {
            return gndd;
        }

        public void setGndd(int gndd) {
            this.gndd = gndd;
        }

        public int getGnweb() {
            return gnweb;
        }

        public void setGnweb(int gnweb) {
            this.gnweb = gnweb;
        }

        public double getGnapp() {
            return gnapp;
        }

        public void setGnapp(double gnapp) {
            this.gnapp = gnapp;
        }

        public String getGnhoteltype() {
            return gnhoteltype;
        }

        public void setGnhoteltype(String gnhoteltype) {
            this.gnhoteltype = gnhoteltype;
        }

        public int getGnhoteldd() {
            return gnhoteldd;
        }

        public void setGnhoteldd(int gnhoteldd) {
            this.gnhoteldd = gnhoteldd;
        }

        public int getGnhotelweb() {
            return gnhotelweb;
        }

        public void setGnhotelweb(int gnhotelweb) {
            this.gnhotelweb = gnhotelweb;
        }

        public double getGnhotelapp() {
            return gnhotelapp;
        }

        public void setGnhotelapp(double gnhotelapp) {
            this.gnhotelapp = gnhotelapp;
        }

        public String getGjhoteltype() {
            return gjhoteltype;
        }

        public void setGjhoteltype(String gjhoteltype) {
            this.gjhoteltype = gjhoteltype;
        }

        public int getTraindd() {
            return traindd;
        }

        public void setTraindd(int traindd) {
            this.traindd = traindd;
        }

        public int getTrainweb() {
            return trainweb;
        }

        public void setTrainweb(int trainweb) {
            this.trainweb = trainweb;
        }

        public int getTrainapp() {
            return trainapp;
        }

        public void setTrainapp(int trainapp) {
            this.trainapp = trainapp;
        }
    }

}
