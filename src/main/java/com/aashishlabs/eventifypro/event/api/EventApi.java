package com.aashishlabs.eventifypro.event.api;

import com.aashishlabs.eventifypro.event.api.request.CreateEventRequest;
import com.aashishlabs.eventifypro.event.api.request.UpdateEventRequest;
import com.aashishlabs.eventifypro.event.exception.EventException;
import com.aashishlabs.eventifypro.event.service.IEventService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventApi {

  private final IEventService eventService;

  @PostMapping("/add")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> createEvent(@Valid @RequestBody CreateEventRequest request)
      throws EventException {
    return new ResponseEntity<>(
        eventService.createEvent(request.getName(),
            request.getDescription(),
            request.getLocation(),
            request.getDate()), HttpStatus.OK);
  }

  @PostMapping("/update")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> updateEvent(@Valid @RequestBody UpdateEventRequest request)
      throws EventException {
    return new ResponseEntity<>(
        eventService.updateEvent(request.getEventId(),
            request.getName(),
            request.getDescription(),
            request.getLocation(),
            request.getDate()), HttpStatus.OK);
  }

  @GetMapping("/delete")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> delete(@RequestParam Long eventId)
      throws EventException {
    eventService.deleteEvent(eventId);

    return new ResponseEntity<>(Map.of("message", "Event successfully deleted")
        , HttpStatus.OK);
  }

  @GetMapping("/getAll")
  public ResponseEntity<?> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {

    return new ResponseEntity<>(eventService.getAllEvents(page, size)
        , HttpStatus.OK);
  }
}
