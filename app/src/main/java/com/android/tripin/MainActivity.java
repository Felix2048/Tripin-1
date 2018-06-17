package com.android.tripin;

import android.os.Bundle;

import com.android.tripin.base.BaseActivity;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.fragment.map.MapFragment;
import com.android.tripin.manager.ActivityCollector;

/**
 * Created by Felix on 6/8/2018.
 * Description: Main Activity
 */


public class MainActivity extends BaseActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getContextViewId() {
        return R.id.tripin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            BaseFragment fragment = new MapFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  处理因按后退键导致的内存泄漏
        ActivityCollector.finishAll();
    }
}