package com.aashishlabs.eventifypro.commons.exception;

import lombok.Getter;

public class BaseException extends Exception {

  private static final long serialVersionUID = 6160197389100967977L;

  @Getter
  private final int code;
  private String message;

  public BaseException(String message, int code) {
    super(message);
    this.code = code;
  }
}
