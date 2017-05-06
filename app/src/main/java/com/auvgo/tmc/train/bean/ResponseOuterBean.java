package com.auvgo.tmc.train.bean;

/**
 * Created by lc on 2016/11/9
 */

public class ResponseOuterBean {

    /**
     * status : 200
     * msg : 登录成功
     * data : {"id":1,"name":"士大夫十分","nameen":"shenghui","firstname":"he","sex":"1","birthday":"","accno":"0013","mobile":"13311095221","email":"jishubu@126.com","zhiwei":"126","level":"dept","deptid":1,"deptname":"技术中心","companyid":1,"kaitong":1,"username":"13311095221","password":"9EC0CFBCBEDAC6143392329EC0740D56","guoji":null,"ifallowlogin":null,"ifallowbook":0,"bookrange":2,"bookdept":"/1/2/3/112/25","ifapprove":0,"approvesms":"1","approveemail":"1","openid":null,"certtype":"NI","certno":"430481198706236134","passportno":"","passportdate":"2015-08-20","roleid":7,"status":0,"createtime":1438597075000,"bookDeptIds":[1,2,3,112,25]}
     */

    private int status;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
