package com.sochat.activity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sochat.R;
import com.sochat.activity.adaptors.ViewPagerAdapter;
import com.sochat.activity.fragments.HomeFragment;
import com.sochat.activity.fragments.InboxFragment;
import com.sochat.activity.fragments.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;

    HomeFragment homeFragment;
    InboxFragment inboxFragment;
    ProfileFragment profileFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        viewPager=(ViewPager)findViewById(R.id.viewpager);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_inbox:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_profile:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            if (prevMenuItem!=null){
                prevMenuItem.setChecked(false);
            }
            else{
                bottomNavigationView.getMenu().getItem(position).setChecked(false);
            }
                Log.d("Page","onPageSelected: " +position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem=bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setViewPager(viewPager);

    }
    public void setViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        inboxFragment = new InboxFragment();
        profileFragment = new ProfileFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(inboxFragment);
        adapter.addFragment(profileFragment);
        this.viewPager.setAdapter(adapter);

    }
}