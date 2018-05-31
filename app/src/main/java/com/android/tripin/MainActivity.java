package com.android.tripin;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.tripin.fragment.FindFragment;
import com.android.tripin.fragment.HomeFragment;
import com.android.tripin.fragment.ShareFragment;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private FindFragment findFragment;
    private HomeFragment homeFragment;
    private ShareFragment shareFragment;
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Find").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_launch_white_24dp, "Share").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise();




        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
    }


    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = HomeFragment.newInstance();
        transaction.replace(R.id.fragment_container, homeFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch(position){
            case 0:
                if(homeFragment == null){
                   homeFragment = HomeFragment.newInstance();
                }
                transaction.replace(R.id.fragment_container, homeFragment);
                break;
            case 1:
                if(findFragment == null){
                    findFragment = FindFragment.newInstance();
                }
                transaction.replace(R.id.fragment_container, findFragment);
                break;
            case 2:
                if(shareFragment == null){
                    shareFragment = ShareFragment.newInstance();
                }
                transaction.replace(R.id.fragment_container, shareFragment);
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
