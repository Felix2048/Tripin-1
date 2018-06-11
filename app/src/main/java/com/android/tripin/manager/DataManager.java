package com.android.tripin.manager;

/**
 * Created by Felix on 6/11/2018.
 * Description: 存储管理运行时所需数据
 */
public class DataManager {

    private static String PlanName;

    public static void setPlanName(String planName) {
        PlanName = planName;
    }

    public static String getPlanName() {
        return PlanName;
    }

}
