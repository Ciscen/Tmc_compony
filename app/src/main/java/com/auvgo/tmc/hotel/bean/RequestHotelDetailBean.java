package com.auvgo.tmc.hotel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

/**
 * Created by lc on 2017/2/17
 */

public class RequestHotelDetailBean extends BaseRequestBean implements Parcelable {

    /**
     * hotelId : 213213
     * arrivalDate : 2017-02-15
     * departureDate : 2017-02-17
     */

    private String hotelId;
    private String arrivalDate;
    private String departureDate;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hotelId);
        dest.writeString(arrivalDate);
        dest.writeString(departureDate);
    }

    public static final Creator<RequestHotelDetailBean> CREATOR = new Creator<RequestHotelDetailBean>() {
        @Override
        public RequestHotelDetailBean createFromParcel(Parcel source) {
            RequestHotelDetailBean b = new RequestHotelDetailBean();
            b.setHotelId(source.readString());
            b.setArrivalDate(source.readString());
            b.setDepartureDate(source.readString());
            return b;
        }

        @Override
        public RequestHotelDetailBean[] newArray(int size) {

            return new RequestHotelDetailBean[size];
        }
    };
}
