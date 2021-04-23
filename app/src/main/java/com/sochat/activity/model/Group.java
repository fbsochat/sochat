package com.sochat.activity.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;

public class Group {

    Timestamp createdAt;
    String createdBy;
    String groupId;
    ArrayList<String> members;
    Timestamp modifiedAt;
    String name;
    HashMap<String,Object> recentMessage; // messageText , sentAt, sentBy, type, users
    String groupRoomNo;
    String groupPicUrl;
    String groupAbout;

    public Group() {
        //empty constructor needed
    }

    public Group(Timestamp createdAt,
                 String createdBy,
                 String groupId,
                 ArrayList<String> members,
                 Timestamp modifiedAt,
                 String name,
                 HashMap<String,Object> recentMessage,
                 String groupRoomNo,
                 String groupPicUrl,
                 String groupAbout)
                {
                    this.createdAt = createdAt;
                    this.createdBy = createdBy;
                    this.groupId = groupId;
                    this.members = members;
                    this.modifiedAt = modifiedAt;
                    this.name = name;
                    this.recentMessage = recentMessage;
                    this.groupRoomNo = groupRoomNo;
                    this.groupPicUrl = groupPicUrl;
                    this.groupAbout = groupAbout;
    }

    // getter
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

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public String getName() {
        return name;
    }

    public HashMap<String,Object> getRecentMessage() {
        return recentMessage;
    }

    public String getGroupRoomNo() {
        return groupRoomNo;
    }

    public String getGroupPicUrl() {
        return groupPicUrl;
    }

    public String getGroupAbout() {
        return groupAbout;
    }

    // setter
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public void setModifieddAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecentMessage(HashMap<String, Object> recentMessage) {
        this.recentMessage = recentMessage;
    }

    public void setGroupRoomNo(String groupRoomNo) {
        this.groupRoomNo = groupRoomNo;
    }

    public void setGroupPicUrl(String groupPicUrl) {
        this.groupPicUrl = groupPicUrl;
    }

    public void setGroupAbout(String groupAbout) {
        this.groupAbout = groupAbout;
    }
}
