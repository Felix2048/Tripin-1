package com.android.tripin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Felix on 6/8/2018.
 * Description: Tripin Application 入口
 */

public class TripinApplication extends Application {

    @SuppressLint("StaticFieldLeak") private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}