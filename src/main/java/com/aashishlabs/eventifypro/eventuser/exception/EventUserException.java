package com.aashishlabs.eventifypro.eventuser.exception;

import com.aashishlabs.eventifypro.commons.enums.ErrorCode;
import com.aashishlabs.eventifypro.commons.exception.BaseException;

public class EventUserException extends BaseException {

  private static final long serialVersionUID = 6815188006868819959L;
  private ErrorCode code;
  private String message;


  public EventUserException(String message, ErrorCode code) {
    super(message, code);
  }
}
