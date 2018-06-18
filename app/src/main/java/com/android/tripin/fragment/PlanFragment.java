package com.android.tripin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tripin.R;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.fragment.map.MapFragment;

/**
 * Created by Felix on 6/8/2018.
 * Description:
 */

public class PlanFragment extends BaseFragment {

    private final static String TAG = PlanFragment.class.getSimpleName();

    private static PlanFragment planFragment = null;

    private final static String TITLE = "Plan";
    public static String getTitle() {
        return TITLE;
    }

    /**
     * onCreateView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plan_detail,container,false);
        return v;
    }
    public static PlanFragment newInstance() {
       if (null != planFragment) {
           return planFragment;
       }
       else {
           planFragment = new PlanFragment();
           return planFragment;
       }
    }
}