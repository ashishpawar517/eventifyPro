package com.aashishlabs.eventifypro.commons.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  USER,
  ADMIN;

  @Override
  public String getAuthority() {
    return this.name();
  }
}
