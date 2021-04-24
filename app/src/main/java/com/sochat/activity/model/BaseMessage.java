package com.sochat.activity.model;

import java.io.Serializable;

public class BaseMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    String message = "";
    UserMessage sender = null;//new UserMessage();
    String createdAt = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserMessage getSender() {
        return sender;
    }

    public void setSender(UserMessage sender) {
        this.sender = sender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}