package com.aashishlabs.eventifypro.event.service;

import com.aashishlabs.eventifypro.commons.enums.ErrorCode;
import com.aashishlabs.eventifypro.event.exception.EventException;
import com.aashishlabs.eventifypro.event.mapper.EventDtoMapper;
import com.aashishlabs.eventifypro.event.model.Event;
import com.aashishlabs.eventifypro.event.model.EventDTO;
import com.aashishlabs.eventifypro.event.repository.IEventRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

  private final IEventRepository eventRepository;
  private final EventDtoMapper eventDtoMapper = new EventDtoMapper();

  @Override
  public EventDTO createEvent(String eventName, String eventDescription, String eventLocation,
      String eventDate) throws EventException {
    LocalDate parseDate = LocalDate.parse(eventDate);
    if (eventRepository.findEventByDateAndLocation(parseDate, eventLocation).isPresent()) {
      throw new EventException("Already occupied event in this location",
          ErrorCode.EVENT_NOT_ALLOWED);
    }

    Event createdEvent = eventRepository.save(new Event(eventName,
        eventDescription,
        parseDate,
        eventLocation,
        LocalDateTime.now(),
        LocalDateTime.now()));

    return eventDtoMapper.apply(createdEvent);
  }

  @Override
  public EventDTO updateEvent(Long eventId, String eventName, String eventDescription,
      String eventLocation, String eventDate) throws EventException,
      DateTimeParseException {
    log.info("update {}", eventId);
    Optional<Event> fetchedEventOptional = eventRepository.findEventByEventId(eventId);
    if (fetchedEventOptional.isEmpty()) {
      throw new EventException("Invalid Event Id",
          ErrorCode.EVENT_NOT_FOUND);
    }
    Event fetchedEvent = fetchedEventOptional.get();
    fetchedEvent.setName(eventName);
    fetchedEvent.setDescription(eventDescription);
    fetchedEvent.setLocation(eventLocation);
    fetchedEvent.setDate(LocalDate.parse(eventDate));
    Event updatedEvent = eventRepository.save(fetchedEvent);

    log.info("event with event id {} updated.", updatedEvent.getEventId());
    return eventDtoMapper.apply(updatedEvent);
  }

  @Override
  @Transactional
  public void deleteEvent(Long eventId) throws EventException {
    Optional<Event> fetchedEventOptional = eventRepository.findEventByEventId(eventId);
    if (fetchedEventOptional.isEmpty()) {
      log.info("delete event {}", eventId);

      throw new EventException("Invalid Event Id",
          ErrorCode.EVENT_NOT_FOUND);
    }
   long ret = eventRepository.deleteByEventId(eventId);

  }

  public Page<Event> getAllEvents(int page, int size) {
    PageRequest pageable = PageRequest.of(page, size);
    return eventRepository.findAll(pageable);
  }

}
