package com.aashishlabs.eventifypro.eventuser.service;

import com.aashishlabs.eventifypro.eventuser.exception.EventUserNotFoundException;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.repository.IEventUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class EventUserService implements IEventUserService {

  private final IEventUserRepository repository;

  @Autowired
  EventUserService(IEventUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public EventUser findById(Long userId) throws EventUserNotFoundException {
    return repository.findEventUserByUserId(userId)
        .orElseThrow(
            () -> new EventUserNotFoundException("User not found with id: " + userId, 100));

  }
}
