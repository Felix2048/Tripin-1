package com.android.tripin.fragment.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tripin.R;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import java.util.Objects;


/**
 * Created by Felix on 6/11/2018.
 * Description: MapFragment的Controller
 */
public class MapController {
    private final static String TAG = MapController.class.getSimpleName();

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private View mView;
    private Activity mActivity;


    public View getView() {
        return mView;
    }

    /**
     * 通过传入mapFragment代理初始化mapView
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    MapController(@NonNull LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState, Activity activity) {
        mActivity = activity;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(Objects.requireNonNull(mActivity).getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        mView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = mView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setTrafficEnabled(true);
    }

    public void onResume() {
        //在Fragment执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    public void onPause() {
        //执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    public void onDestroy() {
        //在Fragment执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

}
