package com.auvgo.tmc.hotel.bean;

import com.auvgo.tmc.views.MyPickerView;

import java.util.List;

/**
 * Created by lc on 2017/2/25
 */

public class HotelCheckResultBean{

    /**
     * guaranteeRate : 0.0
     * currencyCode : ￥
     * cancelTime : 1488038399000
     * arrivalOptionTimes : [{"showTime":"14:00","value":"2017-02-25 14:00:00"},{"showTime":"15:00","value":"2017-02-25 15:00:00"},{"showTime":"16:00","value":"2017-02-25 16:00:00"},{"showTime":"17:00","value":"2017-02-25 17:00:00"},{"showTime":"18:00","value":"2017-02-25 18:00:00"},{"showTime":"19:00","value":"2017-02-25 19:00:00"},{"showTime":"20:00","value":"2017-02-25 20:00:00"},{"showTime":"21:00","value":"2017-02-25 21:00:00"},{"showTime":"22:00","value":"2017-02-25 22:00:00"},{"showTime":"23:00","value":"2017-02-25 23:00:00"},{"showTime":"23:59","value":"2017-02-25 23:59:00"},{"showTime":"凌晨01:00","value":"2017-02-26 01:00:00"},{"showTime":"凌晨02:00","value":"2017-02-26 02:00:00"},{"showTime":"凌晨03:00","value":"2017-02-26 03:00:00"},{"showTime":"凌晨04:00","value":"2017-02-26 04:00:00"},{"showTime":"凌晨05:00","value":"2017-02-26 05:00:00"},{"showTime":"凌晨06:00","value":"2017-02-26 06:00:00"}]
     */

    private double guaranteeRate;
    private String currencyCode;
    private long cancelTime;
    private List<ArrivalOptionTimesBean> arrivalOptionTimes;

    public double getGuaranteeRate() {
        return guaranteeRate;
    }

    public void setGuaranteeRate(double guaranteeRate) {
        this.guaranteeRate = guaranteeRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public long getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(long cancelTime) {
        this.cancelTime = cancelTime;
    }

    public List<ArrivalOptionTimesBean> getArrivalOptionTimes() {
        return arrivalOptionTimes;
    }

    public void setArrivalOptionTimes(List<ArrivalOptionTimesBean> arrivalOptionTimes) {
        this.arrivalOptionTimes = arrivalOptionTimes;
    }

    public static class ArrivalOptionTimesBean  extends MyPickerView.Selection{
        /**
         * showTime : 14:00
         * value : 2017-02-25 14:00:00
         */

        private String showTime;
        private String value;

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String getName() {
            return showTime;
        }
    }
}
