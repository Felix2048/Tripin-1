package com.android.tripin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.tripin.MainActivity;

/**
 * @author cginechen
 * @date 2016-12-08
 */

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
