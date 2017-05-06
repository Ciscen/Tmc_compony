package com.auvgo.tmc.train.bean;

import com.auvgo.tmc.hotel.interfaces.IUserZhiWei;

import java.io.Serializable;
import java.util.List;

/**
 * Created by  lc on 2016/11/14
 */
public class UserBean implements Serializable, IUserZhiWei {


    /**
     * id : 1
     * name : 士大夫十分
     * nameen : shenghui
     * firstname : he
     * sex : 1
     * birthday :
     * accno : 0013
     * mobile : 13311095221
     * email : jishubu@126.com
     * zhiwei : 1
     * level : dept
     * deptid : 1
     * deptname : 技术中心
     * companyid : 1
     * kaitong : 1
     * username : 13311095221
     * password : 9EC0CFBCBEDAC6143392329EC0740D56
     * ifallowbook : 0
     * bookrange : 2
     * bookdept : /1/2/3/112/25
     * ifapprove : 0
     * approvesms : 1
     * approveemail : 1
     * certtype : NI
     * certno : 430481198706236134
     * passportno :
     * passportdate : 2015-08-20
     * roleid : 7
     * status : 0
     * addcustflage:1
     * createtime : 1438597075000
     * bookDeptIds : [1,2,3,112,25]
     */

    private int id;
    private String name;
    private String nameen;
    private String firstname;
    private String sex;
    private String birthday;
    private String accno;
    private String mobile;
    private String email;
    private String zhiwei;
    private String addcustflage;//是否开启添加临时乘客的开关0关1开
    private String level;
    private int deptid;
    private String deptname;
    private int companyid;
    private int kaitong;
    private String username;
    private String password;
    private int ifallowbook;
    private int bookrange;
    private String bookdept;
    private int ifapprove;
    private String approvesms;
    private String approveemail;
    private String certtype;
    private String certno;
    private String passportno;
    private String passportdate;
    private int roleid;
    private int status;
    private long createtime;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public String getAddcustflage() {
        return addcustflage;
    }

    public void setAddcustflage(String addcustflage) {
        this.addcustflage = addcustflage;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private List<Integer> bookDeptIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameen() {
        return nameen;
    }

    public void setNameen(String nameen) {
        this.nameen = nameen;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZhiwei() {
        return zhiwei;
    }

    public void setZhiwei(String zhiwei) {
        this.zhiwei = zhiwei;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public int getKaitong() {
        return kaitong;
    }

    public void setKaitong(int kaitong) {
        this.kaitong = kaitong;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIfallowbook() {
        return ifallowbook;
    }

    public void setIfallowbook(int ifallowbook) {
        this.ifallowbook = ifallowbook;
    }

    public int getBookrange() {
        return bookrange;
    }

    public void setBookrange(int bookrange) {
        this.bookrange = bookrange;
    }

    public String getBookdept() {
        return bookdept;
    }

    public void setBookdept(String bookdept) {
        this.bookdept = bookdept;
    }

    public int getIfapprove() {
        return ifapprove;
    }

    public void setIfapprove(int ifapprove) {
        this.ifapprove = ifapprove;
    }

    public String getApprovesms() {
        return approvesms;
    }

    public void setApprovesms(String approvesms) {
        this.approvesms = approvesms;
    }

    public String getApproveemail() {
        return approveemail;
    }

    public void setApproveemail(String approveemail) {
        this.approveemail = approveemail;
    }

    public String getCerttype() {
        return certtype;
    }

    public void setCerttype(String certtype) {
        this.certtype = certtype;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getPassportno() {
        return passportno;
    }

    public void setPassportno(String passportno) {
        this.passportno = passportno;
    }

    public String getPassportdate() {
        return passportdate;
    }

    public void setPassportdate(String passportdate) {
        this.passportdate = passportdate;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
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

    public List<Integer> getBookDeptIds() {
        return bookDeptIds;
    }

    public void setBookDeptIds(List<Integer> bookDeptIds) {
        this.bookDeptIds = bookDeptIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBean userBean = (UserBean) o;

        return id == userBean.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String getZhiWei() {
        return zhiwei;
    }
}
