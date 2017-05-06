package com.auvgo.tmc.plane.bean;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

/**
 * Created by lc on 2017/2/24
 */

public class RequestHotelValidateBean extends BaseRequestBean {

    /**
     * arrivalDate :
     * departureDate :
     * hotelId :
     * roomTypeId :
     * ratePlanId :
     * totalPrice :
     */

    private String arrivalDate;
    private String departureDate;
    private String hotelId;
    private String roomTypeId;
    private String ratePlanId;
    private double totalPrice;

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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRatePlanId() {
        return ratePlanId;
    }

    public void setRatePlanId(String ratePlanId) {
        this.ratePlanId = ratePlanId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
