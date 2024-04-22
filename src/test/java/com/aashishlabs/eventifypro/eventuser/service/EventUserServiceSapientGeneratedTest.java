package com.aashishlabs.eventifypro.eventuser.service;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import com.aashishlabs.eventifypro.eventuser.exception.EventUserException;
import com.aashishlabs.eventifypro.eventuser.model.EventUserDTO;
import com.aashishlabs.eventifypro.eventuser.repository.IEventUserRepository;
import com.aashishlabs.eventifypro.commons.enums.ErrorCode;
import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class EventUserServiceSapientGeneratedTest {

  private final IEventUserRepository eventUserRepositoryMock = mock(IEventUserRepository.class,
      "eventUserRepository");

  private final EventUser eventUserMock = mock(EventUser.class);

  //Sapient generated method id: ${c28caada-4bc6-3f84-9d59-f4959d055219}, hash: 4C1A62C195929EE58A3B5D0BD030B83B
  @Test()
  void createEventUserWhenEventUserRepositoryFindEventUserByUsernameUsernameIsPresentThrowsEventUserException()
      throws EventUserException {
    /* Branches:
     * (eventUserRepository.findEventUserByUsername(username).isPresent()) : true
     */
    //Arrange Statement(s)
    EventUserService target = new EventUserService(eventUserRepositoryMock);
    doReturn(Optional.of(eventUserMock)).when(eventUserRepositoryMock)
        .findEventUserByUsername("username1");
    EventUserException eventUserException = new EventUserException("Username already exists",
        ErrorCode.DUPLICATE_USERNAME);
    //Act Statement(s)
    final EventUserException result = assertThrows(EventUserException.class, () -> {
      target.createEventUser("username1", "firstName1", "lastName1", "emailAddress1", "password1");
    });
    //Assert statement(s)
    assertAll("result", () -> {
      assertThat(result, is(notNullValue()));
      assertThat(result.getMessage(), equalTo(eventUserException.getMessage()));
      verify(eventUserRepositoryMock).findEventUserByUsername("username1");
    });
  }

  //Sapient generated method id: ${07d69981-1ebe-39ec-a340-ddde9b28e528}, hash: DC11107AEBBE679DF20BF6C523FE7995
  @Test()
  void createEventUserWhenEventUserRepositoryFindEventUserByEmailAddressEmailAddressIsPresentThrowsEventUserException()
      throws EventUserException {
    /* Branches:
     * (eventUserRepository.findEventUserByUsername(username).isPresent()) : false
     * (eventUserRepository.findEventUserByEmailAddress(emailAddress).isPresent()) : true
     */
    //Arrange Statement(s)
    EventUserService target = new EventUserService(eventUserRepositoryMock);
    doReturn(Optional.empty()).when(eventUserRepositoryMock).findEventUserByUsername("username1");
    doReturn(Optional.of(eventUserMock)).when(eventUserRepositoryMock)
        .findEventUserByEmailAddress("emailAddress1");
    EventUserException eventUserException = new EventUserException("Email already exists",
        ErrorCode.DUPLICATE_EMAIL);
    //Act Statement(s)
    final EventUserException result = assertThrows(EventUserException.class, () -> {
      target.createEventUser("username1", "firstName1", "lastName1", "emailAddress1", "password1");
    });
    //Assert statement(s)
    assertAll("result", () -> {
      assertThat(result, is(notNullValue()));
      assertThat(result.getMessage(), equalTo(eventUserException.getMessage()));
      verify(eventUserRepositoryMock).findEventUserByUsername("username1");
      verify(eventUserRepositoryMock).findEventUserByEmailAddress("emailAddress1");
    });
  }

  //Sapient generated method id: ${224f670d-10dd-3127-be0a-0a74e045399b}, hash: 9C103E717BEFBC64DA2148003F61E810
  @Test()
  void createEventUserWhenEventUserRepositoryFindEventUserByEmailAddressEmailAddressNotIsPresent()
      throws EventUserException {
    /* Branches:
     * (eventUserRepository.findEventUserByUsername(username).isPresent()) : false
     * (eventUserRepository.findEventUserByEmailAddress(emailAddress).isPresent()) : false
     *
     * TODO: Help needed! This method is not unit testable!
     *  A variable could not be isolated/mocked when calling a method - Variable name: eventUserDTOMapper - Method: apply
     *  Suggestions:
     *  You can pass them as constructor arguments or create a setter for them (avoid new operator)
     *  or adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
     *  The test code, including the assertion statements, has been successfully generated.
     */
    //Arrange Statement(s)
    EventUserService target = new EventUserService(eventUserRepositoryMock);
    doReturn(Optional.empty()).when(eventUserRepositoryMock).findEventUserByUsername("username1");
    doReturn(Optional.empty()).when(eventUserRepositoryMock)
        .findEventUserByEmailAddress("emailAddress1");
    EventUser eventUser = new EventUser();
    eventUser.setUsername("username1");
    doReturn(eventUser).when(eventUserRepositoryMock).save((EventUser) any());
    //Act Statement(s)
    EventUserDTO result = target.createEventUser("username1", "firstName1", "lastName1",
        "emailAddress1", "password1");
    //Assert statement(s)
    //TODO: Please implement equals method in EventUserDTO for verification of the entire object or you need to adjust respective assertion statements
    assertAll("result", () -> {
      assertThat(result, is(notNullValue()));
      verify(eventUserRepositoryMock).findEventUserByUsername("username1");
      verify(eventUserRepositoryMock).findEventUserByEmailAddress("emailAddress1");
      verify(eventUserRepositoryMock).save((EventUser) any());
    });
  }
}
