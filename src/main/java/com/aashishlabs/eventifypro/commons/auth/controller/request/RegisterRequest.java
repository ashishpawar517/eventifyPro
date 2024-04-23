package com.aashishlabs.eventifypro.commons.auth.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String username;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String password;
}
