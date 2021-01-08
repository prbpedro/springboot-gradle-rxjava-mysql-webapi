package com.github.prbpedro.springboot.webapi.gradle.groovy.repositories

import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.DumbEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DumbEntityRepository extends JpaRepository<DumbEntity, Long> {

}
