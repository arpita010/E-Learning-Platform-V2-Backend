package com.app.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByPhoneNumber(String phoneNumber);
}
