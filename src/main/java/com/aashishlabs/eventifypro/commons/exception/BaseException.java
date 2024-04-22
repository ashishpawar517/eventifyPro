package com.aashishlabs.eventifypro.commons.exception;

import com.aashishlabs.eventifypro.commons.enums.ErrorCode;
import lombok.Getter;

public class BaseException extends Exception {

  private static final long serialVersionUID = 6160197389100967977L;

  @Getter
  private ErrorCode code;
  private String message;

  public BaseException(String message, ErrorCode code) {
    super(message);
    this.code = code;
  }
}
