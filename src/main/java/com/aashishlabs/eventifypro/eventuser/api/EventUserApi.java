package com.aashishlabs.eventifypro.eventuser.api;

import com.aashishlabs.eventifypro.eventuser.api.request.CreateEventUserRequest;
import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.model.EventUserDTO;
import com.aashishlabs.eventifypro.eventuser.service.IEventUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/eventUser")
public class EventUserApi {

  private final IEventUserService iEventUserService;

  @Autowired
  public EventUserApi(IEventUserService iEventUserService) {
    this.iEventUserService = iEventUserService;
  }

  @GetMapping("/getUser")
  public ResponseEntity<?> getUserById(@RequestParam Long userId)
      throws EventUserException {
    EventUserDTO user = iEventUserService.findById(userId);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<?> createNewEventUser(@Valid @RequestBody CreateEventUserRequest request)
      throws EventUserException {
    EventUserDTO eventUser = iEventUserService.createEventUser(request.getUsername(),
        request.getFirstName(),
        request.getLastName(),
        request.getEmailAddress(),
        request.getPassword());

    return new ResponseEntity<>(eventUser, HttpStatus.OK);
  }
}
