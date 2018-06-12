package com.android.tripin.fragment.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.presenter.MapPresenter;
import com.android.tripin.view.IMapView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class MapFragment extends BaseFragment implements IMapView, OnClickListener  {

    private final static String TAG = MapFragment.class.getSimpleName();

    //  Map
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    UiSettings mUiSettings;
    //  当前地图缩放级别
    private double zoomLevel;
    //  按钮
    private ImageButton ib_location;
    //定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    //是否第一次定位，如果是第一次定位的话要将自己的位置显示在地图 中间
    private boolean isFirstLocation = true;
    private View mView;
    private MapPresenter mapPresenter;


    /**
     *  自定义的定位监听
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && null != mMapView) {
                int errorCode = location.getLocType();
                showInfo(String.valueOf(errorCode));
                showInfo(location.getLocTypeDescription());
                //将获取的location信息给百度map
                MyLocationData data = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100)
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();
                mBaiduMap.setMyLocationData(data);
                if (isFirstLocation) {
                    //获取经纬度
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
                    //mBaiduMap.setMapStatus(status);//直接到中间
                    mBaiduMap.animateMapStatus(status);//动画的方式到中间
                    isFirstLocation = false;
                    showInfo("位置：" + location.getAddrStr());
                }
            }
        }
    }

    /**
     * 显示消息
     * @param str 需要显示的Toast的字符串
     */
    private void showInfo(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * onCreateView
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        //  自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //  包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        //初始化控件
        initView();
        //初始化地图
        initMapView();
        //定位
        initLocation();
        return mView;
    }

    private void initView() {
        ib_location = (ImageButton) mView.findViewById(R.id.ib_location);
        ib_location.setOnClickListener(this);
    }

    public void initMapView() {
        mMapView = mView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //  显示交通状况
        mBaiduMap.setTrafficEnabled(true);
        //  实例化UiSettings类对象
        mUiSettings = mBaiduMap.getUiSettings();
        //  指南针
        mUiSettings.setOverlookingGesturesEnabled(true);
        //  禁用俯视（3D）功能
        mUiSettings.setOverlookingGesturesEnabled(false);
        //  去掉标尺
        mMapView.showZoomControls(false);
        //  去掉缩放按钮
        mMapView.showScaleControl(false);
        //  不显示百度地图Logo
        mMapView.removeViewAt(1);
        //  设置地图状态改变监听器
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
            }
            @Override
            public void onMapStatusChange(MapStatus arg0) {
                //当地图状态改变的时候，获取放大级别
                zoomLevel = arg0.zoom;
            }
        });

        //调用BaiduMap对象的setOnMarkerDragListener方法设置Marker拖拽的监听
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
                //拖拽中
            }
            public void onMarkerDragEnd(Marker marker) {
                //拖拽结束
            }
            public void onMarkerDragStart(Marker marker) {
                //开始拖拽
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                showTrip();
                addPin(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    private void initLocation() {
        //  定位客户端的设置
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationListener = new MyLocationListener();
        //  注册监听
        mLocationClient.registerLocationListener(mLocationListener);
        //  配置定位
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");// 坐标类型
        option.setIsNeedAddress(true);//    可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//  打开Gps
        option.setScanSpan(1000);// 1000毫秒定位一次
        option.disableCache(true);//    禁止启用缓存定位
        option.setIsNeedLocationPoiList(false);//    可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//   可选，定位SDK内部是一个service，并放到了独立进程。设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setWifiCacheTimeOut(5*60*1000);//    可选，7.2版本新增能力，如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        mLocationClient.setLocOption(option);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_location:
                isFirstLocation = true;
                showInfo("返回自己位置");
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        //关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        if(mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在Fragment执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在Fragment执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    /**
     * 显示当前Plan下的所有pin和route
     */
    @Override
    public void showTrip() {
        //  创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        //  设置坐标点
        LatLng point1 = new LatLng(39.92235, 116.380338);
        LatLng point2 = new LatLng(39.947246, 116.414977);
        //  构建Marker图标
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_markb);
        //  创建OverlayOptions属性
        OverlayOptions option1 =  new MarkerOptions()
                .position(point1)
                .icon(bitmap1);
        OverlayOptions option2 =  new MarkerOptions()
                .position(point2)
                .icon(bitmap2);
        //将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        //在地图上批量添加
        mBaiduMap.addOverlays(options);
    }

    /**
     * 向地图中添加一个Pin
     */
    @Override
    public void addPin() {

    }

    /**
     * 向地图中添加一个Pin
     */
    public void addPin(LatLng latLng) {
        //  定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
        //  构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        //  在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    /**
     * 将地图中的Pin移除
     */
    @Override
    public void removePin() {

    }

    /**
     * 将当前地图中的所有点移除
     */
    @Override
    public void removeAllPins() {
        mBaiduMap.clear();
    }

    /**
     * 编辑地图中的pin
     */
    @Override
    public void editPin() {

    }

    /**
     * 返回到当前位置
     */
    @Override
    public void getMyLocation() {

    }

}
