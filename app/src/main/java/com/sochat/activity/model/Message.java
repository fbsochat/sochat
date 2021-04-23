package com.sochat.activity.model;

import com.google.firebase.Timestamp;

public class Message {

    String message, sentBy;
    Timestamp sentAt;

    public Message(){

    }

    public Message(String message, Timestamp sentAt, String sentBy) {
        this.message = message;
        this.sentAt = sentAt;
        this.sentBy = sentBy;
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

}
