package com.android.tripin.util;

import com.android.tripin.entity.Pin;
import com.android.tripin.entity.Route;
import com.android.tripin.enums.Transportation;
import com.android.tripin.manager.DataManager;
import com.android.tripin.util.overlayutil.OverlayManager;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Felix on 6/18/2018.
 * Description:
 */
public class MapDiagram {


    List<Pin> pinList = new ArrayList<>();
    List<Route> routeList = new ArrayList<>();
    Map<Pin, Marker> pinMarkerMap = new HashMap<>();
    Map<Route, HashSet<Pin>> routePinSetMap = new HashMap<>();
    Map<Route, List<? extends RouteLine>> routeLineListMap = new HashMap<>();
    Map<RouteLine, OverlayManager> routeOverlayManagerMap = new HashMap<>();

    boolean isUpdated = false;

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public MapDiagram() {
    }

    public List<Pin> getPinList() {
        return pinList;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public Map<Pin, Marker> getPinMarkerMap() {
        return pinMarkerMap;
    }

    public Map<Route, HashSet<Pin>> getRoutePinSetMap() {
        return routePinSetMap;
    }

    public Map<Route, List<? extends RouteLine>> getRouteLineListMap() {
        return routeLineListMap;
    }

    public Map<RouteLine, OverlayManager> getRouteOverlayManagerMap() {
        return routeOverlayManagerMap;
    }

    public void clearAndUpdateRoute() {
        clear();
        routeList.clear();
        for(int i = 0; i < pinList.size() - 1; i++) {
            Pin origin = pinList.get(i);
            Pin destination = pinList.get(i + 1);
            double distance = DistanceUtil.getDistance(new LatLng(origin.getPinLatitude(), origin.getPinLongitude()),
                    new LatLng(origin.getPinLatitude(), destination.getPinLongitude()));
            Transportation transportation;
            if (distance > 2000) {
                //  如果距离大于2km，使用该计划默认的交通方式
                transportation = DataManager.getCurrentPlan().getDefaultTransportation();
            }
            else {
                //  否则走路
                transportation = Transportation.WALK;
            }
            Route route = new Route(DataManager.getRouteCountAndIncrease(), DataManager.getCurrentPlan().getPlanID(), origin.getPinID(),
                    destination.getPinID(), transportation,0, 0, true);
            routeList.add(route);
        }
    }

    public void clear() {
        pinMarkerMap.clear();
        routePinSetMap.clear();
        routeLineListMap.clear();
        routeOverlayManagerMap.clear();
    }

}
