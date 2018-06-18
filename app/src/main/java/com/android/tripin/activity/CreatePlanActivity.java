package com.android.tripin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;

public class CreatePlanActivity extends BaseActivity {

    private final static String TAG = CreatePlanActivity.class.getSimpleName();

    @Override
    protected int getContextViewId() {
        return R.layout.activity_create_plan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
    }
}
