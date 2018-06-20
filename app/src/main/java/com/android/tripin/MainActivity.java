package com.android.tripin;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.tripin.activity.CreatePlanActivity;
import com.android.tripin.activity.LoginActivity;
import com.android.tripin.activity.PersonalFileActivity;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.fragment.InvitationFragment;
import com.android.tripin.fragment.schedule.ScheduleFragment;
import com.android.tripin.fragment.map.MapFragment;
import com.android.tripin.manager.ActivityCollector;
import com.android.tripin.manager.DataManager;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix on 6/8/2018.
 * Description: Main Activity
 */


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    private final static String TAG = MainActivity.class.getSimpleName();

    /**
     * 初始化dataManager
     */
    private DataManager dataManager = new DataManager();

    private int REQUESTCODE = 0;

    private List<String> permissionList = new ArrayList<String>();

    @Override
    protected int getContextViewId() {
        return R.id.tripin;
    }


    private MapFragment mapFragment;
    private ScheduleFragment scheduleFragment;
    private InvitationFragment invitationFragment;
    BottomNavigationBar bottomNavigationBar;
    private ImageButton btnUser;
    private TextView choosePlan;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(null != DataManager.getCurrentPlan()) {
            choosePlan.setText(DataManager.getCurrentPlan().getPlanName());
        }
        else{
            choosePlan.setText("添加计划");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUser = (ImageButton) findViewById(R.id.home_for_user);
        choosePlan = (TextView) findViewById(R.id.choose_plan_text);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Plan").setActiveColorResource(R.color.green))
                .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "Map").setActiveColorResource(R.color.green))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Share").setActiveColorResource(R.color.green))
                .setFirstSelectedPosition(1)
                .initialise();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataManager.getIsLogin() == false) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, PersonalFileActivity.class);
                    startActivity(intent);
                }
            }
        });

        choosePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataManager.getIsLogin() == true) {
                    Intent intent = new Intent(MainActivity.this, CreatePlanActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        //  请求权限
        permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.INTERNET);
        for(String permission : permissionList) {
            checkPermission(permission);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  处理因按后退键导致的内存泄漏
        ActivityCollector.finishAll();
        //  保存DataManager
    }


    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mapFragment = MapFragment.newInstance();
        transaction.replace(R.id.fragment_container, mapFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch(position){
            case 0:
                if(scheduleFragment == null){
                    scheduleFragment = new ScheduleFragment();
                }
                transaction.replace(R.id.fragment_container, scheduleFragment);
                break;
            case 1:
                if(mapFragment == null){
                    mapFragment = MapFragment.newInstance();
                }
                transaction.replace(R.id.fragment_container, mapFragment);
                break;
            case 2:
                if(invitationFragment == null){
                    invitationFragment= InvitationFragment.newInstance();
                }
                transaction.replace(R.id.fragment_container, invitationFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 运行时请求权限，检查是否成功获取权限
     */
    public void checkPermission(String permission) {
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, permission.hashCode());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    askForPermission();
                }
            }
        }
    }

    private void askForPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("需要权限");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
        });
        builder.create().show();
    }
}