package ru.softdarom.qrcheck.auth.google.rest.handler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j(topic = "GOOGLE-AUTH-EXCEPTION-HANDLER")
public class GoogleAuthExceptionHandler {

    @ExceptionHandler(FeignException.NotFound.class)
    ResponseEntity<Void> feignException(FeignException.NotFound e) {
        LOGGER.warn(e.getMessage(), e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(FeignException.InternalServerError.class)
    ResponseEntity<Void> feignInternalServerException(FeignException.InternalServerError e) {
        LOGGER.warn(e.getMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Void> unknown(Exception e) {
        LOGGER.warn(e.getMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }

}