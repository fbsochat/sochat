package com.sochat.activity.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.sochat.R;
import com.sochat.activity.adaptors.ViewPageAdapter;


public class RelatedFragment extends Fragment {

    EditText roomname,welcome;
    Button submit;
    String name,announsment;

    private ViewPageAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;




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
        viewPager = (ViewPager)getActivity().findViewById(R.id.related_viewpager);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabLayout);
        roomname=(EditText)getActivity().findViewById(R.id.et_CreateRoom);
        welcome=(EditText)getActivity().findViewById(R.id.et_WelcomeNote);
        submit=(Button)getActivity().findViewById(R.id.btn_Submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=roomname.getText().toString().trim();
                announsment=welcome.getText().toString().trim();



            }
        });


        adapter = new ViewPageAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new RecentFragment(), "Recent");
        adapter.addFragment(new JoinedFragment(), "Joined");
        adapter.addFragment(new FollowingFragment(), "Following");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);




        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
               // highLightCurrentTab(position); // for tab change
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}