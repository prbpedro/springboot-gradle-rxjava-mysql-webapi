package com.github.prbpedro.springboot.webapi.gradle.groovy.service

import com.github.prbpedro.springboot.webapi.gradle.groovy.SpringBootWebapiGradleApplication
import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.DumbEntity
import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.SecondDumbEntity
import com.github.prbpedro.springboot.webapi.gradle.groovy.repositories.DumbEntityRepository
import com.github.prbpedro.springboot.webapi.gradle.groovy.repositories.SecondDumbEntityRepository
import com.github.prbpedro.springboot.webapi.gradle.groovy.services.DumbService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = SpringBootWebapiGradleApplication)
class DumbServiceIntegrationSpec extends Specification {

    @Autowired
    DumbService service

    @Autowired
    DumbEntityRepository repository

    @Autowired
    SecondDumbEntityRepository secondDumbEntityRepository

    def setup(){
        repository.deleteAll()
        secondDumbEntityRepository.deleteAll()
    }

    def 'Should call DumbService::create successfully'() {
        given:
        DumbEntity result = null

        when:
        service.createDumbEntity(1).blockingSubscribe({ r ->
            result = r
        }, {
            throw it
        })

        then:
        result
        result.id == 1
    }

    def 'Should ensure transaction to DumbService::create'() {
        given:
        DumbEntity d = null

        when:
        service.createDumbEntity(1).blockingSubscribe({ r ->
            d = r
        }, {
            throw it
        })

        Throwable asd = null
        Optional<SecondDumbEntity> a123 = null
        service.getSecondDumbEntity(2).blockingSubscribe({ r ->
            a123 = r
        }, {
            asd = it
        })

        Throwable t = null
        DumbEntity d2 = null
        service.create(1).blockingSubscribe({ r ->
            d2 = r
        }, {
            t = it
        })

        Throwable t2 = null
        Optional<SecondDumbEntity> sd = null
        service.getSecondDumbEntity(2).blockingSubscribe({ r ->
            sd = r
        }, {
            t2 = it
        })

        Throwable t3 = null
        DumbEntity d3 = null
        service.getDumbEntity(1).blockingSubscribe({ r ->
            d3 = r
        }, {
            t3 = it
        })

        Throwable t4 = null
        def result = null
        service.findAll().blockingSubscribe({ r ->
            result = r
        }, {
            t4 = it
        })

        then:
        d
        d.id == 1
        !sd.isPresent()
    }
}
