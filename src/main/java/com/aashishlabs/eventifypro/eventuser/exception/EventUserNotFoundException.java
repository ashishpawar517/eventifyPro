package com.aashishlabs.eventifypro.eventuser.exception;

import com.aashishlabs.eventifypro.commons.exception.BaseException;

public class EventUserNotFoundException extends BaseException {

  private static final long serialVersionUID = 6815188006868819959L;
  private int code;
  private String message;


  public EventUserNotFoundException(String message, int code) {
    super(message, code);
  }
}
