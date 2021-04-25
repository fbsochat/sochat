package com.sochat.activity.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Timestamp;
import com.sochat.R;
import com.sochat.activity.adaptors.ViewPageAdapter;
import com.sochat.activity.api.GroupHelper;
import com.sochat.activity.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class RelatedFragment extends Fragment {

    EditText roomname,welcome;
    Button submit;
    String name,announsment;

    private ViewPageAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CardView cardViewNewGroup;
    private ImageView g_image;
    private TextView tv_GroopName;
    private TextView tv_WelcomeNote;
    private TextView group_members;





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
        String groupName = Utility.getSharedPreferencesUserGroupName();
        if(TextUtils.isEmpty(groupName) || groupName == null ){
            // Show create Group name
        }else{
            // Hide create Group name
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
        cardViewNewGroup=(CardView) getActivity().findViewById(R.id.card_NewGroup);
        tv_GroopName= (TextView)getActivity().findViewById(R.id.tv_GroopName);
        tv_WelcomeNote= (TextView)getActivity().findViewById(R.id.tv_WelcomeNote);
        group_members= (TextView)getActivity().findViewById(R.id.group_members);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=roomname.getText().toString().trim();
                announsment=welcome.getText().toString().trim();

                Timestamp createdAt = Timestamp.now();
                String createdBy = Utility.getSharedPreferencesUserId();
                ArrayList<String> members = new ArrayList<>();
                members.add(null);
                Timestamp modifieddAt= Timestamp.now();
                String namegroup = name;
                HashMap recentMessage=new HashMap();
                String groupRoomNo = Utility.getRandomNumber();
                String groupPicUrl = "https://homepages.cae.wisc.edu/~ece533/images/tulips.png";
                String groupAbout = announsment;

                GroupHelper.createGroup(createdAt,createdBy,members,modifieddAt,namegroup,recentMessage,groupRoomNo,groupPicUrl,groupAbout).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // save to sharedpref
                        Utility.setCurrentUserGroupName(namegroup);
                        Utility.setCurrentUserGroupWelcomeNote(groupAbout);
                        Utility.setCurrentUserGroupMembers("0");
                        // Hide Create group room name
                        roomname.setVisibility(View.GONE);
                        // visible new created group
                        cardViewNewGroup.setVisibility(View.VISIBLE);
                        // set values
                        tv_GroopName.setText(Utility.getSharedPreferencesUserGroupName());
                        tv_WelcomeNote.setText(Utility.getSharedPreferencesGroupWelcomeNote());
                        group_members.setText(Utility.getSharedPreferencesGroupMembers());
                    }
                });


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