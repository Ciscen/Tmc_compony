package com.auvgo.tmc.hotel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.auvgo.tmc.views.MyPickerView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lc on 2017/2/18
 */

public class HotelKeywordBean extends MyPickerView.Selection {

    /**
     * cityId : 0101
     * type : 1
     * name : 交通枢纽
     * typeNameList : [{"name":"北京站","type":1,"propertiesId":"706","lat":"39.909806","lng":"116.433643","accept":false},{"name":"北京北站","type":1,"propertiesId":"707","lat":"39.948758","lng":"116.360284","accept":false},{"name":"北京东站","type":1,"propertiesId":"708","lat":"39.908693","lng":"116.491176","accept":false},{"name":"北京南站","type":1,"propertiesId":"709","lat":"39.87113","lng":"116.385521","accept":false},{"name":"北京西站","type":1,"propertiesId":"710","lat":"39.901712","lng":"116.327874","accept":false},{"name":"北京南苑机场","type":1,"propertiesId":"519","lat":"39.797571","lng":"116.403545","accept":false},{"name":"北京首都国际机场","type":1,"propertiesId":"518","lat":"40.061075","lng":"116.621192","accept":false}]
     */

    private String cityId;
    private int type;
    private String name;
    private List<TypeNameListBean> typeNameList;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeNameListBean> getTypeNameList() {
        return typeNameList;
    }

    public void setTypeNameList(List<TypeNameListBean> typeNameList) {
        this.typeNameList = typeNameList;
    }

    public static class TypeNameListBean extends MyPickerView.Selection implements Parcelable {
        /**
         * name : 北京站
         * type : 1
         * propertiesId : 706
         * lat : 39.909806
         * lng : 116.433643
         * accept : false
         */

        private String name;
        private int type;
        private String propertiesId;
        private String lat;
        private String lng;
        private boolean accept;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPropertiesId() {
            return propertiesId;
        }

        public void setPropertiesId(String propertiesId) {
            this.propertiesId = propertiesId;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public boolean isAccept() {
            return accept;
        }

        public void setAccept(boolean accept) {
            this.accept = accept;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeInt(this.type);
            dest.writeString(this.propertiesId);
            dest.writeString(this.lat);
            dest.writeString(this.lng);
            dest.writeByte(this.accept ? (byte) 1 : (byte) 0);
        }

        public TypeNameListBean() {
        }

        protected TypeNameListBean(Parcel in) {
            this.name = in.readString();
            this.type = in.readInt();
            this.propertiesId = in.readString();
            this.lat = in.readString();
            this.lng = in.readString();
            this.accept = in.readByte() != 0;
        }

        public static final Parcelable.Creator<TypeNameListBean> CREATOR = new Parcelable.Creator<TypeNameListBean>() {
            @Override
            public TypeNameListBean createFromParcel(Parcel source) {
                return new TypeNameListBean(source);
            }

            @Override
            public TypeNameListBean[] newArray(int size) {
                return new TypeNameListBean[size];
            }
        };
    }
}
