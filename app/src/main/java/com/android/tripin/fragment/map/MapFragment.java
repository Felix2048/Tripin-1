package com.android.tripin.fragment.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.presenter.MapPresenter;
import com.android.tripin.view.IMapView;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
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

public class MapFragment extends BaseFragment implements IMapView {

    private final static String TAG = MapFragment.class.getSimpleName();

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private View mView;
    UiSettings mUiSettings;
    private MapPresenter mapPresenter;

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
        initMapView();
        return mView;
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
                addPin(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                addPin(mapPoi.getPosition());
                return false;
            }
        });
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
