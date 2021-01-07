package com.github.prbpedro.springboot.webapi.gradle.repositories;

import com.github.prbpedro.springboot.webapi.gradle.entities.DumbEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DumbEntityRepository extends JpaRepository<DumbEntity, Long> {

}
