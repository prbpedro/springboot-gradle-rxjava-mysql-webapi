package com.github.prbpedro.springboot.webapi.gradle.groovy.exceptions

class ConflictException extends UserMessageException{

    ConflictException(Object conflicted) {
        super("Entity already exists")
        this.conflicted = conflicted
    }

    Object conflicted
}
