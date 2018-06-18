package com.android.tripin;

import android.os.Bundle;

import com.android.tripin.fragment.InvitationFragment;
import com.android.tripin.fragment.PlanFragment;
import com.android.tripin.fragment.map.MapFragment;

/**
 * Created by Felix on 6/8/2018.
 * Description: Main Activity
 */



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private MapFragment mapFragment;
    private PlanFragment planFragment;
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
                if(planFragment == null){
                    planFragment = PlanFragment.newInstance();
                }
                transaction.replace(R.id.fragment_container, planFragment);
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
}