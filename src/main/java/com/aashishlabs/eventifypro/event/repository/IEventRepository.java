package com.aashishlabs.eventifypro.event.repository;

import com.aashishlabs.eventifypro.event.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface IEventRepository extends CrudRepository<Event, Long> {

}
