package com.aashishlabs.eventifypro.eventuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.mapper.EventUserDTOMapper;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.model.EventUserDTO;
import com.aashishlabs.eventifypro.eventuser.repository.IEventUserRepository;
import com.aashishlabs.eventifypro.eventuser.service.EventUserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;


public class EventUserServiceTest {

  // findById method returns EventUserDTO when given a valid userId
  @Test
  public void test_findById_returns_EventUserDTO_when_given_valid_userId()
      throws EventUserException {
    // Arrange
    Long userId = 1L;
    EventUserDTO expected = new EventUserDTO("username", "firstName", "lastName", "emailAddress");
    IEventUserRepository eventUserRepository = mock(IEventUserRepository.class);
    EventUserDTOMapper eventUserDTOMapper = mock(EventUserDTOMapper.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    EventUserService eventUserService = new EventUserService(eventUserRepository, passwordEncoder);
    when(eventUserRepository.findEventUserByUserId(userId)).thenReturn(
        Optional.of(new EventUser()));
    when(eventUserDTOMapper.apply(any(EventUser.class))).thenReturn(expected);

    // Act
    EventUserDTO result = eventUserService.findById(userId);

    // Assert
    assertEquals(expected, result);
  }

  // findById method throws EventUserException when given an invalid userId
  @Test
  public void test_findById_throws_EventUserException_when_given_invalid_userId() {
    // Arrange
    Long userId = 1L;
    IEventUserRepository eventUserRepository = mock(IEventUserRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    EventUserService eventUserService = new EventUserService(eventUserRepository, passwordEncoder);
    when(eventUserRepository.findEventUserByUserId(userId)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(EventUserException.class, () -> eventUserService.findById(userId));
  }

  // createEventUser method creates a new user when given valid parameters
  @Test
  public void test_createEventUser_creates_new_user_with_valid_parameters()
      throws EventUserException {
    // Arrange
    String username = "testUser";
    String firstName = "John";
    String lastName = "Doe";
    String emailAddress = "johndoe@example.com";
    String password = "password";
    IEventUserRepository eventUserRepository = mock(IEventUserRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    EventUserService eventUserService = new EventUserService(eventUserRepository, passwordEncoder);
    when(eventUserRepository.findEventUserByUsername(username)).thenReturn(Optional.empty());
    when(eventUserRepository.findEventUserByEmailAddress(emailAddress)).thenReturn(
        Optional.empty());
    when(eventUserRepository.save(any(EventUser.class))).thenReturn(new EventUser());

    // Act
    EventUser result = eventUserService.createEventUser(username, firstName, lastName, emailAddress,
        password);

    // Assert
    assertNotNull(result);
    assertEquals(username, result.getUsername());
    assertEquals(firstName, result.getFirstName());
    assertEquals(lastName, result.getLastName());
    assertEquals(emailAddress, result.getEmailAddress());
    assertTrue(passwordEncoder.matches(password, result.getPassword()));
  }

  // findByUsername method returns Optional<EventUser> when given a valid username
  @Test
  public void test_findByUsername_returns_Optional_EventUser_when_given_valid_username() {
    // Arrange
    String username = "testUser";
    IEventUserRepository eventUserRepository = mock(IEventUserRepository.class);
    EventUserService eventUserService = new EventUserService(eventUserRepository, null);
    when(eventUserRepository.findEventUserByUsername(username)).thenReturn(
        Optional.of(new EventUser()));

    // Act
    Optional<EventUser> result = eventUserService.findByUsername(username);

    // Assert
    assertNotNull(result);
    assertTrue(result.isPresent());
  }

  // createEventUser method encodes the password before saving the user
  @Test
  public void test_createEventUser_encodes_password_before_saving_user() throws EventUserException {
    // Arrange
    String username = "testUser";
    String firstName = "John";
    String lastName = "Doe";
    String emailAddress = "johndoe@example.com";
    String password = "password";
    IEventUserRepository eventUserRepository = mock(IEventUserRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    EventUserService eventUserService = new EventUserService(eventUserRepository, passwordEncoder);
    when(eventUserRepository.findEventUserByUsername(username)).thenReturn(Optional.empty());
    when(eventUserRepository.findEventUserByEmailAddress(emailAddress)).thenReturn(
        Optional.empty());
    when(eventUserRepository.save(any(EventUser.class))).thenReturn(new EventUser());

    // Act
    eventUserService.createEventUser(username, firstName, lastName, emailAddress, password);

    // Assert
    verify(passwordEncoder).encode(password);
  }
}
