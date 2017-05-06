package com.auvgo.tmc.common.bean;

import com.auvgo.tmc.common.adapter.PeisongAdapter;
import com.auvgo.tmc.utils.AppUtils;

/**
 * Created by lc on 2017/4/11
 */

public class PeisonListBean implements PeisongAdapter.IPeisong {


    /**
     * id : 3
     * companyname : 北京艾优薇文化科技有限公司
     * companycode : AUVGO
     * companyid : 35
     * linkname : null
     * linkmobile : null
     * address : 北京市朝阳区亚运村安慧里四区北京五矿大厦
     * isdefault : 1
     * createtime : 1491897290000
     */

    private int id;
    private String companyname;
    private String companycode;
    private int companyid;
    private String linkname;
    private String linkmobile;
    private String address;
    private int isdefault;
    private long createtime;

    @Override
    public String getName() {
        return AppUtils.getNoNullStr(linkname);
    }

    @Override
    public String getTel() {
        return AppUtils.getNoNullStr(linkmobile);
    }

    @Override
    public String getAddr() {
        return address;
    }

    @Override
    public boolean isDefault() {
        return isdefault == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }

    public String getLinkmobile() {
        return linkmobile;
    }

    public void setLinkmobile(String linkmobile) {
        this.linkmobile = linkmobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
