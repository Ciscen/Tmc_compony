package com.auvgo.tmc.hotel.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 2017/3/6
 */

public class HotelFiltBean implements Cloneable {

    private ArrayList<HotelLocationBean> brands;
    private ArrayList<HotelLocationBean> aihao;
    private ArrayList<HotelLocationBean> weizhi;
    private ArrayList<HotelLocationBean> sheshifuwu;

    public ArrayList<HotelLocationBean> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<HotelLocationBean> brands) {
        this.brands = brands;
    }

    public ArrayList<HotelLocationBean> getAihao() {
        return aihao;
    }

    public void setAihao(ArrayList<HotelLocationBean> aihao) {
        this.aihao = aihao;
    }

    public ArrayList<HotelLocationBean> getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(ArrayList<HotelLocationBean> weizhi) {
        this.weizhi = weizhi;
    }

    public ArrayList<HotelLocationBean> getSheshifuwu() {
        return sheshifuwu;
    }

    public void setSheshifuwu(ArrayList<HotelLocationBean> sheshifuwu) {
        this.sheshifuwu = sheshifuwu;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        HotelFiltBean hfb = null;
        hfb = (HotelFiltBean) super.clone();
        hfb.brands = (ArrayList<HotelLocationBean>) brands.clone();
        hfb.aihao = (ArrayList<HotelLocationBean>) aihao.clone();
        hfb.sheshifuwu = (ArrayList<HotelLocationBean>) sheshifuwu.clone();
        hfb.weizhi = (ArrayList<HotelLocationBean>) weizhi.clone();
        return hfb;
    }
}
