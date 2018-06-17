package com.android.tripin.fragment.map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.List;

/**
 * Created by Felix on 6/15/2018.
 * Description:
 */
public class MyOnGetRoutePlanResultListener implements OnGetRoutePlanResultListener {

    private MapFragment mapFragment;

    public MyOnGetRoutePlanResultListener(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            mapFragment.mapFragmentAuxiliary.showToast("抱歉，未找到结果");
            if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                mapFragment.mapFragmentAuxiliary.showToast("检索地址有歧义，请重新设置");
                return;
            }
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            if (walkingRouteResult.getRouteLines().size() > 1) {
                //  获取到的路线结果 > 1, 选择其中最快的一条，并将结果存储
                List<WalkingRouteLine> walkingRouteLineList = walkingRouteResult.getRouteLines();
                mapFragment.mapFragmentAuxiliary.addRouteLineListToMap(walkingRouteLineList);
                //  获取用时最短的routeLine
                double minDuration = Double.POSITIVE_INFINITY;
                WalkingRouteLine quickestWalkingRouteLine = null;
                for(WalkingRouteLine walkingRouteLine : walkingRouteLineList) {
                    int duration = walkingRouteLine.getDuration();
                    if (duration < minDuration) {
                        minDuration = duration;
                        quickestWalkingRouteLine = walkingRouteLine;
                    }
                }
                if(null != quickestWalkingRouteLine) {
                    mapFragment.mapFragmentAuxiliary.showWalkingRoute(quickestWalkingRouteLine);
                }
            }
            else if (walkingRouteResult.getRouteLines().size() == 1) {
                // 直接显示
                List<WalkingRouteLine> walkingRouteLineList = walkingRouteResult.getRouteLines();
                mapFragment.mapFragmentAuxiliary.addRouteLineListToMap(walkingRouteLineList);
                mapFragment.mapFragmentAuxiliary.showWalkingRoute(walkingRouteLineList.get(0));
            } else {
                Log.d("route result", "结果数 < 0");
            }
        }
    }

    @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            mapFragment.mapFragmentAuxiliary.showToast("抱歉，未找到结果");
            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                mapFragment.mapFragmentAuxiliary.showToast("检索地址有歧义，请重新设置");
                return;
            }
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            if (drivingRouteResult.getRouteLines().size() > 1) {
                //  获取到的路线结果 > 1, 选择其中最快的一条，并将结果存储
                List<DrivingRouteLine> drivingRouteLineList = drivingRouteResult.getRouteLines();
                mapFragment.mapFragmentAuxiliary.addRouteLineListToMap(drivingRouteLineList);
                //  获取用时最短的routeLine
                double minDuration = Double.POSITIVE_INFINITY;
                DrivingRouteLine quickestDrivingRouteLine = null;
                for(DrivingRouteLine drivingRouteLine : drivingRouteLineList) {
                    int duration = drivingRouteLine.getDuration();
                    if (duration < minDuration) {
                        minDuration = duration;
                        quickestDrivingRouteLine = drivingRouteLine;
                    }
                }
                if(null != quickestDrivingRouteLine) {
                    mapFragment.mapFragmentAuxiliary.showDrivingRoute(quickestDrivingRouteLine);
                }
            }
            else if (drivingRouteResult.getRouteLines().size() == 1) {
                // 直接显示
                List<DrivingRouteLine> drivingRouteLineList = drivingRouteResult.getRouteLines();
                mapFragment.mapFragmentAuxiliary.addRouteLineListToMap(drivingRouteLineList);
                mapFragment.mapFragmentAuxiliary.showDrivingRoute(drivingRouteLineList.get(0));
            } else {
                Log.d("route result", "结果数 < 0");
            }
        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
        if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            mapFragment.mapFragmentAuxiliary.showToast("抱歉，未找到结果");
            if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                mapFragment.mapFragmentAuxiliary.showToast("检索地址有歧义，请重新设置");
                return;
            }
        }
        if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            if (bikingRouteResult.getRouteLines().size() > 1) {
                //  获取到的路线结果 > 1, 选择其中最快的一条，并将结果存储
                List<BikingRouteLine> bikingRouteLineList = bikingRouteResult.getRouteLines();
                mapFragment.mapFragmentAuxiliary.addRouteLineListToMap(bikingRouteLineList);
                //  获取用时最短的routeLine
                double minDuration = Double.POSITIVE_INFINITY;
                BikingRouteLine quickestBikingRouteLine = null;
                for(BikingRouteLine bikingRouteLine : bikingRouteLineList) {
                    int duration = bikingRouteLine.getDuration();
                    if (duration < minDuration) {
                        minDuration = duration;
                        quickestBikingRouteLine = bikingRouteLine;
                    }
                }
                if(null != quickestBikingRouteLine) {
                    mapFragment.mapFragmentAuxiliary.showBikingRoute(quickestBikingRouteLine);
                }
            }
            else if (bikingRouteResult.getRouteLines().size() == 1) {
                // 直接显示
                List<BikingRouteLine> bikingRouteLineList = bikingRouteResult.getRouteLines();
                mapFragment.mapFragmentAuxiliary.addRouteLineListToMap(bikingRouteLineList);
                mapFragment.mapFragmentAuxiliary.showBikingRoute(bikingRouteLineList.get(0));
            } else {
                Log.d("route result", "结果数 < 0");
            }
        }
    }
}
