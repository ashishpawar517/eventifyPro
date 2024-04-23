package com.aashishlabs.eventifypro.eventuser.model;

import com.aashishlabs.eventifypro.commons.auth.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "event_user")
public class EventUser implements UserDetails {

  private static final String EVENT_USER_LAST_NAME_COLUMN_NAME = "lastname";
  private static final String EVENT_USER_EMAIL_ADDRESS_COLUMN_NAME = "email_address";
  private static final String EVENT_USER_PASSWORD_COLUMN_NAME = "password";
  private static final String EVENT_USER_CREATED_DATE_COLUMN_NAME = "created_date";
  private static final String EVENT_USER_UPDATED_DATE_COLUMN_NAME = "updated_date";
  private static final String EVENT_USER_FIRST_NAME_COLUMN = "firstname";
  private static final String EVENT_USER_USERNAME_COLUMN = "username";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Getter
  @Column(name = EVENT_USER_USERNAME_COLUMN)
  private String username;

  @Getter
  @Column(name = EVENT_USER_FIRST_NAME_COLUMN)
  private String firstName;

  @Getter
  @Column(name = EVENT_USER_LAST_NAME_COLUMN_NAME)
  private String lastName;

  @Getter
  @Column(name = EVENT_USER_EMAIL_ADDRESS_COLUMN_NAME)
  private String emailAddress;

  @Column(name = EVENT_USER_PASSWORD_COLUMN_NAME)
  private String password;

  @Setter
  @Enumerated(EnumType.STRING)
  private Role role;

  @Getter
  @Setter
  @Column(name = EVENT_USER_CREATED_DATE_COLUMN_NAME)
  private LocalDateTime createdDate;

  @Setter
  @Column(name = EVENT_USER_UPDATED_DATE_COLUMN_NAME)
  private LocalDateTime updatedDate;

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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
