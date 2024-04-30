package com.aashishlabs.eventifypro.event.api;

import com.aashishlabs.eventifypro.event.api.request.CreateEventRequest;
import com.aashishlabs.eventifypro.event.exception.EventException;
import com.aashishlabs.eventifypro.event.service.IEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
