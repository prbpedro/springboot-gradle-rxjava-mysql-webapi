package com.github.prbpedro.springboot.webapi.gradle.groovy.exceptions

class UserMessageException extends RuntimeException{

    private final String userMessage

    UserMessageException(String userMessage) {
        this.userMessage = userMessage
    }

    String getUserMessage() {
        return userMessage
    }
}
