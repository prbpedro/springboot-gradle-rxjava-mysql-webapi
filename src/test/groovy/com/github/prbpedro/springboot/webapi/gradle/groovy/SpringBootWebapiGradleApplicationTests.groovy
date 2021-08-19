package com.github.prbpedro.springboot.webapi.gradle.groovy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = SpringBootWebapiGradleApplication)
class SpringBootWebapiGradleApplicationTests extends Specification {

    @Autowired
    ApplicationContext context

    def "context is as expected"() {
        given:
        String s = new URL("http://localhost:8080/dumb").getText()

        expect:
        context
        s
    }
}