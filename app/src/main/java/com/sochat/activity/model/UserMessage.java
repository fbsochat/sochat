package com.sochat.activity.model;

public class UserMessage extends BaseMessage {
    String nickname = "NICKNAME";
    String profileUrl = "PROFILE_URL";
    Boolean isMsgSent = false;
    String ipAddress = "0.0.0.0";
    String deviceModel = "UNKNOWN";

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUserId() {
        return "";
    }

    public Boolean getMsgSent() {
        return isMsgSent;
    }

    public void setMsgSent(Boolean msgSent) {
        isMsgSent = msgSent;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
}