package com.android.tripin.fragment.home;

import android.content.Context;

import com.android.tripin.fragment.ShareFragment;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class HomeShareController extends HomeController {

    ShareFragment shareFragment;

    public HomeShareController(Context context) {
        super(context);
    }

    @Override
    protected String getTitle() {
        return ShareFragment.getTitle();
    }
}
