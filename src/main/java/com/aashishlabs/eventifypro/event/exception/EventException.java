package com.aashishlabs.eventifypro.event.exception;

import com.aashishlabs.eventifypro.commons.enums.ErrorCode;
import com.aashishlabs.eventifypro.commons.exception.BaseException;

public class EventException extends BaseException {

  private static final long serialVersionUID = 8873102948162518419L;
  private ErrorCode code;
  private String message;


  public EventException(String message, ErrorCode code) {
    super(message, code);
  }
}
