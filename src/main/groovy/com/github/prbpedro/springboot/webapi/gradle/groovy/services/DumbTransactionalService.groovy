package com.github.prbpedro.springboot.webapi.gradle.groovy.services

import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.DumbEntity
import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.SecondDumbEntity
import com.github.prbpedro.springboot.webapi.gradle.groovy.exceptions.ConflictException
import com.github.prbpedro.springboot.webapi.gradle.groovy.repositories.DumbEntityRepository
import com.github.prbpedro.springboot.webapi.gradle.groovy.repositories.SecondDumbEntityRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.util.Pair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DumbTransactionalService {

    @Autowired
    DumbEntityRepository repository

    @Autowired
    SecondDumbEntityRepository secondRepository

    @Transactional
    DumbEntity transactionalCreate(long id) {
        secondRepository.save(new SecondDumbEntity(id: id + 1))
        throw new Exception()
    }
}
