package com.auvgo.tmc.hotel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 2017/2/20
 */

public class HotelLocationBean implements Cloneable {

    /**
     * cityId : 0101
     * type : district
     * typeName : 行政区
     * positions : [{"code":"0003","name":"朝阳区"},{"code":"0011","name":"海淀区"},{"code":"0024","name":"密云县"},{"code":"0028","name":"昌平区"},{"code":"0029","name":"怀柔区"},{"code":"0006","name":"丰台区"},{"code":"0002","name":"东城区"},{"code":"0001","name":"西城区"},{"code":"0005","name":"崇文区"},{"code":"0004","name":"宣武区"},{"code":"0026","name":"延庆县"},{"code":"0027","name":"通州区"},{"code":"0032","name":"房山区"},{"code":"0030","name":"平谷区"},{"code":"0033","name":"大兴区"},{"code":"0031","name":"门头沟区"},{"code":"0025","name":"顺义区"},{"code":"0009","name":"石景山区"}]
     */

    private String cityId;
    private String type;
    private String typeName;
    private ArrayList<PositionsBean> positions;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public ArrayList<PositionsBean> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<PositionsBean> positions) {
        this.positions = positions;
    }

    public static class PositionsBean extends MyPickerView.Selection implements Parcelable, Cloneable {
        /**
         * code : 0003
         * name : 朝阳区
         */

        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.name);
        }

        public PositionsBean() {
        }

        protected PositionsBean(Parcel in) {
            this.code = in.readString();
            this.name = in.readString();
        }

        public static final Parcelable.Creator<PositionsBean> CREATOR = new Parcelable.Creator<PositionsBean>() {
            @Override
            public PositionsBean createFromParcel(Parcel source) {
                return new PositionsBean(source);
            }

            @Override
            public PositionsBean[] newArray(int size) {
                return new PositionsBean[size];
            }
        };

        @Override
        public Object clone() throws CloneNotSupportedException {
            PositionsBean pb = null;
            pb = (PositionsBean) super.clone();
            return pb;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        HotelLocationBean hlb = null;
        hlb = (HotelLocationBean) super.clone();
        hlb.positions = (ArrayList<PositionsBean>) positions.clone();
        return hlb;
    }
}
