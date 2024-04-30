package com.aashishlabs.eventifypro.commons.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//  @ExceptionHandler(Exception.class)
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//  public ResponseEntity<String> handleGeneralError(Exception ex, WebRequest request) {
//    log.error("Global exception occurred:: {} request {} ", ex.getMessage(), request.toString());
//    return new ResponseEntity<>(
//        "The system is experiencing problems. Please try again after some time.",
//        HttpStatus.INTERNAL_SERVER_ERROR);
//  }
}
