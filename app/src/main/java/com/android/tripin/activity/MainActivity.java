package com.android.tripin.activity;

import android.os.Bundle;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.fragment.home.HomeFragment;

/**
 * Created by Felix on 6/8/2018.
 * Description: Main Activity
 */


public class MainActivity extends BaseActivity {

    @Override
    protected int getContextViewId() {
        return R.id.tripin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            BaseFragment fragment = new HomeFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}
