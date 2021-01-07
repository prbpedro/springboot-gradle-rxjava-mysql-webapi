package com.github.prbpedro.springboot.webapi.gradle.exceptions;

public class ConflictException extends RuntimeException{

    public ConflictException(Object conflicted) {
        this.conflicted = conflicted;
    }

    private Object conflicted;

    public Object getConflicted() {
        return conflicted;
    }

    public void setConflicted(Object conflicted) {
        this.conflicted = conflicted;
    }
}
