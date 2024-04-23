package com.aashishlabs.eventifypro.commons.auth.service;

import com.aashishlabs.eventifypro.commons.auth.controller.request.AuthenticationRequest;
import com.aashishlabs.eventifypro.commons.auth.controller.request.RegisterRequest;
import com.aashishlabs.eventifypro.commons.auth.controller.response.AuthenticationResponse;
import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.service.IEventUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final IEventUserService eventUserService;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    try {
      EventUser eventUser = eventUserService.createEventUser(request.getUsername(),
          request.getFirstName(), request.getLastName(), request.getEmailAddress(),
          request.getPassword());
      var jwtToken = jwtService.generateToken(eventUser);
      return new AuthenticationResponse(jwtToken);
    } catch (EventUserException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    var user = eventUserService.findByUsername(request.getUsername()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponse(jwtToken);
  }
}
