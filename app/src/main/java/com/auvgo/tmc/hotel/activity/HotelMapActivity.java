package com.auvgo.tmc.hotel.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.utils.LogFactory;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelMapActivity extends BaseActivity implements BaiduMap.OnMarkerClickListener
        , BaiduMap.OnMapClickListener, BaiduMap.OnMapDoubleClickListener {

    @BindView(R.id.hotel_mapView)
    TextureMapView mMapView;
    private double lng;
    private double lat;
    private String name;
    private String addr;
    private BaiduMap bmap;
    private BitmapDescriptor bdA;
    private Marker m;//marker对象，就是那个标记
    private Marker mMarker;
    private InfoWindow mInfoWindow;
    private boolean isShow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_map;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        lat = Double.parseDouble(bundle.getString("lat"));
        lng = Double.parseDouble(bundle.getString("lng"));
        name = bundle.getString("name");
        addr = bundle.getString("addr");
    }

    @Override
    protected void initView() {
        bmap = mMapView.getMap();
        bdA = BitmapDescriptorFactory
                .fromResource(R.mipmap.marker);
        LatLng llA = new LatLng(lat, lng);
        bmap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                .zoom(15).target(llA).build()));
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(9).draggable(false);//set the marker options
        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);//marker animation
        m = (Marker) bmap.addOverlay(ooA);//add the marker on the map,and return the entity of marker
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        bundle.putString("name", name);
        bundle.putString("addr", addr);
        m.setExtraInfo(bundle);
    }

    @Override
    protected void initListener() {
        bmap.setOnMarkerClickListener(this);
        bmap.setOnMapClickListener(this);
        bmap.setOnMapDoubleClickListener(this);
    }

    @Override
    protected void setViews() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMarker = marker;
        showWindow();
        return true;
    }

    private void showWindow() {
        Bundle bundle = mMarker.getExtraInfo();
        //生成一个TextView用户在地图中显示InfoWindow
        View v = LayoutInflater.from(this).inflate(R.layout.marker, null);
        ((TextView) v.findViewById(R.id.marker_name)).setText(bundle.getString("name"));
        ((TextView) v.findViewById(R.id.marker_addr)).setText(bundle.getString("addr"));
        LatLng ll = mMarker.getPosition();//infoWindow的坐标
        //将marker所在的经纬度的信息转化成屏幕上的坐标
        Point p = bmap.getProjection().toScreenLocation(ll);
        p.y -= 47;
        LatLng llInfo = bmap.getProjection().fromScreenLocation(p);
        //为弹出的InfoWindow添加点击事件
        mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(v), llInfo, 0, new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                LogFactory.d("---onInfoWindowClick---");
            }
        });
        //显示InfoWindow
        bmap.showInfoWindow(mInfoWindow);
        isShow = true;
    }


    @Override
    public void onMapClick(LatLng latLng) {
        bmap.hideInfoWindow();
        isShow = false;
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        LogFactory.d("--onMapPoiClick--");
        return false;
    }

    @Override
    public void onMapDoubleClick(LatLng latLng) {
        if (isShow) {
            bmap.hideInfoWindow();
            showWindow();
        }
    }


}
