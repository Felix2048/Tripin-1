package com.android.tripin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.squareup.leakcanary.LeakCanary;

import java.util.Objects;

/**
 * Created by Felix on 6/8/2018.
 * Description: Tripin Application 入口
 */

public class TripinApplication extends Application {
    private final static String TAG = TripinApplication.class.getSimpleName();

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
        //  在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(Objects.requireNonNull(this.getApplicationContext()));
        //  自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //  包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}