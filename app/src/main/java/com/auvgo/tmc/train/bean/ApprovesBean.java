package com.auvgo.tmc.train.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lc on 2017/1/11
 */

public class ApprovesBean implements Serializable, Parcelable {
    /**
     * id : 113
     * companyid : 1
     * orderno : MDW112970272640710
     * employeeid : 7
     * name : 管理员
     * deptname : 运营部
     * level : 1
     * mobile : 13311095220
     * email :
     * isinert : null
     * status : 0
     * foujueyuanyin : null
     * approvetime : null
     * createtime : 1480398384000
     */

    private int id;
    private int companyid;
    private String orderno;
    private int employeeid;
    private String name;
    private String deptname;
    private int level;
    private String mobile;
    private String email;
    private String isinert;
    private int status;
    private String foujueyuanyin;
    private String approvetime;
    private long createtime;

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

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getIsinert() {
        return isinert;
    }

    public void setIsinert(String isinert) {
        this.isinert = isinert;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFoujueyuanyin() {
        return foujueyuanyin;
    }

    public void setFoujueyuanyin(String foujueyuanyin) {
        this.foujueyuanyin = foujueyuanyin;
    }

    public String getApprovetime() {
        return approvetime;
    }

    public void setApprovetime(String approvetime) {
        this.approvetime = approvetime;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.companyid);
        dest.writeString(this.orderno);
        dest.writeInt(this.employeeid);
        dest.writeString(this.name);
        dest.writeString(this.deptname);
        dest.writeInt(this.level);
        dest.writeString(this.mobile);
        dest.writeString(this.email);
        dest.writeString(this.isinert);
        dest.writeInt(this.status);
        dest.writeString(this.foujueyuanyin);
        dest.writeString(this.approvetime);
        dest.writeLong(this.createtime);
    }

    public ApprovesBean() {
    }

    protected ApprovesBean(Parcel in) {
        this.id = in.readInt();
        this.companyid = in.readInt();
        this.orderno = in.readString();
        this.employeeid = in.readInt();
        this.name = in.readString();
        this.deptname = in.readString();
        this.level = in.readInt();
        this.mobile = in.readString();
        this.email = in.readString();
        this.isinert = in.readString();
        this.status = in.readInt();
        this.foujueyuanyin = in.readString();
        this.approvetime = in.readString();
        this.createtime = in.readLong();
    }

    public static final Parcelable.Creator<ApprovesBean> CREATOR = new Parcelable.Creator<ApprovesBean>() {
        @Override
        public ApprovesBean createFromParcel(Parcel source) {
            return new ApprovesBean(source);
        }

        @Override
        public ApprovesBean[] newArray(int size) {
            return new ApprovesBean[size];
        }
    };
}
