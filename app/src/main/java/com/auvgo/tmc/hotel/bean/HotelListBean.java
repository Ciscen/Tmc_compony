package com.auvgo.tmc.hotel.bean;

import com.auvgo.tmc.hotel.interfaces.IHotelListBean;
import com.auvgo.tmc.utils.AppUtils;

import java.util.List;

/**
 * Created by lc on 2017/2/17
 */

public class HotelListBean {

    /**
     * totalCount : 15
     * list : [{"hotelId":"90101033","hotelName":"北京欣燕都连锁酒店(陶然亭店)","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/00006dGT.jpg","lowRate":310,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":11,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"北京市宣武区黑窑厂街18号(陶然亭公园北侧)","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0840-5050088","roomList":null},{"hotelId":"50101021","hotelName":"北京国宾酒店","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/000067Cz.jpg","lowRate":170,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":0,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"北京市西城区阜成门外大街甲9号(紧邻二环路阜成门桥)","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0630-5050088","roomList":null},{"hotelId":"90000329","hotelName":"北京自动化测试酒店","thumbnailUrl":"../images/common/no_pic.png","lowRate":200,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":0,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"北京国际艺术村新","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"04316666666","roomList":null},{"hotelId":"70101548","hotelName":"北京富豪宾馆（新楼A座）","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/0000ckTY.jpg","lowRate":760,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":1,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"东城区王府井大街45号（首都剧场正对面）","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0500-5236417","roomList":null},{"hotelId":"30101147","hotelName":"北京东方饭店","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/00006cNu.jpg","lowRate":760,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":0,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"西城区万明路11号(万明路与香厂路交汇处)","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0630-5609077","roomList":null},{"hotelId":"30101023","hotelName":"北京亚洲大酒店","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/0000cpbU.jpg","lowRate":67,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":0,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"东城区工体北路新中西街8号（工人体育馆西侧）","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0520-5050088","roomList":null},{"hotelId":"40101006","hotelName":"北京西苑饭店","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/00006eEA.jpg","lowRate":200,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":7,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"北京市海淀区三里河路1号(动物园正门斜对面)","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0500-5050088","roomList":null},{"hotelId":"40101669","hotelName":"北京好特热温泉酒店&酒店","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/00006p3i.jpg","lowRate":1,"currencyFlag":"$","districtName":null,"businessZoneName":null,"reviewCount":2,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"北京市丰台区方庄芳星园二区12号(近贵友大厦(方庄店))","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0870-4677428","roomList":null},{"hotelId":"50101467","hotelName":"北京大悦城酒店公寓","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/000066Hr.jpg","lowRate":200,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":0,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"北京西城区西单北大街131号（西单地铁站步行约5分钟）","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0840-6168066","roomList":null},{"hotelId":"00101120","hotelName":"北京锦航商务酒店(首都机场店)","thumbnailUrl":"http://pavo.elongstatic.com/i/Hotel120_120/00006sMr.jpg","lowRate":185,"currencyFlag":"￥","districtName":null,"businessZoneName":null,"reviewCount":0,"arrivalDate":null,"departureDate":null,"intervalDay":null,"address":"顺义区首都机场国门商务区11号(国门商务区管委会)","score":"4.3分","scoreDes":"挺好哒","traffic":null,"hotelImageList":null,"phone":"0630-8404022","roomList":null}]
     * isHaveNextPage : true
     */

    private int totalCount;
    private boolean isHaveNextPage;
    private List<ListBean> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIsHaveNextPage() {
        return isHaveNextPage;
    }

    public void setIsHaveNextPage(boolean isHaveNextPage) {
        this.isHaveNextPage = isHaveNextPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements IHotelListBean {
        /**
         * hotelId : 90101033
         * hotelName : 北京欣燕都连锁酒店(陶然亭店)
         * thumbnailUrl : http://pavo.elongstatic.com/i/Hotel120_120/00006dGT.jpg
         * lowRate : 310.0
         * currencyFlag : ￥
         * districtName : null
         * businessZoneName : null
         * reviewCount : 11
         * arrivalDate : null
         * departureDate : null
         * intervalDay : null
         * address : 北京市宣武区黑窑厂街18号(陶然亭公园北侧)
         * score : 4.3分
         * scoreDes : 挺好哒
         * traffic : null
         * hotelImageList : null
         * phone : 0840-5050088
         * roomList : null
         * starRateName:""经济型，等级
         */

        private String hotelId;
        private String hotelName;
        private String thumbnailUrl;
        private double lowRate;
        private String currencyFlag;
        private String districtName;
        private String businessZoneName;
        private int reviewCount;
        private String arrivalDate;
        private String departureDate;
        private String intervalDay;
        private String address;
        private String score;
        private String scoreDes;
        private String traffic;
        private List<String> hotelImageList;
        private String phone;
        private String starRateName;
        private Object roomList;
        private String type;
        private int distance;

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStarRateName() {
            return starRateName;
        }

        public void setStarRateName(String starRateName) {
            this.starRateName = starRateName;
        }

        public String getHotelId() {
            return hotelId;
        }

        public void setHotelId(String hotelId) {
            this.hotelId = hotelId;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public double getLowRate() {
            return lowRate;
        }

        public void setLowRate(double lowRate) {
            this.lowRate = lowRate;
        }

        public String getCurrencyFlag() {
            return currencyFlag;
        }

        public void setCurrencyFlag(String currencyFlag) {
            this.currencyFlag = currencyFlag;
        }

        public Object getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getBusinessZoneName() {
            return businessZoneName;
        }

        public void setBusinessZoneName(String businessZoneName) {
            this.businessZoneName = businessZoneName;
        }

        public int getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public String getIntervalDay() {
            return intervalDay;
        }

        public void setIntervalDay(String intervalDay) {
            this.intervalDay = intervalDay;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getScore() {
            return score;
        }

        @Override
        public String getComments() {
            return reviewCount + "条点评";
        }

        @Override
        public String getLevel() {
            return starRateName;
        }

        @Override
        public String getPos() {
            return businessZoneName;
        }

        @Override
        public String getPrice() {
            return AppUtils.keepNSecimal(lowRate + "", 2);
        }

        @Override
        public String getMark() {
            return type;
        }

        @Override
        public String getOther() {
            return "";
        }

        @Override
        public String getImg() {
            return thumbnailUrl;
        }

        @Override
        public int getDistance() {
            return distance;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getScoreDes() {
            return scoreDes;
        }

        public void setScoreDes(String scoreDes) {
            this.scoreDes = scoreDes;
        }

        public String getTraffic() {
            return traffic;
        }

        public void setTraffic(String traffic) {
            this.traffic = traffic;
        }

        public List<String> getHotelImageList() {
            return hotelImageList;
        }

        public void setHotelImageList(List<String> hotelImageList) {
            this.hotelImageList = hotelImageList;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getRoomList() {
            return roomList;
        }

        public void setRoomList(Object roomList) {
            this.roomList = roomList;
        }
    }
}
