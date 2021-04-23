package com.sochat.activity.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;

public class Group {

    Timestamp createdAt;
    String createdBy;
    String groupId;
    ArrayList<String> members;
    Timestamp modifieddAt;
    String name;
    HashMap recentMessage; // messageText , sentAt, sentBy, type, users
    Integer groupRoomNo;

    public Group() {
        //empty constructor needed
    }

    public Group(Timestamp createdAt,
                 String createdBy,
                 String groupId,
                 ArrayList<String> members,
                 Timestamp modifieddAt,
                 String name,
                 HashMap recentMessage,
                 String groupRoomNo)
                {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.groupId = groupId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getGroupId() {
        return groupId;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public Timestamp getModifieddAt() {
        return modifieddAt;
    }

    public String getName() {
        return name;
    }

    public HashMap getRecentMessage() {
        return recentMessage;
    }

    public Integer getGroupRoomNo() {
        return groupRoomNo;
    }
}
