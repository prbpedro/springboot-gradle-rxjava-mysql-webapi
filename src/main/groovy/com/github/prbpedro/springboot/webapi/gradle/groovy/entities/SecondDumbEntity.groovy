package com.github.prbpedro.springboot.webapi.gradle.groovy.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Transient

@Entity
class SecondDumbEntity {

    @Id
    Long id

    @Transient
    String transientString
}