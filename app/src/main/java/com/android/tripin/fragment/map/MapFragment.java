package com.android.tripin.fragment.map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tripin.MainActivity;
import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.entity.Pin;
import com.android.tripin.enums.PinStatus;
import com.android.tripin.manager.DataManager;
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
import com.baidu.mapapi.map.InfoWindow;
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
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

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
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    UiSettings mUiSettings;
    //  当前地图缩放级别
    private double zoomLevel;
    //  按钮
    private ImageButton ib_location;
    private ImageButton ib_get_back_to_current_pin;
    private ImageButton ib_previous_pin;
    private ImageButton ib_next_pin;
    private ImageButton ib_add_pin;
    private ImageButton ib_add_pin_confirm;
    //  定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirstLocation = false; //  是否第一次定位，如果是第一次定位的话要将自己的位置显示在地图 中间
    //  构建Marker图标
    private BitmapDescriptor bitmapDescriptor;
    //  地址编码与逆地址编码
    private GeoCoder geoCoder;
    private LatLng mapCenter;
    private String mapCenterAddress;

    private boolean isAddingPin = false;    //  是否向地图正在添加Pin
    private boolean showPinInfo = false;
    private int currentPinIndex;    //  当前pin的index

    private View mView;
    private LinearLayout pin_adding;    //  显示正在添加的Pin的icon
    private RelativeLayout pin_info;    //  显示Pin的信息
    private MapPresenter mapPresenter;

    private List<Pin> pinList = new ArrayList<>();
    private Map<Pin, Marker> pinMarkerMap = new HashMap<>();

    /**
     *  自定义的定位监听
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && null != mMapView) {
                int errorCode = location.getLocType();

//                showInfo(String.valueOf(errorCode));
//                showInfo(location.getLocTypeDescription());

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
                    moveToLocationInMap(latLng);
                    isFirstLocation = false;
                    showInfo("位置：" + location.getAddrStr());
                }
            }
        }
    }

    /**
     *  自定义的GeoCoderResult监听
     */
    private class MyOnGetGeoCoderResultListener implements OnGetGeoCoderResultListener {
        // 反地理编码查询结果回调函数
        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                // 没有检测到结果
                showInfo("抱歉，未能找到结果");
            }
            else {
                showInfo("位置：" + result.getAddress());
                mapCenterAddress = result.getAddress();
            }
        }
        // 地理编码查询结果回调函数
        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                // 没有检测到结果
                showInfo("抱歉，未能找到结果");
            }
            else {
                showInfo("位置：" + result.getLocation().toString());
            }
        }
    };

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
        //  初始化控件
        initView();
        //  初始化地图
        initMapView();
        //  定位
        initLocation();

        //  加载pinList
        pinList.add(new Pin(0, 39.914935, 116.403119, "天安门",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第1个pin"));
        pinList.add(new Pin(1, 31.24166, 121.48612,  "外滩",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第2个pin"));

        //  将PinList显示在地图上
        showTrip();

        return mView;
    }

    private void initView() {
        //  载入控件
        pin_info = (RelativeLayout) mView.findViewById(R.id.pin_info);
        ib_location = (ImageButton) mView.findViewById(R.id.ib_location);
        ib_get_back_to_current_pin = (ImageButton) mView.findViewById(R.id.ib_get_back_to_current_pin);
        ib_previous_pin = (ImageButton) mView.findViewById(R.id.ib_previous_pin);
        ib_next_pin = (ImageButton) mView.findViewById(R.id.ib_next_pin);
        ib_add_pin = (ImageButton) mView.findViewById(R.id.ib_add_pin);
        ib_add_pin_confirm = (ImageButton) mView.findViewById(R.id.ib_add_pin_confirm);
        pin_adding = (LinearLayout) mView.findViewById(R.id.pin_adding);
        //  添加点击事件Listener
        ib_location.setOnClickListener(this);
        ib_get_back_to_current_pin.setOnClickListener(this);
        ib_previous_pin.setOnClickListener(this);
        ib_next_pin.setOnClickListener(this);
        ib_add_pin.setOnClickListener(this);
        ib_add_pin_confirm.setOnClickListener(this);
        pin_adding.setOnLongClickListener(this);
        //   设置可见性
        ib_add_pin.setVisibility(View.VISIBLE);
        ib_add_pin_confirm.setVisibility(View.INVISIBLE);
        pin_adding.setVisibility(View.GONE);

        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin);

    }

    public void initMapView() {
        mMapView = mView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        geoCoder = GeoCoder.newInstance();

        //  为GeoCoder添加Listener
        geoCoder.setOnGetGeoCodeResultListener(new MyOnGetGeoCoderResultListener());
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

        //  设置缩放级别
        MapStatusUpdate status = MapStatusUpdateFactory.zoomTo(17);
        mBaiduMap.animateMapStatus(status);//动画的方式到中间

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
                //  当地图移动结束后，更新地图的中心点
                if(isAddingPin) {
                    requestMapCenterReverseGeoCode();
                }
            }
            @Override
            public void onMapStatusChange(MapStatus arg0) {
                //  当地图状态改变的时候，获取放大级别
                zoomLevel = arg0.zoom;
                if(isAddingPin) {
                    showInfo("正在获取位置...");
                }
            }
        });

        //添加marker点击事件的监听
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (!isAddingPin) {
                    selectPin(marker);
                    getBackToCurrentPin();
                }
                return true;
            }
        });

        //  添加地图点击的Listener
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //  点击地图时隐藏pin的信
                pin_info.setVisibility(View.GONE);
                showPinInfo = false;
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
                break;
            case R.id.ib_get_back_to_current_pin:
                getBackToCurrentPin();
                break;
            case R.id.ib_next_pin:
                getToNextPin();
                break;
            case R.id.ib_previous_pin:
                getToPreviousPin();
                break;
            case R.id.ib_add_pin:
                addPin();
                break;
            case R.id.ib_add_pin_confirm:
                confirmPin();
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
                confirmPin();
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
        //在Fragment执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        // 释放地理编码检索实例
         geoCoder.destroy();
    }

    /**
     * 将当前地图移动到latLng的位置
     * @param latLng 要移动的点位置
     */
    private void moveToLocationInMap(LatLng latLng) {
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
        //mBaiduMap.setMapStatus(status);//直接到中间
        mBaiduMap.animateMapStatus(status);//动画的方式到中间
        isFirstLocation = false;
    }

    /**
     * 选中标记Pin覆盖物，显示infoWindow，点击infoWindow显示PinInfoView
     * @param pin 被选中的Pin
     */
    private void selectPin(Pin pin) {
        selectPin(pinMarkerMap.get(pin));
    }

    /**
     * 选中标记Pin覆盖物，显示infoWindow，点击infoWindow显示PinInfoView
     * @param marker 被选中的标记覆盖物
     */
    private void selectPin(Marker marker) {
        //  隐藏infowindow
        mBaiduMap.hideInfoWindow();
        //  从marker中获取info信息
        Bundle bundle = marker.getExtraInfo();
        Pin pin = (Pin) bundle.getSerializable("pin");
        //  修改currentPinIndex
        currentPinIndex = pinList.indexOf(pin);
        //  将信息显示在界面上
        TextView pin_title = (TextView)pin_info.findViewById(R.id.pin_title);
        pin_title.setText(pin.getPinTitle());
        TextView pin_notes = (TextView)pin_info.findViewById(R.id.pin_notes);
        pin_notes.setText(pin.getPinNotes());
        //  加载infoWindow中的布局
        TextView textView = new TextView(getContext());
        textView.setBackgroundResource(R.drawable.common_bg_with_radius_and_border);
        textView.setPadding(20, 10, 20, 20);
        textView.setTextColor(Color.BLACK);
        textView.setText(pin.getPinTitle());
        textView.setGravity(Gravity.CENTER);
        textView.setElevation(4);
        BitmapDescriptor infoWindowView = BitmapDescriptorFactory.fromView(textView);
        //  infoWindow点击事件
        InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                //  将布局显示出来
                pin_info.setVisibility(View.VISIBLE);
            }
        };
        //  显示infoWindow
        InfoWindow infoWindow = new InfoWindow(infoWindowView, new LatLng(pin.getPinLatitude(), pin.getPinLongitude()), -100, listener);
        mBaiduMap.showInfoWindow(infoWindow);
    }

    /**
     * 返回给定的的PinIndex的位置
     * @param pinIndex 要移动到的pin的index
     */
    public void getBackToPin(int pinIndex) {
        if(null != pinList && !pinList.isEmpty() && pinIndex >= 0 && pinIndex < pinList.size()) {
            Pin pin = pinList.get(pinIndex);
            moveToLocationInMap(new LatLng(pin.getPinLatitude(), pin.getPinLongitude()));
            selectPin(pin);
        }
    }

    /**
     * 返回当前的Pin的位置
     */
    public void getBackToCurrentPin() {
        getBackToPin(currentPinIndex);
    }

    /**
     * 返回当前的Pin的位置
     */
    public void getToNextPin() {
        if(currentPinIndex == pinList.size() - 1) {
            currentPinIndex = 0;
        }
        else {
            currentPinIndex++;
        }
        getBackToCurrentPin();
    }


    /**
     * 返回当前的Pin的位置
     */
    public void getToPreviousPin() {
        if (currentPinIndex == 0) {
            currentPinIndex = pinList.size() - 1;
        }
        else {
            currentPinIndex--;
        }
        getBackToCurrentPin();
    }
    /**
     * 显示当前Plan下的所有pin和route
     */
    @Override
    public void showTrip() {
        //  清空地图
        mBaiduMap.clear();
        for (Pin pin : pinList) {
            addPin(pin);
        }
        //  初始化currentPinIndex为0
        currentPinIndex = 0;
        getBackToCurrentPin();
    }

    /**
     * 向地图中添加一个Pin，并将这个Pin加入到当前Plan
     * 在地图中心显示一个Pin的icon，用户通过拖动地图来选择要把Pin添加到什么位置
     * 拖动地图的同时请求mapCenter的位置的逆地址编码
     */
    @Override
    public void addPin() {
        isAddingPin = true;
        //  显示Pin的icon
        pin_adding.setVisibility(View.VISIBLE);
        ib_add_pin.setVisibility(View.INVISIBLE);
        ib_add_pin_confirm.setVisibility(View.VISIBLE);
        requestMapCenterReverseGeoCode();
    }

    public void requestMapCenterReverseGeoCode() {
        //  获取mapCenter的经纬度
        mapCenter = mBaiduMap.getMapStatus().target;
        //  请求逆地址编码
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(mapCenter));
    }

    /**
     * 确认向地图中添加一个Pin
     * pre-condition: addPin()已经执行
     */
    @Override
    public void confirmPin() {
        pin_adding.setVisibility(View.GONE);
        //  以当前位置构造一个Pin
        Pin pin = new Pin(DataManager.getPlanID(), mapCenter.latitude, mapCenter.longitude, mapCenterAddress, new Date(), new Date(), PinStatus.WANTED, "");
        if(addPin(pin)) {   //  判断是否成功添加Pin
            isAddingPin = false;
            ib_add_pin.setVisibility(View.VISIBLE);
            ib_add_pin_confirm.setVisibility(View.INVISIBLE);
            //  隐藏Pin的icon
            pinList.add(pin);
            selectPin(pin);
        }
        else{
            pin_adding.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 通过已有的Pin，向地图中添加一个Pin
     * @param pin 要添加的Pin
     * @return added 是否成功添加
     */
    public boolean addPin(Pin pin) {
        LatLng latLng = new LatLng(pin.getPinLatitude(), pin.getPinLongitude());
        if (!pinAlreadyAdded(latLng)) {
            //  构建Maker坐标点
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng) //    设置位置
                    .zIndex(9) //   设置marker所在层级
                    .icon(bitmapDescriptor) //  设置图标样式
                    .draggable(false) // 设置手势拖拽
                    .animateType(MarkerOptions.MarkerAnimateType.grow);
            //  在地图上添加Marker，并显示
            Marker marker = (Marker) mBaiduMap.addOverlay(markerOptions);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("pin", pin);
            marker.setExtraInfo(bundle);
            pinMarkerMap.put(pin, marker);
            return true;
        }
        else {
            showInfo("此处已经添加过Pin,,,");
            return false;
        }
    }

    /**
     *
     * 检测附近50m范围内是否已有添加的pin
     *
     * 地球半径：6371000M
     * 地球周长：2 * 6371000M  * π = 40030173
     * 纬度38°地球周长：40030173 * cos38 = 31544206M
     * 任意地球经度周长：40030173M
     * 经度（东西方向）1M实际度：360°/31544206M=1.141255544679108e-5=0.00001141
     * 纬度（南北方向）1M实际度：360°/40030173M=8.993216192195822e-6=0.00000899
     * @param latLng 要添加的点的pin的位置
     * @return pinFound
     */
    public boolean pinAlreadyAdded(LatLng latLng) {
        //  检测附近50m是否已有Pin
        boolean pinFound = false;
        //  构建LatLngBounds
        LatLng northeast = new LatLng(latLng.latitude + 0.00001141 * 50, latLng.longitude + 0.00000899 * 50);
        LatLng southwest = new LatLng(latLng.latitude - 0.00001141 * 50, latLng.longitude - 0.00000899 * 50);
        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
        for (Pin pin : pinMarkerMap.keySet()) {
            LatLng temp = new LatLng(pin.getPinLatitude(), pin.getPinLongitude());
            if (latLngBounds.contains(temp)) {
                pinFound = true;
                break;
            }
        }
        return pinFound;
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
