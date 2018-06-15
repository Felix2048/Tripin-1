package com.android.tripin.fragment.map;

import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by Felix on 6/15/2018.
 * Description:
 */
public class MyOnMapClickListener implements BaiduMap.OnMapClickListener {

    MapFragment mapFragment;

    public MyOnMapClickListener(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //  点击地图时隐藏pin的信
        mapFragment.pin_info.setVisibility(View.GONE);
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }
}
