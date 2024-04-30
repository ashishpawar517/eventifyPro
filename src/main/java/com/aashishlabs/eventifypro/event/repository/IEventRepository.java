package com.aashishlabs.eventifypro.event.repository;

import com.aashishlabs.eventifypro.event.model.Event;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface IEventRepository extends CrudRepository<Event, Long> {

  Optional<Event> findEventByEventId(Long eventId);

  Optional<Event> findEventByDateAndLocation(LocalDate date, String location);

  long deleteByEventId(Long eventId);

  Page<Event> findAll(Pageable pageable);
}
