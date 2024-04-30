package com.aashishlabs.eventifypro.event.service;

import com.aashishlabs.eventifypro.event.exception.EventException;
import com.aashishlabs.eventifypro.event.model.EventDTO;

public interface IEventService {

  EventDTO createEvent(String eventName,
      String eventDescription,
      String eventLocation,
      String eventDate) throws EventException;

  EventDTO updateEvent(Long eventId,
      String eventName,
      String eventDescription,
      String eventLocation,
      String eventDate) throws EventException;


  void deleteEvent(Long eventId) throws EventException;
}
