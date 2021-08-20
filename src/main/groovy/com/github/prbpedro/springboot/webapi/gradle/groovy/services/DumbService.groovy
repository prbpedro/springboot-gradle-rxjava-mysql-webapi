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

@Service
class DumbService {

    private final DumbEntityRepository repository

    private final SecondDumbEntityRepository secondRepository

    @Autowired
    DumbTransactionalService dumbTransactionalService

    DumbService(
            DumbEntityRepository repository,
            SecondDumbEntityRepository secondRepository) {
        this.secondRepository = secondRepository
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

    Observable<Optional<DumbEntity>> getDumbEntity(Long id) {
        return Observable.defer({ -> Observable.just(repository.findById(id)) })
                .flatMap(
                        { entity ->
                            return Observable.just(entity)
                        }
                )
                .subscribeOn(Schedulers.io())
    }

    Observable<Optional<SecondDumbEntity>> getSecondDumbEntity(Long id) {
        return Observable.defer({ -> Observable.just(secondRepository.findById(id)) })
                .flatMap(
                        { entity ->
                            return Observable.just(entity)
                        }
                )
                .subscribeOn(Schedulers.io())
    }

    Single<DumbEntity> createDumbEntity(Long id) {
        return Single.defer({ -> Single.just(id) })
                .flatMap(
                        { idToSearch -> Single.just(Pair.of(idToSearch, repository.existsById(idToSearch))) }
                )
                .flatMap(
                        {
                            pair ->
                                if (pair.getSecond()) {
                                    throw new ConflictException(pair.getFirst())
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

    Single<DumbEntity> create(Long id) {
        return Single.defer({ -> Single.just(dumbTransactionalService.transactionalCreate(id)) })
                .subscribeOn(Schedulers.io())
    }
}
