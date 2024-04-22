package com.aashishlabs.eventifypro.eventuser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventUserDTO {

  private String username;

  private String firstName;

  private String lastName;

  private String emailAddress;
}
