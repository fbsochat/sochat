package com.sochat.activity.model;

import java.util.ArrayList;

public class User {

    private String uid;
    private String username;
    private String profilePicUrl;
    private String phoneNumber;
    private String emailAddress;
    private Integer badges;
    private String country;
    private Integer fans;
    private Integer follow;
    private Boolean gender;
    private Boolean isActive;
    private Integer visitors;

    private ArrayList<String> groupUsersList = new ArrayList<>();

    public User() {
        //empty constructor needed
    }

    public User(String uid,
                String username,
                String profilePicUrl,
                String phonenumber,
                String emailAddress,
                Integer badges,
                String country,
                Integer fans,
                Integer follow,
                Boolean gender,
                Boolean isActive,
                Integer visitors,
                ArrayList<String> groupUsersList
    ) {
        this.uid = uid;
        this.username = username;
        this.profilePicUrl = profilePicUrl;
        this.phoneNumber = phonenumber;
        this.emailAddress = emailAddress;
        this.badges = badges;
        this.country = country;
        this.fans = fans;
        this.follow = follow;
        this.gender = gender;
        this.isActive = isActive;
        this.visitors = visitors;
        this.groupUsersList = groupUsersList;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Integer getBadges() {
        return badges;
    }

    public String getCountry() {
        return country;
    }

    public Integer getFans() {
        return fans;
    }

    public Integer getFollow() {
        return follow;
    }

    public Boolean getGender() {
        return gender;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Integer getVisitors() {
        return visitors;
    }

    public ArrayList<String> getGroupUsersList() {
        return groupUsersList;
    }
}
