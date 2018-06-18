package com.android.tripin;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.tripin.base.BaseActivity;
import com.android.tripin.fragment.InvitationFragment;
import com.android.tripin.fragment.schedule.ScheduleFragment;
import com.android.tripin.fragment.map.MapFragment;
import com.android.tripin.manager.ActivityCollector;
import com.android.tripin.manager.DataManager;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

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
                .setFirstSelectedPosition(0)
                .initialise();


        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
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
}