package com.aashishlabs.eventifypro.eventuser.repository;

import com.aashishlabs.eventifypro.eventuser.model.EventUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventUserRepository extends JpaRepository<EventUser, Long> {

  Optional<EventUser> findEventUserByUserId(Long userId);

  Optional<EventUser> findEventUserByUsername(String username);

  Optional<EventUser> findEventUserByEmailAddress(String emailAddress);

}
