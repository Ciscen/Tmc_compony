package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.hotel.interfaces.ViewManager_hotelquery;
import com.auvgo.tmc.hotel.service.LocationService;
import com.auvgo.tmc.p.PHotelQuery;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelQueryActivity extends BaseActivity implements ViewManager_hotelquery {

    @BindView(R.id.hotel_query_psgs_tv)
    TextView psgs_tv;
    @BindView(R.id.hotel_query_chailv_ll)
    View chailv_ll;
    @BindView(R.id.hotel_query_city_tv)
    TextView city_tv;
    @BindView(R.id.hotel_query_location_ll)
    View location_ll;
    @BindView(R.id.hotel_query_inDate_tv)
    TextView check_in_date_tv;
    @BindView(R.id.hotel_query_inDay_tv)
    TextView check_in_day_tv;
    @BindView(R.id.hotel_query_outDate_tv)
    TextView check_out_date_tv;
    @BindView(R.id.hotel_query_outDay_tv)
    TextView check_out_day_tv;
    @BindView(R.id.hotel_query_totalDays_tv)
    TextView total_days_tv;
    @BindView(R.id.hotel_query_keyword_tv)
    TextView keyword_tv;
    @BindView(R.id.hotel_query_level_tv)
    TextView level_tv;
    @BindView(R.id.hotel_query_query_ll)
    View query_ll;
    @BindView(R.id.hotel_query_keyword_del)
    View del;


    private PHotelQuery pHotelQuery;
    private LocationService locationService;//定位类

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_query;
    }

    @Override
    protected void initData() {
        pHotelQuery = new PHotelQuery(this, this);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        psgs_tv.setClickable(isAllowBook());
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void getData() {
        pHotelQuery.getPolicy(new PHotelQuery.OnPolicyGotListener() {
            @Override
            public void onPolicyGot() {
                if (MyApplication.mHotelPolicy != null) {
                    chailv_ll.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.hotel_query_psgs_tv)
    void onPsgsClick() {
        pHotelQuery.jumpTo(Constant.ACTIVITY_PASSENGER_FLAG);
    }

    @OnClick(R.id.hotel_query_chailv_ll)
    void onChailvClick() {
        pHotelQuery.getPolicy(new PHotelQuery.OnPolicyGotListener() {
            @Override
            public void onPolicyGot() {
                DialogUtil.showHotelPolicyDialog(HotelQueryActivity.this, MUtils.getHotelPolicyStr());
            }
        });
    }

    @OnClick(R.id.hotel_query_location_ll)
    void onLocationClick() {
        location();
    }

    @OnClick(R.id.hotel_query_city_tv)
    void onCityClick() {
        pHotelQuery.jumpTo(Constant.ACTIVITY_CITY_FLAG);
    }

    @OnClick(R.id.hotel_query_date_rl)
    void onDateClick() {
        pHotelQuery.jumpTo(Constant.ACTIVITY_CALENDAR_FLAG);
    }

    @OnClick(R.id.hotel_query_keyword_tv)
    void onKeywordClick() {
        pHotelQuery.jumpTo(Constant.ACTIVITY_HOTEL_KEYWORD_FLAG);
    }

    @OnClick(R.id.hotel_query_level_tv)
    void onLevelClick() {
        pHotelQuery.showStarPop();
    }

    @OnClick(R.id.hotel_query_query_ll)
    void onQueryClick() {
        if (pHotelQuery.getCityCode().isEmpty()) {
            pHotelQuery.getCityCodeByName();
        } else {
            pHotelQuery.getPolicy(new PHotelQuery.OnPolicyGotListener() {
                @Override
                public void onPolicyGot() {
                    pHotelQuery.jumpTo(Constant.ACTIVITY_HOTEL_LIST_FLAG);
                }
            });
        }
    }

    @OnClick(R.id.hotel_query_keyword_del)
    void onDeleteClick() {
        keyword_tv.setText("");
        pHotelQuery.setQueryText("");
        del.setVisibility(View.GONE);
    }


    @Override
    protected void setViews() {
        setDate();
        psgs_tv.setText(MyApplication.mUserInfoBean.getName());
        city_tv.setText(pHotelQuery.getCityName());
        del.setVisibility(View.GONE);
    }

    @Override
    public void updateSelectedPsgs() {
        psgs_tv.setText(pHotelQuery.getPsgsNames());
    }

    @Override
    public void setCityName() {
        city_tv.setText(pHotelQuery.getCityName());
    }

    @Override
    public void setDate() {
        check_in_date_tv.setText(pHotelQuery.getCheckInDate().substring(5));
        check_out_date_tv.setText(pHotelQuery.getCheckOutDate().substring(5));
        check_in_day_tv.setText(TimeUtils.getTomorrowWeekDay(pHotelQuery.getCheckInDate()));
        check_out_day_tv.setText(TimeUtils.getTomorrowWeekDay(pHotelQuery.getCheckOutDate()));
        total_days_tv.setText("共" + TimeUtils.compareDay(pHotelQuery.getCheckInDate(), pHotelQuery.getCheckOutDate()) + "晚");
    }

    @Override
    public void setKeyword() {
        keyword_tv.setText(pHotelQuery.getQueryText());
    }

    @Override
    public void setStarLevel() {
        level_tv.setText(pHotelQuery.getLevelStr());
    }

    @Override
    public void setDelVisibility(int visibility) {
        del.setVisibility(visibility);
    }

    @Override
    public void showPolicy(String policyStr) {
        DialogUtil.showHotelPolicyDialog(this, policyStr);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0)
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        else if (type == 1)
            locationService.setLocationOption(locationService.getOption());
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    private void location() {
        locationService.start();// 定位SDK
    }

    private String latitude;
    private String longtitude;
    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            LogFactory.d("onReceiveLocation: " + location);
            locationService.stop();
            if (null != location && location.getLocType() != BDLocation.TypeServerError && location.getCityCode() != null) {
                printLogs(location);
            } else {
                ToastUtils.showTextToast("定位失败！");
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

    private void printLogs(final BDLocation location) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        /**
         * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
         * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
         */
        sb.append(location.getTime());
        sb.append("\nlocType : ");// 定位类型
        sb.append(location.getLocType());
        sb.append("\nlocType description : ");// *****对应的定位类型说明*****
        sb.append(location.getLocTypeDescription());
        sb.append("\nlatitude : ");// 纬度
        latitude = location.getLatitude() + "";
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");// 经度
        sb.append(location.getLongitude());
        longtitude = location.getLongitude() + "";
        sb.append("\nradius : ");// 半径
        sb.append(location.getRadius());
        sb.append("\nCountryCode : ");// 国家码
        sb.append(location.getCountryCode());
        sb.append("\nCountry : ");// 国家名称
        sb.append(location.getCountry());
        sb.append("\ncitycode : ");// 城市编码
        sb.append(location.getCityCode());
        sb.append("\ncity : ");// 城市
        sb.append(location.getCity());

        sb.append("\ncityCode");
        sb.append(location.getCityCode());
        sb.append("\nDistrict : ");// 区
        sb.append(location.getDistrict());
        sb.append("\nStreet : ");// 街道
        city_tv.post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(location.getCityCode())) {
                    ToastUtils.showTextToast("定位失败，请检查网络及定位权限");
                    return;
                }
                ToastUtils.showTextToast("定位成功");
                city_tv.setText(location.getCity() + location.getDistrict() + location.getStreet());
                pHotelQuery.setCityCode("");
                pHotelQuery.setCityName(location.getCity().substring(0, location.getCity().length() - 1));
                pHotelQuery.setLatitude(String.valueOf(location.getLatitude()));
                pHotelQuery.setLongitude(String.valueOf(location.getLongitude()));
            }
        });
        sb.append(location.getStreet());
        sb.append("\naddr : ");// 地址信息
        sb.append(location.getAddrStr());
        sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
        sb.append(location.getUserIndoorState());
        sb.append("\nDirection(not all devices have value): ");
        sb.append(location.getDirection());// 方向
        sb.append("\nlocationdescribe: ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        sb.append("\nPoi: ");// POI信息
        if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
            for (int i = 0; i < location.getPoiList().size(); i++) {
                Poi poi = (Poi) location.getPoiList().get(i);
                sb.append(poi.getName() + ";");
            }
        }
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 速度 单位：km/h
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());// 卫星数目
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 海拔高度 单位：米
            sb.append("\ngps status : ");
            sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            // 运营商信息
            if (location.hasAltitude()) {// *****如果有海拔高度*****
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
            }
            sb.append("\noperationers : ");// 运营商信息
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        LogFactory.d("onReceiveLocation: " + sb.toString());
        String addr = location.getCountry() + location.getCity() + location.getDistrict() + location.getStreet() + location.getStreetNumber();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != Constant.ACTIVITY_HOTEL_HOME_FLAG) return;
        /*
        选择乘客返回
         */
        if (resultCode == Constant.ACTIVITY_PASSENGER_FLAG) {
            pHotelQuery.receivePsgData(data);
        } else if (resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            pHotelQuery.receiveCldData(data);
        } else if (resultCode == Constant.ACTIVITY_CITY_FLAG) {
            pHotelQuery.receiveCityData(data);
        } else if (resultCode == Constant.ACTIVITY_HOTEL_KEYWORD_FLAG) {
            pHotelQuery.receiveKeywordData(data);
        }
    }


}
