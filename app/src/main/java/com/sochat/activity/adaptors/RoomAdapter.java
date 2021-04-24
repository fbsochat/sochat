package com.sochat.activity.adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sochat.R;
import com.sochat.activity.fragments.HomeFragment;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewClass> {

    public ArrayList<String> roomname;
    public ArrayList<String> announcment;
    public ArrayList<Integer> members;
    public Fragment context;

    public RoomAdapter(ArrayList<String> roomname, ArrayList<String> announcment, ArrayList<Integer> members, Fragment context) {
        this.roomname = roomname;
        this.announcment = announcment;
        this.members = members;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms,parent,false);
        ViewClass viewClass=new ViewClass(view);
        return viewClass;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewClass holder, int position) {
        holder.roomname.setText(roomname.get(position));
        holder.announcment.setText(announcment.get(position));
        holder.members.setText(String.valueOf(members.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here You Do Your Click Magic
                Log.d("Deepak","" + members.get(position));
            }
        });

        }

    @Override
    public int getItemCount() {
        return roomname.size();
    }

    public class ViewClass extends RecyclerView.ViewHolder {
        TextView roomname,announcment,members;


        public ViewClass(@NonNull View itemView) {
            super(itemView);
            roomname=(TextView)itemView.findViewById(R.id.tv_RoomTitle);
            announcment=(TextView)itemView.findViewById(R.id.tv_RoomAnnounus);
            members=(TextView)itemView.findViewById(R.id.tv_NoOfMembers);
        }
    }
}
