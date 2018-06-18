package com.android.tripin.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;

import com.android.tripin.MainActivity;
import com.android.tripin.manager.ActivityCollector;

/**
 * @author cginechen
 * @date 2016-12-08
 */

public class LauncherActivity extends Activity {
    private final static String TAG = LauncherActivity.class.getSimpleName();
    /**
     * 增加一个判断用户是否已经登陆的flag，初始值为0，登陆成功后置为1，注销后置为0
     */
    public static int CHECK_LOGIN_STATUS_FLAG= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        Intent intent = new Intent(this, CreatePlanActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  处理因按后退键导致的内存泄漏
        ActivityCollector.finishAll();
    }
}
