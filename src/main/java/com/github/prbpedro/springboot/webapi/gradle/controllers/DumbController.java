package com.github.prbpedro.springboot.webapi.gradle.controllers;

import com.github.prbpedro.springboot.webapi.gradle.entities.DumbEntity;
import com.github.prbpedro.springboot.webapi.gradle.exceptions.ConflictException;
import com.github.prbpedro.springboot.webapi.gradle.services.DumbEntityService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("dumb")
public class DumbController {

    private final Logger logger = LoggerFactory.getLogger(DumbController.class);

    @Autowired
    private final DumbEntityService service;

    public DumbController(DumbEntityService service) {
        this.service = service;
    }

    @ApiOperation(value = "Dumb get")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dumb get ok", responseContainer = "List", response = DumbEntity.class),
            @ApiResponse(code = 500, message = "Dumb get error"), })
    @GetMapping
    public Single<?> get() {
        return service.findAll()
        .map(
            entities -> {
                return ResponseEntity.ok(entities);
            }
        ).onErrorReturn(
            throwable -> {
                logger.error(throwable.getMessage(), throwable);
                return (ResponseEntity) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dumb get error");
            }
        );
    }

    @ApiOperation(value = "Dumb post")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Dumb post ok", response = DumbEntity.class),
        @ApiResponse(code = 409, message = "Dumb post already exists"),
        @ApiResponse(code = 500, message = "Dumb post error"), })
    @PostMapping
    public Observable<? extends ResponseEntity<?>> post(
        @RequestBody
        Long id
        ) {
        return service.create(id)
        .map(
            entities -> {
                return ResponseEntity.ok(entities);
            }
        );
    }

    @ExceptionHandler({ ConflictException.class })
    public ResponseEntity<?> handleException(ConflictException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
