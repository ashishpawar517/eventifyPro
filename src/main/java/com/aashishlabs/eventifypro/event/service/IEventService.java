package com.aashishlabs.eventifypro.event.service;

import com.aashishlabs.eventifypro.event.exception.EventException;
import com.aashishlabs.eventifypro.event.model.Event;
import com.aashishlabs.eventifypro.event.model.EventDTO;
import org.springframework.data.domain.Page;

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

   Page<Event> getAllEvents(int page, int size) ;
}
