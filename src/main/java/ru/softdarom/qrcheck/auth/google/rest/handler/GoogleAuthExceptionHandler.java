package ru.softdarom.qrcheck.auth.google.rest.handler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.softdarom.qrcheck.auth.google.exception.NotAuthenticatedException;
import ru.softdarom.qrcheck.auth.google.model.dto.response.BaseResponse;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
@Slf4j(topic = "EXCEPTION-HANDLER")
public class GoogleAuthExceptionHandler {

    @ExceptionHandler({FeignException.Unauthorized.class, NotAuthenticatedException.class})
    ResponseEntity<BaseResponse> notAuthenticatedException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(UNAUTHORIZED).body(new BaseResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<BaseResponse> unknown(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new BaseResponse(e.getMessage()));
    }

}