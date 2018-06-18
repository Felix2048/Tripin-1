package com.android.tripin.manager;

import com.android.tripin.util.MapDiagram;
import com.android.tripin.entity.Pin;
import com.android.tripin.entity.Plan;
import com.android.tripin.entity.Route;
import com.android.tripin.enums.PinStatus;
import com.android.tripin.enums.PlanType;
import com.android.tripin.enums.Transportation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Felix on 6/11/2018.
 * Description: 存储管理运行时所需数据
 */

public class DataManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean isInitialized = false;

    private static int pinCount = 1;

    private static int routeCount = 1;

    private static int planCount = 1;

    private static int mapCount = 1;

    private static Plan currentPlan = null;

    private static List<Plan> planList = new ArrayList<>();

    private static HashMap<Plan, MapDiagram> planMapDiagramHashMap = new HashMap<>();

    public static int getPinCountAndIncrease() {
        return pinCount++;
    }

    public static int getPinCount() {
        return pinCount;
    }

    public static int getRouteCountAndIncrease() {
        return routeCount++;
    }

    public static int getPlanCountAndIncrease() {
        return planCount++;
    }

    public static int getMapCountAndIncrease() {
        return mapCount++;
    }

    public static void setCurrentPlan(Plan currentPlan) {
        DataManager.currentPlan = currentPlan;
    }

    public static Plan getCurrentPlan() {
        return currentPlan;
    }

    public static List<Plan> getPlanList() {
        return planList;
    }

    public static HashMap<Plan, MapDiagram> getPlanMapDiagramHashMap() {
        return planMapDiagramHashMap;
    }


    public DataManager() {
        if (!isInitialized) {
            init();
            isInitialized = true;
        }
    }

    private void init() {
        Plan plan1 = new Plan(getPlanCountAndIncrease(), getMapCountAndIncrease(), " 上海",
                PlanType.PUBLIC, new Date(), new Date(), Transportation.MASS_TRANSIT);
        Plan plan2 = new Plan(getPlanCountAndIncrease(), getMapCountAndIncrease(), " 北京",
                PlanType.PUBLIC, new Date(), new Date(), Transportation.MASS_TRANSIT);

        planList.add(plan1);
        planList.add(plan2);

        MapDiagram mapDiagram1 = new MapDiagram();

        Pin pin1 = new Pin(getPinCountAndIncrease(), plan1.getPlanID(), 31.24166, 121.48612,  "上海1",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第1个pin");
        Pin pin2 = new Pin(getPinCountAndIncrease(), plan1.getPlanID(),31.24296, 121.48602,  "上海2",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第2个pin");
        Pin pin3 = new Pin(getPinCountAndIncrease(), plan1.getPlanID(),31.23968, 121.49501,  "上海3",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第3个pin");
        Pin pin4 = new Pin(getPinCountAndIncrease(), plan1.getPlanID(), 31.24078, 121.49809,  "上海4",
                new Date(), new Date(), PinStatus.WANTED, "这是我的第4个pin");

        Route route1 = new Route(getRouteCountAndIncrease(), plan1.getPlanID(), pin1.getPinID(), pin2.getPinID(), Transportation.WALK, 0, 0, true);
        Route route2 = new Route(getRouteCountAndIncrease(), plan1.getPlanID(), pin2.getPinID(), pin3.getPinID(), Transportation.MASS_TRANSIT, 0, 0, true);
        Route route3 = new Route(getRouteCountAndIncrease(), plan1.getPlanID(), pin3.getPinID(), pin4.getPinID(), Transportation.RIDING, 0, 0, true);

        mapDiagram1.getPinList().add(pin1);
        mapDiagram1.getPinList().add(pin2);
        mapDiagram1.getPinList().add(pin3);
        mapDiagram1.getPinList().add(pin4);

        mapDiagram1.getRouteList().add(route1);
        mapDiagram1.getRouteList().add(route2);
        mapDiagram1.getRouteList().add(route3);

        MapDiagram mapDiagram2 = new MapDiagram();

        Pin pin5 = new Pin(getPinCountAndIncrease(), plan2.getPlanID(), 39.915291, 116.403857,  "天安门",
                new Date(), new Date(), PinStatus.WANTED, "这是天安门");
        mapDiagram2.getPinList().add(pin5);

        planMapDiagramHashMap.put(plan1, mapDiagram1);
        planMapDiagramHashMap.put(plan2, mapDiagram2);

        currentPlan = plan1;

    }

}
