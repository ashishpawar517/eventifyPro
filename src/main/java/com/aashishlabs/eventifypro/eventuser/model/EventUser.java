package com.aashishlabs.eventifypro.eventuser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "event_user")
public class EventUser {

  private static final String EVENT_USER_FIRST_NAME_COLUMN = "firstname";
  private static final String EVENT_USER_USERNAME_COLUMN = "username";
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @Column(name = EVENT_USER_USERNAME_COLUMN)
  private String username;

  @Column(name = EVENT_USER_FIRST_NAME_COLUMN)
  private String firstName;
  @Column(name = "lastname")
  private String lastName;
  @Column(name = "email_address")
  private String emailAddress;

  @Column(name = "password")
  private String password;

  public EventUser() {
  }

  public EventUser(String username, String firstName, String lastName, String emailAddress,
      String password) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.password = password;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
