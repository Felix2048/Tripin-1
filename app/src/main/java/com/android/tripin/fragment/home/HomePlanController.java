package com.android.tripin.fragment.home;

import android.content.Context;

import com.android.tripin.fragment.PlanFragment;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class HomePlanController extends HomeController {

    PlanFragment planFragment;

    public HomePlanController(Context context) {
        super(context);
    }

    @Override
    protected String getTitle() {
        return PlanFragment.getTitle();
    }
}
