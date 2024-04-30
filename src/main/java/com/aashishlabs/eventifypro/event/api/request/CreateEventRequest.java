package com.aashishlabs.eventifypro.event.api.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequest {

  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull
  private String date;

  @NotNull
  private String location;
}
