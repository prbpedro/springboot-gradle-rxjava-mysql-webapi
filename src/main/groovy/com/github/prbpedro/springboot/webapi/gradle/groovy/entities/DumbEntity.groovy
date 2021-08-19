package com.github.prbpedro.springboot.webapi.gradle.groovy.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Transient

@Entity
class DumbEntity {

    @Id
    Long id

    @Transient
    String transientString
}