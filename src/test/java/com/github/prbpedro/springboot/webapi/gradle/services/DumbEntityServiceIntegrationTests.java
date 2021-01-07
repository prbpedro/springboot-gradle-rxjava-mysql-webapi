package com.github.prbpedro.springboot.webapi.gradle.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DumbEntityServiceIntegrationTests {

    @Autowired
    private DumbEntityService service;

    @Test
    void runIntegrationTest() {
        service.create(1L);

        service.findAll().subscribe(
            result -> {
                assertEquals(result.size(), 1);
            },
            throwable -> {
                throw new AssertionError(throwable);
            }
        );

    }
}
