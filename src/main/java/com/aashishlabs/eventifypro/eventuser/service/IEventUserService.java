package com.aashishlabs.eventifypro.eventuser.service;

import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.model.EventUserDTO;

public interface IEventUserService {

  EventUserDTO findById(Long userId) throws EventUserException;

  EventUserDTO createEventUser(String username, String firstName, String lastName,
      String emailAddress, String password) throws EventUserException;

}
