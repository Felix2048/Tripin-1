package com.android.tripin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.tripin.base.BaseActivity;
import com.android.tripin.base.BaseFragment;
import com.android.tripin.fragment.map.MapFragment;
import com.baidu.mapapi.SDKInitializer;

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
        finish();
    }
}