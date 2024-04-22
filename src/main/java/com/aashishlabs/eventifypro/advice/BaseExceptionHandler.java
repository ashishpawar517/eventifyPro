package com.aashishlabs.eventifypro.advice;

import com.aashishlabs.eventifypro.commons.exception.BaseException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
    Map<String, String> map = new HashMap<>();
    exception.getBindingResult().getFieldErrors()
        .forEach(fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage()));

    return map;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BaseException.class)
  public Map<String, String> handleBaseException(BaseException exception) {
    Map<String, String> map = new HashMap<>();

    map.put("errorMessage", exception.getMessage());
    map.put("errorCode", String.valueOf(exception.getCode().getErrorCode()));

    log.error(exception.getMessage(), exception.getCode().getErrorCode());
    return map;
  }

}
