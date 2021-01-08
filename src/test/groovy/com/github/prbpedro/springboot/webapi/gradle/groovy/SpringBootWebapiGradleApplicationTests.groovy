package com.github.prbpedro.springboot.webapi.gradle.groovy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = SpringBootWebapiGradleApplication)
class SpringBootWebapiGradleApplicationTests extends Specification {

    @Autowired
    ApplicationContext context

    def "context is as expected"() {
        expect:
        context
    }
}