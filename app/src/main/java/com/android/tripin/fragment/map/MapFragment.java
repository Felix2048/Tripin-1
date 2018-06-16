package com.android.tripin.fragment.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.entity.Pin;
import com.android.tripin.entity.Route;
import com.android.tripin.enums.PinStatus;
import com.android.tripin.enums.Transportation;
import com.android.tripin.manager.DataManager;
import com.android.tripin.presenter.MapPresenter;
import com.android.tripin.view.IMapView;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class MapFragment extends BaseFragment implements IMapView, OnClickListener, OnLongClickListener {

    private final static String TAG = MapFragment.class.getSimpleName();

    //  Map
    MapView mMapView;
    BaiduMap mBaiduMap;
    UiSettings mUiSettings;
    //  当前地图缩放级别
    double zoomLevel;
    //  按钮
    ImageButton ib_location;
    ImageButton ib_get_back_to_current_pin;
    ImageButton ib_previous_pin;
    ImageButton ib_next_pin;
    ImageButton ib_add_pin;
    ImageButton ib_add_pin_confirm;
    ImageButton ib_add_pin_cancel;
    ImageButton ib_delete_pin;
    ImageButton ib_delete_pin_cancel;
    ImageButton ib_delete_pin_confirm;
    //  定位相关
    LocationClient mLocationClient;
    MyLocationListener mLocationListener;
    boolean isFirstLocation = false; //  是否第一次定位，如果是第一次定位的话要将自己的位置显示在地图 中间
    //  构建Marker图标
    BitmapDescriptor pinIcon;
    BitmapDescriptor pinSelectedIcon;
    //  地址编码与逆地址编码
    GeoCoder mGeoCoder;
    //  搜索相关
    SuggestionSearch mSuggestionSearch;
    PoiSearch mPoiSearch;
    //  路线相关
    RoutePlanSearch mRoutePlanSearch;
    //  MapInfo
    LatLng mapCenter;
    String mapCenterAddress;
    String cityInMap;   //  地图中心点所在城市

    private View mView;
    private MapPresenter mapPresenter;

    MapFragmentAuxiliary mapFragmentAuxiliary = new MapFragmentAuxiliary(this);
    LayoutInflater inflater;
    LinearLayout pin_adding;    //  显示正在添加的Pin的icon
    RelativeLayout pin_info;    //  显示Pin的信息

    List<Pin> pinList = new ArrayList<>();
    List<Route> routeList = new ArrayList<>();
    List<Marker> poiMarkerList = new ArrayList<>();
    Map<Pin, Marker> pinMarkerMap = new HashMap<>();
    Map<Route, List<? extends RouteLine>> routeLineListMap = new HashMap<>();
    List<PoiInfo> poiInfoList;
    List<Pin> pinDeleteList = new ArrayList<>();    //  将要被删除的Pin

    boolean isAddingPin = false;    //  是否向地图正在添加Pin
    boolean isDeletingPins = false;  //  是否从地图中删除Pin
    int currentPinIndex;    //  当前pin的index
    double matchedPoiHash = Double.POSITIVE_INFINITY;   //  用户将当前pin_adding的icon拖动到poi点的附近，匹配到的poi的hash
    boolean mapStatusChangeIgnored = false; //  若为true，则此次mapStatusChange将会被忽略

    List<WalkingRouteLine> walkingRouteLineList;

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
        this.inflater = inflater;
        //  初始化控件
        initView();
        //  初始化地图
        initMapView();
        //  定位
        initLocation();

        Pin pin1 = new Pin(1, 1, 31.24166, 121.48612,  "上海1",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第1个pin");
        Pin pin2 = new Pin(2, 1,31.24296, 121.48602,  "上海2",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第2个pin");
        Pin pin3 = new Pin(3, 1,31.24168, 121.49812,  "上海3",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第3个pin");
        Pin pin4 = new Pin(4, 1, 31.24178, 121.49809,  "上海4",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第4个pin");

        Route route1 = new Route(1, 1, 1, 2, Transportation.MASS_TRANSIT, 0, true);
        Route route2 = new Route(2, 1, 2, 3, Transportation.WALK, 0, true);
        Route route3 = new Route(3, 1, 3, 4, Transportation.WALK, 0, true);


        //  加载pinList
        pinList.add(pin1);
        pinList.add(pin2);
        pinList.add(pin3);
        pinList.add(pin4);

        //  加载routeList
        routeList.add(route1);
        routeList.add(route2);
        routeList.add(route3);

        //  将trip显示在地图上
        mapFragmentAuxiliary.showTrip();

        return mView;
    }

    private void initView() {
        //  载入控件

        ib_location = (ImageButton) mView.findViewById(R.id.ib_location);
        ib_get_back_to_current_pin = (ImageButton) mView.findViewById(R.id.ib_get_back_to_current_pin);
        ib_previous_pin = (ImageButton) mView.findViewById(R.id.ib_previous_pin);
        ib_next_pin = (ImageButton) mView.findViewById(R.id.ib_next_pin);
        ib_add_pin = (ImageButton) mView.findViewById(R.id.ib_add_pin);
        ib_add_pin_confirm = (ImageButton) mView.findViewById(R.id.ib_add_pin_confirm);
        pin_adding = (LinearLayout) mView.findViewById(R.id.pin_adding);
        ib_add_pin_cancel = (ImageButton) mView.findViewById(R.id.ib_add_pin_cancel);
        ib_delete_pin = (ImageButton) mView.findViewById(R.id.ib_delete_pin);
        ib_delete_pin_cancel = (ImageButton) mView.findViewById(R.id.ib_delete_pin_cancel);
        ib_delete_pin_confirm = (ImageButton) mView.findViewById(R.id.ib_delete_pin_confirm);
        //  添加点击事件Listener
        ib_location.setOnClickListener(this);
        ib_get_back_to_current_pin.setOnClickListener(this);
        ib_previous_pin.setOnClickListener(this);
        ib_next_pin.setOnClickListener(this);
        ib_add_pin.setOnClickListener(this);
        ib_add_pin_confirm.setOnClickListener(this);
        pin_adding.setOnLongClickListener(this);
        ib_add_pin_cancel.setOnClickListener(this);
        ib_delete_pin.setOnClickListener(this);
        ib_delete_pin_cancel.setOnClickListener(this);
        ib_delete_pin_confirm.setOnClickListener(this);
        //   设置可见性
        ib_add_pin.setVisibility(View.VISIBLE);
        ib_add_pin_confirm.setVisibility(View.GONE);
        pin_adding.setVisibility(View.GONE);
        ib_add_pin_cancel.setVisibility(View.GONE);
        ib_delete_pin.setVisibility(View.VISIBLE);
        ib_delete_pin_cancel.setVisibility(View.GONE);
        ib_delete_pin_confirm.setVisibility(View.GONE);

        pinIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin);
        pinSelectedIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_selected);
    }

    public void initMapView() {
        mMapView = mView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mGeoCoder = GeoCoder.newInstance();
        mSuggestionSearch = SuggestionSearch.newInstance();
        mPoiSearch = PoiSearch.newInstance();
        mRoutePlanSearch = RoutePlanSearch.newInstance();

        //  为mRoutePlanSearch添加Listener
        mRoutePlanSearch.setOnGetRoutePlanResultListener(new MyOnGetRoutePlanResultListener(this));
        //  为PoiSearch添加Listener
        mPoiSearch.setOnGetPoiSearchResultListener(new MyOnGetPoiSearchResultListener(this));
        //  为GeoCoder添加Listener
        mGeoCoder.setOnGetGeoCodeResultListener(new MyOnGetGeoCoderResultListener(this));
        //  为SuggestionSearch添加Listener
        mSuggestionSearch.setOnGetSuggestionResultListener(new MyOnGetSuggestionResultListener(this));
        //  显示交通状况
        mBaiduMap.setTrafficEnabled(true);
        //  实例化UiSettings类对象
        mUiSettings = mBaiduMap.getUiSettings();
        //  指南针
        mUiSettings.setOverlookingGesturesEnabled(true);
        //  禁用俯视（3D）功能
        mUiSettings.setOverlookingGesturesEnabled(false);
        //  去掉缩放按钮
        mMapView.showZoomControls(false);
        //  去掉标尺
        mMapView.showScaleControl(false);
        //  不显示百度地图Logo
        mMapView.removeViewAt(1);

        //  设置缩放级别
        mapFragmentAuxiliary.setZoomLevelInMap(18);
        zoomLevel = 18;

        //  设置地图状态改变监听器
        mBaiduMap.setOnMapStatusChangeListener(new MyOnMapStatusChangeListener(this));

        //添加marker点击事件的监听
        mBaiduMap.setOnMarkerClickListener(new MyOnMarkerClickListener(this));

        //  添加地图点击的Listener
        mBaiduMap.setOnMapClickListener(new MyOnMapClickListener(this));
    }

    private void initLocation() {
        //  定位客户端的设置
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationListener = new MyLocationListener(this);
        //  注册监听
        mLocationClient.registerLocationListener(mLocationListener);
        //  配置定位
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");// 坐标类型
        option.setIsNeedAddress(true);//    可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//  打开Gps
        option.setScanSpan(1000);// 1000毫秒定位一次
        option.disableCache(true);//    禁止启用缓存定位
        option.setIsNeedLocationPoiList(true);//    可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
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
                break;
            case R.id.ib_get_back_to_current_pin:
                mapFragmentAuxiliary.getBackToCurrentPin();
                break;
            case R.id.ib_next_pin:
                mapFragmentAuxiliary.getToNextPin();
                break;
            case R.id.ib_previous_pin:
                mapFragmentAuxiliary.getToPreviousPin();
                break;
            case R.id.ib_add_pin:
                addPinStart();
                break;
            case R.id.ib_add_pin_confirm:
                addPinConfirm();
                break;
            case R.id.ib_add_pin_cancel:
                addPinCancel();
                break;
            case R.id.ib_delete_pin:
                deletePinsStart();
                break;
            case R.id.ib_delete_pin_confirm:
                deletePinsConfirm();
                break;
            case R.id.ib_delete_pin_cancel:
                deletePinsCancel();
                break;
            default:
                break;
        }
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.pin_adding:   //  长按Pin_Adding的icon进行确认
                addPinConfirm();
                break;

            default:
                break;
        }
        return true;
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
        // 释放定位监听
        mLocationClient.unRegisterLocationListener(mLocationListener);
        // 在Fragment执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        // 释放地理编码检索实例
        mGeoCoder.destroy();
        // 释放在线建议检索实例
        mSuggestionSearch.destroy();
        // 释放poi检索实例
        mPoiSearch.destroy();
        // 释放路线搜索实例
        mRoutePlanSearch.destroy();
    }

    /**
     * 向地图中添加一个Pin，并将这个Pin加入到当前Plan
     * 在地图中心显示一个Pin的icon，用户通过拖动地图来选择要把Pin添加到什么位置
     * 拖动地图的同时请求更新要添加的Pin的位置的信息
     */
    public void addPinStart() {
        isAddingPin = true;
        //  显示Pin的icon
        pin_adding.setVisibility(View.VISIBLE);
        ib_add_pin.setVisibility(View.GONE);
        ib_delete_pin.setVisibility(View.GONE);
        ib_add_pin_confirm.setVisibility(View.VISIBLE);
        ib_add_pin_cancel.setVisibility(View.VISIBLE);
        addPinUpdate();
    }

    /**
     * 更新要添加的Pin的位置的信息,获取mapCenter的经纬度、逆地址编码和周边poi信息
     */
    public void addPinUpdate() {
        //  隐藏现有的infowindow
        mBaiduMap.hideInfoWindow();
        //  获取mapCenter的经纬度
        mapCenter = mBaiduMap.getMapStatus().target;
        mapFragmentAuxiliary.requestMapCenterReverseGeoCode();
        mapFragmentAuxiliary.clearPoiInfo();
        if (zoomLevel >= 14) {   //  若地图缩放太小，则不进行poi搜索
            mapFragmentAuxiliary.requestMapCenterPoi();
        }
    }

    /**
     * 确认向地图中添加一个Pin
     * pre-condition: addPin()已经执行
     */
    public void addPinConfirm() {
        pin_adding.setVisibility(View.GONE);
        //  以当前位置构造一个Pin
        Pin pin = new Pin(DataManager.getPlanID(), mapCenter.latitude, mapCenter.longitude, mapCenterAddress, new Date(), new Date(), PinStatus.WANTED, "");
        if(mapFragmentAuxiliary.addPin(pin)) {   //  判断是否成功添加Pin
            isAddingPin = false;
            ib_add_pin.setVisibility(View.VISIBLE);
            ib_add_pin_confirm.setVisibility(View.GONE);
            ib_add_pin_cancel.setVisibility(View.GONE);
            ib_delete_pin.setVisibility(View.VISIBLE);
            //  隐藏Pin的icon
            pin_adding.setVisibility(View.GONE);
            pinList.add(pin);
            mapFragmentAuxiliary.clearPoiInfo();
            mapFragmentAuxiliary.selectPin(pin);
            matchedPoiHash = Double.POSITIVE_INFINITY;
        }
        else{
            pin_adding.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 取消当前向地图添加pin的操作
     */
    public void addPinCancel() {
        isAddingPin = false;
        ib_add_pin.setVisibility(View.VISIBLE);
        ib_add_pin_confirm.setVisibility(View.GONE);
        ib_add_pin_cancel.setVisibility(View.GONE);
        ib_delete_pin.setVisibility(View.VISIBLE);
        //  隐藏Pin的icon
        pin_adding.setVisibility(View.GONE);
        mapFragmentAuxiliary.clearPoiInfo();
    }

    public void deletePinsStart() {
        //  隐藏infowindow
        mBaiduMap.hideInfoWindow();
        ib_add_pin.setVisibility(View.GONE);
        ib_delete_pin.setVisibility(View.GONE);
        ib_delete_pin_cancel.setVisibility(View.VISIBLE);
        ib_delete_pin_confirm.setVisibility(View.VISIBLE);
        isDeletingPins = true;
    }

    public void deletePinsConfirm() {
        if (!pinDeleteList.isEmpty()) {
            ib_add_pin.setVisibility(View.VISIBLE);
            ib_delete_pin.setVisibility(View.VISIBLE);
            ib_delete_pin_cancel.setVisibility(View.GONE);
            ib_delete_pin_confirm.setVisibility(View.GONE);
            for (Pin pin : pinDeleteList) {
                mapFragmentAuxiliary.deletePin(pin);
            }
            pinDeleteList.clear();
            isDeletingPins = false;
        }
        else {
            mapFragmentAuxiliary.showToast("未选择要删除的Pin");
        }
    }

    public void deletePinsCancel() {
        ib_add_pin.setVisibility(View.VISIBLE);
        ib_delete_pin.setVisibility(View.VISIBLE);
        ib_delete_pin_cancel.setVisibility(View.GONE);
        ib_delete_pin_confirm.setVisibility(View.GONE);
        if (!pinDeleteList.isEmpty()) {
            for (Pin pin : pinDeleteList) {
                pinMarkerMap.get(pin).setIcon(pinIcon); //  取消被选中
            }
            pinDeleteList.clear();
        }
        isDeletingPins = false;
    }



}
