package com.android.tripin.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tripin.R;
import com.android.tripin.fragment.map.MapFragment;
import com.android.tripin.model.MapModel;
import com.android.tripin.presenter.interfaces.IMapPresenter;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import java.util.Objects;


/**
 * Created by Felix on 6/11/2018.
 * Description: MapFragment的Controller
 */
public class MapPresenter implements IMapPresenter{
    private final static String TAG = MapPresenter.class.getSimpleName();

    private MapModel mapModel;
    private MapFragment mapFragment;

    public MapPresenter(MapModel mapModel) {
        this.mapModel = mapModel;
        this.mapModel = new MapModel();
    }


}
