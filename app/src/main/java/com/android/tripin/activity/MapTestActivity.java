package com.android.tripin.activity;

import android.os.Bundle;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

/**
 * Created by Felix on 6/11/2018.
 * Description:
 */
public class MapTestActivity extends BaseActivity {
    private final static String TAG = MapTestActivity.class.getSimpleName();
    private MapView mMapView;
    private BaiduMap bdMap;

    @Override
    protected int getContextViewId() {
        return R.id.map_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.map_view);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        MapView.setMapCustomEnable(false);
        mMapView = null;
    }
}
