package com.sochat.activity.model;

import com.google.firebase.Timestamp;

public class Message {

    String message;
    String sentBy;
    String userName;
    Timestamp sentAt;
    Boolean isMsgSent;

    public Message(){

    }

    public Message(String message, Timestamp sentAt, String sentBy,String userName,Boolean isMsgSent) {
        this.message = message;
        this.sentAt = sentAt;
        this.sentBy = sentBy;
        this.userName = userName;
        this.isMsgSent = isMsgSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Boolean getMsgSent() {
        return isMsgSent;
    }

    public void setMsgSent(Boolean msgSent) {
        isMsgSent = msgSent;
    }



}
