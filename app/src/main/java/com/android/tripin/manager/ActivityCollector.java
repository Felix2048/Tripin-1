package com.android.tripin.manager;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix on 6/8/2018.
 * Description: 活动管理器
 */

public class ActivityCollector {
    private final static String TAG = ActivityCollector.class.getSimpleName();

    private static List<AppCompatActivity> activities = new ArrayList<>();

    public static void addActivity(AppCompatActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity) {
        activities.remove(activity);
    }

    /**
     * 讲List中的所有Activity销毁
     */
    public static void finishAll() {
        for(AppCompatActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }
}
