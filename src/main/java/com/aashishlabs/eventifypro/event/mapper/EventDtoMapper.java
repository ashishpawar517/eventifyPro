package com.aashishlabs.eventifypro.event.mapper;

import com.aashishlabs.eventifypro.event.model.Event;
import com.aashishlabs.eventifypro.event.model.EventDTO;
import java.util.function.Function;

public class EventDtoMapper implements Function<Event, EventDTO> {

  @Override
  public EventDTO apply(Event event) {
    return new EventDTO(event.getEventId(),
        event.getName(),
        event.getDescription(),
        event.getDate(),
        event.getLocation());
  }
}
