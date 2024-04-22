package com.aashishlabs.eventifypro.eventuser.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateEventUserRequest {
  @NotNull
  @NotBlank
  private String username;

  @NotNull
  @NotBlank
  private String firstName;

  @NotNull
  @NotBlank
  private String lastName;

  @NotNull
  @NotBlank
  private String emailAddress;

  @NotNull
  @NotBlank
  private String password;

}
