package com.android.tripin.fragment.map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.activity.PinDetailActivity;
import com.android.tripin.entity.Pin;
import com.android.tripin.entity.Route;
import com.android.tripin.enums.Transportation;
import com.android.tripin.manager.DataManager;
import com.android.tripin.util.overlayutil.BikingRouteOverlay;
import com.android.tripin.util.overlayutil.DrivingRouteOverlay;
import com.android.tripin.util.overlayutil.OverlayManager;
import com.android.tripin.util.overlayutil.TransitRouteOverlay;
import com.android.tripin.util.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Felix on 6/15/2018.
 * Description: 为MapFragment提供一系列辅助方法
 */
public class MapFragmentAuxiliary {

    /**
     * 逆地址编码回调获取到的AddressComponent
     */
    ReverseGeoCodeResult.AddressComponent addressComponent;

    /**
     * Toast对象
     */
    Toast mToast = null;

    /**
     * 是否显示对话框
     */
    boolean hasShownDialogue;

//    boolean isRequestingOriginCity = false;
//    boolean isRequestingDestinationCity = false;

//    String originCity;
//    String destinationCity;

    MapFragment mapFragment;

    public MapFragmentAuxiliary(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    /**
     * 显示消息
     * @param msg 需要显示的Toast的字符串
     */
    public void showToast(String msg){
        //判断队列中是否包含已经显示的Toast
        if (mToast == null) {
            mToast = Toast.makeText(mapFragment.getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }

    /**
     *  marker默认只能加载bitmap图片，将view转换成Bitmap,进行添加
     * @param addViewContent 要添加的View
     * @return bitmap
     */
    public Bitmap getViewBitMap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();

        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }

    /**
     * 将当前地图移动到latLng的位置
     * @param latLng 要移动的点位置
     */
    public void moveToLocationInMap(LatLng latLng) {
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
        //mBaiduMap.setMapStatus(status);//直接到中间
        mapFragment.mBaiduMap.animateMapStatus(status);//动画的方式到中间
        mapFragment.isFirstLocation = false;
    }

    /**
     * 调整地图zoom等级
     * @param zoom 要移动的点位置
     */
    public void setZoomLevelInMap(float zoom) {
        MapStatusUpdate status = MapStatusUpdateFactory.zoomTo(zoom);
        //mBaiduMap.setMapStatus(status);//直接到中间
        mapFragment.mBaiduMap.animateMapStatus(status);//动画的方式到中间
        mapFragment.isFirstLocation = false;
    }

    /**
     * 返回给定的的PinIndex的pin的位置
     * @param pinIndex 要移动到的pin的index
     */
    public void getBackToPin(int pinIndex) {
        if(null != DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList() && !DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList().isEmpty() && pinIndex >= 0 && pinIndex < DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList().size()) {
            Pin pin = DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList().get(pinIndex);
            moveToLocationInMap(new LatLng(pin.getPinLatitude(), pin.getPinLongitude()));
            selectPin(pin);
        }
    }

    /**
     * 返回当前pinIndex的Pin的位置
     */
    public void getBackToCurrentPin() {
        if(mapFragment.currentPinIndex != -1) {
            getBackToPin(mapFragment.currentPinIndex);
            setZoomLevelInMap(18);
            mapFragment.zoomLevel = 18;
        }
    }

    /**
     * （循环）返回的前一个pinIndex的Pin的位置
     */
    public void getToNextPin() {
        if(mapFragment.currentPinIndex != -1) {

            if (mapFragment.currentPinIndex == DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList().size() - 1) {
                mapFragment.currentPinIndex = 0;
            } else {
                mapFragment.currentPinIndex++;
            }
            getBackToCurrentPin();
        }
    }

    /**
     * （循环）返回的后一个pinIndex的Pin的位置
     */
    public void getToPreviousPin() {
        if(mapFragment.currentPinIndex != -1) {
            if (mapFragment.currentPinIndex == 0) {
                mapFragment.currentPinIndex = DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList().size() - 1;
            } else {
                mapFragment.currentPinIndex--;
            }
            getBackToCurrentPin();
        }
    }

    /**
     * 选中标记Pin覆盖物，显示infoWindow，点击infoWindow显示PinInfoView
     * @param pin 被选中的Pin
     */
    public void selectPin(Pin pin) {
        selectPin(DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinMarkerMap().get(pin));
    }

    /**
     * 选中标记Pin覆盖物，显示infoWindow，点击infoWindow显示PinInfoView
     * @param marker 被选中的标记覆盖物
     */
    public void selectPin(Marker marker) {
        //  从marker中获取info信息
        Bundle bundle = marker.getExtraInfo();
        Pin pin = (Pin) bundle.getSerializable("pin");
        if(null != pin) {
            //  如果获取pin成功
            //  隐藏infowindow
            mapFragment.mBaiduMap.hideInfoWindow();
            //  修改currentPinIndex
            mapFragment.currentPinIndex = DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList().indexOf(pin);
            //  将信息显示在界面上
            //  TODO: 使用intent将pinInfo传给pinDetailActivity
//        TextView pin_title = (TextView) mapFragment.pin_info.findViewById(R.id.pin_title);
//        pin_title.setText(pin.getPinTitle());
//        TextView pin_notes = (TextView) mapFragment.pin_info.findViewById(R.id.pin_notes);
//        pin_notes.setText(pin.getPinNotes());
            //  加载infoWindow中的布局
            TextView textView = new TextView(mapFragment.getContext());
            textView.setBackgroundResource(R.drawable.common_bg_with_radius_and_border);
            textView.setPadding(20, 10, 20, 20);
            textView.setTextColor(Color.BLACK);
            textView.setText(pin.getPinTitle());
            textView.setGravity(Gravity.CENTER);
            BitmapDescriptor infoWindowView = BitmapDescriptorFactory.fromView(textView);
            //  infoWindow点击事件
            InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick() {
                    //  将布局显示出来
                    //  TODO:跳转到PinDetailActivity
                    Intent intent = new Intent(mapFragment.getActivity(), PinDetailActivity.class);
                    mapFragment.getActivity().startActivity(intent);
                }
            };
            //  显示infoWindow
            InfoWindow infoWindow = new InfoWindow(infoWindowView, new LatLng(pin.getPinLatitude(), pin.getPinLongitude()), -100, listener);
            mapFragment.mBaiduMap.showInfoWindow(infoWindow);
        }
    }

    /**
     * 显示当前Plan下的所有pin和route
     */
    public void showTrip() {
        //  清空地图
        mapFragment.mBaiduMap.clear();

        for (Pin pin : DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList()) {
            addPin(pin);
        }
        for (Route route : DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRouteList()) {
            addRoutePinSetMap(route);
            addRoute(route);
        }
        //  初始化currentPinIndex为0
        mapFragment.currentPinIndex = 0;
        mapFragment.mapFragmentAuxiliary.getBackToCurrentPin();
    }

    /**
     * 获取当前地图的可视区域的bounds
     * @return latLngBounds
     */
    public LatLngBounds getMapBounds() {

        Display display = mapFragment.getActivity().getWindowManager().getDefaultDisplay();

        Point bottomLeft = new Point();
        bottomLeft.x = 0;
        bottomLeft.y = display.getHeight();
        Point topRight = new Point();
        topRight.x = display.getWidth();
        topRight.y = 0;

        LatLng southwest = mapFragment.mBaiduMap.getProjection().fromScreenLocation(bottomLeft);

        LatLng northeast = mapFragment.mBaiduMap.getProjection().fromScreenLocation(topRight);
        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(southwest).include(northeast).build();
        return latLngBounds;
    }

    /**
     * 向GeoCoder请求当前MapCenter的逆地址编码，在监听中获取回调
     */
    public void requestMapCenterReverseGeoCode() {
        //  请求逆地址编码
        requestReverseGeoCode(mapFragment.mapCenter);
    }

    /**
     * 向poiSearch请求当前mapCenter的附近（地图可视范围内）的poiInfo，在监听中获取回调
     */
    public void requestMapCenterPoi() {
        LatLngBounds mapBounds = getMapBounds();
        //  检索15条可视范围内的POI信息，用于辅助用户添加Pin
        mapFragment.mPoiSearch.searchInBound(new PoiBoundSearchOption()
                .bound(mapBounds)
                .keyword("景点")
                .pageNum(0)
                .pageCapacity(15));
        mapFragment.mPoiSearch.searchInBound(new PoiBoundSearchOption()
                .bound(mapBounds)
                .keyword("酒店")
                .pageNum(0)
                .pageCapacity(5));
        mapFragment.mPoiSearch.searchInBound(new PoiBoundSearchOption()
                .bound(mapBounds)
                .keyword("美食")
                .pageNum(0)
                .pageCapacity(5));
    }


    /**
     * 将回调获取到的poiInfo在地图上显示
     * @param poiResult 回调获取到的poiInfo
     */
    public void showPoiInfo(PoiResult poiResult) {
        //  获取新的poiInfoList
        mapFragment.poiInfoList = poiResult.getAllPoi();
        if (null != mapFragment.poiInfoList) {
            LatLngBounds mapBounds = getMapBounds();
            for(PoiInfo poiInfo : mapFragment.poiInfoList) {
                LatLng latLng = new LatLng(poiInfo.location.latitude, poiInfo.location.longitude);
                //  若该poiInfo包含在map的显示范围内，且50m内无pin、10m内无poiMarker，则添加
                if (!pinAlreadyAdded(latLng) && mapBounds.contains(latLng) && !markerAlreadyAdded(latLng)) {
                    //  加载布局文件
                    View poiInfoView= mapFragment.inflater.inflate(R.layout.poi_marker, null);
                    TextView poiInfoTextView = (TextView) poiInfoView.findViewById(R.id.poi_name);
                    poiInfoTextView.setText(poiInfo.name);
                    Bitmap poiInfoBitMap = getViewBitMap(poiInfoView);
                    BitmapDescriptor candidateBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(poiInfoBitMap);
                    //  构建Maker坐标点
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latLng) //    设置位置
                            .zIndex(8) //   设置marker所在层级
                            .icon(candidateBitmapDescriptor) //  设置图标样式
                            .title(poiInfo.name)
                            .draggable(false); // 设置手势拖拽
                    //  在地图上添加Marker，并显示
                    Marker marker = (Marker) mapFragment.mBaiduMap.addOverlay(markerOptions);
                    mapFragment.poiMarkerList.add(marker);
                }
            }
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
                    .icon(mapFragment.pinIcon) //  设置图标样式
                    .draggable(false) // 设置手势拖拽
                    .animateType(MarkerOptions.MarkerAnimateType.grow);
            //  在地图上添加Marker，并显示
            Marker marker = (Marker) mapFragment.mBaiduMap.addOverlay(markerOptions);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("pin", pin);
            marker.setExtraInfo(bundle);
            DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinMarkerMap().put(pin, marker);
            return true;
        }
        else {
            mapFragment.mapFragmentAuxiliary.showToast("此处已经添加过Pin...");
            return false;
        }
    }

    /**
     * 检查用户是否将当前pin_adding的icon拖动到poi点的附近（30m）
     * 若存在在这样的poi点，则将pin的title设置为poi点的name，并移动至poi点的位置
     * @return 用户是否将Pin将当前pin_adding的icon拖动到poi点的附近（30m）
     */
    public boolean matchPinToPoi() {
        //  获取现在的mapCenter
        mapFragment.mapCenter = mapFragment.mBaiduMap.getMapStatus().target;
        //  求出最近的poi点
        Marker closestPoiInfoMarker = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Marker marker : mapFragment.poiMarkerList) {
            double distance = DistanceUtil.getDistance(mapFragment.mapCenter, marker.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                closestPoiInfoMarker = marker;
            }
        }
        if (minDistance < 30 && null != closestPoiInfoMarker && mapFragment.matchedPoiHash != (double) closestPoiInfoMarker.hashCode()) {     //  距离小于50m，且未重复匹配
            mapFragment.mapCenterAddress = closestPoiInfoMarker.getTitle();
            mapFragment.matchedPoiHash = closestPoiInfoMarker.hashCode();
            mapFragment.mapStatusChangeIgnored = true;  //  忽略下一次移动到该poiMarker的mapStatusChange
            mapFragment.mapCenter = closestPoiInfoMarker.getPosition();
            moveToLocationInMap(closestPoiInfoMarker.getPosition());
            return true;
        }
        else {
            mapFragment.matchedPoiHash = Double.POSITIVE_INFINITY;
            return false;
        }
    }

    /**
     * 清空地图上的poiInfo
     */
    public void clearPoiInfo() {
        if (null != mapFragment.poiInfoList) {
            mapFragment.poiInfoList.clear();
        }
        if (null != mapFragment.poiMarkerList) {
            for (Marker poiMarker : mapFragment.poiMarkerList) {
                poiMarker.remove();
            }
            mapFragment.poiMarkerList.clear();
        }
    }

    /**
     * 检测附近50m范围内是否已有添加的
     * 地球半径：6371000M
     * 地球周长：2 * 6371000M  * π = 40030173
     * 纬度38°地球周长：40030173 * cos38 = 31544206M
     * 任意地球经度周长：40030173M
     * 经度（东西方向）1M实际度：360°/31544206M=1.141255544679108e-5=0.00001141
     * 纬度（南北方向）1M实际度：360°/40030173M=8.993216192195822e-6=0.00000899
     * @param latLng 要添加的pin的位置
     * @return pinFound
     */
    public boolean pinAlreadyAdded(LatLng latLng) {
        //  检测附近50m是否已有Pin
        boolean pinFound = false;
        //  构建LatLngBounds
        LatLng northeast = new LatLng(latLng.latitude + 0.00001141 * 50, latLng.longitude + 0.00000899 * 50);
        LatLng southwest = new LatLng(latLng.latitude - 0.00001141 * 50, latLng.longitude - 0.00000899 * 50);
        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
        for (Pin pin : DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinMarkerMap().keySet()) {
            LatLng temp = new LatLng(pin.getPinLatitude(), pin.getPinLongitude());
            if (latLngBounds.contains(temp)) {
                pinFound = true;
                break;
            }
        }
        return pinFound;
    }

    /**
     *
     * 检测附近10m范围内是否已有添加的marker
     * @param latLng 要添加的marker的位置
     * @return markerFound
     */
    public boolean markerAlreadyAdded(LatLng latLng) {
        //  检测附近10m是否已有Pin
        boolean markerFound = false;
        //  构建LatLngBounds
        LatLng northeast = new LatLng(latLng.latitude + 0.00001141 * 10, latLng.longitude + 0.00000899 * 10);
        LatLng southwest = new LatLng(latLng.latitude - 0.00001141 * 10, latLng.longitude - 0.00000899 * 10);
        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
        for (Marker marker : mapFragment.poiMarkerList) {
            LatLng temp = marker.getPosition();
            if (latLngBounds.contains(temp)) {
                markerFound = true;
                break;
            }
        }
        return markerFound;
    }

    /**
     * 将地图中的Pin移除
     * @param pin 要删除的Pin
     */
    public void deletePin(Pin pin) {
        Marker marker = DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinMarkerMap().get(pin);
        if (null != marker) {
            marker.remove();
        }
        DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinMarkerMap().remove(pin);
        DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList().remove(pin);
        //  调用presenter删除Pin
    }

    /**
     * 发起逆地址编码请求，获取location所在地的AddressComponent
     * @param location 请求的地址
     */
    public void requestReverseGeoCode(LatLng location) {
        //  请求逆地址编码
        mapFragment.mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(location));
    }

    /**
     * 在地图上添加连接起点和终点的路线
     * @param origin 起点的location
     * @param destination 终点的location
     * @param transportationType 交通方式
     */
    public void addRouteWithLocation(LatLng origin, LatLng destination, Transportation transportationType) {
        PlanNode originNode = PlanNode.withLocation(origin);
        PlanNode destinationNode = PlanNode.withLocation(destination);
        requestRoute(originNode, destinationNode, transportationType);
    }


    /**
     * 在地图上添加连接起点和终点的路线
     * @param origin 起点的pin
     * @param destination 终点的pin
     * @param transportationType 交通方式
     */
    public void addRouteWithPins(Pin origin, Pin destination, Transportation transportationType) {
        LatLng originLocation = new LatLng(origin.getPinLatitude(), origin.getPinLongitude());
//        isRequestingOriginCity = true;
//        requestReverseGeoCode(originLocation);
        LatLng destinationLocation = new LatLng(destination.getPinLatitude(), destination.getPinLongitude());
//        isRequestingDestinationCity = true;
//        requestReverseGeoCode(destinationLocation);
        addRouteWithLocation(originLocation, destinationLocation, transportationType);
    }

    /**
     * 根据Route在地图上添加路线
     * @param route 要添加的路线
     */
    public void addRoute(Route route) {
        int originID = route.getOrigin();
        int destinationID = route.getDestination();
        if (originID == destinationID) {
            return;
        }
        Transportation transportation = route.getRouteTransportation();

        Pin origin = null, destination = null;
        HashSet<Pin> pins = DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRoutePinSetMap().get(route);
        for (Pin pin : pins) {
            if(pin.getPinID() == route.getOrigin()) {
                origin = pin;
            }
            else if (pin.getPinID() == route.getDestination()) {
                destination = pin;
            }
        }
        if (null != origin && null != destination) {
            addRouteWithPins(origin, destination, transportation);
        }
    }

    /**
     * 添加新添加的pin与上一个pin的路线
     */
    public void addRoute(Pin newAddedPin) {


    }

    /**
     * 在pinList中找对应route中起始点ID的pin，routePinSetMap
     * @param route 路线
     */
    public void addRoutePinSetMap(Route route) {
        int originID = route.getOrigin();
        int destinationID = route.getDestination();
        if (originID == destinationID) {
            return;
        }
        Pin origin = null, destination = null;
        for (Pin pin : DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList()) {
            if (pin.getPinID() == originID) {
                origin = pin;
            }
            else if (pin.getPinID() == destinationID) {
                destination = pin;
            }
        }
        HashSet<Pin> pins = new HashSet<>();
        pins.add(origin);
        pins.add(destination);
        DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRoutePinSetMap().put(route, pins);
    }

    /**
     * 在routeList中匹配到对应的route，然后将(route, routeLineList)加入到routeLineListMap中
     * @param routeLineList 回调获取到的routeLine
     */
    public void addRouteLineListToMap(List<? extends RouteLine> routeLineList) {
        LatLng origin = routeLineList.get(0).getStarting().getLocation();
        LatLng destination = routeLineList.get(0).getTerminal().getLocation();
        Route route = null;
        for (Route temp : DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRoutePinSetMap().keySet()) {
            HashSet<Pin> pins = DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRoutePinSetMap().get(temp);
            boolean originInPins = false, destinationInPins = false;
            for (Pin pin: pins) {
                if (origin.latitude == pin.getPinLatitude() && origin.longitude == pin.getPinLongitude()) {
                    originInPins = true;
                }
                else if (destination.latitude == pin.getPinLatitude() && destination.longitude == pin.getPinLongitude()) {
                    destinationInPins = true;
                }
            }
            if (originInPins && destinationInPins) {
                route = temp;
                break;
            }
        }
        if(null != route) {
            DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRouteLineListMap().put(route, routeLineList);
        }
    }

    /**
     * 请求以 transportationType 的交通方式，从起点的planNode到终点的终点的planNode的路线
     * @param origin 起点的planNode
     * @param destination 终点的planNode
     * @param transportationType 交通方式
     */
    public void requestRoute(PlanNode origin, PlanNode destination, Transportation transportationType) {
        switch (transportationType) {
            //  TODO:在搜索时可以加入policy
            case WALK:
                WalkingRoutePlanOption walkingRoutePlanOption = (new WalkingRoutePlanOption())
                        .from(origin)
                        .to(destination);
                mapFragment.mRoutePlanSearch.walkingSearch(walkingRoutePlanOption);
                break;
            case DRIVING:
                DrivingRoutePlanOption drivingRoutePlanOption = (new DrivingRoutePlanOption())
                        .from(origin)
                        .to(destination);
                mapFragment.mRoutePlanSearch.drivingSearch(drivingRoutePlanOption);
                break;
            case RIDING:
                BikingRoutePlanOption bikingRoutePlanOption = (new BikingRoutePlanOption())
                        .from(origin)
                        .to(destination);
                mapFragment.mRoutePlanSearch.bikingSearch(bikingRoutePlanOption);
                break;
            case MASS_TRANSIT:
                //  检查二者是否在同一个城市
                //  在相同城市
//                showToast("在相同城市");
                TransitRoutePlanOption transitRoutePlanOption = (new TransitRoutePlanOption())
                        .from(origin).to(destination).city("北京");
                mapFragment.mRoutePlanSearch.transitSearch(transitRoutePlanOption);

//                //  不在相同城市
//                showToast("不在相同城市");
//                MassTransitRoutePlanOption massTransitRoutePlanOption = (new MassTransitRoutePlanOption())
//                        .from(origin)
//                        .to(destination);
//                mapFragment.mRoutePlanSearch.masstransitSearch(massTransitRoutePlanOption);
                break;
            default:
                break;
        }
    }

    /**
     * 在地图上显示walkingRouteLine
     * @param walkingRouteLine 要显示的routeLine
     */
    public void showWalkingRoute(WalkingRouteLine walkingRouteLine) {
        if (null != walkingRouteLine) {
            WalkingRouteOverlay overlay = new WalkingRouteOverlay(mapFragment.mBaiduMap);
//            mapFragment.mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(walkingRouteLine);
            overlay.addToMap();
            DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRouteOverlayManagerMap().put(walkingRouteLine, overlay);
//            overlay.zoomToSpan();
        }
    }

    /**
     * 在地图上显示drivingRouteLine
     * @param drivingRouteLine 要显示的routeLine
     */
    public void showDrivingRoute(DrivingRouteLine drivingRouteLine) {
        if (null != drivingRouteLine) {
            DrivingRouteOverlay overlay = new DrivingRouteOverlay(mapFragment.mBaiduMap);
//            mapFragment.mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(drivingRouteLine);
            overlay.addToMap();
            DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRouteOverlayManagerMap().put(drivingRouteLine, overlay);
//            overlay.zoomToSpan();
        }
    }

    /**
     * 在地图上显示bikingRouteLine
     * @param bikingRouteLine 要显示的routeLine
     */
    public void showBikingRoute(BikingRouteLine bikingRouteLine) {
        if (null != bikingRouteLine) {
            BikingRouteOverlay overlay = new BikingRouteOverlay(mapFragment.mBaiduMap);
//            mapFragment.mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(bikingRouteLine);
            overlay.addToMap();
            DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRouteOverlayManagerMap().put(bikingRouteLine, overlay);
//            overlay.zoomToSpan();
        }
    }

    /**
     * 在地图上显示transitRouteLine
     * @param transitRouteLine 要显示的routeLine
     */
    public void showTransitRoute(TransitRouteLine transitRouteLine) {
        if (null != transitRouteLine) {
            TransitRouteOverlay overlay = new TransitRouteOverlay(mapFragment.mBaiduMap);
//            mapFragment.mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(transitRouteLine);
            overlay.addToMap();
            DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getRouteOverlayManagerMap().put(transitRouteLine, overlay);
//            overlay.zoomToSpan();
        }
    }


}
