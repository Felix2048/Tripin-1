package com.android.tripin.util;

import com.android.tripin.entity.Pin;
import com.android.tripin.entity.Route;
import com.android.tripin.util.overlayutil.OverlayManager;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.search.core.RouteLine;

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

    public void clear() {
        pinMarkerMap.clear();
        routePinSetMap.clear();
        routeLineListMap.clear();
        routeOverlayManagerMap.clear();
    }

}
