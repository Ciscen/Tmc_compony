package com.auvgo.tmc.hotel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.auvgo.tmc.views.MyPickerView;

/**
 * Created by lc on 2017/2/20
 */

public class HotelKeywordSearchBean extends MyPickerView.Selection implements Parcelable {

    /**
     * type : poi
     * hotelCount : 0
     * skip : true
     * provinceId : null
     * cityId : 1702
     * cityCode : 1702
     * cityNameCn : 万达广场/南昌路王府井/珠江路美食街
     * cityNameEn :
     * cityThreeSign : null
     * cityType : hotel
     * oldEnglishName : null
     */

    private String type;
    private int hotelCount;
    private boolean skip;
    private String provinceId;
    private String cityId;
    private String cityCode;
    private String cityNameCn;
    private String cityNameEn;
    private String cityThreeSign;
    private String cityType;
    private String oldEnglishName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHotelCount() {
        return hotelCount;
    }

    public void setHotelCount(int hotelCount) {
        this.hotelCount = hotelCount;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityNameCn() {
        return cityNameCn;
    }

    public void setCityNameCn(String cityNameCn) {
        this.cityNameCn = cityNameCn;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public String getCityThreeSign() {
        return cityThreeSign;
    }

    public void setCityThreeSign(String cityThreeSign) {
        this.cityThreeSign = cityThreeSign;
    }

    public String getCityType() {
        return cityType;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    public String getOldEnglishName() {
        return oldEnglishName;
    }

    public void setOldEnglishName(String oldEnglishName) {
        this.oldEnglishName = oldEnglishName;
    }

    @Override
    public String getName() {
        return cityNameCn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.cityNameCn);
    }

    public HotelKeywordSearchBean() {
    }

    protected HotelKeywordSearchBean(Parcel in) {
        this.type = in.readString();
        this.cityNameCn = in.readString();
    }

    public static final Parcelable.Creator<HotelKeywordSearchBean> CREATOR = new Parcelable.Creator<HotelKeywordSearchBean>() {
        @Override
        public HotelKeywordSearchBean createFromParcel(Parcel source) {
            return new HotelKeywordSearchBean(source);
        }

        @Override
        public HotelKeywordSearchBean[] newArray(int size) {
            return new HotelKeywordSearchBean[size];
        }
    };
}
