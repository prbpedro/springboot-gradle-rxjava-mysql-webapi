package com.github.prbpedro.springboot.webapi.gradle.groovy.repositories

import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.SecondDumbEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SecondDumbEntityRepository extends JpaRepository<SecondDumbEntity, Long> {

}
