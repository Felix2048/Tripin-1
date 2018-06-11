package com.android.tripin.activity;

import android.os.Bundle;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;

public class SignUpActivity extends BaseActivity {

    @Override
    protected int getContextViewId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}
