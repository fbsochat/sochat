package com.sochat.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.sochat.R;


public class RelatedFragment extends Fragment {

    TableLayout tableLayout;
    TabItem tabItem1,tabItem2,tabItem3;
    ViewPager viewPager;




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RelatedFragment() {
        // Required empty public constructor
    }

    public static RelatedFragment newInstance(String param1, String param2) {
        RelatedFragment fragment = new RelatedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_related, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tableLayout = getActivity().findViewById(R.id.tabLayout);
        tabItem1 = getActivity().findViewById(R.id.tabitem_Recent);
        tabItem2 = getActivity().findViewById(R.id.tabitem_Joined);
        tabItem3 = getActivity().findViewById(R.id.tabitem_Following);
        viewPager = getActivity().findViewById(R.id.related_viewpager);





    }
}