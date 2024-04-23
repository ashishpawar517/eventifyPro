package com.aashishlabs.eventifypro.eventuser.service;

import com.aashishlabs.eventifypro.commons.auth.Role;
import com.aashishlabs.eventifypro.commons.enums.ErrorCode;
import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.mapper.EventUserDTOMapper;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import com.aashishlabs.eventifypro.eventuser.model.EventUserDTO;
import com.aashishlabs.eventifypro.eventuser.repository.IEventUserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventUserService implements IEventUserService {

  private final IEventUserRepository eventUserRepository;
  private final EventUserDTOMapper eventUserDTOMapper = new EventUserDTOMapper();
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public EventUserService(IEventUserRepository repository, PasswordEncoder passwordEncoder) {
    this.eventUserRepository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public EventUserDTO findById(Long userId) throws EventUserException {
    return eventUserRepository.findEventUserByUserId(userId)
        .map(eventUserDTOMapper)
        .orElseThrow(
            () -> new EventUserException("User not found with id: " + userId,
                ErrorCode.USER_NOT_FOUND));

  }

  @Override
  public EventUser createEventUser(String username, String firstName, String lastName,
      String emailAddress, String password) throws EventUserException {

    if (eventUserRepository.findEventUserByUsername(username).isPresent()
    ) {
      throw new EventUserException("Username already exists", ErrorCode.DUPLICATE_USERNAME);
    }
    if (eventUserRepository.findEventUserByEmailAddress(emailAddress).isPresent()) {
      throw new EventUserException("Email already exists", ErrorCode.DUPLICATE_EMAIL);
    }

    EventUser eventUser = new EventUser(username, firstName, lastName, emailAddress,
        passwordEncoder.encode(password));

    eventUser.setCreatedDate(LocalDateTime.now());
    eventUser.setUpdatedDate(LocalDateTime.now());
    eventUser.setRole(Role.USER);

    EventUser createdUser = eventUserRepository.save(eventUser);
    log.info("New user created with username : {}", createdUser.getUsername());
    return createdUser;
  }

  @Override
  public Optional<EventUser> findByUsername(String username) {
    return eventUserRepository.findEventUserByUsername(username);
  }
}
