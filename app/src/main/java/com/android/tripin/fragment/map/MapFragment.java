package com.android.tripin.fragment.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tripin.base.BaseFragment;
import com.android.tripin.presenter.MapPresenter;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class MapFragment extends BaseFragment {

    private final static String TAG = MapFragment.class.getSimpleName();

    private final static String TITLE = "Map";

    public static String getTitle() {
        return TITLE;
    }

    private MapPresenter mapPresenter;


    /**
     * onCreateView
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapPresenter = new MapPresenter(inflater, container, savedInstanceState, getActivity());
        return mapPresenter.getView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在Fragment执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapPresenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在Fragment执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapPresenter.onDestroy();
    }

}
