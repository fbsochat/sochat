package com.sochat.activity.adaptors;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sochat.ChatActivity;
import com.sochat.R;
import com.sochat.activity.MyApplication;
import com.sochat.activity.util.Constants;
import com.sochat.activity.util.Utility;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewClass> {

    public ArrayList<String> roomname;
    public ArrayList<String> announcment;
    public ArrayList<Integer> members;
    public ArrayList<String> groupid;
    public ArrayList<String> groupRoomNumber;
    public Fragment context;
    //ConstraintLayout constraintLayout;



    public RoomAdapter(ArrayList<String> roomname, ArrayList<String> announcment, ArrayList<Integer> members, ArrayList<String> groupid, ArrayList<String> groupRoomNumber, Fragment context) {
        this.roomname = roomname;
        this.announcment = announcment;
        this.members = members;
        this.groupid=groupid;
        this.groupRoomNumber=groupRoomNumber;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms,parent,false);
        ViewClass viewClass=new ViewClass(view);
        View viewhome = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home,parent,false);
        return viewClass;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewClass holder, int position) {
//        String userid = Utility.getSharedPreferencesUserId();

        holder.roomname.setText(roomname.get(position));
        holder.announcment.setText(announcment.get(position));
        holder.members.setText(String.valueOf(members.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here You Do Your Click Magic
               /* Log.d("members","" + members.get(position));
                Log.d("groupid","" + groupid.get(position));
                String msg = "Jai Ho";
                Timestamp sentAt = Timestamp.now();
                String sentBy = userid ;
                String currentGroupId = groupid.get(position);
                MessageHelper.saveMessage("Jai Ho",sentAt,sentBy,currentGroupId);

                */
                Intent intent = new Intent(MyApplication.getContext(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GROUP_ID,groupid.get(position));
                bundle.putString(Constants.MEMBERS, String.valueOf(members.get(position)));
                bundle.putString(Constants.GROUP_ROOM_NAME, roomname.get(position));
                bundle.putString(Constants.GROUP_ROOM_NUMBER, groupRoomNumber.get(position));
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_MULTIPLE_TASK);
                MyApplication.getContext().startActivity(intent);

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
