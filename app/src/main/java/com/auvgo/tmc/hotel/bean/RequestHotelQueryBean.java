package com.auvgo.tmc.hotel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.auvgo.tmc.train.bean.requestbean.BaseRequestBean;

import java.io.Serializable;

/**
 * Created by lc on 2017/2/16
 */

public class RequestHotelQueryBean extends BaseRequestBean implements Serializable {

    /**
     * arrivalDate : 2017-02-15
     * departureDate : 2017-02-17
     * cityId : 0011
     * queryText : 查询关键词
     * brandId : 品牌编码
     * lowRate : 最小价格
     * highRate : 最大价格
     * starRate : 推荐星级
     * longitude : 经度
     * latitude : 维度
     * radius : 半径
     * pageIndex :
     * "sort":"Default艺龙默认排序 StarRankDesc推荐星级降序RateAsc价格升序RateDesc价格降序DistanceAsc距离升序",
     * pageSize :
     * districtId://行政区编码
     * facilities://设施编码
     */

    private String arrivalDate;
    private String departureDate;
    private String cityId;
    private String queryText;
    private String brandId;
    private String lowRate;
    private String highRate;
    private String starRate;
    private String longitude;
    private String latitude;
    private String radius;
    private String pageIndex;
    private String pageSize;
    private String sort;
    private String districtId;
    private String facilities;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getLowRate() {
        return lowRate;
    }

    public void setLowRate(String lowRate) {
        this.lowRate = lowRate;
    }

    public String getHighRate() {
        return highRate;
    }

    public void setHighRate(String highRate) {
        this.highRate = highRate;
    }

    public String getStarRate() {
        return starRate;
    }

    public void setStarRate(String starRate) {
        this.starRate = starRate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

}
