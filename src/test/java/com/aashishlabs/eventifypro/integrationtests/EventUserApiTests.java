package com.aashishlabs.eventifypro.integrationtests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class EventUserApiTests {

  public static final String API_V_1_EVENT_USER = "/api/v1/eventUser/";
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetUserByIdWithoutAuth() throws Exception {
    // Arrange
    long userId = 1L;

    // Act
    ResultActions result = mockMvc.perform(get(API_V_1_EVENT_USER + "getUser?userId={id}", userId));

    // Assert
    result.andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "user1", roles = "USER")
  public void testGetUserById() throws Exception {
    // Arrange
    long userId = 1L;

    // Act
    ResultActions result = mockMvc.perform(get(API_V_1_EVENT_USER + "getUser?userId={id}", userId));

    // Assert
    result.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.firstName").value("John"))
        .andExpect(jsonPath("$.lastName").value("Doe"))
        .andExpect(jsonPath("$.username").value("john_doe"))
        .andExpect(jsonPath("$.emailAddress").value("john.doe@example.com"));
  }
}
