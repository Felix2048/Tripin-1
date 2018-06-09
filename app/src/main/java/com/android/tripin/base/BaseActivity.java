package com.android.tripin.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.tripin.manager.ActivityCollector;
import com.android.tripin.base.arch.QMUIFragmentActivity;

/**
 * Created by Felix on 6/8/2018.
 * Description: Activity基类
 */

@SuppressLint("Registered")
public abstract class BaseActivity extends QMUIFragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
