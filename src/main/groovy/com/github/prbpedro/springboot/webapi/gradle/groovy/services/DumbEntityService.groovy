package com.github.prbpedro.springboot.webapi.gradle.groovy.services

import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.DumbEntity
import com.github.prbpedro.springboot.webapi.gradle.groovy.exceptions.ConflictException
import com.github.prbpedro.springboot.webapi.gradle.groovy.repositories.DumbEntityRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.springframework.data.util.Pair
import org.springframework.stereotype.Service

@Service
class DumbEntityService {

    private final DumbEntityRepository repository

    DumbEntityService(DumbEntityRepository repository) {
        this.repository = repository
    }

    Single<List<DumbEntity>> findAll() {
        return Observable.defer({ -> Observable.fromIterable(repository.findAll()) })
                .flatMap(
                        { entity ->
                            entity.setTransientString(entity.getId() + " Transient")
                            return Observable.just(entity)
                        }
                )
                .toList()
                .subscribeOn(Schedulers.io())
    }

    Single<DumbEntity> create(Long id) {
        return Single.defer({ -> Single.just(id) })
                .flatMap(
                        { idToSearch -> Single.just(Pair.of(idToSearch, repository.existsById(idToSearch))) }
                )
                .flatMap(
                        {
                            pair ->
                                if (pair.getSecond()) {
                                    return Single.error(new ConflictException(pair.getFirst()))
                                } else {
                                    return Single.just(pair.getFirst())
                                }
                        }
                )
                .flatMap(
                        { idToInsert -> Single.just(repository.save(new DumbEntity(id: idToInsert as Long))) }
                )
                .subscribeOn(Schedulers.io())
    }
}
