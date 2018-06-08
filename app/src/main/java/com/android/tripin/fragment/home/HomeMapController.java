package com.android.tripin.fragment.home;

import android.content.Context;

import com.android.tripin.fragment.MapFragment;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class HomeMapController extends HomeController {

    MapFragment mapFragment;

    public HomeMapController(Context context) {
        super(context);
    }

    @Override
    protected String getTitle() {
        return MapFragment.getTitle();
    }
}
