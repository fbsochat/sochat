package com.sochat.activity.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sochat.R;
import com.sochat.activity.adaptors.RoomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

       private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    RecyclerView recyclerView;
    ArrayList<String> roomname =new ArrayList<>();
    ArrayList<String> announcment =new ArrayList<>();
    ArrayList<String> members =new ArrayList<>();
    TextView related;
    ConstraintLayout constraintLayout;


    private String mParam1;
    private String mParam2;

    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        related=(TextView)getActivity().findViewById(R.id.tv_Related);
        constraintLayout=(ConstraintLayout)getActivity().findViewById(R.id.cons_home);
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler_GroupAdopter);

        related.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            constraintLayout.setVisibility(view.GONE);
            Fragment fragment=new RelatedFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_FrameContainer,fragment);
            transaction.commit();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAssets());
            JSONArray jsonArray = jsonObject.getJSONArray("ROOMS");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject roomsdata=jsonArray.getJSONObject(i);
                roomname.add(roomsdata.getString("roomname"));
                announcment.add(roomsdata.getString("announcment"));
                members.add(roomsdata.getString("members"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RoomAdapter roomAdapter= new RoomAdapter(roomname,announcment,members,HomeFragment.this);
        recyclerView.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();
    }

    private String JsonDataFromAssets() {
        String json=" ";
        try {
            InputStream assetManager = getActivity().getAssets().open("rooms.json");
            int sizeOfFile= assetManager.available();
            byte[] bufferdata= new byte[sizeOfFile];
            assetManager.read(bufferdata);
            assetManager.close();
            json=new String(bufferdata,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}