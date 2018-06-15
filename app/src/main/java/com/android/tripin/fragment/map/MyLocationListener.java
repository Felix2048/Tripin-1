package com.android.tripin.fragment.map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by Felix on 6/15/2018.
 * Description: 自定义的定位监听
 */
public class MyLocationListener implements BDLocationListener {

    private MapFragment mapFragment;

    public MyLocationListener(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (null != location && null != mapFragment.mMapView) {
//                int errorCode = location.getLocType();
            //将获取的location信息给百度map
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mapFragment.mBaiduMap.setMyLocationData(data);
            if (mapFragment.isFirstLocation) {
                //获取经纬度
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mapFragment.mapFragmentAuxiliary.moveToLocationInMap(latLng);
                mapFragment.isFirstLocation = false;
                mapFragment.mapFragmentAuxiliary.showToast("位置：" + location.getAddrStr());
            }
        }
    }
}