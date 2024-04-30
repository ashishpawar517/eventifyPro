package com.aashishlabs.eventifypro.integrationtests;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class EventApiTests {

  public static final String EVENT_API_ENDPOINT = "/api/v1/event/";
  public static final String AUTH_LOGIN_ENDPOINT = "/api/v1/auth/login";
  private final JacksonJsonParser jsonParser = new JacksonJsonParser();

  @Autowired
  private MockMvc mockMvc;


  @Test
  @WithMockUser(username = "user1", roles = "USER")
  public void testAddWithUser() throws Exception {
    // Arrange
    String request = """
            {
          "name":"First Event Concert",
            "description":"Dua lipa concert",
            "location":"Perth-22",
            "date":"2024-04-29"
        }""";
    // Act
    ResultActions result = mockMvc.perform(
        post(EVENT_API_ENDPOINT + "add").contentType(MediaType.APPLICATION_JSON).content(request));

    // Assert
    result.andExpect(status().isForbidden())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.error").value("You are not authorized to access this resource."));
  }

  private String obtainAccessToken(String username, String password) throws Exception {
    String authRequest = """
        {
        "username":"%s",
        "password":"%s"
        }
        """.formatted(username, password);

    ResultActions result = mockMvc.perform(post(AUTH_LOGIN_ENDPOINT)
        .content(authRequest)
        .contentType(MediaType.APPLICATION_JSON));

    String resultString = result.andReturn().getResponse().getContentAsString();
    return jsonParser.parseMap(resultString).get("token").toString();
  }

  @Test
  public void testAddWithAdmin() throws Exception {
    // Arrange
    String accessToken = obtainAccessToken("admin_Alice", "password");

    String request = """
            {
            "name":"First Event Concert",
            "description":"Dua lipa concert",
            "location":"Perth",
            "date":"2024-04-29"
        }""";
    // Act
    ResultActions result = mockMvc.perform(
        post(EVENT_API_ENDPOINT + "add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request)
            .header("Authorization", "Bearer " + accessToken)
    );

    // Assert
    result.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.eventId").value("1"))
        .andExpect(jsonPath("$.name").value("First Event Concert"))
        .andExpect(jsonPath("$.description").value("Dua lipa concert"))
        .andExpect(jsonPath("$.location").value("Perth"))
        .andExpect(jsonPath("$.date").value("2024-04-29"));
  }


  @Test
  @WithMockUser(username = "user1", roles = "USER")
  public void testUpdateWithUser() throws Exception {
    // Arrange
    String request = """
            {
            "eventId":1,
            "name":"First Event Concert",
            "description":"Dua lipa concert-update",
            "location":"Perth-22",
            "date":"2024-04-29"
        }""";
    // Act
    ResultActions result = mockMvc.perform(
        post(EVENT_API_ENDPOINT + "update").contentType(MediaType.APPLICATION_JSON)
            .content(request));

    // Assert
    result.andExpect(status().isForbidden())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.error").value("You are not authorized to access this resource."));
  }

  @Test
  public void testUpdateWithAdmin() throws Exception {
    // Arrange
    String accessToken = obtainAccessToken("admin_Alice", "password");

    String requestToUpdate = """
            {
            "eventId":"1",
            "name":"First Event Concert-updated",
            "description":"Dua lipa concert-updated",
            "location":"Perth",
            "date":"2024-04-29"
        }""";
    // Act
    ResultActions result = mockMvc.perform(
        post(EVENT_API_ENDPOINT + "update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestToUpdate)
            .header("Authorization", "Bearer " + accessToken)
    );

    // Assert
    result.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.eventId").value("1"))
        .andExpect(jsonPath("$.name").value("First Event Concert-updated"))
        .andExpect(jsonPath("$.description").value("Dua lipa concert-updated"))
        .andExpect(jsonPath("$.location").value("Perth"))
        .andExpect(jsonPath("$.date").value("2024-04-29"));
  }

  @Test
  public void testUpdateWithAdminWithInvalidId() throws Exception {
    // Arrange
    String accessToken = obtainAccessToken("admin_Alice", "password");

    String requestToUpdate = """
            {
            "eventId":"100",
            "name":"First Event Concert-updated",
            "description":"Dua lipa concert-updated",
            "location":"Perth",
            "date":"2024-04-29"
        }""";
    // Act
    ResultActions result = mockMvc.perform(
        post(EVENT_API_ENDPOINT + "update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestToUpdate)
            .header("Authorization", "Bearer " + accessToken)
    );

    // Assert
    result.andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorCode").value("105"))
        .andExpect(jsonPath("$.errorMessage").value("Invalid Event Id"));
  }

  @Test
  @WithMockUser(username = "user1", roles = "USER")
  public void testDeleteWithUser() throws Exception {
    // Arrange
    // Act
    ResultActions result = mockMvc.perform(
        get(EVENT_API_ENDPOINT + "delete?eventId=" + 1001)
    );

    // Assert
    result.andExpect(status().isForbidden())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.error").value("You are not authorized to access this resource."));
  }

  @Test
  public void testDeleteWithAdminWithInvalidId() throws Exception {
    // Arrange
    String accessToken = obtainAccessToken("admin_Alice", "password");

    // Act
    ResultActions result = mockMvc.perform(
        get(EVENT_API_ENDPOINT + "delete?eventId=" + 1001)
            .header("Authorization", "Bearer " + accessToken)
    );

    // Assert
    result.andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorCode").value("105"))
        .andExpect(jsonPath("$.errorMessage").value("Invalid Event Id"));
  }

  @Test
  @Order(9)
  public void testDeleteWithAdminWithValidId() throws Exception {
    // Arrange
    String accessToken = obtainAccessToken("admin_Alice", "password");

    // Act
    ResultActions result = mockMvc.perform(
        get(EVENT_API_ENDPOINT + "delete?eventId=" + 1)
            .header("Authorization", "Bearer " + accessToken)
    );

    // Assert
    result.andExpect(status().isOk());
  }
}
