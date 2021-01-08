package com.github.prbpedro.springboot.webapi.gradle.exceptions;

public class UserMessageException extends RuntimeException{

    private String userMessage;

    public UserMessageException(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
