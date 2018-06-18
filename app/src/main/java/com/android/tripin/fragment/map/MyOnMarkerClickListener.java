package com.android.tripin.fragment.map;

import com.android.tripin.entity.Pin;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.android.tripin.manager.DataManager;


/**
 * Created by Felix on 6/15/2018.
 * Description: 地图Marker点击事件监听
 */
public class MyOnMarkerClickListener implements BaiduMap.OnMarkerClickListener {

    private MapFragment mapFragment;

    public MyOnMarkerClickListener(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!mapFragment.isAddingPin && !mapFragment.isDeletingPins) {
            mapFragment.mapFragmentAuxiliary.selectPin(marker);
            mapFragment.mapFragmentAuxiliary.getBackToCurrentPin();
        }
        else if(mapFragment.isDeletingPins) {
            //  获取Pin
            Pin pin = null;
            for (Pin temp : DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinMarkerMap().keySet()) {
                if (marker.equals(DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinMarkerMap().get(temp))) {
                    pin = temp;
                    break;
                }
            }
            if(null != pin) {
                if (marker.getIcon().equals(mapFragment.pinSelectedIcon))    //  若pin已经被选中
                {
                    //  取消被选中
                    marker.setIcon(mapFragment.pinIcon);
                    mapFragment.pinDeleteList.remove(pin);
                }
                else {
                    marker.setIcon(mapFragment.pinSelectedIcon);
                    mapFragment.pinDeleteList.add(pin);
                }
            }
        }
        return true;
    }
}
