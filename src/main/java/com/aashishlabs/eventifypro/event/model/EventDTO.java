package com.aashishlabs.eventifypro.event.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDTO {

  private Long eventId;

  private String name;

  private String description;

  private LocalDate date;

  private String location;

}
