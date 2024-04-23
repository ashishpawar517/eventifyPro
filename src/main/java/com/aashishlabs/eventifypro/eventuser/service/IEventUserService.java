package com.aashishlabs.eventifypro.eventuser.service;

import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.model.EventUserDTO;
import java.util.Optional;

public interface IEventUserService {

  EventUserDTO findById(Long userId) throws EventUserException;

  EventUser createEventUser(String username, String firstName, String lastName,
      String emailAddress, String password) throws EventUserException;

  Optional<EventUser> findByUsername(String Username);
}
