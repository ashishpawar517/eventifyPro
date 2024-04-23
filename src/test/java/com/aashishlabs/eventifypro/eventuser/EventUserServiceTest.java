package com.aashishlabs.eventifypro.eventuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aashishlabs.eventifypro.commons.auth.Role;
import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.model.EventUserDTO;
import com.aashishlabs.eventifypro.eventuser.repository.IEventUserRepository;
import com.aashishlabs.eventifypro.eventuser.service.EventUserService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class EventUserServiceTest {

  @Mock
  private IEventUserRepository eventUserRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  private EventUserService eventUserService;

  @BeforeEach
  void setUp() {
    eventUserService = new EventUserService(eventUserRepository, passwordEncoder);
  }

  @Test
  void testFindById_ExistingUser() throws EventUserException {
    // Arrange
    Long userId = 1L;
    EventUser eventUser = new EventUser("username", "firstName", "lastName", "email@example.com",
        "encodedPassword");
    when(eventUserRepository.findEventUserByUserId(userId)).thenReturn(Optional.of(eventUser));

    // Act
    EventUserDTO result = eventUserService.findById(userId);

    // Assert
    assertNotNull(result);
    // Add more assertions if needed
  }

  @Test
  void testFindById_NonExistingUser() {
    // Arrange
    Long userId = 2L;
    when(eventUserRepository.findEventUserByUserId(userId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EventUserException.class, () -> eventUserService.findById(userId));
  }

  @Test
  void testCreateEventUser_NewUser() throws EventUserException {
    // Arrange
    String username = "newuser";
    String firstName = "John";
    String lastName = "Doe";
    String emailAddress = "john.doe@example.com";
    String password = "password123";
    String encodedPassword = "encodedPassword";
    when(eventUserRepository.findEventUserByUsername(username)).thenReturn(Optional.empty());
    when(eventUserRepository.findEventUserByEmailAddress(emailAddress)).thenReturn(
        Optional.empty());
    when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

    EventUser expectedEventUser = new EventUser(username, firstName, lastName, emailAddress,
        encodedPassword);
    expectedEventUser.setCreatedDate(LocalDateTime.now());
    expectedEventUser.setUpdatedDate(LocalDateTime.now());
    expectedEventUser.setRole(Role.USER);
    when(eventUserRepository.save(any(EventUser.class))).thenReturn(expectedEventUser);

    // Act
    EventUser result = eventUserService.createEventUser(username, firstName, lastName, emailAddress,
        password);

    // Assert
    assertNotNull(result);
    assertEquals(username, result.getUsername());
    assertEquals(firstName, result.getFirstName());
    assertEquals(lastName, result.getLastName());
    assertEquals(emailAddress, result.getEmailAddress());
    assertEquals(encodedPassword, result.getPassword());
    assertNotNull(result.getCreatedDate());
    assertNotNull(result.getUpdatedDate());
    assertEquals(Role.USER, result.getRole());
    verify(eventUserRepository, times(1)).save(any(EventUser.class));
  }

  @Test
  void testCreateEventUser_DuplicateUsername() {
    // Arrange
    String username = "existinguser";
    String firstName = "John";
    String lastName = "Doe";
    String emailAddress = "john.doe@example.com";
    String password = "password123";
    when(eventUserRepository.findEventUserByUsername(username)).thenReturn(
        Optional.of(new EventUser()));

    // Act & Assert
    assertThrows(EventUserException.class,
        () -> eventUserService.createEventUser(username, firstName, lastName, emailAddress,
            password));
  }

  @Test
  void testCreateEventUser_DuplicateEmail() {
    // Arrange
    String username = "newuser";
    String firstName = "John";
    String lastName = "Doe";
    String emailAddress = "existing@example.com";
    String password = "password123";
    when(eventUserRepository.findEventUserByUsername(username)).thenReturn(Optional.empty());
    when(eventUserRepository.findEventUserByEmailAddress(emailAddress)).thenReturn(
        Optional.of(new EventUser()));

    // Act & Assert
    assertThrows(EventUserException.class,
        () -> eventUserService.createEventUser(username, firstName, lastName, emailAddress,
            password));
  }

  @Test
  void testFindByUsername_ExistingUsername() {
    // Arrange
    String username = "existinguser";
    EventUser eventUser = new EventUser(username, "firstName", "lastName", "email@example.com",
        "encodedPassword");
    when(eventUserRepository.findEventUserByUsername(username)).thenReturn(Optional.of(eventUser));

    // Act
    Optional<EventUser> result = eventUserService.findByUsername(username);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(eventUser, result.get());
  }

  @Test
  void testFindByUsername_NonExistingUsername() {
    // Arrange
    String username = "nonexistinguser";
    when(eventUserRepository.findEventUserByUsername(username)).thenReturn(Optional.empty());

    // Act
    Optional<EventUser> result = eventUserService.findByUsername(username);

    // Assert
    assertFalse(result.isPresent());
  }
}