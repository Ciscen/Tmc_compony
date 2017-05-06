package com.auvgo.tmc.train.bean;

/**
 * Created by lc on 2016/11/25
 */

public class Response12306Bean {

    /**
     * status : 200
     * msg : 登录失败:该邮箱不存在。
     * data : {"trainAccount":"96053d4505@qq.com","isPass":1}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * trainAccount : 96053d4505@qq.com
         * isPass : 1
         */

        private String trainAccount;
        private int isPass;

        public String getTrainAccount() {
            return trainAccount;
        }

        public void setTrainAccount(String trainAccount) {
            this.trainAccount = trainAccount;
        }

        public int getIsPass() {
            return isPass;
        }

        public void setIsPass(int isPass) {
            this.isPass = isPass;
        }
    }
}
