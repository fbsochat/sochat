package com.sochat.activity.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sochat.R;
import com.sochat.activity.adaptors.RoomAdapter;
import com.sochat.activity.api.GroupHelper;
import com.sochat.activity.model.Group;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;


public class FollowingFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<String> roomname =new ArrayList<>();
    ArrayList<String> announcment =new ArrayList<>();
    ArrayList<Integer> members =new ArrayList<>();
    ArrayList<String> groupid =new ArrayList<>();
    ArrayList<String> groupRoomNumber =new ArrayList<>();

    public FollowingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowingFragment newInstance(String param1, String param2) {
        FollowingFragment fragment = new FollowingFragment();
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
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler_following);

        AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage(R.string.loading)
                .setCancelable(false)
                .build();


        firebaseAuth = FirebaseAuth.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (firebaseAuth.getCurrentUser() != null) {
            dialog.show();
            GroupHelper.getGroups().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("GroupIds: ",document.getId());
                            getGroupDetails(document.getId());
                        }
                    } else {
                        dialog.dismiss();
                        Log.d(FollowingFragment.class.getName(), "Error getting documents: ", task.getException());
                    }
                }
            });


            }
//        try {
//            JSONObject jsonObject = new JSONObject(JsonDataFromAssets());
//            JSONArray jsonArray = jsonObject.getJSONArray("ROOMS");
//            for (int i=0;i<jsonArray.length();i++){
//                JSONObject roomsdata=jsonArray.getJSONObject(i);
//                roomname.add(roomsdata.getString("roomname"));
//                announcment.add(roomsdata.getString("announcment"));
//                members.add(roomsdata.getString("members"));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
    //"lKOrSqAo1hbRBftByHr4"
    public void getGroupDetails(String groupId){
        GroupHelper.getGroup(groupId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Group group = documentSnapshot.toObject(Group.class);

                ImageView imageView = (ImageView) getActivity().findViewById(R.id.img_RoomProfilePicture);
                Glide.with(getActivity()).load(group.getGroupPicUrl()).into(imageView);

                roomname.add(group.getName());
                announcment.add(group.getGroupAbout());
                members.add(group.getMembers().size());
                groupid.add(group.getGroupId());
                groupRoomNumber.add(group.getGroupRoomNo());
                RoomAdapter roomAdapter= new RoomAdapter(roomname,announcment,members,groupid,groupRoomNumber,FollowingFragment.this);
                recyclerView.setAdapter(roomAdapter);
                roomAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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