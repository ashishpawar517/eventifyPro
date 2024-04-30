package com.aashishlabs.eventifypro.event.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event")
@NoArgsConstructor
public class Event {

  private static final String EVENT_NAME_COLUMN = "event_name";
  private static final String EVENT_NAME_DESCRIPTION = "event_description";
  private static final String EVENT_NAME_DATE_COLUMN = "event_date";
  private static final String EVENT_NAME_LOCATION_COLUMN = "event_location";
  private static final String EVENT_NAME_CREATED_DATE_COLUMN = "created_date";
  private static final String EVENT_NAME_UPDATED_DATE_COLUMN = "updated_date";

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eventId;

  @Setter
  @Getter
  @Column(name = EVENT_NAME_COLUMN)
  private String name;

  @Setter
  @Getter
  @Column(name = EVENT_NAME_DESCRIPTION)
  private String description;

  @Setter
  @Getter
  @Column(name = EVENT_NAME_DATE_COLUMN)
  private LocalDate date;

  @Setter
  @Getter
  @Column(name = EVENT_NAME_LOCATION_COLUMN)
  private String location;

  @Getter
  @Column(name = EVENT_NAME_CREATED_DATE_COLUMN)
  private LocalDateTime createdDate;

  @Setter
  @Column(name = EVENT_NAME_UPDATED_DATE_COLUMN)
  private LocalDateTime updatedDate;

  public Event(String name, String description, LocalDate date, String location,
      LocalDateTime createdDate, LocalDateTime updatedDate) {
    this.name = name;
    this.description = description;
    this.date = date;
    this.location = location;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }
}
