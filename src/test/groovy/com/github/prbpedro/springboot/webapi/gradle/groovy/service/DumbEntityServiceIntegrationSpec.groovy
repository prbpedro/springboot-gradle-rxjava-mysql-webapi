package com.github.prbpedro.springboot.webapi.gradle.groovy.service

import com.github.prbpedro.springboot.webapi.gradle.groovy.SpringBootWebapiGradleApplication
import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.DumbEntity
import com.github.prbpedro.springboot.webapi.gradle.groovy.repositories.DumbEntityRepository
import com.github.prbpedro.springboot.webapi.gradle.groovy.services.DumbEntityService
import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.reactivestreams.Subscriber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = SpringBootWebapiGradleApplication)
class DumbEntityServiceIntegrationSpec  extends Specification {

    @Autowired
    DumbEntityService service

    @Autowired
    DumbEntityRepository repository

    def setup(){
        repository.deleteAll()
    }

    def 'Should call DumbEntityService::create successfully'() {
        given:
        DumbEntity result = null

        when:
        service.create(1).blockingSubscribe({ r ->
            result = r
        }, {
            throw it
        })

        then:
        result
        result.id == 1
    }
}
