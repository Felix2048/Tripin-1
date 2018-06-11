package com.android.tripin.manager;

import java.io.Serializable;

/**
 * Created by Felix on 6/11/2018.
 * Description: 存储管理运行时所需数据
 */

public class DataManager implements Serializable {

    private static final long serialVersionUID = 1L;


    private static String PlanName;

    public static void setPlanName(String planName) {
        PlanName = planName;
    }

    public static String getPlanName() {
        return PlanName;
    }

}
