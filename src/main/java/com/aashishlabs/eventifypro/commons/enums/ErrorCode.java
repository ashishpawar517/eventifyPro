package com.aashishlabs.eventifypro.commons.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

  USER_NOT_FOUND(100),

  DUPLICATE_USERNAME(102),

  DUPLICATE_EMAIL(103),

  EVENT_NOT_ALLOWED(104),

  EVENT_NOT_FOUND(105);

  private int errorCode;

  ErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
