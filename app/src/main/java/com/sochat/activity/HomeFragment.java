package com.sochat.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sochat.R;

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
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler_GroupAdopter);
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