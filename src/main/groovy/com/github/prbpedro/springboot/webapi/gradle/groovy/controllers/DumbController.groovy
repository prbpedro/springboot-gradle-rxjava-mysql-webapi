package com.github.prbpedro.springboot.webapi.gradle.groovy.controllers

import com.github.prbpedro.springboot.webapi.gradle.groovy.entities.DumbEntity
import com.github.prbpedro.springboot.webapi.gradle.groovy.exceptions.ConflictException
import com.github.prbpedro.springboot.webapi.gradle.groovy.exceptions.UserMessageException
import com.github.prbpedro.springboot.webapi.gradle.groovy.services.DumbEntityService
import io.reactivex.rxjava3.core.Single
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api
@RestController
@RequestMapping("dumb")
@SuppressWarnings("unused")
class DumbController {

    private final Logger logger = LoggerFactory.getLogger(DumbController.class)

    @Autowired
    private final DumbEntityService service

    DumbController(DumbEntityService service) {
        this.service = service
    }

    @ApiOperation(value = "Dumb get")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Dumb get ok", responseContainer = "List", response = DumbEntity.class),
        @ApiResponse(code = 500, message = "Dumb get error") ])
    @GetMapping
    Single<ResponseEntity<?>> get() {
        return service.findAll()
                .map(
                        ResponseEntity.&ok
                ).onErrorResumeNext(
                { throwable ->
                    logger.error(throwable.getMessage(), throwable)
                    return Single.error(new UserMessageException("Dumb get error"))
                }
        )
    }

    @ApiOperation(value = "Dumb post")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "Dumb post ok", response = DumbEntity.class),
        @ApiResponse(code = 409, message = "Entity already exists"),
        @ApiResponse(code = 500, message = "Dumb post error") ])
    @PostMapping
    Single<ResponseEntity<?>> post(
            @RequestBody
                    Long id
    ) {
        return service.create(id)
                .map(
                        ResponseEntity.&ok
                ).onErrorResumeNext(
                { throwable ->
                    if (!(throwable instanceof ConflictException)) {
                        logger.error(throwable.getMessage(), throwable)
                        return Single.error(new UserMessageException("Dumb post error"))
                    } else {
                        return Single.error(throwable)
                    }
                }
        )
    }

    @ExceptionHandler([ UserMessageException.class ])
    static ResponseEntity<String> handleException(UserMessageException ex) {
        return new ResponseEntity<>(ex.getUserMessage(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler([ ConflictException.class ])
    static ResponseEntity<String> handleException(ConflictException ex) {
        return new ResponseEntity<>(ex.getUserMessage(), HttpStatus.CONFLICT)
    }
}