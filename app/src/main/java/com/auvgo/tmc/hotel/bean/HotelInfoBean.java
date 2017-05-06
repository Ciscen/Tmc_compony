package com.auvgo.tmc.hotel.bean;

/**
 * Created by lc on 2017/2/21
 */

public class HotelInfoBean {
    /**
     * hotelid : 00101012
     * phone : 0570-8776682
     * establishmentDate : 2005-05
     * renovationDate : 2011-01
     * creditCards : 牡丹卡、金穗卡、长城卡、龙卡、太平洋卡、东方卡、万事达卡、运通卡、大莱卡、JCBcard
     * introEditor : 北京诚泰商务酒店位于王府井商圈地处著名的东四休闲街暨四合院游览区。特色商店，各地名吃密集于此。周边景点有故宫、景山、北海公园、什刹海、恭王府等。毗邻三里屯酒吧街、南新仓文化饮食城、簋街（小吃不夜城）、工人体育场、农展馆。交通距地铁五号线（六号线）200米，百米之内有多条公交站交通极为方便。
     *
     * 【温馨提示】
     * 1、酒店无电梯；
     * 2、酒店没有停车场。
     * description : 酒店开业时间2005年5月1日，2011年1月全部装修，楼高3层，附楼高3层，客房总数70间（套）。
     * facilities : 12
     * traffic :
     * 酒店位于东四北大街，南临王府井商业区，北靠宋庆龄故居，西近后海，多条公交均可到达。
     * - 距离地铁5号线张自忠路站0.4公里，步行约5分钟； 交通：酒店位于东四商圈，邻近隆福寺、地铁5号线东四站，交通便利。
     * - 距离地铁5号线张自忠路站0.5公里，步行约5分钟；
     * - 距离三里屯4公里，乘坐出租车约15分钟；
     * - 距离雍和宫1.8公里，乘坐出租车约5分钟；
     * - 距离北京首都国际机场26公里，乘坐出租车约30分钟；
     * - 距离北京站3.5公里，乘坐出租车约10分钟；
     * - 距离北京西客站15公里，乘坐出租车约25分钟。
     */

    private String hotelid;
    private String phone;
    private String establishmentDate;
    private String renovationDate;
    private String creditCards;
    private String introEditor;
    private String description;
    private String facilities;
    private String traffic;

    public String getHotelid() {
        return hotelid;
    }

    public void setHotelid(String hotelid) {
        this.hotelid = hotelid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getRenovationDate() {
        return renovationDate;
    }

    public void setRenovationDate(String renovationDate) {
        this.renovationDate = renovationDate;
    }

    public String getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(String creditCards) {
        this.creditCards = creditCards;
    }

    public String getIntroEditor() {
        return introEditor;
    }

    public void setIntroEditor(String introEditor) {
        this.introEditor = introEditor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }
}
