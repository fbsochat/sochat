package com.sochat.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {

    int tabcount;
    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :return new RecentFragment();
            case 1 :return new JoinedFragment();
            case 2 :return new FollowingFragment();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
