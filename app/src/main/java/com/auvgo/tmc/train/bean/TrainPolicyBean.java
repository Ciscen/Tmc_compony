package com.auvgo.tmc.train.bean;

/**
 * Created by lc on 2016/11/11
 */

public class TrainPolicyBean {

    /**
     * id : 36
     * companyid : 1
     * startlevel : 1
     * endlevel : 3
     * gaotie : O/
     * donche : P/M/
     * pukuai : 3/
     * controltype : 1/1/1/
     */

    private int id;
    private int companyid;
    private int startlevel;
    private int endlevel;
    private String gaotie;
    private String donche;
    private String pukuai;
    private String controltype;

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

    public int getStartlevel() {
        return startlevel;
    }

    public void setStartlevel(int startlevel) {
        this.startlevel = startlevel;
    }

    public int getEndlevel() {
        return endlevel;
    }

    public void setEndlevel(int endlevel) {
        this.endlevel = endlevel;
    }

    public String getGaotie() {
        return gaotie;
    }

    public void setGaotie(String gaotie) {
        this.gaotie = gaotie;
    }

    public String getDonche() {
        return donche;
    }

    public void setDonche(String donche) {
        this.donche = donche;
    }

    public String getPukuai() {
        return pukuai;
    }

    public void setPukuai(String pukuai) {
        this.pukuai = pukuai;
    }

    public String getControltype() {
        return controltype;
    }

    public void setControltype(String controltype) {
        this.controltype = controltype;
    }
}
