package com.aashishlabs.eventifypro.eventuser.api;

import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.exception.EventUserNotFoundException;
import com.aashishlabs.eventifypro.eventuser.service.IEventUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
      throws EventUserNotFoundException {
    EventUser user = iEventUserService.findById(userId);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

}
