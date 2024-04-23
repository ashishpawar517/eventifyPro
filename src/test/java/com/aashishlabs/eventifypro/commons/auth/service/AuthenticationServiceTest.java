package com.aashishlabs.eventifypro.commons.auth.service;

import com.aashishlabs.eventifypro.commons.auth.controller.request.AuthenticationRequest;
import com.aashishlabs.eventifypro.commons.auth.controller.request.RegisterRequest;
import com.aashishlabs.eventifypro.commons.auth.controller.response.AuthenticationResponse;
import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.service.IEventUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

  @Mock
  private IEventUserService eventUserService;

  @Mock
  private JwtService jwtService;

  @Mock
  private AuthenticationManager authenticationManager;

  private AuthenticationService authenticationService;

  @BeforeEach
  void setUp() {
    authenticationService = new AuthenticationService(eventUserService, jwtService, authenticationManager);
  }

  @Test
  void testRegister_Success() throws EventUserException {
    // Arrange
    RegisterRequest request = new RegisterRequest("username", "firstName", "lastName", "email@example.com", "password");
    EventUser eventUser = new EventUser("username", "firstName", "lastName", "email@example.com", "encodedPassword");
    String jwtToken = "jwtToken";

    when(eventUserService.createEventUser(any(), any(), any(), any(), any())).thenReturn(eventUser);
    when(jwtService.generateToken(eventUser)).thenReturn(jwtToken);

    // Act
    AuthenticationResponse response = authenticationService.register(request);

    // Assert
    assertNotNull(response);
    assertEquals(jwtToken, response.getToken());
    verify(eventUserService, times(1)).createEventUser(request.getUsername(), request.getFirstName(), request.getLastName(), request.getEmailAddress(), request.getPassword());
  }

  @Test
  void testRegister_EventUserException() throws EventUserException {
    // Arrange
    RegisterRequest request = new RegisterRequest("username", "firstName", "lastName", "email@example.com", "password");
    EventUserException exception = new EventUserException("User not found", null);

    when(eventUserService.createEventUser(any(), any(), any(), any(), any())).thenThrow(exception);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> authenticationService.register(request));
  }

  @Test
  void testAuthenticate_Success() {
    // Arrange
    AuthenticationRequest request = new AuthenticationRequest("username", "password");
    EventUser eventUser = new EventUser("username", "firstName", "lastName", "email@example.com", "encodedPassword");
    String jwtToken = "jwtToken";

    when(eventUserService.findByUsername(request.getUsername())).thenReturn(Optional.of(eventUser));
    when(jwtService.generateToken(eventUser)).thenReturn(jwtToken);

    // Act
    AuthenticationResponse response = authenticationService.authenticate(request);

    // Assert
    assertNotNull(response);
    assertEquals(jwtToken, response.getToken());
    verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
  }

  @Test
  void testAuthenticate_UserNotFound() {
    // Arrange
    AuthenticationRequest request = new AuthenticationRequest("username", "password");

    when(eventUserService.findByUsername("username")).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RuntimeException.class, () -> authenticationService.authenticate(request));
  }

  @Test
  void testAuthenticate_InvalidCredentials() {
    // Arrange
    AuthenticationRequest request = new AuthenticationRequest("username", "password");

    when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

    // Act & Assert
    assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(request));
  }
}