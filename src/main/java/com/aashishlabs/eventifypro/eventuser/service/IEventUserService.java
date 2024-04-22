package com.aashishlabs.eventifypro.eventuser.service;

import com.aashishlabs.eventifypro.eventuser.exception.EventUserNotFoundException;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;

public interface IEventUserService {

  EventUser findById(Long userId) throws EventUserNotFoundException;
}
