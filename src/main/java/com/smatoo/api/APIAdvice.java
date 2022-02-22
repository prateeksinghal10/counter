package com.smatoo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIAdvice {
    private static Logger LOG= LoggerFactory.getLogger(APIAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception exception, WebRequest request) {
        LOG.error("Error in processing request ",exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("failed");
    }

}
