package com.aashishlabs.eventifypro.eventuser.mapper;

import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.model.EventUserDTO;
import java.util.function.Function;

public class EventUserDTOMapper implements Function<EventUser, EventUserDTO> {

  @Override
  public EventUserDTO apply(EventUser eventUser) {
    return new EventUserDTO(eventUser.getUsername(),
        eventUser.getFirstName(),
        eventUser.getLastName(),
        eventUser.getEmailAddress());
  }
}
