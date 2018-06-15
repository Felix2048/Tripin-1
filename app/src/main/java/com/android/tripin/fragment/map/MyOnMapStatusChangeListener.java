package com.android.tripin.fragment.map;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;

/**
 * Created by Felix on 6/15/2018.
 * Description: 地图状态改变监听
 */
public class MyOnMapStatusChangeListener implements BaiduMap.OnMapStatusChangeListener {

    private MapFragment mapFragment;

    public MyOnMapStatusChangeListener(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void onMapStatusChangeStart(MapStatus arg0) {
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus arg0) {
        if (mapFragment.isAddingPin && !mapFragment.mapStatusChangeIgnored) {  //  正在添加pin且mapStatusChange未被忽略
            if (!mapFragment.matchPinToPoi()) {     //  如果要添加的pin匹配未到poiMarker，则进行更新
                mapFragment.addPinUpdate();
            }
        }
        else {
            //  初始化mapStatusChange，不忽略
            mapFragment.mapStatusChangeIgnored = false;
        }
    }

    @Override
    public void onMapStatusChange(MapStatus arg0) {
        //  当地图状态改变的时候，获取放大级别
        mapFragment.zoomLevel = arg0.zoom;
        if (mapFragment.isAddingPin && !mapFragment.mapStatusChangeIgnored) {  //  正在添加的pin如果成功匹配到poiMarker，则忽略此次mapStatusChange
            mapFragment.mapFragmentAuxiliary.showToast("正在获取位置...");
        }
    }
}