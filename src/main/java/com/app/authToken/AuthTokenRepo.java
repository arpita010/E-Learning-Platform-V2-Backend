package com.app.authToken;

import com.app.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthTokenRepo extends CrudRepository<AuthToken, Long> {
  Optional<List<AuthToken>> findAllByUser(User user);

  Optional<AuthToken> findByAuthToken(String authToken);
}
