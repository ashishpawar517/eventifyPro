package com.aashishlabs.eventifypro.commons.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

  USER_NOT_FOUND(100),

  DUPLICATE_USERNAME(102),

  DUPLICATE_EMAIL(103);

  private int errorCode;

  ErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
